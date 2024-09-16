window.onload = function () {
    const form = document.getElementById('searchForm');

    // Função para buscar usuários com base nos campos de pesquisa
    function searchUsers(event) {
        event.preventDefault(); // Evita o envio do formulário tradicional

        const nome = document.getElementById('nome').value;
        const cpf = document.getElementById('cpf').value;
        const email = document.getElementById('email').value;

        // Faz uma requisição à API (ou endpoint) que retorna os usuários filtrados
        fetch(`/site_admin/pesquisar?nome=${nome}&cpf=${cpf}&email=${email}`)
            .then(response => response.json())
            .then(data => {
                if (data.length > 0) {
                    // Limpa a lista de usuários existente
                    const userList = document.getElementById('userList');
                    userList.innerHTML = '';

                    // Popula a lista com os usuários retornados
                    data.forEach(user => {
                        const listItem = document.createElement('li');
                        listItem.textContent = `Nome: ${user.nome}, CPF: ${user.cpf}, Email: ${user.email}`;
                        userList.appendChild(listItem);
                    });
                } else {
                    console.error('Nenhum usuário encontrado');
                    document.getElementById('userList').innerHTML = 'Nenhum usuário encontrado.';
                }
            })
            .catch(error => console.error('Erro ao buscar usuários:', error));
    }

    // Adiciona evento ao formulário de pesquisa
    if (form) {
        form.addEventListener('submit', searchUsers);
    }
};
