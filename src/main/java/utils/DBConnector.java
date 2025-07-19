/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 *
 * @author admin
 */
public class DBConnector {
    private static Connection connection;

    public static Connection getConnection() {
        if (connection != null) return connection;

        try (InputStream input = DBConnector.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties props = new Properties();
            if (input == null) {
                throw new RuntimeException("Fichier db.properties introuvable.");
            }

            props.load(input);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.username");
            String password = props.getProperty("db.password");
            String driver = props.getProperty("db.driver");

            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion à la base MariaDB établie avec succès.");

        } catch (Exception e) {
            System.err.println("error when connecting to database"+e);
            throw new RuntimeException("Erreur lors de la connexion à la base de données."+e);
        }

        return connection;
    }
}
