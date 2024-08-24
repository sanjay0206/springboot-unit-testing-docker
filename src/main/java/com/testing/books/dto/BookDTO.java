package com.testing.books.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BookDTO {
    private Long bookId;
    private String name;
    private String summary;
    private Double rating;
}
