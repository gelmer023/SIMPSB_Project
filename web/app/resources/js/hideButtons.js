function hideButtons() {
    var estado = $("#estado").val();
    if (estado === "Cancelada") {
        $(".ocultar").hide();
    }
};
window.onload = function () {
    hideButtons();
};

