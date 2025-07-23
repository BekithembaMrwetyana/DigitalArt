package factory;

/*
ProductFactory.java
Product Factory class
Author: Thimna Gogwana 222213973
Date: 25 May 2025
*/

import domain.Product;
import util.Helper;
import org.springframework.stereotype.Component;

@Component
public class ProductFactory {

    public Product createBasicProduct(String productID, String title, double price) {
        validateBasicProductParameters(productID, title, price);

        return new Product.Builder()
                .setProductID(productID)
                .setTitle(title)
                .setPrice(price)
                .build();
    }

    public Product createFullProduct(String productID, String title, String description,
                                     double price, String categoryID) {
        validateBasicProductParameters(productID, title, price);
        if (!Helper.isValidDescription(description)) {
            throw new IllegalArgumentException("Invalid description");
        }
        if (!Helper.isValidCategoryID(categoryID)) {
            throw new IllegalArgumentException("Invalid category ID: " + categoryID);
        }

        return new Product.Builder()
                .setProductID(productID)
                .setTitle(title)
                .setDescription(description)
                .setPrice(price)
                .setCategoryID(categoryID)
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

    private void validateBasicProductParameters(String productID, String title, double price) {
        if (!Helper.isValidProductID(productID)) {
            throw new IllegalArgumentException("Invalid product ID: " + productID);
        }
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

