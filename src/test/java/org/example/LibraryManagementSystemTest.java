package org.example;

import org.example.model.Book;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LibraryManagementSystemTest {
    LibraryManagementSystem lms;
    public static final List<Book> availableBooks = LibraryManagementSystem.getAvailableBooks();
    public static final List<Book> borrowedBooks = LibraryManagementSystem.getBorrowedBooks();

    // Note: All the test cases are written in such a way that they can be tested independently and all together also
    @BeforeEach
    public void setUp() {
        lms = new LibraryManagementSystem();
    }

    @Test
    @Order(1)
    public void viewAvailableBooksWhenEmptyLibraryTest() {
        // Redirecting System.out to capture the output for assertions
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        // Test when no books are available
        lms.viewAvailableBooks();
        String expectedOutput = "Sorry, currently no books are available with us.";
        assertTrue(outContent.toString().contains(expectedOutput));
    }


    @Test
    public void viewAvailableBooksTest() {
        // Redirecting System.out to capture the output for assertions
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        // Add a single books
        lms.addBook(new Book("Atomic Habits", "234-234-234-1255", "James Clear", 2018));
        // Test When one book is available
        lms.viewAvailableBooks();
        String expectedOutput = "Following Books are available with us: \n";
        assertTrue(outContent.toString().contains(expectedOutput));
        assertTrue(outContent.toString().contains("Title: Atomic Habits"));
        assertTrue(outContent.toString().contains("Author: James Clear"));
        assertTrue(outContent.toString().contains("ISBN: 234-234-234-1255"));
        assertTrue(outContent.toString().contains("PublicationYear: 2018"));
    }

    @Test
    public void addBookWithEmptyTitleTest() {
        Book book = new Book("", "133-123-123-9875", "author", 2004);
        assertThrows(IllegalArgumentException.class, () -> lms.addBook(book),
                "Adding a book with empty title should throw an IllegalArgumentException");
    }

    @Test
    public void addBookWithNullTitleTest() {
        Book book = new Book(null, "132-123-123-9875", "author", 2004);
        assertThrows(IllegalArgumentException.class, () -> lms.addBook(book),
                "Adding a book with null title should throw an IllegalArgumentException");
    }

    @Test
    public void addBookWithEmptyAuthorTest() {
        Book book = new Book("title", "134-123-123-9875", "", 2004);
        assertThrows(IllegalArgumentException.class, () -> lms.addBook(book),
                "Adding a book with empty author name should throw an IllegalArgumentException");
    }

    @Test
    public void addBookWithNullAuthorTest() {
        Book book = new Book("title", "135-123-123-9875", null, 2004);
        assertThrows(IllegalArgumentException.class, () -> lms.addBook(book),
                "Adding a book with null author name should throw an IllegalArgumentException");
    }

    @Test
    public void addBookWithFuturePublicationYearTest() {
        Book book = new Book("title", "136-123-123-9875", "author", 2030);
        assertThrows(IllegalArgumentException.class, () -> lms.addBook(book),
                "Adding a book having publication year >" + Year.now() + " should throw an IllegalArgumentException");
    }

    @Test
    public void addBookWithVeryOldPublicationYearTest() {
        Book book = new Book("title", "137-123-123-9875", "author", 2);
        assertThrows(IllegalArgumentException.class, () -> lms.addBook(book),
                "Adding a book having publication year < 100 should throw an IllegalArgumentException");
    }

    @Test
    public void addBookWithNullISBNTest() {
        Book book = new Book("title", null, "author", 2000);
        assertThrows(IllegalArgumentException.class, () -> lms.addBook(book),
                "Adding a book having null ISBN should throw an IllegalArgumentException");
    }

    @Test
    public void addBookWithImproperLengthISBNTest() {
        Book book = new Book("title", "123", "author", 2000);
        assertThrows(IllegalArgumentException.class, () -> lms.addBook(book),
                "Adding a book having length != 13 should throw an IllegalArgumentException");
    }

    @Test
    public void addBookWithDuplicateISBNTest() {
        Book book1 = new Book("title", "987-123-123-9875", "author", 2004);
        Book book2 = new Book("title", "987-123-123-9875", "author", 2004);
        lms.addBook(book1);
        assertThrows(IllegalArgumentException.class, () -> lms.addBook(book2),
                "Adding a book with duplicate ISBN should thrown an IllegalArgumentException");
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

//    @Test
//    public void addAllBooksTest() {
//        List<Book> books = new ArrayList<>();
//        books.add(new Book("Atomic Habits", "234-234-234-2344", "James Clear", 2018));
//        books.add(new Book("The Alchemist", "345-345-345-3455", "Paulo Coelho", 1988));
//        // number of books before adding
//        int noOfBooks = availableBooks.size();
//        lms.addBooks(books);
//        assertEquals(noOfBooks + books.size(), availableBooks.size());
//        assertTrue(availableBooks.containsAll(books));
//    }

//    @Test
//    public void addAllBooksWithDuplicateISBNTest() {
//        lms.addBook(new Book("Atomic Habits", "234-234-234-2345", "James Clear", 2018));
//        List<Book> books = new ArrayList<>();
//        books.add(new Book("Atomic Habits", "234-234-234-2345", "James Clear", 2018));
//        books.add(new Book("The Alchemist", "345-345-345-3456", "Paulo Coelho", 1988));
//        assertThrows(IllegalArgumentException.class, () -> lms.addBooks(books),
//                "Adding a list of books that contains a book with duplicate ISBN should throw an IllegalArgumentException");
//    }

    @Test
    public void borrowAvailableBookTest() {
        Book book = new Book("title", "987-123-123-9876", "author", 2004);
        // Add a single books
        lms.addBook(book);
        // available books before borrowing
        int noOfAvailableBooks = availableBooks.size();
        // borrowed books before borrowing
        int noOfBorrowedBooks = borrowedBooks.size();
        lms.borrowBook("987-123-123-9876");
        assertEquals(noOfAvailableBooks - 1, availableBooks.size());
        assertEquals(noOfBorrowedBooks + 1, borrowedBooks.size());
        assertTrue(borrowedBooks.contains(book));
        assertFalse(availableBooks.contains(book));
    }

    @Test
    public void borrowUnavailableBookTest() {
        Book book = new Book("title", "987-123-123-9811", "author", 2004);
        // Add a single books
        lms.addBook(book);
        assertThrows(IllegalArgumentException.class, () -> lms.borrowBook("654-987-987-9812"),
                "Trying to borrow an unavailable book should thrown an IllegalArgumentException");
    }

    @Test
    public void returnBorrowedBookTest() {
        Book book = new Book("title", "987-123-123-0000", "author", 2004);
        // Add a single books
        lms.addBook(book);
        // borrow that book
        lms.borrowBook("987-123-123-0000");
        // available books before returning
        int noOfAvailableBooks = availableBooks.size();
        // borrowed books before returning
        int noOfBorrowedBooks = borrowedBooks.size();
        lms.returnBook("987-123-123-0000");
        assertEquals(noOfAvailableBooks + 1, availableBooks.size());
        assertEquals(noOfBorrowedBooks - 1, borrowedBooks.size());
        assertTrue(availableBooks.contains(book));
        assertFalse(borrowedBooks.contains(book));
    }

    @Test
    public void returnWrongBookTest() {
        Book book = new Book("title", "987-123-123-1111", "author", 2004);
        // Add a single books
        lms.addBook(book);
        // borrow that book
        lms.borrowBook("987-123-123-1111");
        assertThrows(IllegalArgumentException.class, () -> lms.returnBook("789-789-789-7899"),
                "Trying to return a wrong book which is not yet borrowed should throw an IllegalArgumentException");
    }
}