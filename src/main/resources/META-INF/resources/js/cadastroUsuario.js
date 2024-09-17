document.getElementById('cadastroUsuarioForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const usuario = {
        nome: document.getElementById('nome').value,
        username: document.getElementById('username').value,
        email: document.getElementById('email').value,
        cpf: document.getElementById('cpf').value,
        perfil: document.getElementById('perfil').value
    };

    fetch('/site_admin/cadastrarUsuario', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(usuario)
    })
        .then(response => {
            if (response.ok) {
                alert('Usuário cadastrado com sucesso!');
            } else {
                alert('Erro ao cadastrar usuário');
            }
        })
        .catch(error => {
            console.error('Erro:', error);
        });
});
