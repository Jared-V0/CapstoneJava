import java.util.Map;

public class Restaurant {
    String restaurantName;
    String restaurantLocation;
    String restaurantNumber;
    Map<String, Integer> mealOrders;  // Using a map to store item names and their prices    int mealPrice;
    String specialPrep;
    int totalAmount;

    public Restaurant(String restaurantName,String restaurantLocation,String restaurantNumber,Map<String, Integer> mealOrders,String specialPrep){
        this.restaurantName = restaurantName;
        this.restaurantLocation = restaurantLocation;
        this.restaurantNumber = restaurantNumber;
        this.mealOrders = mealOrders;
        this.specialPrep = specialPrep;
        this.totalAmount = calculateTotalAmount();
    }

    // Method to calculate total amount by summing the prices of selected items
    public int calculateTotalAmount() {
        int total = 0;
        for (int price : mealOrders.values()) {
            total += price;  // Sum up the price of each selected item
        }
        return total;
    }
    // Getters
    public String getRestaurantName() {
        return restaurantName;
    }

    public String getRestaurantLocation() {
        return restaurantLocation;
    }

    public String getRestaurantNumber() {
        return restaurantNumber;
    }

    public Map<String, Integer> getMealOrders() {
        return mealOrders;
    }

    public int getTotalAmount() {
        return totalAmount;
    }
    // Override toString to display restaurant details and meal orders
    @Override
    public String toString() {
        StringBuilder ordersList = new StringBuilder();
        for (Map.Entry<String, Integer> entry : mealOrders.entrySet()) {
            ordersList.append(entry.getKey()).append(" - $").append(entry.getValue()).append("\n");
        }
        return "Restaurant Name: " + restaurantName +
                "\nLocation: " + restaurantLocation +
                "\nContact Number: " + restaurantNumber +
                "\nMeal Orders:\n" + ordersList.toString() +
                "Total Amount: $" + totalAmount;
    }

    // Helper method to print meal orders
    public static void printMealOrders(Map<String, Integer> mealOrders) {
        for (Map.Entry<String, Integer> entry : mealOrders.entrySet()) {
            System.out.println(entry.getKey() + " - $" + entry.getValue());
        }
    }

}
