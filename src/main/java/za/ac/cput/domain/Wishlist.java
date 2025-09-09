package za.ac.cput.domain;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Wishlist")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlistID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "wishlist_products",
            joinColumns = @JoinColumn(name = "wishlistID"),
            inverseJoinColumns = @JoinColumn(name = "productId")
    )
    private List<Product> products;

    protected Wishlist() {}

    public Wishlist(Builder builder) {
        this.wishlistID = builder.wishlistID;
        this.user = builder.user;
        this.products = builder.products;
    }

    public Long getWishlistID() {
        return wishlistID;
    }

    public User getUser() {
        return user;
    }

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "Wishlist{" +
                "wishlistID=" + wishlistID +
                ", user=" + (user != null ? user.getUserId() : null) +
                ", products=" + products +
                '}';
    }

    public static class Builder {
        private Long wishlistID;
        private User user;
        private List<Product> products;

        public Builder setWishlistID(Long wishlistID) {
            this.wishlistID = wishlistID;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setProducts(List<Product> products) {
            this.products = products;
            return this;
        }

        public Builder copy(Wishlist wishlist) {
            this.wishlistID = wishlist.wishlistID;
            this.user = wishlist.user;
            this.products = wishlist.products;
            return this;
        }

        public Wishlist build() {
            return new Wishlist(this);
        }
    }
}
