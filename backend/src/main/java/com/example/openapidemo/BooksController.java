package com.example.openapidemo;

import com.example.openapidemo.generated.api.BooksApi;
import com.example.openapidemo.generated.model.Book;
import com.example.openapidemo.generated.model.Books;
import com.example.openapidemo.generated.model.NewBook;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
class BooksController implements BooksApi {

  @Override
  public ResponseEntity<Books> getBooks() {
    var exampleBooks = List.of(
        new Book().id(UUID.randomUUID())
                  .title("My book")
                  .author("Me")
                  .publishedOn(LocalDate.of(2024, 7, 15)),
        new Book().id(UUID.randomUUID())
                  .title("Someone's book")
                  .author("Anon")
                  .publishedOn(LocalDate.of(2022, 2, 23))
                              );
    var books = new Books();
    books.setList(exampleBooks);

    return ResponseEntity.ok(books);
  }

  @Override
  public ResponseEntity<Book> createBook(NewBook newBook) {
    // Assume we create a book
    return ResponseEntity.ok(new Book());
  }
}
