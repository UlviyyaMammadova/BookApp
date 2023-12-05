import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        BookstoreDAO dao = new BookstoreDAO();

        // Example usage of CRUD operations
        dao.addAuthor(new Author(1, "John Doe"));
        dao.addCustomer(new Customer(1, "Alice"));

        // Example usage of metadata access
        dao.displayTableInfo();
    }
}
