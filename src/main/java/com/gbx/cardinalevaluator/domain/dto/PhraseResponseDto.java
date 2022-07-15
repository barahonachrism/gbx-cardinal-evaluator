package com.gbx.cardinalevaluator.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PhraseResponseDto {
    private String evaluatedPhrase;
    private String responseMessage;
}
