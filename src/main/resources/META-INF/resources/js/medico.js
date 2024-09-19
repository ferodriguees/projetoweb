document.addEventListener('DOMContentLoaded', function () {
    const chamarPacienteBtn = document.getElementById('chamarPacienteBtn');
    const realizarAtendimentoBtn = document.getElementById('realizarAtendimentoBtn');
    const pacienteAusenteBtn = document.getElementById('pacienteAusenteBtn');
    const nomePacienteElem = document.getElementById('paciente-info');

    chamarPacienteBtn.addEventListener('click', function () {
        fetch('/medico/chamarPaciente', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`,
            }
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
        window.location.href = '/medico/laudoMedico';
    });

    pacienteAusenteBtn.addEventListener('click', function () {
        fetch('/medico/marcarAusente', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({})
        })
            .then(response => response.text())
            .then(data => {
                alert(data);
                nomePacienteElem.textContent = '';
                realizarAtendimentoBtn.disabled = true;
                pacienteAusenteBtn.disabled = true;
            })
            .catch(error => {
                console.error('Erro ao marcar paciente como ausente:', error);
            });
    });
});
