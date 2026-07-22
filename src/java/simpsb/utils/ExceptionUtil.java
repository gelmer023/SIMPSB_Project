package simpsb.utils;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Utilidad para manejo centralizado de excepciones.
 * Proporciona métodos para loguear y manejar errores de forma consistente.
 * 
 * @author Sistema SIMPSB
 * @version 1.0
 */
public class ExceptionUtil {

    private static final Logger LOGGER = Logger.getLogger(ExceptionUtil.class.getName());

    private ExceptionUtil() {
        // Clase de utilidad, no debe ser instanciada
    }

    /**
     * Registra una excepción a nivel SEVERE.
     * 
     * @param message mensaje descriptivo
     * @param exception excepción a loguear
     */
    public static void logSevere(String message, Exception exception) {
        LOGGER.log(Level.SEVERE, message, exception);
    }

    /**
     * Registra una excepción a nivel WARNING.
     * 
     * @param message mensaje descriptivo
     * @param exception excepción a loguear
     */
    public static void logWarning(String message, Exception exception) {
        LOGGER.log(Level.WARNING, message, exception);
    }

    /**
     * Registra una excepción a nivel INFO.
     * 
     * @param message mensaje descriptivo
     * @param exception excepción a loguear
     */
    public static void logInfo(String message, Exception exception) {
        LOGGER.log(Level.INFO, message, exception);
    }

    /**
     * Obtiene el mensaje de error más descriptivo.
     * 
     * @param exception excepción
     * @return mensaje de error
     */
    public static String getErrorMessage(Exception exception) {
        if (exception == null) {
            return AppConstants.MSG_ERROR_GENERICO;
        }
        
        String message = exception.getMessage();
        if (message != null && !message.isEmpty()) {
            return message;
        }
        
        Throwable cause = exception.getCause();
        if (cause != null) {
            return cause.getMessage();
        }
        
        return exception.getClass().getSimpleName();
    }

    /**
     * Maneja una excepción de forma consistente, registrándola y mostrando mensaje al usuario.
     * 
     * @param message mensaje para el usuario
     * @param exception excepción
     * @param isWarning true para warnings, false para errores
     */
    public static void handleException(String message, Exception exception, boolean isWarning) {
        if (isWarning) {
            logWarning(message, exception);
            SessionUtil.addWarningMessage("Advertencia", message);
        } else {
            logSevere(message, exception);
            SessionUtil.addErrorMessage("Error", message);
        }
    }

    /**
     * Maneja una excepción como error (no warning).
     * 
     * @param message mensaje para el usuario
     * @param exception excepción
     */
    public static void handleException(String message, Exception exception) {
        handleException(message, exception, false);
    }

    /**
     * Valida que no sea nulo, lanza IllegalArgumentException si lo es.
     * 
     * @param object objeto a validar
     * @param name nombre del objeto (para mensaje de error)
     * @throws IllegalArgumentException si el objeto es null
     */
    public static void validateNotNull(Object object, String name) {
        if (object == null) {
            throw new IllegalArgumentException("El objeto " + name + " no puede ser nulo");
        }
    }

    /**
     * Obtiene el stack trace como String.
     * 
     * @param exception excepción
     * @return stack trace completo
     */
    public static String getStackTrace(Exception exception) {
        StringBuilder sb = new StringBuilder();
        sb.append(exception.getClass().getName()).append(": ").append(exception.getMessage()).append("\n");
        
        for (StackTraceElement element : exception.getStackTrace()) {
            sb.append("\tat ").append(element).append("\n");
        }
        
        if (exception.getCause() != null) {
            sb.append("Caused by: ").append(getStackTrace((Exception) exception.getCause()));
        }
        
        return sb.toString();
    }
}
