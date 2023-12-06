import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookstoreDAO dao = new BookstoreDAO();

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Add a book");
            System.out.println("2. Update a book");
            System.out.println("3. Delete a book");
            System.out.println("4. Display all books");
            System.out.println("5. Add a customer");
            System.out.println("6. Display all customers");
            System.out.println("7. Add an order");
            System.out.println("8. Display all orders");
            System.out.println("9. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Add a book
                    System.out.println("Enter book title:");
                    String title = scanner.nextLine();
                    System.out.println("Enter stock quantity:");
                    int stockQuantity = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter author name:");
                    String authorName = scanner.nextLine();

                    Author author = new Author(authorName);
                    Book book = new Book(title, stockQuantity, 0); // 0 for authorId, will be set later

                    dao.addBook(book, author);
                    System.out.println("Book added successfully!");
                    break;
                case 2:
                    // Update a book
                    System.out.println("Enter old title of the book to update:");
                    String oldTitle = scanner.nextLine();
                    System.out.println("Enter new title:");
                    String newTitle = scanner.nextLine();
                    dao.updateBookDetails(oldTitle, newTitle);
                    System.out.println("Book updated successfully!");
                    break;

                case 3:
                    // Delete a book
                    System.out.println("Enter title of the book to delete:");
                    String titleToDelete = scanner.nextLine();
                    dao.removeBook(titleToDelete);
                    System.out.println("Book deleted successfully!");
                    break;
                case 4:
                    // Display all books
                    List<Book> books = dao.getAllBooks();
                    for (Book b : books) {
                        System.out.println(b);
                    }
                    break;

                case 5:
                    // Add a customer
                    System.out.println("Enter customer name:");
                    String customerName = scanner.nextLine();
                    dao.addCustomer(new Customer(customerName));
                    System.out.println("Customer added successfully!");
                    break;

                case 6:
                    // Display all customers
                    List<Customer> customers = dao.getAllCustomers();
                    for (Customer customer : customers) {
                        System.out.println(customer);
                    }
                    break;

                case 7:
                    // Add an order
                    System.out.println("Enter customer ID:");
                    int orderCustomerId = scanner.nextInt();
                    System.out.println("Enter book ID:");
                    int orderBookId = scanner.nextInt();
                    dao.addOrder(new Order(orderCustomerId, orderBookId));
                    System.out.println("Order added successfully!");
                    break;

                case 8:
                    // Display all orders
                    List<Order> orders = dao.getAllOrders();
                    for (Order order : orders) {
                        System.out.println(order);
                    }
                    break;

                case 9:
                    // Exit the program
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0);
                    break;

                case 10:
                    // Update customer name
                    System.out.println("Enter customer ID to update:");
                    int updateCustomerId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter new customer name:");
                    String newCustomerName = scanner.nextLine();
                    dao.updateCustomerDetails(updateCustomerId, newCustomerName);
                    System.out.println("Customer name updated successfully!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 9.");
            }
        }
    }
}
