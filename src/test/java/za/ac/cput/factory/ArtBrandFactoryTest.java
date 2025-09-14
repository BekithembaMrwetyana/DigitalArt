package za.ac.cput.factory;

import org.junit.jupiter.api.*;
import za.ac.cput.domain.ArtBrand;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ArtBrandFactoryTest {

    private ArtBrand brand1;

    @BeforeAll
    void setUp() {

        String imageUrl = "https://i.natgeofe.com/n/9a6e5e2e-5e7f-4308-a33e-64cf1978a649/42-18269780_square.jpg".trim();

        brand1 = ArtBrandFactory.createArtBrand(
                "ZeebrahStudios",
                imageUrl,
                "Zebras running, having fun",
                LocalDate.now()
        );
    }

    @Test
    @Order(1)
    void testCreateValidArtBrand() {
        assertNotNull(brand1, "brand1 should not be null");
        assertNotNull(brand1.getBrandId(), "brand1 ID should not be null");
        assertEquals("Zeebrah", brand1.getBrandName());
        assertEquals("Zebras running, having fun", brand1.getDescription());


        String expectedUrl = "https://i.natgeofe.com/n/9a6e5e2e-5e7f-4308-a33e-64cf1978a649/42-18269780_square.jpg";
        assertEquals(expectedUrl, brand1.getImageUrl(), "Image URL should match expected");

        System.out.println("Valid ArtBrand created: " + brand1);
    }

    @Test
    @Order(2)
    void testCreateArtBrandWithInvalidDescription() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ArtBrandFactory.createArtBrand(
                    "The Matrix",
                    "https://images.hdqwalls.com/download/bio-hackers-and-the-matrix-4k-6p-1920x1080.jpg".trim(),
                    "", // Invalid: empty description
                    LocalDate.now()
            );
        });

        assertEquals("Description is required", exception.getMessage());
        System.out.println("Expected exception: " + exception.getMessage());
    }





    @Test
    @Order(5)
    @Disabled("Not yet implemented")
    void testCreateArtBrandThatNotYetImplemented() {
    }
}