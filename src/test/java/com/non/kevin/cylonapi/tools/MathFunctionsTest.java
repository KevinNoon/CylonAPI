package com.non.kevin.cylonapi.tools;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.non.kevin.cylonapi.tools.MathFunctions.getTime;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class MathFunctionsTest {
    @Test
    public void TimeAt2022_06_17_1145(){

 //       assertEquals(Timestamp.valueOf("2022-06-17 10:45:00"),getTime(64,209,118,44));
        assertEquals( LocalDateTime.of(2022,06,17,10,45,00) ,getTime((byte) 64,(byte) 209,(byte) 118,(byte) 44));
    }
    @Test
    public void byteToFloat20P1(){
        System.out.println(MathFunctions.getValue(65,160,204,205));
    }
    @Test
    public void Float20P1toByte(){
        byte[] b = MathFunctions.setAnalogueValue(20.1f);
        System.out.println(b[0]+ " " + (b[1] & 0xFF));
    }
}
