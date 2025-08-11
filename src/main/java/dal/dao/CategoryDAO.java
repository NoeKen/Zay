package dal.dao;

/**
 *
 * @author Aurel Noe Kenfack
 */

import interfaces.CategoryService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.Category; 
import utils.DBConnector; 

public class CategoryDAO implements CategoryService{

    /**
     * Récupère toutes les catégories de la base de données.
     * @return Une liste de toutes les catégories.
     */
    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT id, name, description, image_url FROM categories LIMIT 3"; 

        try (Connection conn = DBConnector.getConnection(); // Obtient une connexion du pool
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                category.setDescription(rs.getString("description"));
                category.setImageUrl(rs.getString("image_url")); 
                categories.add(category);
            }
            System.out.println("========================> Nombre de catégories récupérées: " + categories.size());

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de toutes les catégories : " + e.getMessage());
        }
        return categories;
    }

    /**
     * Récupère une catégorie par son identifiant.
     * @param id L'ID de la catégorie à récupérer.
     * @return L'objet Category correspondant à l'ID, ou null si non trouvée.
     */
    @Override
    public Category findById(int id) {
        Category category = null;
        String sql = "SELECT id, name, description, image_url FROM categories WHERE id = ?";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id); // Définit le paramètre ID dans la requête
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    category = new Category();
                    category.setId(rs.getInt("id"));
                    category.setName(rs.getString("name"));
                    category.setDescription(rs.getString("description"));
                    category.setImageUrl(rs.getString("image_url"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de la catégorie par ID : " + e.getMessage());
        }
        return category;
    }

    /**
     * Ajoute une nouvelle catégorie à la base de données.
     * L'ID de la catégorie sera généré par la base de données.
     * @param category L'objet Category à ajouter (l'ID sera mis à jour après l'insertion).
     * @return true si l'ajout est réussi, false sinon.
     */
    @Override
    public boolean addCategory(Category category) {
        String sql = "INSERT INTO categories (name, description, image_url) VALUES (?, ?, ?)";
        boolean success = false;

        try (Connection conn = DBConnector.getConnection();
             // Permet de récupérer l'ID généré automatiquement par la DB
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            ps.setString(3, category.getImageUrl());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                success = true;
                // Récupérer l'ID généré
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        category.setId(rs.getInt(1)); // Met à jour l'ID de l'objet Category
                    }
                }
            }
            System.out.println("Catégorie '" + category.getName() + "' ajoutée avec succès.");

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de la catégorie : " + e.getMessage());
        }
        return success;
    }

    /**
     * Met à jour une catégorie existante dans la base de données.
     * @param category L'objet Category avec les informations mises à jour (l'ID est utilisé pour l'identification).
     * @return true si la mise à jour est réussie, false sinon.
     */
    @Override
    public boolean updateCategory(Category category) {
        String sql = "UPDATE categories SET name = ?, description = ?, image_url = ? WHERE id = ?";
        boolean success = false;

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            ps.setString(3, category.getImageUrl());
            ps.setInt(4, category.getId()); // Condition WHERE basée sur l'ID

            int rowsAffected = ps.executeUpdate();
            success = rowsAffected > 0;
            System.out.println("Catégorie ID " + category.getId() + " mise à jour : " + success);

        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de la catégorie : " + e.getMessage());
        }
        return success;
    }

    /**
     * Supprime une catégorie de la base de données par son identifiant.
     * @param id L'ID de la catégorie à supprimer.
     * @return true si la suppression est réussie, false sinon.
     */
    @Override
    public boolean deleteCategory(int id) {
        String sql = "DELETE FROM categories WHERE id = ?";
        boolean success = false;

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();
            success = rowsAffected > 0;
            System.out.println("Catégorie ID " + id + " supprimée : " + success);

        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la catégorie : " + e.getMessage());
        }
        return success;
    }

    /**
     * Compte le nombre total de catégories dans la base de données.
     * @return Le nombre total de catégories.
     */
    @Override
    public int countAllCategories() {
        int totalCategories = 0;
        String sql = "SELECT COUNT(*) FROM categories";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                totalCategories = rs.getInt(1);
            }
            System.out.println("========================> Nombre total de catégories: " + totalCategories);

        } catch (SQLException e) {
            System.err.println("Erreur lors du comptage des catégories : " + e.getMessage());
        }
        return totalCategories;
    }

    // Pas de méthode getCategories(offset, limit) pour l'instant car la pagination
    // des catégories n'est pas demandée explicitement, mais elle serait similaire à ProductDAO.


    
}