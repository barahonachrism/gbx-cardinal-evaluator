package com.globantx.cardinalevaluator.domain.usescases;

import com.globantx.cardinalevaluator.domain.entities.CardinalNumber;
import com.globantx.cardinalevaluator.domain.exception.PhraseParseException;
import com.globantx.cardinalevaluator.domain.ports.services.SyntaxService;
import org.springframework.stereotype.Service;

@Service
public class CardinalNumberSyntaxService implements SyntaxService {
    @Override
    public void validateSyntaxNumber(CardinalNumber previosCardinalNumber, CardinalNumber cardinalNumber) {
        int currentExponential = cardinalNumber.getDecimalExponential().intValue();
        int previousExponential = previosCardinalNumber.getDecimalExponential().intValue();
        //if the number is between 1 and 999, validate syntax
        if(currentExponential < 3 &&
                previousExponential < 3 &&
                previousExponential>= currentExponential){
            throw new PhraseParseException("The syntax of number not is valid");
        }
        //if the number is part of a plural number, validate that current number not has more digits that previous number
        if(previosCardinalNumber.isPlural()
                && currentExponential >= previousExponential){
            throw new PhraseParseException("The syntax of number not is valid");
        }
        //La centena se expresa como «cien» si va sola o acompañada de un multiplo de mil
        if(cardinalNumber.getSingularCardinalName().equals("cien")  && previousExponential < 3){
            throw new PhraseParseException("The syntax of number not is valid");
        }
        //La centena se expresa como «ciento» si va acompañada de decenas o unidades
        if(cardinalNumber.getSingularCardinalName().equals("ciento")  && previousExponential > 1){
            throw new PhraseParseException("The syntax of number not is valid");
        }
    }

    public void validateSyntax(CardinalNumber cardinalNumber, int currentExponential) {
        if(cardinalNumber != null && cardinalNumber.isPlural() && currentExponential > 3){
            throw new PhraseParseException("The syntax number not is valid");
        }
    }
}
