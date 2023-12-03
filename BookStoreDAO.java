// BookstoreDAO.java
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

    public void addBook(Book book) {
        try {
            String insertQuery = "INSERT INTO Books (book_id, title, stock_quantity, author_id) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, book.getBookId());
                preparedStatement.setString(2, book.getTitle());
                preparedStatement.setInt(3, book.getStockQuantity());
                preparedStatement.setInt(4, book.getAuthorId());
                preparedStatement.executeUpdate();
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

                    Book book = new Book(bookId, title, stockQuantity, authorId);
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public void updateBookDetails(int bookId, String newTitle) {
        try {
            String updateQuery = "UPDATE Books SET title = ? WHERE book_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, newTitle);
                preparedStatement.setInt(2, bookId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeBook(int bookId) {
        try {
            String deleteQuery = "DELETE FROM Books WHERE book_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, bookId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void placeOrder(int customerId, List<Book> books) {
        try {
            connection.setAutoCommit(false);

            for (Book book : books) {
                String checkStockQuery = "SELECT stock_quantity FROM Books WHERE book_id = ?";
                try (PreparedStatement stockStatement = connection.prepareStatement(checkStockQuery)) {
                    stockStatement.setInt(1, book.getBookId());
                    ResultSet resultSet = stockStatement.executeQuery();
                    if (resultSet.next()) {
                        int stockQuantity = resultSet.getInt("stock_quantity");
                        if (stockQuantity < 1) {
                            throw new SQLException("Not enough stock for book: " + book.getBookId());
                        }
                    }
                }

                String insertOrderQuery = "INSERT INTO Orders (customer_id, book_id, order_date) VALUES (?, ?, CURRENT_DATE)";
                try (PreparedStatement orderStatement = connection.prepareStatement(insertOrderQuery)) {
                    orderStatement.setInt(1, customerId);
                    orderStatement.setInt(2, book.getBookId());
                    orderStatement.executeUpdate();
                }

                String updateStockQuery = "UPDATE Books SET stock_quantity = stock_quantity - 1 WHERE book_id = ?";
                try (PreparedStatement stockUpdateStatement = connection.prepareStatement(updateStockQuery)) {
                    stockUpdateStatement.setInt(1, book.getBookId());
                    stockUpdateStatement.executeUpdate();
                }
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

