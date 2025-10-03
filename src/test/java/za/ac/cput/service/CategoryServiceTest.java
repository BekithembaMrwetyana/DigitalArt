package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Category;
import za.ac.cput.factory.CategoryFactory;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class CategoryServiceTest {

    @Autowired
    private  CategoryService service ;

    private static Category category1;
    private static Category category2;

    @BeforeAll
    static void setUp() {
        category1= CategoryFactory.createCategory("Potraits" , "Digital art showing a person's face or expression");
        System.out.println(category1);

        category2= CategoryFactory.createCategory("Abstract Art" , "Digital art conveys feelings through shapes and colors.");
        System.out.println(category2);
    }




    @Test
    @Order(1)
    void a_create() {
        Category firstCategory = service.create(category1);
        assertNotNull(firstCategory);
        System.out.println(firstCategory);
        System.out.println();

        Category secondCategory = service.create(category2);
        assertNotNull(secondCategory);
        System.out.println(secondCategory);
        System.out.println();
    }

    @Test
    @Order(2)
    void b_read() {
        Category read = service.read(category1.getCategoryId());
        assertNotNull(read);
        System.out.println(read);

    }

    @Test
    @Order(3)
    void d_update() {
        Category newCategory= new Category.Builder().copy(category2).setDescription("A calming digital artwork of a sunset over the ocean.")
                .build();
        Category updated = service.update(newCategory);
        assertNotNull(updated);
        System.out.println(updated);
    }

    @Test
    @Order(4)
    void e_delete() {
        service.delete(category1.getCategoryId());
        Category deleted = service.read(category1.getCategoryId());
        assertNull(deleted);
        System.out.println("Category deleted successfully");

    }

    @Test
    @Order(5)
    void e_getAll() {
        System.out.println( service.getAll());
    }
}