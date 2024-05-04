package com.testing.books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // Get all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBookRecords() {
        List<Book> books = bookService.getAllBookRecords();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // Get a book by ID
    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable("bookId") Long bookId) {
        Book book = bookService.getBookById(bookId);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    // Create a new book
    @PostMapping("/create-book")
    public ResponseEntity<Book> createBookRecord(@RequestBody Book book) {
        Book savedBook = bookService.createBookRecord(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    // Update a book
    @PutMapping("/update-book/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable Long bookId, @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(bookId, book);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    // Delete a book
    @DeleteMapping("/delete-book/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable(value = "bookId") Long bookId) {
        bookService.deleteBook(bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
