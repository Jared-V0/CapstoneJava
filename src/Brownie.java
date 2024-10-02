import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Brownie extends Restaurant {

  public Brownie(String restaurantName, String restaurantLocation, String restaurantNumber, Map<String, Integer> mealOrders, String specialPrep) {
    super(restaurantName, restaurantLocation, restaurantNumber, mealOrders, specialPrep);
  }

  // Show brownie menu
  public void showMenu() {
    System.out.println("=== Brownie Menu ===");
    System.out.println("1. Classic Brownie - $3");
    System.out.println("2. Chocolate Chip Brownie - $4");
    System.out.println("3. Walnut Brownie - $5");
  }

  // Allow user to choose brownies and return Brownie object
  public static Brownie chooseBrownies() {
    Scanner scanner = new Scanner(System.in);
    Map<String, Integer> selectedItems = new HashMap<>();

    Brownie brownieOrder = new Brownie("Good Eats", "123 Food St", "065122342342", selectedItems, "");

    brownieOrder.showMenu();
    System.out.println("Select your brownies (type the numbers separated by commas, e.g., 1,2): ");
    String choice = scanner.nextLine();

    // Adding selected brownies to the map
    String[] choices = choice.split(",");
    for (String ch : choices) {
      switch (ch.trim()) {
        case "1":
          selectedItems.put("Classic Brownie", 3);
          break;
        case "2":
          selectedItems.put("Chocolate Chip Brownie", 4);
          break;
        case "3":
          selectedItems.put("Walnut Brownie", 5);
          break;
        default:
          System.out.println("Invalid choice: " + ch);
          break;
      }
    }

    // Ask for special preparation instructions
    System.out.println("Any special preparation instructions? (Leave blank if none): ");
    String specialPrep = scanner.nextLine();

    // Return a new Brownie object with meal orders and special preparation
    return new Brownie("Good Eats", "123 Food St", "065122342342", selectedItems, specialPrep);
  }
}
