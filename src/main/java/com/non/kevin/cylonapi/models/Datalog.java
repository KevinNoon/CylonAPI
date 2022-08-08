package com.non.kevin.cylonapi.models;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class Datalog {
    LocalDateTime logTime;
    Float logValue;
}
