package com.globantx.cardinalevaluator.domain.ports;

import com.globantx.cardinalevaluator.domain.dto.PhraseRequestDto;
import com.globantx.cardinalevaluator.domain.dto.PhraseResponseDto;
import org.springframework.http.ResponseEntity;

public interface AnalyzerController {
    ResponseEntity<PhraseResponseDto> evaluatePhrase( PhraseRequestDto phrase);
}
