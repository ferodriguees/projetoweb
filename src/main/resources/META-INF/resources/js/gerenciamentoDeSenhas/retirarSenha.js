// retirarSenha.js

document.addEventListener('DOMContentLoaded', function () {
    const retirarSenhaBtn = document.getElementById('retirarSenhaBtn');
    const senhaGeradaElem = document.getElementById('senhaGerada');

    retirarSenhaBtn.addEventListener('click', function () {
        const tipoFila = prompt("Digite o tipo de fila:\nG - Geral\nP - Preferencial").toUpperCase();

        if (tipoFila !== 'G' && tipoFila !== 'P') {
            alert('Tipo de fila inválido. Por favor, insira "G" ou "P".');
            return;
        }

        fetch(`/retirarSenha/gerar?tipo=${tipoFila}`, {
            method: 'POST'
        })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => { throw new Error(text); });
                }
                return response.json();
            })
            .then(data => {
                senhaGeradaElem.textContent = `Sua senha é: ${data.tipo}${String(data.numero).padStart(3, '0')}`;
            })
            .catch(error => {
                console.error('Erro ao gerar senha:', error);
                alert('Erro ao gerar senha: ' + error.message);
            });
    });
});

