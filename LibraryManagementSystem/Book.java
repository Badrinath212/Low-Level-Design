package LibraryManagementSystem;

import java.util.Objects;

public class Book {
    private final String bookId;
    private final String title;
    private Author author;
    private volatile BookStatus status;

    public Book(String bookId, String title, Author author, BookStatus status) {
        this.bookId = Objects.requireNonNull(bookId, "bookId");
        this.title = Objects.requireNonNull(title, "title");
        this.author = Objects.requireNonNull(author, "author");
        this.status = Objects.requireNonNull(status, "status");
    }

    public boolean isAvailable() {
        return status == BookStatus.AVAILABLE;
    }

    public synchronized boolean borrow() {
        if (isAvailable()) {
            status = BookStatus.BORROWED;
            return true;
        }
        return false;
    }

    public synchronized void returnBook() {
        status = BookStatus.AVAILABLE;
    }

    public String getBookId() {
        return bookId;
    }

    public Author getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    void setAuthor(Author author) {
        this.author = Objects.requireNonNull(author, "author");
    }
}
