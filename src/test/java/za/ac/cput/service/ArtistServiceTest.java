package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import za.ac.cput.domain.Artist;
import za.ac.cput.factory.ArtistFactory;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class ArtistServiceTest {

    @Autowired
    private ArtistService service;

    private static Artist artist1;
    private static Artist artist2;

    @BeforeAll
    static void setUp() {


        String imageUrl = "/digital_artDB/images/art1.jpeg";
        artist1 = ArtistFactory.createArtist(
                "SkynStudios",
                imageUrl,
                "Thinking out",
                LocalDate.now()
        );
        assertNotNull(artist1);
        System.out.println(artist1);

        String imageUrl2 = "/digital_artDB/images/art3.jpeg";

        artist2 = ArtistFactory.createArtist(
                "Summit Art Studio",
                imageUrl2,
                "Creative image",
                LocalDate.now()
        );
        assertNotNull(artist2);
        System.out.println(artist2);
    }

    @Test
    @Order(1)
    void a_create() {
        Artist saved1 = service.create(artist1);
        assertNotNull(saved1);
        System.out.println(saved1);
        System.out.println();

        Artist saved2 = service.create(artist2);
        assertNotNull(saved2);
        System.out.println(saved2);
        System.out.println();
    }

    @Test
    @Order(2)
    void b_read() {
        Artist read = service.read(artist1.getArtistId());
        assertNotNull(read);
        System.out.println(read);
    }

    @Test
    @Order(3)
    void c_update() {
        Artist updatedBrand = new Artist.Builder()
                .copy(artist2)
                .setDescription("Breathtaking mountain view at sunrise")
                .build();
        Artist updated = service.update(updatedBrand);
        assertNotNull(updated);
        System.out.println(updated);
    }

    @Test
    @Order(4)
    void d_delete() {
        service.delete(artist1.getArtistId());
        Artist deleted = service.read(artist1.getArtistId());
        assertNull(deleted);
        System.out.println("Artist deleted successfully");
    }

    @Test
    @Order(5)
    void e_getAll() {
        System.out.println(service.getAll());
    }
}
