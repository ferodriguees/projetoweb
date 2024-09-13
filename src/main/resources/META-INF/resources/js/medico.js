document.addEventListener('DOMContentLoaded', function () {
    const chamarPacienteBtn = document.getElementById('chamarPacienteBtn');
    const realizarAtendimentoBtn = document.getElementById('realizarAtendimentoBtn');
    const pacienteAusenteBtn = document.getElementById('pacienteAusenteBtn');
    const nomePacienteElem = document.getElementById('paciente-info');

    chamarPacienteBtn.addEventListener('click', function () {
        fetch('/medico/chamarPaciente', {
            method: 'GET'
        })
            .then(response => response.json())
            .then(data => {
                if (data && data.nome) {
                    nomePacienteElem.textContent = `Paciente: ${data.nome}`;
                    realizarAtendimentoBtn.disabled = false;
                    pacienteAusenteBtn.disabled = false;
                } else {
                    nomePacienteElem.textContent = 'Nenhum paciente na fila';
                    realizarAtendimentoBtn.disabled = true;
                    pacienteAusenteBtn.disabled = true;
                }
            })
            .catch(error => {
                console.error('Erro ao chamar o paciente:', error);
                nomePacienteElem.textContent = 'Erro ao chamar o paciente';
            });
    });


    realizarAtendimentoBtn.addEventListener('click', function () {
        window.location.href = '/medico/laudoMedico';  // Redireciona para a página de laudo
    });

    pacienteAusenteBtn.addEventListener('click', function () {
        fetch('/medico/marcarAusente', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json' // Mudamos para 'application/json'
            },
            body: JSON.stringify({}) // Enviando um corpo vazio
        })
            .then(response => response.text())
            .then(data => {
                alert(data);
                nomePacienteElem.textContent = ''; // Limpa o nome do paciente
                realizarAtendimentoBtn.disabled = true;
                pacienteAusenteBtn.disabled = true;
            })
            .catch(error => {
                console.error('Erro ao marcar paciente como ausente:', error);
            });
    });
});
