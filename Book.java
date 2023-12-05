// Book.java
public class Book {
    private int bookId;
    private String title;
    private int stockQuantity;
    private int authorId;

    public Book(int bookId, String title, int stockQuantity, int authorId) {
        this.bookId = bookId;
        this.title = title;
        this.stockQuantity = stockQuantity;
        this.authorId = authorId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}
