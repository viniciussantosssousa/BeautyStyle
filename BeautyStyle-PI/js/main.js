document.addEventListener("DOMContentLoaded", function () {
    const menuIcon = document.querySelector(".menu-icon");
    const navMenu = document.querySelector(".ul");
    const carousels = document.querySelectorAll(".div-carosel");

    menuIcon.addEventListener("click", function () {
        navMenu.classList.toggle("ativo");

        // Se o menu está ativo, esconder os carrosséis
        if (navMenu.classList.contains("ativo")) {
            carousels.forEach(carousel => carousel.classList.add("hidden"));
        } else {
            carousels.forEach(carousel => carousel.classList.remove("hidden"));
        }
    });
});
