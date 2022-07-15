package com.gbx.cardinalevaluator.domain.ports;

import com.gbx.cardinalevaluator.domain.dto.PhraseRequestDto;
import com.gbx.cardinalevaluator.domain.dto.PhraseResponseDto;
import org.springframework.http.ResponseEntity;

public interface AnalyzerController {
    ResponseEntity<PhraseResponseDto> evaluatePhrase( PhraseRequestDto phrase);
}
