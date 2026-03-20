package com.example.openapidemo.web;

import com.example.openapidemo.Application;
import com.example.openapidemo.generated.dto.CreateBookRequest;
import com.example.openapidemo.generated.dto.GetBooksResponse;
import com.example.openapidemo.generated.dto.StandardError;
import com.example.openapidemo.generated.dto.ValidationError;
import com.example.openapidemo.generated.dto.ValidationErrors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookResourceIT {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void createBook_missingRequestIdHeader_returnsBaRequest() {
    final var createBookRequest = new CreateBookRequest().title("This title is way too long I'm afraid sir so you will need to shorten it");

    final var response = restTemplate.postForEntity("/books", createBookRequest, StandardError.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody().getErrorMessage()).isEqualTo("Missing required request header: X-Request-ID");
  }

  @Test
  void createBook_invalidRequest_returnsValidationErrors() {
    final var createBookRequest = new CreateBookRequest().title("This title is way too long I'm afraid sir so you will need to shorten it");

    final var headers = new HttpHeaders();
    headers.set("X-Request-ID", "test-request-id");
    final var requestEntity = new HttpEntity<>(createBookRequest, headers);
    final var response = restTemplate.postForEntity("/books", requestEntity, ValidationErrors.class);
    final var actualErrors = response.getBody()
                                     .getErrors()
                                     .stream()
                                     .collect(Collectors.toMap(ValidationError::getFieldName, ValidationError::getMessage));

    final var expectedErrors = Map.of(
      "isbn", "must not be null",
      "title", "size must be between 1 and 70",
      "author", "must not be null",
      "category", "must not be null",
      "publishedOn", "must not be null"
    );
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    assertThat(actualErrors).isEqualTo(expectedErrors);
  }

  @Test
  void getBooks_validRequest_returnsExampleBooks() {
    final var headers = new HttpHeaders();
    headers.set("X-Request-ID", "test-request-id");
    final var response = restTemplate.exchange("/books", HttpMethod.GET, new HttpEntity<>(headers), GetBooksResponse.class);

    final var booksList = response.getBody()
                                  .getBooks();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(booksList).hasSize(2);

    final var book1 = booksList.get(0);
    assertThat(book1.getIsbn()).isEqualTo("978-0-7432-7356-5");
    assertThat(book1.getTitle()).isEqualTo("The Great Gatsby");

    final var book2 = booksList.get(1);
    assertThat(book2.getIsbn()).isEqualTo("978-0-06-112008-4");
    assertThat(book2.getTitle()).isEqualTo("To Kill a Mockingbird");
  }

}
