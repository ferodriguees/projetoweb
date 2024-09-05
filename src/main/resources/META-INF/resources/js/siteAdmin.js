document.addEventListener('DOMContentLoaded', function () {
    const dropdown = document.getElementById("userDropdown");
    const dropdownContent = document.getElementById("userDropdownContent");

    dropdown.addEventListener('click', function () {
        dropdownContent.classList.toggle("show");
    });

    // Fechar dropdown se clicar fora
    window.onclick = function(event) {
        if (!event.target.matches('.dropbtn')) {
            if (dropdownContent.classList.contains('show')) {
                dropdownContent.classList.remove('show');
            }
        }
    };
});
