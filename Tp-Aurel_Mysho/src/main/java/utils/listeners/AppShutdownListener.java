/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils.listeners;

/**
 *
 * @author admin
 */

import com.zaxxer.hikari.HikariDataSource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

/**
 * Ce listener s'exécute au démarrage et à l'arrêt de l'application web.
 * Il permet de libérer proprement les ressources, comme le pool HikariCP.
 */
@WebListener // Permet d’éviter de l’ajouter manuellement dans web.xml
public class AppShutdownListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Aucun traitement ici, sauf si tu veux logger le démarrage
        System.out.println("Application démarrée.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Arrêt de l'application en cours...");

        try {
            // Récupérer la DataSource stockée dans le contexte (si applicable)
            Object dataSourceObj = sce.getServletContext().getAttribute("dataSource");

            if (dataSourceObj instanceof HikariDataSource) {
                HikariDataSource ds = (HikariDataSource) dataSourceObj;
                ds.close(); // Fermeture propre du pool
                System.out.println("HikariDataSource fermé correctement.");
            } else {
                System.out.println("Aucune datasource Hikari trouvée dans le contexte.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
