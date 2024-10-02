public class Driver {
  String name;
  String location;
  int load;  // Represents the number of deliveries

  public Driver(String name, String location, int load) {
    this.name = name;
    this.location = location;
    this.load = load;
  }

  public String getName() {
    return name;
  }

  public String getLocation() {
    return location;
  }

  public int getLoad() {
    return load;
  }

  public void addLoad() {
    this.load++;
  }
}
