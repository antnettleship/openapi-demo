package com.example.openapidemo.service;

import com.example.openapidemo.generated.dto.Book;

import java.time.LocalDate;
import java.util.List;

public class DummyData {

  private DummyData() {
    /* This utility class should not be instantiated */
  }

  public static final List<Book> DUMMY_BOOKS = List.of(

    new Book().isbn("978-0-7432-7356-5")
              .title("The Great Gatsby")
              .author("F. Scott Fitzgerald")
              .category(Book.CategoryEnum.FICTION)
              .publishedOn(LocalDate.of(1925, 4, 10)),

    new Book().isbn("978-0-06-112008-4")
              .title("To Kill a Mockingbird")
              .author("Harper Lee")
              .category(Book.CategoryEnum.FICTION)
              .publishedOn(LocalDate.of(1960, 7, 11))
  );

}
