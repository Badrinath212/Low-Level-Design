package LibraryManagementSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Author {
    private final String authorId;
    private String name;
    private final List<Book> books;

    public Author(String authorId, String name) {
        this.authorId = Objects.requireNonNull(authorId, "authorId");
        this.name = Objects.requireNonNull(name, "name");
        this.books = new ArrayList<>();
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "name");
    }

    public List<Book> getBooks() {
        return List.copyOf(books);
    }

    public boolean addBook(Book book) {
        Objects.requireNonNull(book, "book");
        if (books.stream().anyMatch(existing -> Objects.equals(existing.getBookId(), book.getBookId()))) {
            return false;
        }
        books.add(book);
        return true;
    }
}
