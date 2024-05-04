package com.testing.books;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing.books.exception.BookNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Annotation to use Mockito's JUnit runner
@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {
    private MockMvc mockMvc;

    // ObjectMapper to serialize/deserialize JSON
    ObjectMapper mapper = new ObjectMapper();

    // Mocking the BookService dependency
    @Mock
    private BookService bookService;

    // Injecting mocks into BookController
    @InjectMocks
    private BookController bookController;

    // Sample book records for testing
    Book RECORD_1 = new Book(1L, "Atomic Habits", "How to build better habits", 5.0);
    Book RECORD_2 = new Book(2L, "Think Fast and Slow", "How to create good mental models about thinking", 4.0);
    Book RECORD_3 = new Book(3L, "Grokking Algorithms", "Learn algorithms the fun way", 5.0);

    // Setting up the mockMvc instance before each test
    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    // Testing the retrieval of all book records
    @Test
    public void getAllRecords_success() throws Exception {
        // Mocking the behavior of BookService to return a list of records
        List<Book> records = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));
        Mockito.when(bookService.getAllBookRecords())
                .thenReturn(records);

        // Performing a GET request and validating the response
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].name", is(RECORD_3.getName())))
                .andExpect(jsonPath("$[2].summary", is(RECORD_3.getSummary())))
                .andExpect(jsonPath("$[2].rating", is(RECORD_3.getRating())));
    }

    // Testing the retrieval of a book by ID
    @Test
    public void getBookById_success() throws Exception {
        // Mocking the behavior of BookService to return a specific record by ID
        long bookId = 2L;
        Mockito.when(bookService.getBookById(bookId))
                .thenReturn(RECORD_2);

        // Performing a GET request for a specific book ID and validating the response
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/books/" + bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is(RECORD_2.getName())))
                .andExpect(jsonPath("$.summary", is(RECORD_2.getSummary())))
                .andExpect(jsonPath("$.rating", is(RECORD_2.getRating())));
    }

    // Testing the creation of a book record
    @Test
    public void createRecord_success() throws Exception {
        // Creating a new book record
        Book RECORD4 =  Book.builder()
                .bookId(4L)
                .name("Introduction to C")
                .summary("The name but longer")
                .rating(5.0)
                .build();

        // Mocking the behavior of BookService to return the created record
        Mockito.when(bookService.createBookRecord(RECORD4))
                .thenReturn(RECORD4);

        // Converting the record to JSON and performing a POST request
        String content = mapper.writeValueAsString(RECORD4);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/books/create-book")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        // Validating the response after creating the record
        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is(RECORD4.getName())))
                .andExpect(jsonPath("$.summary", is(RECORD4.getSummary())))
                .andExpect(jsonPath("$.rating", is(RECORD4.getRating())));

    }

    // Testing the update of a book record
    @Test
    public void updateBook_success() throws Exception {
        // Updating an existing book record
        long bookId = 3L;
        Book updatedBook = new Book(3L, "Updated Title", "Updated Summary", 4.5);
        Mockito.when(bookService.updateBook(bookId, updatedBook)).thenReturn(updatedBook);

        // Converting the updated record to JSON and performing a PUT request
        String content = mapper.writeValueAsString(updatedBook);
        mockMvc.perform(MockMvcRequestBuilders.put("/books/update-book/" + bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is(updatedBook.getName())))
                .andExpect(jsonPath("$.summary", is(updatedBook.getSummary())))
                .andExpect(jsonPath("$.rating", is(updatedBook.getRating())));
    }

    // Testing the deletion of a book record
    @Test
    public void deleteBook_success() throws Exception {
        // Deleting a book record
        long bookId = 1L;
        mockMvc.perform(delete("/books/delete-book/" + bookId))
                .andExpect(status().isNoContent());
    }

    // Testing the scenario where a book is not found for retrieval/updation/deletion
    @Test
    public void book_notFound() throws Exception {
        // Simulating a scenario where the book to be deleted is not found
        long bookId = 11L;
        Mockito.doThrow(new BookNotFoundException("Book", "ID", bookId)).when(bookService).deleteBook(bookId);

        // Performing a delete request for a non-existing book ID and validating the response
        mockMvc.perform(delete("/books/delete-book/" + bookId))
                .andExpect(status().isNotFound());
    }
}
