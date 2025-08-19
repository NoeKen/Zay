package models;

import java.sql.Timestamp;
import java.util.Objects;

public class CartItem {
    private int id;
    private int userId;
    private int productId;
    private int quantity;
    private String size;
    private Timestamp addedAt;

    // Champs optionnels pour affichage
    private String productName;
    private double productPrice;
    private String productImage;

    public CartItem() {}

    public CartItem(int userId, int productId, int quantity, String size) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.size = size;
        this.addedAt = new Timestamp(System.currentTimeMillis());
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public Timestamp getAddedAt() { return addedAt; }
    public void setAddedAt(Timestamp addedAt) { this.addedAt = addedAt; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public double getProductPrice() { return productPrice; }
    public void setProductPrice(double productPrice) { this.productPrice = productPrice; }

    public String getProductImage() { return productImage; }
    public void setProductImage(String productImage) { this.productImage = productImage; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItem)) return false;
        CartItem that = (CartItem) o;
        return userId == that.userId &&
               productId == that.productId &&
               Objects.equals(size, that.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, productId, size);
    }
}
