package com.testing.books.service;

import com.testing.books.dto.BookDTO;
import com.testing.books.entity.Book;
import com.testing.books.exception.BookNotFoundException;
import com.testing.books.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
 //   @Transactional
    public BigDecimal getCountOfAllBooks() {
        return bookRepository.get_count_of_books();
    }

    @Override
    public List<Book> getAllBookRecords() {
        return  bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book" , "bookId", bookId));
    }

    @Override
    public Book createBookRecord(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long bookId, BookDTO bookDTO) {
        Book existingBookRecord = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book" , "bookId", bookId));

        if (bookDTO.getName() != null)
            existingBookRecord.setName(bookDTO.getName());

        if (bookDTO.getSummary() != null)
            existingBookRecord.setSummary(bookDTO.getSummary());

        if (bookDTO.getRating() != null)
            existingBookRecord.setRating(bookDTO.getRating());

        return bookRepository.save(existingBookRecord);
    }

    @Override
    public void deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book" , "bookId", bookId));

        bookRepository.delete(book);
    }
}
