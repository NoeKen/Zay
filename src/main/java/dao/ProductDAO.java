package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Product;
import utils.DBConnector;

public class ProductDAO {

    public List<Product> findAll() {
        System.out.println("Début récupération des produits en BD");
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("description"));
                p.setPrice(rs.getDouble("price"));
                p.setImage(rs.getString("image"));
                p.setStock(rs.getInt("stock"));
                p.setFeatured(rs.getBoolean("featured"));
                p.setCategoryId(rs.getInt("category_id")); // Gère le cas NULL en Model
                products.add(p);
            }

            System.out.println("========================> Nombre de produits: " + products.size());

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des produits : " + e.getMessage());
        }

        return products;
    }

    public Product findById(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        Product p = null;

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("description"));
                p.setPrice(rs.getDouble("price"));
                p.setImage(rs.getString("image"));
                p.setStock(rs.getInt("stock"));
                p.setFeatured(rs.getBoolean("featured"));
                p.setCategoryId(rs.getInt("category_id"));
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du produit : " + e.getMessage());
        }

        return p;
    }
}
