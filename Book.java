package smartlibrary;
public class Book {
    int isbn;
    String title, author;
    boolean isBorrowed;
    Book left, right;

    public Book(int isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.isBorrowed = false;
    }
}

// For Member C: The Stack Node (Linked List style)
class HistoryNode {
    Book data;
    HistoryNode next;

    public HistoryNode(Book b) {
        this.data = b;
        this.next = null;
    }
}