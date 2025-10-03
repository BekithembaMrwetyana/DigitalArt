package za.ac.cput.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "wishlists")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "date_added")
    private LocalDateTime dateAdded;

    protected Wishlist() {}

    private Wishlist(Builder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.product = builder.product;
        this.dateAdded = builder.dateAdded;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public LocalDateTime getDateAdded() { return dateAdded; }
    public void setDateAdded(LocalDateTime dateAdded) { this.dateAdded = dateAdded; }

    @Override
    public String toString() {
        return "Wishlist{" +
                "id=" + id +
                ", userId=" + (user != null ? user.getUserId() : null) +
                ", productId=" + (product != null ? product.getProductID() : null) +
                ", dateAdded=" + dateAdded +
                '}';
    }

    public static class Builder {
        private Long id;
        private User user;
        private Product product;
        private LocalDateTime dateAdded;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setProduct(Product product) {
            this.product = product;
            return this;
        }

        public Builder setDateAdded(LocalDateTime dateAdded) {
            this.dateAdded = dateAdded;
            return this;
        }

        public Builder copy(Wishlist wishlist) {
            this.id = wishlist.id;
            this.user = wishlist.user;
            this.product = wishlist.product;
            this.dateAdded = wishlist.dateAdded;
            return this;
        }

        public Wishlist build() {
            return new Wishlist(this);
        }
    }
}
