package za.ac.cput.service;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.Product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceTest {

    @Autowired
    private IProductService productService;

    @Autowired
    private ICategoryService categoryService;


    static Category digitalArt, portraits, abstractCat, landscape, photography, popArt, surreal, fantasy, streetArt, urban;


    static Product neonDreams, pixelSunrise, portraitAlice, portraitBob, abstractWaves, fantasyForest,
            sculpture3D, abstractArt, auroraFields, dreamMachine, cityScape,purpleMirage, galsticVoyage, monaDiva;

    private Product createIfNotExists(String title, Category category, double price, String imageUrl, String description) {
        return productService.getAll().stream()
                .filter(p -> p.getTitle().equals(title) &&
                        p.getCategory().getCategoryId().equals(category.getCategoryId()))
                .findFirst()
                .orElseGet(() -> productService.create(
                        new Product.Builder()
                                .setTitle(title)
                                .setDescription(description)
                                .setPrice(price)
                                .setImageUrl(imageUrl)
                                .setCategory(category)
                                .build()
                ));
    }

    @BeforeAll
    void setUp() {

        digitalArt = categoryService.create(new Category.Builder()
                .setName("Digital Art")
                .setDescription("Digital artworks")
                .build());

        portraits = categoryService.create(new Category.Builder()
                .setName("Portraits")
                .setDescription("Portrait artworks")
                .build());

        abstractCat = categoryService.create(new Category.Builder()
                .setName("Abstract")
                .setDescription("Abstract artworks")
                .build());

        landscape = categoryService.create(new Category.Builder()
                .setName("Landscape")
                .setDescription("Landscape artworks")
                .build());

        photography = categoryService.create(new Category.Builder()
                .setName("Photography")
                .setDescription("Photography artworks")
                .build());

        popArt = categoryService.create(new Category.Builder()
                .setName("Pop Art")
                .setDescription("Modern pop artworks")
                .build());

        surreal = categoryService.create(new Category.Builder()
                .setName("Surreal")
                .setDescription("Surreal artworks")
                .build());
        fantasy = categoryService.create(new Category.Builder()
                .setName("Fantasy")
                .setDescription("Fantasy artworks")
                .build());
        streetArt = categoryService.create(new Category.Builder()
                .setName("StreetArt")
                .setDescription("StreetArt artworks")
                .build());

        urban = categoryService.create(new Category.Builder()
                .setName("Urban")
                .setDescription("Urban artworks")
                .build());


        neonDreams = productService.create(new Product.Builder()
                .setTitle("Neon Dreams")
                .setDescription("A stunning digital artwork exploring dreams and reality")
                .setPrice(299.99)
                .setImageUrl("/images/art1.jpeg")
                .setCategory(digitalArt)
                .build());

        pixelSunrise = productService.create(new Product.Builder()
                .setTitle("Pixel Sunrise")
                .setDescription("A beautiful sunrise in pixel art style")
                .setPrice(149.99)
                .setImageUrl("/images/art15.jpeg")
                .setCategory(digitalArt)
                .build());

        portraitAlice = productService.create(new Product.Builder()
                .setTitle("Portrait of Alice")
                .setDescription("Digital portrait of Alice")
                .setPrice(199.99)
                .setImageUrl("/images/art3.jpeg")
                .setCategory(portraits)
                .build());

        portraitBob = productService.create(new Product.Builder()
                .setTitle("Expressionist Mind")
                .setDescription("Expressionist linguistic barriers")
                .setPrice(179.99)
                .setImageUrl("/images/art4.jpeg")
                .setCategory(portraits)
                .build());

        abstractWaves = productService.create(new Product.Builder()
                .setTitle("Abstract Waves")
                .setDescription("Abstract artwork with colorful waves")
                .setPrice(249.99)
                .setImageUrl("/images/art13.jpeg")
                .setCategory(digitalArt)
                .build());

        fantasyForest = productService.create(new Product.Builder()
                .setTitle("Fantasy Forest")
                .setDescription("A dreamy digital forest landscape")
                .setPrice(299.99)
                .setImageUrl("/images/art5.jpeg")
                .setCategory(digitalArt)
                .build());


        sculpture3D = productService.create(new Product.Builder()
                .setTitle("3D Sculpture")
                .setDescription("Digital 3D sculpture")
                .setPrice(159.99)
                .setImageUrl("/images/art25.jpeg")
                .setCategory(digitalArt)
                .build());

        abstractArt = productService.create(new Product.Builder()
                .setTitle("Chromatic chaos")
                .setDescription("Colorful abstract design")
                .setPrice(199.99)
                .setImageUrl("/images/art6.jpeg")
                .setCategory(abstractCat)
                .build());

        auroraFields = productService.create(new Product.Builder()
                .setTitle("Aurora fields")
                .setDescription("Beautiful aurora fields landscape")
                .setPrice(179.99)
                .setImageUrl("/images/art9.jpeg")
                .setCategory(landscape)
                .build());

        dreamMachine = productService.create(new Product.Builder()
                .setTitle("Dream machine")
                .setDescription("Fantasy dreams")
                .setPrice(189.99)
                .setImageUrl("/images/art11.jpeg")
                .setCategory(surreal)
                .build());

        purpleMirage = productService.create(new Product.Builder()
                .setTitle("Purple Mirage")
                .setDescription("Nicely done artistic drawing of trees ")
                .setPrice(139.99)
                .setImageUrl("/images/art10.jpeg")
                .setCategory(streetArt)
                .build());

        galsticVoyage = productService.create(new Product.Builder()
                .setTitle("Galstic Voyage")
                .setDescription("A pop of colors for this beautiful voyage")
                .setPrice(99.99)
                .setImageUrl("/images/art14.jpeg")
                .setCategory(popArt)
                .build());

        cityScape = productService.create(new Product.Builder()
                .setTitle("City Scape")
                .setDescription("Beautifully done city scapes")
                .setPrice(129.99)
                .setImageUrl("/images/art7.jpeg")
                .setCategory(popArt)
                .build());

        monaDiva = productService.create(new Product.Builder()
                .setTitle("Mona Diva")
                .setDescription("Diva Mona takes place")
                .setPrice(149.99)
                .setImageUrl("/images/art21.jpeg")
                .setCategory(portraits)
                .build());
    }

    @Test
    @Order(1)
    void testAllProductsCreated() {
        List<Product> all = productService.getAll();
        assertTrue(all.size() >= 14, "Should have at least 14 products seeded");
        all.forEach(System.out::println);
    }

    @Test
    @Order(2)
    void testGetByCategory() {
        List<Product> digitalProducts = productService.getByCategoryId(digitalArt.getCategoryId());
        assertFalse(digitalProducts.isEmpty(), "Digital Art category should not be empty");
        digitalProducts.forEach(p -> System.out.println("Digital Art product: " + p));
    }


    @Test
    @Order(3)
    void testSaveImagesBatch() throws IOException {
        List<Product> products = productService.getAll().subList(0, 3);
        for (Product p : products) {
            String fileName = Paths.get(p.getImageUrl()).getFileName().toString();
            Path path = Paths.get("src/main/resources/static/images/" + fileName);
            assertTrue(Files.exists(path), "Test image must exist: " + path.toAbsolutePath());

            MultipartFile file = new MockMultipartFile(
                    "file",
                    fileName,
                    "image/jpeg",
                    Files.readAllBytes(path)
            );

            Product updated = productService.saveImage(p.getProductID(), file);
            assertNotNull(updated.getImageData());
            assertEquals(p.getImageUrl(), updated.getImageUrl());
            System.out.println("Saved image for product: " + updated.getTitle());
        }
    }


    @Test
    @Order(4)
    void testUpdateProductCopyOnly() {
        Product productToUpdate = abstractWaves;

        Product updated = new Product.Builder()
                .copy(productToUpdate)
                .setPrice(productToUpdate.getPrice() + 50.0)
                .setTitle(productToUpdate.getTitle() + " Updated")
                .build();


        assertEquals(productToUpdate.getPrice() + 50.0, updated.getPrice());
        assertEquals("Abstract Waves Updated", updated.getTitle());
        assertEquals(productToUpdate.getProductID(), updated.getProductID());

        System.out.println("Updated product copy (not persisted): " + updated);
    }


    @Test
    @Order(5)
    void testDeleteProductsBatch() {
        List<Product> productsToDelete = new ArrayList<>(productService.getAll().subList(0, 3));
        for (Product p : productsToDelete) {
            productService.delete(p.getProductID());
            assertNull(productService.read(p.getProductID()), "Product should be deleted: " + p.getTitle());
            System.out.println("Deleted product: " + p.getTitle());
        }
    }
}