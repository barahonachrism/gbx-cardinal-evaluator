package com.gbx.cardinalevaluator.domain.usescases;

import com.gbx.cardinalevaluator.domain.entities.CardinalNumber;
import com.gbx.cardinalevaluator.domain.exception.PhraseParseException;
import com.gbx.cardinalevaluator.domain.ports.services.SyntaxService;
import org.springframework.stereotype.Service;

@Service
public class CardinalNumberSyntaxService implements SyntaxService {
    private static final String SYNTAX_INVALID_MESSAGE = "The syntax of number not is valid";
    @Override
    public void validateSyntaxNumber(CardinalNumber previosCardinalNumber, CardinalNumber cardinalNumber) {
        int currentExponential = cardinalNumber.getDecimalExponential().intValue();
        int previousExponential = previosCardinalNumber.getDecimalExponential().intValue();
        //if the number is between 1 and 999, validate syntax
        if(currentExponential < 3 &&
                previousExponential < 3 &&
                previousExponential>= currentExponential){
            throw new PhraseParseException(SYNTAX_INVALID_MESSAGE);
        }
        //if the number is part of a plural number, validate that current number not has more digits that previous number
        if(previosCardinalNumber.isPlural()
                && currentExponential >= previousExponential){
            throw new PhraseParseException(SYNTAX_INVALID_MESSAGE);
        }
        //La centena se expresa como «cien» si va sola o acompañada de un multiplo de mil
        if(cardinalNumber.getSingularCardinalName().equals("cien")  && previousExponential < 3){
            throw new PhraseParseException(SYNTAX_INVALID_MESSAGE);
        }
        //La centena se expresa como «ciento» si va acompañada de decenas o unidades
        if(cardinalNumber.getSingularCardinalName().equals("ciento")  && previousExponential > 1){
            throw new PhraseParseException(SYNTAX_INVALID_MESSAGE);
        }
    }

    public void validateSyntax(CardinalNumber cardinalNumber, int currentExponential) {
        if(cardinalNumber != null && cardinalNumber.isPlural() && currentExponential > 3){
            throw new PhraseParseException(SYNTAX_INVALID_MESSAGE);
        }
    }

    @Override
    public void validateTenNumber(CardinalNumber cardinalTenNumber, CardinalNumber cardinalUnitNumber) {
        if(!(cardinalTenNumber.getDecimalExponential().intValue()==1 && cardinalUnitNumber.getDecimalExponential().intValue()==0)){
            throw new PhraseParseException(SYNTAX_INVALID_MESSAGE);
        }
    }
}
