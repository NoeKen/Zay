<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Accueil - Mode & Style</title>
    </head>
    <body>
        <!-- Start Banner Hero -->
    <div id="template-mo-zay-hero-carousel" class="carousel slide" data-bs-ride="carousel">
        <ol class="carousel-indicators">
            <li data-bs-target="#template-mo-zay-hero-carousel" data-bs-slide-to="0" class="active"></li>
            <li data-bs-target="#template-mo-zay-hero-carousel" data-bs-slide-to="1"></li>
            <li data-bs-target="#template-mo-zay-hero-carousel" data-bs-slide-to="2"></li>
        </ol>
        <div class="carousel-inner">
            <!-- Slide 1 -->
            <div class="carousel-item active">
                <div class="container">
                    <div class="row p-5">
                        <div class="mx-auto col-md-8 col-lg-6 order-lg-last">
                            <img class="img-fluid" src="${pageContext.request.contextPath}/assets/img/banner_img_01.jpg" alt="Nouvelle collection">
                        </div>
                        <div class="col-lg-6 mb-0 d-flex align-items-center">
                            <div class="text-align-left align-self-center">
                                <h1 class="h1 text-success"><b>Style&Co</b> Mode</h1>
                                <h3 class="h2">Nouvelle collection printemps-été</h3>
                                <p>
                                    Découvrez les dernières tendances mode pour femmes, hommes et enfants.  
                                    Des vêtements confortables, élégants et accessibles pour sublimer chaque instant de votre journée.  
                                    Profitez de nos offres exclusives et démarquez-vous avec Style&Co.
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Slide 2 -->
            <div class="carousel-item">
                <div class="container">
                    <div class="row p-5">
                        <div class="mx-auto col-md-8 col-lg-6 order-lg-last">
                            <img class="img-fluid" src="${pageContext.request.contextPath}/assets/img/banner_img_02.jpg" alt="Mode urbaine">
                        </div>
                        <div class="col-lg-6 mb-0 d-flex align-items-center">
                            <div class="text-align-left">
                                <h1 class="h1">Style urbain</h1>
                                <h3 class="h2">Look moderne & décontracté</h3>
                                <p>
                                    Que vous soyez au bureau ou en sortie entre amis, notre collection urbaine s’adapte à tous vos moments.  
                                    Des pièces stylées, faciles à assortir et pensées pour votre confort au quotidien.
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Slide 3 -->
            <div class="carousel-item">
                <div class="container">
                    <div class="row p-5">
                        <div class="mx-auto col-md-8 col-lg-6 order-lg-last">
                            <img class="img-fluid" src="${pageContext.request.contextPath}/assets/img/banner_img_03.jpg" alt="Accessoires de mode">
                        </div>
                        <div class="col-lg-6 mb-0 d-flex align-items-center">
                            <div class="text-align-left">
                                <h1 class="h1">Accessoires tendance</h1>
                                <h3 class="h2">La touche finale à votre look</h3>
                                <p>
                                    Complétez votre tenue avec notre sélection d’accessoires : sacs, lunettes, ceintures et plus encore.  
                                    Des détails qui font toute la différence pour un style unique.
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Navigation buttons -->
        <a class="carousel-control-prev text-decoration-none w-auto ps-3" href="#template-mo-zay-hero-carousel" role="button" data-bs-slide="prev">
            <i class="fas fa-chevron-left"></i>
        </a>
        <a class="carousel-control-next text-decoration-none w-auto pe-3" href="#template-mo-zay-hero-carousel" role="button" data-bs-slide="next">
            <i class="fas fa-chevron-right"></i>
        </a>
    </div>
    <!-- End Banner Hero -->
    </body>
</html>
