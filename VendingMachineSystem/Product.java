package VendingMachineSystem;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private String productId;
    private String productName;
    private BigDecimal price;
    private int quantity;

    public Product(String productId, String productName, BigDecimal price, int quantity) {
        this.productId = Objects.requireNonNull(productId, "Product ID cannot be null");
        this.productName = Objects.requireNonNull(productName, "Product name cannot be null");
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        this.price = price;
        if (quantity <= 0 ) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        this.quantity = quantity;
    }


    public void updateQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must be a non-negative value");
        }
        this.quantity = quantity;
        System.out.println("Quantity updated. New quantity: " + this.quantity);
    }

    public void updatePrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        this.price = price;
        System.out.println("Price updated. New price: " + this.price);
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
