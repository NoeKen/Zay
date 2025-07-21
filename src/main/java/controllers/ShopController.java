/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import Factory.ProductServiceFactory;
import interfaces.ProductService;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import models.Product;

/**
 *
 * @author admin
 */
public class ShopController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        ProductDAO dao = new ProductDAO();
//        List<Product> produits = dao.findAll();
//        System.out.println("====================> Produit: debut test <=========================");
//        for (Product produit : produits) {
//            System.out.println("====================> Produit: "+ produit.getName()+"<=========================");
//        }
//        request.setAttribute("produits", produits);
//        request.getRequestDispatcher("/views/shop.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Instanciation de la classe ProductService
        ProductService productService = ProductServiceFactory.getProductService();
        int currentPage = 1; // Page par défaut
        if (request.getParameter("page") != null) {
            try {
                currentPage = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                // Gérer l'erreur si le paramètre 'page' n'est pas un nombre
                currentPage = 1;
            }
        }

        int recordsPerPage = 9; // 3 cartes par ligne * 3 lignes = 9 produits par page

        // Obtenez le nombre total de produits (nécessaire pour calculer le nombre total de pages)
        // Ceci devrait idéalement être optimisé pour ne pas charger tous les produits si vous en avez des milliers
        // La méthode countAllProducts() serait meilleure ici.
        int totalProducts = productService.countAllProducts(); // Supposons cette méthode dans votre DAO

        int totalPages = (int) Math.ceil((double) totalProducts / recordsPerPage);

        int start = (currentPage - 1) * recordsPerPage;
        // Récupérez seulement les produits pour la page actuelle
        List<Product> produitsPagines = productService.getProducts(start, recordsPerPage); 
        
        for (Product produitPagine : produitsPagines) {
            System.out.println("===========> Product name: "+ produitPagine.getName());
            System.out.println("===========> Product description: "+ produitPagine.getDescription());
            System.out.println("<=========================>\n");
        }

        request.setAttribute("produits", produitsPagines);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("recordsPerPage", recordsPerPage); // Utile si vous voulez offrir des options de taille de page

        request.getRequestDispatcher("/views/shop.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
