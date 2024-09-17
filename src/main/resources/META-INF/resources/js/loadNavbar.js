document.addEventListener('DOMContentLoaded', function() {
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
        function loadUserPage() {
            // Carrega a página de usuários ao clicar no menu "Usuário"
            document.querySelector("#usuarioOption").addEventListener("click", function (event) {
                event.preventDefault(); // Evita o recarregamento completo

                fetch('/site_admin/usuario_list') // Pega o conteúdo da lista de usuários
                    .then(response => response.text())
                    .then(html => {
                        document.getElementById('conteudo').innerHTML = html; // Carrega o conteúdo no corpo da página
                        loadSearchFunctionality(); // Chama a função para carregar a pesquisa
                    })
                    .catch(error => console.error('Erro ao carregar a página de usuários:', error));
            });
        }

    // Função para carregar a página de atendimento
    function loadAtendimentoPage() {
        document.querySelector("#atendimentoOption").addEventListener("click", function(event) {
            event.preventDefault(); // Evita o recarregamento completo

            fetch('/atendimento') // Pega o conteúdo da página de atendimento
                .then(response => response.text())
                .then(html => {
                    document.getElementById('conteudo').innerHTML = html; // Carrega o conteúdo no corpo da página
                    loadAtendimentoFunctionality(); // Chama a função para carregar a funcionalidade de atendimento
                })
                .catch(error => console.error('Erro ao carregar a página de atendimento:', error));
        });
    }


        // Função para adicionar eventos ao formulário de pesquisa
        function loadSearchFunctionality() {
            const searchForm = document.getElementById('searchForm');
            searchForm.addEventListener('submit', function (event) {
                event.preventDefault(); // Evita o envio padrão

                const nome = document.getElementById('nome').value;
                const cpf = document.getElementById('cpf').value;
                const email = document.getElementById('email').value;

                // Faz a requisição de busca com base nos filtros
                fetch(`/site_admin/pesquisar?nome=${nome}&cpf=${cpf}&email=${email}`)
                    .then(response => response.json())
                    .then(data => {
                        const userList = document.getElementById('userList');
                        userList.innerHTML = ''; // Limpa a lista anterior

                        // Exibe os resultados da pesquisa
                        data.forEach(usuario => {
                            const userItem = document.createElement('div');
                            userItem.textContent = `Nome: ${usuario.nome}, CPF: ${usuario.cpf}, Email: ${usuario.email}`;
                            userList.appendChild(userItem);
                        });
                    })
                    .catch(error => console.error('Erro ao buscar usuários:', error));
            });
        }

    // Função para adicionar eventos ao formulário de atendimento
    function loadAtendimentoFunctionality() {
        const chamarProximaBtn = document.getElementById('chamarProximaBtn');
        const realizarAtendimentoBtn = document.getElementById('realizarAtendimentoBtn');
        const pacienteAusenteBtn = document.getElementById('pacienteAusenteBtn');
        const senhaChamadaElem = document.getElementById('senhaChamada');

        if (chamarProximaBtn) {
            chamarProximaBtn.addEventListener('click', function() {
                fetch('/atendimento/chamar', {
                    method: 'GET'
                })
                    .then(response => {
                        if (response.status === 204) {
                            throw new Error('Não há senhas na fila.');
                        }
                        if (!response.ok) {
                            return response.text().then(text => { throw new Error(text); });
                        }
                        return response.json();
                    })
                    .then(data => {
                        senhaChamadaElem.textContent = `Senha chamada: ${data.tipo}${String(data.numero).padStart(3, '0')}`;
                        realizarAtendimentoBtn.disabled = false;
                        pacienteAusenteBtn.disabled = false;
                    })
                    .catch(error => {
                        console.error('Erro ao chamar a próxima senha:', error);
                        senhaChamadaElem.textContent = error.message;
                        realizarAtendimentoBtn.disabled = true;
                        pacienteAusenteBtn.disabled = true;
                    });
            });
        }

        if (realizarAtendimentoBtn) {
            realizarAtendimentoBtn.addEventListener('click', function() {
                window.location.href = '/cadastro';
            });
        }

        if (pacienteAusenteBtn) {
            pacienteAusenteBtn.addEventListener('click', function() {
                alert('Paciente marcado como ausente.');
                senhaChamadaElem.textContent = '';
                realizarAtendimentoBtn.disabled = true;
                pacienteAusenteBtn.disabled = true;
            });
        }
    }

    loadUserPage();
    loadAtendimentoPage();




    // Alterna o menu esquerdo
    document.getElementById('menuToggle').addEventListener('click', function() {
        document.getElementById('menuContent').classList.toggle('show');
    });

// Alterna o dropdown do usuário
    document.getElementById('nomeUsuarioDropdown').addEventListener('click', function() {
        document.getElementById('dropdownContent').classList.toggle('show');
    });

    document.getElementById('logout').addEventListener('click', function() {
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
});
