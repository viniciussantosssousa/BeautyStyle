document.addEventListener('DOMContentLoaded', function () {
    
    // Seleciona o ícone do menu
    const menuIcon = document.querySelector('.menu-icon');
    // Seleciona a lista de navegação (o menu que será mostrado/escondido)
    const navList = document.querySelector('.ul');
    // Seleciona todos os links e botões (como o dropdown toggle) dentro dos itens da lista de navegação
    const navLinksAndButtons = document.querySelectorAll('.ul li a, .ul li button'); // Inclui botões para o dropdown

    // Função para fechar o menu
    function closeMenu() {
        if (navList.classList.contains('ativo')) {
            navList.classList.remove('ativo');
        }
    }

    // Função para abrir/fechar o menu
    function toggleMenu() {
        navList.classList.toggle('ativo');
    }

    // Verifica se os elementos principais foram encontrados
    if (menuIcon && navList) {
        // Adiciona um evento de clique ao ícone do menu
        menuIcon.addEventListener('click', function (event) {
            event.stopPropagation(); // Impede que o clique no ícone feche o menu imediatamente se o evento borbulhar para o document
            toggleMenu();
        });

        // Adiciona um evento de clique para cada link ou botão dentro do menu
        navLinksAndButtons.forEach(function (element) {
            element.addEventListener('click', function(event) {
                // Verifica se o elemento clicado é o dropdown toggle "Perfil"
                // O dropdown toggle tem o id "dropdownPerfil" e a classe "dropdown-toggle"
                if (element.id === 'dropdownPerfil' && element.classList.contains('dropdown-toggle')) {
                    // Se for o dropdown "Perfil", não faz nada aqui para não fechar o menu.
                    // A funcionalidade do dropdown do Bootstrap cuidará de abrir/fechar o submenu.
                    // event.stopPropagation(); // Pode ser útil para evitar que outros listeners fechem o menu
                    return; 
                }
                
                // Para qualquer outro link ou botão que não seja um dropdown que precise ficar aberto,
                // fecha o menu se ele estiver ativo.
                if (navList.classList.contains('ativo')) {
                    // Apenas fecha se não for um botão que abre outro submenu dentro do overlay
                    // ou se a intenção é claramente navegar para outra página/seção.
                    // Para botões que são parte de um 'dropdown', o Bootstrap pode precisar controlar o estado.
                    // Neste caso, a lógica acima para 'dropdownPerfil' já trata o caso específico.
                    // Para outros links, o fechamento é desejado.
                    if(element.tagName === 'A' && element.getAttribute('href') && element.getAttribute('href') !== '#') {
                         closeMenu();
                    } else if (element.tagName === 'BUTTON' && !element.classList.contains('dropdown-toggle')) {
                        // Se for um botão que não é um dropdown toggle (ex: botão Sair do form)
                        closeMenu();
                    }
                    // Se for um link para uma âncora na mesma página (ex: #feedback-section)
                    if(element.tagName === 'A' && element.getAttribute('href') && element.getAttribute('href').startsWith('#')) {
                        closeMenu();
                    }
                }
            });
        });

    } else {
        // Loga um erro no console se um dos elementos não for encontrado
        if (!menuIcon) {
            console.error('Elemento .menu-icon não encontrado.');
        }
        if (!navList) {
            console.error('Elemento .ul (lista de navegação) não encontrado.');
        }
    }
});