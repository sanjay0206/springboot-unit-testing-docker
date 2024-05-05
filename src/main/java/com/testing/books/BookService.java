package com.testing.books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBookRecords() {
        return  bookRepository.findAll();
    }

    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalStateException("Book with ID: " + bookId + " not found."));
    }

    public Book createBookRecord(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Long bookId, BookDto bookDto) {
        Book existingBookRecord = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalStateException("Book with ID: " + bookId + " not found."));

        if (bookDto.getName() != null)
            existingBookRecord.setName(bookDto.getName());

        if (bookDto.getSummary() != null)
            existingBookRecord.setSummary(bookDto.getSummary());

        if (bookDto.getRating() != null)
            existingBookRecord.setRating(bookDto.getRating());

        return bookRepository.save(existingBookRecord);
    }

    public void deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalStateException("Book with ID: " + bookId + " not found."));

        bookRepository.delete(book);
    }
}
