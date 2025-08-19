package dal.dao;

import models.CartItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartItemDAO {
    private final Connection connection;

    public CartItemDAO(Connection connection) {
        this.connection = connection;
    }

    /** Ajouter un produit au panier (ou incrémenter si déjà présent) */
    public void addOrUpdateCartItem(CartItem item) throws SQLException {
        String checkSql = "SELECT id, quantity FROM cart_items WHERE user_id = ? AND product_id = ? AND size = ?";
        try (PreparedStatement ps = connection.prepareStatement(checkSql)) {
            ps.setInt(1, item.getUserId());
            ps.setInt(2, item.getProductId());
            ps.setString(3, item.getSize());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int existingQty = rs.getInt("quantity");
                String updateSql = "UPDATE cart_items SET quantity = ? WHERE id = ?";
                try (PreparedStatement ups = connection.prepareStatement(updateSql)) {
                    ups.setInt(1, existingQty + item.getQuantity());
                    ups.setInt(2, rs.getInt("id"));
                    ups.executeUpdate();
                }
            } else {
                String insertSql = "INSERT INTO cart_items (user_id, product_id, quantity, size) VALUES (?, ?, ?, ?)";
                try (PreparedStatement ins = connection.prepareStatement(insertSql)) {
                    ins.setInt(1, item.getUserId());
                    ins.setInt(2, item.getProductId());
                    ins.setInt(3, item.getQuantity());
                    ins.setString(4, item.getSize());
                    ins.executeUpdate();
                }
            }
        }
    }

    /** Modifier la quantité */
    public void updateQuantity(int cartItemId, int quantity) throws SQLException {
        String sql = "UPDATE cart_items SET quantity = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, cartItemId);
            ps.executeUpdate();
        }
    }

    /** Supprimer un produit du panier */
    public void removeCartItem(int cartItemId) throws SQLException {
        String sql = "DELETE FROM cart_items WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, cartItemId);
            ps.executeUpdate();
        }
    }

    /** Récupérer le panier d'un utilisateur */
    public List<CartItem> getCartItemsByUser(int userId) throws SQLException {
        List<CartItem> items = new ArrayList<>();
        String sql = "SELECT ci.*, p.name, p.price, p.image " +
                     "FROM cart_items ci " +
                     "JOIN products p ON ci.product_id = p.id " +
                     "WHERE ci.user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CartItem item = new CartItem();
                item.setId(rs.getInt("id"));
                item.setUserId(rs.getInt("user_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setSize(rs.getString("size"));
                item.setAddedAt(rs.getTimestamp("added_at"));
                item.setProductName(rs.getString("name"));
                item.setProductPrice(rs.getDouble("price"));
                item.setProductImage(rs.getString("image"));
                items.add(item);
            }
        }
        return items;
    }
}
