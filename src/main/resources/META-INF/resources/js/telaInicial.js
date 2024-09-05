document.addEventListener('DOMContentLoaded', function () {
    const links = document.querySelectorAll('.menu a');

    links.forEach(link => {
        link.addEventListener('click', function (event) {
            // Aqui você pode adicionar lógica extra antes de redirecionar, se necessário.
            console.log(`Opção selecionada: ${link.textContent}`);
        });
    });
});
