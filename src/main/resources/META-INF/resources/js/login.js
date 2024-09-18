document.getElementById('loginForm').addEventListener('submit', function (event) {
    event.preventDefault();

    fetch('/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams({
            username: document.getElementById('username').value,
            senha: document.getElementById('senha').value
        })
    })
        .then(response => response.json())
        .then(data => {
            if (data.token) {
                localStorage.setItem('token', data.token);

                carregarSiteAdminComToken(data.token);
            } else {
                alert('Login falhou! Verifique seu nome de usuário e senha.');
            }
        })
        .catch(error => {
            console.error('Erro ao fazer login:', error);
            alert('Ocorreu um erro ao fazer login. Tente novamente mais tarde.');
        });
});

function carregarSiteAdminComToken(token) {
    fetch('/site_admin', {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao carregar conteúdo');
            }
            return response.text();
        })
        .then(html => {
            window.history.pushState({}, '', '/site_admin');

            document.open();
            document.write(html);
            document.close();
        })
        .catch(error => {
            console.error('Erro ao carregar a página do site admin:', error);
        });
}
