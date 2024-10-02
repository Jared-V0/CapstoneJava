import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.*;


public class Main1 {


  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    int orderNumber = 0;
    boolean validInput = false;

    while(!validInput){
      try{
        System.out.println("Enter an order number: ");
        orderNumber = scanner.nextInt();
        scanner.nextLine();
       validInput = true;
      }catch(Exception e){
        System.out.println("Invalid input. Please enter a number.");
        scanner.nextLine();
      }
    }


    System.out.print("Enter your name: ");
    String customerName = scanner.nextLine();


    System.out.print("Enter your email address: ");
    String emailAddress = scanner.nextLine();


    System.out.print("Enter your phone number: ");
    String phoneNumber = scanner.nextLine();
    scanner.nextLine(); // consume newline


    System.out.print("Enter your location (city): ");
    String location = scanner.nextLine().trim();


    System.out.print("Enter your address: ");
    String address = scanner.nextLine();


    // Create a Customer object using the user input
    Customer customer = new Customer(orderNumber, customerName, emailAddress, phoneNumber, location, address);


    // Start the order process
    System.out.println("Welcome to Good Eats, " + customer.customerName + "! Let's start with your order:");


    // === Burger Order ===
    System.out.println("\n--- Burger Order ---");
    Burger burgerOrder = Burger.chooseBurgers();


    // === Pizza Order ===
    System.out.println("\n--- Pizza Order ---");
    Pizza pizzaOrder = Pizza.choosePizzas();


    // === Brownie Order ===
    System.out.println("\n--- Brownie Order ---");
    Brownie brownieOrder = Brownie.chooseBrownies();


    // Read the drivers from the file
    List<Driver> drivers = readDriversFromFile("drivers.txt");


    // Print the invoice and save it to a text file
    printInvoice(customer, burgerOrder, pizzaOrder, brownieOrder, drivers);
  }


  // Method to read drivers from a file
  public static List<Driver> readDriversFromFile(String fileName) {
    List<Driver> drivers = new ArrayList<>();
    try {
      File file = new File(fileName);
      Scanner fileScanner = new Scanner(file);
      while (fileScanner.hasNextLine()) {
        String line = fileScanner.nextLine();
        String[] driverInfo = line.split(", ");
        String name = driverInfo[0];
        String location = driverInfo[1];
        int load = Integer.parseInt(driverInfo[2]);
        drivers.add(new Driver(name, location, load));
      }
      fileScanner.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred while reading the file.");
      e.printStackTrace();
    }
    return drivers;
  }




  // Method to select a random driver from the list
  public static Driver findDriverWithLeastLoad(String customerLocation, List<Driver> drivers) {
    Driver selectedDriver = null;
    for (Driver driver : drivers) {
      // Check if the driver is in the same location as the customer
      if (driver.getLocation().equalsIgnoreCase(customerLocation)) {
        if (selectedDriver == null || driver.getLoad() < selectedDriver.getLoad()) {
          selectedDriver = driver;  // Select driver with the least load
        }
      }
    }
    return selectedDriver;
  }


  // Method to print the invoice and save it to a text file
  public static void printInvoice(Customer customer, Burger burgerOrder, Pizza pizzaOrder, Brownie brownieOrder, List<Driver> drivers) {
    try {
      FileWriter fileWriter = new FileWriter("invoice.txt");
      PrintWriter printWriter = new PrintWriter(fileWriter);


      StringBuilder invoice = new StringBuilder();


      invoice.append("\n=== INVOICE ===\n");
      invoice.append("Order Number: ").append(customer.orderNumber).append("\n");
      invoice.append("Customer: ").append(customer.customerName).append("\n");
      invoice.append("Email: ").append(customer.emailAddress).append("\n");
      invoice.append("Phone Number: ").append(customer.phoneNumber).append("\n");
      invoice.append("Location: ").append(customer.location).append("\n");


      invoice.append("\nYou have ordered the following from ").append(burgerOrder.getRestaurantName())
              .append(" in ").append(burgerOrder.getRestaurantLocation()).append(":\n");


      invoice.append("\n--- Burger Order ---\n");
      invoice.append(getMealOrdersString(burgerOrder.getMealOrders()));


      invoice.append("\n--- Pizza Order ---\n");
      invoice.append(getMealOrdersString(pizzaOrder.getMealOrders()));


      invoice.append("\n--- Brownie Order ---\n");
      invoice.append(getMealOrdersString(brownieOrder.getMealOrders()));


      // Find the driver with the least load in the same location as the customer
      Driver assignedDriver = findDriverWithLeastLoad(customer.location, drivers);
      if (assignedDriver != null) {
        // Increment the load for the driver (to reflect that they are taking a new delivery)
        assignedDriver.addLoad();


        // Driver found
        invoice.append("\n").append(assignedDriver.getName())
                .append(" is the nearest driver to the restaurant and so they will be delivering your order to you at: \n");
        invoice.append(customer.address).append("\n");
        invoice.append("If you need to contact the restaurant, their number is: ").append(burgerOrder.getRestaurantNumber()).append("\n");
      } else {
        // No driver available in the customer's location
        invoice.append("\nWe are sorry, but no drivers are currently available in your area.\n");
      }


      // Special preparation instructions
      invoice.append("\nSpecial Preparation for Burger: ").append(burgerOrder.specialPrep).append("\n");
      invoice.append("Special Preparation for Pizza: ").append(pizzaOrder.specialPrep).append("\n");
      invoice.append("Special Preparation for Brownie: ").append(brownieOrder.specialPrep).append("\n");


      // Calculate and print total amount
      int grandTotal = burgerOrder.getTotalAmount() + pizzaOrder.getTotalAmount() + brownieOrder.getTotalAmount();
      invoice.append("\nTotal Amount Due: $").append(grandTotal).append("\n");


      // Print to console
      System.out.println(invoice.toString());


      // Write to the file
      printWriter.print(invoice.toString());


      // Close the writer
      printWriter.close();
      System.out.println("\nInvoice has been saved to invoice.txt");


    } catch (IOException e) {
      System.out.println("An error occurred while writing the file.");
      e.printStackTrace();
    }
  }




  // Helper method to convert meal orders to a formatted string
  public static String getMealOrdersString(Map<String, Integer> mealOrders) {
    StringBuilder orders = new StringBuilder();
    for (Map.Entry<String, Integer> entry : mealOrders.entrySet()) {
      orders.append(entry.getKey()).append(" - $").append(entry.getValue()).append("\n");
    }
    return orders.toString();
  }
}
