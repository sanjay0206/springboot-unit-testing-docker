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
	CommandLineRunner commandLineRunner (BookRepository bookRepository) {
		return args -> {
			Book RECORD_1 = new Book(1L, "Atomic Habits", "How to build better habits", 5);
			Book RECORD_2 = new Book(2L, "Think Fast and Slow", "How to create good mental models about thinking", 4);
			Book RECORD_3 = new Book(3L, "Grokking Algorithms", "Learn algorithms the fun way", 5);

			bookRepository.saveAll(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));
		};
	}
}
