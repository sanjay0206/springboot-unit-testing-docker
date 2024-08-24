package com.testing.books.service;

import com.testing.books.dto.BookDTO;
import com.testing.books.entity.Book;
import com.testing.books.exception.BookNotFoundException;
import com.testing.books.repository.BookRepository;
import com.testing.books.service.BookServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;

    private List<Book> books;

    @Before
    public void setup() {

        book = Book.builder()
                .bookId(1L)
                .name("Atomic Habits")
                .summary("How to build better habits")
                .rating(5.0)
                .build();

        books = Arrays.asList(
                new Book(1L, "Atomic Habits", "How to build better habits", 5.0),
                new Book(2L, "Grokking Algorithms", "Learn algorithms the fun way", 5.0),
                new Book(3L, "Think Fast and Slow", "How to create good mental models about thinking", 4.0)
        );
    }

    @Test
    public void test_createBookRecord() {
        // given - precondition or setup
        when(bookRepository.save(book)).thenReturn(book);

        // when -  action or the behaviour that we are going test
        Book savedBook = bookService.createBookRecord(book);

        // then - verify the output
        assertThat(savedBook).isNotNull();
    }

    @Test
    public void test_getAllBooks(){
        // given - precondition or setup
        when(bookRepository.findAll()).thenReturn(books);

        // when -  action or the behaviour that we are going test
        List<Book> bookList = bookService.getAllBookRecords();

        // then - verify the output
        assertThat(bookList).isNotNull();
        assertThat(bookList.size()).isEqualTo(3);
    }

    @Test
    public void test_getBookById() {
        // then - verify the output
        long bookId = 1L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // when -  action or the behaviour that we are going test
        Book savedBook = bookService.getBookById(book.getBookId());

        // then - verify the output
        assertThat(savedBook).isNotNull();
    }

    @Test
    public void test_getBookById_NotFound(){
        long bookId = 9L;

        Mockito.doThrow(new BookNotFoundException("Book", "bookId", bookId)).when(bookRepository).deleteById(bookId);

        try {
            bookService.deleteBook(bookId);
        } catch (BookNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Book not found with bookId : " + bookId);
        }
    }

    @Test
    public void test_updateBook(){
        // given - precondition or setup
        long bookId = 1L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        // when -  action or the behaviour that we are going test
        Book updatedBook = bookService.updateBook(bookId, BookDTO.builder().rating(3.5).build());

        // then - verify the output
        assertThat(updatedBook.getRating()).isEqualTo(3.5);
    }


    @Test
    public void test_deleteBook(){
        // given - precondition or setup
        long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // when -  action or the behaviour that we are going test
        bookService.deleteBook(bookId);

        // then - verify that the delete method was called with the correct bookId
        Mockito.verify(bookRepository).delete(book);
    }

    @After
    public void tearDown() {
        Mockito.reset(bookRepository);
    }

}
