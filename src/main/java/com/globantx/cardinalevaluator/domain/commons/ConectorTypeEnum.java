package com.globantx.cardinalevaluator.domain.commons;

public enum ConectorTypeEnum {
    AND("Y");
    ConectorTypeEnum(String value){
        this.value = value;
    }
    private String value;

    public String getValue() {
        return value;
    }
}
