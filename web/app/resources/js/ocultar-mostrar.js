function mostrar_ocultar() {
    var fecha = $("#fecha").val();
    if (fecha !== null) {
        $("#contenido").show();
    } else {
        alert("Debe seleccionar una fecha");
    }
}