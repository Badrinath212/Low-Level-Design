package VendingMachineSystem;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.List;

public class VendingMachine {
    private final List<Product> inventory;
    private MachineState machineState = MachineState.IDLE;
    private Product selectedProduct;
    private int selectedQuantity;

    public VendingMachine(List<Product> inventory) {
        this.inventory = List.copyOf(Objects.requireNonNull(inventory, "Inventory cannot be null"));
    }

    public static void main(String[] args) {
        VendingMachine vendingMachine = new VendingMachine(
                                    List.of(new Product("001", "Coke", new BigDecimal("20"), 10), 
                                    new Product("002", "Pepsi", new BigDecimal("20"), 10)));

        System.out.println("Vending Machine initialized with state: " + vendingMachine.machineState);

        vendingMachine.selectProduct("001", 5);
        BigDecimal change = vendingMachine.purchaseProduct(new BigDecimal("100"));
        System.out.println("Change returned: " + change);
    }

    public MachineState getMachineState() {
        return machineState;
    }

    public Product selectProduct(String productId, int quantity) {
        if (machineState != MachineState.IDLE) {
            throw new IllegalStateException("Vending machine is not in IDLE state");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be a positive value");
        }

        Product product = inventory.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (product.getQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient quantity for product: " + productId);
        }

        selectedProduct = product;
        selectedQuantity = quantity;
        machineState = MachineState.PROCESSING;

        return product;
    }

    public BigDecimal purchaseProduct(BigDecimal insertedAmount) {
        if (machineState != MachineState.PROCESSING) {
            throw new IllegalStateException("Vending machine is not in PROCESSING state");
        }

        Objects.requireNonNull(insertedAmount, "Inserted amount cannot be null");
        if (insertedAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Inserted amount cannot be negative");
        }

        if (selectedProduct.getQuantity() < selectedQuantity) {
            resetTransaction();
            throw new IllegalStateException("Insufficient quantity for product: " + selectedProduct.getProductId());
        }

        BigDecimal totalCost = selectedProduct.getPrice().multiply(BigDecimal.valueOf(selectedQuantity));
        if (insertedAmount.compareTo(totalCost) < 0) {
            resetTransaction();
            throw new IllegalArgumentException("Inserted amount is less than the total cost");
        }

        Product dispensedProduct = selectedProduct;
        dispensedProduct.updateQuantity(dispensedProduct.getQuantity() - selectedQuantity);
        System.out.println("Product dispensed: " + dispensedProduct);
        BigDecimal change = insertedAmount.subtract(totalCost);
        resetTransaction();
        return change;
    }

    private void resetTransaction() {
        selectedProduct = null;
        selectedQuantity = 0;
        machineState = MachineState.IDLE;
    }
}
