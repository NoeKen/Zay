package utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnector {
    // Déclarez le dataSource comme statique et final pour qu'il soit initialisé une seule fois
    private static final HikariDataSource dataSource;

    // Bloc statique pour initialiser le pool de connexions une seule fois au chargement de la classe
    static {
        Properties props = new Properties();
        try (InputStream input = DBConnector.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException("Fichier db.properties introuvable. Assurez-vous qu'il est dans le classpath.");
            }
            props.load(input);

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.username"));
            config.setPassword(props.getProperty("db.password"));
            config.setDriverClassName(props.getProperty("db.driver")); // Définir explicitement le driver

            // Paramètres HikariCP recommandés pour de bonnes performances
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            config.setConnectionTimeout(30000); // Temps d'attente maximum pour une connexion (ms)
            config.setMaximumPoolSize(10);     // Nombre maximum de connexions dans le pool
            config.setMinimumIdle(5);          // Nombre minimum de connexions inactives dans le pool
            config.setIdleTimeout(600000);     // Temps maximum qu'une connexion peut rester inactive dans le pool (ms)

            dataSource = new HikariDataSource(config);
            System.out.println("HikariCP Connection Pool initialisé avec succès.");

        } catch (Exception e) {
            System.err.println("Erreur fatale lors de l'initialisation du pool de connexions: " + e.getMessage());
            e.printStackTrace(); // Afficher la pile d'appels pour un diagnostic complet
            throw new RuntimeException("Impossible d'initialiser le pool de connexions à la base de données.", e);
        }
    }

    /**
     * Retourne une connexion à partir du pool.
     * Cette connexion doit être fermée (retournée au pool) par l'appelant
     * via un bloc try-with-resources.
     * @return Une connexion JDBC active du pool.
     * @throws SQLException Si une erreur SQL survient lors de l'obtention de la connexion.
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection(); // Obtient une connexion du pool
    }

    /**
     * Méthode optionnelle pour fermer explicitement le pool de connexions lors de l'arrêt de l'application.
     * Peut être appelée dans un ServletContextListener par exemple.
     */
    public static void closeDataSource() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println("HikariCP Connection Pool fermé.");
        }
    }
}