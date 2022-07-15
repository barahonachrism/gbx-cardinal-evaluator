package com.gbx.cardinalevaluator.domain.ports.services;

import com.gbx.cardinalevaluator.domain.entities.Token;

import java.util.List;

public interface LexerService {
    List<Token> readAllTokens();

    Token readToken();
}
