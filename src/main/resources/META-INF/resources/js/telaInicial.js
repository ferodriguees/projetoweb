document.addEventListener('DOMContentLoaded', function() {
    const content = document.getElementById('content');

    function loadContent(page) {
        fetch(page)
            .then(response => response.text())
            .then(data => {
                content.innerHTML = data;
                attachMenuEvents();
            })
            .catch(error => {
                content.innerHTML = "<p>Erro ao carregar a página. Tente novamente mais tarde.</p>";
                console.error("Erro:", error);
            });
    }

    function attachMenuEvents() {
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
            window.open('/home/pagina-consulta', '_blank'); // Abre em uma nova guia
        });
    }

    attachMenuEvents();
});
