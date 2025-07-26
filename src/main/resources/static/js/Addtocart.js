// Wait for DOM to fully load
document.addEventListener('DOMContentLoaded', function() {
    // Get cart button
    const addToCartBtn = document.getElementById('add-to-cart-btn');
    const buyNowBtn = document.getElementById('buy-now-btn');
    const wishlistBtn = document.getElementById('wishlist-btn');
    
    // Initialize local storage arrays if they don't exist
    if (!localStorage.getItem('cart')) {
        localStorage.setItem('cart', JSON.stringify([]));
    }
    
    if (!localStorage.getItem('wishlist')) {
        localStorage.setItem('wishlist', JSON.stringify([]));
    }
    
    // Update cart and wishlist badges
    updateBadges();
    
    // Add to Cart functionality
    if (addToCartBtn) {
        addToCartBtn.addEventListener('click', function() {
            // Get current product details
            const product = {
                id: generateUniqueId(),
                name: document.querySelector('.product-info h1').textContent,
                price: document.querySelector('.current-price').textContent,
                image: document.getElementById('main-product-image').src,
                platform: document.querySelector('.additional-info p:nth-child(2)').textContent.replace('Platform:', '').trim(),
                quantity: 1
            };
            
            // Get current cart
            let cart = JSON.parse(localStorage.getItem('cart')) || [];
            
            // Check if product already exists in cart
            const existingProductIndex = cart.findIndex(item => item.name === product.name);
            
            if (existingProductIndex > -1) {
                // If product exists, increase quantity
                cart[existingProductIndex].quantity += 1;
                showToast('Product quantity updated in cart!');
            } else {
                // If product doesn't exist, add it
                cart.push(product);
                showToast('Product added to cart!');
            }
            
            // Save updated cart to localStorage
            localStorage.setItem('cart', JSON.stringify(cart));
            
            // Update badges
            updateBadges();
        });
    }
    
    // Buy Now functionality
    if (buyNowBtn) {
        buyNowBtn.addEventListener('click', function() {
            // Get current product details
            const product = {
                id: generateUniqueId(),
                name: document.querySelector('.product-info h1').textContent,
                price: document.querySelector('.current-price').textContent,
                image: document.getElementById('main-product-image').src,
                platform: document.querySelector('.additional-info p:nth-child(2)').textContent.replace('Platform:', '').trim(),
                quantity: 1
            };
            
            // Get current cart and clear it
            let cart = [];
            
            // Add only this product
            cart.push(product);
            
            // Save updated cart to localStorage
            localStorage.setItem('cart', JSON.stringify(cart));
            
            // Update badges
            updateBadges();
            
            // Redirect to cart page
            window.location.href = 'cart';
        });
    }
    
    // Wishlist functionality
    if (wishlistBtn) {
        wishlistBtn.addEventListener('click', function() {
            // Toggle wishlist button icon
            const wishlistIcon = wishlistBtn.querySelector('i');
            const isInWishlist = wishlistIcon.classList.contains('fas');
            
            // Get current product details
            const product = {
                id: generateUniqueId(),
                name: document.querySelector('.product-info h1').textContent,
                price: document.querySelector('.current-price').textContent,
                image: document.getElementById('main-product-image').src,
                platform: document.querySelector('.additional-info p:nth-child(2)').textContent.replace('Platform:', '').trim()
            };
            
            // Get current wishlist
            let wishlist = JSON.parse(localStorage.getItem('wishlist')) || [];
            
            if (isInWishlist) {
                // Remove from wishlist
                wishlist = wishlist.filter(item => item.name !== product.name);
                wishlistIcon.classList.remove('fas');
                wishlistIcon.classList.add('far');
                showToast('Product removed from wishlist!');
            } else {
                // Check if product already exists in wishlist
                const existingProductIndex = wishlist.findIndex(item => item.name === product.name);
                
                if (existingProductIndex === -1) {
                    // If product doesn't exist, add it
                    wishlist.push(product);
                    wishlistIcon.classList.remove('far');
                    wishlistIcon.classList.add('fas');
                    showToast('Product added to wishlist!');
                }
            }
            
            // Save updated wishlist to localStorage
            localStorage.setItem('wishlist', JSON.stringify(wishlist));
            
            // Update badges
            updateBadges();
        });
    }
    
    // Check if current product is in wishlist on page load
    checkWishlistStatus();
});

// Generate a unique ID for products
function generateUniqueId() {
    return Date.now().toString(36) + Math.random().toString(36).substr(2);
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
    
    // Update mobile badges
    const mobileCartBadge = document.getElementById('mobile-cart-badge');
    const mobileWishlistBadge = document.getElementById('mobile-wishlist-badge');
    
    if (mobileCartBadge) {
        mobileCartBadge.textContent = cart.length > 0 ? cart.length : '';
    }
    
    if (mobileWishlistBadge) {
        mobileWishlistBadge.textContent = wishlist.length > 0 ? wishlist.length : '';
    }
}

// Check if current product is in wishlist and update icon
function checkWishlistStatus() {
    const wishlistBtn = document.getElementById('wishlist-btn');
    if (!wishlistBtn) return;
    
    const wishlistIcon = wishlistBtn.querySelector('i');
    if (!wishlistIcon) return;
    
    const productName = document.querySelector('.product-info h1')?.textContent;
    if (!productName) return;
    
    const wishlist = JSON.parse(localStorage.getItem('wishlist')) || [];
    const isInWishlist = wishlist.some(item => item.name === productName);
    
    if (isInWishlist) {
        wishlistIcon.classList.remove('far');
        wishlistIcon.classList.add('fas');
    } else {
        wishlistIcon.classList.remove('fas');
        wishlistIcon.classList.add('far');
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