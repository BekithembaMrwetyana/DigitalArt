package za.ac.cput.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "artbrands")
public class ArtBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long brandId;
    private String brandName;
    private String imageUrl;
    private String description;
    private LocalDate creationDate;

    protected ArtBrand() {}

    private ArtBrand(Builder builder) {
        this.brandId = builder.brandId;
        this.brandName = builder.brandName;
        this.imageUrl = builder.imageUrl;
        this.description = builder.description;
        this.creationDate = builder.creationDate;
    }


    public Long getBrandId() { return brandId; }
    public String getBrandName() { return brandName; }
    public String getImageUrl() { return imageUrl; }
    public String getDescription() { return description; }
    public LocalDate getCreationDate() { return creationDate; }

    @Override
    public String toString() {
        return "ArtBrand{" +
                "brandId=" + brandId +
                ", brandName='" + brandName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }

    public static class Builder {
        private Long brandId;
        private String brandName;
        private String imageUrl;
        private String description;
        private LocalDate creationDate;

        public Builder setBrandId(Long brandId) {
            this.brandId = brandId;
            return this;
        }

        public Builder setBrandName(String brandName) {
            this.brandName = brandName;
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

        public Builder copy(ArtBrand artBrand) {
            this.brandId = artBrand.brandId;
            this.brandName = artBrand.brandName;
            this.imageUrl = artBrand.imageUrl;
            this.description = artBrand.description;
            this.creationDate = artBrand.creationDate;
            return this;
        }

        public ArtBrand build() {
            return new ArtBrand(this);
        }
    }
}
