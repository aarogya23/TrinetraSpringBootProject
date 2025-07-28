class ThemeController {
    constructor() {
        this.themeToggle = document.getElementById('theme-toggle');
        this.themeIcon = document.getElementById('theme-icon');
        this.themeText = document.getElementById('theme-text');
        this.logo = document.querySelector('.logo img'); // logo img inside .logo div
        this.body = document.body;

        this.initializeTheme();
        this.themeToggle.addEventListener('click', () => this.toggleTheme());
    }

    initializeTheme() {
        const savedTheme = localStorage.getItem('theme') || 'light';
        this.setTheme(savedTheme);
    }

    toggleTheme() {
        const currentTheme = this.body.getAttribute('data-theme');
        const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
        this.setTheme(newTheme);
    }

    setTheme(theme) {
        this.body.setAttribute('data-theme', theme);

        if (theme === 'dark') {
            this.themeIcon.className = 'fas fa-moon';
            this.themeIcon.style.color = '#ffffff';
            this.themeToggle.style.backgroundColor = '#111';
            this.themeToggle.style.borderColor = '#555';
            this.themeText.style.visibility = 'hidden'; // keep space, hide text
            this.themeToggle.appendChild(this.themeIcon); // icon after text
        } else {
            this.themeIcon.className = 'fas fa-sun';
            this.themeIcon.style.color = 'orange';
            this.themeToggle.style.backgroundColor = '#ffffff';
            this.themeToggle.style.borderColor = '#ffffff';
            this.themeText.style.visibility = 'hidden'; // keep space, hide text
            this.themeToggle.insertBefore(this.themeIcon, this.themeText); // icon before text
        }

        this.updateLogo(theme);
        this.loadThemeCSS(theme);
        localStorage.setItem('theme', theme);
    }

    updateLogo(theme) {
        if (!this.logo) {
            console.warn('Logo element not found.');
            return;
        }

        // Fade out logo
        this.logo.style.opacity = '0';

        // Set new logo after fade transition
        setTimeout(() => {
            const logoSrc = theme === 'dark' ? '/image/white.png' : '/image/black.png';
            const logoAlt = theme === 'dark' ? 'Trinetra Games Dark Mode' : 'Trinetra Games';

            // Update attributes
            this.logo.src = logoSrc;
            this.logo.alt = logoAlt;
            this.logo.style.width = '170px';

            // Optionally fade it back in
            this.logo.onload = () => {
                this.logo.style.opacity = '1';
                console.log(`Logo updated for ${theme} theme`);
            };

            this.logo.onerror = () => {
                console.error(`Failed to load logo for ${theme} theme`);
            };
        }, 100); // matches CSS transition time
    }

    loadThemeCSS(theme) {
        // Remove existing theme stylesheets
        const existingLinks = document.querySelectorAll('link[data-theme-css]');
        existingLinks.forEach(link => link.remove());

        // Define theme-specific CSS files
        const cssFiles = theme === 'dark'
            ? ['/css/Index.css','/css/darkthemepayment.css']
            : ['/css/lightmode.css','/css/lightthemepayment.css'];

        // Load all CSS files for the theme
        cssFiles.forEach((href) => {
            const link = document.createElement('link');
            link.rel = 'stylesheet';
            link.href = href;
            link.setAttribute('data-theme-css', 'true');
            document.head.appendChild(link);

            link.onload = () => console.log(`${href} loaded`);
            link.onerror = () => console.error(`Failed to load ${href}`);
        });
    }
}

// Inject smooth transitions CSS
const addThemeTransition = () => {
    const style = document.createElement('style');
    style.textContent = `
        * {
            transition: background-color 0.3s ease, color 0.3s ease, border-color 0.3s ease !important;
        }
        .theme-toggle {
            transition: all 0.3s ease !important;
        }
        .card, .header, footer, .nav-link, input, .logo img {
            transition: background-color 0.3s ease, color 0.3s ease, border-color 0.3s ease, box-shadow 0.3s ease !important;
        }
        .logo img {
            transition: opacity 0.3s ease;
        }
    `;
    document.head.appendChild(style);
};

// Initialize after DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    new ThemeController();
    addThemeTransition();
});
