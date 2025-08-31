package za.ac.cput.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.Product;

@Component
public class DataInitializer implements CommandLineRunner {

    private final IProductService productService;
    private final ICategoryService categoryService;

    public DataInitializer(IProductService productService, ICategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if products already exist
        if (!productService.getAll().isEmpty()) {
            System.out.println("Products already exist, skipping seeding.");
            return;
        }

        // Create categories
        Category digitalArt = new Category.Builder()
                .setName("Digital Art")
                .setDescription("Digital artwork and illustrations")
                .build();
        digitalArt = categoryService.create(digitalArt);

        Category portraits = new Category.Builder()
                .setName("Portraits")
                .setDescription("Portrait artworks")
                .build();
        portraits = categoryService.create(portraits);

        // Create 6 products
        productService.create(new Product.Builder()
                .setTitle("Neon Dreams")
                .setDescription("A stunning digital artwork exploring dreams and reality")
                .setPrice(299.99)
                .setImageUrl("/images/art1.jpeg")
                .setCategory(digitalArt)
                .build());

        productService.create(new Product.Builder()
                .setTitle("Pixel Sunrise")
                .setDescription("A beautiful sunrise in pixel art style")
                .setPrice(149.99)
                .setImageUrl("/images/art2.jpeg")
                .setCategory(digitalArt)
                .build());

        productService.create(new Product.Builder()
                .setTitle("Portrait of Alice")
                .setDescription("Digital portrait of Alice")
                .setPrice(199.99)
                .setImageUrl("/images/art3.jpeg")
                .setCategory(portraits)
                .build());

        productService.create(new Product.Builder()
                .setTitle("Portrait of Bob")
                .setDescription("Digital portrait of Bob")
                .setPrice(179.99)
                .setImageUrl("/images/art4.jpeg")
                .setCategory(portraits)
                .build());

        productService.create(new Product.Builder()
                .setTitle("Abstract Waves")
                .setDescription("Abstract artwork with colorful waves")
                .setPrice(249.99)
                .setImageUrl("/images/art5.jpeg")
                .setCategory(digitalArt)
                .build());

        productService.create(new Product.Builder()
                .setTitle("Fantasy Forest")
                .setDescription("A dreamy digital forest landscape")
                .setPrice(299.99)
                .setImageUrl("/images/art6.jpeg")
                .setCategory(digitalArt)
                .build());

        System.out.println("Seeded 6 products and 2 categories!");
    }
}

