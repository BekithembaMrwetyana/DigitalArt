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
    /**
     * Create a new Product with given parameters.
     *
     * @param productID     optional product ID (can be null for new products)
     * @param category      associated Category
     * @param title         product title
     * @param description   product description
     * @param price         product price
     * @param imageFileName relative file name (stored in /images/)
     * @return Product instance
     */
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

    /**
     * Create a copy of an existing Product.
     *
     * @param product the original Product
     * @return a new Product instance with same data
     */
    public Product copy(Product product) {
        return new Product.Builder()
                .copy(product)
                .build();
    }

<<<<<<< HEAD
}
=======
    public Product withBase64Image(Product product, String base64Image) {
        return new Product.Builder()
                .copy(product)
                .setImage(base64Image)
                .build();
    }
}
>>>>>>> f4960f5feb3b10bde06d7ae53c265dd2644e9506
