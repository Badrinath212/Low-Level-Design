package VendingMachineSystem;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VendingMachine {
    private final List<Product> inventory;
    private MachineState machineState;

    public VendingMachine(MachineState machineState, List<Product> inventory) {
        this.machineState = machineState;
        this.inventory = List.copyOf(inventory);
    }
    public static void main(String[] args) {
        VendingMachine vendingMachine = new VendingMachine(
                                    MachineState.IDLE, 
                                    List.of(new Product("001", "Coke", new BigDecimal("20"), 10), 
                                    new Product("002", "Pepsi", new BigDecimal("20"), 10)));

        System.out.println("Vending Machine initialized with state: " + vendingMachine.machineState);

        // Simulate a user selecting a product
        int inputQuantity = 5; // Example quantity input
        if (inputQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be a positive value");
        }

        // transanction flow
        
        Product product = vendingMachine.selectProduct("001", inputQuantity);
        System.out.println("Product selected: " + product.toString());
        // Simulate a user inserting money and purchasing the product
        double change = vendingMachine.purchaseProduct(product, 100, inputQuantity);

        if (change >= 0) {
            System.out.println("Change returned: " + change);
        }

    }

    public void setMachineState(MachineState machineState) {
        this.machineState = machineState;
    }

    public Product selectProduct(String productId, int quantity) {
        if (machineState != MachineState.IDLE) {
            throw new IllegalStateException("Vending machine is not in IDLE state");
        }

        Product product = inventory.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (product.getQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient quantity for product: " + productId);
        }
        machineState = MachineState.PROCESSING;

        return product;
    }

    public double purchaseProduct(Product product, double insertedAmount, int quantity) {
        if (machineState != MachineState.PROCESSING) {
            throw new IllegalStateException("Vending machine is not in PROCESSING state");
        }

        BigDecimal totalCost = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        if (insertedAmount < totalCost.doubleValue()) {
            setMachineState(MachineState.IDLE);
            throw new IllegalArgumentException("Inserted amount is less than the total cost");
        }

        dispenseProduct(product, quantity);

        return insertedAmount - totalCost.doubleValue();

    }

    public void dispenseProduct(Product product, int quantity) {
        if (machineState != MachineState.PROCESSING) {
            throw new IllegalStateException("Vending machine is not in PROCESSING state");
        }

        product.updateQuantity(product.getQuantity() - quantity);

        machineState = MachineState.IDLE;
        System.out.println("Product dispensed: " + product);
    }

}
