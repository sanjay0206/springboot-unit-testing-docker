package com.testing.books.service;

import com.testing.books.dto.BookDTO;
import com.testing.books.entity.Book;

import java.math.BigDecimal;
import java.util.List;



public interface BookService {

    BigDecimal getCountOfAllBooks();
    List<Book> getAllBookRecords();
    Book getBookById(Long bookId);
    Book createBookRecord(Book book);
    Book updateBook(Long bookId, BookDTO bookDTO);
    void deleteBook(Long bookId);
}
