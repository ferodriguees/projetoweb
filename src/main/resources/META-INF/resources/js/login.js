function login(email, senha) {
    fetch('/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams({
            'email': email,
            'senha': senha
        })
    })
        .then(response => {
            console.log('Status da resposta:', response.status);
            if (!response.ok) {
                throw new Error('Falha ao fazer login: ' + response.status);
            }
            return response.json();
        })
        .then(data => {
            console.log('Dados recebidos do servidor:', data);
            if (data && data.token) {
                localStorage.setItem('jwtToken', data.token);
                console.log('Login bem-sucedido! Token:', data.token);
            } else {
                console.error('Login falhou: Token não encontrado na resposta.');
            }
        })
        .catch(error => {
            console.error('Erro durante o login:', error);
        });
}

// Exemplo de uso
login('usuario@exemplo.com', 'senha123');
