// DOM elements
        const hamburgerMenu = document.getElementById('hamburger-menu');
        const hamburgerIcon = document.getElementById('hamburger-icon');
        const mobileNav = document.getElementById('mobile-nav');
        const profileContainer = document.getElementById('profile-container');
        const profileDropdown = document.getElementById('profile-dropdown');
        const mobileProfileContainer = document.getElementById('mobile-profile-container');
        const mobileProfileDropdown = document.getElementById('mobile-profile-dropdown');
        const logoutButton = document.getElementById('logout-button');
        const mobileLogoutButton = document.getElementById('mobile-logout-button');
        const searchForm = document.getElementById('search-form');
        const mobileSearchForm = document.getElementById('mobile-search-form');
        const searchInput = document.getElementById('search-input');
        const mobileSearchInput = document.getElementById('mobile-search-input');
        
        // Badge elements
        const cartBadge = document.getElementById('cart-badge');
        const wishlistBadge = document.getElementById('wishlist-badge');
        const mobileCartBadge = document.getElementById('mobile-cart-badge');
        const mobileWishlistBadge = document.getElementById('mobile-wishlist-badge');

        // Initial counts update
        updateCounts();

        // Toggle mobile menu
        hamburgerMenu.addEventListener('click', () => {
            hamburgerIcon.classList.toggle('open');
            mobileNav.classList.toggle('open');
        });

        // Toggle profile dropdown
        profileContainer.addEventListener('click', (e) => {
            e.stopPropagation();
            profileDropdown.style.display = profileDropdown.style.display === 'none' ? 'block' : 'none';
            if (mobileProfileDropdown.style.display !== 'none') {
                mobileProfileDropdown.style.display = 'none';
            }
        });

        // Toggle mobile profile dropdown
        mobileProfileContainer.addEventListener('click', (e) => {
            e.stopPropagation();
            mobileProfileDropdown.style.display = mobileProfileDropdown.style.display === 'none' ? 'block' : 'none';
            if (profileDropdown.style.display !== 'none') {
                profileDropdown.style.display = 'none';
            }
        });

        // Close dropdowns when clicking outside
        document.addEventListener('click', (e) => {
            if (!profileContainer.contains(e.target)) {
                profileDropdown.style.display = 'none';
            }
            if (!mobileProfileContainer.contains(e.target)) {
                mobileProfileDropdown.style.display = 'none';
            }
        });

        // Handle logout
        logoutButton.addEventListener('click', handleLogout);
        mobileLogoutButton.addEventListener('click', handleLogout);

        // Handle search form submission
        searchForm.addEventListener('submit', (e) => {
            handleSearch(e, searchInput.value);
        });

        mobileSearchForm.addEventListener('submit', (e) => {
            handleSearch(e, mobileSearchInput.value);
            hamburgerIcon.classList.remove('open');
            mobileNav.classList.remove('open');
        });

        // Function to handle search
        function handleSearch(e, searchTerm) {
            e.preventDefault();
            console.log("Searching for:", searchTerm);
            // Implement search functionality here
            // You could redirect to a search results page with a query parameter
            // window.location.href = `search-results.html?q=${encodeURIComponent(searchTerm)}`;
        }

        // Function to handle logout
        function handleLogout() {
            console.log("Logging out...");
            // Implement logout functionality here
            // This could involve clearing localStorage tokens, redirecting to login page, etc.
        }

        // Function to update cart and wishlist counts
        function updateCounts() {
            // Get cart and wishlist from localStorage
            const cart = JSON.parse(localStorage.getItem('cart') || '[]');
            const wishlist = JSON.parse(localStorage.getItem('wishlist') || '[]');
            
            // Calculate total items in cart
            const totalCartItems = cart.reduce((total, item) => total + item.quantity, 0);
            
            // Update badges
            updateBadge(cartBadge, totalCartItems);
            updateBadge(mobileCartBadge, totalCartItems);
            updateBadge(wishlistBadge, wishlist.length);
            updateBadge(mobileWishlistBadge, wishlist.length);
        }

        // Helper function to update badge display
        function updateBadge(badgeElement, count) {
            if (count > 0) {
                badgeElement.textContent = count;
                badgeElement.style.display = 'flex';
            } else {
                badgeElement.style.display = 'none';
            }
        }

        // Listen for storage changes
        window.addEventListener('storage', updateCounts);
        
        // Custom events for direct updates
        window.addEventListener('cartUpdated', updateCounts);
        window.addEventListener('wishlistUpdated', updateCounts);

        // Initialize localStorage if needed
        if (!localStorage.getItem('cart')) {
            localStorage.setItem('cart', '[]');
        }
        
        if (!localStorage.getItem('wishlist')) {
            localStorage.setItem('wishlist', '[]');
        }