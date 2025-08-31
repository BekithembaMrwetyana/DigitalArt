package za.ac.cput.service;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.Product;
import za.ac.cput.factory.ProductFactory;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class ProductServiceTest {

    @Autowired
    private IProductService productService;

    @Autowired
    private ProductFactory productFactory;

    private static Product product1;
    private static Product product2;

    private static Category category1;
    private static Category category2;

    @BeforeAll
    static void setUp(@Autowired ProductFactory productFactory) {
        category1 = new Category.Builder()
                .setCategoryId(1001L)
                .setName("Portraits")
                .setDescription("Portrait artworks")
                .build();

        category2 = new Category.Builder()
                .setCategoryId(1002L)
                .setName("Abstract")
                .setDescription("Abstract artworks")
                .build();

        product1 = productFactory.create(1L, category1, "Portrait Art", "Digital portrait of a person", 150.0);
        product2 = productFactory.create(2L, category2, "Abstract Art", "Colorful abstract design", 200.0);
    }

    @Test
    void a_create() {
        Product created1 = productService.create(product1);
        Product created2 = productService.create(product2);

        assertNotNull(created1);
        assertNotNull(created2);
        assertEquals(category1.getCategoryId(), created1.getCategory().getCategoryId());
        assertEquals(category2.getCategoryId(), created2.getCategory().getCategoryId());
        System.out.println("Created products: " + created1 + ", " + created2);
    }

    @Test
    void b_read() {
        Product read = productService.read(product1.getProductID());
        assertNotNull(read);
        assertEquals(150.0, read.getPrice());
        assertEquals(category1.getCategoryId(), read.getCategory().getCategoryId());
        System.out.println("Read product: " + read);
    }

    @Test
    void c_update() {
        Product updated = new Product.Builder()
                .copy(product2)
                .setPrice(250.0)
                .build();
        Product result = productService.update(updated);
        assertNotNull(result);
        assertEquals(250.0, result.getPrice());
        assertEquals(category2.getCategoryId(), result.getCategory().getCategoryId());
        System.out.println("Updated product: " + result);
    }

    @Test
    void d_getAll() {
        List<Product> all = productService.getAll();
        assertFalse(all.isEmpty());
        System.out.println("All products: " + all);
    }
    @Test
    void e_delete() {
        productService.delete(product1.getProductID()); // delete product1

        Product deleted = productService.read(product1.getProductID());
        assertNull(deleted, "Deleted product should not be found");

        System.out.println(" Deleted product with ID: " + product1.getProductID());
    }

    @Test
    void f_getByCategory() {
        List<Product> byCategory = productService.getByCategory(category1);
        assertFalse(byCategory.isEmpty());
        assertEquals(category1.getCategoryId(), byCategory.get(0).getCategory().getCategoryId());
        System.out.println("Products by category " + category1.getCategoryId() + ": " + byCategory);
    }

    @Test
    void g_searchByTitle() {
        List<Product> found = productService.searchByTitle("Portrait");
        assertFalse(found.isEmpty());
        System.out.println("Products found with 'Portrait': " + found);
    }

    @Test
    void h_filterByPrice() {
        List<Product> filtered = productService.filterByPrice(100.0, 200.0);
        assertFalse(filtered.isEmpty());
        System.out.println("Products between 100 and 200: " + filtered);
    }

    @Test
    void i_filterByMaxPrice() {
        List<Product> filtered = productService.filterByMaxPrice(200.0);
        assertFalse(filtered.isEmpty());
        System.out.println("Products with price <= 200: " + filtered);
    }

}