package com.globantx.cardinalevaluator.domain.ports.repository;

import com.globantx.cardinalevaluator.domain.entities.CardinalNumber;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public interface CardinalNumbersCatalogRepository {
    Map<String, CardinalNumber> getCardinalNumberMap() throws URISyntaxException, IOException;

    boolean hasNumber(String phrase) throws URISyntaxException, IOException ;

    CardinalNumber getCardinalNumber(String phrase) throws URISyntaxException, IOException;

    String getNormalizedText(String phrase);
}
