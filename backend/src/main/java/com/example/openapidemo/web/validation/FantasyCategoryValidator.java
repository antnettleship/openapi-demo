package com.example.openapidemo.web.validation;

import com.example.openapidemo.generated.dto.CreateBookRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FantasyCategoryValidator implements ConstraintValidator<ValidFantasyCategory, CreateBookRequest> {

  @Override
  public boolean isValid(final CreateBookRequest createBookRequest, final ConstraintValidatorContext context) {
    if (createBookRequest.getCategory() == CreateBookRequest.CategoryEnum.FANTASY) {
      return createBookRequest.getFantasyCategory() != null;
    }
    return true;
  }
}