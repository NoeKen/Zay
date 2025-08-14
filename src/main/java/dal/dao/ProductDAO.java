package dal.dao;

import interfaces.ProductService;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Product;
import models.Review;
import utils.DBConnector;

public class ProductDAO implements ProductService {

    @Override
    public int countAllProducts() {
        int totalProducts = 0;
        String sql = "SELECT COUNT(*) FROM products";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                totalProducts = rs.getInt(1);
            }
            System.out.println("Nombre total de produits: " + totalProducts);

        } catch (SQLException e) {
            System.err.println("Erreur lors du comptage des produits : " + e);
        }
        return totalProducts;
    }

    @Override
    public List<Product> getProducts(int offset, int limit) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.*, COALESCE(ROUND(AVG(r.rating)), 0) AS avg_rating, COUNT(r.comment) AS nbr_comments "
               + "FROM products p LEFT JOIN reviews r ON p.id = r.product_id "
               + "GROUP BY p.id ORDER BY p.id LIMIT ? OFFSET ?";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    products.add(mapToProduct(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des produits paginés : " + e);
        }
        return products;
    }

    @Override
    public Product findById(int id) {
        Product p = null;
        String sql = "SELECT p.*, COALESCE(ROUND(AVG(r.rating)), 0) AS avg_rating, COUNT(r.comment) AS nbr_comments "
               + "FROM products p LEFT JOIN reviews r ON p.id = r.product_id "
               + "WHERE p.id = ? GROUP BY p.id";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    p = mapToProduct(rs);
                    // Charger la liste des commentaires liés
                    p.setReviews(getReviewsByProductId(id));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du produit : " + e);
        }
        return p;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.*, COALESCE(ROUND(AVG(r.rating)), 0) AS avg_rating, COUNT(r.comment) AS nbr_comments "
               + "FROM products p LEFT JOIN reviews r ON p.id = r.product_id "
               + "GROUP BY p.id ORDER BY p.id";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                products.add(mapToProduct(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de tous les produits : " + e);
        }
        return products;
    }

    @Override
    public boolean addProduct(Product product) {
        String sql = "INSERT INTO products (name, description, price, image, stock, featured, category_id, sizes, specifications) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setBigDecimal(3, product.getPrice());
            ps.setString(4, product.getImage());
            ps.setInt(5, product.getStock());
            ps.setBoolean(6, product.isFeatured());

            if (product.getCategoryId() == 0) {
                ps.setNull(7, Types.INTEGER);
            } else {
                ps.setInt(7, product.getCategoryId());
            }

            ps.setString(8, product.getSizesRaw());
            ps.setString(9, product.getSpecifications());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                System.err.println("L'insertion du produit a échoué, aucune ligne affectée.");
                return false;
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    product.setId(generatedKeys.getInt(1));
                }
            }
            return true;

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du produit : " + e);
            return false;
        }
    }

    @Override
    public boolean updateProduct(Product product) {
        String sql = "UPDATE products SET name=?, description=?, price=?, image=?, stock=?, featured=?, category_id=?, sizes=?, specifications=? WHERE id=?";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setBigDecimal(3, product.getPrice());
            ps.setString(4, product.getImage());
            ps.setInt(5, product.getStock());
            ps.setBoolean(6, product.isFeatured());

            if (product.getCategoryId() == 0) {
                ps.setNull(7, Types.INTEGER);
            } else {
                ps.setInt(7, product.getCategoryId());
            }

            ps.setString(8, product.getSizesRaw());
            ps.setString(9, product.getSpecifications());

            ps.setInt(10, product.getId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                System.err.println("La mise à jour du produit a échoué, aucun produit trouvé avec cet id.");
                return false;
            }
            return true;

        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du produit : " + e);
            return false;
        }
    }

    @Override
    public boolean deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id = ?";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                System.err.println("La suppression a échoué, aucun produit trouvé avec cet id.");
                return false;
            }
            return true;

        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du produit : " + e);
            return false;
        }
    }

    @Override
    public List<Product> findByCriteria(int categoryId, BigDecimal minPrice, BigDecimal maxPrice, boolean featured) {
        List<Product> products = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT p.*, COALESCE(ROUND(AVG(r.rating)), 0) AS avg_rating, COUNT(r.comment) AS nbr_comments "
  + "FROM products p LEFT JOIN reviews r ON p.id = r.product_id WHERE 1=1");

        if (categoryId != -1) {
            sql.append(" AND category_id = ?");
        }
        if (minPrice != null) {
            sql.append(" AND price >= ?");
        }
        if (maxPrice != null) {
            sql.append(" AND price <= ?");
        }
        if (featured) {
            sql.append(" AND featured = TRUE");
        }
        sql.append(" GROUP BY p.id");
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int index = 1;
            if (categoryId != -1) ps.setInt(index++, categoryId);
            if (minPrice != null) ps.setBigDecimal(index++, minPrice);
            if (maxPrice != null) ps.setBigDecimal(index++, maxPrice);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    products.add(mapToProduct(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche par critères : " + e.getMessage());
        }
        return products;
    }

    @Override
    public List<Product> findFeaturedProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.*, COALESCE(ROUND(AVG(r.rating)), 0) AS avg_rating, COUNT(r.comment) AS nbr_comments "
               + "FROM products p LEFT JOIN reviews r ON p.id = r.product_id "
               + "WHERE p.featured = TRUE GROUP BY p.id LIMIT 3";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                products.add(mapToProduct(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des produits vedettes : " + e.getMessage());
        }
        return products;
    }

    private Product mapToProduct(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setId(rs.getInt("id"));
        p.setName(rs.getString("name"));
        p.setDescription(rs.getString("description"));
        p.setPrice(rs.getBigDecimal("price"));
        p.setImage(rs.getString("image"));
        p.setStock(rs.getInt("stock"));
        p.setFeatured(rs.getBoolean("featured"));
        int categoryId = rs.getInt("category_id");
        if (rs.wasNull()) {
            p.setCategoryId(1);
        } else {
            p.setCategoryId(categoryId);
        }
        p.setSizesRaw(rs.getString("sizes"));
        p.setSpecifications(rs.getString("specifications"));
        p.setAvgRating(rs.getInt("avg_rating"));      // moyenne arrondie
        p.setNbrComments(rs.getInt("nbr_comments"));  // nombre de commentaires
        return p;
    }

    @Override
    public int countProductsByCategory(int categoryId) {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM products WHERE category_id = ?";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, categoryId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors du comptage des produits par catégorie : " + e);
        }
        return total;
    }

    @Override
    public List<Product> getProductsByCategory(int categoryId, int offset, int limit) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.*, COALESCE(ROUND(AVG(r.rating)), 0) AS avg_rating, COUNT(r.comment) AS nbr_comments "
               + "FROM products p LEFT JOIN reviews r ON p.id = r.product_id "
               + "WHERE p.category_id = ? GROUP BY p.id ORDER BY p.id LIMIT ? OFFSET ?";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, categoryId);
            ps.setInt(2, limit);
            ps.setInt(3, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    products.add(mapToProduct(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des produits par catégorie paginés : " + e);
        }
        return products;
    }
    
    public List<Review> getReviewsByProductId(int productId) {
    List<Review> reviews = new ArrayList<>();
    String sql = "SELECT id, user_id, product_id, rating, comment, created_at FROM reviews WHERE product_id = ? ORDER BY created_at DESC";

    try (Connection conn = DBConnector.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, productId);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Review r = new Review();
                r.setId(rs.getInt("id"));
                r.setUserId(rs.getInt("user_id"));
                r.setProductId(rs.getInt("product_id"));
                r.setRating(rs.getInt("rating"));
                r.setComment(rs.getString("comment"));
                r.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                reviews.add(r);
            }
        }
    } catch (SQLException e) {
        System.err.println("Erreur lors de la récupération des reviews : " + e);
    }
    return reviews;
}

}
