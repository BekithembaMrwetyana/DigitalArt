package za.ac.cput.dto;

import za.ac.cput.domain.Product;

/**
 * ProductDtoO
 * Lightweight data transfer object for Product entity.
 * Includes a dynamically generated download link for digital art.
 *
 * Author: Mpilo Mzimela
 * Date: 06 October 2025
 */
public class ProductDto {

    private Long id;
    private String title;
    private String description;
    private double price;
    private String imageUrl;
    private String categoryName;
    private String downloadUrl; // 👈 not stored in DB — generated dynamically

    // Constructors
    public ProductDto() {}

    // Factory method to convert from entity
    public static ProductDto fromEntity(Product product) {
        ProductDto dto = new ProductDto();
        dto.id = product.getProductID();
        dto.title = product.getTitle();
        dto.description = product.getDescription();
        dto.price = product.getPrice();
        dto.imageUrl = product.getImageUrl();

        if (product.getCategory() != null) {
            dto.categoryName = product.getCategory().getName();
        }

        // ✅ Dynamically generate the download link (no need to modify Product entity)
        dto.downloadUrl = "http://localhost:8080/digital_artDB/api/products/download/"
                + product.getTitle().toLowerCase().replaceAll("\\s+", "-");

        return dto;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    @Override
    public String toString() {
        return "ProductDtoO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", category='" + categoryName + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                '}';
    }
}
