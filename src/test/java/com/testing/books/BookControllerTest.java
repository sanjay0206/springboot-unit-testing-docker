package com.testing.books;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {
    private MockMvc mockMvc;
    ObjectMapper mapper = new ObjectMapper();

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookController bookController;


    Book RECORD_1 = new Book(1L, "Atomic Habits", "How to build better habits", 5);
    Book RECORD_2 = new Book(2L, "Think Fast and Slow", "How to create good mental models about thinking", 4);
    Book RECORD_3 = new Book(3L, "Grokking Algorithms", "Learn algorithms the fun way", 5);


    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void getAllRecords_success() throws Exception {
        List<Book> records = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));

        Mockito.when(bookRepository.findAll())
                .thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].name", is("Grokking Algorithms")));
    }

    @Test
    public void getBookById_success() throws Exception {

        Mockito.when(bookRepository.findById(RECORD_1.getBookId()))
                .thenReturn(Optional.of(RECORD_1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Atomic Habits")));
    }

    @Test
    public void createRecord_success() throws Exception {
        Book record =  Book.builder()
                .bookId(4L)
                .name("Introduction to C")
                .summary("The name but longer")
                .rating(5)
                .build();

        Mockito.when(bookRepository.save(record))
                .thenReturn(record);

        String content = mapper.writeValueAsString(record);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/books/create-book")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Introduction to C")));
    }



    @Test
    public void deleteBook_success() throws Exception {
        Mockito.when(bookRepository.existsById(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/books/delete-book/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteBook_notFound() throws Exception {
        Mockito.when(bookRepository.existsById(anyLong())).thenReturn(false);

        mockMvc.perform(delete("/books/delete-book/1"))
                .andExpect(status().isNotFound());
    }
}

