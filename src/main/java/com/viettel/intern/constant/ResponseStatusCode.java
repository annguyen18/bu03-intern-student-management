package com.viettel.intern.constant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Builder
@Getter
public class ResponseStatusCode implements Serializable {
    private final String code;
    private final int httpCode;
}
