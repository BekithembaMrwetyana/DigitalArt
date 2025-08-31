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
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        Product product = productRepository.findById(id).orElse(null);
        return product != null ? encodeImage(product) : null;
    }

    @Override
    public Product update(Product product) {
        if (product.getProductID() == null || !productRepository.existsById(product.getProductID())) {
            return null;
        }
        Product updated = productRepository.save(product);
        return encodeImage(updated);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getAll() {
        return encodeImages(productRepository.findAll());
    }

    @Override
    public List<Product> getByCategoryId(Long categoryId) {
        if (categoryId == null) return Collections.emptyList();
        return encodeImages(productRepository.findAllByCategory_CategoryId(categoryId));
    }

    public List<Product> getProductsWithoutCategory() {
        return encodeImages(productRepository.findAllByCategoryIsNull());
    }

    @Override
    public List<Product> searchByTitle(String keyword) {
        if (keyword == null || keyword.isEmpty()) return Collections.emptyList();
        return encodeImages(productRepository.findByTitleContainingIgnoreCase(keyword));
    }

    @Override
    public List<Product> filterByPrice(double minPrice, double maxPrice) {
        return encodeImages(productRepository.findByPriceBetween(minPrice, maxPrice));
    }

    @Override
    public List<Product> filterByMaxPrice(double maxPrice) {
        return encodeImages(productRepository.findByPriceLessThanEqual(maxPrice));
    }

    // ---------------- Image Upload ----------------
    @Override
    public Product saveImage(Long productId, MultipartFile file) throws IOException {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) return null;

        String filename = generateUniqueFilename(file);

        // Absolute path to static/images folder
        Path uploadDir = Paths.get("src/main/resources/static/images");
        Files.createDirectories(uploadDir);

        Path filePath = uploadDir.resolve(filename);
        Files.write(filePath, file.getBytes());

        // Set relative URL for the frontend
        product.setImageUrl("/images/" + filename);
        return encodeImage(update(product));
    }

    // ---------------- Base64 Encoding ----------------
    private Product encodeImage(Product product) {
        if (product.getImageUrl() != null) {
            try {
                Path path = Paths.get("src/main/resources/static/images", product.getImageUrl().replace("/images/", ""));
                if (Files.exists(path)) {
                    byte[] bytes = Files.readAllBytes(path);
                    product.setImage(Base64.getEncoder().encodeToString(bytes));
                }
            } catch (IOException e) {
                product.setImage(null);
            }
        }
        return product;
    }

    private List<Product> encodeImages(List<Product> products) {
        return products.stream()
                .map(this::encodeImage)
                .collect(Collectors.toList());
    }
}
