package com.non.kevin.cylonapi.controllers;

import com.non.kevin.cylonapi.tools.Checksums;
import com.non.kevin.cylonapi.tools.MathFunctions;

import static com.non.kevin.cylonapi.constants.CylonCommands.*;
import static com.non.kevin.cylonapi.tools.Checksums.calCheckSum;
import static com.non.kevin.cylonapi.tools.CommonMethods.rawPacket;

public class SetPoints {
    //Get Points header
    static byte siteNoByte1 = 0; //Set to 0 for direct connect
    static byte siteNoByte2 = 0; //Set to 0 for direct connect
    static byte packetSize = 0;
    static byte dataLength = 1;
    static byte blockLength = 1;
    static byte checksum = 0;
    static byte redundantByte = 0;

    public static Float setUC32VirtualAnaloguePoint(String hostIP, int hostPort,byte netAddress,
                                                    byte subNetAddress, int pointNo,float value){
        int pointOffset =  0x8000;
        byte UC32AnalogueSetLength = 8;
        byte[] valueBytes = MathFunctions.setAnalogueValue(value);
        byte[] pointCharNo = MathFunctions.intTo2Byte(pointOffset + pointNo);
        byte[] crc16byte =  {subNetAddress,
                CHANGE_COMMAND_UC32, dataLength, pointCharNo[0], pointCharNo[1], UC32AnalogueSetLength,
                valueBytes[0],valueBytes[1],valueBytes[2],valueBytes[3],
                0,0,0,0};
        byte[] crc = Checksums.crc16Bytes(crc16byte);
        byte[] packet = {FROM_CONTROLLER, siteNoByte1, siteNoByte2, netAddress, packetSize, subNetAddress,
                CHANGE_COMMAND_UC32, dataLength,pointCharNo[0],pointCharNo[1],UC32AnalogueSetLength,
                (byte) (valueBytes[0] & 0xFF),(byte) (valueBytes[1] & 0xFF),(byte) (valueBytes[2] & 0xFF),(byte) (valueBytes[3] & 0xFF),
                (byte) 0,(byte) 0,(byte) 0,(byte) 0, (byte) (crc[1] & 0xFF), (byte) (crc[0] & 0xFF),checksum};
        byte[] rawData = rawPacket(packet, hostIP, hostPort);
        if (rawData.length != 8) {return null;}
        return GetPoints.getUC32VirtualAnaloguePoint(hostIP,hostPort,netAddress,subNetAddress,pointNo);
    }

    public static Float setUC32VirtualDigitalPoint(String hostIP, int hostPort,byte netAddress,
                                                   byte subNetAddress, int pointNo,float value) {
        int pointOffset = 0xA000;
        byte UC32DigitalSetLength = 4;
        byte[] valueBytes = MathFunctions.setDigitalValue(value);
        byte[] pointCharNo = MathFunctions.intTo2Byte(pointOffset + pointNo);
        byte[] crc16byte =  {subNetAddress,
                CHANGE_COMMAND_UC32, dataLength, pointCharNo[0], pointCharNo[1], UC32DigitalSetLength,
                valueBytes[0],valueBytes[1],valueBytes[2],valueBytes[3]};
        byte[] crc = Checksums.crc16Bytes(crc16byte);
        byte[] packet = {FROM_CONTROLLER, siteNoByte1, siteNoByte2, netAddress, packetSize, subNetAddress,
                CHANGE_COMMAND_UC32, dataLength,pointCharNo[0],pointCharNo[1],UC32DigitalSetLength,
                (byte) (valueBytes[0] & 0xFF),(byte) (valueBytes[1] & 0xFF),(byte) (valueBytes[2] & 0xFF),(byte) (valueBytes[3] & 0xFF),
                 (byte) (crc[1] & 0xFF), (byte) (crc[0] & 0xFF),checksum};
        byte[] rawData = rawPacket(packet, hostIP, hostPort);
        if (rawData.length != 8) {return null;}
        return GetPoints.getUC32VirtualDigitalPoint(hostIP,hostPort,netAddress,subNetAddress,pointNo);
    }

    public static Float setUC16VirtualPointA(String hostIP, int hostPort, byte netAddress,
                                            byte subNetAddress, int pointNo, float value){
        byte[] rawData = setUC16VirtualPoint(hostIP,hostPort,netAddress,subNetAddress,pointNo,value,UC16ANALOGUE);
//        byte[] valueBytes = MathFunctions.setAnalogueValue(value);
//        byte[] y = {packetSize, subNetAddress,
//        CHANGE_COMMAND_UC16,UC16POINT_VALUE,UC16POINT_VALUE, (byte) pointNo,UC16ANALOGUE,0,
//                (byte) (valueBytes[0] & 0xFF),(byte) (valueBytes[1] & 0xFF),(byte) (valueBytes[2] & 0xFF),(byte) (valueBytes[3] & 0xFF),
//                0,0,0,0,0,0,0,0};
//        byte subnetChecksum =(byte) (calCheckSum(y) & 0xFF);
//        byte[] packet = {FROM_CONTROLLER, siteNoByte1, siteNoByte2, netAddress, packetSize, subNetAddress,
//                CHANGE_COMMAND_UC16,UC16POINT_VALUE,UC16POINT_VALUE, (byte) pointNo,UC16ANALOGUE,0,
//                (byte) (valueBytes[0] & 0xFF),(byte) (valueBytes[1] & 0xFF),(byte) (valueBytes[2] & 0xFF),(byte) (valueBytes[3] & 0xFF),
//                0,0,0,0,0,0,0,subnetChecksum,checksum};
//        byte[] rawData = rawPacket(packet, hostIP, hostPort);
        if (rawData.length < 11) return null;
        return GetPoints.getUC16AnaloguePoint(hostIP,hostPort,netAddress,subNetAddress,pointNo);
    }
    private static byte[] setUC16VirtualPoint(String hostIP, int hostPort, byte netAddress,
                                             byte subNetAddress, int pointNo, float value,byte type){
        byte[] valueBytes = MathFunctions.setAnalogueValue(value);
        byte[] y = {packetSize, subNetAddress,
                CHANGE_COMMAND_UC16,UC16POINT_VALUE,UC16POINT_VALUE, (byte) pointNo,type,0,
                (byte) (valueBytes[0] & 0xFF),(byte) (valueBytes[1] & 0xFF),(byte) (valueBytes[2] & 0xFF),(byte) (valueBytes[3] & 0xFF),
                0,0,0,0,0,0,0,0};
        byte subnetChecksum =(byte) (calCheckSum(y) & 0xFF);
        byte[] packet = {FROM_CONTROLLER, siteNoByte1, siteNoByte2, netAddress, packetSize, subNetAddress,
                CHANGE_COMMAND_UC16,UC16POINT_VALUE,UC16POINT_VALUE, (byte) pointNo,type,0,
                (byte) (valueBytes[0] & 0xFF),(byte) (valueBytes[1] & 0xFF),(byte) (valueBytes[2] & 0xFF),(byte) (valueBytes[3] & 0xFF),
                0,0,0,0,0,0,0,subnetChecksum,checksum};
        return rawPacket(packet, hostIP, hostPort);
    }



    public static Float setUC16VirtualPointD(String hostIP, int hostPort, byte netAddress,
                                            byte subNetAddress, int pointNo, float value){
        byte[] rawData = setUC16VirtualPoint(hostIP,hostPort,netAddress,subNetAddress,pointNo,value,UC16DIGITAL);

//        byte[] valueBytes = MathFunctions.setAnalogueValue(value);
//        byte[] y = {packetSize, subNetAddress,
//                CHANGE_COMMAND_UC16,UC16POINT_VALUE,UC16POINT_VALUE, (byte) pointNo,UC16DIGITAL,0,
//                (byte) (valueBytes[0] & 0xFF),(byte) (valueBytes[1] & 0xFF),(byte) (valueBytes[2] & 0xFF),(byte) (valueBytes[3] & 0xFF),
//                0,0,0,0,0,0,0,0};
//        byte subnetChecksum =(byte) (calCheckSum(y) & 0xFF);
//        byte[] packet = {FROM_CONTROLLER, siteNoByte1, siteNoByte2, netAddress, packetSize, subNetAddress,
//                CHANGE_COMMAND_UC16,UC16POINT_VALUE,UC16POINT_VALUE, (byte) pointNo,UC16DIGITAL,0,
//                (byte) (valueBytes[0] & 0xFF),(byte) (valueBytes[1] & 0xFF),(byte) (valueBytes[2] & 0xFF),(byte) (valueBytes[3] & 0xFF),
//                0,0,0,0,0,0,0,subnetChecksum,checksum};
//        byte[] rawData = rawPacket(packet, hostIP, hostPort);

        if (rawData.length < 11) return null;
        return GetPoints.getUC16DigitalPoint(hostIP,hostPort,netAddress,subNetAddress,pointNo);
    }
}
