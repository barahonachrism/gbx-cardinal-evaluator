package com.globantx.cardinalevaluator;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;


@Builder
@Getter
public class CardinalNumber {
    private String singularCardinalName;
    private OrderCardinalNumberEnum order;
    private boolean plural;
    private String numericValueText;
    private BigDecimal decimalExponential;
    private BigDecimal numericValue;

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
