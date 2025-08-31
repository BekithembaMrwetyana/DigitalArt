package za.ac.cput.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import za.ac.cput.domain.Product;
import za.ac.cput.repository.ProductRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    // Helper method to generate unique filename
    private String generateUniqueFilename(MultipartFile file) {
        return UUID.randomUUID() + "_" + file.getOriginalFilename();
    }


    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product read(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product update(Product product) {
        if (product.getProductID() == null || !productRepository.existsById(product.getProductID())) {
            return null;
        }
        return productRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    // ---- Updated methods ----

    @Override
    public List<Product> getByCategoryId(Long categoryId) {
        if (categoryId == null) {
            return Collections.emptyList();
        }
        List<Product> products = productRepository.findAllByCategory_CategoryId(categoryId);
        return products != null ? products : Collections.emptyList();
    }

    @Override
    public List<Product> searchByTitle(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return Collections.emptyList();
        }
        return productRepository.findByTitleContainingIgnoreCase(keyword);
    }

    @Override
    public List<Product> filterByPrice(double minPrice, double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    @Override
    public List<Product> filterByMaxPrice(double maxPrice) {
        return productRepository.findByPriceLessThanEqual(maxPrice);
    }

    // Optional: get products without category for fallback
    public List<Product> getProductsWithoutCategory() {
        return productRepository.findAllByCategoryIsNull();
    }
    public Product saveImage(Long productId, MultipartFile file) throws IOException {
        Product product = read(productId);
        if (product == null) return null;

        String filename = generateUniqueFilename(file);

        // Absolute path to the static/images folder
        Path uploadDir = Paths.get("src/main/resources/static/images");
        Files.createDirectories(uploadDir);

        Path filePath = uploadDir.resolve(filename);
        Files.write(filePath, file.getBytes());

        // Set relative URL for the frontend
        product.setImageUrl("/images/" + filename);
        return update(product);
    }


}

