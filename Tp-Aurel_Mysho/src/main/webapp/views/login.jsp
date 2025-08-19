<%-- 
    Document   : auth
    Created on : 14 août 2025, 01 h 14 min 50 s
    Author     : admin
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Connexion</title>
    <%@ include file="/WEB-INF/jspf/head.jspf" %>
</head>
    <body class="bg-light">
        <%@ include file="/WEB-INF/jspf/header.jspf" %>
        <%@ include file="/WEB-INF/jspf/cart-offcanvas.jspf" %>
        <div class="container">
            <div class="row justify-content-center mt-5">
                <div class="col-md-6">
                    <!--<h1 class="text-center text-success mb-4">Zay</h1>-->

                    <div class="card shadow-sm">
                        <div class="card-body">
                            <h3 class="card-title text-center mb-4">Connexion</h3>

                            <c:if test="${not empty error}">
                                <div class="alert alert-danger">${error}</div>
                            </c:if>


                            <form action="${pageContext.request.contextPath}/Auth?login"  method="post">
                                <input type="hidden" name="action" value="login"/>
                                <div class="mb-3">
                                    <label for="email" class="form-label">Email</label>
                                    <input type="email" class="form-control" id="email" name="email" placeholder="Votre email" required>
                                </div>
                                <div class="mb-3">
                                    <label for="password" class="form-label">Mot de passe</label>
                                    <input type="password" class="form-control" id="password" name="password" placeholder="Votre mot de passe" required>
                                </div>
                                <div class="d-grid">
                                    <button type="submit" class="btn btn-primary">Se connecter</button>
                                </div>
                            </form>

                            <hr>
                            <p class="text-center mb-0">
                                Pas encore de compte ?<a href="${pageContext.request.contextPath}/Auth?page=register">S'inscrire</a>

                            </p>
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <%--<%@ include file="/WEB-INF/jspf/footer.jspf" %>--%>
        <%@ include file="/WEB-INF/jspf/scripts.jspf" %>
    </body>
</html>
