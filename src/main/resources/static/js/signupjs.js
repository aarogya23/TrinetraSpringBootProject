// Theme Controller
  class ThemeController {
      constructor() {
          this.themeToggle = document.getElementById('theme-toggle');
          this.themeIcon = document.getElementById('theme-icon');
          this.logoImage = document.getElementById('logo-image');
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
              this.logoImage.src = '/image/white.png';
              this.logoImage.style.width = '240px';     // 👈 Custom size for white logo
      this.logoImage.style.height = '115px';
          } else {
              this.themeIcon.className = 'fas fa-sun';
              this.themeIcon.style.color = '#ffa500';
              this.logoImage.src = '/image/black.png';
                      this.logoImage.style.width = '200px';     // 👈 Custom size for dark logo
      		this.logoImage.style.height = '114px';
          }
          localStorage.setItem('theme', theme);
      }
  }

  // Password Toggle
  class PasswordToggle {
      constructor() {
          this.setupToggles();
      }

      setupToggles() {
          document.querySelectorAll('.password-toggle').forEach(toggle => {
              toggle.addEventListener('click', (e) => {
                  const input = e.target.closest('.password-field').querySelector('input');
                  const icon = toggle.querySelector('i');

                  if (input.type === 'password') {
                      input.type = 'text';
                      icon.className = 'fas fa-eye-slash';
                  } else {
                      input.type = 'password';
                      icon.className = 'fas fa-eye';
                  }
              });
          });
      }
  }

  // Password Strength
  class PasswordStrength {
      constructor() {
          this.passwordInput = document.getElementById('password');
          this.strengthFill = document.querySelector('.strength-fill');
          this.strengthLabel = document.getElementById('strength-label');
          this.strengthContainer = document.getElementById('password-strength');

          this.passwordInput.addEventListener('input', () => this.evaluate());
      }

      evaluate() {
          const password = this.passwordInput.value;
          const score = this.getScore(password);
          const { level, text } = this.getStrengthLevel(score);

          this.strengthContainer.className = `password-strength strength-${level}`;
          this.strengthLabel.textContent = text;
      }

      getScore(password) {
          let score = 0;
          if (password.length >= 8) score += 25;
          if (password.length >= 12) score += 25;
          if (/[a-z]/.test(password)) score += 10;
          if (/[A-Z]/.test(password)) score += 15;
          if (/[0-9]/.test(password)) score += 10;
          if (/[^A-Za-z0-9]/.test(password)) score += 15;
          return Math.min(score, 100);
      }

      getStrengthLevel(score) {
          if (score < 30) return { level: 'weak', text: 'Weak' };
          if (score < 50) return { level: 'medium', text: 'Medium' };
          if (score < 80) return { level: 'strong', text: 'Strong' };
          return { level: 'very-strong', text: 'Very Strong' };
      }
  }

  // Form Validator & Submission
  class FormValidator {
      constructor() {
          this.form = document.getElementById('signup-form');
          this.setupEvents();
      }

      setupEvents() {
          this.form.addEventListener('submit', (e) => this.handleSubmit(e));

          this.form.querySelectorAll('input, select').forEach(input => {
              input.addEventListener('blur', () => this.validateField(input));
              input.addEventListener('input', () => this.clearError(input));
          });
      }

      handleSubmit(e) {
          e.preventDefault();

          if (this.validateForm()) {
              this.submitForm();
          }
      }

      validateForm() {
          let isValid = true;
          const requiredInputs = this.form.querySelectorAll('input[required], select[required]');

          requiredInputs.forEach(input => {
              if (!this.validateField(input)) isValid = false;
          });

          const password = document.getElementById('password').value;
          const confirmPassword = document.getElementById('confirm-password').value;

          if (password !== confirmPassword) {
              this.showError(document.getElementById('confirm-password'), 'Passwords do not match');
              isValid = false;
          }

          return isValid;
      }

      validateField(input) {
          const value = input.value.trim();
          let error = '';

          if (input.hasAttribute('required') && !value) {
              error = 'This field is required';
          }

          if (input.type === 'email' && value) {
              const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
              if (!emailRegex.test(value)) error = 'Enter a valid email address';
          }

          if (input.name === 'username' && value) {
              if (value.length < 3) error = 'Minimum 3 characters required';
              else if (!/^[a-zA-Z0-9_]+$/.test(value)) error = 'Only letters, numbers & underscores';
          }

          if (input.name === 'password' && value.length < 8) {
              error = 'Password must be at least 8 characters';
          }

          if (error) {
              this.showError(input, error);
              return false;
          }

          this.clearError(input);
          return true;
      }

      showError(input, message) {
          input.classList.add('is-invalid');
          const feedback = input.closest('.form-group').querySelector('.invalid-feedback');
          if (feedback) feedback.textContent = message;
      }

      clearError(input) {
          input.classList.remove('is-invalid');
          const feedback = input.closest('.form-group').querySelector('.invalid-feedback');
          if (feedback) feedback.textContent = '';
      }

      async submitForm() {
          const submitBtn = document.getElementById('signup-btn');
          const btnText = document.getElementById('btn-text');
          const btnLoading = document.getElementById('btn-loading');

          submitBtn.disabled = true;
          btnText.style.display = 'none';
          btnLoading.style.display = 'inline-block';

          try {
              await new Promise((res, rej) => {
                  setTimeout(() => (Math.random() > 0.2 ? res() : rej()), 2000);
              });

              this.showAlert('Account created successfully! Please verify your email.', 'success');
              this.form.reset();
          } catch {
              this.showAlert('Something went wrong. Try again.', 'error');
          } finally {
              submitBtn.disabled = false;
              btnText.style.display = 'inline';
              btnLoading.style.display = 'none';
          }
      }

      showAlert(message, type) {
          const alertContainer = document.getElementById('alert-container');
          const alertClass = type === 'success' ? 'alert-success' : 'alert-danger';

          const alert = document.createElement('div');
          alert.className = `alert ${alertClass}`;
          alert.textContent = message;

          alertContainer.innerHTML = '';
          alertContainer.appendChild(alert);

          setTimeout(() => alert.remove(), 5000);
      }
  }

  // Initialize all components on page load
  document.addEventListener('DOMContentLoaded', () => {
      new ThemeController();
      new PasswordToggle();
      new PasswordStrength();
      new FormValidator();
  });

  // Social logins (placeholder actions)
  document.getElementById('google-login').addEventListener('click', (e) => {
      e.preventDefault();
      console.log('Google login clicked');
      // window.location.href = '/auth/google';
  });

  document.getElementById('discord-login').addEventListener('click', (e) => {
      e.preventDefault();
      console.log('GitHub login clicked');
      // window.location.href = '/auth/github';
  });