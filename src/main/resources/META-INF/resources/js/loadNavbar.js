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

    // Alterna o menu esquerdo
    document.getElementById('menuToggle').addEventListener('click', function() {
        document.getElementById('menuContent').classList.toggle('show');
    });

// Alterna o dropdown do usuário
    document.getElementById('nomeUsuarioDropdown').addEventListener('click', function() {
        document.getElementById('dropdownContent').classList.toggle('show');
    });

    document.getElementById('logoutButton').addEventListener('click', function() {
        // Limpa o localStorage (onde o token e outros dados do usuário estão armazenados)
        localStorage.removeItem('cpf'); // Remove o CPF do usuário
        localStorage.removeItem('token');   // Se estiver usando JWT, remova o token também

        // Redireciona para a página de login
        window.location.href = '/login';
    });


// Fecha o menu quando clicar fora
    window.onclick = function(event) {
        if (!event.target.matches('#menuToggle')) {
            var menuContent = document.getElementById('menuContent');
            if (menuContent.classList.contains('show')) {
                menuContent.classList.remove('show');
            }
        }

        if (!event.target.matches('.dropdown-toggle')) {
            var dropdownContent = document.getElementById('dropdownContent');
            if (dropdownContent.classList.contains('show')) {
                dropdownContent.classList.remove('show');
            }
        }
    };
};