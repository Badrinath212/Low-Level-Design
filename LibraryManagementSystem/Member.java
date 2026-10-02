package LibraryManagementSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Member {
    private final String memberId;
    private final String name;
    private final List<Book> borrowedBooks;

    public Member(String memberId, String name) {
        this.memberId = Objects.requireNonNull(memberId, "memberId");
        this.name = Objects.requireNonNull(name, "name");
        this.borrowedBooks = new ArrayList<>();
    }

    public String getMemberId() {
        return memberId;
    }

    public void addBorrowedBook(Book book) {
        Objects.requireNonNull(book, "book");
        if (borrowedBooks.stream().noneMatch(existing -> existing.getBookId().equals(book.getBookId()))) {
            borrowedBooks.add(book);
        }
    }

    public boolean removeBorrowedBook(String bookId) {
        return borrowedBooks.removeIf(book -> book.getBookId().equals(bookId));
    }

    public List<Book> getBorrowedBooks() {
        return List.copyOf(borrowedBooks);
    }
}
