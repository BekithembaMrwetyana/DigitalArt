package za.ac.cput.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryID;

    @ManyToOne
    @JoinColumn(name = "product_product_id")
    private Product product;

    private int quantity;


    protected Inventory() {}

    public Inventory(Builder builder) {
        this.inventoryID = builder.inventoryID;
        this.product = builder.product;
        this.quantity = builder.quantity;

    }

    // Getters
    public Long getInventoryID() { return inventoryID; }
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }



    public static class Builder {
        private Long inventoryID;
        private Product product;
        private int quantity;


        public Builder setInventoryID(Long inventoryID) { this.inventoryID = inventoryID; return this; }
        public Builder setProduct(Product product) { this.product = product; return this; }
        public Builder setQuantity(int quantity) { this.quantity = quantity; return this; }


        public Builder copy(Inventory inventory) {
            this.inventoryID = inventory.inventoryID;
            this.product = inventory.product;
            this.quantity = inventory.quantity;

            return this;
        }

        public Inventory build() { return new Inventory(this); }
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "inventoryID=" + inventoryID +
                ", product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
