document.addEventListener('DOMContentLoaded', function() {
    // Função para carregar o nome do usuário e atualizar o dropdown
    function carregarNomeUsuario() {
        const token = localStorage.getItem('token');

        if (token) {
            fetch('/usuario/me', {
                method: 'GET',
                headers: {
                    'Authorization': 'Bearer ' + token,
                    'Content-Type': 'application/json'
                }
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Erro ao buscar nome do usuário');
                    }
                    return response.json();
                })
                .then(data => {
                    if (data && data.nome) {
                        document.getElementById('nomeUsuarioDropdown').textContent = data.nome;
                    } else {
                        console.error('Nome do usuário não encontrado');
                    }
                })
                .catch(error => {
                    console.error('Erro ao buscar nome do usuário:', error);
                });
        } else {
            console.error('Token não encontrado');
        }
    }

    carregarNomeUsuario();

    document.getElementById('menuToggle').addEventListener('click', function() {
        document.getElementById('menuContent').classList.toggle('show');
    });

    document.getElementById('nomeUsuarioDropdown').addEventListener('click', function() {
        document.getElementById('dropdownContent').classList.toggle('show');
    });

    document.getElementById('logout').addEventListener('click', function() {
        localStorage.removeItem('cpf');
        localStorage.removeItem('token');

        window.location.href = '/login';
    });


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
});
