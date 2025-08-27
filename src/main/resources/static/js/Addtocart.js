document.addEventListener('DOMContentLoaded', () => {
    const addToCartBtn = document.getElementById('add-to-cart-btn');
    if (!addToCartBtn) {
        console.error('Add to Cart button not found');
        return;
    }

    const gameId = addToCartBtn.getAttribute('data-game-id');
    const gameName = addToCartBtn.getAttribute('data-game-name');
    const gamePrice = parseFloat(addToCartBtn.getAttribute('data-game-price'));
    const gameImage = addToCartBtn.getAttribute('data-game-image');

    if (!gameId || !gameName || isNaN(gamePrice) || !gameImage) {
        console.error('Invalid game data:', { gameId, gameName, gamePrice, gameImage });
        return;
    }

    // Check if game is already in cart on page load
    fetch(`/cart/check?gameId=${gameId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to check cart status');
        }
        return response.json();
    })
    .then(data => {
        if (data.isInCart) {
            addToCartBtn.disabled = true;
            addToCartBtn.textContent = 'In Cart';
        }
    })
    .catch(error => console.error('Error checking cart status:', error));

    // Handle Add to Cart button click
    addToCartBtn.addEventListener('click', () => {
        fetch('/addToCart', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ gameId: parseInt(gameId) })
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to add to cart');
            }
            return response.json();
        })
        .then(data => {
            showToast(data.message);
            addToCartBtn.disabled = true;
            addToCartBtn.textContent = 'In Cart';
            updateCartBadge(data.cartItemCount);
        })
        .catch(error => {
            console.error('Error adding to cart:', error);
            showToast('Failed to add to cart. Please try again.');
        });
    });

    function updateCartBadge(cartItemCount) {
        const cartBadge = document.getElementById('cart-badge');
        const mobileCartBadge = document.getElementById('mobile-cart-badge');
        if (cartBadge && mobileCartBadge) {
            if (cartItemCount > 0) {
                cartBadge.textContent = cartItemCount;
                mobileCartBadge.textContent = cartItemCount;
                cartBadge.style.display = 'inline';
                mobileCartBadge.style.display = 'inline';
            } else {
                cartBadge.style.display = 'none';
                mobileCartBadge.style.display = 'none';
            }
        } else {
            console.error('Cart badge elements not found');
        }
    }

    function showToast(message) {
        const toastContainer = document.getElementById('toast-container');
        if (!toastContainer) {
            console.error('Toast container not found');
            return;
        }
        const toastEl = document.createElement('div');
        toastEl.className = 'toast show';
        toastEl.setAttribute('role', 'alert');
        toastEl.setAttribute('aria-live', 'assertive');
        toastEl.setAttribute('aria-atomic', 'true');
        toastEl.innerHTML = `
            <div class="toast-header">
                <strong class="me-auto">Notification</strong>
                <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
            <div class="toast-body">${message}</div>
        `;
        toastContainer.appendChild(toastEl);
        const toast = new bootstrap.Toast(toastEl);
        toast.show();
        setTimeout(() => toast.hide(), 3000);
    }
});