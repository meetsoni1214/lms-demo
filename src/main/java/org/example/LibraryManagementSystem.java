package org.example;

import org.example.model.Book;

import java.util.ArrayList;
import java.util.List;

public class LibraryManagementSystem {
    public static final List<Book> availableBooks = new ArrayList<>();
    public static final List<Book> borrowedBooks = new ArrayList<>();
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
    public void addBook(Book book) throws IllegalArgumentException {
        for (Book availableBook : availableBooks) {
            if (availableBook.getISBN().equals(book.getISBN())) {
                throw new IllegalArgumentException("Book cannot be added as there is already a book added with ISBN: " + book.getISBN());
            }
        }
        availableBooks.add(book);
        System.out.println("Book with ISBN " + book.getISBN() + " added successfully!");
    }
    public void addBooks(List<Book> books) throws IllegalArgumentException {
        for (Book book : books) {
            for (Book availableBook : availableBooks) {
                if (availableBook.getISBN().equals(book.getISBN()))  {

                }
            }
        }
        availableBooks.addAll(books);
    }
    public void borrowBook(String ISBN) throws IllegalArgumentException {
        for (Book book : availableBooks) {
            if(book.getISBN().equals(ISBN)) {
                availableBooks.remove(book);
                borrowedBooks.add(book);
                System.out.println("Book with ISBN " + ISBN + " borrowed Successfully!");
                return;
            }
        }
        throw new IllegalArgumentException("Sorry the book with ISBN " + ISBN +  " is not available!");
    }
    public void returnBook(String ISBN) throws IllegalArgumentException {
        for (Book book : borrowedBooks) {
            if(book.getISBN().equals(ISBN)) {
                borrowedBooks.remove(book);
                availableBooks.add(book);
                System.out.println("Book with ISBN " + ISBN + " returned Successfully!");
                return;
            }
        }
        throw new IllegalArgumentException("You are returning the wrong book with ISBN as: " + ISBN);
    }
}
