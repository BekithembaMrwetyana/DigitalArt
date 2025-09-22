package za.ac.cput.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import za.ac.cput.domain.Artist;
import za.ac.cput.factory.ArtistFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ArtistControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static Artist artist;

    private final String BASE_URL = "/api/artist";

    @BeforeAll
    void setup() {
        artist = ArtistFactory.createArtist(
                "ZeeStudios",
                "http://localhost:8080/digital_artDB/images/art18.jpeg",
                "Creative image done by Zeeh from KZN from valleys of Dududu, who is passionate about art",
                LocalDate.now()
        );
    }

    @Test
    @Order(1)
    void a_create() {
        ResponseEntity<Artist> response = restTemplate.postForEntity(BASE_URL + "/create", artist, Artist.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        artist = response.getBody();
        assertNotNull(artist);
        assertEquals("ZeeStudios", artist.getArtistName());
        System.out.println("Created: " + artist);
    }

    @Test
    @Order(2)
    void b_read() {
        ResponseEntity<Artist> response = restTemplate.getForEntity(BASE_URL + "/read/" + artist.getArtistId(), Artist.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(artist.getArtistId(), response.getBody().getArtistId());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    @Order(3)
    void c_update() {
        Artist updated = new Artist.Builder()
                .copy(artist)
                .setArtistName("Abstract Art")
                .setDescription("Updated description for Zeebrah")
                .build();

        HttpEntity<Artist> request = new HttpEntity<>(updated);
        ResponseEntity<Artist> response = restTemplate.exchange(BASE_URL + "/update", HttpMethod.PUT, request, Artist.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Abstract Art", response.getBody().getArtistName());
        assertEquals("Updated description for Zeebrah", response.getBody().getDescription());
        artist = response.getBody();
        System.out.println("Updated: " + artist);
    }

    @Test
    @Order(4)
    void d_getAll() {
        ResponseEntity<Artist[]> response = restTemplate.getForEntity(BASE_URL + "/getAll", Artist[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        List<Artist> artists = Arrays.asList(response.getBody());
        assertTrue(artists.size() > 0);
        System.out.println("All Artists: " + artists);
    }

    @Test
    @Order(5)
    void e_delete() {
        restTemplate.delete(BASE_URL + "/delete/" + artist.getArtistId());
        System.out.println("Deleted Artist with ID: " + artist.getArtistId());
    }
}
