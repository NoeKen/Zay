package dal.jpa;

/**
 * Implémentation du service de gestion des catégories à l'aide de JPA.
 * Cette classe permet d'effectuer des opérations CRUD sur l'entité Category.
 *
 * @author Aurel Noe Kenfack
 */

import interfaces.CategoryService;
import jakarta.persistence.*;
import models.Category;
import java.util.List;
import utils.JpaUtil;

public class CategoryJPA implements CategoryService {

    // Instance d'EntityManager, récupérée via l'utilitaire JpaUtil
    private EntityManager em = JpaUtil.getEntityManager();

    /**
     * Récupère une catégorie à partir de son identifiant.
     *
     * @param id Identifiant de la catégorie
     * @return L'objet Category correspondant ou null si non trouvé
     */
    @Override
    public Category findById(int id) {
        return em.find(Category.class, id);
    }

    /**
     * Retourne la liste de toutes les catégories disponibles en base.
     *
     * @return Liste des objets Category
     */
    @Override
    public List<Category> findAll() {
        // Requête JPQL pour récupérer toutes les catégories
        TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c", Category.class);
        return query.getResultList();
    }

    /**
     * Ajoute une nouvelle catégorie à la base de données.
     *
     * @param category L'objet Category à insérer
     * @return true si l'opération a réussi, false sinon
     */
    @Override
    public boolean addCategory(Category category) {
        try {
            em.getTransaction().begin(); // Début de la transaction
            em.persist(category);        // Persist de l'entité
            em.getTransaction().commit(); // Validation
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback(); // Annulation en cas d'erreur
            System.err.println("Erreur lors de l'ajoute d'une nouvelle catégorie : " + e.getMessage());
            return false;
        }
    }

    /**
     * Met à jour une catégorie existante dans la base.
     *
     * @param category L'objet Category mis à jour
     * @return true si la mise à jour a été effectuée, false sinon
     */
    @Override
    public boolean updateCategory(Category category) {
        try {
            em.getTransaction().begin();
            em.merge(category); // Fusionne les modifications avec l'entité existante
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Erreur lors de la mise à jour d'une catégorie : " + e.getMessage());
            return false;
        }
    }

    /**
     * Supprime une catégorie à partir de son identifiant.
     *
     * @param id Identifiant de la catégorie à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    @Override
    public boolean deleteCategory(int id) {
        try {
            em.getTransaction().begin();
            Category category = em.find(Category.class, id);
            if (category != null) {
                em.remove(category); // Suppression de l'entité
                em.getTransaction().commit();
                return true;
            }
            em.getTransaction().rollback(); // Rien à supprimer, donc rollback
            return false;
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Erreur lors de la suppression d'une catégorie : " + e.getMessage());
            return false;
        }
    }

    /**
     * Compte le nombre total de catégories dans la base de données.
     *
     * @return Le nombre total de catégories ou 0 en cas d'erreur
     */
    @Override
    public int countAllCategories() {
        try {
            Query query = em.createQuery("SELECT COUNT(c) FROM Category c");
            return ((Long) query.getSingleResult()).intValue();
        } catch (Exception e) {
            System.err.println("Erreur lors du décompte de toutes les catégories : " + e.getMessage());
            return 0;
        }
    }
}
