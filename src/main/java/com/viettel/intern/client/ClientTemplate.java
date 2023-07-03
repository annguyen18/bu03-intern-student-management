package com.viettel.intern.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

/**
 * @author KhaiBQ
 * @since 6/16/2023 - 9:38 PM
 */
@SuppressWarnings({"java:S1452"}) // disable rule sonalint
public interface ClientTemplate {
    <R> ResponseEntity<?> get(String uri, HttpHeaders headers, int timeOutSeconds, Class<R> responseClass);

    <B, R> ResponseEntity<?> post(String uri, HttpHeaders headers, int timeOutSeconds, B body, Class<R> responseClass);
}
