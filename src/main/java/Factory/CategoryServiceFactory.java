package Factory;

/**
 *
 * @author Aurel Noe Kenfack
 */
// factory/CategoryServiceFactory.java
// Pour definir si c'est JDBC/DAO ou JPA qui utilise pour gerer la communication avec la base de donnees
import dal.dao.CategoryDAO;
import dal.jpa.CategoryJPA;
import interfaces.CategoryService;

public class CategoryServiceFactory {
    private static final String MODE = System.getProperty("data.mode", "DAO"); // Pour definir si c'est JDBC/DAO ou JPA qui utilise pour gerer la base de donnees 

    public static CategoryService getInstance() {
        if (MODE.equalsIgnoreCase("JPA")) {
            return new CategoryJPA();
        } else {
            return new CategoryDAO();
        }
    }
}
