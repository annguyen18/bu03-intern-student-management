package com.viettel.intern.exception;

import com.viettel.intern.config.factory.response.GeneralResponse;
import com.viettel.intern.constant.ResponseStatusCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@SuppressWarnings({"java:S1165", "java:S1948"}) // disable rule sonalint
public class BaseResponseException extends RuntimeException {
    private final ResponseStatusCode responseStatusCode;
    private GeneralResponse<Object> dataResponse;
    private Map<String, String> params;

    public BaseResponseException(ResponseStatusCode responseStatusCode) {
        this.responseStatusCode = responseStatusCode;
    }

    public BaseResponseException(GeneralResponse<Object> dataResponse, ResponseStatusCode responseStatusCode) {
        this.dataResponse = dataResponse;
        this.responseStatusCode = responseStatusCode;
    }

    public BaseResponseException(GeneralResponse<Object> dataResponse, ResponseStatusCode responseStatusCode, Map<String, String> params) {
        this.dataResponse = dataResponse;
        this.responseStatusCode = responseStatusCode;
        this.params = params;
    }
}
