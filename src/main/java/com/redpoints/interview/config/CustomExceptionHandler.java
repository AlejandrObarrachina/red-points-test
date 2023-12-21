package com.redpoints.interview.config;

import com.redpoints.interview.exceptionModels.WrongIdException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidInputMovie(MethodArgumentNotValidException exception) {
        Map<String, String> map =  new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            map.put("code",HttpStatus.BAD_REQUEST.toString());
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        return map;
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongIdException.class)
    public Map<String, String> handleInvalidMovieId(WrongIdException exception) {
        Map<String, String> map =  new HashMap<>();
        map.put("errorMessage", exception.getMessage());
        return map;
    }


}
