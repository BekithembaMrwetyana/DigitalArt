package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import za.ac.cput.domain.ArtBrand;
import za.ac.cput.factory.ArtBrandFactory;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ArtBrandControllerTest {

    private static ArtBrand artBrand;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "/art_brands";

    @BeforeAll
    void setup() {
        artBrand = ArtBrandFactory.createArtBrand(
                "Zeebrah",
                "https://sl.bing.net/iQ2c6HPUr6W",
                "zebras running",
                LocalDate.now()
        );
    }

    @Test
    @Order(1)
    void a_create() {
        String url = BASE_URL + "/create";
        ArtBrand createdArtBrand = restTemplate.postForObject(url, artBrand, ArtBrand.class);

        assertNotNull(createdArtBrand);
        assertEquals(artBrand.getBrandName(), createdArtBrand.getBrandName());

        artBrand = createdArtBrand;
        System.out.println("Created: " + createdArtBrand);
    }

    @Test
    @Order(2)
    void b_read() {
        String url = BASE_URL + "/read/" + artBrand.getBrandId();
        ResponseEntity<ArtBrand> response = restTemplate.getForEntity(url, ArtBrand.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(artBrand.getBrandId(), response.getBody().getBrandId());

        System.out.println("Read: " + response.getBody());
    }

    @Test
    @Order(3)
    void c_update() {
        ArtBrand updated = new ArtBrand.Builder()
                .copy(artBrand)
                .setBrandName("Abstract Art")
                .setDescription("Updated description for Zeebrah")
                .build();

        HttpEntity<ArtBrand> request = new HttpEntity<>(updated);
        ResponseEntity<ArtBrand> response = restTemplate.exchange(
                BASE_URL + "/update",
                HttpMethod.PUT,
                request,
                ArtBrand.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Abstract Art", response.getBody().getBrandName());
        assertEquals("Updated description for Zeebrah", response.getBody().getDescription());

        artBrand = response.getBody(); // update reference
        System.out.println("Updated: " + artBrand);
    }

    @Test
    @Order(4)
    void d_getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<ArtBrand[]> response = restTemplate.getForEntity(url, ArtBrand[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        List<ArtBrand> artBrands = Arrays.asList(response.getBody());
        assertTrue(artBrands.size() > 0);

        System.out.println("All ArtBrands: " + artBrands);
    }


    @Test
    @Order(5)
    void e_delete() {

        restTemplate.delete(BASE_URL + "/delete" + artBrand.getBrandId() );
}}