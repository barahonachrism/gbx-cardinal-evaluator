package com.globantx.cardinalevaluator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private int position;

    private String[] tokenParts;
    public Lexer(String phrase) {
        this.tokenParts = phrase.split(" ");
        position = 0;
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
            if (CardinalNumbersCatalog.hasNumber(value)) {
                token = Token.builder().type(TokenType.NUMBER).value(value).build();
            } else if (ConectorEnum.AND.value.equals(value.toUpperCase()) ) {
                token = Token.builder().type(TokenType.CONNECTOR).value(value).build();
            } else {
                token = Token.builder().value(value).type(TokenType.WORD).build();
            }
            position++;
            return token;
        }
        return null;
    }
}