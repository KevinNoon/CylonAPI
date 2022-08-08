package com.non.kevin.cylonapi.tools;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.LocalDateTime;

public class MathFunctions {
//    public static Timestamp getTime(int a, int b, int c, int d) {
//        a = a & 0xFF;
//        b = b & 0xFF;
//        c = c & 0xFF;
//        d = d & 0xFF;
//        int timeOffset = ((((a * 256 ) + b) * 256) + c) * 256 + d;
//        if (timeOffset == 0) return null;
//        Timestamp original = Timestamp.valueOf("1988-1-1 00:00:00");
//        return new Timestamp(original.getTime() + (timeOffset * 1000L));
//    }

        public static LocalDateTime getTime(byte a, byte b, byte c, byte d) {
            int timeOffset = (((((a & 0xFF) * 256) + (b & 0xFF)) * 256) + (c & 0xFF)) * 256 + (d & 0xFF);
            if (timeOffset == 0) return null;
            LocalDateTime original = LocalDateTime.of(1988,1,1,0,0,0);
            return  original.plusSeconds(timeOffset);
        }

    public static Float getValue(int a, int b, int c, int d) {
        byte[] array = {(byte) a, (byte) b, (byte) c, (byte) d};
        if ((a & 0xFF) == 255) return 0.0F;
        return ByteBuffer.wrap(array).order(ByteOrder.BIG_ENDIAN).getFloat();
    }

    public static Float getValue(byte a, byte b, byte c, byte d) {
        byte[] array = {a, b, c, d};
        if ((a & 0xFF) == 255) return 0.0F;
        return ByteBuffer.wrap(array).order(ByteOrder.BIG_ENDIAN).getFloat();
    }

    public static byte[] setAnalogueValue(Float value){
        int intBits =  Float.floatToIntBits(value);
        return new byte[] {
                (byte) (intBits >> 24), (byte) (intBits >> 16), (byte) (intBits >> 8), (byte) (intBits) };
    }

    public static byte[] setDigitalValue(Float value){
        byte state;
        if (value == 0) {state = 0;} else {state = 1;}
        return new byte[] {
                state, (byte) (0), (byte) (0), (byte) (0) };
    }

    public static int get2ByteInt(Byte a, Byte b) {
        int w = a & 0xFF;
        int x = b & 0xFF;
        return (w * 256 + x);
    }

    public static  int get4ByteInt(Byte a,Byte b, Byte c, Byte d){
        int w = a & 0xFF;
        int x = b & 0xFF;
        int y = c & 0xFF;
        int z = d & 0xFF;
        return (((((w * 256) + x) * 256) + y) * 256) + z;
    }

    public static byte[] intTo2Byte(int no){
        return new byte[]{(byte) (no >> 8 & 0xFF), (byte) (no & 0xFF)};
    }
}
