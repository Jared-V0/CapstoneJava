public class Customer {
    //Attributes for the customer
    int orderNumber;
    String customerName;
    String emailAddress;
    String phoneNumber;
    String location;
    String address;

    public Customer(int orderNumber,String customerName,String emailAddress,String phoneNumber,String location,String address){
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.address = address;
    }

    //Getters for the customer details


    public int getOrderNumber() {
        return orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public String getAddress() {
        return address;
    }

    //Override toString to display customer details
    @Override
    public String toString(){
        return "Customer Name: " + customerName +
                "\nOrder Number: " + orderNumber +
                "\nEmail: " + emailAddress +
                "\nPhone Number: " + phoneNumber +
                "\nLocation: " + location +
                "\nAddress: " + address;
    }
}
