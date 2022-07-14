package com.globantx.cardinalevaluator.domain.entities;

import com.globantx.cardinalevaluator.domain.commons.TokenTypeEnum;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
public class Token {
    String value;
    TokenTypeEnum type;
}
