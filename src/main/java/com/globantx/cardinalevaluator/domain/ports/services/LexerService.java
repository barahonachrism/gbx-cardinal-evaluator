package com.globantx.cardinalevaluator.domain.ports.services;

import com.globantx.cardinalevaluator.domain.entities.Token;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface LexerService {
    List<Token> readAllTokens();

    Token readToken();
}
