CREATE TABLE books (
    book_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    summary TEXT,
    rating NUMERIC(3, 1) -- or use FLOAT if preferred
);

INSERT INTO books (book_id, name, summary, rating) VALUES
(1, 'Atomic Habits', 'How to build better habits', 5.0),
(2, 'Think Fast and Slow', 'How to create good mental models about thinking', 4.5),
(3, 'Grokking Algorithms', 'Learn algorithms the fun way', 5.0),
(4, 'Cooking Fundamentals', 'Explores essential cooking principles and techniques to elevate your culinary skills.', 5.0),
(5, 'Science of Cooking', 'Delves into the scientific aspects of cooking, providing insights to enhance your culinary expertise.', 4.5),
(6, 'Italian Cuisine', 'Showcases classic Italian recipes and culinary traditions, offering a taste of Italy\'s rich culinary heritage.', 4.0);


CREATE OR REPLACE FUNCTION get_count_of_books(OUT total NUMERIC)
LANGUAGE plpgsql
AS $$
BEGIN
    SELECT count(*) INTO total FROM books;
END;
$$;

select get_count_of_books()