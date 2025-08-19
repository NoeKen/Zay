package dal.dao;

import models.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import org.mindrot.jbcrypt.BCrypt;
import utils.DBConnector;
import utils.PasswordUtils;

public class UserDAO {

    private Connection connection;

    // Constructeur pour injecter la connexion
    public UserDAO(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Crée un nouvel utilisateur avec mot de passe haché
     * @param user Objet User avec nom, email et mot de passe en clair
     * @return true si l'insertion a réussi, false sinon
     */
    public boolean createUser(User user) {
        String sql = "INSERT INTO users (full_name, email, password) VALUES (?, ?, ?)";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Hash du mot de passe avec BCrypt
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, hashedPassword);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                System.err.println("La création de l'utilisateur a échoué, aucune ligne affectée.");
                return false;
            }

            // Récupération de l'ID auto-généré
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
            }

            return true;

        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de l'utilisateur : " + e.getMessage());
            return false;
        }
    }

    /**
     * Trouve un utilisateur par son email et mot de passe.
     * @param email L'email de l'utilisateur.
     * @param plainPassword Le mot de passe (clair ou déjà hashé selon stockage en DB).
     * @return L'utilisateur correspondant ou null si aucun trouvé.
     * @throws SQLException En cas d'erreur SQL.
     */
    public User findByEmailAndPassword(String email, String plainPassword) throws Exception {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password");
                    // Vérification du mot de passe
                    if (PasswordUtils.verifyPassword(plainPassword, storedHash)) {
                        User user = new User();
                        user.setId(rs.getInt("id"));
                        user.setName(rs.getString("full_name"));
                        user.setEmail(rs.getString("email"));
                        // Ne pas remettre le hash dans l'objet
                        return user;
                    }
                }
            }
        }
        return null; // email introuvable ou mot de passe incorrect
    }
}
