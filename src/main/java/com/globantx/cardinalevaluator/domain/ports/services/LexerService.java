package com.globantx.cardinalevaluator.domain.ports.services;

import com.globantx.cardinalevaluator.domain.entities.Token;

import java.util.List;

public interface LexerService {
    List<Token> readAllTokens();

    Token readToken();
}
