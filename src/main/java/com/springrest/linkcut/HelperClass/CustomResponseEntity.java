package com.springrest.linkcut.HelperClass;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.io.Serial;
import java.io.Serializable;
public class CustomResponseEntity<T> extends ResponseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -950111661934555731L;

    public CustomResponseEntity(HttpStatusCode status) {
        super(status);
    }

    public CustomResponseEntity(Object body, HttpStatusCode status) {
        super(body, status);
    }

    public CustomResponseEntity(MultiValueMap headers, HttpStatusCode status) {
        super(headers, status);
    }

    public CustomResponseEntity(Object body, MultiValueMap headers, HttpStatusCode status) {
        super(body, headers, status);
    }

    public CustomResponseEntity(Object body, MultiValueMap headers, int rawStatus) {
        super(body, headers, rawStatus);
    }
}
