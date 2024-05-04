package com.testing.books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> getAllBookRecords() {
        return  bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalStateException("Book with ID: " + bookId + " not found."));
    }

    @Override
    public Book createBookRecord(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long bookId, Book book) {
        Book existingBookRecord = bookRepository.findById(book.getBookId())
                .orElseThrow(() -> new IllegalStateException("Book with ID: " + bookId + " not found."));
        existingBookRecord.setName(book.getName());
        existingBookRecord.setSummary(book.getSummary());
        existingBookRecord.setRating(book.getRating());

        return existingBookRecord;
    }

    @Override
    public void deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalStateException("Book with ID: " + bookId + " not found."));

        bookRepository.delete(book);
    }
}
