document.addEventListener('DOMContentLoaded', function () {
    // Obtém o CPF do localStorage (se estiver armazenado) ou pede ao usuário
    const cpf = localStorage.getItem('cpf');
    if (!cpf) {
        alert("CPF não encontrado. Por favor, faça o login novamente.");
        window.location.href = '/login';
        return;
    }

    // Faz a requisição para obter os dados do usuário pelo CPF
    fetch(`/usuario/buscar/${cpf}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
            'Content-Type': 'application/json',
        }
    })
        .then(response => response.json())
        .then(data => {
            if (data) {
                // Preenche os campos do formulário com os dados do usuário
                document.getElementById('nome').value = data.nome;
                document.getElementById('username').value = data.username;
                document.getElementById('cpf').value = data.cpf; // CPF é fixo, mas pode exibir
                document.getElementById('email').value = data.email;
            } else {
                alert("Usuário não encontrado.");
            }
        })
        .catch(error => {
            console.error('Erro ao buscar os dados do usuário:', error);
            alert("Ocorreu um erro ao buscar os dados. Tente novamente mais tarde.");
        });

    // Lidar com a atualização dos dados do usuário
    document.getElementById('contaForm').addEventListener('submit', function (event) {
        event.preventDefault();

        // Verifica se todos os campos do formulário existem
        const nomeField = document.getElementById('nome');
        const senhaField = document.getElementById('senha');
        const repetirSenhaField = document.getElementById('repetirSenha');

        // Certifica-se de que os campos foram corretamente encontrados no DOM
        if (!nomeField || !senhaField || !repetirSenhaField) {
            console.error("Um ou mais campos do formulário não foram encontrados.");
            return; // Impede o envio do formulário se algum campo não for encontrado
        }

        // Obtém os valores dos campos
        const nome = nomeField.value;
        const senha = senhaField.value;
        const repetirSenha = repetirSenhaField.value;

        // Verifica se as senhas coincidem
        if (senha !== repetirSenha) {
            alert("As senhas não coincidem. Por favor, tente novamente.");
            return; // Impede o envio do formulário
        }

        // Cria o objeto de dados do usuário, incluindo a senha (se fornecida)
        const usuarioAtualizado = {
            nome: nome,
            senha: senha || null // Se o campo senha estiver vazio, o backend pode ignorar a alteração
        };

        // Faz a requisição PUT para atualizar o usuário
        fetch(`/site_admin/atualizar/${document.getElementById('cpf').value}`, {
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
                } else if (response.status === 401) {
                    alert("Você precisa estar logado para acessar essa funcionalidade.");
                }
                return response.text();
            })
            .then(data => {
                alert("Usuário atualizado com sucesso!");
                // Aqui você pode redirecionar ou recarregar a página, se necessário

            })
            .catch(error => {
                console.error('Erro ao atualizar os dados do usuário:', error);
            });
    });
});
