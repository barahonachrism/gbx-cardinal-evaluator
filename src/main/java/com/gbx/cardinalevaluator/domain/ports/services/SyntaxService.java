package com.gbx.cardinalevaluator.domain.ports.services;

import com.gbx.cardinalevaluator.domain.entities.CardinalNumber;

public interface SyntaxService {
    void validateSyntaxNumber(CardinalNumber previosCardinalNumber, CardinalNumber cardinalNumber);


    void validateSyntax(CardinalNumber cardinalNumber, int currentExponential);

    void validateTenNumber(CardinalNumber cardinalTenNumber, CardinalNumber cardinalUnitNumber);
}
