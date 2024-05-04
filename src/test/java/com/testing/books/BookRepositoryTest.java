package com.testing.books;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;

@RunWith(MockitoJUnitRunner.class)
public class BookRepositoryTest {

    @Mock
    private BookRepository bookRepository;

    private final Book RECORD_1 = new Book(1L, "Atomic Habits", "How to build better habits", 5.0);
    private final Book RECORD_2 = new Book(2L, "Think Fast and Slow", "How to create good mental models about thinking", 4.0);
    private final Book RECORD_3 = new Book(3L, "Grokking Algorithms", "Learn algorithms the fun way", 4.5);

    @Test
    public void testFindAll() {
        List<Book> records = Arrays.asList(RECORD_1, RECORD_2, RECORD_3);
        Mockito.when(bookRepository.findAll()).
                thenReturn(records);

        List<Book> result = bookRepository.findAll();

        assertEquals(3, result.size());
        assertEquals(RECORD_1.getName(), result.get(0).getName());
        assertEquals(RECORD_2.getName(), result.get(1).getName());
        assertEquals(RECORD_3.getName(), result.get(2).getName());
    }

    @Test
    public void testFindById() {
        Mockito.when(bookRepository.findById(RECORD_1.getBookId()))
                .thenReturn(Optional.of(RECORD_1));

        Optional<Book> result = bookRepository.findById(RECORD_1.getBookId());

        assertTrue(result.isPresent());
        assertEquals(RECORD_1.getName(), result.get().getName());
    }

    @Test
    public void testSave() {
        Book record =  Book.builder()
                .bookId(4L)
                .name("Introduction to C")
                .summary("The name but longer")
                .rating(5.0)
                .build();
        Mockito.when(bookRepository.save(record)).thenReturn(record);

        Book result = bookRepository.save(record);

        assertEquals(record.getName(), result.getName());
        assertEquals(record.getSummary(), result.getSummary());
        assertEquals(record.getRating(), result.getRating());
    }

    @Test
    public void testExistsById() {
        Mockito.when(bookRepository.existsById(anyLong())).thenReturn(true);

        boolean result = bookRepository.existsById(1L);

        assertTrue(result);
    }
}
