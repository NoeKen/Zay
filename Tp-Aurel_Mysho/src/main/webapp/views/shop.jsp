<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ include file="/WEB-INF/jspf/head.jspf" %>
        <title>Shop - Zay</title>
        
        <!-- Styles additionnels pour les filtres -->
        <style>
            .filter-section {
                background: #f8f9fa;
                border-radius: 8px;
                padding: 20px;
                margin-bottom: 20px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            }
            
            .filter-title {
                font-size: 1.1rem;
                font-weight: 600;
                color: #333;
                margin-bottom: 15px;
                padding-bottom: 10px;
                border-bottom: 2px solid #59ab6e;
            }
            
            /* Styles pour les checkboxes personnalisées */
            .size-checkbox {
                display: none;
            }
            
            .size-label {
                display: inline-block;
                padding: 8px 16px;
                margin: 5px;
                border: 2px solid #dee2e6;
                border-radius: 4px;
                cursor: pointer;
                transition: all 0.3s;
                background: white;
            }
            
            .size-checkbox:checked + .size-label {
                background: #59ab6e;
                color: white;
                border-color: #59ab6e;
            }
            
            .size-label:hover {
                border-color: #59ab6e;
                transform: translateY(-2px);
            }
            
            /* Styles pour le range slider */
            .price-range-container {
                padding: 10px 0;
            }
            
            .price-inputs {
                display: flex;
                justify-content: space-between;
                margin-bottom: 15px;
            }
            
            .price-input {
                width: 45%;
            }
            
            .slider-container {
                position: relative;
                height: 5px;
                margin: 20px 0;
            }
            
            .slider-track {
                position: absolute;
                width: 100%;
                height: 5px;
                background: #ddd;
                border-radius: 5px;
            }
            
            .slider-range {
                position: absolute;
                height: 5px;
                background: #59ab6e;
                border-radius: 5px;
            }
            
            .slider-thumb {
                position: absolute;
                width: 20px;
                height: 20px;
                background: #59ab6e;
                border: 2px solid white;
                border-radius: 50%;
                cursor: pointer;
                top: -7px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.2);
            }
            
            /* Styles pour les radio buttons */
            .featured-radio {
                display: none;
            }
            
            .featured-label {
                display: inline-block;
                padding: 10px 20px;
                margin: 5px;
                border: 2px solid #dee2e6;
                border-radius: 25px;
                cursor: pointer;
                transition: all 0.3s;
                background: white;
            }
            
            .featured-radio:checked + .featured-label {
                background: #59ab6e;
                color: white;
                border-color: #59ab6e;
            }
            
            /* Autocomplete dropdown */
            .autocomplete-container {
                position: relative;
            }
            
            .autocomplete-results {
                position: absolute;
                top: 100%;
                left: 0;
                right: 0;
                background: white;
                border: 1px solid #ddd;
                border-top: none;
                border-radius: 0 0 4px 4px;
                max-height: 200px;
                overflow-y: auto;
                z-index: 1000;
                display: none;
                box-shadow: 0 4px 6px rgba(0,0,0,0.1);
            }
            
            .autocomplete-item {
                padding: 10px;
                cursor: pointer;
                transition: background 0.2s;
            }
            
            .autocomplete-item:hover,
            .autocomplete-item.active {
                background: #f8f9fa;
            }
            
            .autocomplete-item.selected {
                background: #e8f5e9;
            }
            
            /* Bouton appliquer les filtres */
            .apply-filters-btn {
                width: 100%;
                padding: 12px;
                background: #59ab6e;
                color: white;
                border: none;
                border-radius: 4px;
                font-size: 1.1rem;
                font-weight: 600;
                cursor: pointer;
                transition: all 0.3s;
                margin-top: 20px;
            }
            
            .apply-filters-btn:hover {
                background: #4a9b5e;
                transform: translateY(-2px);
                box-shadow: 0 4px 8px rgba(0,0,0,0.2);
            }
            
            .clear-filters-btn {
                width: 100%;
                padding: 10px;
                background: transparent;
                color: #6c757d;
                border: 2px solid #dee2e6;
                border-radius: 4px;
                cursor: pointer;
                transition: all 0.3s;
                margin-top: 10px;
            }
            
            .clear-filters-btn:hover {
                border-color: #6c757d;
                background: #f8f9fa;
            }
            
            /* Badge pour les filtres actifs */
            .active-filter-badge {
                display: inline-block;
                padding: 5px 10px;
                margin: 2px;
                background: #59ab6e;
                color: white;
                border-radius: 15px;
                font-size: 0.85rem;
            }
            
            .active-filter-badge .remove-filter {
                margin-left: 5px;
                cursor: pointer;
            }
        </style>
    </head>
    <body>
        <%@ include file="/WEB-INF/jspf/header.jspf" %>

        <!-- Start Content -->
        <div class="container py-5">
            <div class="row">
                <!-- Section des filtres -->
                <div class="col-lg-3">
                    <h1 class="h2 pb-4">Filtres</h1>
                    
                    <form id="filterForm" action="${pageContext.request.contextPath}/Shop" method="get">
                        <!-- Catégories avec autocomplete -->
                        <div class="filter-section">
                            <h3 class="filter-title">
                                <i class="fas fa-list"></i> Catégorie
                            </h3>
                            <div class="autocomplete-container">
                                <input type="text" 
                                       id="categorySearch" 
                                       class="form-control" 
                                       placeholder="Rechercher une catégorie..."
                                       autocomplete="off">
                                <input type="hidden" id="categoryId" name="categoryId" value="">
                                <div id="categoryResults" class="autocomplete-results"></div>
                            </div>
                            <div id="selectedCategory" class="mt-2"></div>
                        </div>
                        
                        <!-- Filtre de prix avec double slider -->
                        <div class="filter-section">
                            <h3 class="filter-title">
                                <i class="fas fa-dollar-sign"></i> Fourchette de prix
                            </h3>
                            <div class="price-range-container">
                                <div class="price-inputs">
                                    <div class="price-input">
                                        <label for="minPrice" class="form-label">Min</label>
                                        <div class="input-group">
                                            <span class="input-group-text">$</span>
                                            <input type="number" 
                                                   id="minPrice" 
                                                   name="minPrice" 
                                                   class="form-control" 
                                                   value="0" 
                                                   min="0" 
                                                   max="1000">
                                        </div>
                                    </div>
                                    <div class="price-input">
                                        <label for="maxPrice" class="form-label">Max</label>
                                        <div class="input-group">
                                            <span class="input-group-text">$</span>
                                            <input type="number" 
                                                   id="maxPrice" 
                                                   name="maxPrice" 
                                                   class="form-control" 
                                                   value="500" 
                                                   min="0" 
                                                   max="1000">
                                        </div>
                                    </div>
                                </div>
                                <div class="slider-container">
                                    <div class="slider-track"></div>
                                    <div class="slider-range" id="sliderRange"></div>
                                    <div class="slider-thumb" id="minThumb"></div>
                                    <div class="slider-thumb" id="maxThumb"></div>
                                </div>
                                <div class="text-center text-muted">
                                    <small>Prix: $<span id="priceRangeText">0 - 500</span></small>
                                </div>
                            </div>
                        </div>
                        
                        <!-- Filtre des tailles -->
                        <div class="filter-section">
                            <h3 class="filter-title">
                                <i class="fas fa-ruler"></i> Tailles
                            </h3>
                            <div class="size-filter">
                                <input type="checkbox" id="size-s" name="sizes" value="S" class="size-checkbox">
                                <label for="size-s" class="size-label">S</label>
                                
                                <input type="checkbox" id="size-m" name="sizes" value="M" class="size-checkbox">
                                <label for="size-m" class="size-label">M</label>
                                
                                <input type="checkbox" id="size-l" name="sizes" value="L" class="size-checkbox">
                                <label for="size-l" class="size-label">L</label>
                                
                                <input type="checkbox" id="size-xl" name="sizes" value="XL" class="size-checkbox">
                                <label for="size-xl" class="size-label">XL</label>
                                
                                <input type="checkbox" id="size-xxl" name="sizes" value="XXL" class="size-checkbox">
                                <label for="size-xxl" class="size-label">XXL</label>
                            </div>
                        </div>
                        
                        <!-- Filtre Featured -->
                        <div class="filter-section">
                            <h3 class="filter-title">
                                <i class="fas fa-star"></i> Produits vedettes
                            </h3>
                            <div class="featured-filter">
                                <input type="radio" id="featured-all" name="featured" value="all" class="featured-radio" checked>
                                <label for="featured-all" class="featured-label">
                                    <i class="fas fa-th"></i> Tous
                                </label>
                                
                                <input type="radio" id="featured-yes" name="featured" value="yes" class="featured-radio">
                                <label for="featured-yes" class="featured-label">
                                    <i class="fas fa-star"></i> Vedettes
                                </label>
                                
                                <input type="radio" id="featured-no" name="featured" value="no" class="featured-radio">
                                <label for="featured-no" class="featured-label">
                                    <i class="far fa-star"></i> Standards
                                </label>
                            </div>
                        </div>
                        
                        <!-- Boutons d'action -->
                        <button type="submit" class="apply-filters-btn">
                            <i class="fas fa-filter"></i> Appliquer les filtres
                        </button>
                        <button type="button" class="clear-filters-btn" onclick="clearAllFilters()">
                            <i class="fas fa-times"></i> Effacer les filtres
                        </button>
                    </form>
                </div>

                <!-- Section des produits -->
                <div class="col-lg-9">
                    <!-- Filtres actifs -->
                    <div class="row mb-3">
                        <div class="col-12">
                            <div id="activeFilters">
                                <c:if test="${not empty appliedFilters}">
                                    <h5>Filtres actifs:</h5>
                                    <c:forEach var="filter" items="${appliedFilters}">
                                        <span class="active-filter-badge">
                                            ${filter}
                                            <span class="remove-filter" onclick="removeFilter('${filter}')">×</span>
                                        </span>
                                    </c:forEach>
                                </c:if>
                            </div>
                        </div>
                    </div>
                    
                    <div class="row">
                        <div class="col-md-6">
                            <p class="text-muted">
                                ${totalProducts} produits trouvés
                            </p>
                        </div>
                        <div class="col-md-6 pb-4">
                            <div class="d-flex justify-content-end">
                                <select class="form-control w-auto" onchange="sortProducts(this.value)">
                                    <option value="">Trier par</option>
                                    <option value="name-asc">Nom (A-Z)</option>
                                    <option value="name-desc">Nom (Z-A)</option>
                                    <option value="price-asc">Prix croissant</option>
                                    <option value="price-desc">Prix décroissant</option>
                                    <option value="featured">Produits vedettes</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Grille de produits -->
                    <div class="row">
                        <c:forEach var="product" items="${produits}">
                            <div class="col-md-4">
                                <div class="card mb-4 product-wap rounded-0">
                                    <div class="card rounded-0">
                                        <img class="card-img rounded-0 img-fluid" 
                                             src="${pageContext.request.contextPath}/assets/img/${product.image}"
                                             alt="${product.name}">
                                        <div class="card-img-overlay rounded-0 product-overlay d-flex align-items-center justify-content-center">
                                            <ul class="list-unstyled">
                                                <li><a class="btn btn-success text-white" href="ProductDetails?productId=${product.id}">
                                                    <i class="far fa-heart"></i></a></li>
                                                <li><a class="btn btn-success text-white mt-2" href="ProductDetails?productId=${product.id}">
                                                    <i class="far fa-eye"></i></a></li>
                                                <li><a class="btn btn-success text-white mt-2" href="ProductDetails?productId=${product.id}">
                                                    <i class="fas fa-cart-plus"></i></a></li>
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="card-body">
                                        <a href="ProductDetails?productId=${product.id}" class="h3 text-decoration-none">${product.name}</a>
                                        <ul class="w-100 list-unstyled d-flex justify-content-between mb-0">
                                            <li>
                                                <c:forEach var="size" items="${product.sizesList}" varStatus="status">
                                                    ${size}<c:if test="${!status.last}">/</c:if>
                                                </c:forEach>
                                            </li>
                                            <li class="pt-2">
                                                <c:if test="${product.featured}">
                                                    <span class="badge bg-warning text-dark">
                                                        <i class="fas fa-star"></i> Vedette
                                                    </span>
                                                </c:if>
                                            </li>
                                        </ul>
                                        <ul class="list-unstyled d-flex justify-content-center mb-1">
                                            <li>
                                                <c:forEach var="i" begin="1" end="${product.avgRating}">
                                                    <i class="text-warning fa fa-star"></i>
                                                </c:forEach>
                                                <c:forEach var="i" begin="1" end="${5 - product.avgRating}">
                                                    <i class="text-muted fa fa-star"></i>
                                                </c:forEach>
                                                <small class="text-muted">(${product.nbrComments})</small>
                                            </li>
                                        </ul>
                                        <p class="text-center mb-0">$${product.price}</p>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    
                    <!-- Pagination -->
                    <div class="row">
                        <div class="col-12">
                            <ul class="pagination pagination-lg justify-content-end">
                                <li class="page-item <c:if test="${currentPage == 1}">disabled</c:if>">
                                    <a class="page-link rounded-0 mr-3 shadow-sm border-top-0 border-left-0" 
                                       href="?page=${currentPage - 1}${filterParams}" 
                                       tabindex="-1">Précédent</a>
                                </li>

                                <c:forEach begin="1" end="${totalPages}" var="i">
                                    <li class="page-item <c:if test="${currentPage == i}">active</c:if>">
                                        <a class="page-link rounded-0 mr-3 shadow-sm border-top-0 border-left-0 
                                           <c:if test="${currentPage == i}">text-white bg-success</c:if>" 
                                           href="?page=${i}${filterParams}">${i}</a>
                                    </li>
                                </c:forEach>

                                <li class="page-item <c:if test="${currentPage == totalPages}">disabled</c:if>">
                                    <a class="page-link rounded-0 shadow-sm border-top-0 border-left-0" 
                                       href="?page=${currentPage + 1}${filterParams}">Suivant</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- End Content -->

        <%@ include file="/WEB-INF/jspf/footer.jspf" %>
        <%@ include file="/WEB-INF/jspf/scripts.jspf" %>
        
        <!-- Script pour les filtres -->
        <script>
            // Liste des catégories (à remplacer par un appel AJAX)
            const categories = [
                { id: 1, name: "Watches" },
                { id: 2, name: "Shoes" },
                { id: 3, name: "Glasses" },
                { id: 4, name: "Electronics" },
                { id: 5, name: "Home & Living" },
                { id: 6, name: "Sportswear" },
                { id: 7, name: "Jewelry" },
                { id: 8, name: "Beauty" },
                { id: 9, name: "Kids" },
                { id: 10, name: "Outdoors" }
            ];
            
            // Autocomplete pour les catégories
            const categorySearch = document.getElementById('categorySearch');
            const categoryResults = document.getElementById('categoryResults');
            const categoryIdInput = document.getElementById('categoryId');
            const selectedCategoryDiv = document.getElementById('selectedCategory');
            
            categorySearch.addEventListener('input', function() {
                const searchTerm = this.value.toLowerCase();
                
                if (searchTerm.length < 1) {
                    categoryResults.style.display = 'none';
                    return;
                }
                
                const filteredCategories = categories.filter(cat => 
                    cat.name.toLowerCase().includes(searchTerm)
                );
                
                if (filteredCategories.length > 0) {
                    categoryResults.innerHTML = '';
                    filteredCategories.forEach(cat => {
                        const item = document.createElement('div');
                        item.className = 'autocomplete-item';
                        item.textContent = cat.name;
                        item.onclick = function() {
                            selectCategory(cat);
                        };
                        categoryResults.appendChild(item);
                    });
                    categoryResults.style.display = 'block';
                } else {
                    categoryResults.style.display = 'none';
                }
            });
            
            function selectCategory(category) {
                categorySearch.value = '';
                categoryIdInput.value = category.id;
                categoryResults.style.display = 'none';
                
                selectedCategoryDiv.innerHTML = `
                    <span class="active-filter-badge">
                        ${category.name}
                        <span class="remove-filter" onclick="clearCategory()">×</span>
                    </span>
                `;
            }
            
            function clearCategory() {
                categoryIdInput.value = '';
                selectedCategoryDiv.innerHTML = '';
            }
            
            // Fermer l'autocomplete en cliquant ailleurs
            document.addEventListener('click', function(e) {
                if (!e.target.closest('.autocomplete-container')) {
                    categoryResults.style.display = 'none';
                }
            });
            
            // Gestion du double slider de prix
            const minPriceInput = document.getElementById('minPrice');
            const maxPriceInput = document.getElementById('maxPrice');
            const priceRangeText = document.getElementById('priceRangeText');
            const sliderRange = document.getElementById('sliderRange');
            
            function updatePriceRange() {
                const min = parseInt(minPriceInput.value);
                const max = parseInt(maxPriceInput.value);
                
                if (min > max) {
                    minPriceInput.value = max;
                    return;
                }
                
                priceRangeText.textContent = `${min} - ${max}`;
                
                // Mettre à jour la barre de progression
                const percentMin = (min / 1000) * 100;
                const percentMax = (max / 1000) * 100;
                sliderRange.style.left = percentMin + '%';
                sliderRange.style.width = (percentMax - percentMin) + '%';
            }
            
            minPriceInput.addEventListener('input', updatePriceRange);
            maxPriceInput.addEventListener('input', updatePriceRange);
            
            // Fonction pour effacer tous les filtres
            function clearAllFilters() {
                document.getElementById('filterForm').reset();
                clearCategory();
                updatePriceRange();
            }
            
            // Fonction pour retirer un filtre spécifique
            function removeFilter(filter) {
                // Logique pour retirer un filtre spécifique
                // À implémenter selon vos besoins
                location.reload();
            }
            
            // Fonction de tri
            function sortProducts(sortBy) {
                const currentUrl = new URL(window.location.href);
                currentUrl.searchParams.set('sort', sortBy);
                window.location.href = currentUrl.toString();
            }
            
            // Initialisation
            updatePriceRange();
        </script>
    </body>
</html>