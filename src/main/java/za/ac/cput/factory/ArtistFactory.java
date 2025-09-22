package za.ac.cput.factory;

import za.ac.cput.domain.Artist;
import java.time.LocalDate;

public class ArtistFactory {

    public static Artist createArtist(String artistName, String imageUrl, String description, LocalDate creationDate) {

        // Validate  name
        if (artistName == null || artistName.isBlank()) {
            throw new IllegalArgumentException("Artist name is required");
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

        // ✅ Use the Builder
        return new Artist.Builder()
                .setArtistName(artistName)
                .setImageUrl(imageUrl)
                .setDescription(description)
                .setCreationDate(creationDate)
                .build();
    }
}
