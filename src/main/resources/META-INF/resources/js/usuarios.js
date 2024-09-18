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
                if (data.length > 0) {

                    const userList = document.getElementById('userList');
                    userList.innerHTML = '';

                    data.forEach(user => {
                        const listItem = document.createElement('li');
                        listItem.textContent = `Nome: ${user.nome}, CPF: ${user.cpf}, Email: ${user.email}`;
                        userList.appendChild(listItem);
                    });
                } else {
                    document.getElementById('userList').innerHTML = 'Nenhum usuário encontrado.';
                }
            })
            .catch(error => console.error('Erro ao buscar usuários:', error));

    }

    if (form) {
        form.addEventListener('submit', searchUsers);
    }

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
