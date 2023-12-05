// Main.java
public class Main {
    public static void main(String[] args) {
        BookstoreDAO dao = new BookstoreDAO();

        // Example usage of CRUD operations
        dao.addAuthor(new Author("John Doe")); // No need to provide author_id
        dao.addCustomer(new Customer("Alice")); // No need to provide customer_id

        // Example usage of metadata access
        dao.displayTableInfo();
    }
}
