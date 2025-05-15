document.addEventListener('DOMContentLoaded', function() {
    // Riferimenti agli elementi del DOM
    const registrationForm = document.getElementById('registrationForm');
    const loginForm = document.getElementById('loginForm');
    const loginLink = document.getElementById('loginLink');
    const registerLink = document.getElementById('registerLink');
    const successModal = document.getElementById('successModal');
    const closeModal = document.querySelector('.close');
    const continueBtn = document.querySelector('.btn-continue');
    const togglePasswordBtns = document.querySelectorAll('.toggle-password');

    // Aggiungi classe hidden al CSS se non esiste
    if (!document.querySelector('style.hidden-style')) {
        const style = document.createElement('style');
        style.className = 'hidden-style';
        style.textContent = '.hidden { display: none; }';
        document.head.appendChild(style);
    }

    // Mostra/nascondi password
    togglePasswordBtns.forEach(btn => {
        btn.addEventListener('click', function() {
            const input = this.previousElementSibling;
            if (input.type === 'password') {
                input.type = 'text';
                this.classList.remove('fa-eye');
                this.classList.add('fa-eye-slash');
            } else {
                input.type = 'password';
                this.classList.remove('fa-eye-slash');
                this.classList.add('fa-eye');
            }
        });
    });

    // Gestione cambio form (login/registrazione)
    if (loginLink) {
        loginLink.addEventListener('click', function(e) {
            e.preventDefault();
            registrationForm.classList.add('hidden');
            loginForm.classList.remove('hidden');
        });
    }

    if (registerLink) {
        registerLink.addEventListener('click', function(e) {
            e.preventDefault();
            loginForm.classList.add('hidden');
            registrationForm.classList.remove('hidden');
        });
    }

    // Gestione invio form di registrazione
    if (registrationForm) {
        registrationForm.addEventListener('submit', function(e) {
            e.preventDefault();
            
            // Mostra un indicatore di caricamento
            const submitBtn = this.querySelector('button[type="submit"]');
            const originalText = submitBtn.textContent;
            submitBtn.textContent = 'Invio in corso...';
            submitBtn.disabled = true;
            
            // Raccolta dati dal form
            const userData = {
                name: document.getElementById('name').value,
                surname: document.getElementById('surname').value,
                email: document.getElementById('email').value,
                password: document.getElementById('password').value
            };
            
            console.log('Invio dati:', userData);
            
            // Invio dati al server
            fetch('http://localhost:8080/register/send', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify(userData)
            })
            .then(response => {
                console.log('Risposta ricevuta:', response);
                if (!response.ok) {
                    throw new Error('Errore nella registrazione: ' + response.status);
                }
                return response.json();
            })
            .then(data => {
                console.log('Dati ricevuti:', data);
                // Mostra il modal di successo
                successModal.style.display = 'flex';
                // Ripristina il pulsante
                submitBtn.textContent = originalText;
                submitBtn.disabled = false;
            })
            .catch(error => {
                console.error('Errore:', error);
                alert('Si è verificato un errore durante la registrazione: ' + error.message);
                // Ripristina il pulsante
                submitBtn.textContent = originalText;
                submitBtn.disabled = false;
            });
        });
    }

    // Gestione invio form di login
    if (loginForm) {
        loginForm.addEventListener('submit', function(e) {
            e.preventDefault();
            
            // Mostra un indicatore di caricamento
            const submitBtn = this.querySelector('button[type="submit"]');
            const originalText = submitBtn.textContent;
            submitBtn.textContent = 'Accesso in corso...';
            submitBtn.disabled = true;
            
            // Raccolta dati dal form
            const loginData = {
                email: document.getElementById('loginEmail').value,
                password: document.getElementById('loginPassword').value
            };
            
            console.log('Invio dati login:', loginData);
            
            // Invio dati al server
            fetch('http://localhost:8080/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify(loginData)
            })
            .then(response => {
                console.log('Risposta login ricevuta:', response);
                if (!response.ok) {
                    throw new Error('Credenziali non valide');
                }
                return response.json();
            })
            .then(data => {
                console.log('Login riuscito:', data);
                // Reindirizzamento alla home o altra pagina dopo il login
                window.location.href = '/home.html';
            })
            .catch(error => {
                console.error('Errore login:', error);
                alert('Credenziali non valide. Riprova.');
                // Ripristina il pulsante
                submitBtn.textContent = originalText;
                submitBtn.disabled = false;
            });
        });
    }

    // Gestione chiusura modal
    if (closeModal) {
        closeModal.addEventListener('click', function() {
            successModal.style.display = 'none';
        });
    }

    // Gestione pulsante continua nel modal
    if (continueBtn) {
        continueBtn.addEventListener('click', function() {
            successModal.style.display = 'none';
            // Reindirizzamento alla home o altra pagina dopo la registrazione
            window.location.href = '/home.html';
        });
    }

    // Chiudi modal se si clicca fuori da esso
    window.addEventListener('click', function(e) {
        if (e.target === successModal) {
            successModal.style.display = 'none';
        }
    });
});