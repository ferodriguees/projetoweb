document.getElementById('cadastroForm').addEventListener('submit', function (event) {
    event.preventDefault(); // Previne o envio do formulário padrão

    // Coleta os dados do formulário
    const nome = document.getElementById('nome').value;
    const email = document.getElementById('email').value;
    const cpf = document.getElementById('cpf').value;

    // Envia os dados via fetch
    fetch('/usuario/cadastro', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ nome, email, cpf })
    })
        .then(response => response.text()) // Recebe a mensagem do backend
        .then(message => {
            // Exibe a mensagem de sucesso
            const mensagemDiv = document.getElementById('mensagem');
            mensagemDiv.innerText = message;
            mensagemDiv.style.display = 'block';

            // Redireciona para a página de login após 3 segundos
            setTimeout(() => {
                window.location.href = '/login';
            }, 3000);
        })
        .catch(error => {
            console.error('Erro ao cadastrar o usuário:', error);
        });
});