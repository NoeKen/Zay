/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import Factory.CategoryServiceFactory;
import Factory.ProductServiceFactory;
import interfaces.CategoryService;
import interfaces.ProductService;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import models.Category;
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
    }

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
    //Liste de filtres appliques
    List<String> appliedFilters = new ArrayList<>();

    ProductService productService = ProductServiceFactory.getInstance();
    CategoryService categoryService = CategoryServiceFactory.getInstance();

    // Pagination comme avant
    int currentPage = 1;
    if (request.getParameter("page") != null) {
        try {
            currentPage = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e) {
            currentPage = 1;
        }
    }

    int recordsPerPage = 9;

    // Lecture du paramètre categoryId envoyé depuis la page catégories
    int categoryId = -1; // -1 signifie "toutes catégories"
    if (request.getParameter("categoryId") != null) {
        try {
            categoryId = Integer.parseInt(request.getParameter("categoryId"));
        } catch (NumberFormatException e) {
            categoryId = -1;
        }
    }

    Category selectedCategory = null;
    
    // On compte les produits selon la catégorie, ou tous si categoryId == -1
    int totalProducts;
    if (categoryId == -1) {
        totalProducts = productService.countAllProducts();
    } else {
        totalProducts = productService.countProductsByCategory(categoryId);
        selectedCategory = categoryService.findById(categoryId);    // On recupere la category selectionnee pour l'afficher en front
        appliedFilters.add("Category: " + selectedCategory.getName());
    }
    int totalPages = (int) Math.ceil((double) totalProducts / recordsPerPage);
    int start = (currentPage - 1) * recordsPerPage;

    List<Product> produitsPagines;
    if (categoryId == -1) {
        produitsPagines = productService.getProducts(start, recordsPerPage);
    } else {
        produitsPagines = productService.getProductsByCategory(categoryId, start, recordsPerPage);
    }

    request.setAttribute("produits", produitsPagines);
    request.setAttribute("currentPage", currentPage);
    request.setAttribute("totalPages", totalPages);
    request.setAttribute("recordsPerPage", recordsPerPage);
    request.setAttribute("appliedFilters", appliedFilters); // pour garder la sélection en front

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
    }
}
