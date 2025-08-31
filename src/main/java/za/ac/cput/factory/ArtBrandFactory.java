package za.ac.cput.factory;

import za.ac.cput.domain.ArtBrand;
import java.time.LocalDate;

public class ArtBrandFactory {

    public static ArtBrand createArtBrand(String brandName, String imageUrl, String description, LocalDate creationDate) {

        // Validate brand name
        if (brandName == null || brandName.isBlank()) {
            throw new IllegalArgumentException("Brand name is required");
        }

        // Validate image URL
        if (imageUrl == null || imageUrl.isBlank()) {
            throw new IllegalArgumentException("Image URL is required");
        }

        // Validate description
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description is required");
        }

        // Validate creation date
        if (creationDate == null || creationDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Creation date is invalid");
        }

        return new ArtBrand.Builder()
                .setBrandName(brandName)
                .setImageUrl(imageUrl)
                .setDescription(description)
                .setCreationDate(creationDate)
                .build();
    }
}
