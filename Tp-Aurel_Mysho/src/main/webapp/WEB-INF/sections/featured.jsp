<%-- 
    Document   : featured
    Created on : 15 juill. 2025, 12 h 18 min 54 s
    Author     : Aurel Noe Kenfack
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Feateured Product section</title>
    </head>
    <body>
        <section class="bg-light">
        <div class="container py-5">
            <div class="row text-center py-3">
                <div class="col-lg-6 m-auto">
                    <h1 class="h1">Featured Product</h1>
                    <p>
                        Découvrez notre sélection exclusive de produits mis en avant, choisis avec soin pour leur qualité, leur design et leur popularité auprès de nos clients. 
                        Chaque article présenté ici allie style et performance, afin de vous offrir le meilleur de notre catalogue. 
                        Laissez-vous inspirer et trouvez le produit qui répondra parfaitement à vos besoins.
                    </p>
                </div>
            </div>
            <div class="row">
                <%--<%@include file="/WEB-INF/jspf/product.jspf" %>--%>
                <c:forEach var="product" items="${featuredProducts}">
                    <div class="col-12 col-md-4 mb-4">
                        <div class="card h-100">
                            <a href="ProductDetails?productId=${product.id}">
                                <img src="${pageContext.request.contextPath}/assets/img/feature_prod_02.jpg" class="card-img-top" alt="...">
                            </a>
                            <div class="card-body">
                                <ul class="list-unstyled d-flex justify-content-between">
                                    <li>
                                        <!-- Affichage des étoiles pleines -->
                                        <c:forEach var="i" begin="1" end="${product.avgRating}">
                                            <i class="text-warning fa fa-star"></i>
                                        </c:forEach>

                                        <!-- Affichage des étoiles vides -->
                                        <c:forEach var="i" begin="1" end="${5 - product.avgRating}">
                                            <i class="text-muted fa fa-star"></i>
                                        </c:forEach>
                                    </li>
                                    <li class="text-muted text-right">${product.price}</li>
                                </ul>
                                <a href="shop-single.html" class="h2 text-decoration-none text-dark">${product.name}</a>
                                <p class="card-text">
                                    ${product.description}
                                </p>
                                <p class="text-muted">Reviews (${product.nbrComments})</p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </section>
    <!-- End Featured Product -->
    </body>
</html>
