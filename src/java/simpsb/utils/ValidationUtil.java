package simpsb.utils;

import java.util.regex.Pattern;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Utilidad para validaciones de datos.
 * Proporciona métodos para validar entrada de usuario.
 * 
 * @author Sistema SIMPSB
 * @version 1.0
 */
public class ValidationUtil {

    private static final Logger LOGGER = Logger.getLogger(ValidationUtil.class.getName());
    
    // Patrones de validación
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );
    
    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^[0-9]{7,15}$"
    );
    
    private static final Pattern ALPHANUM_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9\\s]+$"
    );

    private ValidationUtil() {
        // Clase de utilidad, no debe ser instanciada
    }

    /**
     * Valida que una cadena no sea nula o vacía.
     * 
     * @param value valor a validar
     * @param fieldName nombre del campo (para mensajes)
     * @return true si es válida, false en caso contrario
     */
    public static boolean isNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Campo vacío: " + fieldName);
            return false;
        }
        return true;
    }

    /**
     * Valida que un email sea válido.
     * 
     * @param email email a validar
     * @return true si es válido, false en caso contrario
     */
    public static boolean isValidEmail(String email) {
        if (!isNotEmpty(email, "email")) {
            return false;
        }
        boolean valid = EMAIL_PATTERN.matcher(email).matches();
        if (!valid) {
            LOGGER.log(Level.WARNING, "Email inválido: " + email);
        }
        return valid;
    }

    /**
     * Valida que un teléfono sea válido.
     * 
     * @param phone teléfono a validar
     * @return true si es válido, false en caso contrario
     */
    public static boolean isValidPhone(String phone) {
        if (!isNotEmpty(phone, "phone")) {
            return false;
        }
        boolean valid = PHONE_PATTERN.matcher(phone).matches();
        if (!valid) {
            LOGGER.log(Level.WARNING, "Teléfono inválido: " + phone);
        }
        return valid;
    }

    /**
     * Valida que una contraseña sea segura (mínimo 8 caracteres).
     * 
     * @param password contraseña a validar
     * @return true si es segura, false en caso contrario
     */
    public static boolean isValidPassword(String password) {
        if (!isNotEmpty(password, "password")) {
            return false;
        }
        if (password.length() < 8) {
            LOGGER.log(Level.WARNING, "Contraseña muy corta");
            return false;
        }
        return true;
    }

    /**
     * Valida que un número esté dentro de un rango.
     * 
     * @param value número a validar
     * @param min valor mínimo (inclusive)
     * @param max valor máximo (inclusive)
     * @param fieldName nombre del campo (para mensajes)
     * @return true si está en rango, false en caso contrario
     */
    public static boolean isInRange(int value, int min, int max, String fieldName) {
        if (value < min || value > max) {
            LOGGER.log(Level.WARNING, "Valor fuera de rango para " + fieldName + ": " + value);
            return false;
        }
        return true;
    }

    /**
     * Valida que una cadena sea alfanumérica.
     * 
     * @param value valor a validar
     * @param fieldName nombre del campo (para mensajes)
     * @return true si es alfanumérica, false en caso contrario
     */
    public static boolean isAlphanumeric(String value, String fieldName) {
        if (!isNotEmpty(value, fieldName)) {
            return false;
        }
        boolean valid = ALPHANUM_PATTERN.matcher(value).matches();
        if (!valid) {
            LOGGER.log(Level.WARNING, "Valor no alfanumérico para " + fieldName);
        }
        return valid;
    }

    /**
     * Valida la longitud de una cadena.
     * 
     * @param value valor a validar
     * @param minLength longitud mínima
     * @param maxLength longitud máxima
     * @param fieldName nombre del campo (para mensajes)
     * @return true si cumple con la longitud, false en caso contrario
     */
    public static boolean isValidLength(String value, int minLength, int maxLength, String fieldName) {
        if (!isNotEmpty(value, fieldName)) {
            return false;
        }
        int length = value.trim().length();
        if (length < minLength || length > maxLength) {
            LOGGER.log(Level.WARNING, "Longitud inválida para " + fieldName + ": " + length);
            return false;
        }
        return true;
    }

    /**
     * Sanitiza una cadena removiendo caracteres especiales peligrosos.
     * 
     * @param value valor a sanitizar
     * @return valor sanitizado
     */
    public static String sanitize(String value) {
        if (value == null) {
            return null;
        }
        return value.replaceAll("[<>\"'%;()&+]", "").trim();
    }
}
