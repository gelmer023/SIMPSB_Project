package simpsb.utils;

import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Utilidad para manejo de sesiones y contexto de Faces.
 * Centraliza operaciones comunes de sesión y mensajes.
 * 
 * @author Sistema SIMPSB
 * @version 1.0
 */
public class SessionUtil {

    private static final Logger LOGGER = Logger.getLogger(SessionUtil.class.getName());

    private SessionUtil() {
        // Clase de utilidad, no debe ser instanciada
    }

    /**
     * Obtiene el contexto actual de Faces.
     * 
     * @return FacesContext actual
     */
    public static FacesContext getCurrentContext() {
        return FacesContext.getCurrentInstance();
    }

    /**
     * Obtiene un atributo de la sesión.
     * 
     * @param key clave del atributo
     * @return valor del atributo o null si no existe
     */
    public static Object getSessionAttribute(String key) {
        try {
            return getCurrentContext().getExternalContext().getSessionMap().get(key);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error al obtener atributo de sesión: " + key, e);
            return null;
        }
    }

    /**
     * Establece un atributo en la sesión.
     * 
     * @param key clave del atributo
     * @param value valor del atributo
     */
    public static void setSessionAttribute(String key, Object value) {
        try {
            getCurrentContext().getExternalContext().getSessionMap().put(key, value);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error al establecer atributo de sesión: " + key, e);
        }
    }

    /**
     * Invalida la sesión actual.
     */
    public static void invalidateSession() {
        try {
            getCurrentContext().getExternalContext().invalidateSession();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error al invalidar sesión", e);
        }
    }

    /**
     * Redirige a una URL específica.
     * 
     * @param url URL destino
     */
    public static void redirect(String url) {
        try {
            getCurrentContext().getExternalContext().redirect(url);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error al redirigir a: " + url, e);
        }
    }

    /**
     * Agrega un mensaje de información.
     * 
     * @param titulo título del mensaje
     * @param mensaje texto del mensaje
     */
    public static void addInfoMessage(String titulo, String mensaje) {
        addMessage(FacesMessage.SEVERITY_INFO, titulo, mensaje);
    }

    /**
     * Agrega un mensaje de advertencia.
     * 
     * @param titulo título del mensaje
     * @param mensaje texto del mensaje
     */
    public static void addWarningMessage(String titulo, String mensaje) {
        addMessage(FacesMessage.SEVERITY_WARN, titulo, mensaje);
    }

    /**
     * Agrega un mensaje de error.
     * 
     * @param titulo título del mensaje
     * @param mensaje texto del mensaje
     */
    public static void addErrorMessage(String titulo, String mensaje) {
        addMessage(FacesMessage.SEVERITY_ERROR, titulo, mensaje);
    }

    /**
     * Agrega un mensaje genérico.
     * 
     * @param severity severidad del mensaje
     * @param titulo título del mensaje
     * @param mensaje texto del mensaje
     */
    private static void addMessage(FacesMessage.Severity severity, String titulo, String mensaje) {
        try {
            getCurrentContext().addMessage(null, new FacesMessage(severity, titulo, mensaje));
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error al agregar mensaje: " + titulo, e);
        }
    }
}
