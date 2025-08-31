package za.ac.cput.domain;

import jakarta.persistence.*;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
Product.java
Product POJO class
Author: Thimna Gogwana (222213973)
Date: 25 May 2025
*/
@Entity
@Table(name = "products")
@JsonIgnoreProperties({"orderItems", "hibernateLazyInitializer"})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private Long productID;

    private String title;
    private String description;
    private double price;
    private String imageUrl; // stores relative pathway for images


    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;

    protected Product() {
    }

    private Product(Builder builder) {
        this.productID = builder.productID;
        this.category = builder.category;
        this.title = builder.title;
        this.description = builder.description;
        this.price = builder.price;
        this.imageUrl = builder.imageUrl;
    }
    // Add these setter methods
    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getProductID() {
        return productID;
    }

    public Category getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getImageUrl() { return imageUrl; }

    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", categoryID=" + (category != null ? category.getCategoryId() : null) +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    public static class Builder {
        private Long productID;
        private Category category;
        private String title;
        private String description;
        private double price;
        private String imageUrl;

        public Builder setProductID(Long productID) {
            this.productID = productID;
            return this;
        }

        public Builder setCategory(Category category) {
            this.category = category;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setPrice(double price) {
            this.price = price;
            return this;
        }
        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder copy(Product product) {
            this.productID = product.productID;
            this.category = product.category;
            this.title = product.title;
            this.description = product.description;
            this.price = product.price;
            this.imageUrl = product.imageUrl;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}

