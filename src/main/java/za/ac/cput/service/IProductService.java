package za.ac.cput.service;


import org.springframework.web.multipart.MultipartFile;
import za.ac.cput.domain.Product;

import java.io.IOException;
import java.util.List;


public interface IProductService extends IService<Product, Long> {
    List<Product> getByCategoryId(Long category);

    List<Product> searchByTitle(String keyword);

    List<Product> filterByPrice(double minPrice, double maxPrice);

    List<Product> filterByMaxPrice(double maxPrice);

    Product saveImage(Long productId, MultipartFile file) throws IOException;
}
