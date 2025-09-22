package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Artist;
import za.ac.cput.repository.ArtistRepository;

import java.util.List;

@Service
public class ArtistService implements IArtistService {


    private final ArtistRepository repository;

    @Autowired
    public ArtistService(ArtistRepository repository) {
        this.repository = repository;
    }

    @Override
    public Artist create(Artist artist) {

        if(repository.findByArtistName(artist.getArtistName()).isPresent()) {
            throw new IllegalArgumentException("Artist already exists");
        }
        return repository.save(artist);

    }


    @Override
    public Artist read(Long artistId) {
        return this.repository.findById(artistId).orElse(null);
    }

    @Override
    public Artist update(Artist artist) {
        return repository.save(artist);
    }

    @Override
    public void delete(Long artistId) {
        repository.deleteById(artistId);
    }

    @Override
    public List<Artist> getAll() {
        return repository.findAll();
    }

}




