package com.example.main.exceptions;

import com.example.main.responses.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseObject> handleGeneralException(Exception ex){
        return ResponseEntity.internalServerError().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message(ex.getMessage())
                        .status(INTERNAL_SERVER_ERROR)
                        .statusCode(INTERNAL_SERVER_ERROR.value())
                        .build()
        );
    }

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<ResponseObject> handleIdNotFoundException(Exception ex){
        return ResponseEntity.internalServerError().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message(ex.getMessage())
                        .status(NOT_FOUND)
                        .statusCode(NOT_FOUND.value())
                        .build()
        );
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ResponseObject> handleInvalidDataException(Exception ex){
        return ResponseEntity.badRequest().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message(ex.getMessage())
                        .status(BAD_REQUEST)
                        .statusCode(BAD_REQUEST.value())
                        .build()
        );
    }

    @ExceptionHandler(ExpiredException.class)
    public ResponseEntity<ResponseObject> handleExpiredException(Exception ex){
        return ResponseEntity.badRequest().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message(ex.getMessage())
                        .status(BAD_REQUEST)
                        .statusCode(BAD_REQUEST.value())
                        .build()
        );
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ResponseObject> handleDataNotFoundException(Exception ex){
        return ResponseEntity.status(NOT_FOUND).body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message(ex.getMessage())
                        .status(NOT_FOUND)
                        .statusCode(NOT_FOUND.value())
                        .build()
        );
    }

    @ExceptionHandler(NotMatchException.class)
    public ResponseEntity<ResponseObject> handleNotMatchException(Exception ex){
        return ResponseEntity.badRequest().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message(ex.getMessage())
                        .status(BAD_REQUEST)
                        .statusCode(BAD_REQUEST.value())
                        .build()
        );
    }

    @ExceptionHandler(PayloadTooLargeException.class)
    public ResponseEntity<ResponseObject> handlePayloadTooLargeException(Exception ex){
        return ResponseEntity.status(PAYLOAD_TOO_LARGE).body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("File is too large! Maximum size is 10MB")
                        .status(PAYLOAD_TOO_LARGE)
                        .statusCode(PAYLOAD_TOO_LARGE.value())
                        .build()
        );
    }

    @ExceptionHandler(UnsupportedMediaTypeException.class)
    public ResponseEntity<ResponseObject> handleUnsupportedMediaTypeException(Exception ex){
        return ResponseEntity.status(UNSUPPORTED_MEDIA_TYPE).body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("File must be an image")
                        .status(UNSUPPORTED_MEDIA_TYPE)
                        .statusCode(UNSUPPORTED_MEDIA_TYPE.value())
                        .build()
        );
    }



}
