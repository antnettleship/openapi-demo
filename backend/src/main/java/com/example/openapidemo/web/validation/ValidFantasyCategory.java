package com.example.openapidemo.web.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FantasyCategoryValidator.class)
public @interface ValidFantasyCategory {

  String message() default "fantasyCategory is required when category is FANTASY";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}