package com.non.kevin.cylonapi.models;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class DatalogInfo {
    int logNo;
    int format;
    int logType;
    int pointType;
    int logPoint;
    byte entryCounterHi;
    byte entryCounterLo;
    int updateInterval;
    LocalDateTime date;
    int precision;
    boolean fullFlag;
    int blockNo;
    int length;
    boolean timeStampFlag;
}
