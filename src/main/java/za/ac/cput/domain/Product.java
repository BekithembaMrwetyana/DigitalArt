package za.ac.cput.domain;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
Product.java
Product POJO class
Author: Thimna Gogwana (222213973)
Date: 25 May 2025
*/
@Entity
@Table(name = "products")
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private Long productID;

    private String title;
    private String description;
    private double price;

    private String imageUrl;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imageData;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;


    @Transient
    private String imageBase64;

    protected Product() {}

    private Product(Builder builder) {
        this.productID = builder.productID;
        this.category = builder.category;
        this.title = builder.title;
        this.description = builder.description;
        this.price = builder.price;
        this.imageUrl = builder.imageUrl;
        this.imageData = builder.imageData;
        this.imageBase64 = builder.imageBase64;
    }


    public Long getProductID() { return productID; }
    public void setProductID(Long productID) { this.productID = productID; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public byte[] getImageData() { return imageData; }
    public void setImageData(byte[] imageData) { this.imageData = imageData; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public String getImageBase64() { return imageBase64; }
    public void setImageBase64(String imageBase64) { this.imageBase64 = imageBase64; }

    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", categoryID=" + (category != null ? category.getCategoryId() : null) +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageData=" + (imageData != null ? "[BINARY DATA]" : null) +
                ", imageBase64=" + (imageBase64 != null ? "[BASE64]" : null) +
                '}';
    }


    public static class Builder {
        private Long productID;
        private Category category;
        private String title;
        private String description;
        private double price;
        private String imageUrl;
        private byte[] imageData;
        private String imageBase64;

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

        public Builder setImageData(byte[] imageData) {
            this.imageData = imageData;
            return this;
        }

        public Builder setImageBase64(String imageBase64) {
            this.imageBase64 = imageBase64;
            return this;
        }

        public Builder copy(Product product) {
            this.productID = product.productID;
            this.category = product.category;
            this.title = product.title;
            this.description = product.description;
            this.price = product.price;
            this.imageUrl = product.imageUrl;
            this.imageData = product.imageData;
            this.imageBase64 = product.imageBase64;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
