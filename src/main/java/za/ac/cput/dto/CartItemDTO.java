package za.ac.cput.dto;

public class CartItemDTO {
    private Long cartItemID;
    private Long productID;
    private String productName;
    private int quantity;
    private double price;
    private Long userId;

    public CartItemDTO(Long cartItemID, Long productID, String productName, int quantity, double price, Long userId) {
        this.cartItemID = cartItemID;
        this.productID = productID;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.userId = userId;
    }

    // Getters & setters
    public Long getCartItemID() { return cartItemID; }
    public Long getProductID() { return productID; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public Long getUserId() { return userId; }
}
