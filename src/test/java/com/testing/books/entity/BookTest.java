package com.testing.books.entity;

import com.testing.books.entity.Book;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class BookTest {

    @Test
    public void testBookConstructorAndGetters() {
        Book book = Book.builder()
                .bookId(1L)
                .name("Atomic Habits")
                .summary("How to build better habits")
                .rating(5.0)
                .build();

        // Assert values using getters
        assertEquals(1L, book.getBookId());
        assertEquals("Atomic Habits", book.getName());
        assertEquals("How to build better habits", book.getSummary());
        assertEquals(5.0, book.getRating());
    }

    @Test
    public void testBookSetters() {
        Book book = new Book();
        book.setBookId(2L);
        book.setName("The Power of Habit");
        book.setSummary("A guide to understanding how habits work");
        book.setRating(4.5);

        // Assert values using getters
        assertEquals(2L, book.getBookId());
        assertEquals("The Power of Habit", book.getName());
        assertEquals("A guide to understanding how habits work", book.getSummary());
        assertEquals(4.5, book.getRating());
    }

    @Test
    public void testBookEquality() {
        Book book1 = Book.builder()
                .bookId(1L)
                .name("Atomic Habits")
                .summary("How to build better habits")
                .rating(5.0)
                .build();

        Book book2 = Book.builder()
                .bookId(1L)
                .name("Atomic Habits")
                .summary("How to build better habits")
                .rating(5.0)
                .build();

        assertEquals(book1, book2); // This checks for equality based on the @Data annotation
    }

    @Test
    public void testBookNotEqual() {
        Book book1 = Book.builder()
                .bookId(1L)
                .name("Atomic Habits")
                .summary("How to build better habits")
                .rating(5.0)
                .build();

        Book book2 = Book.builder()
                .bookId(2L)
                .name("The Power of Habit")
                .summary("A guide to understanding how habits work")
                .rating(4.5)
                .build();

        assertNotEquals(book1, book2); // They should not be equal
    }
}
