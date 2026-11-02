CREATE DATABASE IF NOT EXISTS LibraryDB;
USE LibraryDB;

CREATE TABLE IF NOT EXISTS Books (
    book_id INT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    author VARCHAR(100) NOT NULL,
    category VARCHAR(50),
    total_copies INT NOT NULL,
    available_copies INT NOT NULL
);

INSERT INTO Books (book_id, title, author, category, total_copies, available_copies) VALUES
(101, 'Java Programming', 'Herbert Schildt', 'Programming', 5, 5),
(102, 'Python Crash Course', 'Eric Matthes', 'Programming', 4, 4),
(103, 'Clean Code', 'Robert C. Martin', 'Software Engineering', 3, 3),
(104, 'Database System Concepts', 'Abraham Silberschatz', 'Database', 4, 4),
(105, 'Introduction to Algorithms', 'Thomas H. Cormen', 'Algorithms', 2, 2),
(106, 'The Alchemist', 'Paulo Coelho', 'Fiction', 6, 6),
(107, 'Wings of Fire', 'A. P. J. Abdul Kalam', 'Biography', 3, 3),
(108, 'Artificial Intelligence', 'Stuart Russell', 'AI and ML', 2, 2),
(109, 'Web Development with HTML and CSS', 'Jon Duckett', 'Web Development', 4, 4),
(110, 'Operating System Concepts', 'Abraham Silberschatz', 'Operating Systems', 3, 3);

CREATE TABLE IF NOT EXISTS Users (
    user_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role ENUM('LIBRARIAN', 'MEMBER') NOT NULL
);

INSERT INTO Users (user_id, name, email, role) VALUES
(1, 'Priya Sharma', 'priya@library.com', 'LIBRARIAN'),
(2, 'Rahul Patil', 'rahul@gmail.com', 'MEMBER'),
(3, 'Bhumi Kamble', 'bhumi@gmail.com', 'MEMBER'),
(4, 'Sneha Joshi', 'sneha@gmail.com', 'MEMBER'),
(5, 'Amit Desai', 'amit@gmail.com', 'MEMBER'),
(6, 'Neha Shah', 'neha@gmail.com', 'MEMBER');

CREATE TABLE IF NOT EXISTS BorrowRecords (
    record_id INT PRIMARY KEY,
    member_id INT NOT NULL,
    book_id INT NOT NULL,
    borrow_date DATE NOT NULL,
    due_date DATE NOT NULL,
    return_date DATE,
    status ENUM('BORROWED', 'RETURNED') NOT NULL,
    FOREIGN KEY (member_id) REFERENCES Users(user_id),
    FOREIGN KEY (book_id) REFERENCES Books(book_id)
);

INSERT INTO BorrowRecords
    (record_id, member_id, book_id, borrow_date, due_date, return_date, status) VALUES
(1, 2, 101, '2026-10-18', '2026-11-01', NULL, 'BORROWED'),
(2, 3, 102, '2026-10-20', '2026-11-03', NULL, 'BORROWED'),
(3, 4, 106, '2026-10-05', '2026-10-19', '2026-10-17', 'RETURNED'),
(4, 5, 104, '2026-10-21', '2026-11-04', NULL, 'BORROWED'),
(5, 6, 103, '2026-10-10', '2026-10-24', NULL, 'BORROWED');
