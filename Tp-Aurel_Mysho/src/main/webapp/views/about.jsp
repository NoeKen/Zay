<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/WEB-INF/jspf/head.jspf" %>
        <title>À propos - Notre Histoire</title>
    </head>
    <body>
        <%@ include file="/WEB-INF/jspf/header.jspf" %>
        <%@ include file="/WEB-INF/jspf/cart-offcanvas.jspf" %>
        
    <!-- Bannière -->
    <section class="bg-success py-5">
        <div class="container">
            <div class="row align-items-center py-5">
                <div class="col-md-8 text-white">
                    <h1>À propos de nous</h1>
                    <p>
                        Nous croyons que chaque vêtement raconte une histoire. Depuis nos débuts, notre mission est
                        d’offrir des pièces de qualité qui allient style, confort et authenticité.  
                        Que ce soit pour un look casual ou une tenue élégante, nous mettons tout en œuvre pour vous aider à exprimer votre personnalité à travers la mode.
                    </p>
                </div>
                <div class="col-md-4">
                    <img src="${pageContext.request.contextPath}/assets/img/about-hero.svg" alt="Notre équipe et notre vision">
                </div>
            </div>
        </div>
    </section>
    <!-- Fin Bannière -->

    <!-- Nos Services -->
    <section class="container py-5">
        <div class="row text-center pt-5 pb-3">
            <div class="col-lg-6 m-auto">
                <h1 class="h1">Nos Services</h1>
                <p>
                    Nous vous accompagnons avant, pendant et après votre achat pour garantir une expérience shopping parfaite.
                </p>
            </div>
        </div>
        <div class="row">

            <div class="col-md-6 col-lg-3 pb-5">
                <div class="h-100 py-5 services-icon-wap shadow">
                    <div class="h1 text-success text-center"><i class="fa fa-truck fa-lg"></i></div>
                    <h2 class="h5 mt-4 text-center">Livraison Rapide</h2>
                </div>
            </div>

            <div class="col-md-6 col-lg-3 pb-5">
                <div class="h-100 py-5 services-icon-wap shadow">
                    <div class="h1 text-success text-center"><i class="fas fa-exchange-alt"></i></div>
                    <h2 class="h5 mt-4 text-center">Échanges & Retours</h2>
                </div>
            </div>

            <div class="col-md-6 col-lg-3 pb-5">
                <div class="h-100 py-5 services-icon-wap shadow">
                    <div class="h1 text-success text-center"><i class="fa fa-percent"></i></div>
                    <h2 class="h5 mt-4 text-center">Promotions Exclusives</h2>
                </div>
            </div>

            <div class="col-md-6 col-lg-3 pb-5">
                <div class="h-100 py-5 services-icon-wap shadow">
                    <div class="h1 text-success text-center"><i class="fa fa-user"></i></div>
                    <h2 class="h5 mt-4 text-center">Service Client 24/7</h2>
                </div>
            </div>
        </div>
    </section>
    <!-- Fin Services -->

    <!-- Nos Marques -->
    <section class="bg-light py-5">
        <div class="container my-4">
            <div class="row text-center py-3">
                <div class="col-lg-6 m-auto">
                    <h1 class="h1">Nos Marques Partenaires</h1>
                    <p>
                        Nous collaborons avec des marques reconnues pour leur savoir-faire et leur engagement envers la qualité et la durabilité.
                    </p>
                </div>
                <div class="col-lg-9 m-auto tempaltemo-carousel">
                    <div class="row d-flex flex-row">
                        <!-- Contrôles -->
                        <div class="col-1 align-self-center">
                            <a class="h1" href="#templatemo-slide-brand" role="button" data-bs-slide="prev">
                                <i class="text-light fas fa-chevron-left"></i>
                            </a>
                        </div>
                        <!-- Fin Contrôles -->

                        <!-- Carousel -->
                        <div class="col">
                            <div class="carousel slide carousel-multi-item pt-2 pt-md-0" id="templatemo-slide-brand" data-bs-ride="carousel">
                                <div class="carousel-inner product-links-wap" role="listbox">
                                    <div class="carousel-item active">
                                        <div class="row">
                                            <c:forEach var="brand" items="${brandsList}">
                                                <div class="col-3 p-md-5">
                                                    <a href="#"><img class="img-fluid brand-img" src="${pageContext.request.contextPath}/assets/img/${brand.logo}" alt="${brand.name}"></a>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                    <!-- Tu peux dupliquer ce bloc pour d'autres slides -->
                                </div>
                            </div>
                        </div>
                        <!-- Fin Carousel -->

                        <!-- Contrôles -->
                        <div class="col-1 align-self-center">
                            <a class="h1" href="#templatemo-slide-brand" role="button" data-bs-slide="next">
                                <i class="text-light fas fa-chevron-right"></i>
                            </a>
                        </div>
                        <!-- Fin Contrôles -->
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- Fin Marques -->

        <%@ include file="/WEB-INF/jspf/footer.jspf" %>
        <%@ include file="/WEB-INF/jspf/scripts.jspf" %>
    </body>
</html>
