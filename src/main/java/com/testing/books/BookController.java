package com.testing.books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    // Get all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBookRecords() {
        List<Book> books = bookRepository.findAll();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // Get a book by ID
    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable("bookId") Long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        return bookOptional.map(book -> new ResponseEntity<>(book, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Create a new book
    @PostMapping("/create-book")
    public ResponseEntity<Book> createBookRecord(@RequestBody Book book) {
        Book savedBook = bookRepository.save(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    // Update a book
    @PutMapping("/update-book")
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        if (book == null || book.getBookId() == null) {
            throw new IllegalStateException("Book or ID should not be null.");
        }

        Optional<Book> optionalBook = bookRepository.findById(book.getBookId());
        if (optionalBook.isPresent()) {
            throw new IllegalStateException("Book with ID " + book.getBookId() + " does not exist.");
        }

        Book existingBookRecord = optionalBook.get();
        existingBookRecord.setName(book.getName());
        existingBookRecord.setSummary(book.getSummary());
        existingBookRecord.setRating(book.getRating());

        return new ResponseEntity<>(existingBookRecord, HttpStatus.OK);
    }

    // Delete a book
    @DeleteMapping("/delete-book/{bookId}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable(value = "bookId") Long bookId) {
        if (!bookRepository.existsById(bookId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookRepository.deleteById(bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
