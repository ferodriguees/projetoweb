document.addEventListener('DOMContentLoaded', function() {
    const loginButton = document.getElementById('loginButton');

    loginButton.addEventListener('click', function() {
        login();
    });
});

function login() {
    const email = document.getElementById('email').value;
    const senha = document.getElementById('senha').value;

    // Construa os dados do formulário manualmente
    const formData = new URLSearchParams();
    formData.append('email', email);
    formData.append('senha', senha);

    fetch('/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'  // Tipo de conteúdo correto
        },
        body: formData.toString()  // Converte os dados para a string correta
    })
        .then(response => response.json())
        .then(data => {
            if (data.token) {
                localStorage.setItem('jwtToken', data.token);
                console.log('Login bem-sucedido! Token armazenado.');
                window.location.href = '/telaPrincipal';
            } else {
                console.error('Login falhou.');
            }
        })
        .catch(error => console.error('Erro:', error));
}