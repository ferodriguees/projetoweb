document.addEventListener('DOMContentLoaded', function () {
    fetch(`/usuario/me`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
            'Content-Type': 'application/json',
        }
    })
        .then(response => response.json())
        .then(data => {
            if (data) {
                document.getElementById('nome').value = data.nome;
                document.getElementById('perfil').value = data.perfil;
                document.getElementById('username').value = data.username;
                document.getElementById('cpf').value = data.cpf;
                document.getElementById('email').value = data.email;
            } else {
                alert("Usuário não encontrado.");
            }
        })
        .catch(error => {
            console.error('Erro ao buscar os dados do usuário:', error);
            alert("Ocorreu um erro ao buscar os dados. Tente novamente mais tarde.");
        });

    document.getElementById('contaForm').addEventListener('submit', function (event) {
        event.preventDefault();

        const nomeField = document.getElementById('nome');
        const perfilField = document.getElementById('perfil');
        const senhaField = document.getElementById('senha');
        const repetirSenhaField = document.getElementById('repetirSenha');

        if (!nomeField || !senhaField || !repetirSenhaField) {
            console.error("Um ou mais campos do formulário não foram encontrados.");
            return;
        }

        const nome = nomeField.value;
        const perfil = perfilField.value;
        const senha = senhaField.value;
        const repetirSenha = repetirSenhaField.value;

        if (senha !== repetirSenha) {
            alert("As senhas não coincidem. Por favor, tente novamente.");
            return;
        }

        const usuarioAtualizado = {
            nome: nome,
            perfil: perfil,
            senha: senha || null
        };

        fetch(`/site_admin/atualizar`, {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(usuarioAtualizado)
        })
            .then(response => {
                if (response.status === 403) {
                    alert("Você não tem autorização para acessar essa funcionalidade. Entre em contato com o Administrador.");
                    return;
                } else if (response.status === 401) {
                    alert("Você precisa estar logado para acessar essa funcionalidade.");
                    window.location.href = '/login';
                    return;
                }
                return response.text();
            })
            .then(message => {
                if (message) {
                    alert(message);
                    localStorage.removeItem('token');
                    window.location.href = '/login';
                }
            })
            .catch(error => {
                console.error('Erro ao atualizar os dados do usuário:', error);
            });
    });
});