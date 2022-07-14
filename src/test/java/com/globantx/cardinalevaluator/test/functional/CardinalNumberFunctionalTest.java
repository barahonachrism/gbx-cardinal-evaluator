package com.globantx.cardinalevaluator.test.functional;

import com.globantx.cardinalevaluator.domain.dto.PhraseRequestDto;
import com.globantx.cardinalevaluator.domain.dto.PhraseResponseDto;
import com.globantx.cardinalevaluator.domain.ports.services.ParserService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;

public class CardinalNumberFunctionalTest {
    private String phrase;
    @Autowired
    TestRestTemplate restTemplate;


    @Given("La frase en texto {string} por servicio web")
    public void la_frase_en_texto(String phrase) {
        this.phrase = phrase;
    }
    @Then("La frase en numeros {string} por servicio web")
    public void la_frase_en_numeros(String expectedPhrase) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/cardinal-evaluator");
        PhraseRequestDto phraseRequestDto = PhraseRequestDto.builder().phrase(phrase).build();
        HttpEntity<PhraseRequestDto> requestEntity = new HttpEntity<>(phraseRequestDto, headers);

        ResponseEntity<PhraseResponseDto> evaluatedPhraseDto = restTemplate.postForEntity(builder.toUriString(), requestEntity,PhraseResponseDto.class );

        Assertions.assertEquals(expectedPhrase,evaluatedPhraseDto.getBody().getEvaluatedPhrase());
    }
}
