package domain;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
/*
Product.java
Product POJO class
Author: Thimna Gogwana (222213973)
Date: 25 May 2025
*/
@Entity
@Table
public class  Product {
    @Id
    private String productID;
    private String categoryID;
    private String title;
    private String description;
    private double price;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;

    public Product(){
    }

    public Product (Builder builder){
        this.productID = builder.productID;
        this.title = builder.title;
        this.description = builder.description;
        this.price = builder.price;
        this.categoryID = builder.categoryID;

    }


    public String getProductID() {
        return productID;
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

    public String getCategoryID() {
        return categoryID;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productID='" + productID + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", categoryID='" + categoryID + '\'' +
                '}';
    }


    public static class Builder{

        private String productID;
        private String title;
        private String description;
        private double price;
        private String categoryID;

        public Builder setProductID(String productID) {
            this.productID = productID;
            return this;
        }

        public Builder setCategoryID(String categoryID) {
            this.categoryID = categoryID;
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

        public Builder copy(Product product){
            this.productID = product.productID;
            this.categoryID = product.categoryID;
            this.title = product.title;
            this.description = product.description;
            this.price = product.price;
            return this;
        }
        public Product build(){
            return new Product(this);
        }
    }

}
