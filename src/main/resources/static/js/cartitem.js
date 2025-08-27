// src/main/resources/static/js/cartitem.js
document.addEventListener('DOMContentLoaded', () => {
    const cartItemsContainer = document.getElementById('cart-items');
    const cartEmptyMessage = document.getElementById('cart-empty');
    const cartTotalContainer = document.getElementById('cart-total');
    const checkoutButton = document.getElementById('checkout-button');
    const clearCartButton = document.getElementById('clear-cart-btn');

    if (!cartItemsContainer || !cartEmptyMessage || !cartTotalContainer || !checkoutButton || !clearCartButton) {
        console.error('Cart page elements missing');
        return;
    }

    let cart;
    try {
        cart = JSON.parse(localStorage.getItem('cart')) || [];
    } catch (e) {
        console.error('Error parsing cart from localStorage:', e);
        cart = [];
    }
    console.log('Cart data:', cart);

    if (cart.length === 0) {
        cartEmptyMessage.style.display = 'block';
        checkoutButton.style.display = 'none';
    } else {
        cartEmptyMessage.style.display = 'none';
        checkoutButton.style.display = 'block';

        cart.forEach(item => {
            if (!item.id || !item.name || !item.price || !item.quantity) {
                console.error('Invalid cart item:', item);
                return;
            }
            const itemDiv = document.createElement('div');
            itemDiv.className = 'cart-item d-flex justify-content-between align-items-center';
            itemDiv.innerHTML = `
                <div>
                    <h5>${item.name}</h5>
                    <p>Price: Rs ${parseFloat(item.price).toFixed(2)}</p>
                    <p>Quantity: 
                        <button class="qty-btn" data-game-id="${item.id}" data-action="decrease">-</button> 
                        ${item.quantity} 
                        <button class="qty-btn" data-game-id="${item.id}" data-action="increase">+</button>
                    </p>
                </div>
                <button class="btn btn-danger btn-sm remove-btn" data-game-id="${item.id}">
                    <i class="fas fa-trash"></i> Remove
                </button>
            `;
            cartItemsContainer.appendChild(itemDiv);
        });

        const totalPrice = cart.reduce((total, item) => total + parseFloat(item.price) * item.quantity, 0);
        cartTotalContainer.textContent = `Total: Rs ${totalPrice.toFixed(2)}`;
    }

    document.querySelectorAll('.remove-btn').forEach(button => {
        button.addEventListener('click', () => {
            const gameId = button.getAttribute('data-game-id');
            let cart = JSON.parse(localStorage.getItem('cart')) || [];
            cart = cart.filter(item => item.id !== gameId);
            localStorage.setItem('cart', JSON.stringify(cart));
            console.log('Item removed, new cart:', cart);
            showToast('Item removed from cart!');
            location.reload();
        });
    });

    document.querySelectorAll('.qty-btn').forEach(button => {
        button.addEventListener('click', () => {
            const gameId = button.getAttribute('data-game-id');
            const action = button.getAttribute('data-action');
            let cart = JSON.parse(localStorage.getItem('cart')) || [];
            const item = cart.find(item => item.id === gameId);
            if (item) {
                if (action === 'increase') item.quantity += 1;
                if (action === 'decrease' && item.quantity > 1) item.quantity -= 1;
                if (action === 'decrease' && item.quantity === 1) cart = cart.filter(i => i.id !== gameId);
                localStorage.setItem('cart', JSON.stringify(cart));
                console.log('Quantity updated, new cart:', cart);
                showToast(`Quantity updated for ${item.name}!`);
                location.reload();
            }
        });
    });

    clearCartButton.addEventListener('click', () => {
        localStorage.removeItem('cart');
        console.log('Cart cleared');
        showToast('Cart cleared!');
        location.reload();
    });

    function updateCartBadge() {
        const cart = JSON.parse(localStorage.getItem('cart')) || [];
        const cartItemCount = cart.reduce((total, item) => total + item.quantity, 0);
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

    updateCartBadge();
});