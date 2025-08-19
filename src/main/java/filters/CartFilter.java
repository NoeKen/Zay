package filters;

import dal.dao.CartItemDAO;
import models.CartItem;
import utils.DBConnector;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class CartFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false);

        // Par défaut : panier vide
        List<CartItem> cartItems = null;

        // Vérifier si l'utilisateur est connecté et a un ID
        if (session != null && session.getAttribute("userId") != null) {
            int userId = (int) session.getAttribute("userId");
            try (Connection conn = DBConnector.getConnection()) {
                CartItemDAO dao = new CartItemDAO(conn);
                cartItems = dao.getCartItemsByUser(userId);
            } catch (Exception e) {
                throw new ServletException("Erreur lors du chargement du panier", e);
            }
        }

        // Placer le panier dans la requête pour être accessible aux JSP
        req.setAttribute("cartItems", cartItems);

        // Continuer la chaîne de filtres
        chain.doFilter(request, response);
    }
}
