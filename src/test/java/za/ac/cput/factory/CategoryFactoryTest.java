package za.ac.cput.factory;
/*
Category.java
Category FactoryTest
Author: Abethu Ngxitho 221297820
Date: 18 May 2025
*/

import za.ac.cput.domain.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class CategoryFactoryTest {

    @Test
    void createValidCategory() {
        Category category = CategoryFactory.createCategory(
                "Portraits",
                "Digital art showing a person's face or expression"
        );
        assertNotNull(category);
        assertEquals("Portraits", category.getName());
        assertEquals("Digital art showing a person's face or expression", category.getDescription());
        System.out.println(category);
    }

    @Test
    void createCategoryWithNullName_ShouldFail() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CategoryFactory.createCategory(null, "Digital art using shapes and colors");
        });
        assertEquals("Category name cannot be null or empty", exception.getMessage());
    }

    @Test
    void createCategoryWithEmptyName_ShouldFail() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CategoryFactory.createCategory("", "Empty name test");
        });
        assertEquals("Category name cannot be null or empty", exception.getMessage());
    }

    @Test
    void createCategoryWithEmptyDescription_ShouldPass() {
        Category category = CategoryFactory.createCategory("Wall Art", "");
        assertNotNull(category);
        assertEquals("Wall Art", category.getName());
        assertEquals("", category.getDescription());
        System.out.println(category);
    }
}