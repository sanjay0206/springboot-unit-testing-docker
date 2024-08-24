package com.testing.books.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing.books.controller.BookController;
import com.testing.books.dto.BookDTO;
import com.testing.books.entity.Book;
import com.testing.books.service.BookService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private Book book;

    private List<Book> books;

    private ObjectMapper mapper;

    @Before
    public void setup() {
        mapper = new ObjectMapper();

        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();

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
    public void test_getAllBookRecords() throws Exception {
        when(bookService.getAllBookRecords()).thenReturn(books);

        mockMvc.perform(get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].name").value("Atomic Habits"))
                .andExpect(jsonPath("$[1].name").value("Grokking Algorithms"))
                .andExpect(jsonPath("$[2].name").value("Think Fast and Slow"));

        verify(bookService, Mockito.times(1)).getAllBookRecords();
    }

    @Test
    public void test_getBookById() throws Exception {
        long bookId = 1L;
        when(bookService.getBookById(bookId)).thenReturn(book);

        mockMvc.perform(get("/books/{bookId}", bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Atomic Habits"));

        verify(bookService, Mockito.times(1)).getBookById(bookId);
    }

    @Test
    public void test_createBookRecord() throws Exception {
        when(bookService.createBookRecord(book)).thenReturn(book);

        mockMvc.perform(post("/books/create-book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Atomic Habits"));

        verify(bookService, Mockito.times(1)).createBookRecord(book);
    }

    @Test
    public void test_updateBook() throws Exception {
        long bookId = 1L;
        BookDTO bookDTO = BookDTO.builder().rating(4.5).build();
        Book updatedBook = Book.builder()
                .bookId(bookId)
                .name("Atomic Habits")
                .summary("How to build better habits")
                .rating(4.5)
                .build();

        when(bookService.updateBook(bookId, bookDTO)).thenReturn(updatedBook);

        mockMvc.perform(put("/books/update-book/{bookId}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(bookDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating").value(4.5));

        verify(bookService, Mockito.times(1)).updateBook(bookId, bookDTO);
    }

    @Test
    public void test_deleteBook() throws Exception {
        long bookId = 1L;

        doNothing().when(bookService).deleteBook(bookId);

        mockMvc.perform(delete("/books/delete-book/{bookId}", bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(bookService, Mockito.times(1)).deleteBook(bookId);
    }

    @After
    public void tearDown() {
        Mockito.reset(bookService);
    }

}
