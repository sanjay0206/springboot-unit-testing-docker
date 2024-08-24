package com.testing.books.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
@NamedStoredProcedureQuery(
        name = "getBooksCountNamedSPQuery",
        procedureName = "get_count_of_books",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "total", type = BigDecimal.class)
        }
)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_sequence")
    @SequenceGenerator(name = "book_sequence", sequenceName = "book_seq", allocationSize = 1)
    private Long bookId;

    private String name;

    private String summary;

    private Double rating;
}
