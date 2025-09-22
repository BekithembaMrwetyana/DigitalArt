package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import za.ac.cput.domain.Artist;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    Optional<Artist> findByArtistName(String artistName);

    List<Artist> findByCreationDateAfter(LocalDate date);

}
