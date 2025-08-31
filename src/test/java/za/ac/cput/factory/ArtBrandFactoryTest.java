package za.ac.cput.factory;

import org.junit.jupiter.api.*;
import za.ac.cput.domain.ArtBrand;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ArtBrandFactoryTest {

    private static final ArtBrand brand1 = ArtBrandFactory.createArtBrand(
            "Zeebrah",
            "https://sl.bing.net/iQ2c6HPUr6W",
            "Zebras running, having fun",
            LocalDate.now()
    );

    @Test
    @Order(1)
    void testCreateValidArtBrand() {
        assertNotNull(brand1, "brand1 should not be null");
        System.out.println("brand1: " + brand1);
    }

    @Test
    @Order(2)
    void testCreateArtBrandWithInvalidDescription() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                ArtBrandFactory.createArtBrand(
                        "The Matrix",
                        "https://images.hdqwalls.com/download/bio-hackers-and-the-matrix-4k-6p-1920x1080.jpg",
                        "",
                        LocalDate.now()
                )
        );
        assertEquals("Description is required", exception.getMessage());
        System.out.println("brand2 creation failed due to invalid description: " + exception.getMessage());
    }

    @Test
    @Order(3)
    @Disabled("Not yet implemented")
    void testCreateArtBrandThatNotYetImplemented() {
    }
}
