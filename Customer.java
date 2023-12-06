public class Customer {
    private int customerId; // No need to set this in the constructor
    private String customerName;

    public Customer(String customerName) {
        this.customerName = customerName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String toString() {
        return "Customer ID: " + customerId + ", Customer Name: " + customerName;
    }

}
