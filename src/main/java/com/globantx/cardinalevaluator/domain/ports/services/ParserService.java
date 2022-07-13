package com.globantx.cardinalevaluator.domain.ports.services;

import com.globantx.cardinalevaluator.domain.entities.CardinalNumber;

import java.io.IOException;
import java.net.URISyntaxException;

public interface ParserService {
    String parsePhrase(String phrase) throws URISyntaxException, IOException;

    void validateSyntaxNumber(CardinalNumber previosCardinalNumber, CardinalNumber cardinalNumber);
}
