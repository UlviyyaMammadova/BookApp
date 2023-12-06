import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookstoreDAO {
    private Connection connection;

    public BookstoreDAO() {
        try {
            this.connection = DatabaseConnector.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // CRUD operations for Author
    public void addAuthor(Author author) {
        try {
            String insertQuery = "INSERT INTO Authors (author_name) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, author.getAuthorName());
                preparedStatement.executeUpdate();

                // Retrieve the generated auto-incremented key (author_id)
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    author.setAuthorId(generatedId);
                } else {
                    throw new SQLException("Failed to retrieve generated author_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // CRUD operations for Book
    public void addBook(Book book, Author author) {
        try {
            // Check if the author exists, if not, add the author
            if (author.getAuthorId() == 0) {
                addAuthor(author);
            }

            // Now, add the book
            String insertQuery = "INSERT INTO Books (title, stock_quantity, author_id) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, book.getTitle());
                preparedStatement.setInt(2, book.getStockQuantity());

                // Use the author ID from the Author object
                preparedStatement.setInt(3, author.getAuthorId());

                preparedStatement.executeUpdate();

                // Retrieve the generated auto-incremented key (book_id)
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        book.setBookId(generatedId);
                    } else {
                        throw new SQLException("Failed to retrieve generated book_id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM Books";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectQuery)) {
                while (resultSet.next()) {
                    int bookId = resultSet.getInt("book_id");
                    String title = resultSet.getString("title");
                    int stockQuantity = resultSet.getInt("stock_quantity");
                    int authorId = resultSet.getInt("author_id");


                    Book book = new Book();
                    book.setBookId(bookId);
                    book.setTitle(title);
                    book.setStockQuantity(stockQuantity);
                    book.setAuthorId(authorId);

                    books.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public void updateBookDetails(String oldTitle, String newTitle) {
        try {
            String updateQuery = "UPDATE Books SET title = ? WHERE title = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, newTitle);
                preparedStatement.setString(2, oldTitle);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeBook(String title) {
        try {
            String deleteQuery = "DELETE FROM Books WHERE title = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setString(1, title);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // CRUD operations for Customers
    public void addCustomer(Customer customer) {
        try {
            String insertQuery = "INSERT INTO Customers (customer_name) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, customer.getCustomerName());
                preparedStatement.executeUpdate();

                // Retrieve the generated auto-incremented key (customer_id)
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    customer.setCustomerId(generatedId);
                } else {
                    throw new SQLException("Failed to retrieve generated customer_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM Customers";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectQuery)) {
                while (resultSet.next()) {
                    int customerId = resultSet.getInt("customer_id");
                    String customerName = resultSet.getString("customer_name");

                    Customer customer = new Customer(customerName);
                    customer.setCustomerId(customerId);
                    customers.add(customer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public void updateCustomerDetails(int customerId, String newCustomerName) {
        try {
            String updateQuery = "UPDATE Customers SET customer_name = ? WHERE customer_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, newCustomerName);
                preparedStatement.setInt(2, customerId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeCustomer(int customerId) {
        try {
            String deleteQuery = "DELETE FROM Customers WHERE customer_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, customerId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // CRUD operations for Orders
    public void addOrder(Order order) {
        try {
            connection.setAutoCommit(false);

            String checkStockQuery = "SELECT stock_quantity FROM Books WHERE book_id = ?";
            try (PreparedStatement stockStatement = connection.prepareStatement(checkStockQuery)) {
                stockStatement.setInt(1, order.getBookId());
                ResultSet resultSet = stockStatement.executeQuery();
                if (resultSet.next()) {
                    int stockQuantity = resultSet.getInt("stock_quantity");
                    if (stockQuantity < 1) {
                        throw new SQLException("Not enough stock for book: " + order.getBookId());
                    }
                }
            }

            // Add the order
            String insertOrderQuery = "INSERT INTO Orders (customer_id, book_id, order_date) VALUES (?, ?, CURRENT_DATE)";
            try (PreparedStatement orderStatement = connection.prepareStatement(insertOrderQuery, Statement.RETURN_GENERATED_KEYS)) {
                orderStatement.setInt(1, order.getCustomerId());
                orderStatement.setInt(2, order.getBookId());
                orderStatement.executeUpdate();

                // Retrieve the generated auto-incremented key (order_id)
                ResultSet generatedKeys = orderStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    order.setOrderId(generatedId);
                } else {
                    throw new SQLException("Failed to retrieve generated order_id");
                }
            }

            // Update the stock quantity
            String updateStockQuery = "UPDATE Books SET stock_quantity = stock_quantity - 1 WHERE book_id = ?";
            try (PreparedStatement stockUpdateStatement = connection.prepareStatement(updateStockQuery)) {
                stockUpdateStatement.setInt(1, order.getBookId());
                stockUpdateStatement.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM Orders";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectQuery)) {
                while (resultSet.next()) {
                    int orderId = resultSet.getInt("order_id");
                    int customerId = resultSet.getInt("customer_id");
                    int bookId = resultSet.getInt("book_id");
                    Date orderDate = resultSet.getDate("order_date");

                    
                    Order order = new Order(customerId, bookId);
                    order.setOrderId(orderId);
                    order.setOrderDate(orderDate);
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }


    public void displayTableInfo() {
        try {
            DatabaseMetaData metaData = connection.getMetaData();

            ResultSet tables = metaData.getTables(null, null, null, new String[]{"TABLE"});
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                System.out.println("Table Name: " + tableName);

                ResultSet columns = metaData.getColumns(null, null, tableName, null);
                while (columns.next()) {
                    String columnName = columns.getString("COLUMN_NAME");
                    String columnType = columns.getString("TYPE_NAME");
                    System.out.println("   Column Name: " + columnName + ", Type: " + columnType);
                }

                ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, tableName);
                while (primaryKeys.next()) {
                    String primaryKeyColumn = primaryKeys.getString("COLUMN_NAME");
                    System.out.println("   Primary Key: " + primaryKeyColumn);
                }

                ResultSet foreignKeys = metaData.getImportedKeys(null, null, tableName);
                while (foreignKeys.next()) {
                    String foreignKeyColumn = foreignKeys.getString("FKCOLUMN_NAME");
                    String referencedTable = foreignKeys.getString("PKTABLE_NAME");
                    String referencedColumnName = foreignKeys.getString("PKCOLUMN_NAME");
                    System.out.println("   Foreign Key: " + foreignKeyColumn +
                            ", References: " + referencedTable + "(" + referencedColumnName + ")");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
