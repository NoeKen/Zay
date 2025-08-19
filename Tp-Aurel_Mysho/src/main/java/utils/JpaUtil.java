
package utils;

/**
 *
 * @author Aurel Noe Kenfack
 */
import jakarta.persistence.*;

public class JpaUtil {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("myshopPU");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void close() {
        emf.close();
    }
}
