package za.ac.cput.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import za.ac.cput.domain.Artist;
import za.ac.cput.factory.ArtistFactory;

import java.time.LocalDate;

@Component
public class ArtistDataInitializer implements CommandLineRunner {

    private final ArtistService artistService;

    public ArtistDataInitializer(ArtistService artistService) {
        this.artistService = artistService;
    }

    @Override
    public void run(String... args) {

        Artist a1 = ArtistFactory.createArtist(
                "Kruben Naidoo",
                "http://localhost:8080/digital_artDB/images/art1.jpeg",
                "Kruben Naidoo is a contemporary artist from KwaZulu-Natal, known for his vibrant compositions and innovative use of color. His work explores themes of culture, identity, and the human experience, earning recognition both locally and nationally.",
                LocalDate.now()
        );

        Artist a2 = ArtistFactory.createArtist(
                "T Shongwe",
                "http://localhost:8080/digital_artDB/images/art16.jpeg",
                "From the Eastern Cape, this artist turns everyday scenes into colorful stories. Each artwork shows calm moments and vibrant colors, inviting viewers to feel and imagine their own meaning.",
                LocalDate.now()
        );

        Artist a3 = ArtistFactory.createArtist(
                "Bekithemba M",
                "http://localhost:8080/digital_artDB/images/art18.jpeg",
                "Bekithemba M is a visionary artist from the Eastern Cape. His work blends imagination with cultural narratives, reflecting both personal and collective experiences in unique and captivating ways.",
                LocalDate.now()
        );




        if (artistService.getAll().stream()
                .noneMatch(a -> a.getArtistName().equals(a1.getArtistName()))) {
            artistService.create(a1);
        }

        if (artistService.getAll().stream()
                .noneMatch(a -> a.getArtistName().equals(a2.getArtistName()))) {
            artistService.create(a2);
        }

        if (artistService.getAll().stream()
                .noneMatch(a -> a.getArtistName().equals(a3.getArtistName()))) {
            artistService.create(a3);
        }

        System.out.println("ArtistDataInitializer: Artists seeded!");
    }
}
