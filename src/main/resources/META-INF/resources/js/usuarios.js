window.onload = function () {
    const form = document.getElementById('searchForm');

    function searchUsers(event) {
        event.preventDefault();

        const nome = document.getElementById('nome').value;
        const cpf = document.getElementById('cpf').value;
        const email = document.getElementById('email').value;

        fetch(`/site_admin/pesquisar?nome=${nome}&cpf=${cpf}&email=${email}`)
            .then(response => {
                if (!response.ok) {
                    return response.text().then(errorMessage => {
                        document.getElementById('userList').innerHTML = errorMessage;
                        throw new Error(errorMessage);
                    });
                }
                return response.json();
            })
            .then(data => {
                updateUserList(data);
            })
            .catch(error => console.error('Erro ao buscar usuários:', error));
    }

    function updateUserList(users) {
        const userList = document.getElementById('userList');
        userList.innerHTML = '';

        if (users.length > 0) {
            users.forEach(user => {
                const listItem = document.createElement('li');
                listItem.className = 'user-list-item';
                listItem.innerHTML = `
                    Nome: ${user.nome}, CPF: ${user.cpf}, Email: ${user.email}
                    <img src="/img/lixeira.png" alt="Deletar usuário" class="delete-icon" data-user-id="${user.id}" />
                `;

                userList.appendChild(listItem);
            });

            document.querySelectorAll('.delete-icon').forEach(icon => {
                icon.addEventListener('click', function() {
                    if (confirm("Você tem certeza que deseja deletar o usuário? Essa alteração não poderá ser desfeita.")) {
                        deleteUser(this.dataset.userId);
                    }
                });
            });

        } else {
            userList.innerHTML = 'Nenhum usuário encontrado.';
        }
    }

    function deleteUser(userId) {
        const token = localStorage.getItem('token');

        fetch(`/usuario/delete/${userId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (response.ok) {
                    loadAllUsers();
                } else {
                    console.error('Erro ao deletar usuário:', response.statusText);
                }
            })
            .catch(error => console.error('Erro ao deletar usuário:', error));
    }

    function loadAllUsers() {
        fetch('/usuario/all')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro ao carregar os usuários.');
                }
                return response.json();
            })
            .then(users => {
                updateUserList(users);
            })
            .catch(error => console.error('Erro ao carregar usuários:', error));
    }

    if (form) {
        form.addEventListener('submit', searchUsers);
    }

    loadAllUsers();

    function loadCadastroUsuarioPage() {
        const novoUsuarioBtn = document.getElementById('novoUsuarioBtn');
        if (novoUsuarioBtn) {
            novoUsuarioBtn.addEventListener('click', function(event) {
                event.preventDefault();
                window.location.href = '/site_admin/cadastrarUsuarioPage';
            });
        } else {
            console.error('Botão "Novo Usuário" não encontrado.');
        }
    }

    loadCadastroUsuarioPage();
};
