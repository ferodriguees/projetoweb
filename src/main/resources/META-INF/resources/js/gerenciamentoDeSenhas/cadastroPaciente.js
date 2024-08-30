// cadastroPaciente.js

document.addEventListener('DOMContentLoaded', function () {
    const cadastroPacienteForm = document.getElementById('cadastroPacienteForm');
    const resultadoCadastroElem = document.getElementById('resultadoCadastro');
    resultadoCadastroElem.className = 'error';

    function isCpfValid(cpf) {
        // Verifica se o CPF é uma sequência de 11 dígitos numéricos
        const cpfRegex = /^\d{11}$/;
        if (!cpfRegex.test(cpf)) return false;

        // Verifica se todos os dígitos são iguais
        if (/^(\d)\1{10}$/.test(cpf)) return false;

        return true;
    }

    cadastroPacienteForm.addEventListener('submit', function (event) {
        event.preventDefault(); // Impede o envio padrão do formulário

        const nome = document.getElementById('nome').value;
        const cpf = document.getElementById('cpf').value;

        if (!isCpfValid(cpf)) {
            resultadoCadastroElem.textContent = 'CPF inválido. Deve conter 11 dígitos e não pode ser uma sequência de números repetidos.';
            return;
        }

        fetch('/cadastro/realizarCadastro', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ nome: nome, cpf: cpf })
        })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => { throw new Error(text); });
                }
                // Verifica se há um corpo na resposta para evitar erro de JSON
                return response.text().then(text => {
                    return text ? JSON.parse(text) : {};
                });
            })
            .then(data => {
                resultadoCadastroElem.textContent = 'Paciente cadastrado com sucesso!';
                // Redirecionar após 2 segundos para a página de chamar senhas
                setTimeout(() => {
                    window.location.href = '/atendimento';
                }, 1000);
            })
            .catch(error => {
                console.error('Erro ao cadastrar paciente:', error);
                resultadoCadastroElem.textContent = 'Erro ao cadastrar paciente: ' + error.message;
            });
    });
});
