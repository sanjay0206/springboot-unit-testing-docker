package com.testing.books;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing.books.exception.BookNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    // Mocking the BookService dependency
    @Mock
    private BookService bookService;

    // Injecting mocks into BookController
    @InjectMocks
    private BookController bookController;

    // Sample book records for testing
    private final Book BOOK_1 = new Book(1L, "Atomic Habits", "How to build better habits", 5.0);
    private final Book BOOK_2 = new Book(2L, "Think Fast and Slow", "How to create good mental models about thinking", 4.0);
    private final Book BOOK_3 = new Book(3L, "Grokking Algorithms", "Learn algorithms the fun way", 5.0);

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void getAllBooks_success() throws Exception {
        List<Book> records = Arrays.asList(BOOK_1, BOOK_2, BOOK_3);
        Mockito.when(bookService.getAllBookRecords()).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].name", is(BOOK_3.getName())))
                .andExpect(jsonPath("$[2].summary", is(BOOK_3.getSummary())))
                .andExpect(jsonPath("$[2].rating", is(BOOK_3.getRating())));
    }

    @Test
    public void getBookById_success() throws Exception {
        long bookId = 2L;
        Mockito.when(bookService.getBookById(bookId)).thenReturn(BOOK_2);

        mockMvc.perform(MockMvcRequestBuilders.get("/books/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(BOOK_2.getName())))
                .andExpect(jsonPath("$.summary", is(BOOK_2.getSummary())))
                .andExpect(jsonPath("$.rating", is(BOOK_2.getRating())));
    }

    @Test
    public void createBook_success() throws Exception {
        Book newBook = new Book(4L, "Introduction to C", "The name but longer", 5.0);
        Mockito.when(bookService.createBookRecord(newBook)).thenReturn(newBook);

        String requestBody = mapper.writeValueAsString(newBook);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/books/create-book")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(newBook.getName())))
                .andExpect(jsonPath("$.summary", is(newBook.getSummary())))
                .andExpect(jsonPath("$.rating", is(newBook.getRating())));
    }

    @Test
    public void updateBook_success() throws Exception {
        long bookId = 3L;
        BookDto bookDto = BookDto.builder().name("Updated Grokking Algorithms").build();
        BOOK_3.setName(bookDto.getName());

        Mockito.when(bookService.updateBook(bookId, bookDto)).thenReturn(BOOK_3);

        String requestBody = mapper.writeValueAsString(bookDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/books/update-book/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(bookDto.getName())));
    }

    @Test
    public void deleteBook_success() throws Exception {
        long bookId = 1L;
        Mockito.doNothing().when(bookService).deleteBook(bookId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/books/delete-book/{id}", bookId))
                .andExpect(status().isNoContent());

        Mockito.verify(bookService, Mockito.times(1)).deleteBook(bookId);
    }

    @Test
    public void deleteBook_notFound() throws Exception {
        long bookId = 11L;
        Mockito.doThrow(new BookNotFoundException("Book", "ID", bookId)).when(bookService).deleteBook(bookId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/books/delete-book/{id}", bookId))
                .andExpect(status().isNotFound());
    }
}
