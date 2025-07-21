package dal.dao;

import interfaces.ProductService;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Product; // Assurez-vous que votre classe Product est correctement définie
import utils.DBConnector; // Assurez-vous que votre classe DBConnector est correcte

public class ProductDAO implements ProductService{

    /**
     * Compte le nombre total de produits dans la base de données.
     * Cette méthode est essentielle pour calculer le nombre total de pages.
     * @return Le nombre total de produits.
     */
    @Override
    public int countAllProducts() {
        int totalProducts = 0;
        String sql = "SELECT COUNT(*) FROM products"; // Requête SQL pour compter les produits

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                totalProducts = rs.getInt(1); // Récupère le résultat du COUNT(*)
            }
            System.out.println("========================> Nombre total de produits: " + totalProducts);

        } catch (SQLException e) {
            System.err.println("Erreur lors du comptage des produits : " + e);
        }
        return totalProducts;
    }

    /**
     * Récupère une liste de produits paginée à partir de la base de données.
     * Utilise LIMIT et OFFSET pour la pagination.
     * @param offset Le décalage (nombre de lignes à sauter) pour la pagination.
     * @param limit Le nombre maximum de produits à retourner (taille de la page).
     * @return Une liste de produits pour la page spécifiée.
     */
    public List<Product> getProducts(int offset, int limit) {
        System.out.println("Début récupération des produits paginés en BD (Offset: " + offset + ", Limit: " + limit + ")");
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY id LIMIT ? OFFSET ?";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, limit);  // Le premier '?' est pour LIMIT
            ps.setInt(2, offset); // Le deuxième '?' est pour OFFSET

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
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
                        p.setCategoryId(1); // valeur par défaut si NULL
                    } else {
                        p.setCategoryId(categoryId);
                    }
                    products.add(p);
                }
            }
            System.out.println("========================> Nombre de produits récupérés (page): " + products.size());

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des produits paginés : " + e);
        }
        return products;
    }

    @Override
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
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du produit : " + e);
        }

        return p;
    }

    /**
     * Récupère tous les produits sans pagination.
     * @return Liste de tous les produits en base.
     */
    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY id";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
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
                products.add(p);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de tous les produits : " + e);
        }

        return products;
    }

    /**
     * Ajoute un nouveau produit dans la base de données.
     * @param product Le produit à ajouter.
     * @return true si l'insertion a réussi, false sinon.
     */
    @Override
    public boolean addProduct(Product product) {
        String sql = "INSERT INTO products (name, description, price, image, stock, featured, category_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Remplir les paramètres de la requête
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setBigDecimal(3, product.getPrice());
            ps.setString(4, product.getImage());
            ps.setInt(5, product.getStock());
            ps.setBoolean(6, product.isFeatured());
            // categoryId peut être null dans Product, gérer en conséquence
            if (product.getCategoryId() == 0) {
                ps.setNull(7, Types.INTEGER);
            } else {
                ps.setInt(7, product.getCategoryId());
            }

            int affectedRows = ps.executeUpdate();

            // On peut récupérer l'id auto-généré si besoin
            if (affectedRows == 0) {
                System.err.println("L'insertion du produit a échoué, aucune ligne affectée.");
                return false;
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    product.setId(generatedKeys.getInt(1)); // mettre à jour l'id du produit
                }
            }

            return true;

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du produit : " + e);
            return false;
        }
    }

    /**
     * Met à jour un produit existant dans la base.
     * @param product Le produit avec ses nouvelles valeurs (doit avoir un id valide).
     * @return true si la mise à jour a réussi, false sinon.
     */
    @Override
    public boolean updateProduct(Product product) {
        String sql = "UPDATE products SET name = ?, description = ?, price = ?, image = ?, stock = ?, featured = ?, category_id = ? WHERE id = ?";

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

            ps.setInt(8, product.getId());

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

    /**
     * Supprime un produit de la base par son id.
     * @param id L'identifiant du produit à supprimer.
     * @return true si la suppression a réussi, false sinon.
     */
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
}
