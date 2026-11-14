import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Project {
    public static void main(String[] args) {

        User user = new User("User");

        GroceryStore groceryStore = new GroceryStore();
        groceryStore.populateStock();

        Menu menu = new Menu();

        menu.runMainMenu(user, groceryStore);
    }
}

// ENUM & INTERFACE
enum ProductType {
    FRUIT, // SELF EXPLANATORY
    VEGETABLES, // SELF EXPLANATORY
    MEAT, // MEAT & SEAFOOD
    DAIRY, // MILK, CHEESE, ETC.
    FROZEN, // FROZEN FOOD
    PANTRY, // SNACKS & CONFECTIONARY
    HOUSEHOLD, // CLEANING AND HEALTH
}

interface StockManagement {
    void addProduct(Product product);

    void removeProduct(Product product);

    String displayProducts(List<Product> products);
}

interface ProductSearch {
    String searchProducts();

    String searchFilteredProducts(ProductType type);
}

interface Rewardable {
    int getRewardValue();
}

// GROCERY STORE
class GroceryStore implements StockManagement, ProductSearch {
    List<Product> productStock;
    List<Product> rewardItemStock;
    Map<ProductType, List<Product>> productFilter;

    GroceryStore() {
        this.productStock = new ArrayList<>();
        this.rewardItemStock = new ArrayList<>();

        this.productFilter = new HashMap<>();
        this.productFilter.put(ProductType.FRUIT, new ArrayList<>());
        this.productFilter.put(ProductType.VEGETABLES, new ArrayList<>());
        this.productFilter.put(ProductType.MEAT, new ArrayList<>());
        this.productFilter.put(ProductType.DAIRY, new ArrayList<>());
        this.productFilter.put(ProductType.FROZEN, new ArrayList<>());
        this.productFilter.put(ProductType.PANTRY, new ArrayList<>());
        this.productFilter.put(ProductType.HOUSEHOLD, new ArrayList<>());
    }

    public String displayRewardItems() {
        String list = "--- Reward Products ---";
        list += displayProducts(this.rewardItemStock);
        list += "\n----------------------";
        return list;
    }

    public String searchProducts() { // Product Search method
        String list = "--- All Products ---"; // Initialises String that will be returned at the end of the method

        System.out.println("Apply filter?"); // Asks for Search filters
        System.out.println("  1. Yes\n  2. No");
        int choice = In.nextInt();
        if (choice == 1) {
            System.out.println("Select Filter.");
            System.out.println("  1. Fruit");
            System.out.println("  2. Vegetables");
            System.out.println("  3. Meat");
            System.out.println("  4. Dairy");
            System.out.println("  5. Frozen");
            System.out.println("  6. Pantry");
            System.out.println("  7. Household");
            choice = In.nextInt();
            if (choice == 1) {
                list = "--- Fruit Products ---";
                list += searchFilteredProducts(ProductType.FRUIT); // runs searchfilter method
                list += "\n----------------------"; // the producttype is used as a parameter
            } else if (choice == 2) {
                list = "--- Vegetable Products ---";
                list += searchFilteredProducts(ProductType.VEGETABLES);
                list += "\n-------------------------";
            } else if (choice == 3) {
                list = "--- Meat Products ---";
                list += searchFilteredProducts(ProductType.MEAT);
                list += "\n--------------------";
            } else if (choice == 4) {
                list = "--- Dairy Products ---";
                list += searchFilteredProducts(ProductType.DAIRY);
                list += "\n---------------------";
            } else if (choice == 5) {
                list = "--- Frozen Products ---";
                list += searchFilteredProducts(ProductType.FROZEN);
                list += "\n----------------------";
            } else if (choice == 6) {
                list = "--- Pantry Products ---";
                list += searchFilteredProducts(ProductType.PANTRY);
                list += "\n----------------------";
            } else if (choice == 7) {
                list = "--- Household Products ---";
                list += searchFilteredProducts(ProductType.HOUSEHOLD);
                list += "\n-------------------------";
            } else {
                System.out.println("Invalid choice.");
            }
        } else if (choice == 2) {
            list += displayProducts(this.productStock); // runs a displayproduct method
            list += "\n--------------------";
        } else {
            System.out.println("Select options 1 or 2");
        }
        return list;
    }

    public String searchFilteredProducts(ProductType type) { // this is an alternative to the search
        List<Product> filteredProducts = productFilter.get(type); // runs displayproducts at the end
        return displayProducts(filteredProducts);
    }

    public String searchRewardProducts() {
        String list = "--- Reward Products ---";
        list += displayProducts(this.rewardItemStock);
        list += "\n-------------------------";
        return list;
    }

    public String displayProducts(List<Product> products) { // a for loop that iterates through
        String list = ""; // the given product list (parameter)
        if (products.size() > 0) {
            for (int i = 0; i < products.size(); i++) {
                list += "\n" + (i + 1) + ". " + products.get(i); // creates a numbered list of each product
            }
        } else {
            list += "\n" + "No products available."; // if the list is empty, notifies
        }
        return list;
    }

    public void addProduct(Product product) { // Method adds products to productStock and
        this.productStock.add(product); // and their corresponding HashMap

        this.productFilter.get(product.productType).add(product);
        if (product instanceof RewardProduct) {
            this.rewardItemStock.add(product);
        }
    }

    public void removeProduct(Product product) {
        this.productStock.remove(product);

        this.productFilter.get(product.productType).remove(product);
    }

    void populateStock() { // Populates groceryStore object

        // FRUIT
        addProduct(new Product("Kiwi Fruit", ProductType.FRUIT, 2.99));
        addProduct(new Product("Bananas", ProductType.FRUIT, 3.50));
        addProduct(new DiscountProduct("Apples", ProductType.FRUIT, 5.99));

        // VEGETABLES
        addProduct(new Product("Carrots", ProductType.VEGETABLES, 2.50));
        addProduct(new Product("Potatoes", ProductType.VEGETABLES, 4.99));
        addProduct(new RewardProduct("Broccoli", ProductType.VEGETABLES, 3.49, 10));

        // MEAT
        addProduct(new Product("Rump Steak", ProductType.MEAT, 11.99));
        addProduct(new Product("Chicken Breast", ProductType.MEAT, 9.99));
        addProduct(new DiscountProduct("Pork Sausages", ProductType.MEAT, 7.99));

        // DAIRY
        addProduct(new Product("Milk", ProductType.DAIRY, 5.99));
        addProduct(new Product("Lactose Free Milk", ProductType.DAIRY, 4.99));
        addProduct(new Product("Cheddar Cheese", ProductType.DAIRY, 3.99));
        addProduct(new RewardProduct("Greek Yoghurt", ProductType.DAIRY, 6.50, 15));

        // FROZEN
        addProduct(new Product("Frozen Peas", ProductType.FROZEN, 3.99));
        addProduct(new Product("Frozen Pizza", ProductType.FROZEN, 8.99));
        addProduct(new DiscountProduct("Ice Cream", ProductType.FROZEN, 6.99));

        // PANTRY
        addProduct(new Product("White Rice", ProductType.PANTRY, 3.50));
        addProduct(new Product("Pasta", ProductType.PANTRY, 2.99));
        addProduct(new RewardProduct("Breakfast Cereal", ProductType.PANTRY, 5.99, 20));

        // HOUSEHOLD
        addProduct(new Product("Dishwashing Liquid", ProductType.HOUSEHOLD, 4.50));
        addProduct(new Product("Laundry Detergent", ProductType.HOUSEHOLD, 12.99));
        addProduct(new DiscountProduct("Paper Towels", ProductType.HOUSEHOLD, 5.99));
    }
}

// SHOPPING CART
class ShoppingCart {
    User owner;
    List<Product> cart;
    double cartPrice;
    int cartPoints;

    ShoppingCart(User owner) {
        this.owner = owner;
        this.cart = new ArrayList<>();
        this.cartPrice = 0;
        this.cartPoints = 0;
    }

    public String toString() {
        return "Cart has " + this.cart.size() + " items with a total of $" + this.cartPrice + " and " + cartPoints
                + " reward points.";
    }

    public int getCartSize() {
        return this.cart.size();
    }

    public double getCartPrice() {
        return this.cartPrice;
    }

    public int getCartPoints() {
        return this.cartPoints;
    }

    public User getOwner() {
        return this.owner;
    }

    public Product getProduct(int i) {
        return this.cart.get(i);
    }

    public String displayProducts(List<Product> products) {
        String list = "";
        if (products.size() > 0) {
            for (int i = 0; i < products.size(); i++) {
                list += "\n" + (i + 1) + ". " + products.get(i);
            }
        } else {
            list += "\n" + "Cart is empty.";
        }
        return list;
    }

    public void addProduct(Product product) {
        this.cart.add(product);

        if (product instanceof RewardProduct) {
            addReward((RewardProduct) product);
        }

        addCost(product);
    }

    public void addCost(Product product) {
        this.cartPrice += product.getProductCost();
    }

    public void addReward(RewardProduct product) {
        this.cartPoints += product.getRewardValue();
    }

    public void deductReward(RewardProduct product) {
        this.cartPoints -= product.getRewardValue();

        if (this.cartPoints < 0) {
            this.cartPoints = 0;
        }
    }

    public void removeProduct() {
        System.out.println(displayProducts(this.cart));
        System.out.println("Which product would you like to remove?");
        int i = In.nextInt();
        if (i > 0 && i <= getCartSize()) {
            if (this.cart.get(i - 1) instanceof RewardProduct) {
                deductReward((RewardProduct) this.cart.get(i - 1));
            }
            this.cartPrice -= this.cart.get(i - 1).getProductCost();
            this.cart.remove((i - 1));
        } else {
            System.out.println("Invalid selection.");
        }
    }

    public void checkout() {
        if (this.cart.size() <= 0) {
            System.out.println("Cart has no items.");
        } else {
            String cartItems = "";
            for (int i = 0; i < this.cart.size(); i++) {
                cartItems += this.cart.get(i).getProductName();
                if (i < this.cart.size() - 1) {
                    cartItems += ", ";
                }
                System.out.println("You are checking out: " + cartItems + ". Your total is: " + cartPrice
                        + ". Thank you for shopping with us.");
                for (int ii = getCartSize() - 1; ii >= 0; ii--) {
                    this.cart.remove(ii);
                    this.cartPrice = 0;
                }
            }
        }
    }
}

// USER & USER INPUT
class User {
    String name;
    ShoppingCart shoppingCart;
    int rewardPoints;

    User(String name) {
        this.name = name;
        this.shoppingCart = new ShoppingCart(this);
        this.rewardPoints = 0;
    }

    public String toString() {
        return this.name + " has " + this.shoppingCart.getCartSize() + " in their shopping cart and "
                + this.rewardPoints + " reward points.";
    }

    public String getName() {
        return this.name;
    }

    public ShoppingCart getCart() {
        return this.shoppingCart;
    }

    public List<Product> getCartList() {
        return this.shoppingCart.cart;
    }

    protected void setName(String name) {
        this.name = name;
    }

    int getRewardPoints() {
        return this.rewardPoints;
    }
}

class Menu {
    void runMainMenu(User user, GroceryStore groceryStore) {
        System.out.println("--- Online Grocery Store ---");
        while (true) {
            System.out.println("Select an option:");
            System.out.println("  1. View grocery store");
            System.out.println("  2. View user profile");
            System.out.println("  3. Exit");
            int choice = In.nextInt();
            if (choice == 1) {
                runGroceryStore(user, groceryStore);
            } else if (choice == 2) {
                runUserProfile(user, groceryStore);
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Pick an option 1, 2, or 3");
            }
        }

        System.out.println("Exiting...");
    }

    void addProductToCart(User user, GroceryStore groceryStore) {

        System.out.println("--- Add Product to Cart ---");
        // Display every product
        System.out.println(groceryStore.displayProducts(groceryStore.productStock));
        System.out.println("\nEnter product number (0 to cancel):");
        int choice = In.nextInt();

        if (choice == 0) {
            return;
        }

        if (choice >= 1 && choice <= groceryStore.productStock.size()) {

            Product product = groceryStore.productStock.get(choice - 1);

            user.getCart().addProduct(product);

            System.out.println(product.getProductName() + " has been added to your cart.");

        } else {
            System.out.println("Invalid product.");
        }
    }

    void runGroceryStore(User user, GroceryStore groceryStore) {
        System.out.println("--- Store Products ---");
        while (true) {
            System.out.println("Select an option:");
            System.out.println("  1. View products");
            System.out.println("  2. Add product to cart");
            System.out.println("  3. View reward tasks");
            System.out.println("  4. Back to main menu");
            int choice = In.nextInt();
            if (choice == 1) {
                System.out.println(groceryStore.searchProducts());
            } else if (choice == 2) {
                addProductToCart(user, groceryStore);
            } else if (choice == 3) {
                System.out.println(groceryStore.searchRewardProducts());
            } else if (choice == 4) {
                break;
            } else {
                System.out.println("Pick an option 1, 2, 3 or 4");
            }
        }
        System.out.println("Back to main menu...");
    }

    void runUserProfile(User user, GroceryStore groceryStore) {
        System.out.println("--- Your Profile ---");
        while (true) {
            System.out.println("Select an option:");
            System.out.println("  1. View shopping cart");
            System.out.println("  2. View statistics");
            System.out.println("  3. Back to main menu");
            int choice = In.nextInt();
            if (choice == 1) {
                runUserCart(user, groceryStore);
            } else if (choice == 2) {
                System.out.println(user.toString());
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Pick an option 1, 2, or 3");
            }
        }
        System.out.println("Back to main menu...");
    }

    void runUserCart(User user, GroceryStore groceryStore) {
        System.out.println("--- Your Cart ---");
        while (true) {
            System.out.println("Select an option:");
            System.out.println("  1. Review cart");
            System.out.println("  2. Pay for cart");
            System.out.println("  3. Back to main menu");
            int choice = In.nextInt();
            if (choice == 1) {
                System.out.println(user.getCart().displayProducts(user.getCartList()));
                System.out.println("Remove products?");
                System.out.println("1. Yes\n  2. No");
                choice = In.nextInt();
                if (choice == 1) {
                    user.getCart().removeProduct();
                } else if (choice == 2) {
                    System.out.println("");
                } else {
                    System.out.println("Invalid option.");
                }
            } else if (choice == 2) {
                user.getCart().checkout();
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Pick an option 1, 2, or 3");
            }
        }
        System.out.println("Back to main menu...");
    }
}

// PRODUCTS
class Product {
    protected String productName;
    protected ProductType productType;
    protected double productCost;

    Product(String productName, ProductType productType, double productCost) {
        this.productName = productName;
        this.productType = productType;
        this.productCost = productCost;
    }

    public String toString() {
        return this.productName + " ($" + this.productCost + ")";
    }

    public String getProductName() {
        return this.productName;
    }

    public ProductType getProductType() {
        return this.productType;
    }

    public double getProductCost() {
        return this.productCost;
    }
}

class DiscountProduct extends Product {
    double discountPrice;

    DiscountProduct(String productName, ProductType productType, double productCost) {
        super(productName, productType, productCost);
        this.productName = "Discounted " + this.productName;
        this.discountPrice = this.productCost -= (this.productCost / 2);

    }

    @Override
    public String toString() {
        return this.productName + " ($" + this.discountPrice + ")";
    }

    @Override
    public double getProductCost() {
        return this.discountPrice;
    }
}

class RewardProduct extends Product implements Rewardable {
    int rewardValue;

    RewardProduct(String productName, ProductType productType, double productCost, int rewardValue) {
        super(productName, productType, productCost);
        this.rewardValue = rewardValue;
    }

    @Override
    public String toString() {
        return this.productName + " ($" + this.productCost + ")" + " +" + this.rewardValue + " Reward Points";
    }

    public int getRewardValue() {
        return this.rewardValue;
    }
}
