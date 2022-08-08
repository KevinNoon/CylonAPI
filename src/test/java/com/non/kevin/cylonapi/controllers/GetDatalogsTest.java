package com.non.kevin.cylonapi.controllers;

import org.junit.jupiter.api.Test;

import static com.non.kevin.cylonapi.controllers.GetPoints.*;
import static com.non.kevin.cylonapi.controllers.SetPoints.*;

public class GetDatalogsTest {
    @Test
    public void GetDatalogsTest(){
        System.out.println(GetDatalogs.getLogs("192.168.16.211",(byte) 1,(byte) 1,(byte) 2));
    }

    @Test
    public void getUC32HardwarePointTest(){
        System.out.println(getUC32HardwarePoint("192.168.16.211",4950,(byte) 1,(byte) 1, 8));
    }
    @Test
    public void getUC32VirtualPointTest(){
        System.out.println(getUC32VirtualAnaloguePoint("192.168.16.211",4950,(byte) 1,(byte) 1, 1));
        System.out.println(getUC32VirtualDigitalPoint("192.168.16.211",4950,(byte) 1,(byte) 1, 1));
    }
    @Test
    public void getUC16AnaloguePointTest(){
        System.out.println(getUC16AnaloguePoint("192.168.16.211",4950,(byte) 1,(byte) 3, 17));
        System.out.println(getUC16DigitalPoint("192.168.16.211",4950,(byte) 1,(byte) 3, 17));
    }
    @Test
    public void setUC32VirtualPointTest(){
        System.out.println(setUC32VirtualAnaloguePoint("192.168.16.211",4950,(byte) 1,(byte) 1, 1,
                17.4f));
        System.out.println(setUC32VirtualDigitalPoint("192.168.16.211",4950,(byte) 1,(byte) 1, 1,
                0));
    }
    @Test
    public void setUC16VirtualPointTest(){
        System.out.println(setUC16VirtualPointA("192.168.16.211",4950,(byte) 1,(byte) 3, 200,
                12.3f));
        System.out.println(setUC16VirtualPointD("192.168.16.211",4950,(byte) 1,(byte) 3, 200,
                0f));
    }
}








