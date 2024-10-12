package com.ffs.task.demo.exception;

import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class ExceptionHandler  {


    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<Object> globalExceptionHandler(Exception exc) {
        return new ResponseEntity<Object>(new ErrorResponse(INTERNAL_SERVER_ERROR, exc.getMessage(), LocalDateTime.now()), INTERNAL_SERVER_ERROR);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notFoundExceptionHandler(NotFoundException exc) {

        return new ResponseEntity<Object>(new ErrorResponse(HttpStatus.NOT_FOUND, exc.getMessage(), LocalDateTime.now()), HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ArgumentException.class)
    public ResponseEntity<Object> argumentExceptionHandler(ArgumentException exc) {

        return new ResponseEntity<Object>(new ErrorResponse(BAD_REQUEST, exc.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ReportGenerationException.class)
    public ResponseEntity<Object> handleJRException(ReportGenerationException ex) {
        return new ResponseEntity<Object>(new ErrorResponse(INTERNAL_SERVER_ERROR, ex.getMessage(), LocalDateTime.now()), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    }

