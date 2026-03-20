package com.example.openapidemo.web;

import com.example.openapidemo.generated.api.BooksApi;
import com.example.openapidemo.generated.dto.CreateBookRequest;
import com.example.openapidemo.generated.dto.CreateBookResponse;
import com.example.openapidemo.generated.dto.GetBooksResponse;
import com.example.openapidemo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
class BookResource implements BooksApi {

  private final BookService bookService;

  @Override
  public ResponseEntity<GetBooksResponse> getBooks(final String xRequestID) {

    final var booksResponse = bookService.getBooks();

    return ResponseEntity.ok(booksResponse);
  }

  @Override
  public ResponseEntity<CreateBookResponse> createBook(final String xRequestID, final CreateBookRequest requestBody) {

    final var createBookResponse = bookService.createBook(requestBody);

    return new ResponseEntity<>(createBookResponse, CREATED);
  }
}
