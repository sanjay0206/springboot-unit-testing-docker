package com.testing.books;

import java.util.List;

public interface BookService {
    List<Book> getAllBookRecords ();

    Book getBookById (Long bookId);

    Book createBookRecord (Book book);

    Book updateBook (Long bookId, Book book);

    void deleteBook (Long bookId);
}
