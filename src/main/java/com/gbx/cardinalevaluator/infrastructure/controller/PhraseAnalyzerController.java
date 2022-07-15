package com.gbx.cardinalevaluator.infrastructure.controller;

import com.gbx.cardinalevaluator.domain.dto.PhraseResponseDto;
import com.gbx.cardinalevaluator.domain.ports.AnalyzerController;
import com.gbx.cardinalevaluator.domain.dto.PhraseRequestDto;
import com.gbx.cardinalevaluator.domain.exception.CsvParseException;
import com.gbx.cardinalevaluator.domain.exception.PhraseParseException;
import com.gbx.cardinalevaluator.domain.ports.services.ParserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/cardinal-evaluator")
@Tag(name = "Service for evaluate cardinal numbers")
public class PhraseAnalyzerController implements AnalyzerController {
    @Autowired
    private ParserService parserService;
    @PostMapping
    @Operation(description = "Evaluate phrase and convert cardinal numbers to decimal numbers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The phrase is evaluate successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Error to evaluate the phrase, the syntax not is valid",
                    content = @Content) })
    @Override
    public ResponseEntity<PhraseResponseDto> evaluatePhrase(@RequestBody @Parameter(description = "Phrase to evaluate") PhraseRequestDto phrase){
        String evaluatedPhrase;
        String responseMessage;
        HttpStatus status;
        try {
            evaluatedPhrase = parserService.parsePhrase(phrase.getPhrase());
            status = HttpStatus.OK;
            responseMessage = status.toString();
        }catch(CsvParseException | PhraseParseException e){
            evaluatedPhrase = "";
            responseMessage = e.getMessage();
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(PhraseResponseDto.builder()
                .evaluatedPhrase(evaluatedPhrase).responseMessage(responseMessage).build(), status);
    }

}
