package com.ecotech.invoicelinkgen.exceptions;


public class EcotechApiException extends RuntimeException {

    EcotechApiException(String message){
        super(message);
    }

    EcotechApiException(String message, Throwable cause){
        super(message, cause);
        if(this.getCause() == null && cause != null){
            this.initCause(cause);
        }
    }
}
