/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Factory;

import dal.dao.ReviewDAO;
import interfaces.ReviewService;

/**
 *
 * @author admin
 */
public class ReviewServiceFactory {
    private static final String MODE = System.getProperty("data.mode", "DAO");      // Pour definir si c'est JDBC/DAO ou JPA qui utilise pour gerer la base de donnees 

    public static ReviewService getInstance() {
        if (MODE.equalsIgnoreCase("DAO")) {
            return new ReviewDAO();
        } 
        else {
            return new ReviewDAO();
        }
    }
}
