package com.globantx.cardinalevaluator.domain.entities;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;


@Getter
public class CardinalNumber {
    private String singularCardinalName;
    private boolean plural;
    private String numericValueText;
    private BigDecimal decimalExponential;
    private BigDecimal numericValue;
    @Builder
    public CardinalNumber(String singularCardinalName, boolean plural, String numericValueText){
        this.singularCardinalName = singularCardinalName;
        this.plural = plural;
        this.numericValueText = numericValueText;
    }

    public BigDecimal getDecimalExponential(){
        if(decimalExponential == null){
            decimalExponential = new BigDecimal(numericValueText.length() - 1);
        }
        return decimalExponential;
    }

    public BigDecimal getNumericValue(){
        if(numericValue ==  null){
            numericValue = new BigDecimal(numericValueText);
        }
        return numericValue;
    }
}
