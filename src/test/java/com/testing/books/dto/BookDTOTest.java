package com.testing.books.dto;

import com.testing.books.dto.BookDTO;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BookDTOTest {

    @Test
    public void testBookDTOBuilder() {
        BookDTO bookDTO = BookDTO.builder()
                .bookId(1L)
                .name("Atomic Habits")
                .summary("How to build better habits")
                .rating(5.0)
                .build();


        assertNotNull(bookDTO); // Assert that the object is created successfully
    }
}
