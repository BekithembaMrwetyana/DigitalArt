package za.ac.cput.service;
import jakarta.transaction.Transactional;
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
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public Product create(Product product) {
        try {
            if (product.getImageUrl() != null && (product.getImageData() == null || product.getImageData().length == 0)) {
                String fileName = Paths.get(product.getImageUrl()).getFileName().toString();
                Path path = Paths.get("src/main/resources/static/images/" + fileName);
                if (Files.exists(path)) {
                    product.setImageData(Files.readAllBytes(path));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image for product: " + product.getTitle(), e);
        }

        return encodeImage(productRepository.save(product));
    }

    @Override
    public Product read(Long id) {
        return productRepository.findById(id)
                .map(this::encodeImage)
                .orElse(null);
    }

    @Override
    public Product update(Product product) {
        if (product.getProductID() == null || !productRepository.existsById(product.getProductID())) {
            return null;
        }
        return encodeImage(productRepository.save(product));
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


    @Override
    @Transactional
    public Product saveImage(Long productId, MultipartFile file) throws IOException {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) return null;

        byte[] bytes = file.getBytes();
        if (bytes.length == 0) throw new IllegalArgumentException("File is empty");

        product.setImageData(bytes);


        Product saved = productRepository.save(product);


        return encodeImage(saved);
    }


    private Product encodeImage(Product product) {
        if (product.getImageData() != null) {
            product.setImageBase64(Base64.getEncoder().encodeToString(product.getImageData()));
        } else {
            product.setImageBase64(null);
        }
        return product;
    }

    private List<Product> encodeImages(List<Product> products) {
        return products.stream()
                .map(this::encodeImage)
                .collect(Collectors.toList());
    }
}