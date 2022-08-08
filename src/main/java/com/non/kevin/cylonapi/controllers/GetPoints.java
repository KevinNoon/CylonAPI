package com.non.kevin.cylonapi.controllers;

import com.non.kevin.cylonapi.tools.MathFunctions;

import static com.non.kevin.cylonapi.constants.CylonCommands.*;
import static com.non.kevin.cylonapi.constants.CylonCommands.GET_POINT;
import static com.non.kevin.cylonapi.tools.CommonMethods.rawPacket;

public class GetPoints {
    //Get Points header
    static byte siteNoByte1 = 0; //Set to 0 for direct connect
    static byte siteNoByte2 = 0; //Set to 0 for direct connect
    static byte packetSize = 0;
    static byte dataLength = 1;
    static byte blockLength = 2;
    static byte checksum = 0;
    static byte redundantByte = 0;

    public static Float getUC32HardwarePoint( String hostIP, int hostPort,byte netAddress, byte subNetAddress, int pointNo){
        int pointOffset = 0x9BFF; //0xC8FF Offset for UC32 hardware points Point 1 = 0xC900
        byte[] pointCharNo = MathFunctions.intTo2Byte(pointOffset + pointNo);
        byte[] packet = {FROM_CONTROLLER, siteNoByte1, siteNoByte2, netAddress, packetSize, subNetAddress,
                GET_COMMAND_UC32, dataLength, GET_POINT[0], GET_POINT[1], blockLength,pointCharNo[0],pointCharNo[1], checksum};
        byte[] rawData = rawPacket(packet, hostIP, hostPort);
        if (rawData.length < 18) return null;
        return MathFunctions.getValue(rawData[15],rawData[16],rawData[17],rawData[18]);
    }

    public static Float getUC32VirtualAnaloguePoint(String hostIP, int hostPort,byte netAddress, byte subNetAddress, int pointNo){
        int pointOffset =  0x8000;
        byte[] rawData = getUC32VirtualPoint(pointOffset,hostIP,hostPort,netAddress,subNetAddress,pointNo);
        if (rawData.length < 18) {return null;}
        return MathFunctions.getValue(rawData[11], rawData[12], rawData[13], rawData[14]);

    }
     public static Float getUC32VirtualDigitalPoint(String hostIP, int hostPort,byte netAddress, byte subNetAddress, int pointNo) {
        int pointOffset = 0xA000;
         byte[] rawData = getUC32VirtualPoint(pointOffset, hostIP, hostPort, netAddress, subNetAddress, pointNo);
         if (rawData.length < 14) {
            return null;
        }
        int intValue = rawData[11] & 0xFF;
        return (float) intValue;
    }
    private static byte[] getUC32VirtualPoint(int  pointOffset,String hostIP, int hostPort,byte netAddress, byte subNetAddress, int pointNo){
        byte[] pointCharNo = MathFunctions.intTo2Byte(pointOffset + pointNo);
        byte[] packet = {FROM_CONTROLLER, siteNoByte1, siteNoByte2, netAddress, packetSize, subNetAddress,
                GET_COMMAND_UC32, dataLength,pointCharNo[0],pointCharNo[1],redundantByte, checksum};
        return rawPacket(packet, hostIP, hostPort);
    }
    public static Float getUC16AnaloguePoint(String hostIP, int hostPort,byte netAddress, byte subNetAddress, int pointNo){
        int pointOffset = 0xCC00; //0xCC00 Offset for UC16 points Point 1 = 0xCC01
        byte[] rawData = getUC16Point(UC16ANALOGUE,pointOffset,hostIP,hostPort,netAddress,subNetAddress,pointNo);

        if (rawData.length < 18) return null;
        return MathFunctions.getValue(rawData[12],rawData[13],rawData[14],rawData[15]);
    }
    public static Float getUC16DigitalPoint(String hostIP, int hostPort,byte netAddress, byte subNetAddress, int pointNo){
        int pointOffset = 0xCC00; //0xCC00 Offset for UC16 points Point 1 = 0xCC01
        byte[] rawData = getUC16Point(UC16DIGITAL,pointOffset,hostIP,hostPort,netAddress,subNetAddress,pointNo);
        if (rawData.length < 18) return null;
//        int intValue = rawData[15] & 0xFF;
//        return (float) intValue;
        return MathFunctions.getValue(rawData[12],rawData[13],rawData[14],rawData[15]);
    }
    private static byte[] getUC16Point(byte pointType,int pointOffset,String hostIP, int hostPort,byte netAddress, byte subNetAddress, int pointNo){
        byte[] pointCharNo = MathFunctions.intTo2Byte(pointOffset + pointNo);
        byte[] packet = {FROM_CONTROLLER, siteNoByte1, siteNoByte2, netAddress, packetSize, subNetAddress,
                GET_COMMAND_UC16, pointCharNo[0],pointCharNo[1],pointType, checksum};
        return rawPacket(packet, hostIP, hostPort);
    }
}
