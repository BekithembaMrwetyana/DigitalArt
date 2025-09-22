package za.ac.cput.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "artist")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "artist_id")
    private Long artistId;
    @Column(name = "artist_name")
    private String artistName;
    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "description")
    private String description;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    public Artist() {}

    private Artist(Builder builder) {
        this.artistId = builder.artistId;
        this.artistName = builder.artistName;
        this.imageUrl = builder.imageUrl;
        this.description = builder.description;
        this.creationDate = builder.creationDate;
    }


    public Long getArtistId() { return artistId; }
    public String getArtistName() { return artistName; }
    public String getImageUrl() { return imageUrl; }
    public String getDescription() { return description; }
    public LocalDate getCreationDate() { return creationDate; }

    @Override
    public String toString() {
        return "Artist{" +
                "artistId=" + artistId +
                ", artistName='" + artistName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }

    public static class Builder {
        private Long artistId;
        private String artistName;
        private String imageUrl;
        private String description;
        private LocalDate creationDate;

        public Builder setArtistId(Long artistId) {
            this.artistId = artistId;
            return this;
        }

        public Builder setArtistName(String artistName) {
            this.artistName = artistName;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setCreationDate(LocalDate creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public Builder copy(Artist artist) {
            this.artistId = artist.artistId;
            this.artistName = artist.artistName;
            this.imageUrl = artist.imageUrl;
            this.description = artist.description;
            this.creationDate = artist.creationDate;
            return this;
        }

        public Artist build() {
            return new Artist ( this);
        }
    }
}
