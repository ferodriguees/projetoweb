window.onload = function() {
    const form = document.getElementById('agendarForm');
    const cpfInput = document.getElementById('cpf');
    const horaSelect = document.getElementById('hora');
    const mensagemDiv = document.getElementById('mensagem');

    form.addEventListener('submit', function(event) {
        event.preventDefault();

        const cpf = cpfInput.value;

        if (!validarCPF(cpf)) {
            alert("Por favor, insira um CPF válido com 11 dígitos e sem números sequenciais.");
            return;
        }

        if (horaSelect.value === "") {
            alert("Por favor, escolha um horário para sua consulta.");
            return;
        }

        const formData = new FormData(form);

        fetch('/home/agendar-consulta', {
            method: 'POST',
            body: formData
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    mensagemDiv.textContent = "Agendamento realizado com sucesso!";
                    mensagemDiv.style.color = "green";
                } else {
                    mensagemDiv.textContent = "Erro ao realizar agendamento.";
                    mensagemDiv.style.color = "red";
                }
            })
            .catch(error => {
                mensagemDiv.textContent = "Erro ao realizar agendamento.";
                mensagemDiv.style.color = "red";
                console.error('Erro:', error);
            });
    });

    function validarCPF(cpf) {
        // Remove qualquer caracter não numérico
        cpf = cpf.replace(/\D/g, '');

        // Verifica se o CPF tem 11 dígitos
        if (cpf.length !== 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais (sequenciais)
        if (/^(\d)\1+$/.test(cpf)) {
            return false;
        }

        return true;
    }
};
