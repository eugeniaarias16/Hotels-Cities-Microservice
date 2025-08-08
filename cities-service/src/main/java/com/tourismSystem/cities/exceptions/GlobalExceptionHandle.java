package com.tourismSystem.cities.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<Map<String,String>>handleNotFound(ResourceNotFound ex){
        Map<String,String> error= new HashMap<>();
        error.put("error","Not Found");
        error.put("message:", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<Map<String,String>>handleBadRequest(BadRequest ex){
        Map<String,String> error=new HashMap<>();
        error.put("error","Bad Request");
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

}
