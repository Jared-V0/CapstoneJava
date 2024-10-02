import java.io.*;
import java.util.*;

public class Main1 {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    int orderNumber = 0;
    boolean validInput = false;

    while (!validInput) {
      try {
        System.out.println("Enter an order number: ");
        orderNumber = scanner.nextInt();
        scanner.nextLine(); // consume newline
        validInput = true;
      } catch (Exception e) {
        System.out.println("Invalid input. Please enter a number.");
        scanner.nextLine(); // consume invalid input
      }
    }

    System.out.print("Enter your name: ");
    String customerName = scanner.nextLine();

    System.out.print("Enter your email address: ");
    String emailAddress = scanner.nextLine();

    System.out.print("Enter your phone number: ");
    String phoneNumber = scanner.nextLine();

    System.out.print("Enter your location (city): ");
    String location = scanner.nextLine().trim();

    System.out.print("Enter your address: ");
    String address = scanner.nextLine();

    // Create a Customer object using the user input
    Customer customer = new Customer(orderNumber, customerName, emailAddress, phoneNumber, location, address);

    // Start the order process
    System.out.println("Welcome to Good Eats, " + customer.customerName + "!");

    // === Customer selects what to order ===
    Burger burgerOrder = null;
    Pizza pizzaOrder = null;
    Brownie brownieOrder = null;

    boolean validMenuSelection = false;
    while (!validMenuSelection) {
      System.out.println("\nWould you like to order:");
      System.out.println("1 - Burger");
      System.out.println("2 - Pizza");
      System.out.println("3 - Brownie");
      System.out.println("Enter your choices separated by spaces (e.g., '1 2' for Burger and Pizza): ");
      String[] choices = scanner.nextLine().split(" ");

      // Flag to check if any valid option was selected
      validMenuSelection = true; // assume valid input, will set to false if invalid choice found

      // Process the user's choices
      for (String choice : choices) {
        switch (choice) {
          case "1":
            System.out.println("\n--- Burger Order ---");
            burgerOrder = Burger.chooseBurgers();
            break;
          case "2":
            System.out.println("\n--- Pizza Order ---");
            pizzaOrder = Pizza.choosePizzas();
            break;
          case "3":
            System.out.println("\n--- Brownie Order ---");
            brownieOrder = Brownie.chooseBrownies();
            break;
          default:
            // If an invalid choice is entered, show an error and reset the flag
            System.out.println("Invalid choice: " + choice + ". Please try again.");
            validMenuSelection = false;
        }
      }

      // If no valid choices were made, warn the user and ask again
      if (!validMenuSelection) {
        System.out.println("Please select at least one valid option (1 for Burger, 2 for Pizza, 3 for Brownie).");
      }
    }

    // Check if any valid orders were placed
    if (burgerOrder == null && pizzaOrder == null && brownieOrder == null) {
      System.out.println("You didn't place any orders.");
      return; // Exit if no orders were placed
    }

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

      if (burgerOrder != null) {
        invoice.append("\n--- Burger Order ---\n");
        invoice.append(getMealOrdersString(burgerOrder.getMealOrders()));
      }
      if (pizzaOrder != null) {
        invoice.append("\n--- Pizza Order ---\n");
        invoice.append(getMealOrdersString(pizzaOrder.getMealOrders()));
      }
      if (brownieOrder != null) {
        invoice.append("\n--- Brownie Order ---\n");
        invoice.append(getMealOrdersString(brownieOrder.getMealOrders()));
      }

      // Find the driver with the least load in the same location as the customer
      Driver assignedDriver = findDriverWithLeastLoad(customer.location, drivers);
      if (assignedDriver != null) {
        // Increment the load for the driver (to reflect that they are taking a new delivery)
        assignedDriver.addLoad();

        // Driver found
        invoice.append("\n").append(assignedDriver.getName())
                .append(" is the nearest driver to the restaurant and so they will be delivering your order to you at: \n");
        invoice.append(customer.address).append("\n");
      } else {
        // No driver available in the customer's location
        invoice.append("\nWe are sorry, but no drivers are currently available in your area.\n");
      }

      // Special preparation instructions (if applicable)
      if (burgerOrder != null) {
        invoice.append("\nSpecial Preparation for Burger: ").append(burgerOrder.specialPrep).append("\n");
      }
      if (pizzaOrder != null) {
        invoice.append("Special Preparation for Pizza: ").append(pizzaOrder.specialPrep).append("\n");
      }
      if (brownieOrder != null) {
        invoice.append("Special Preparation for Brownie: ").append(brownieOrder.specialPrep).append("\n");
      }

      // Calculate and print total amount
      int grandTotal = 0;
      if (burgerOrder != null) grandTotal += burgerOrder.getTotalAmount();
      if (pizzaOrder != null) grandTotal += pizzaOrder.getTotalAmount();
      if (brownieOrder != null) grandTotal += brownieOrder.getTotalAmount();
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
