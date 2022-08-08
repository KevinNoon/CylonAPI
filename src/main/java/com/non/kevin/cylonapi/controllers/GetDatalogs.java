package com.non.kevin.cylonapi.controllers;

import com.non.kevin.cylonapi.exceptionHandling.ControllerErrorResponse;
import com.non.kevin.cylonapi.exceptionHandling.ControllerOffLine;
import com.non.kevin.cylonapi.models.Datalog;
import com.non.kevin.cylonapi.models.DatalogInfo;
import com.non.kevin.cylonapi.tools.MathFunctions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static com.non.kevin.cylonapi.constants.CylonCommands.*;
import static com.non.kevin.cylonapi.tools.Checksums.calCheckSum;
import static com.non.kevin.cylonapi.tools.CommonMethods.rawPacket;

public class GetDatalogs {


    public static ArrayList<Datalog> getLogs(String hostIP, byte netAddress, byte subNetAddress, byte datalogNo) {
        //Header

        byte siteNoByte1 = 0; //Set to 0 for direct connect
        byte siteNoByte2 = 0; //Set to 0 for direct connect

//        char packetSize = 0;
//        char dataLength = 1;
//        char checksum = 0;
//        char blockLength =1;
//        char[] packet = {FROM_CONTROLLER, siteNoByte1, siteNoByte2, netAddress, packetSize, subNetAddress,
//                GET_COMMAND, dataLength, GET_DATALOG_INFO[0], GET_DATALOG_INFO[1], blockLength,datalogNo, checksum};

        DatalogInfo datalogInfo = getDatalogInfo(siteNoByte1,siteNoByte2, netAddress, subNetAddress, datalogNo,hostIP,4950);

        if (datalogInfo == null) return null;
        ArrayList<Datalog> dataLogs = getDataLogs(datalogInfo,siteNoByte1, siteNoByte2, netAddress, subNetAddress, datalogNo,hostIP,4950);
        return dataLogs;
    }

    private static DatalogInfo getDatalogInfo(byte siteNoByte1, byte siteNoByte2, byte netAddress, byte subNetAddress, byte datalogNo,String hostIP,int hostPort){
        byte packetSize = 0;
        byte dataLength = 1;
        byte blockLength = 1;
        byte checksum = 0;
        DatalogInfo datalogInfo = new DatalogInfo();
        byte[] packet = {FROM_CONTROLLER, siteNoByte1, siteNoByte2, netAddress, packetSize, subNetAddress,
                GET_COMMAND_UC32, dataLength, GET_DATALOG_INFO[0], GET_DATALOG_INFO[1], blockLength,datalogNo, checksum};
        byte[] rawData = rawPacket(packet, hostIP, hostPort);
        if (rawData.length < 28) return datalogInfo;
        datalogInfo.setLogNo(rawData[11] & 0xFF);
        datalogInfo.setFormat(rawData[12] & 0xFF);
        datalogInfo.setLogType(rawData[13] & 0xFF);
        datalogInfo.setPointType(rawData[14] & 0xFF);
        datalogInfo.setLogPoint((rawData[15] & 0xFF) * 256 + (rawData[16] & 0xFF));
        datalogInfo.setUpdateInterval(MathFunctions.get4ByteInt(rawData[19],rawData[20],rawData[21],rawData[22]));
        datalogInfo.setDate(MathFunctions.getTime(rawData[23],rawData[24],rawData[25],rawData[26]));
        datalogInfo.setPrecision(rawData[27]);
        datalogInfo.setFullFlag(rawData[28] == 1);
        if (datalogInfo.isFullFlag()){
            datalogInfo.setEntryCounterHi((rawData[17]));
            datalogInfo.setEntryCounterLo((rawData[18]));
        } else {
            datalogInfo.setEntryCounterHi((byte) 0);
            datalogInfo.setEntryCounterLo((byte) 0);
        }
        datalogInfo.setBlockNo(MathFunctions.get2ByteInt(rawData[29],rawData[30]));
        datalogInfo.setLength(MathFunctions.get2ByteInt(rawData[31],rawData[32]));
        datalogInfo.setTimeStampFlag(rawData[33] == 1);
        return datalogInfo;
    }

    private static ArrayList<Datalog> getDataLogs(DatalogInfo datalogInfo,byte siteNoByte1, byte siteNoByte2, byte netAddress, byte subNetAddress, int datalogNo,String hostIP,int hostPort){
        ArrayList<Datalog> dataLogs = new ArrayList<>();
        byte packetSize = 0;
        byte dataLength = 1;
        byte blockLength = 1;
        byte checksum = 0;


        int size = datalogInfo.getLength();
        int dataSize = 26;
        int entryKey = ((datalogInfo.getEntryCounterHi() & 0xFF) * 256) + (datalogInfo.getEntryCounterLo() & 0xFF);
        int total = 0;
        byte entryHi = (byte) datalogInfo.getEntryCounterHi();
        byte entryLo = (byte) datalogInfo.getEntryCounterLo();
        while (total < size) {
            byte[] packet = {FROM_CONTROLLER, siteNoByte1, siteNoByte2, netAddress, packetSize, subNetAddress,
                    GET_COMMAND_UC32, dataLength, GET_DATALOG_DATA[0], GET_DATALOG_DATA[1], DATA_LOG_OFFSET[0], (byte) (DATA_LOG_OFFSET[1] + (byte) datalogNo),
                    entryHi, entryLo, checksum};
            byte[] rawPacketData = rawPacket(packet, hostIP, hostPort);

            int pointer = 17;
            while (pointer + 8 < rawPacketData.length) {
                Datalog datalog = new Datalog();
                datalog.setLogValue(MathFunctions.getValue(rawPacketData[pointer], rawPacketData[pointer + 1]
                        , rawPacketData[pointer + 2], rawPacketData[pointer + 3]));

                datalog.setLogTime(MathFunctions.getTime(rawPacketData[pointer + 4], rawPacketData[pointer + 5]
                        , rawPacketData[pointer + 6], rawPacketData[pointer + 7]));
                dataLogs.add(datalog);
                pointer = pointer + 8;
            }
            entryKey = entryKey + dataSize;
            if (entryKey > size){
                entryKey -= size;
            }
            entryHi = (byte) ((entryKey >> 8) & 0xFF );
            entryLo = (byte) ((entryKey) & 0xFF);
            total += dataSize;
        }

//        packet[12] = 0;
//        packet[13] = 26;
//        rawPacketData = rawPacket(packet, hostIP, hostPort);
//         pointer = 17;
//        while (pointer + 8 < rawPacketData.length){
//            Datalog datalog = new Datalog();
//            datalog.setLogValue(MathFunctions.getValue(rawPacketData[pointer],rawPacketData[pointer + 1]
//                    ,rawPacketData[pointer + 2]  ,rawPacketData[pointer + 3] ));
//
//            datalog.setLogTime(MathFunctions.getTime(rawPacketData[pointer + 4],rawPacketData[pointer + 5]
//                    ,rawPacketData[pointer + 6],rawPacketData[pointer + 7]));
//            dataLogs.add(datalog);
//            pointer = pointer + 8;
//        }
//        System.out.println(rawPacketData[13]);
//        System.out.println(rawPacketData[14]);

        return dataLogs;
    }


}
