package com.testing.books;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BookDto {
    private Long bookId;
    private String name;
    private String summary;
    private Double rating;
}
