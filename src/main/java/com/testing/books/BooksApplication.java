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

            Book RECORD_4 = new Book(4L, "Salt, Fat, Acid, Heat: Mastering the Elements of Good Cooking", "Teaches the fundamentals of cooking", 5.0);
            Book RECORD_5 = new Book(5L, "The Food Lab: Better Home Cooking Through Science", "Applies science to improve cooking techniques", 4.5);
            Book RECORD_6 = new Book(6L, "Essentials of Classic Italian Cooking", "Classic Italian recipes and techniques", 4.0);

            Book RECORD_7 = new Book(7L, "The Inner Game of Tennis", "Explains the mental aspect of tennis", 4.5);
            Book RECORD_8 = new Book(8L, "Bounce: The Myth of Talent and the Power of Practice", "Explores the science of talent and practice", 4.0);
            Book RECORD_9 = new Book(9L, "The Boys in the Boat: Nine Americans and Their Epic Quest for Gold at the 1936 Berlin Olympics", "Story of the U.S. rowing team at the 1936 Olympics", 5.0);
            Book RECORD_10 = new Book(10L, "Light on Yoga", "Classic guide to yoga practice and philosophy", 5.0);

            bookRepository.saveAll(Arrays.asList(RECORD_1, RECORD_2, RECORD_3, RECORD_4, RECORD_5, RECORD_6, RECORD_7, RECORD_8, RECORD_9, RECORD_10));
        };
    }
}
