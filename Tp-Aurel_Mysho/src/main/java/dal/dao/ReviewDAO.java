/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.dao;

import interfaces.ReviewService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Review;
import models.ReviewStats;
import utils.DBConnector;

/**
 *
 * @author admin
 */
public class ReviewDAO implements ReviewService {
    
    public List<Review> getReviewsByProductId(int productId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT id, user_id, product_id, rating, comment, created_at FROM reviews WHERE product_id = ? ORDER BY created_at DESC";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Review review = new Review();
                    review.setId(rs.getInt("id"));
                    review.setUserId(rs.getInt("user_id"));
                    review.setProductId(rs.getInt("product_id"));
                    review.setRating(rs.getInt("rating"));
                    review.setComment(rs.getString("comment"));
                    review.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    reviews.add(review);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des reviews : " + e.getMessage());
        }
        return reviews;
    }
    
    // Recuperer les statistiques de review par produit
    public ReviewStats getReviewStatsByProductId(int productId) {
        String sql = "SELECT ROUND(AVG(rating)) AS avg_rating, COUNT(comment) AS total_comments FROM reviews WHERE product_id = ?";
        ReviewStats stats = new ReviewStats(0, 0); // Ici avgRating est int

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int avgRating = rs.getInt("avg_rating"); // récupère un entier arrondi
                    int totalComments = rs.getInt("total_comments");

                    stats = new ReviewStats(avgRating, totalComments);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors du calcul des stats des reviews : " + e.getMessage());
        }
        return stats;
    }
}
