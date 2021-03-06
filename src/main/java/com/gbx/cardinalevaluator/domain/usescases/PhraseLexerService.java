package com.gbx.cardinalevaluator.domain.usescases;

import com.gbx.cardinalevaluator.domain.entities.Token;
import com.gbx.cardinalevaluator.domain.commons.TokenTypeEnum;
import com.gbx.cardinalevaluator.domain.commons.ConectorTypeEnum;
import com.gbx.cardinalevaluator.domain.ports.repository.CardinalNumbersCatalogRepository;
import com.gbx.cardinalevaluator.domain.ports.services.LexerService;

import java.util.ArrayList;
import java.util.List;

public class PhraseLexerService implements LexerService {
    private int position;

    private String[] tokenParts;

    private CardinalNumbersCatalogRepository cardinalNumbersCatalogRepository;

    public PhraseLexerService(String phrase, CardinalNumbersCatalogRepository cardinalNumbersCatalogRepository) {
        this.tokenParts = phrase.split(" ");
        position = 0;
        this.cardinalNumbersCatalogRepository = cardinalNumbersCatalogRepository;
    }

    public boolean hasMoreTokens(){
        return position < tokenParts.length;
    }
    public List<Token> readAllTokens() {
        List<Token> tokens = new ArrayList<>();
        Token token = readToken();
        while(token!= null){
            tokens.add(token);
            token = readToken();
        }
        return tokens;
    }

    public Token readToken() {
        if(position < tokenParts.length) {
            Token token;
            String value = tokenParts[position];
            if (cardinalNumbersCatalogRepository.hasNumber(value)) {
                token = Token.builder().type(TokenTypeEnum.NUMBER).value(value).build();
            } else if (ConectorTypeEnum.AND.getValue().equals(value.toUpperCase()) ) {
                token = Token.builder().type(TokenTypeEnum.CONNECTOR).value(value).build();
            } else {
                token = Token.builder().value(value).type(TokenTypeEnum.WORD).build();
            }
            position++;
            return token;
        }
        return null;
    }
}