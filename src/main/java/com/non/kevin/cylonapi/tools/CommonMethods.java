package com.non.kevin.cylonapi.tools;

import com.non.kevin.cylonapi.exceptionHandling.ControllerErrorResponse;
import com.non.kevin.cylonapi.exceptionHandling.ControllerOffLine;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

import static com.non.kevin.cylonapi.tools.Checksums.calCheckSum;

public class CommonMethods {
    public static byte[] rawPacket(byte[] packet, String hostIP, int port){
        packet[4] = (byte) (packet.length);
        packet[packet.length - 1] = (byte) (calCheckSum(packet) & 0xFF);
        int bytesRead = 0;
        byte[] rawData = new byte[0];
        try (Socket socket = new Socket(hostIP, port)) {
            socket.setSoTimeout(10000);
            port = 4950;
            //Create a stream to send message to controller
            OutputStream output = new DataOutputStream(socket.getOutputStream());

            //Send the message to the controller
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, StandardCharsets.ISO_8859_1), true);
            output.write(packet);
            //Need to flush the buffer
            output.flush();

            //Create a stream to accept the message from the controller
            InputStream input = new DataInputStream(socket.getInputStream());

            //The message length is unknown therefore create an array long enough to accept any length
            byte[] rawAlarmPacket = new byte[4080];
            //Get the number of bytes read
            bytesRead = input.read( rawAlarmPacket, 0, rawAlarmPacket.length);
            //Create an array the same length as the packet from the controller
            rawData = new byte[bytesRead];
            System.arraycopy(rawAlarmPacket,0,rawData,0,bytesRead);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new ControllerOffLine("Controller offline");
            //e.printStackTrace();
        }
        return rawData;
    }
    @ExceptionHandler
    public ResponseEntity<ControllerErrorResponse> handleException(ControllerOffLine exc) {
        ControllerErrorResponse error = new ControllerErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ControllerErrorResponse> handleException(Exception exc) {
        ControllerErrorResponse error = new ControllerErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
