package com.librarymanagement.model;

public record LibraryUser(int userId, String name, String email, UserRole role) {
}
