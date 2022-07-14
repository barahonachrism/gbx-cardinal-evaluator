package com.globantx.cardinalevaluator.domain.ports.repository;

import com.globantx.cardinalevaluator.domain.entities.CardinalNumber;

import java.util.Map;

public interface CardinalNumbersCatalogRepository {
    Map<String, CardinalNumber> getCardinalNumberMap();

    boolean hasNumber(String phrase) ;

    CardinalNumber getCardinalNumber(String phrase);

    String getNormalizedText(String phrase);
}
