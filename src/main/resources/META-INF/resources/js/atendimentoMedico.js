document.addEventListener('DOMContentLoaded', function () {
    const realizarAtendimentoBtn = document.getElementById('realizarAtendimentoBtn');

    realizarAtendimentoBtn.addEventListener('click', function () {
        const laudo = document.getElementById('laudo').value;
        const atestado = document.getElementById('atestado').value;

        const atendimento = {
            laudo: laudo,
            atestado: atestado
        };

        fetch('/medico/realizarAtendimento', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(atendimento)
        })
            .then(response => {
                if (response.ok) {
                    alert('Atendimento realizado com sucesso!');
                } else {
                    alert('Erro ao realizar atendimento.');
                }
            })
            .catch(error => {
                console.error('Erro ao realizar atendimento:', error);
            });
    });
});
