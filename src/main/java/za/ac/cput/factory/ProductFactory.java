package za.ac.cput.factory;

/*
ProductFactory.java
Product Factory class
Author: Thimna Gogwana 222213973
Date: 25 May 2025
*/

import za.ac.cput.domain.Category;
import za.ac.cput.domain.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductFactory {
    public Product create(Long productID, Category category, String title, String description, double price, String imageFileName) {
        return new Product.Builder()
                .setProductID(productID)
                .setCategory(category)
                .setTitle(title)
                .setDescription(description)
                .setPrice(price)
                .setImageUrl("/images/" + imageFileName)
                .build();
    }

    public Product copy(Product product) {
        return new Product.Builder()
                .copy(product)
                .build();
    }

}