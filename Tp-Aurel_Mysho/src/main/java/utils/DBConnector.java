package utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnector {

    private static final HikariDataSource dataSource;

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
            config.setDriverClassName(props.getProperty("db.driver"));

            // Limiter le pool pour éviter de dépasser max_user_connections
            config.setMaximumPoolSize(5);    // Ajuste selon ton MySQL (max_user_connections)
            config.setMinimumIdle(2);
            config.setIdleTimeout(300000);   // 5 min
            config.setConnectionTimeout(30000);

            // Optimisations
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            dataSource = new HikariDataSource(config);

            System.out.println("HikariCP Connection Pool initialisé avec succès.");
        } catch (Exception e) {
            System.err.println("Erreur lors de l'initialisation du pool : " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Impossible d'initialiser le pool de connexions", e);
        }
    }

    // Utilisation en try-with-resources : auto-fermeture (retour au pool)
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void closeDataSource() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println("Pool HikariCP fermé.");
        }
    }
}
