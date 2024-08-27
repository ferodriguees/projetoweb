document.addEventListener('DOMContentLoaded', function() {
    const chamarSenhaBtn = document.getElementById('chamarSenhaBtn');
    const realizarAtendimentoBtn = document.getElementById('realizarAtendimentoBtn');
    const pacienteAusenteBtn = document.getElementById('pacienteAusenteBtn');
    const statusMessage = document.getElementById('statusMessage');

    chamarSenhaBtn.addEventListener('click', function() {
        fetch('/fila/chamar-proxima-senha', {
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('jwtToken')
            }
        })
            .then(response => response.json())
            .then(data => {
                if (data.senha) {
                    statusMessage.textContent = 'Próxima senha: ' + data.senha;
                    realizarAtendimentoBtn.disabled = false;
                    pacienteAusenteBtn.disabled = false;
                } else {
                    statusMessage.textContent = 'Nenhuma senha disponível no momento.';
                }
            })
            .catch(error => {
                console.error('Erro:', error);
                statusMessage.textContent = 'Erro ao chamar a próxima senha.';
            });
    });

    realizarAtendimentoBtn.addEventListener('click', function() {
        statusMessage.textContent = 'Realizando atendimento...';
        realizarAtendimentoBtn.disabled = true;
        pacienteAusenteBtn.disabled = true;

        // Lógica para realizar o atendimento
    });

    pacienteAusenteBtn.addEventListener('click', function() {
        statusMessage.textContent = 'Paciente marcado como ausente.';
        realizarAtendimentoBtn.disabled = true;
        pacienteAusenteBtn.disabled = true;

        // Lógica para marcar o paciente como ausente
    });
});
