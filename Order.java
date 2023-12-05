// Orders.java
import java.util.Date;

public class Order {
    private int orderId;
    private int customerId;
    private int bookId;
    private Date orderDate;

    public Order(int orderId, int customerId, int bookId, Date orderDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.bookId = bookId;
        this.orderDate = orderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
