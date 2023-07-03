package com.viettel.intern.client;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author KhaiBQ
 * @since 6/16/2023 - 9:38 PM
 */
@Slf4j
@Component
public class ClientTemplateImpl implements ClientTemplate {

    @Override
    public <R> ResponseEntity<?> get(String uri, HttpHeaders headers, int timeOutSeconds, Class<R> responseClass) {
        log.info("Start blocking GET uri: " + uri);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectTimeout(timeOutSeconds );
        httpRequestFactory.setReadTimeout(timeOutSeconds);
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        try {
            return restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, request, responseClass);
        } catch (RestClientException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public <B, R> ResponseEntity<?> post(String uri, HttpHeaders headers, int timeOutSeconds, B body, Class<R> responseClass) {
        log.info("Start blocking POST uri: " + uri);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectTimeout(timeOutSeconds * 1000);
        httpRequestFactory.setReadTimeout(timeOutSeconds * 1000);
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        HttpEntity<B> request = new HttpEntity<>(body, headers);
        try {
            return restTemplate.postForEntity(builder.build().toUri(), request, responseClass);
        } catch (RestClientException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
