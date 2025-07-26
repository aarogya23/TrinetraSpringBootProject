// Wait for DOM to fully load
document.addEventListener('DOMContentLoaded', function() {
    // Load cart items from localStorage
    loadCartItems();
    
    // Update badges
    updateBadges();
});

function loadCartItems() {
    const cartItemsContainer = document.getElementById('cart-items');
    const cartSummaryContainer = document.getElementById('cart-summary');
    
    // Get cart from localStorage
    const cart = JSON.parse(localStorage.getItem('cart')) || [];
    
    // Check if cart is empty
    if (cart.length === 0) {
        cartItemsContainer.innerHTML = `
            <div class="empty-cart">
                <i class="fas fa-shopping-cart"></i>
                <h3>Your cart is empty</h3>
                <p>Looks like you haven't added any games to your cart yet.</p>
                <a href="TrinetraGamePage.html" class="continue-shopping-btn">
                    <i class="fas fa-arrow-left"></i> Continue Shopping
                </a>
            </div>
        `;
        cartSummaryContainer.style.display = 'none';
        return;
    }
    
    // Reset cart items container
    cartItemsContainer.innerHTML = '';
    
    // Initialize total price
    let subtotal = 0;
    
    // Loop through cart items and create HTML
    cart.forEach((item, index) => {
        // Extract price value (assumes format like "Rs1999")
        const priceValue = parseFloat(item.price.replace(/[^0-9]/g, ''));
        
        // Calculate item total
        const itemTotal = priceValue * item.quantity;
        
        // Add to subtotal
        subtotal += itemTotal;
        
        // Create cart item element
        const cartItemElement = document.createElement('div');
        cartItemElement.className = 'cart-item';
        cartItemElement.innerHTML = `
            <img src="${item.image}" alt="${item.name}" class="cart-item-image">
            <div class="cart-item-details">
                <div class="cart-item-title">${item.name}</div>
                <div class="cart-item-price">${item.price}</div>
                <div class="cart-item-platform">${item.platform}</div>
                <div class="quantity-control">
                    <button class="quantity-btn decrease-btn" data-index="${index}">-</button>
                    <input type="number" class="quantity-input" value="${item.quantity}" min="1" max="10" data-index="${index}">
                    <button class="quantity-btn increase-btn" data-index="${index}">+</button>
                </div>
            </div>
            <div class="cart-item-actions">
                <button class="remove-btn" data-index="${index}">
                    <i class="fas fa-trash"></i> Remove
                </button>
            </div>
        `;
        
        cartItemsContainer.appendChild(cartItemElement);
    });
    
    // Create summary
    const tax = subtotal * 0.18; // Assuming 18% GST
    const total = subtotal + tax;
    
    cartSummaryContainer.innerHTML = `
        <div class="summary-row">
            <div>Subtotal:</div>
            <div>Rs${subtotal.toFixed(2)}</div>
        </div>
        <div class="summary-row">
            <div>Tax (18% GST):</div>
            <div>Rs${tax.toFixed(2)}</div>
        </div>
        <div class="summary-row total">
            <div>Total:</div>
            <div>Rs${total.toFixed(2)}</div>
        </div>
        <button class="checkout-btn" id="checkout-btn">
            <i class="fas fa-lock"></i> Proceed to Checkout
        </button>
    `;
    
    // Add event listeners
    addEventListeners();
}

function addEventListeners() {
    // Quantity decrease buttons
    const decreaseBtns = document.querySelectorAll('.decrease-btn');
    decreaseBtns.forEach(btn => {
        btn.addEventListener('click', function() {
            const index = parseInt(this.getAttribute('data-index'));
            updateQuantity(index, -1);
        });
    });
    
    // Quantity increase buttons
    const increaseBtns = document.querySelectorAll('.increase-btn');
    increaseBtns.forEach(btn => {
        btn.addEventListener('click', function() {
            const index = parseInt(this.getAttribute('data-index'));
            updateQuantity(index, 1);
        });
    });
    
    // Quantity input fields
    const quantityInputs = document.querySelectorAll('.quantity-input');
    quantityInputs.forEach(input => {
        input.addEventListener('change', function() {
            const index = parseInt(this.getAttribute('data-index'));
            const newQuantity = parseInt(this.value);
            if (newQuantity >= 1 && newQuantity <= 10) {
                setQuantity(index, newQuantity);
            }
        });
    });
    
    // Remove buttons
    const removeBtns = document.querySelectorAll('.remove-btn');
    removeBtns.forEach(btn => {
        btn.addEventListener('click', function() {
            const index = parseInt(this.getAttribute('data-index'));
            removeItem(index);
        });
    });
    
    // Checkout button
    const checkoutBtn = document.getElementById('checkout-btn');
    if (checkoutBtn) {
        checkoutBtn.addEventListener('click', function() {
            // Show loading toast
            showToast('Proceeding to payment...');
            
            // Store cart data in localStorage to be accessed by the payment page
            const cart = JSON.parse(localStorage.getItem('cart')) || [];
            localStorage.setItem('checkoutData', JSON.stringify({
                items: cart,
                timestamp: new Date().toISOString()
            }));
            
            // Redirect to payment page after a short delay
            setTimeout(() => {
                window.location.href = 'payment.html'; // Replace with your payment page URL
            }, 1000);
        });
    }
}

function updateQuantity(index, change) {
    // Get cart from localStorage
    const cart = JSON.parse(localStorage.getItem('cart')) || [];
    
    // Make sure index is valid
    if (index >= 0 && index < cart.length) {
        // Update quantity, ensuring it's between 1 and 10
        cart[index].quantity += change;
        if (cart[index].quantity < 1) cart[index].quantity = 1;
        if (cart[index].quantity > 10) cart[index].quantity = 10;
        
        // Save updated cart
        localStorage.setItem('cart', JSON.stringify(cart));
        
        // Reload cart items
        loadCartItems();
        
        // Show toast
        showToast('Cart updated!');
    }
}

function setQuantity(index, quantity) {
    // Get cart from localStorage
    const cart = JSON.parse(localStorage.getItem('cart')) || [];
    
    // Make sure index is valid
    if (index >= 0 && index < cart.length) {
        // Set quantity
        cart[index].quantity = quantity;
        
        // Save updated cart
        localStorage.setItem('cart', JSON.stringify(cart));
        
        // Reload cart items
        loadCartItems();
        
        // Show toast
        showToast('Cart updated!');
    }
}

function removeItem(index) {
    // Get cart from localStorage
    const cart = JSON.parse(localStorage.getItem('cart')) || [];
    
    // Make sure index is valid
    if (index >= 0 && index < cart.length) {
        // Remove item
        cart.splice(index, 1);
        
        // Save updated cart
        localStorage.setItem('cart', JSON.stringify(cart));
        
        // Reload cart items
        loadCartItems();
        
        // Update badges
        updateBadges();
        
        // Show toast
        showToast('Item removed from cart!');
    }
}

// Update cart and wishlist badges
function updateBadges() {
    const cart = JSON.parse(localStorage.getItem('cart')) || [];
    const wishlist = JSON.parse(localStorage.getItem('wishlist')) || [];
    
    // Update desktop badges
    const cartBadge = document.getElementById('cart-badge');
    const wishlistBadge = document.getElementById('wishlist-badge');
    
    if (cartBadge) {
        cartBadge.textContent = cart.length > 0 ? cart.length : '';
    }
    
    if (wishlistBadge) {
        wishlistBadge.textContent = wishlist.length > 0 ? wishlist.length : '';
    }
}

// Show toast notification
function showToast(message) {
    const toastContainer = document.getElementById('toast-container');
    
    // Create toast element
    const toast = document.createElement('div');
    toast.className = 'toast';
    toast.innerHTML = `
        <div class="toast-content">
            <i class="fas fa-check-circle"></i>
            <span>${message}</span>
        </div>
    `;
    
    // Add toast to container
    toastContainer.appendChild(toast);
    
    // Show toast
    setTimeout(() => {
        toast.classList.add('show');
    }, 100);
    
    // Remove toast after 3 seconds
    setTimeout(() => {
        toast.classList.remove('show');
        setTimeout(() => {
            toastContainer.removeChild(toast);
        }, 300);
    }, 3000);
}