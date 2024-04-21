package com.example.main.filters;

import com.example.main.responses.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class InputInvalidFilter {
    public static ResponseEntity<ResponseObject> checkInvalidInput(BindingResult result) {
        if(result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .filter(error -> error.getDefaultMessage() != null)
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            return ResponseEntity.ok().body(
                    ResponseObject.builder()
                            .timeStamp(LocalDateTime.now())
                            .message("Input is invalid !")
                            .status(BAD_REQUEST)
                            .statusCode(BAD_REQUEST.value())
                            .data(errors)
                            .build()
            );
        }
        return null;
    }
}
