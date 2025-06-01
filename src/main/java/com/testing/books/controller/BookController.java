package com.testing.books.controller;

import com.testing.books.dto.BookDTO;
import com.testing.books.service.BookService;
import com.testing.books.entity.Book;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/books")
@Tag(name = "Book Controller", description = "APIs for managing books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/count")
    @Operation(
            summary = "Get count of all books",
            description = "Returns the total count of books in the system"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved book count"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<BigDecimal> getCountOfAllBooks() {
        return new ResponseEntity<>(bookService.getCountOfAllBooks(), HttpStatus.OK);
    }

    @GetMapping
    @Operation(
            summary = "Get all books",
            description = "Returns a list of all books in the system"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all books"),
            @ApiResponse(responseCode = "404", description = "No books found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Book>> getAllBookRecords() {
        List<Book> books = bookService.getAllBookRecords();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{bookId}")
    @Operation(
            summary = "Get a book by ID",
            description = "Returns a single book identified by its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the book"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Book> getBookById(
            @Parameter(description = "ID of the book to be retrieved", required = true)
            @PathVariable("bookId") Long bookId) {
        Book book = bookService.getBookById(bookId);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping("/create-book")
    @Operation(
            summary = "Create a new book",
            description = "Creates a new book record in the system"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Successfully created the book"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Book> createBookRecord(
            @Parameter(description = "Book object that needs to be created", required = true)
            @RequestBody Book book) {
        Book savedBook = bookService.createBookRecord(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @PutMapping("/update-book/{bookId}")
    @Operation(
            summary = "Update an existing book",
            description = "Updates the details of an existing book"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully updated the book"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Book> updateBook(
            @Parameter(description = "ID of the book to be updated", required = true)
            @PathVariable("bookId") Long bookId,
            @Parameter(description = "Updated book object", required = true)
            @RequestBody BookDTO bookDTO) {
        Book updatedBook = bookService.updateBook(bookId, bookDTO);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @DeleteMapping("/delete-book/{bookId}")
    @Operation(
            summary = "Delete a book",
            description = "Deletes a book from the system"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Successfully deleted the book"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "ID of the book to be deleted", required = true)
            @PathVariable("bookId") Long bookId) {
        bookService.deleteBook(bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}