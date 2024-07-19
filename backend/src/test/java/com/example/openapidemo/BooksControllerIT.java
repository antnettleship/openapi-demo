package com.example.openapidemo;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.openapidemo.generated.model.Books;
import com.example.openapidemo.generated.model.NewBook;
import com.example.openapidemo.generated.model.ValidationError;
import com.example.openapidemo.generated.model.ValidationErrors;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BooksControllerIT {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void createBook_invalidRequest_returnsValidationErrors() {
    var newBook = new NewBook().title("This title is way too long I'm afraid sir so you will need to shorten it");

    var response = restTemplate.postForEntity("/books", newBook, ValidationErrors.class);
    var actualErrors = response.getBody()
                               .getErrors()
                               .stream()
                               .collect(Collectors.toMap(ValidationError::getFieldName, ValidationError::getMessage));

    var expectedErrors = Map.of(
        "author", "must not be null",
        "publishedOn", "must not be null",
        "title", "size must be between 1 and 70");
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    assertThat(actualErrors).isEqualTo(expectedErrors);
  }

  @Test
  void getBooks_validRequest_returnsExampleBooks() {
    var response = restTemplate.getForEntity("/books", Books.class);

    var booksList = response.getBody()
                            .getList();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(booksList).hasSize(2);
    assertThat(booksList.get(0)
                        .getTitle()).isEqualTo("My book");
    assertThat(booksList.get(1)
                        .getTitle()).isEqualTo("Someone's book");
  }

}
