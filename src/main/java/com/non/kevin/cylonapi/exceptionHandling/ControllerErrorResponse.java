package com.non.kevin.cylonapi.exceptionHandling;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ControllerErrorResponse {

        private int status;
        private String message;
        private long timeStamp;
}
