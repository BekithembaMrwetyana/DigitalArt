package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.ArtBrand;
import za.ac.cput.factory.ArtBrandFactory;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class ArtBrandServiceTest {

    @Autowired
    private ArtBrandService service;

    private static ArtBrand brand1;
    private static ArtBrand brand2;

    @BeforeAll
    static void setUp() {
        brand1 = ArtBrandFactory.createArtBrand(
                "Zeebrah",
                "https://sl.bing.net/iQ2c6HPUr6W",
                "Zebras running, having fun",
                LocalDate.now()
        );
        assertNotNull(brand1);
        System.out.println(brand1);

        brand2 = ArtBrandFactory.createArtBrand(
                "The Matrix",
                "https://images.hdqwalls.com/download/bio-hackers-and-the-matrix-4k-6p-1920x1080.jpg",
                "Matrix picture",
                LocalDate.now()
        );
        assertNotNull(brand2);
        System.out.println(brand2);
    }

    @Test
    @Order(1)
    void a_create() {
        ArtBrand saved1 = service.create(brand1);
        assertNotNull(saved1);
        System.out.println(saved1);
        System.out.println();

        ArtBrand saved2 = service.create(brand2);
        assertNotNull(saved2);
        System.out.println(saved2);
        System.out.println();
    }

    @Test
    @Order(2)
    void b_read() {
        ArtBrand read = service.read(brand1.getBrandId());
        assertNotNull(read);
        System.out.println(read);
    }

    @Test
    @Order(3)
    void c_update() {
        ArtBrand updatedBrand = new ArtBrand.Builder()
                .copy(brand2)
                .setDescription("Updated Matrix poster")
                .build();
        ArtBrand updated = service.update(updatedBrand);
        assertNotNull(updated);
        System.out.println(updated);
    }

    @Test
    @Order(4)
    void d_delete() {
        service.delete(brand1.getBrandId());
        ArtBrand deleted = service.read(brand1.getBrandId());
        assertNull(deleted);
        System.out.println("Brand1 deleted successfully");
    }

    @Test
    @Order(5)
    void e_getAll() {
        System.out.println(service.getAll());
    }
}
