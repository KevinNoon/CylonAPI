package com.non.kevin.cylonapi.ChecksumsTest;

import com.non.kevin.cylonapi.tools.Checksums;
import org.junit.jupiter.api.Test;


public class checksum {
    @Test
    public void crc() {
        byte[] bytes = {(byte) 1,(byte) 99,(byte)1, (byte) 128,(byte) 1,(byte) 8,
                (byte)65, (byte) 140, (byte)20, (byte)123,
                (byte) 0, (byte) 0,(byte) 0,(byte) 0};
        System.out.println(Checksums.crc16(bytes));
    }
}
