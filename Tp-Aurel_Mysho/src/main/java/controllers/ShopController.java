package controllers;

import Factory.CategoryServiceFactory;
import Factory.ProductServiceFactory;
import interfaces.CategoryService;
import interfaces.ProductService;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Category;
import models.Product;

public class ShopController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProductService productService = ProductServiceFactory.getInstance();
        CategoryService categoryService = CategoryServiceFactory.getInstance();

        // Pagination
        int currentPage = 1;
        if (request.getParameter("page") != null) {
            try {
                currentPage = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }
        int recordsPerPage = 9;
        int start = (currentPage - 1) * recordsPerPage;

        // Récupérer toutes les catégories pour autocomplete
        List<Category> allCategories = categoryService.findAll();
        request.setAttribute("allCategories", allCategories);

        // Lecture des filtres depuis la requête
        int categoryId = -1;
        if (request.getParameter("categoryId") != null && !request.getParameter("categoryId").isEmpty()) {
            try {
                categoryId = Integer.parseInt(request.getParameter("categoryId"));
            } catch (NumberFormatException e) {
                categoryId = -1;
            }
        }

        BigDecimal minPrice = null;
        BigDecimal maxPrice = null;
        try {
            if (request.getParameter("minPrice") != null && !request.getParameter("minPrice").isEmpty()) {
                minPrice = new BigDecimal(request.getParameter("minPrice"));
            }
            if (request.getParameter("maxPrice") != null && !request.getParameter("maxPrice").isEmpty()) {
                maxPrice = new BigDecimal(request.getParameter("maxPrice"));
            }
        } catch (NumberFormatException e) {
            minPrice = null;
            maxPrice = null;
        }

        boolean featured = false;
        String featuredParam = request.getParameter("featured");
        if ("yes".equalsIgnoreCase(featuredParam)) {
            featured = true;
        }

        // Tailles sélectionnées
        String[] selectedSizes = request.getParameterValues("sizes");

        // Construction de la liste des filtres appliqués pour affichage
        List<String> appliedFilters = new ArrayList<>();
        if (categoryId != -1) {
            Category selectedCategory = categoryService.findById(categoryId);
            if (selectedCategory != null) {
                appliedFilters.add("Catégorie: " + selectedCategory.getName());
            }
        }
        if (minPrice != null || maxPrice != null) {
            appliedFilters.add("Prix: " + (minPrice != null ? minPrice : "0") + " - " + (maxPrice != null ? maxPrice : "∞"));
        }
        if (featured) {
            appliedFilters.add("Produits vedettes");
        }
        if (selectedSizes != null) {
            appliedFilters.add("Tailles: " + String.join(", ", selectedSizes));
        }

        // Récupération des produits filtrés depuis la DAO
        List<Product> produitsPagines = productService.findByCriteria(categoryId, minPrice, maxPrice, featured);

        // Filtrer par tailles côté serveur si nécessaire
        if (selectedSizes != null && selectedSizes.length > 0) {
            List<Product> filteredBySizes = new ArrayList<>();
            List<String> sizesList = Arrays.asList(selectedSizes);
            for (Product p : produitsPagines) {
                List<String> productSizes = p.getSizesList();
                for (String s : sizesList) {
                    if (productSizes.contains(s)) {
                        filteredBySizes.add(p);
                        break;
                    }
                }
            }
            produitsPagines = filteredBySizes;
        }

        // Pagination côté serveur
        int totalProducts = produitsPagines.size();
        int totalPages = (int) Math.ceil((double) totalProducts / recordsPerPage);
        int end = Math.min(start + recordsPerPage, totalProducts);
        List<Product> paginatedProducts = produitsPagines.subList(start, end);

        // Transmission des attributs à la JSP
        request.setAttribute("produits", paginatedProducts);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("recordsPerPage", recordsPerPage);
        request.setAttribute("appliedFilters", appliedFilters);

        // Pour reconstruction des paramètres GET dans la pagination
        StringBuilder filterParams = new StringBuilder();
        if (categoryId != -1) filterParams.append("&categoryId=").append(categoryId);
        if (minPrice != null) filterParams.append("&minPrice=").append(minPrice);
        if (maxPrice != null) filterParams.append("&maxPrice=").append(maxPrice);
        if (featured) filterParams.append("&featured=yes");
        if (selectedSizes != null) {
            for (String s : selectedSizes) {
                filterParams.append("&sizes=").append(s);
            }
        }
        request.setAttribute("filterParams", filterParams.toString());

        request.getRequestDispatcher("/views/shop.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
