/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.jpa;

/**
 *
 * @author admin
 */
import interfaces.ProductService;
import jakarta.persistence.*;
import java.math.BigDecimal;
import models.Product;
import utils.JpaUtil;

import java.util.List;

/**
 * Implémentation de ProductService en utilisant JPA. Permet d'interagir avec la
 * table des produits via EntityManager.
 */
public class ProductJPA implements ProductService {

    private EntityManager em = JpaUtil.getEntityManager();

    @Override
    public Product findById(int id) {
        // Recherche d'un produit par son identifiant
        return em.find(Product.class, id);
    }

    @Override
    public List<Product> findAll() {
        // Requête JPQL pour récupérer tous les produits
        TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p", Product.class);
        return query.getResultList();
    }

    @Override
    public boolean addProduct(Product product) {
        try {
            em.getTransaction().begin();
            em.persist(product); // Enregistrement du produit
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Erreur lors de l'ajout d'un produit : " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateProduct(Product product) {
        try {
            em.getTransaction().begin();
            em.merge(product); // Mise à jour de l'entité
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Erreur lors de la mise à jour d'un produit : " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteProduct(int id) {
        try {
            em.getTransaction().begin();
            Product product = em.find(Product.class, id);
            if (product != null) {
                em.remove(product); // Suppression du produit
                em.getTransaction().commit();
                return true;
            }
            em.getTransaction().rollback(); // Produit introuvable
            return false;
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Erreur lors de la suppression d'un produit : " + e.getMessage());
            return false;
        }
    }

    @Override
    public int countAllProducts() {
        try {
            Query query = em.createQuery("SELECT COUNT(p) FROM Product p");
            return ((Long) query.getSingleResult()).intValue();
        } catch (Exception e) {
            System.err.println("Erreur lors du comptage des produits : " + e.getMessage());
            return 0;
        }
    }

    /**
     * Récupère une liste paginée de produits en utilisant JPA.
     *
     * @param offset Le nombre d'éléments à ignorer (début de la pagination).
     * @param limit Le nombre maximum de produits à retourner.
     * @return Liste des produits pour la page spécifiée.
     */
    public List<Product> getProducts(int offset, int limit) {
        List<Product> products = null;
        try {

            // JPQL = version objet de SQL. Ici on trie les produits par id.
            TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p ORDER BY p.id", Product.class);

            // Appliquer pagination
            query.setFirstResult(offset); // OFFSET
            query.setMaxResults(limit);   // LIMIT

            products = query.getResultList();

            System.out.println("========================> Nombre de produits récupérés (page): " + products.size());

        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des produits paginés avec JPA : " + e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return products;
    }

    @Override
    public List<Product> findByCriteria(int categoryId, BigDecimal minPrice, BigDecimal maxPrice, boolean featured) {
        try {
            // Construction dynamique de la requête JPQL
            StringBuilder jpql = new StringBuilder("SELECT p FROM Product p WHERE 1=1");

            if (categoryId != -1) {
                jpql.append(" AND p.categoryId = :categoryId");
            }
            if (minPrice != null) {
                jpql.append(" AND p.price >= :minPrice");
            }
            if (maxPrice != null) {
                jpql.append(" AND p.price <= :maxPrice");
            }
            if (featured) {
                jpql.append(" AND p.featured = true");
            }

            TypedQuery<Product> query = em.createQuery(jpql.toString(), Product.class);

            // Application conditionnelle des paramètres
            if (categoryId != -1) {
                query.setParameter("categoryId", categoryId);
            }
            if (minPrice != null) {
                query.setParameter("minPrice", minPrice);
            }
            if (maxPrice != null) {
                query.setParameter("maxPrice", maxPrice);
            }

            return query.getResultList();

        } catch (Exception e) {
            System.err.println("Erreur lors de la recherche par critères : " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public List<Product> findFeaturedProducts() {
        try {
            TypedQuery<Product> query = em.createQuery(
                    "SELECT p FROM Product p WHERE p.featured = true", Product.class
            );
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des produits vedettes : " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public List<Product> getProductsByCategory(int categoryId, int start, int recordsPerPage) {
        System.out.println("Début récupération des produits par catégorie avec JPA (CategoryId: " + categoryId 
            + ", Start: " + start + ", RecordsPerPage: " + recordsPerPage + ")");
        try {
            TypedQuery<Product> query = em.createQuery(
                "SELECT p FROM Product p WHERE p.categoryId = :categoryId ORDER BY p.id", Product.class);
            query.setParameter("categoryId", categoryId);
            query.setFirstResult(start);       // offset
            query.setMaxResults(recordsPerPage); // limit

            List<Product> products = query.getResultList();
            System.out.println("========================> Nombre de produits récupérés (catégorie): " + products.size());
            return products;

        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des produits par catégorie avec JPA : " + e.getMessage());
            return List.of();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public int countProductsByCategory(int categoryId) {
        System.out.println("Comptage des produits par catégorie avec JPA (CategoryId: " + categoryId + ")");
        try {
            Query query = em.createQuery(
                "SELECT COUNT(p) FROM Product p WHERE p.categoryId = :categoryId");
            query.setParameter("categoryId", categoryId);
            Long count = (Long) query.getSingleResult();
            System.out.println("========================> Total produits catégorie: " + count);
            return count.intValue();
        } catch (Exception e) {
            System.err.println("Erreur lors du comptage des produits par catégorie avec JPA : " + e.getMessage());
            return 0;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
