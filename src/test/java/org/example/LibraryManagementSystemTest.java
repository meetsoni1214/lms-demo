package org.example;

import org.example.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LibraryManagementSystemTest {
    LibraryManagementSystem lms;
    public static final List<Book> availableBooks = LibraryManagementSystem.availableBooks;
    public static final List<Book> borrowedBooks = LibraryManagementSystem.borrowedBooks;

    @BeforeEach
    public void setUp() {
        lms = new LibraryManagementSystem();
    }

    @Test
    public void addBookTest() {
        Book book = new Book("title", "987-123-123-9876", "author", 2004);
        // number of books before adding
        int noOfBooks = availableBooks.size();
        lms.addBook(book);
        assertEquals(noOfBooks + 1, availableBooks.size());
        assertTrue(availableBooks.contains(book));
    }

    @Test
    public void addBookWithDuplicateISBNTest() {
        Book book1 = new Book("title", "987-123-123-9876", "author", 2004);
        Book book2 = new Book("title", "987-123-123-9876", "author", 2004);

        lms.addBook(book1);
        assertThrows(IllegalArgumentException.class, () -> {
            lms.addBook(book2);
        }, "Adding a book with duplicate ISBN should thrown an IllegalArgumentException");
    }

    @Test
    public void addAllBookTest() {

    }

    @Test
    public void viewAllBooksTest() {

    }
}