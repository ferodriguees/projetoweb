document.addEventListener('DOMContentLoaded', function () {
    const chamarProximaBtn = document.getElementById('chamarProximaBtn');
    const senhaChamadaElem = document.getElementById('senhaChamada');
    const realizarAtendimentoBtn = document.getElementById('realizarAtendimentoBtn');
    const pacienteAusenteBtn = document.getElementById('pacienteAusenteBtn');

    chamarProximaBtn.addEventListener('click', function () {
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

    realizarAtendimentoBtn.addEventListener('click', function () {
        window.location.href = '/cadastro';
    });

    pacienteAusenteBtn.addEventListener('click', function () {
        alert('Paciente marcado como ausente.');
        senhaChamadaElem.textContent = '';
        realizarAtendimentoBtn.disabled = true;
        pacienteAusenteBtn.disabled = true;
    });
});