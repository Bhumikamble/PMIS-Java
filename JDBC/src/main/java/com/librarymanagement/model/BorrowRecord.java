package com.librarymanagement.model;

import java.time.LocalDate;

public record BorrowRecord(
        int recordId,
        int memberId,
        int bookId,
        LocalDate borrowDate,
        LocalDate dueDate,
        LocalDate returnDate,
        BorrowStatus status
) {
}
