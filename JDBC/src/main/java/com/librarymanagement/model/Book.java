package com.librarymanagement.model;

public record Book(
        int bookId,
        String title,
        String author,
        String category,
        int totalCopies,
        int availableCopies
) {
}
