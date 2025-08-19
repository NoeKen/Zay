<%-- 
    Document   : cart
    Created on : 14 août 2025, 19 h 43 min 30 s
    Author     : admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <!--<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">-->
    <%@ include file="/WEB-INF/jspf/head.jspf" %>
    <title>Mon Panier</title>
</head> 
    <body>
        <%@ include file="/WEB-INF/jspf/header.jspf" %>
        <%@ include file="/WEB-INF/jspf/cart-offcanvas.jspf" %>
        <div class="container my-5">
        <!--        <h1>Mon Panier</h1>-->

            <c:if test="${empty cartItems}">
                <p>Votre panier est vide.</p>
            </c:if>

            <c:if test="${not empty cartItems}">
                <table class="table table-bordered table-striped">
                    <thead>
                        <tr>
                            <th>Produit</th>
                            <th>Image</th>
                            <th>Taille</th>
                            <th>Quantité</th>
                            <th>Prix unitaire</th>
                            <th>Total</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="item" items="${cartItems}">
                        <tr>
                            <td>${item.productName}</td>
                            <td><img src="${pageContext.request.contextPath}/assets/img/${item.productImage}" width="60"></td>
                            <td>${item.size}</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/Cart" method="post" class="d-inline">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="cartItemId" value="${item.id}">
                                    <input type="hidden" name="userId" value="${item.userId}">
                                    <input type="number" name="quantity" value="${item.quantity}" min="1" style="width:70px;">
                                    <button class="btn btn-sm btn-primary" type="submit">OK</button>
                                </form>
                            </td>
                            <td>${item.productPrice} $</td>
                            <td>${item.productPrice * item.quantity} $</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/Cart" method="post" class="d-inline">
                                    <input type="hidden" name="action" value="remove">
                                    <input type="hidden" name="cartItemId" value="${item.id}">
                                    <input type="hidden" name="userId" value="${item.userId}">
                                    <button class="btn btn-sm btn-danger" type="submit">Supprimer</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="text-end">
                    <a href="${pageContext.request.contextPath}/checkout" class="btn btn-success">Passer à la caisse</a>
                </div>
            </c:if>
        </div>
        <%@ include file="/WEB-INF/jspf/footer.jspf" %>
        <%@ include file="/WEB-INF/jspf/scripts.jspf" %>
    </body>
</html>

