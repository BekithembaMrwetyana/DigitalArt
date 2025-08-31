package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.ArtBrand;
import za.ac.cput.domain.Category;
import za.ac.cput.repository.ArtBrandRepository;
import za.ac.cput.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ArtBrandService implements IArtBrandService {


    private final ArtBrandRepository repository;

    @Autowired
    public ArtBrandService(ArtBrandRepository repository) {
        this.repository = repository;
    }

    @Override
    public ArtBrand create(ArtBrand artBrand) {
        return repository.save(artBrand);
    }


    @Override
    public ArtBrand read(Long brandId) {
        return this.repository.findById(brandId).orElse(null);
    }

    @Override
    public ArtBrand update(ArtBrand artBrand) {
        return repository.save(artBrand);
    }

    @Override
    public void delete(Long brandId) {
        repository.deleteById(brandId);
    }

    @Override
    public List<ArtBrand> getAll() {
        return repository.findAll();
    }

}




