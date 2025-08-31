package za.ac.cput.factory;

/*
ProductFactory.java
Product Factory class
Author: Thimna Gogwana 222213973
Date: 25 May 2025
*/

import za.ac.cput.domain.Product;
import za.ac.cput.util.Helper;
import org.springframework.stereotype.Component;

@Component
public class ProductFactory {

    public Product createBasicProduct(String title, double price) {
        validateBasicProductParameters(title, price);

        return new Product.Builder()
                .setTitle(title)
                .setPrice(price)
                .build();
    }

    public Product createFullProduct(String title, String description,
                                     double price, String categoryID) {
        validateBasicProductParameters(title, price);
        if (!Helper.isValidDescription(description)) {
            throw new IllegalArgumentException("Invalid description");
        }
        if (!Helper.isValidCategoryID(categoryID)) {
            throw new IllegalArgumentException("Invalid category ID: " + categoryID);
        }

        return new Product.Builder()
                .setTitle(title)
                .setDescription(description)
                .setPrice(price)
                //.setCategoryID(categoryID)
                .build();
    }

    public Product createCopy(Product originalProduct) {
        if (originalProduct == null) {
            throw new IllegalArgumentException("Original product cannot be null");
        }
        validateProduct(originalProduct);

        return new Product.Builder()
                .copy(originalProduct)
                .build();
    }

    private void validateBasicProductParameters(String title, double price) {

        if (!Helper.isValidTitle(title)) {
            throw new IllegalArgumentException("Invalid title: " + title);
        }
        if (!Helper.isValidPrice(price)) {
            throw new IllegalArgumentException("Invalid price: " + price);
        }
    }

    private void validateProduct(Product product) {
        if (!Helper.isValidProduct(product)) {
            throw new IllegalArgumentException("Invalid product: " + product);
        }
    }
}

