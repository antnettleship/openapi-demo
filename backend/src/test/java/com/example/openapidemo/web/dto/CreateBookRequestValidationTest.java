package com.example.openapidemo.web.dto;

import com.example.openapidemo.generated.dto.CreateBookRequest;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class CreateBookRequestValidationTest {

  private static Validator validator;

  @BeforeAll
  static void setUp() {
    validator = Validation.buildDefaultValidatorFactory()
                          .getValidator();
  }

  @Nested
  class ISBN {

    @ParameterizedTest(name = "valid ISBN-10 (no hyphens): {0}")
    @ValueSource(strings = {
      "0306406152",  // standard digits
      "030640615X",  // X check digit
      "0-306-40615-2",
      "0-306-40615-X",
    })
    void isbn10IsValidWithAndWithoutHyphens(final String isbn) {
      final var violations = validator.validate(validBook().isbn(isbn));

      assertThat(violations).isEmpty();
    }

    @ParameterizedTest(name = "valid ISBN-13 (no hyphens): {0}")
    @ValueSource(strings = {
      "9780306406157",  // 978 prefix
      "9790306406153",  // 979 prefix
      "978-0-306-40615-7",
      "979-0-306-40615-3"
    })
    void isbn13IsValidWithAndWithoutHyphens(final String isbn) {
      final var violations = validator.validate(validBook().isbn(isbn));

      assertThat(violations).isEmpty();
    }

    @Test
    void isbnIsRequired() {
      final var violations = validator.validate(validBook().isbn(null));

      assertThat(violations).hasSize(1);
      assertThat(violations.iterator()
                           .next()
                           .getMessage()).isEqualTo("must not be null");
    }

    @ParameterizedTest(name = "invalid isbn: {0}")
    @ValueSource(strings = {
      "",                    // empty string
      "123",                 // too short
      "12345678901234",      // 14 digits (too long for ISBN-13)
      "97803064061570",      // 14 digits (ISBN-13 + extra digit)
      "030640615Y",          // invalid check character (Y)
      "0-306-40615-Y",       // invalid check character with hyphens
      "978-0-306-40615",     // ISBN-13 with hyphens but missing check digit
      "978 0 306 40615 7",   // spaces instead of hyphens
      "ABCDEFGHIJ",          // letters only
    })
    void invalidIsbnHasViolations(final String isbn) {
      final var violations = validator.validate(validBook().isbn(isbn));

      assertThat(violations).hasSize(1);
      assertThat(violations.iterator()
                           .next()
                           .getMessage()).isEqualTo(
        "must match \"^(?:\\d{9}[\\dX]|\\d{1,5}-\\d{1,7}-\\d{1,6}-[\\dX]|\\d{13}|\\d{3}-\\d{1,5}-\\d{1,7}-\\d{1,6}-\\d)$\"");
    }
  }

  @Nested
  class PublishedOn {

    @Test
    void publishedOnCannotBeInTheFuture() {
      final var violations = validator.validate(validBook().publishedOn(LocalDate.now()
                                                                                 .plusDays(1)));

      assertThat(violations).hasSize(1);
      assertThat(violations.iterator()
                           .next()
                           .getMessage()).isEqualTo("must be a date in the past or in the present");
    }

  }

  @Nested
  class FantasyCategory {

    @Test
    void fantasyCategoryRequiredWhenCategoryIsFanatsy() {
      final var violations = validator.validate(validBook().category(CreateBookRequest.CategoryEnum.FANTASY)
                                                           .fantasyCategory(null));

      assertThat(violations).hasSize(1);
      assertThat(violations.iterator()
                           .next()
                           .getMessage()).isEqualTo("fantasyCategory is required when category is FANTASY");
    }

  }

  private CreateBookRequest validBook() {
    return new CreateBookRequest()
      .isbn("1234567890")
      .title("Test Book")
      .author("Test Author")
      .category(CreateBookRequest.CategoryEnum.FICTION)
      .publishedOn(LocalDate.of(2024, 1, 1));
  }
}

