document.addEventListener('DOMContentLoaded', function () {
    const imprimirBtn = document.getElementById('imprimirBtn');
    const laudoTextarea = document.getElementById('laudo');

    imprimirBtn.addEventListener('click', function () {
        const laudo = laudoTextarea.value;

        if (!laudo) {
            alert('Por favor, preencha o laudo antes de imprimir.');
            return;
        }

        const janelaImpressao = window.open('', '', 'width=800,height=600');
        janelaImpressao.document.write('<html><head><title>Imprimir Laudo</title>');
        janelaImpressao.document.write('</head><body>');
        janelaImpressao.document.write('<h1>Laudo Médico</h1>');
        janelaImpressao.document.write('<pre>' + laudo + '</pre>');
        janelaImpressao.document.write('</body></html>');

        // Iniciar a impressão
        janelaImpressao.document.close();
        janelaImpressao.focus();
        janelaImpressao.print();
        janelaImpressao.close();
    });
});
