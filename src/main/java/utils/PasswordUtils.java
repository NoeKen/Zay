package utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    // Hacher un mot de passe avant de l'enregistrer
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    // Vérifier si le mot de passe en clair correspond au hash stocké
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
