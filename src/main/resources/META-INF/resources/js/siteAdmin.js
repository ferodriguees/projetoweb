window.onload = function() {
    // Obtém o CPF do usuário logado (armazenado no localStorage ou de outro lugar)
    const cpf = localStorage.getItem('cpf');

    if (cpf) {
        // Faz a requisição para obter o nome do usuário pelo CPF
        fetch(`/usuario/buscar/${cpf}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`,
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(data => {
                if (data && data.nome) {
                    // Substitui o texto do dropdown pelo nome do usuário
                    document.getElementById('nomeUsuarioDropdown').textContent = data.nome;
                } else {
                    console.error('Usuário não encontrado');
                }
            })
            .catch(error => {
                console.error('Erro ao buscar o usuário pelo CPF:', error);
            });
    } else {
        console.error('CPF do usuário não encontrado');
    }

    // Adiciona evento de clique para abrir/fechar o dropdown
    document.getElementById('nomeUsuarioDropdown').addEventListener('click', function() {
        document.getElementById('dropdownContent').classList.toggle('show');
    });

    // Fecha o dropdown se o usuário clicar fora dele
    window.onclick = function(event) {
        if (!event.target.matches('.dropdown-toggle')) {
            var dropdowns = document.getElementsByClassName('dropdown-content');
            for (var i = 0; i < dropdowns.length; i++) {
                var openDropdown = dropdowns[i];
                if (openDropdown.classList.contains('show')) {
                    openDropdown.classList.remove('show');
                }
            }
        }
    };
};