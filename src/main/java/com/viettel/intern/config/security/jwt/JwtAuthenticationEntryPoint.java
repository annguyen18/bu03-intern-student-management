package com.viettel.intern.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viettel.intern.config.factory.response.GeneralResponse;
import com.viettel.intern.config.factory.response.ResponseStatus;
import com.viettel.intern.constant.ResponseStatusCodeEnum;
import lombok.AllArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@AllArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        GeneralResponse<Object> generalResponse = new GeneralResponse<>();
        ResponseStatus responseStatus = new ResponseStatus(ResponseStatusCodeEnum.TOKEN_ERROR.getCode(), true);
        responseStatus.setResponseTime(new Date());
        generalResponse.setStatus(responseStatus);

        ServletOutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        out.write(mapper.writeValueAsString(generalResponse).getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }
}
