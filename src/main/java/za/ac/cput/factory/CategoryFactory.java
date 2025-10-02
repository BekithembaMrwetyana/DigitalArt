package za.ac.cput.factory;
/*
Category.java
Category Factory
Author: Abethu Ngxitho 221297820
Date: 18 May 2025
*/

import za.ac.cput.domain.Category;
import za.ac.cput.util.Helper;



public class CategoryFactory {

    public static Category createCategory(String name, String description) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }


        return new Category.Builder()
                .setName(name)
                .setDescription(description)
                .build();
    }
}
