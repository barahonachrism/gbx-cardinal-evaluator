package com.gbx.cardinalevaluator.domain.entities;

import com.gbx.cardinalevaluator.domain.commons.TokenTypeEnum;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Token {
    String value;
    TokenTypeEnum type;
}
