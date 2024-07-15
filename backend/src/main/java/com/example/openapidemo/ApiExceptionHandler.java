package com.example.openapidemo;

import com.example.openapidemo.generated.model.ValidationError;
import com.example.openapidemo.generated.model.ValidationErrors;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationErrors> handleValidationErrors(MethodArgumentNotValidException ex) {
    var validationErrorsResponse = new ValidationErrors().errors(getValidationErrors(ex.getBindingResult()
                                                                                       .getFieldErrors()));

    return new ResponseEntity<>(validationErrorsResponse, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  private List<ValidationError> getValidationErrors(List<FieldError> errors) {
    return errors
        .stream()
        .map(fieldError -> new ValidationError().fieldName(fieldError.getField())
                                                .message(fieldError.getDefaultMessage()))
        .toList();
  }

}
