# Bookstore Application

This is a simple Java application for managing a bookstore's inventory, customers, and orders.

## Classes

### 1. Author

The `Author` class represents an author with the following attributes:

- `authorId`: Unique identifier for the author.
- `authorName`: Name of the author.

### 2. Book

The `Book` class represents a book with the following attributes:

- `bookId`: Unique identifier for the book.
- `title`: Title of the book.
- `stockQuantity`: Quantity of the book in stock.
- `authorId`: Identifier of the author of the book.

### 3. BookstoreDAO

The `BookstoreDAO` class handles the data access operations for the application. It includes methods for CRUD (Create, Read, Update, Delete) operations for Authors, Books, Customers, and Orders. It also provides a method to display information about database tables.

### 4. Customer

The `Customer` class represents a customer with the following attributes:

- `customerId`: Unique identifier for the customer.
- `customerName`: Name of the customer.

### 5. DatabaseConnector

The `DatabaseConnector` class provides a connection to the PostgreSQL database used by the application.

### 6. Main

The `Main` class contains the main method for running the application. It uses the `BookstoreDAO` to interact with the database and provides a simple command-line interface for users to perform actions such as adding books, updating orders, and viewing customer information.

### 7. Order

The `Order` class represents an order with the following attributes:

- `orderId`: Unique identifier for the order.
- `customerId`: Identifier of the customer placing the order.
- `bookId`: Identifier of the book being ordered.
- `orderDate`: Date when the order was placed.

## Setup

1. **Database Setup**: Create a PostgreSQL database named "Books" and add the necessary tables.

   ```sql
   -- Books, Authors, Customers, Orders

2. **Java Environment**: Ensure you have Java installed on your machine.

3. **Compile and Run**: Compile and run the Java application using your preferred IDE or command line.

4. **Usage**: Run the Main class.
   Follow the on-screen instructions to interact with the application.

Perform actions such as adding books, updating orders, and viewing customer information.
