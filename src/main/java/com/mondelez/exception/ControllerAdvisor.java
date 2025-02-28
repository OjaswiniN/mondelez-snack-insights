/* (C) 2023 Mondelez Insights */
package com.mondelez.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@Component
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exceptions.InternalServerException.class)
  public ResponseEntity<Object> handleInternalServerException(
      Exceptions.InternalServerException ex, WebRequest request) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(Exceptions.NotFoundException.class)
  public ResponseEntity<Object> handleCompanyNotFoundException(
      Exceptions.NotFoundException ex, WebRequest request) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({
    BadCredentialsException.class,
    UsernameNotFoundException.class,
    ConstraintViolationException.class,
    Exceptions.BadRequestException.class,
    Exceptions.UnauthorizedUriException.class,
    Exceptions.FileStorageException.class,
    Exceptions.InvalidFileTypeException.class,
    BadCredentialsException.class
  })
  public ResponseEntity<Object> handle400Request(Exception ex, WebRequest request) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }
}
