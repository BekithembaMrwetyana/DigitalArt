package za.ac.cput.factory;

import org.junit.jupiter.api.*;
import za.ac.cput.domain.Artist;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ArtistFactoryTest {

    private Artist artist;

    @Test
    @Order(1)
    void testCreateValidArtist() {
        artist = ArtistFactory.createArtist(
                "ZeeStudios",
                "http://localhost:8080/digital_artDB/images/art18.jpeg",
                "Creative image done by Zeeh from KZN from valleys of Dududu, who is passionate about art",
                LocalDate.now()
        );

        System.out.println("Created Artist: " + artist);

        assertNotNull(artist, "Artist should not be null");
        assertEquals("ZeeStudios", artist.getArtistName(), "Artist name mismatch");
        assertNotNull(artist.getCreationDate(), "Creation date should not be null");
    }

    @Test
    @Order(2)
    void testCreateArtistWithInvalidName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                ArtistFactory.createArtist(
                        "",
                        "http://localhost:8080/digital_artDB/images/art18.jpeg",
                        "Description",
                        LocalDate.now()
                )
        );

        System.out.println("Invalid name test passed: " + exception.getMessage());
    }

    @Test
    @Order(3)
    void testCreateArtistWithInvalidImageUrl() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                ArtistFactory.createArtist(
                        "ZeeStudios",
                        "",
                        "Description",
                        LocalDate.now()
                )
        );

        System.out.println("Invalid image URL test passed: " + exception.getMessage());
    }

    @Test
    @Order(4)
    void testCreateArtistWithFutureDate() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                ArtistFactory.createArtist(
                        "ZeeStudios",
                        "http://localhost:8080/digital_artDB/images/art18.jpeg",
                        "Description",
                        LocalDate.now().plusDays(1) // Future date
                )
        );

        System.out.println("Future date test passed: " + exception.getMessage());
    }
}
