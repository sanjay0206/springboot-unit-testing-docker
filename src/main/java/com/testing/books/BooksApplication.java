package com.testing.books;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class BooksApplication {

    public static void main(String[] args) {
        SpringApplication.run(BooksApplication.class, args);
        System.out.println("Book app is running...");
    }

    @Bean
    CommandLineRunner commandLineRunner(BookRepository bookRepository) {
        return args -> {
            Book RECORD_1 = new Book(1L, "Atomic Habits", "How to build better habits", 5.0);
            Book RECORD_2 = new Book(2L, "Think Fast and Slow", "How to create good mental models about thinking", 4.5);
            Book RECORD_3 = new Book(3L, "Grokking Algorithms", "Learn algorithms the fun way", 5.0);

            Book RECORD_4 = new Book(4L, "Cooking Fundamentals", "Explores essential cooking principles and techniques to elevate your culinary skills.", 5.0);
            Book RECORD_5 = new Book(5L, "Science of Cooking", "Delves into the scientific aspects of cooking, providing insights to enhance your culinary expertise.", 4.5);
            Book RECORD_6 = new Book(6L, "Italian Cuisine", "Showcases classic Italian recipes and culinary traditions, offering a taste of Italy's rich culinary heritage.", 4.0);

            bookRepository.saveAll(Arrays.asList(RECORD_1, RECORD_2, RECORD_3, RECORD_4, RECORD_5, RECORD_6));
        };
    }
}
