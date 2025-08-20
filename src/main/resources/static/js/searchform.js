
    // Simple search functionality
    const searchGames = () => {
        const searchInput = document.getElementById('searchInput');
        const searchTerm = searchInput.value.trim();
        const resultsDiv = document.getElementById('searchResults');
        
        // Clear previous results
        resultsDiv.innerHTML = '';
        resultsDiv.style.display = 'none';
        
        // Don't search if less than 2 characters
        if (searchTerm.length < 2) return;
        
        // Call the API
        fetch(`/api/search?q=${encodeURIComponent(searchTerm)}`)
            .then(response => response.json())
            .then(games => displayResults(games, resultsDiv))
            .catch(error => {
                console.error('Search error:', error);
                resultsDiv.innerHTML = '<div class="search-error">Search failed. Please try again.</div>';
                resultsDiv.style.display = 'block';
            });
    };

    // Mobile search function
    const searchGamesMobile = () => {
        const searchInput = document.getElementById('mobile-search-input');
        const searchTerm = searchInput.value.trim();
        const resultsDiv = document.getElementById('mobileSearchResults');
        
        // Clear previous results
        resultsDiv.innerHTML = '';
        resultsDiv.style.display = 'none';
        
        if (searchTerm.length < 2) return;
        
        fetch(`/api/search?q=${encodeURIComponent(searchTerm)}`)
            .then(response => response.json())
            .then(games => displayResults(games, resultsDiv))
            .catch(error => {
                console.error('Search error:', error);
                resultsDiv.innerHTML = '<div class="search-error">Search failed. Please try again.</div>';
                resultsDiv.style.display = 'block';
            });
    };

    const displayResults = (games, resultsDiv) => {
        if (games.length === 0) {
            resultsDiv.innerHTML = '<div class="no-results">No games found</div>';
            resultsDiv.style.display = 'block';
            return;
        }
        
        const html = games.map(game => `
            <div class="search-item" onclick="goToGame('${game.link}')">
                <img src="data:image/png;base64,${game.image}" alt="${game.gamename}">
                <div class="search-item-details">
                    <div class="search-item-name">${game.gamename}</div>
                    <div class="search-item-price">Rs ${game.gameprice}</div>
                </div>
            </div>
        `).join('');
        
        resultsDiv.innerHTML = html;
        resultsDiv.style.display = 'block';
    };

    const goToGame = link => window.location.href = link;

    // Hide results when clicking outside
    document.addEventListener('click', e => {
        if (!e.target.closest('.search-box')) {
            const desktopResults = document.getElementById('searchResults');
            const mobileResults = document.getElementById('mobileSearchResults');
            
            if (desktopResults) desktopResults.style.display = 'none';
            if (mobileResults) mobileResults.style.display = 'none';
        }
    });

    // Handle form submission
    document.getElementById('search-form').addEventListener('submit', e => {
        e.preventDefault();
        const searchTerm = document.getElementById('searchInput').value.trim();
        if (searchTerm) console.log('Searching for:', searchTerm);
    });

    document.getElementById('mobile-search-form').addEventListener('submit', e => {
        e.preventDefault();
        const searchTerm = document.getElementById('mobile-search-input').value.trim();
        if (searchTerm) console.log('Mobile searching for:', searchTerm);
    });

