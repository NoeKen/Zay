
    // Sélection taille
    function selectSize(size) {
        document.getElementById("product-size").value = size;
        document.querySelectorAll(".btn-size").forEach(btn => btn.classList.remove("active"));
        event.target.classList.add("active");
    }

    // Gestion quantité
    document.getElementById("btn-plus").addEventListener("click", function() {
        let quantity = parseInt(document.getElementById("product-quantity").value);
        quantity++;
        document.getElementById("product-quantity").value = quantity;
        document.getElementById("var-value").textContent = quantity;
    });

    document.getElementById("btn-minus").addEventListener("click", function() {
        let quantity = parseInt(document.getElementById("product-quantity").value);
        if (quantity > 1) quantity--;
        document.getElementById("product-quantity").value = quantity;
        document.getElementById("var-value").textContent = quantity;
    });



<!-- Script pour les filtres -->
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
