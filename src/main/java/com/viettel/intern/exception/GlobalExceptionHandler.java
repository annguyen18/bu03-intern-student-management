package com.viettel.intern.exception;

import com.viettel.intern.config.factory.response.GeneralResponse;
import com.viettel.intern.config.factory.response.ResponseFactory;
import com.viettel.intern.config.factory.response.ResponseStatus;
import com.viettel.intern.constant.ResponseStatusCode;
import com.viettel.intern.constant.ResponseStatusCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final Map<String, ResponseStatusCode> handleHttpMessageNotReadableListError = new HashMap<>();
    @Autowired
    ResponseFactory responseFactory;

    public GlobalExceptionHandler() {
        this.handleHttpMessageNotReadableListError.put("JSON parse error", ResponseStatusCodeEnum.ERROR_BODY_CLIENT);
        this.handleHttpMessageNotReadableListError.put("Required request body is missing", ResponseStatusCodeEnum.ERROR_BODY_REQUIRED);
    }

    @ExceptionHandler({BaseResponseException.class})
    public ResponseEntity<GeneralResponse<Object>> handleValidationExceptions(BaseResponseException ex) {
        try {
            if (Objects.isNull(ex.getParams())) {
                return this.responseFactory.fail(ex.getDataResponse(), ex.getResponseStatusCode());
            } else {
                return Objects.isNull(ex.getDataResponse()) ?
                        this.responseFactory.fail(new GeneralResponse<>(), ex.getResponseStatusCode(), ex.getParams())
                        : this.responseFactory.fail(ex.getDataResponse(), ex.getResponseStatusCode(), ex.getParams());
            }
        } catch (Exception var3) {
            return this.responseFactory.fail(ResponseStatusCodeEnum.INTERNAL_GENERAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler({BadCredentialsException.class})
    public final ResponseEntity<Object> handleBadCredentialsException() {
        return this.createResponse(ResponseStatusCodeEnum.USERNAME_PASSWORD_ERROR);
    }

    @ExceptionHandler({DisabledException.class})
    public final ResponseEntity<Object> handleDisabledException() {
        return this.createResponse(ResponseStatusCodeEnum.INACTIVE_ACCOUNT);
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public final ResponseEntity<Object> handleAllExceptions(Exception ex) {
        log.error("Exception: ", ex);
        return this.createResponse(ResponseStatusCodeEnum.INTERNAL_GENERAL_SERVER_ERROR);
    }

    @ExceptionHandler({BusinessException.class})
    public final ResponseEntity<Object> handleValidationExceptions(RuntimeException ex) {
        return this.createResponse(ResponseStatusCodeEnum.BUSINESS_ERROR, ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(field -> {
            if (!errors.containsKey(field.getField())) {
                errors.put(field.getField(), field.getDefaultMessage());
            }
        });
        return this.createResponse(ResponseStatusCodeEnum.VALIDATION_ERROR, errors);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        if (Objects.nonNull(ex.getMessage())) {
            Optional<ResponseStatusCode> responseStatusCode = this.handleHttpMessageNotReadableListError.entrySet()
                    .stream()
                    .filter(stringResponseStatusCodeEntry -> ex.getMessage().contains(stringResponseStatusCodeEntry.getKey()))
                    .map(Map.Entry::getValue)
                    .findFirst();
            if (responseStatusCode.isPresent()) {
                return this.createResponse(responseStatusCode.get());
            }
        }

        return this.handleExceptionInternal(ex, null, headers, status, request);
    }

    private ResponseEntity<Object> createResponse(ResponseStatusCode response) {
        ResponseStatus responseStatus = new ResponseStatus(response.getCode(), true);
        responseStatus.setResponseTime(new Date());
        GeneralResponse<Object> responseObject = new GeneralResponse<>();
        responseObject.setStatus(responseStatus);
        return new ResponseEntity<>(responseObject, HttpStatus.valueOf(response.getHttpCode()));
    }

    private ResponseEntity<Object> createResponse(ResponseStatusCode response, String displayMessage) {
        ResponseStatus responseStatus = new ResponseStatus(response.getCode(), true);
        responseStatus.setResponseTime(new Date());
        responseStatus.setDisplayMessage(displayMessage);
        GeneralResponse<Object> responseObject = new GeneralResponse<>();
        responseObject.setStatus(responseStatus);
        return new ResponseEntity<>(responseObject, HttpStatus.valueOf(response.getHttpCode()));
    }

    private ResponseEntity<Object> createResponse(ResponseStatusCode response, Object errors) {
        ResponseStatus responseStatus = new ResponseStatus(response.getCode(), true);
        responseStatus.setResponseTime(new Date());
        GeneralResponse<Object> responseObject = new GeneralResponse<>();
        responseObject.setStatus(responseStatus);
        responseObject.setData(errors);
        return new ResponseEntity<>(responseObject, HttpStatus.valueOf(response.getHttpCode()));
    }
}
