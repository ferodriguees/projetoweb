document.addEventListener('DOMContentLoaded', function() {
    const content = document.getElementById('content');

    // Função para carregar conteúdo dinamicamente
    function loadContent(page) {
        fetch(page)
            .then(response => response.text())
            .then(data => {
                content.innerHTML = data;
            })
            .catch(error => {
                content.innerHTML = "<p>Erro ao carregar a página. Tente novamente mais tarde.</p>";
                console.error("Erro:", error);
            });
    }

    // Adiciona eventos para os links
    document.getElementById('nossaClinica').addEventListener('click', function(e) {
        e.preventDefault();
        loadContent('/home/nossa-clinica');
    });

    document.getElementById('corpoClinico').addEventListener('click', function(e) {
        e.preventDefault();
        loadContent('/home/corpo-clinico');
    });

    document.getElementById('convenios').addEventListener('click', function(e) {
        e.preventDefault();
        loadContent('/home/convenios');
    });

    document.getElementById('agendarConsulta').addEventListener('click', function(e) {
        e.preventDefault();
        loadContent('/home/agendar-consulta');
    });
});
