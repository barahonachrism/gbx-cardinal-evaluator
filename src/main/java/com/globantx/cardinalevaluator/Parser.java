package com.globantx.cardinalevaluator;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    public String parsePhrase(String phrase) throws URISyntaxException, IOException {
        Lexer lexer = new Lexer(phrase);
        Token token = lexer.readToken();
        BigDecimal numericValue = BigDecimal.ZERO;
        List<Token> numericTokens = new ArrayList<>();
        while(token != null && token.getType().equals(TokenType.NUMBER)){
            numericTokens.add(token);
            token = lexer.readToken();
            if(token!=null && token.getType().equals(TokenType.CONNECTOR)){
                token = lexer.readToken();
            }
        }
        int sizeNumericTokens = numericTokens.size();
        CardinalNumber previosCardinalNumber = null;
        BigDecimal decimalExponential = BigDecimal.ZERO;

        CardinalNumber cardinalNumber = null;
        int currentExponential = 0;
        //read the number from left to right
        for(int i = sizeNumericTokens -1; i >= 0;i--){
            cardinalNumber = CardinalNumbersCatalog.getCardinalNumber(numericTokens.get(i).getValue());
            currentExponential = cardinalNumber.getDecimalExponential().intValue();
            if(currentExponential> 0 && currentExponential % 6 == 0){
                decimalExponential = cardinalNumber.getDecimalExponential();
            }
            else if(currentExponential>0 && currentExponential % 3 == 0){
                decimalExponential = decimalExponential.add(cardinalNumber.getDecimalExponential());
            }
            if(previosCardinalNumber  != null ){
                validateSyntaxNumber(previosCardinalNumber, cardinalNumber);
            }
            if(decimalExponential.intValue() >= 3){
                if(!cardinalNumber.isPlural()) {
                    if (currentExponential <=3 ||  decimalExponential.intValue() % 6 != 0) {

                        BigDecimal value;
                        boolean addNumber = true;
                        if (cardinalNumber.getSingularCardinalName().equals("mil")) {
                            value = BigDecimal.ONE;
                            if (i > 0) {
                                CardinalNumber nextCardinalNumber = CardinalNumbersCatalog.getCardinalNumber(numericTokens.get(i - 1).getValue());
                                if (nextCardinalNumber.getDecimalExponential().intValue() <= 2) {
                                    addNumber = false;
                                }
                            }
                        } else {
                            value = cardinalNumber.getNumericValue();
                        }
                        BigDecimal multiplier = value.multiply(BigDecimal.TEN.pow(decimalExponential.intValue()));

                        if (addNumber) {
                            numericValue = numericValue.add(multiplier);
                        }
                    }
                }
            }
            else {
                numericValue = numericValue.add(cardinalNumber.getNumericValue());
            }
            previosCardinalNumber = cardinalNumber;
        }
        if(cardinalNumber != null && cardinalNumber.isPlural() && currentExponential > 3){
            throw new ParseException("The syntax number not is valid");
        }
        return numericValue.toString();
    }

    private void validateSyntaxNumber(CardinalNumber previosCardinalNumber, CardinalNumber cardinalNumber) {
        int currentExponential = cardinalNumber.getDecimalExponential().intValue();
        int previousExponential = previosCardinalNumber.getDecimalExponential().intValue();
        //if the number is between 1 and 999, validate syntax
        if(currentExponential < 3 &&
                previousExponential < 3 &&
                previousExponential>= currentExponential){
            throw new ParseException("The syntax of number not is valid");
        }
        //if the number is part of a plural number, validate that current number not has more digits that previous number
        if(previosCardinalNumber.isPlural()
                && currentExponential >= previousExponential){
            throw new ParseException("The syntax of number not is valid");
        }
        //La centena se expresa como «cien» si va sola o acompañada de un multiplo de mil
        if(cardinalNumber.getSingularCardinalName().equals("cien")  && previousExponential < 3){
            throw new ParseException("The syntax of number not is valid");
        }
        //La centena se expresa como «ciento» si va acompañada de decenas o unidades
        if(cardinalNumber.getSingularCardinalName().equals("ciento")  && previousExponential > 1){
            throw new ParseException("The syntax of number not is valid");
        }
    }
}
