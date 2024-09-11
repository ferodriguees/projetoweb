document.getElementById('loginForm').addEventListener('submit', function (event) {
    event.preventDefault();

    fetch('/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams({
            email: document.getElementById('email').value,
            senha: document.getElementById('senha').value
        })
    })
        .then(response => response.json())
        .then(data => {
            if (data.token) {
                // Armazena o token JWT no localStorage
                localStorage.setItem('token', data.token);

                // Armazena o CPF no localStorage ou sessionStorage, se necessário
                localStorage.setItem('cpf', data.cpf);

                // Redireciona para a página principal ou outra página apropriada
                window.location.href = '/site_admin';
            } else {
                alert('Login falhou! Verifique seu email e senha.');
            }
        })
        .catch(error => {
            console.error('Erro ao fazer login:', error);
            alert('Ocorreu um erro ao fazer login. Tente novamente mais tarde.');
        });
});
