/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Factory;

import interfaces.ProductService;
import dal.jpa.ProductJPA;
import dal.dao.ProductDAO; // Si tu as aussi une version DAO

/**
 * Fabrique permettant de fournir une instance de service selon l’environnement.
 * Ici, on renvoie l’implémentation JPA, mais cela peut être configuré
 * dynamiquement.
 *
 * @author Aurel Noe Kenfack
 */
public class ProductServiceFactory {

    // Type d'implémentation (ex: "JPA", "DAO", etc.)
    private static final String IMPLEMENTATION = "JPA";

    public static ProductService getProductService() {
        switch (IMPLEMENTATION) {
            case "JPA" -> {
                return new ProductJPA();
            }
            case "DAO" -> {
                return new ProductDAO();
            }
            default ->
                throw new UnsupportedOperationException("Type d'implémentation non pris en charge : " + IMPLEMENTATION);
        }
    }
}
