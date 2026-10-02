package LibraryManagementSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LibraryManagement {
    private final List<Member> members = new ArrayList<>();
    private final List<Author> authors = new ArrayList<>();

    public LibraryManagement(List<Member> members, List<Author> authors) {
        Objects.requireNonNull(members, "members");
        Objects.requireNonNull(authors, "authors");
        for (Member member : members) {
            if (!addMember(member)) {
                throw new IllegalArgumentException("Member IDs must be unique");
            }
        }
        for (Author author : authors) {
            if (!addAuthor(author)) {
                throw new IllegalArgumentException("Author IDs must be unique");
            }
        }
    }

    public List<Member> getMembers() {
        return List.copyOf(members);
    }

    public List<Author> getAuthors() {
        return List.copyOf(authors);
    }

    public static void main(String[] args) {
        LibraryManagement libraryManagement = new LibraryManagement(
                List.of(
                        new Member("M001", "Alice"),
                        new Member("M002", "Bob")
                ),
                List.of(
                        new Author("A001", "John Doe"),
                        new Author("A002", "Jane Smith")
                )
        );

        System.out.println("Members: " + libraryManagement.getMembers().size());
        System.out.println("Authors: " + libraryManagement.getAuthors().size());

        String authorId = "A001";
        Author author = libraryManagement.getAuthors().stream()
                .filter(a -> a.getAuthorId().equals(authorId))
                .findFirst()
                .orElseThrow();

        Book book = new Book("B001", "Java Programming", author, BookStatus.AVAILABLE);
        System.out.println(libraryManagement.addBook(book));
        System.out.println(libraryManagement.borrowBook("M001", "B001"));
        System.out.println(libraryManagement.returnBook("M001", "B001"));
    }

    public synchronized String borrowBook(String memberId, String bookId) {
        Member member = members.stream()
                .filter(m -> Objects.equals(m.getMemberId(), memberId))
                .findFirst()
                .orElse(null);
        if (member == null) return "Member not found";

        Book book = getBookById(bookId);
        if (book == null) return "Book not found";
        if (!book.borrow()) return "Book is already borrowed";

        member.addBorrowedBook(book);
        return "Book borrowed successfully";
    }

    public Book searchBookByTitle(String title) {
        return searchBooksByTitle(title).stream().findFirst().orElse(null);
    }

    public List<Book> searchBooksByTitle(String title) {
        if (title == null) return List.of();

        List<Book> matches = new ArrayList<>();
        for (Author author : authors) {
            for (Book book : author.getBooks()) {
                if (book.getTitle().equalsIgnoreCase(title)) {
                    matches.add(book);
                }
            }
        }
        return List.copyOf(matches);
    }

    public List<Book> getBooksByAuthor(String authorId) {
        Author author = authors.stream()
                .filter(a -> Objects.equals(a.getAuthorId(), authorId))
                .findFirst()
                .orElse(null);
        if (author == null) return List.of();
        return author.getBooks();
    }

    public List<Book> searchBooksByAuthorName(String authorName) {
        if (authorName == null) return List.of();

        List<Book> matches = new ArrayList<>();
        for (Author author : authors) {
            if (author.getName().equalsIgnoreCase(authorName)) {
                matches.addAll(author.getBooks());
            }
        }
        return List.copyOf(matches);
    }

    public Book getBookById(String bookId) {
        for (Author author : authors) {
            for (Book book : author.getBooks()) {
                if (Objects.equals(book.getBookId(), bookId)) {
                    return book;
                }
            }
        }
        return null;
    }

    public synchronized String returnBook(String memberId, String bookId) {
        Member member = members.stream()
                .filter(m -> Objects.equals(m.getMemberId(), memberId))
                .findFirst()
                .orElse(null);
        if (member == null) return "Member not found";

        Book book = getBookById(bookId);
        if (book == null) return "Book not found";
        if (book.isAvailable()) return "Book is not borrowed";
        if (member.getBorrowedBooks().stream()
                .noneMatch(borrowedBook -> Objects.equals(borrowedBook.getBookId(), bookId))) {
            return "Member did not borrow this book";
        }

        book.returnBook();
        member.removeBorrowedBook(bookId);
        return "Book returned successfully";
    }

    public synchronized String addBook(Book book) {
        if (book == null || book.getBookId().isBlank() || book.getTitle().isBlank()) {
            return "Book ID and title are required";
        }

        Author registeredAuthor = authors.stream()
                .filter(author -> Objects.equals(author.getAuthorId(), book.getAuthor().getAuthorId()))
                .findFirst()
                .orElse(null);
        if (registeredAuthor == null) return "Author not found";
        if (getBookById(book.getBookId()) != null) return "Book ID already exists";

        book.setAuthor(registeredAuthor);
        if (!registeredAuthor.addBook(book)) return "Book ID already exists";
        return "Book added successfully";
    }

    public synchronized boolean addMember(Member member) {
        if (member == null || members.stream()
                .anyMatch(existing -> existing.getMemberId().equals(member.getMemberId()))) {
            return false;
        }
        return members.add(member);
    }

    public synchronized boolean addAuthor(Author author) {
        if (author == null || authors.stream()
                .anyMatch(existing -> existing.getAuthorId().equals(author.getAuthorId()))) {
            return false;
        }
        return authors.add(author);
    }
}
