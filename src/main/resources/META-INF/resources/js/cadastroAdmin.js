document.getElementById('cadastroForm').addEventListener('submit', function (event) {
    event.preventDefault();

    const nome = document.getElementById('nome').value;
    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const cpf = document.getElementById('cpf').value;

    fetch('/usuario/cadastroAdmin', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ nome, username, email, cpf })
    })
        .then(response => response.text())
        .then(message => {
            const mensagemDiv = document.getElementById('mensagem');
            mensagemDiv.innerText = message;
            mensagemDiv.style.display = 'block';

            // Redireciona para a página de login após 3 segundos
            setTimeout(() => {
                //window.location.href = '/login';
            }, 3000);
        })
        .catch(error => {
            console.error('Erro ao cadastrar o usuário:', error);
        });
});