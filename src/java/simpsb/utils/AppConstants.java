package simpsb.utils;

/**
 * Clase de constantes de la aplicación.
 * Centraliza valores hardcodeados como roles, mensajes y rutas.
 * 
 * @author Sistema SIMPSB
 * @version 1.0
 */
public final class AppConstants {

    private AppConstants() {
        // Clase de utilidad, no debe ser instanciada
    }

    // ===== ROLES =====
    public static final String ROLE_CLIENTE = "Cliente";
    public static final String ROLE_EMPLEADO = "Empleado";
    public static final String ROLE_SUPERVISOR = "Supervisor";

    // ===== RUTAS DE NAVEGACIÓN =====
    public static final String ROUTE_INDEX_CLIENTE = "Cliente/indexCliente?faces-redirect=true";
    public static final String ROUTE_INDEX_EMPLEADO = "Empleado/indexEmpleado?faces-redirect=true";
    public static final String ROUTE_INDEX_SUPERVISOR = "Supervisor/indexSupervisor?faces-redirect=true";
    public static final String ROUTE_LOGIN = "../../index?faces-redirect=true";
    public static final String ROUTE_ERROR_404 = "../../../../Error/404.xhtml";

    // ===== MENSAJES DE ERROR =====
    public static final String MSG_CREDENCIALES_INCORRECTAS = "Credenciales incorrectas";
    public static final String MSG_ERROR_LOGIN = "Error al iniciar sesión";
    public static final String MSG_ERROR_LOGOUT = "Error al cerrar sesión";
    public static final String MSG_ERROR_GENERICO = "Ha ocurrido un error";
    public static final String MSG_SESION_EXPIRADA = "Su sesión ha expirado";

    // ===== MENSAJES DE ÉXITO =====
    public static final String MSG_EXITOSO = "Operación realizada exitosamente";
    public static final String MSG_CREADO_EXITOSO = "Registro creado exitosamente";
    public static final String MSG_ACTUALIZADO_EXITOSO = "Registro actualizado exitosamente";
    public static final String MSG_ELIMINADO_EXITOSO = "Registro eliminado exitosamente";

    // ===== CLAVES DE SESIÓN =====
    public static final String SESSION_KEY_USER = "user";
    public static final String SESSION_KEY_ROLE = "role";

    // ===== CONFIGURACIÓN =====
    public static final int MAX_UPLOAD_SIZE = 5242880; // 5 MB
    public static final String[] ALLOWED_IMAGE_TYPES = {"image/jpeg", "image/png", "image/gif"};
}
