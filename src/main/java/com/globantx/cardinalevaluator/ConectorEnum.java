package com.globantx.cardinalevaluator;

public enum ConectorEnum {
    AND("Y");
    ConectorEnum(String value){
        this.value = value;
    }
    public String value;
}
