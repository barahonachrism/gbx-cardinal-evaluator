package com.globantx.cardinalevaluator.domain.exception;

public class CsvParseException extends RuntimeException{
    public CsvParseException(String message,Throwable e){
        super(message,e);
    }

}
