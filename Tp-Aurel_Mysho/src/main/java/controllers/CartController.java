/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.dao.CartItemDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import models.CartItem;
import utils.DBConnector;

/**
 *
 * @author admin
 */
public class CartController extends HttpServlet {

    
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
        response.setContentType("text/html;charset=UTF-8");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param req
     * @param resp
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer userId = getUserIdFromSessionOrParam(req);

        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/Auth?page=login");
            return;
        }

        try (Connection conn = DBConnector.getConnection()) {
            CartItemDAO dao = new CartItemDAO(conn);
            List<CartItem> cartItems = dao.getCartItemsByUser(userId);
            for (CartItem cartItem : cartItems) {
                System.err.println("0000000000000 ======> CartIem: "+ cartItem.getProductImage());
                System.err.println("0000000000000 ======> CartIem: "+ cartItem.getProductName());
            }
            req.setAttribute("cartItems", cartItems);
            req.getRequestDispatcher("views/cart.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param req
     * @param resp
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        Integer userId = getUserIdFromSessionOrParam(req);

        // Si pas d'userId → redirection login
        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/Auth?page=login");
            return;
        }

        try (Connection conn = DBConnector.getConnection()) {
            CartItemDAO dao = new CartItemDAO(conn);

            switch (action) {
                case "add": {
                    int productId = Integer.parseInt(req.getParameter("productId"));
                    int quantity = Integer.parseInt(req.getParameter("quantity"));
                    String size = req.getParameter("size");
                    CartItem newItem = new CartItem(userId, productId, quantity, size);
                    dao.addOrUpdateCartItem(newItem);
                    break;
                }
                case "update": {
                    int cartItemId = Integer.parseInt(req.getParameter("cartItemId"));
                    int newQuantity = Integer.parseInt(req.getParameter("quantity"));
                    dao.updateQuantity(cartItemId, newQuantity);
                    break;
                }
                case "remove": {
                    int removeId = Integer.parseInt(req.getParameter("cartItemId"));
                    dao.removeCartItem(removeId);
                    break;
                }
            }
            resp.sendRedirect("views/cart.jsp");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
    
    /** Récupération sécurisée de l'userId */
    private Integer getUserIdFromSessionOrParam(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        Integer userId = null;

//         On essaie depuis la session
        if (session != null && session.getAttribute("userId") != null) {
            userId = (Integer) session.getAttribute("userId");
        } 
//        Sinon, on tente depuis les paramètres (optionnel, panier invité)
        else {
            String userIdParam = req.getParameter("userId");
            if (userIdParam != null && !userIdParam.isEmpty()) {
                try {
                    userId = Integer.parseInt(userIdParam);
                } catch (NumberFormatException e) {
                    userId = null;
                }
            }
        }
        return userId;
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
