package com.testing.books.repository;

import com.testing.books.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Using procedure name as the method name
    @Procedure
    BigDecimal get_count_of_books();

    // Using @Procedure with different method name.
    @Procedure(procedureName = "get_count_of_books")
    BigDecimal getBooksCountProcedure();

    // Using @Query annotation
    @Query(value = "SELECT get_count_of_books()", nativeQuery = true)
    BigDecimal getBooksCountNativeQuery();

    // Using @NamedStoredProcedureQuery annotation
    @Procedure(name = "getBooksCountNamedSPQuery")
    BigDecimal getBooksCountNamedSPQuery();
}
