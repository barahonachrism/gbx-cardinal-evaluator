package com.globantx.cardinalevaluator.domain.usescases;

import com.globantx.cardinalevaluator.domain.entities.Token;
import com.globantx.cardinalevaluator.domain.commons.TokenTypeEnum;
import com.globantx.cardinalevaluator.domain.commons.ConectorTypeEnum;
import com.globantx.cardinalevaluator.domain.ports.repository.CardinalNumbersCatalogRepository;
import com.globantx.cardinalevaluator.domain.ports.services.LexerService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class PhraseLexerService implements LexerService {
    private int position;

    private String[] tokenParts;

    private CardinalNumbersCatalogRepository cardinalNumbersCatalogRepository;

    public PhraseLexerService(String phrase, CardinalNumbersCatalogRepository CardinalNumbersCatalogRepository) {
        this.tokenParts = phrase.split(" ");
        position = 0;
        this.cardinalNumbersCatalogRepository = CardinalNumbersCatalogRepository;
    }
    public List<Token> readAllTokens() throws URISyntaxException, IOException {
        List<Token> tokens = new ArrayList<>();
        Token token = readToken();
        while(token!= null){
            tokens.add(token);
            token = readToken();
        }
        return tokens;
    }

    public Token readToken() throws URISyntaxException, IOException {
        if(position < tokenParts.length) {
            Token token;
            String value = tokenParts[position];
            if (cardinalNumbersCatalogRepository.hasNumber(value)) {
                token = Token.builder().type(TokenTypeEnum.NUMBER).value(value).build();
            } else if (ConectorTypeEnum.AND.value.equals(value.toUpperCase()) ) {
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