document.addEventListener('DOMContentLoaded', function() {
    const welcomeMessageElem = document.getElementById('welcomeMessage');

    function getJwtToken() {
        return localStorage.getItem('token');
    }

    function getNomeUsuarioFromToken() {
        const token = getJwtToken();
        if (token) {
            // Decodifica o payload do JWT
            const payload = JSON.parse(atob(token.split('.')[1]));
            return payload.nome || 'Usuário';
        }
        return null;
    }

    function exibirMensagemDeBoasVindas() {
        const nomeUsuario = getNomeUsuarioFromToken();
        if (nomeUsuario) {
            const mensagem = `Bem-vindo(a), 
<strong>${nomeUsuario}</strong>! <br> Use o menu lateral para interagir com o sistema.`;
            welcomeMessageElem.innerHTML = mensagem;
        } else {
            welcomeMessageElem.innerHTML = 'Erro ao obter o nome do usuário.';
        }
    }

    exibirMensagemDeBoasVindas();
});
