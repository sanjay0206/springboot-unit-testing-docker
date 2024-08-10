package com.testing.books.exception;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BookNotFoundExceptionTest {

    @Test
    public void testBookNotFoundExceptionMessage() {
        // Arrange
        String resourceName = "Book";
        String fieldName = "id";
        long fieldValue = 12L;

        // Act
        BookNotFoundException exception = new BookNotFoundException(resourceName, fieldName, fieldValue);

        // Assert
        assertEquals("Book not found with id : 123", exception.getMessage());
    }

    @Test
    public void testBookNotFoundExceptionFields() {
        // Arrange
        String resourceName = "Book";
        String fieldName = "id";
        long fieldValue = 5L;

        // Act
        BookNotFoundException exception = new BookNotFoundException(resourceName, fieldName, fieldValue);

        // Assert
        assertEquals(resourceName, exception.getResourceName());
        assertEquals(fieldName, exception.getFieldName());
        assertEquals(fieldValue, exception.getFieldValue());
    }

    @Test(expected = BookNotFoundException.class)
    public void testBookNotFoundExceptionIsRuntimeException() {
        // Arrange
        String resourceName = "Book";
        String fieldName = "id";
        long fieldValue = 12L;

        // Act
        throw new BookNotFoundException(resourceName, fieldName, fieldValue);
    }
}
