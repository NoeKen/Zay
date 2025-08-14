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
public class ProductDetailsController extends HttpServlet {

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
        ProductService productService = ProductServiceFactory.getInstance();
        
        Product currentProduct = null;
        
        List<String> sizesList = new ArrayList();
        List<Product> relatedProducts = new ArrayList();
        
        if (request.getParameter("productId") != null){
            currentProduct = productService.findById(Integer.parseInt(request.getParameter("productId")));
            relatedProducts = productService.getProductsByCategory(currentProduct.getCategoryId(), 1, 9);
            sizesList = currentProduct.getSizesList();
        }
        
        for (Product relatedProduct : relatedProducts) {
            System.out.println("related product: "+ relatedProduct.getName());
        }
        request.setAttribute("currentProduct", currentProduct);
        request.setAttribute("currentProductsizes", sizesList);
        request.setAttribute("relatedProducts", relatedProducts);
        request.getRequestDispatcher("/views/product-details.jsp").forward(request, response);
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
