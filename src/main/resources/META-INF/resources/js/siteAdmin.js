// Função para enviar o token JWT no cabeçalho de autorização
document.addEventListener('DOMContentLoaded', function() {
    function carregarSiteAdmin() {
    const token = localStorage.getItem('token'); // Pega o token do localStorage

    if (token) {
        fetch('/site_admin', {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + token // Adiciona o token no cabeçalho
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro ao carregar conteúdo');
                }
                return response.text();
            })
            .then(html => {
                //document.getElementById('conteudo').innerHTML = html;
            })
            .catch(error => console.error('Erro:', error));
    } else {
        console.log('Token não encontrado');
    }
}
carregarSiteAdmin();
});
