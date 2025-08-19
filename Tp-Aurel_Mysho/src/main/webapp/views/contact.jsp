<%-- 
    Document   : contact
    Created on : 16 juill. 2025, 05 h 17 min 55 s
    Author     : Aurel Noe Kenfack
--%>

<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/WEB-INF/jspf/head.jspf" %>
        <title>Contact</title>
    </head>
    <body>
        <%@ include file="/WEB-INF/jspf/header.jspf" %>
        <%@ include file="/WEB-INF/jspf/cart-offcanvas.jspf" %>

        <!-- Start Content Page -->
        <div class="container-fluid bg-light py-5">
            <div class="col-md-6 m-auto text-center">
                <h1 class="h1">Nous Contacter</h1>
                <p>
                    Vous avez une question sur un produit, une commande ou nos services ?  
                    Remplissez le formulaire ci-dessous et notre équipe vous répondra dans les plus brefs délais.  
                    Nous sommes toujours heureux de vous aider.
                </p>
            </div>
        </div>

        <!-- Start Contact -->
        <div class="container py-5">
            <div class="row py-5">
                <form class="col-md-9 m-auto" method="post" action="${pageContext.request.contextPath}/ContactController" role="form">
                    <div class="row">
                        <div class="form-group col-md-6 mb-3">
                            <label for="inputname">Nom complet</label>
                            <input type="text" class="form-control mt-1" id="name" name="name" placeholder="Votre nom" value="${param.name}">
                        </div>
                        <div class="form-group col-md-6 mb-3">
                            <label for="inputemail">Adresse e-mail</label>
                            <input type="email" class="form-control mt-1" id="email" name="email" placeholder="Votre email" value="${param.email}">
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="inputsubject">Sujet</label>
                        <input type="text" class="form-control mt-1" id="subject" name="subject" placeholder="Objet de votre message" value="${param.subject}">
                    </div>
                    <div class="mb-3">
                        <label for="inputmessage">Message</label>
                        <textarea class="form-control mt-1" id="message" name="message" placeholder="Écrivez votre message ici..." rows="8">${param.message}</textarea>
                    </div>
                    <div class="row">
                        <div class="col text-end mt-2">
                            <button type="submit" class="btn btn-success btn-lg px-3">Envoyer</button>
                        </div>
                    </div>

                    <c:if test="${not empty successMessage}">
                        <div class="alert alert-success mt-3">${successMessage}</div>
                    </c:if>
                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger mt-3">${errorMessage}</div>
                    </c:if>
                </form>
            </div>
        </div>
        <!-- End Contact -->

        <%@ include file="/WEB-INF/jspf/footer.jspf" %>
        <%@ include file="/WEB-INF/jspf/scripts.jspf" %>
    </body>
</html>
