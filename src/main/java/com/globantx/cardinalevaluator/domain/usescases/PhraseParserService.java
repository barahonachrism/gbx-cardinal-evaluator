package com.globantx.cardinalevaluator.domain.usescases;

import com.globantx.cardinalevaluator.domain.entities.Token;
import com.globantx.cardinalevaluator.domain.commons.TokenTypeEnum;
import com.globantx.cardinalevaluator.domain.entities.CardinalNumber;
import com.globantx.cardinalevaluator.domain.ports.repository.CardinalNumbersCatalogRepository;
import com.globantx.cardinalevaluator.domain.ports.services.ParserService;
import com.globantx.cardinalevaluator.domain.ports.services.SyntaxService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PhraseParserService implements ParserService {
    private CardinalNumbersCatalogRepository cardinalNumbersCatalogRepository;
    private String oneThousandNumber = "mil";
    private SyntaxService syntaxService;
    public PhraseParserService(CardinalNumbersCatalogRepository cardinalNumbersCatalogRepository, SyntaxService syntaxService){
        this.cardinalNumbersCatalogRepository = cardinalNumbersCatalogRepository;
        this.syntaxService = syntaxService;
    }
    @Override
    public String parsePhrase(String phrase) {
        StringBuilder evaluatedPhrase = new StringBuilder();
        PhraseLexerService phraseLexerService = new PhraseLexerService(phrase,cardinalNumbersCatalogRepository);

        while (phraseLexerService.hasMoreTokens()){
            Token token = phraseLexerService.readToken();
            List<Token> numericTokens = new ArrayList<>();

            if(token != null && (token.getType().equals(TokenTypeEnum.WORD) || token.getType().equals(TokenTypeEnum.CONNECTOR))){
                appendText(evaluatedPhrase,token.getValue());
            }else while(token != null && token.getType().equals(TokenTypeEnum.NUMBER)){
                numericTokens.add(token);
                token = phraseLexerService.readToken();
                if(token!=null && token.getType().equals(TokenTypeEnum.CONNECTOR)){
                    token = phraseLexerService.readToken();
                }
            }
            if(numericTokens.size()>0) {
                String numberValue = evaluateNumber(numericTokens);
                appendText(evaluatedPhrase, numberValue);
                if(token !=null){
                    appendText(evaluatedPhrase, token.getValue());
                }
            }
        }
        return evaluatedPhrase.toString();
    }

    private StringBuilder appendText(StringBuilder phrase, String tokenValue){
        return phrase.length()>0?phrase.append(" ").append(tokenValue):phrase.append(tokenValue);
    }

    private String evaluateNumber( List<Token> numericTokens) {
        BigDecimal numericValue = BigDecimal.ZERO;
        int sizeNumericTokens = numericTokens.size();
        CardinalNumber previosCardinalNumber = null;
        BigDecimal decimalExponential = BigDecimal.ZERO;

        CardinalNumber cardinalNumber = null;
        int currentExponential = 0;
        //read the number from left to right
        for(int i = sizeNumericTokens -1; i >= 0;i--){
            cardinalNumber = cardinalNumbersCatalogRepository.getCardinalNumber(numericTokens.get(i).getValue());
            currentExponential = cardinalNumber.getDecimalExponential().intValue();
            if(currentExponential> 0 && currentExponential % 6 == 0){
                decimalExponential = cardinalNumber.getDecimalExponential();
            }
            else if(currentExponential>0 && currentExponential % 3 == 0){
                decimalExponential = decimalExponential.add(cardinalNumber.getDecimalExponential());
            }
            if(previosCardinalNumber  != null ){
                syntaxService.validateSyntaxNumber(previosCardinalNumber, cardinalNumber);
            }
            if(decimalExponential.intValue() >= 3){
                if(!cardinalNumber.isPlural()) {
                    if (currentExponential <=3 ||  decimalExponential.intValue() % 6 != 0) {
                        BigDecimal value;
                        boolean addNumber = true;
                        if (cardinalNumber.getSingularCardinalName().equals(oneThousandNumber)) {
                            value = BigDecimal.ONE;
                            if (i > 0) {
                                CardinalNumber nextCardinalNumber = cardinalNumbersCatalogRepository.getCardinalNumber(numericTokens.get(i - 1).getValue());
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
        syntaxService.validateSyntax(cardinalNumber, currentExponential);
        return numericValue.toString();
    }

}
