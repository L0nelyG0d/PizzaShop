import java.util.*;
import java.text.*;


 interface PizzaInterface{

    void addCustomer(Customer customer);

    void updateCustomer(Customer customer);

    List<Pizza> getPizzaBySize(String size);

    Customer getCustomerById(int customerId);

    Order placeOrder(Pizza pizza, int quantity, Customer customer);

    Pizza getPizzaById(int pizzaId);

    Pizza getPizzaByName(String pizzaName);

    Pizza orderNewPizza(Pizza pizza, Customer customer);

    List<Pizza> getAllPizzas();

    Pizza updatePrice(Pizza pizza, double newPrice);

    void deletePizza(Pizza pizza);

    Pizza addNewPizza(Pizza pizza);

}


 class PizzaService implements PizzaInterface {



     Map<Integer, Customer> customersById = new HashMap<>();
     int nextCustomerId = 1; 

     String couldNotFindPizza = "Pizza not found!";
     PizzaStore pizzaStore;

    public PizzaService(PizzaStore pizzaStore) {
        this.pizzaStore = pizzaStore;
    }

    public void addCustomer(Customer customer) {
    customersById.put(customer.getCustomerId(), customer);
    }


   public Customer registerCustomer() {
    
    int customerId = nextCustomerId++; 
    Customer customer = new Customer( customerId);
    customersById.put(customerId, customer); 
    return customer;
    }


    public Order placeOrder(Pizza pizza, int quantity, Customer customer){
        double totalPrice = pizza.getPrice() * quantity;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String orderDate = dateFormat.format(new Date());
        Order newOrder = new Order(pizza.getPizzaName() , totalPrice, orderDate, generateNewOrderId());

        for(int i = 0 ; i < quantity ; i++){
            newOrder.addPizza(pizza);
        }

        customer.addOrder(newOrder);
        updateCustomer(customer);

        return newOrder;
    }

    public int generateNewOrderId() {
    return (int) (Math.random() * 10000);
}



    @Override
    public Customer getCustomerById(int customerId) {
   return customersById.get(customerId);
    }

        
    @Override
    public void updateCustomer(Customer customer) {
    customersById.put(customer.getCustomerId(), customer);
    }

    

    @Override
    public Pizza addNewPizza(Pizza pizza) {
        pizzaStore.getPizzas().add(pizza);
        return pizza;
    }

    @Override
    public void deletePizza(Pizza pizza) {
        pizzaStore.getPizzas().remove(pizza);
    }

    @Override
    public Pizza updatePrice(Pizza pizza, double newPrice) {
        for (Pizza p : pizzaStore.getPizzas()) {
            if (p.equals(pizza)) {
                p.setPrice(newPrice);
                return p;
            }
        }
        throw new RuntimeException(couldNotFindPizza);
    }

    @Override
    public List<Pizza> getAllPizzas() {
        return pizzaStore.getPizzas();
    }

    @Override
    public Pizza orderNewPizza(Pizza pizza, Customer customer) {
        
        Order order = new Order("wow",1.32,"wow2",4);
        order.addPizza(pizza);
        customer.addOrder(order);
        return pizza;
    }

    @Override
    public Pizza getPizzaById(int pizzaId) {
        for (Pizza pizza : pizzaStore.getPizzas()) {
            if (pizza.getPizzaId() == pizzaId) {
                return pizza;
            }
        }
        throw new RuntimeException(couldNotFindPizza);
    }

    @Override
    public Pizza getPizzaByName(String pizzaName) {
        for (Pizza pizza : pizzaStore.getPizzas()) {
            if (pizza.getPizzaName().equalsIgnoreCase(pizzaName)) {
                return pizza;
            }
        }
        throw new RuntimeException(couldNotFindPizza);
    }

    @Override
    public List<Pizza> getPizzaBySize(String size) {
    List<Pizza> pizzas = new ArrayList<>();
    for (Pizza pizza : pizzas) {
        if (pizza.getSize().equalsIgnoreCase(size)) {
            pizzas.add(pizza);
                }
            }
    return pizzas;
        }

    }




class Pizza {
     String size;
     double price;
     String pizzaName;
     int pizzaId;
     PizzaBase base;
     List<Topping> toppings = new ArrayList<>();

    Pizza(String size, double price, String pizzaName, int pizzaId, PizzaBase base) {
        this.size = size;
        this.price = price;
        this.pizzaName = pizzaName;
        this.pizzaId = pizzaId;
        this.base = base;
    }

    void addTopping(Topping topping) { 
        toppings.add(topping);
    }

    String getSize() {
        return size;
    }

    void setSize(String size) { 
        this.size = size;
    }

    double getPrice() {
        return price;
    }

    void setPrice(double price) {
        this.price = price;
    }

    String getPizzaName() {
        return pizzaName;
    }

    void setPizzaName(String pizzaName) { 
        this.pizzaName = pizzaName;
    }

    int getPizzaId() {
        return pizzaId;
    }

    void setPizzaId(int pizzaId) { 
        this.pizzaId = pizzaId;
    }

    PizzaBase getBase() {
        return base;
    }

    void setBase(PizzaBase base) { 
        this.base = base;
    }

    List<Topping> getToppings() { 
        return toppings;
    }

    void setToppings(List<Topping> toppings) { 
        this.toppings = toppings;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID of the pizza: ").append(pizzaId)
          .append(", Its Name: ").append(pizzaName)
          .append(", Its size: ").append(size)
          .append(", its Price: $").append(String.format("%.2f", price))
          .append(", Base: ").append(base.getBaseName())
          .append(", New topping: ");
        for (Topping topping : toppings) {
            sb.append(topping.getToppingName()).append(" ");
            }
        return sb.toString().trim();
        }
    }

 class VegeterianPizza extends Pizza {
    
    VegeterianPizza(String size,double price,  String pizzaName, int pizzaId, PizzaBase base){
        super(size,price,pizzaName,pizzaId,base);
    }

    @Override
   public String toString(){
    return super.toString() + "That's a VegeterianPizza";
   }
}


class PizzaBase{
    String description;
    String baseType;
    String baseName;

    PizzaBase(String description, String baseType, String baseName) {
        this.description = description;
        this.baseType = baseType;
        this.baseName = baseName;
    }

    String getBaseName() {
        return baseName;
    }

    void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    String getBaseType() {
        return baseType;
    }

    void setBaseType(String baseType) {
        this.baseType = baseType;
    }

    @Override
    public String toString() {
        return "Description: " + description + ", Base Type: " + baseType + ", Base Name: " + baseName;
        }
    }

class Topping {
    String toppingName;
    String description;
    String spiceLevel;

    Topping(String toppingName, String description, String spiceLevel) {
        this.toppingName = toppingName;
        this.description = description;
        this.spiceLevel = spiceLevel;
    }

    String getToppingName() {
        return toppingName;
    }

    void setToppingName(String toppingName) {
        this.toppingName = toppingName;
    }

    String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    String getSpiceLevel() {
        return spiceLevel;
    }

    void setSpiceLevel(String spiceLevel) {
        this.spiceLevel = spiceLevel;
    }

    @Override
    public String toString() {
        return "Topping Name: " + toppingName + ", Description: " + description + ", Spice Level: " + spiceLevel;
        }
    }





class PizzaStore{
    private List<Customer> customers = new ArrayList<>();
    private List<Pizza> pizzas = new ArrayList<>();
    String storeLocation;
    String storeName;
    int storeId;

    PizzaStore(String storeLocation, String storeName, int storeId){
        this.storeLocation = storeLocation;
        this.storeName = storeName;
        this.storeId = storeId;
    }


    void addPizza(Pizza pizza){
        pizzas.add(pizza);
    }

    void addCustomers(Customer customer){
        customers.add(customer);
    }

    void deletePizza(Pizza pizza){
        pizzas.remove(pizza);
    }

    int getStoreId(){
        return storeId;
    }

    void setStoreId(int storeId){
        this.storeId = storeId;
    }

    String getStoreLocation(){
        return storeLocation;
    }

    void setStoreLocation(String storeLocation){
        this.storeLocation = storeLocation;
    }

    String getStoreName(){
        return storeName;
    }

    void setStoreName(String storeName){
        this.storeName =storeName;
    }

    List<Pizza> getPizzas(){
        return pizzas;
    }

    void setPizzas(List<Pizza> pizzas){
        this.pizzas = pizzas;
    }

    List<Customer> getCustomers(){
        return customers;
    }



    void setCustomers(List<Customer> customers){
        this.customers = customers;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Store ID: ").append(storeId)
          .append(", Store Name: ").append(storeName)
          .append(", Store Location: ").append(storeLocation)
          .append(", Number of Pizzas: ").append(pizzas.size())
          .append(", Number of Customers: ").append(customers.size());
        return sb.toString();
        }
    }


class PremiumCustomer extends Customer {
    private int loyaltyPoints;

    public PremiumCustomer(int customerId) {
        super(customerId);
        this.loyaltyPoints = 0;
    }

    public void addLoyaltyPoints(int points) {
        this.loyaltyPoints += points;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }
}


class Customer{

    List<Order> orders = new ArrayList<>();
    
    Address address;
    long mobile;
    String email;
    String customerName;
    int customerId;

    public Customer(int customerId){
        this.customerId = customerId;
    }
    Customer (){

    }


    Customer(Address address, long mobile, String email, String customerName, int customerId){
    this.address = address;
    this.mobile = mobile;
    this.email = email;
    this.customerName = customerName;
    this.customerId = customerId;
    }

    void addOrder(Order order){
        orders.add(order);
    }

    double getPayableAmount(){
        double total = 0;

        for(Order order : orders){
            total+= order.getBillAmount();
        }
        return total;
    }

    int getCustomerId(){
        return customerId;
    }

    void setCustomerId(int customerId){
        this.customerId = customerId;
    }

    String getCustomerName(){
        return customerName;
    }

    void setCustomerName(String customerName){
    this.customerName = customerName;
    }

    String getEmail(){
        return email;
    }

    void setEmail(String email){
        this.email = email;
    }

    long getMobile(){
        return mobile;
    }

  void setMobile(long mobile){
    this.mobile = mobile;
    }

    Address getAddress(){
        return address;
    }

   void setAddress(Address address){
    this.address = address;
    }

    List<Order> getOrders(){
        return orders;
    }

    void setOrders(List<Order> orders){
        this.orders = orders;
    }

       
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CustomerID: ").append(customerId)
          .append(", CustomerName: ").append(customerName)
          .append(", Email: ").append(email)
          .append(", Mobile: ").append(mobile)
          .append(", Address: ").append(address.toString());

        for (Order order : orders) {
            sb.append(", Order: ").append(order.toString());
            }
        return sb.toString().trim();
        }
    }


class Order{

    List<Pizza> pizzas = new ArrayList<>();
    String orderDescription;
    double billAmount;
    String orderDate;
    int orderId;


    Order(String orderDescription, double billAmount, String orderDate, int orderId){
    this.orderDescription = orderDescription;
    this.billAmount = billAmount;
    this.orderDate = orderDate;
    this.orderId = orderId;
    }
    void addPizza(Pizza pizza){
        pizzas.add(pizza);
    }

    String getOrderDescription(){
        return orderDescription;
    }

    void setOrderDescription(String orderDescription){
        this.orderDescription = orderDescription;
    }

    double getBillAmount(){
        return billAmount;
    }

    void setBillAmount(Double billAmount){
        this.billAmount = billAmount;
    }

    String getOrderDate(){
        return orderDate;
    }

    void setOrderDate(String orderDate){
        this.orderDate = orderDate;
    }

    int getOrderId(){
        return orderId;
    }

    void setOrderId(int orderId){
        this.orderId = orderId;
    }

    List<Pizza> getPizza(){
        return pizzas;
    }

    void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    @Override
    public String toString(){
    return "OrderDescription: " + orderDescription + ", AmountToPay: " + billAmount + ", OrderDate: " + orderDate + ", OrderId: " + orderId;
        }
    }

 class Address {
    String state;
    String district;
    String city;
    int doorNumber;
    String street;

    Address(String state, String district, String city, int doorNumber, String street) {
        this.state = state;
        this.district = district;
        this.city = city;
        this.doorNumber = doorNumber;
        this.street = street; 
    }

    String getState() {
        return state;
    }

    void setState(String state) {
        this.state = state;
    }

    String getDistrict() {
        return district;
    }

    void setDistrict(String district) {
        this.district = district;
    }

    String getCity() {
        return city;
    }

    void setCity(String city) {
        this.city = city;
    }

    int getDoorNumber() {
        return doorNumber;
    }

    void setDoorNumber(int doorNumber) {
        this.doorNumber = doorNumber;
    }

    String getStreet() {
        return street;
    }

    void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "State: " + state + ", District: " + district + ", City: " + city + ", Door Number: " + doorNumber + ", Street: " + street;
        }
    }





public class PizzaDashboard {


    static List<Customer> customers = Collections.synchronizedList(new ArrayList<>());

 
   static void displayLoyaltyPoints(){
        synchronized(customers){
            System.out.println("Customer points:");
           for(Customer customer : customers){
                if (customer instanceof PremiumCustomer) {
                  PremiumCustomer premium = (PremiumCustomer) customers;
                    System.out.println("Customer ID: " + customer.getCustomerId() + ", Loyalty Points: " +premium.getLoyaltyPoints());
                }
                else {
                    System.out.println("Customer ID: " + customer.getCustomerId() + ", Not a premium customer.");
                }
            }
        }
    }


    static Scanner scanner = new Scanner(System.in);
    static PizzaInterface pizzaService = new PizzaService(new PizzaStore("Navoi 208/2", "Damir Domino's restaurant", 1123123123));
    static List<Integer> ratingSaver = new ArrayList<>();
    static int customerIdCounter = 1; 

    static int generateCustomerId() {
        return customerIdCounter++; 
    }

    

    public static void main(String[] args) {

        
        
        boolean isit = true;
        while (isit) {
            System.out.println(" ");
            System.out.println("Welcome to the Damir Domino's restaurant");
            System.out.println("1 - Admin Console");
            System.out.println("2 - Customer Console");
            System.out.println("3 - Exit");
            System.out.print("Enter choice: ");

            
            int choice = scanner.nextInt();
            scanner.nextLine();  

            switch (choice) {
                case 1:
                    adminConsole();
                    break;
                case 2:
                    customerConsole();
                    break;
                case 3:
                    System.out.println("Exiting");
                    System.out.println("Here's ur salary: ");
                    System.out.println(8 * 14.50);
                    isit = false;
                    break;
                default:
                    System.out.println("brother this Input does not exist, do it again");
                    break;
            }
        }
        scanner.close();
    }


    static void adminConsole() {
    boolean isit = true;
    while (isit) {

        System.out.println(" ");
        System.out.println(" Admin Console ");
        System.out.println("1 - Add New Pizza");
        System.out.println("2 - Update the Price of the Pizza");
        System.out.println("3 - Obliterate the Pizza: ");
        System.out.println("4 - Show all the Pizza's:");
        System.out.println("5 - Find the pizza:");
        System.out.println("6 - View Order History of the Customer");
        System.out.println("7 - View loyalty points:");
        System.out.println("8 - Back to Menu");
        System.out.print("What will u choose my son: ");
        
        int adminChoice = Integer.parseInt(scanner.nextLine());
        switch (adminChoice) {
            case 1:
                addNewPizza();
                break;
            case 2:
                updatePizzaPrice();
                break;
            case 3:
                deletePizza();
                break;
            case 4:
                viewAllPizzas();
                break;
            case 5:
                searchPizza();
                break;
            case 6:
                seeOrderHistory();
                break;
            case 7:
                displayLoyaltyPoints();
                break;
            case 8:
                isit = false;
                break;
            default:
                System.out.println("Are u blind or smth(Should i create a program for the deaf people)");
                break;
            }
        }
    }

    static void addNewPizza() {
    System.out.println("IMput the name:");
    String name = scanner.nextLine();

    System.out.println("Iput the size: ");
    String size = scanner.nextLine();

    System.out.println("Enter Pizza's Base:");
    String baseDescription = scanner.nextLine();

    System.out.println("Enter  Base Type:");
    String baseType = scanner.nextLine();

    System.out.println("Enter the Price:");
    double price = Double.parseDouble(scanner.nextLine());

    System.out.println("Create new id for the Pizza:");
    int pizzaId = Integer.parseInt(scanner.nextLine());

    PizzaBase base = new PizzaBase(baseDescription, baseType, baseDescription);  
    Pizza newPizza = new Pizza(size, price, name, pizzaId, base);

    System.out.println("Add toppings ('done' to finish):");
    while (true) {
        String toppingName = scanner.nextLine();
        if ("done".equalsIgnoreCase(toppingName)) {
            break;
        }
        System.out.println("Give info about this Topping " + toppingName + ":");
        String toppingDescription = scanner.nextLine();
        System.out.println("Enter spice level for " + toppingName + ":");
        String spiceLevel = scanner.nextLine();

        Topping topping = new Topping(toppingName, toppingDescription, spiceLevel);
        newPizza.addTopping(topping);
        }

    pizzaService.addNewPizza(newPizza);
    System.out.println("New pizza added successfully.");
    }


   static void updatePizzaPrice() {
    System.out.println("Write down the ID of the pizza:");
    int id = Integer.parseInt(scanner.nextLine());

    System.out.println("Enter the new price:");
    double price = scanner.nextDouble();
    scanner.nextLine(); 

    try {
        Pizza pizToUp = pizzaService.getPizzaById(id);
        pizzaService.updatePrice(pizToUp, price);
        System.out.println("New price: " + pizToUp);
    } 
    catch (RuntimeException e) {
        System.out.println("There was a mistake updaint the price: " + e.getMessage());
    }
}


    static void deletePizza() {
    System.out.println("Write down pizza Id:");
    int id = scanner.nextInt();
    scanner.nextLine(); 

    try {
        Pizza pizzaToDelete = pizzaService.getPizzaById(id);
        if (pizzaToDelete != null) {
            pizzaService.deletePizza(pizzaToDelete);
            System.out.println("Asked Pizza was deleted");
        } 

        else {
            System.out.println("Pizza containing id  " + id + " does not exist");
        }
    } 

    catch (RuntimeException e) {
        System.out.println("this Pizza does not exist " + e.getMessage());
    }
}



    static void viewAllPizzas() {
        List<Pizza> pizzas;
        try {
            pizzas = pizzaService.getAllPizzas();
            if (pizzas == null || pizzas.isEmpty()) {
                System.out.println("senioro brda Pizza no");
            } 
            else {
                System.out.println(" ");
                System.out.println("Available Pizzas:");
                for (Pizza pizza : pizzas) {
                    System.out.println(pizza);
                }
            }
        }
        catch (Exception e) {
            System.out.println("Could not retrieve pizzas: " + e.getMessage());
        }
    }    


    static void searchPizza() {
    System.out.println("Enter How Do U Want To Search For The Pizza");
    System.out.println("1 - Search with ID");
    System.out.println("2 - Search with Name");
    System.out.println("3 - Search Size");
    System.out.println("4 - Back");

    int choice = scanner.nextInt();
    scanner.nextLine(); 

    switch (choice) {
        case 1:
            searchWithId();
            break;
        case 2:
            searchWithName();
            break;
        case 3:
            searchWithSize();
            break;
        case 4:
            
            System.out.println("Returning to the previous menu...");
            break;
        default:
            System.out.println("Invalid choice, please try again.");
            break;
        }
    }

    static void searchWithId() {
    System.out.println("Enter the Pizza ID:");
    int id = scanner.nextInt();
    scanner.nextLine();

    try {
        Pizza pizza = pizzaService.getPizzaById(id);
        if (pizza != null) { 
            System.out.println("Pizza found: " + pizza);
        } 
        else {
            System.out.println("No pizza found with ID: " + id);
        }
    } 
    catch (RuntimeException e) {
        System.out.println("No pizza found with ID: " + id);
    }
}


    static void searchWithSize() {
    System.out.println("Enter the size of the pizza: ");
    String size = scanner.nextLine();

    try {
        List<Pizza> pizzas = pizzaService.getPizzaBySize(size);
        if (!pizzas.isEmpty()) {
            System.out.println("Pizzas found:");
            for (Pizza pizza : pizzas) {
                System.out.println(pizza);
            }
        } else {
            System.out.println("No pizzas found with size: " + size);
        }
    } catch (RuntimeException e) {
        System.out.println("Error searching for pizza by size: " + e.getMessage());
    }
}






    static void searchWithName() {
    System.out.println("Enter the Pizza Name:");
    String name = scanner.nextLine();

    try {
        Pizza pizza = pizzaService.getPizzaByName(name);
        if (pizza != null) {
            System.out.println("Pizza found: " + pizza);
        } 
        else {
            System.out.println("No pizzas found with name: " + name);
        }
    } 
    catch (RuntimeException e) {
        System.out.println("Error searching for pizza by name: " + e.getMessage());
    }
}


    static void customerConsole() {
        System.out.println(" " );
        System.out.println("Welcome to the Customer Console");
        System.out.print("Do you have a customer ID? (yes/no): ");
        String hasId = scanner.nextLine().trim().toLowerCase();
        Customer customer;
        if ("yes".equals(hasId)) {
            System.out.print("Please enter your customer ID: ");
            int id = scanner.nextInt();
            scanner.nextLine(); 
            customer = pizzaService.getCustomerById(id);
            if (customer == null) {
                System.out.println("Customer not found, please register.");
                customer = registerNewCustomer();
            }
        } 
        else {
            customer = registerNewCustomer();
        }


    boolean isit = true;
    while (isit) {
        System.out.println(" ");
        System.out.println("Customer Console ");
        System.out.println("0 - ViewMenu");
        System.out.println("1 - Place Order");
        System.out.println("2 - Order Delievery");
        System.out.println("3 - Pay Bill");
        System.out.println("4 - View All Pizzas");
        System.out.println("5 - View Order History");
        System.out.println("6 - SearchPizza");
        System.out.println("7 - Leave a Rating");
        System.out.println("8 - ViewAllRatings");
        System.out.println("9. Back to Main Menu");
        System.out.print("Enter choice: ");
        
        int customerChoice = Integer.parseInt(scanner.nextLine());
        switch (customerChoice) {
            case 0:
                viewMenu();
                break;
            case 1:
                makeOrder();
                break;
            case 2:
                orderDelievery();
                break;
            case 3:
                payBill();
                break;
            case 4:
                viewAllPizzas();
                break;
            case 5:
                seeOrderHistory();
                break;
            case 6:
                 searchPizza();
                break;
            case 7:
                leaveARating();
                break;
            case 8:
                displayAllRatings();
                break;
            case 9:
                viewOverallRating();
            case 10:
                isit = false;
                break;
            default:
                System.out.println("Invalid choice, please try again.");
                break;
            }
        }
    }


     
    static void viewOverallRating(){

        int result = 0;

        for(int n : ratingSaver){
            result+= n;
        }
        System.out.println("Overall Rating: ");                

        System.out.println(result/ratingSaver.size());
    }


    


    static  Customer registerNewCustomer() {
         System.out.print("Enter  name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        
        
        System.out.print("Enter phone number: ");
        long phone = scanner.nextLong();
        scanner.nextLine(); 
        
        Address address = new Address("North Carolina", "Virginia", "123", 123, "Askarova");
        
        System.out.print("Enter customerID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); 
        
        Customer newCustomer = new Customer(address, phone, email, name, customerId);
        pizzaService.addCustomer(newCustomer);
        
        System.out.println("Registered new customer with ID: " + customerId);
        return new Customer(address, phone, email, name, customerId);
}


static void leaveARating(){



System.out.println(" ");

    System.out.println("What rating do u wish to leave for our company?");
    System.out.println("Press 1 for -1 star");
    System.out.println("Press 2 for - 2 stars");
    System.out.println("Press 3 for - 3 stars");
    System.out.println("Press 4 for - 4 stars");
    System.out.println("Press 5 for - 5 stars");
    System.out.println("I came here by mistake(Exit button)");

    int rating = Integer.parseInt(scanner.nextLine().trim());

    switch(rating){

    case 1:
        ratingSaver.add(rating);
        System.out.println("Rating of 1 star was added to the system");
        break;
    
    case 2:
        ratingSaver.add(rating);
        System.out.println("Rating of 2 star was added to the system");
        break;
    
    case 3:
        ratingSaver.add(rating);
        System.out.println("Rating of 3 star was added to the system");
        break;
    
    case 4:
        ratingSaver.add(rating);
        System.out.println("Rating of 4 star was added to the system");
        break;
    
    case 5:
        ratingSaver.add(rating);
        System.out.println("Rating of 5 star was added to the system");
        break;
    case 6:
        System.out.println("Exiting");
        break;
    default:
        System.out.println("Press a button that is in the options");
    }

}


static void displayAllRatings(){
    
    System.out.println("Current ratings : ");
    for(int n : ratingSaver){
    System.out.println(n + " star" + (n == 1 ? "" : "s"));

    }

}
    
    static void viewMenu(){

    List<Pizza> viewmenu = new ArrayList<>();
    viewmenu.addAll(pizzaService.getAllPizzas());

    for(Pizza pizza : viewmenu){
    System.out.println("Menu: ");
    System.out.println(pizza);
    }
}




    

   static void makeOrder() {
    System.out.println("Enter your customer ID:");
    int customerId = Integer.parseInt(scanner.nextLine());

    Customer customer = pizzaService.getCustomerById(customerId);
    if (customer == null) {
        System.out.println("Customer not found.");
        return;
    }

    System.out.println("Enter Pizza ID you wish to order:");
    int pizzaId = Integer.parseInt(scanner.nextLine());

    Pizza pizza = pizzaService.getPizzaById(pizzaId);
    if (pizza == null) {
        System.out.println("Pizza not found.");
        return;
    }

    System.out.println("Enter quantity:");
    int quantity = Integer.parseInt(scanner.nextLine());
    
    double totalAmount = pizza.getPrice() * quantity;
    SimpleDateFormat formatter = new SimpleDateFormat("MM-yyyy-dd");
    String orderDate = formatter.format(new Date());

    int orderId = generateNewOrderId();
    Order newOrder = new Order("Order for " + pizza.getPizzaName(), totalAmount, orderDate, orderId);
    
    customer.addOrder(newOrder);
    

    pizzaService.updateCustomer(customer);

    System.out.println("Order for " + quantity + " x " + pizza.getPizzaName() + " was taken " + "loyaltypoints added: " + quantity * 1.43);

}
    
    






static void orderDelievery(){

    System.out.println("So u decided to make an order. Okay tell us ur adress type of the pizza and quantity that u want");

    System.out.println("Enter state: ");
    String state = scanner.next();
    scanner.nextLine();

    System.out.println("Enter district: ");
    String district = scanner.next();
    scanner.nextLine();

    System.out.println("Enter city");
    String city = scanner.next();
    scanner.nextLine();

    System.out.println("Enter doorNumber");
    int doorNumber;
        try{

         doorNumber = Integer.parseInt(scanner.nextLine());
    }
        catch(NumberFormatException e){
            System.out.println("Enter a number of ur door pls...");
                return;
        }


    System.out.println("Street");
    String street = scanner.next();
    scanner.nextLine();

    Address address =new Address(state, district, city, doorNumber, street);

    int id; 

    System.out.println("Enter The Id Of The Piiza: ");
     id = Integer.parseInt(scanner.nextLine());

    System.out.println("Enter amount of pizzas u want: ");

    int quantity;

    try{
     quantity = Integer.parseInt(scanner.nextLine());
    }
    catch(NumberFormatException e){
        System.out.println("Enther the number");
        return;
    }

    Pizza pizza = pizzaService.getPizzaById(id);

    Customer customer = new Customer();

    if(pizza == null){
        System.out.println("The id of the pizza does not exist");
    }

    pizzaService.placeOrder(pizza,quantity,customer);

    System.out.println("Your order for " + quantity + " " + id + " pizza(s) has been placed.");

}







    private static int generateNewOrderId() {
    return new Random().nextInt(10000);
    }

    static void payBill() {
        System.out.println("Enter your customer ID:");
        int customerId = Integer.parseInt(scanner.nextLine());

        Customer customer = pizzaService.getCustomerById(customerId);
        if (customer == null) {
            System.out.println("Customer not found. Please make sure you have entered the correct ID.");
            return;
        }

        List<Order> orders = customer.getOrders();
        if (orders == null ) {
            System.out.println("No orders found for this customer.");
            return;
        }
        else if ( orders.isEmpty()) {
            System.out.println("No orders found for this customer.");
            return;
        }

        System.out.println("Here are your orders:");
        for (int i = 0; i < orders.size(); i++) {
            System.out.println((i + 1) + ". " + orders.get(i));
        }

        System.out.println("Enter the number of the order you wish to pay for:");
        int orderNumber = Integer.parseInt(scanner.nextLine()) - 1;

        if (orderNumber < 0) {
            System.out.println("Invalid order, please make sure u entered the rigth orderNumber");
            return;
        }
        else if (orderNumber >= orders.size()) {
            System.out.println("Invalid order, please make sure u entered the rigth orderNumber");
            return;
        }

        Order orderToPay = orders.get(orderNumber);
        double amountDue = orderToPay.getBillAmount();

        System.out.println("Processing payment for: " + amountDue);

        System.out.println("Payment was successful: " + orderToPay);
    }
    

    static void seeOrderHistory() {
    System.out.println("Enter your customer ID:");
    int customerId = scanner.nextInt();
    scanner.nextLine(); 

    Customer customer = pizzaService.getCustomerById(customerId);
    if (customer == null) {
        System.out.println("Customer not found.");
        return;
    }

    List<Order> orders = customer.getOrders();
    if (orders.isEmpty()) {
        System.out.println("You dont have an order history");
    } else {
        System.out.println("Your order history:");
        for (Order order : orders) {
            System.out.println(order);
        }
    }
}    
}