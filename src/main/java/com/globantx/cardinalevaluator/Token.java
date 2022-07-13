package com.globantx.cardinalevaluator;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Token {
    String value;
    TokenType type;
}
