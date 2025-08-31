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

        //String categoryId = Helper.generateId();

       if(Helper.isNullOrEmpty(name) || Helper.isNullOrEmpty(description)) {
           return null;
       }


        return new Category.Builder()
                .setName(name)
                .setDescription(description)
                .build();
    }
}
