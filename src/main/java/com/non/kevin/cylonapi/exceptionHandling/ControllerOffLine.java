package com.non.kevin.cylonapi.exceptionHandling;

import lombok.Data;


public class ControllerOffLine extends RuntimeException{

    public ControllerOffLine(String message) {
        super(message);
    }

    public ControllerOffLine(String message, Throwable cause) {
        super(message, cause);
    }

    public ControllerOffLine(Throwable cause) {
        super(cause);
    }
}
