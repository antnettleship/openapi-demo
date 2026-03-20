package com.example.openapidemo;

import com.example.openapidemo.generated.dto.StandardError;
import com.example.openapidemo.generated.dto.ValidationError;
import com.example.openapidemo.generated.dto.ValidationErrors;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ValidationErrors handleValidationErrors(final MethodArgumentNotValidException ex) {
    final var errors = getValidationErrors(ex.getBindingResult()
                                             .getFieldErrors());
    return new ValidationErrors().errors(errors);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MissingRequestHeaderException.class)
  public StandardError handleMissingRequestHeader(final MissingRequestHeaderException ex) {
    return new StandardError().errorMessage("Missing required request header: " + ex.getHeaderName());
  }

  private List<ValidationError> getValidationErrors(final List<FieldError> errors) {
    return errors
      .stream()
      .map(fieldError -> new ValidationError().fieldName(fieldError.getField())
                                              .message(fieldError.getDefaultMessage()))
      .toList();
  }

}
