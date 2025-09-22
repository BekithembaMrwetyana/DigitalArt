package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Artist;
import za.ac.cput.service.ArtistService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/artist")
public class ArtistController {

    private final ArtistService service;

    @Autowired
    public ArtistController(ArtistService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Artist artist) {
        try {
            Artist created = service.create(artist);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/read/{artistId}")
    public ResponseEntity<Artist> read(@PathVariable Long artistId) {
        Artist artist = service.read(artistId);
        if (artist != null) {
            return new ResponseEntity<>(artist, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<Artist> update(@RequestBody Artist artist) {
        Artist updated = service.update(artist);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{artistId}")
    public ResponseEntity<Void> delete(@PathVariable Long artistId) {
        service.delete(artistId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Artist>> getAll() {
        List<Artist> artists = service.getAll();
        return new ResponseEntity<>(artists, HttpStatus.OK);
    }
}
