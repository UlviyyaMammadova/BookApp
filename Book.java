public class Book {
    private int bookId;
    private String title;
    private int stockQuantity;
    private int authorId;

    public Book(String title, int stockQuantity, int authorId) {
        this.title = title;
        this.stockQuantity = stockQuantity;
        this.authorId = authorId;
    }

    // No-argument constructor for cases where bookId is generated automatically
    public Book() {
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
    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", stockQuantity=" + stockQuantity +
                ", authorId=" + authorId +
                '}';
    }
}
