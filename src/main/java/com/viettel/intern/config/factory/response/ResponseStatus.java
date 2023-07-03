package com.viettel.intern.config.factory.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.viettel.intern.config.locale.Translator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseStatus implements Serializable {
    private String code;
    private String message;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    )
    private Date responseTime;
    private String displayMessage;

    public ResponseStatus(String code, boolean setMessageImplicitly) {
        this.setCode(code, setMessageImplicitly);
    }

    public void setCode(String code, boolean setMessageImplicitly) {
        this.code = code;
        if (setMessageImplicitly) {
            this.message = Translator.toLocale(code);
        }

        this.displayMessage = this.message;
    }

}
