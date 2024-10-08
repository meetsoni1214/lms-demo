package org.example;

import org.example.model.Book;

import java.time.Year;
import java.util.*;

public class LibraryManagementSystem {
    private static final List<Book> availableBooks = new ArrayList<>();
    private static final List<Book> borrowedBooks = new ArrayList<>();

    // Declaring this methods so that other classes can only access both the lists in a read-only manner
    public static List<Book> getAvailableBooks() {
        return Collections.unmodifiableList(availableBooks);
    }
    public static List<Book> getBorrowedBooks() {
        return Collections.unmodifiableList(borrowedBooks);
    }
    public void viewAvailableBooks() {
        if (availableBooks.isEmpty()) {
            System.out.println("Sorry, currently no books are available with us.");
            return;
        }
        System.out.println("Following Books are available with us: \n");
        for (Book book : availableBooks) {
            System.out.println(
                    "Title: " + book.getTitle() + "\n"
                            + "Author: " + book.getAuthor() + "\n"
                            + "PublicationYear: " + book.getPublicationYear() + "\n"
                            + "ISBN: " + book.getISBN() + "\n");
        }
    }
    // Note: In both addBook and addBooks methods user should not able to add book with duplicate ISBN as in assessment it is specified that ISBN is a unique property of book.
    public void addBook(Book book) throws IllegalArgumentException {
        if (validateTitle(book.getTitle()) && validateAuthor(book.getAuthor()) && validatePublicationYear(book.getPublicationYear())
        && validateISBN(book.getISBN())) {
            availableBooks.add(book);
            System.out.println("Book with ISBN " + book.getISBN() + " added successfully!");
        }
    }
//    public void addBooks(List<Book> books) throws IllegalArgumentException {
//        Set<String> existingISBNs = new HashSet<>();
//        // adding all ISBNs of the available Books to the set
//        for (Book availableBook : availableBooks) {
//            existingISBNs.add(availableBook.getISBN());
//        }
//        for (Book book : books) {
//            if (validateTitle(book.getTitle()) && validateAuthor(book.getAuthor()) && validatePublicationYear(book.getPublicationYear())) {
//                // validating ISBN
//                String ISBN = book.getISBN();
//                if (ISBN == null || ISBN.length() != 16) throw new IllegalArgumentException("ISBN cannot be null, or it must have length >= 13!");
//                if (existingISBNs.contains(book.getISBN())) {
//                    throw new IllegalArgumentException("Book cannot be added as there is already a book added with ISBN: " + book.getISBN());
//                }
//                availableBooks.add(book);
//                System.out.println("Book with ISBN " + ISBN + " added successfully!");
//            }
//        }
//    }
    public void borrowBook(String ISBN) throws IllegalArgumentException {
        // Using Iterator to safely and efficiently remove the book from the list while iterating it at the same time
        Iterator<Book> iterator = availableBooks.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getISBN().equals(ISBN)) {
                iterator.remove();
                borrowedBooks.add(book);
                System.out.println("Book with ISBN " + ISBN + " borrowed Successfully!");
                return;
            }
        }
        throw new IllegalArgumentException("Sorry the book with ISBN " + ISBN + " is not available!");
    }
    public void returnBook(String ISBN) throws IllegalArgumentException {
        // Using Iterator to safely and efficiently remove the book from the list while iterating it at the same time
        Iterator<Book> iterator = borrowedBooks.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getISBN().equals(ISBN)) {
                iterator.remove();
                availableBooks.add(book);
                System.out.println("Book with ISBN " + ISBN + " returned Successfully!");
                return;
            }
        }
        throw new IllegalArgumentException("You are returning the wrong book with ISBN as: " + ISBN);
    }
    private boolean validateTitle(String title) throws IllegalArgumentException{
        if (title == null || title.isEmpty()) throw new IllegalArgumentException("Book title cannot be empty or null!");
        return true;
    }
    private boolean validateAuthor(String author) throws IllegalArgumentException {
        if (author == null || author.isEmpty()) throw new IllegalArgumentException("Book Author cannot be empty or null!");
        return true;
    }
    private boolean validateISBN(String ISBN) throws IllegalArgumentException {
        if (ISBN == null || ISBN.length() != 16) throw new IllegalArgumentException("ISBN cannot be null or it must have length >= 13!");

        for (Book availableBook : availableBooks) {
            if (availableBook.getISBN().equals(ISBN)) {
                throw new IllegalArgumentException("Book cannot be added as there is already a book added with ISBN: " + ISBN);
            }
        }
        return true;
    }
    private boolean validatePublicationYear(int year) {
        // considering there are no books in the library older than this year ;)
        if (year > Year.now().getValue() || year < 100) throw new IllegalArgumentException("Publication year must be between the range of 100 to " + Year.now());
        return true;
    }
}
