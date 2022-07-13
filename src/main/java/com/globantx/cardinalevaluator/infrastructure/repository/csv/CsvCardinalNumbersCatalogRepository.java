package com.globantx.cardinalevaluator.infrastructure.repository.csv;

import com.globantx.cardinalevaluator.domain.entities.CardinalNumber;
import com.globantx.cardinalevaluator.domain.exception.CsvParseException;
import com.globantx.cardinalevaluator.domain.ports.repository.CardinalNumbersCatalogRepository;
import org.springframework.stereotype.Repository;

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

@Repository
public class CsvCardinalNumbersCatalogRepository implements CardinalNumbersCatalogRepository {
    private static final int NUMBER_POSITION = 0;
    private static final int CARDINAL_POSITION = 1;

    private static final int IS_PLURAL_NUMBER_POSITION = 2;

    private Map<String, CardinalNumber> cardinalNumberMap;

    public Map<String, CardinalNumber> getCardinalNumberMap() {
        if (cardinalNumberMap == null) {
            Map<String, CardinalNumber> modifiableMap = new HashMap<>();
            try (Stream<String> stream = Files.lines(Paths.get(ClassLoader.getSystemResource("CardinalNumbersDictionary_es.csv").toURI()))) {
                AtomicBoolean isData = new AtomicBoolean(false);
                stream.forEach(line -> {
                    if (isData.get()) {
                        //1 000 000 000 000	billon	billones
                        String[] tokens = line.split("\t");
                        CardinalNumber cardinalNumber = CardinalNumber.builder()
                                .numericValueText(tokens[CsvCardinalNumbersCatalogRepository.NUMBER_POSITION])
                                .singularCardinalName(tokens[CsvCardinalNumbersCatalogRepository.CARDINAL_POSITION])
                                .plural(Boolean.valueOf(tokens[CsvCardinalNumbersCatalogRepository.IS_PLURAL_NUMBER_POSITION])).build();
                        modifiableMap.put(cardinalNumber.getSingularCardinalName(), cardinalNumber);
                    }
                    isData.set(true);
                });
                cardinalNumberMap = Collections.unmodifiableMap(modifiableMap);
            } catch (IOException e) {
                throw new CsvParseException("Error to read file csv as cardinal numbers database",e);
            } catch (URISyntaxException e) {
                throw new CsvParseException("Error to read file csv as cardinal numbers database",e);
            }
        }

        return cardinalNumberMap;
    }

    public boolean hasNumber(String phrase) {

        return getCardinalNumberMap().containsKey(getNormalizedText(phrase));
    }

    public CardinalNumber getCardinalNumber(String phrase) {
        return getCardinalNumberMap().get(getNormalizedText(phrase));
    }

    public String getNormalizedText(String phrase) {
        //eliminate tildes in comparation of numbers
        String normalizePhrase = Normalizer.normalize(phrase, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        //get numbers in lowercase
        return normalizePhrase.toLowerCase();
    }

}