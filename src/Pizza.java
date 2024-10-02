import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Pizza extends Restaurant {

  public Pizza(String restaurantName, String restaurantLocation, String restaurantNumber, Map<String, Integer> mealOrders, String specialPrep) {
    super(restaurantName, restaurantLocation, restaurantNumber, mealOrders, specialPrep);
  }

  // Show pizza menu
  public void showMenu() {
    System.out.println("=== Pizza Menu ===");
    System.out.println("1. Margherita - $8");
    System.out.println("2. Pepperoni - $9");
    System.out.println("3. Veggie Pizza - $7");
  }

  // Allow user to choose pizzas and return Pizza object
  public static Pizza choosePizzas() {
    Scanner scanner = new Scanner(System.in);
    Map<String, Integer> selectedItems = new HashMap<>();

    Pizza pizzaOrder = new Pizza("Good Eats", "123 Food St", "065122342342", selectedItems, "");

    pizzaOrder.showMenu();
    System.out.println("Select your pizzas (type the numbers separated by commas, e.g., 1,2): ");
    String choice = scanner.nextLine();

    // Adding selected pizzas to the map
    String[] choices = choice.split(",");
    for (String ch : choices) {
      switch (ch.trim()) {
        case "1":
          selectedItems.put("Margherita", 8);
          break;
        case "2":
          selectedItems.put("Pepperoni", 9);
          break;
        case "3":
          selectedItems.put("Veggie Pizza", 7);
          break;
        default:
          System.out.println("Invalid choice: " + ch);
          break;
      }
    }

    // Ask for special preparation instructions
    System.out.println("Any special preparation instructions? (Leave blank if none): ");
    String specialPrep = scanner.nextLine();

    // Return a new Pizza object with meal orders and special preparation
    return new Pizza("Good Eats", "123 Food St", "065122342342", selectedItems, specialPrep);
  }
}
