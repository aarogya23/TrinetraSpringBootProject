// Unified search functionality
const searchGames = (inputId, resultsId, isMobile = false) => {
    const searchInput = document.getElementById(inputId);
    const searchTerm = searchInput.value.trim();
    const resultsDiv = document.getElementById(resultsId);

    // Validate DOM elements
    if (!searchInput || !resultsDiv) {
        console.error(`Missing DOM elements: inputId=${inputId}, resultsId=${resultsId}`);
        return;
    }

    // Clear previous results
    resultsDiv.innerHTML = '';
    resultsDiv.style.display = 'none';

    // Don't search if less than 2 characters
    if (searchTerm.length < 2) return;

    // Call the API
    fetch(`/api/search?q=${encodeURIComponent(searchTerm)}`)
        .then(response => {
            if (!response.ok) throw new Error(`API error: ${response.status}`);
            return response.json();
        })
        .then(games => displayResults(games, resultsDiv))
        .catch(error => {
            console.error('Search error:', error);
            resultsDiv.innerHTML = `<div class="search-error">Error: ${error.message}. Please try again.</div>`;
            resultsDiv.style.display = 'block';
        });
};

// Display search results
const displayResults = (games, resultsDiv) => {
    if (!games || games.length === 0) {
        resultsDiv.innerHTML = '<div class="no-results">No games found</div>';
        resultsDiv.style.display = 'block';
        return;
    }

    const fragment = document.createDocumentFragment();
    games.forEach(game => {
        // Validate game data
        if (!game.id || !game.gamename || !game.gameprice || !game.image) {
            console.warn('Invalid game data:', game);
            return;
        }

        const div = document.createElement('div');
        div.className = 'search-item';
        div.setAttribute('role', 'button');
        div.setAttribute('aria-label', `Go to ${game.gamename}`);
        div.onclick = () => goToGame(game.id); // Use game.id for navigation

        const img = document.createElement('img');
        img.src = `data:image/png;base64,${game.image}`;
        img.alt = game.gamename;

        const details = document.createElement('div');
        details.className = 'search-item-details';

        const name = document.createElement('div');
        name.className = 'search-item-name';
        name.textContent = game.gamename;

        const price = document.createElement('div');
        price.className = 'search-item-price';
        price.textContent = `Rs ${game.gameprice}`;

        details.append(name, price);
        div.append(img, details);
        fragment.append(div);
    });

    resultsDiv.append(fragment);
    resultsDiv.style.display = 'block';
};

// Navigate to game page using /game/{id}
const goToGame = id => {
    if (!id) {
        console.error('No game ID provided for navigation');
        return;
    }
    window.location.href = `/game/${id}`;
};

// Hide results when clicking outside
document.addEventListener('click', e => {
    if (!e.target.closest('.search-box')) {
        ['searchResults', 'mobileSearchResults'].forEach(id => {
            const resultsDiv = document.getElementById(id);
            if (resultsDiv) resultsDiv.style.display = 'none';
        });
    }
});

// Handle form submissions
const setupFormSubmission = (formId, inputId, resultsId, isMobile) => {
    const form = document.getElementById(formId);
    if (!form) {
        console.error(`Form not found: formId=${formId}`);
        return;
    }
    form.addEventListener('submit', e => {
        e.preventDefault();
        searchGames(inputId, resultsId, isMobile);
    });
};

// Debounce search for better performance
let debounceTimeout;
const debounceSearch = (inputId, resultsId, isMobile) => {
    clearTimeout(debounceTimeout);
    debounceTimeout = setTimeout(() => searchGames(inputId, resultsId, isMobile), 300);
};

// Initialize form and input listeners
const initializeSearch = () => {
    // Desktop search
    if (document.getElementById('search-form')) {
        setupFormSubmission('search-form', 'searchInput', 'searchResults', false);
        const searchInput = document.getElementById('searchInput');
        if (searchInput) {
            searchInput.addEventListener('input', () => debounceSearch('searchInput', 'searchResults', false));
        }
    }

    // Mobile search
    if (document.getElementById('mobile-search-form')) {
        setupFormSubmission('mobile-search-form', 'mobile-search-input', 'mobileSearchResults', true);
        const mobileSearchInput = document.getElementById('mobile-search-input');
        if (mobileSearchInput) {
            mobileSearchInput.addEventListener('input', () => debounceSearch('mobile-search-input', 'mobileSearchResults', true));
        }
    }
};

// Run initialization when DOM is loaded
document.addEventListener('DOMContentLoaded', initializeSearch);