package com.example.openapidemo.service;

import com.example.openapidemo.generated.dto.CreateBookRequest;
import com.example.openapidemo.generated.dto.CreateBookResponse;
import com.example.openapidemo.generated.dto.GetBooksResponse;
import org.springframework.stereotype.Service;

import static com.example.openapidemo.service.DummyData.DUMMY_BOOKS;

@Service
public class BookService {

  public GetBooksResponse getBooks() {
    return new GetBooksResponse().books(DUMMY_BOOKS);
  }

  public CreateBookResponse createBook(final CreateBookRequest requestBody) {
    // This is an alternative approach to our custom annotations, however the drawback is the n+1 validation problem.
    // i.e. make a request, bean validation kicks in, make another request passing bean validation, service-level validation kicks in etc.
    //
    //    if (requestBody.getCategory() == CreateBookRequest.CategoryEnum.FANTASY
    //      && requestBody.getFantasyCategory() == null) {
    //      throw new CreateBookValidationException("fantasyCategory is required when category is FANTASY");
    //    }

    // Not implemented
    // - Create the book
    // - Return the book
    return new CreateBookResponse();
  }
}
