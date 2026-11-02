# Library Management System

A Java 21 console application using JDBC and MySQL. It provides create, list, find, update, and delete operations for books, users, and borrow records in `LibraryDB`.

## Project structure

```text
src/
  main/
    java/com/librarymanagement/
      app/Main.java
      config/DatabaseConnection.java
      dao/BookDao.java
      dao/UserDao.java
      dao/BorrowRecordDao.java
      model/Book.java
      model/LibraryUser.java
      model/BorrowRecord.java
    resources/schema.sql
pom.xml
```

## Setup

1. If you already created and populated the `LibraryDB` database, do not rerun the seed inserts. Otherwise, run `src/main/resources/schema.sql` once in MySQL Workbench or the MySQL command line to create the tables and sample data.
2. The default URL is `jdbc:mysql://localhost:3306/LibraryDB` and the default user is `root`. Set `DB_PASSWORD` before running; credentials are read from environment variables and are not stored in source. Set `DB_URL` or `DB_USER` too if your connection differs.
3. From the project root, run:

   ```text
   mvn clean compile exec:java
   ```

The main menu has separate Books, Users, and Borrow Records sections. Each section supports add, list, find by ID, update, and delete using your existing table columns. Enter dates as `YYYY-MM-DD`, enum values as `LIBRARIAN`/`MEMBER` and `BORROWED`/`RETURNED`, and leave the return date blank if a book has not been returned. Borrow records must reference existing user and book IDs. MySQL foreign keys prevent deleting books or users that still have borrow records; remove or update those records first. Database CRUD uses prepared statements and closes JDBC resources automatically.
