document.addEventListener('DOMContentLoaded', function () {
    const chamarProximaBtn = document.getElementById('chamarProximaBtn');
    const senhaChamadaElem = document.getElementById('senhaChamada');
    const realizarAtendimentoBtn = document.getElementById('realizarAtendimentoBtn');
    const pacienteAusenteBtn = document.getElementById('pacienteAusenteBtn');

    function getJwtToken() {
        return localStorage.getItem('token');
    }

    function fetchWithJwt(url, options = {}) {
        const token = getJwtToken();
        const headers = new Headers(options.headers || {});

        if (token) {
            headers.append('Authorization', `Bearer ${token}`);
        }

        return fetch(url, {
            ...options,
            headers: headers
        });
    }

    chamarProximaBtn.addEventListener('click', function () {
        fetchWithJwt('/atendimento/chamar', {
            method: 'GET',
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
        window.location.href = '/paciente';
    });

    pacienteAusenteBtn.addEventListener('click', function () {
        alert('Paciente marcado como ausente.');
        senhaChamadaElem.textContent = '';
        realizarAtendimentoBtn.disabled = true;
        pacienteAusenteBtn.disabled = true;
    });
});
