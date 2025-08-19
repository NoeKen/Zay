package dal.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import models.Order;
import models.OrderLine;
import utils.DBConnector;

/**
 * DAO pour gérer les commandes (Order).
 */
public class OrderDAO {

    /**
     * Enregistre une commande avec ses lignes dans la base de données.
     * @param order L'objet Order à enregistrer.
     * @return true si l'opération a réussi, sinon false.
     */
    public boolean checkout(Order order) {
        Connection conn = null;
        PreparedStatement psOrder = null;
        PreparedStatement psOrderLine = null;

        try {
            conn = DBConnector.getConnection();
            conn.setAutoCommit(false); // On démarre une transaction

            // 1️⃣ Insérer la commande principale
            String sqlOrder = "INSERT INTO orders (customer_name, total_amount) VALUES (?, ?)";
            psOrder = conn.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS);
//            psOrder.setString(1, order.getCustomerName());
//            psOrder.setBigDecimal(2, order.getTotalAmount());
            psOrder.executeUpdate();

            // Récupérer l'ID généré
            ResultSet rs = psOrder.getGeneratedKeys();
            int orderId = -1;
            if (rs.next()) {
                orderId = rs.getInt(1);
            }

            // 2️⃣ Insérer les lignes de commande
            String sqlOrderLine = "INSERT INTO order_lines (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
            psOrderLine = conn.prepareStatement(sqlOrderLine);
            for (OrderLine line : order.getOrderLines()) {
                psOrderLine.setInt(1, orderId);
                psOrderLine.setInt(2, line.getProduct().getId());
                psOrderLine.setInt(3, line.getQuantity());
//                psOrderLine.setBigDecimal(4, line.getPrice());
                psOrderLine.addBatch();
            }
            psOrderLine.executeBatch();

            // 3️⃣ Mise à jour du stock
            String sqlUpdateStock = "UPDATE products SET stock = stock - ? WHERE id = ?";
            try (PreparedStatement psStock = conn.prepareStatement(sqlUpdateStock)) {
                for (OrderLine line : order.getOrderLines()) {
                    psStock.setInt(1, line.getQuantity());
                    psStock.setInt(2, line.getProduct().getId());
                    psStock.addBatch();
                }
                psStock.executeBatch();
            }

            // 4️⃣ Valider la transaction
            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback(); // Annuler la transaction en cas d'erreur
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            try {
                if (psOrder != null) psOrder.close();
                if (psOrderLine != null) psOrderLine.close();
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
