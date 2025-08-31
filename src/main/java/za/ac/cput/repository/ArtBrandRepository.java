package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.ArtBrand;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface ArtBrandRepository extends JpaRepository<ArtBrand, Long> {

    Optional<ArtBrand> findByBrandName(String brandName);

    List<ArtBrand> findByCreationDateAfter(LocalDate date);

}
