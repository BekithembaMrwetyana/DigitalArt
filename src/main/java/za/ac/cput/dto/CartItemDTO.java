package za.ac.cput.dto;

public class CartItemDTO {
    private Long cartItemID;
    private Long productID;
    private String title;
    private int quantity;
    private double price;
    private Long userId;

    public CartItemDTO(Long cartItemID, Long productID, String title, int quantity, double price, Long userId) {
        this.cartItemID = cartItemID;
        this.productID = productID;
        this.title = title;
        this.quantity = quantity;
        this.price = price;
        this.userId = userId;
    }

    // Getters & setters
    public Long getCartItemID() { return cartItemID; }
    public Long getProductID() { return productID; }
    public String getTitle() { return title; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public Long getUserId() { return userId; }
}
