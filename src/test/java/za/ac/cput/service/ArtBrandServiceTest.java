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

        String imageUrl = "https://i.natgeofe.com/n/9a6e5e2e-5e7f-4308-a33e-64cf1978a649/42-18269780_square.jpg".trim();
        brand1 = ArtBrandFactory.createArtBrand(
                "ZeebrahStudios",
                imageUrl,
                "Zebras running, having fun",
                LocalDate.now()
        );
        assertNotNull(brand1);
        System.out.println(brand1);

        String imageUrl2 = "https://images.pexels.com/photos/33244771/pexels-photo-33244771/free-photo-of-majestic-mountain-peak-at-sunrise-with-soft-clouds.jpeg?auto=compress&cs=tinysrgb&w=800".trim();

         brand2 = ArtBrandFactory.createArtBrand(
                "Summit Art Studio",
                imageUrl2,
                "Majestic mountain peak at sunrise with clouds",
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
                .setDescription("Breathtaking mountain view at sunrise")
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
