window.onload = function() {
    // Carrega a barra de navegação
    fetch('/navbar.html')
        .then(response => response.text())
        .then(data => {
            document.getElementById('navbarContainer').innerHTML = data;

            // Após carregar a barra, inicializa as funcionalidades do dropdown
            initializeNavbar();
        })
        .catch(error => console.error('Erro ao carregar a barra de navegação:', error));
};

function initializeNavbar() {
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
            .catch(error => console.error('Erro ao buscar o usuário pelo CPF:', error));
    } else {
        console.error('CPF do usuário não encontrado');
    }

    // Adiciona evento de clique para abrir/fechar o dropdown
    document.getElementById('nomeUsuarioDropdown').addEventListener('click', function() {
        document.getElementById('dropdownContent').classList.toggle('show');
    });

    // Fecha o dropdown se o usuário clicar fora dele
    document.addEventListener("DOMContentLoaded", function() {
        const dropdownButton = document.getElementById('nomeUsuarioDropdown');
        if (dropdownButton) {
            dropdownButton.addEventListener('click', function() {
                document.getElementById('dropdownContent').classList.toggle('show');
            });
        } else {
            console.error("Elemento 'nomeUsuarioDropdown' não encontrado.");
        }
    });

}
