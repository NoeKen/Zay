<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Catégories - Mode & Style</title>
    </head>
    <body>
        <!-- Start Categories of The Month -->
    <section class="container py-5">
        <div class="row text-center pt-3">
            <div class="col-lg-6 m-auto">
                <h1 class="h1">Catégories du Mois</h1>
                <p>
                    Découvrez nos collections soigneusement sélectionnées pour chaque style et chaque occasion.  
                    Du look décontracté aux tenues élégantes, trouvez la pièce parfaite pour révéler votre personnalité.
                </p>
            </div>
        </div>
        <div class="row">
            <c:forEach var="category" items="${productsCategories}">
                <div class="col-12 col-md-4 p-5 mt-3">
                    <a href="Shop?categoryId=${category.id}">
                        <img src="${pageContext.request.contextPath}/assets/img/${category.imageUrl}.jpg" 
                             alt="${category.name}" 
                             class="rounded-circle img-fluid border">
                    </a>
                    <h2 class="h5 text-center mt-3 mb-3">${category.name}</h2>
                    <p class="text-center">
                        <a class="btn btn-success" href="Shop?categoryId=${category.id}">Découvrir</a>
                    </p>
                </div>
            </c:forEach>
        </div>
    </section>
    <!-- End Categories of The Month -->
    </body>
</html>
