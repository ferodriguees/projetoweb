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

    // Função para carregar a página de cadastro de novo usuário
    function loadCadastroUsuarioPage() {
        const novoUsuarioBtn = document.getElementById('novoUsuarioBtn');
        if (novoUsuarioBtn) {
            novoUsuarioBtn.addEventListener('click', function(event) {
                event.preventDefault(); // Evita o comportamento padrão do botão

                // Fazer a requisição para carregar a página de cadastro
                fetch('/site_admin/cadastrarUsuarioPage')
                    .then(response => response.text())
                    .then(html => {
                        // Insere o conteúdo HTML da página de cadastro no corpo da página
                        document.getElementById('conteudo').innerHTML = html;

                        // Caso exista um JS específico para a página de cadastro, ele será carregado
                        const script = document.createElement('script');
                        script.src = '/js/cadastroUsuario.js'; // JS para a página de cadastro
                        document.body.appendChild(script);
                    })
                    .catch(error => console.error('Erro ao carregar a página de cadastro:', error));
            });
        } else {
            console.error('Botão "Novo Usuário" não encontrado.');
        }
    }

    // Executa a função de carregar a página de cadastro após a página estar carregada
    loadCadastroUsuarioPage();
};
