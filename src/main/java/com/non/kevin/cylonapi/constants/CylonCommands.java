package com.non.kevin.cylonapi.constants;

public class CylonCommands {
    public static byte FROM_CONTROLLER = 'P';
    public static byte GET_COMMAND_UC32 = 'g';
    public static byte CHANGE_COMMAND_UC32 = 'c';
    public static byte GET_COMMAND_UC16 = 'G';
    public static byte CHANGE_COMMAND_UC16 = 'C';
    public static byte[] GET_DATALOG_INFO = {32,4};
    public static byte[] GET_DATALOG_DATA = {32,55};
    public static byte[] GET_POINT = {32,5};
    public static byte[] DATA_LOG_OFFSET = {3,127};
    public static byte UC16ANALOGUE = 1;
    public static byte UC16DIGITAL = 2;
    public static byte UC16POINT_VALUE = (byte) 204;

}

