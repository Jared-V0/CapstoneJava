import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Burger extends Restaurant {

  // Constructor for Burger class
  public Burger(String restaurantName, String restaurantLocation, String restaurantNumber, Map<String, Integer> mealOrders, String specialPrep) {
    super(restaurantName, restaurantLocation, restaurantNumber, mealOrders, specialPrep);
  }

  // Show burger menu
  public void showMenu() {
    System.out.println("=== Burger Menu ===");
    System.out.println("1. Cheeseburger - $5");
    System.out.println("2. Chicken Burger - $6");
    System.out.println("3. Veggie Burger - $4");
  }

  // Allow user to choose burgers and return mealOrders along with special instructions
  public static Burger chooseBurgers() {
    Scanner scanner = new Scanner(System.in);
    Map<String, Integer> selectedItems = new HashMap<>();

    // Show burger menu
    Burger burgerOrder = new Burger("Good Eats", "123 Food St", "065122342342", selectedItems, "");

    burgerOrder.showMenu();
    System.out.println("Select your burgers (type the numbers separated by commas, e.g., 1,2): ");
    String choice = scanner.nextLine();

    // Adding selected burgers to the map
    String[] choices = choice.split(",");
    for (String ch : choices) {
      switch (ch.trim()) {
        case "1":
          selectedItems.put("Cheeseburger", 5);
          break;
        case "2":
          selectedItems.put("Chicken Burger", 6);
          break;
        case "3":
          selectedItems.put("Veggie Burger", 4);
          break;
        default:
          System.out.println("Invalid choice: " + ch);
          break;
      }
    }

    // Ask for special preparation instructions
    System.out.println("Any special preparation instructions? (Leave blank if none): ");
    String specialPrep = scanner.nextLine();

    // Return a new Burger object with meal orders and special preparation
    return new Burger("Good Eats", "123 Food St", "065122342342", selectedItems, specialPrep);
  }
}
