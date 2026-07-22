package simpsb.controller;

import java.util.logging.Logger;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import simpsb.dao.UsuarioFacadeLocal;
import simpsb.entidades.Usuario;
import simpsb.utils.AppConstants;
import simpsb.utils.SessionUtil;

/**
 * Controlador de sesión del usuario.
 * Maneja autenticación, logout y verificación de sesión.
 * 
 * @author Sistema SIMPSB
 * @version 1.0
 */
@Named
@RequestScoped
public class SesionController {

    private static final Logger LOGGER = Logger.getLogger(SesionController.class.getName());

    @EJB
    private UsuarioFacadeLocal usuarioFacadeLocal;
    
    private Usuario usuario;
    private Usuario usuarioSesion;

    @PostConstruct
    public void init() {
        usuario = new Usuario();
    }

    // ===== GETTERS Y SETTERS =====
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(Usuario usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }

    // ===== MÉTODOS DE AUTENTICACIÓN =====

    /**
     * Inicia sesión del usuario con credenciales básicas.
     * 
     * @return URL de redirección según el rol del usuario, o null si falla
     */
    public String iniciarSesion() {
        try {
            Usuario usuarioAutenticado = usuarioFacadeLocal.login(usuario);
            
            if (usuarioAutenticado != null) {
                guardarEnSesion(usuarioAutenticado);
                return obtenerRutaSegunRol(usuarioAutenticado.getIdRol().getRol());
            } else {
                SessionUtil.addErrorMessage("Aviso:", AppConstants.MSG_CREDENCIALES_INCORRECTAS);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al iniciar sesión", e);
            SessionUtil.addErrorMessage("Error:", AppConstants.MSG_ERROR_LOGIN);
        }
        return null;
    }

    /**
     * Inicia sesión y redirige a editar perfil.
     * 
     * @return URL de redirección, o null si falla
     */
    public String iniciarSesionConFoto() {
        try {
            Usuario usuarioAutenticado = usuarioFacadeLocal.login(usuario);
            
            if (usuarioAutenticado != null) {
                guardarEnSesion(usuarioAutenticado);
                recargarUsuarioDeSesion();
                return "editarPerfil?faces-redirect=true";
            } else {
                SessionUtil.addErrorMessage("Aviso:", AppConstants.MSG_CREDENCIALES_INCORRECTAS);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al iniciar sesión con foto", e);
            SessionUtil.addErrorMessage("Error:", AppConstants.MSG_ERROR_LOGIN);
        }
        return null;
    }

    /**
     * Cierra la sesión del usuario.
     * 
     * @return URL de redirección al login
     */
    public String logout() {
        try {
            SessionUtil.invalidateSession();
            LOGGER.log(Level.INFO, "Sesión cerrada correctamente");
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error al cerrar sesión", e);
            SessionUtil.addWarningMessage("Advertencia:", AppConstants.MSG_ERROR_LOGOUT);
        }
        return AppConstants.ROUTE_LOGIN;
    }

    /**
     * Verifica que el usuario tenga una sesión válida.
     */
    public void verificarSesion() {
        try {
            usuarioSesion = (Usuario) SessionUtil.getSessionAttribute(AppConstants.SESSION_KEY_USER);
            
            if (usuarioSesion == null) {
                LOGGER.log(Level.INFO, "Sesión expirada o no existe");
                SessionUtil.redirect(AppConstants.ROUTE_ERROR_404);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al verificar sesión", e);
            SessionUtil.addErrorMessage("Error:", AppConstants.MSG_SESION_EXPIRADA);
        }
    }

    // ===== MÉTODOS PRIVADOS =====

    /**
     * Guarda el usuario en la sesión.
     * 
     * @param usuario usuario a guardar
     */
    private void guardarEnSesion(Usuario usuario) {
        SessionUtil.setSessionAttribute(AppConstants.SESSION_KEY_USER, usuario);
        if (usuario.getIdRol() != null) {
            SessionUtil.setSessionAttribute(AppConstants.SESSION_KEY_ROLE, usuario.getIdRol().getRol());
        }
    }

    /**
     * Recarga los datos del usuario desde la base de datos.
     */
    private void recargarUsuarioDeSesion() {
        try {
            Usuario usuarioActual = (Usuario) SessionUtil.getSessionAttribute(AppConstants.SESSION_KEY_USER);
            
            if (usuarioActual != null) {
                usuario = usuarioFacadeLocal.find(usuarioActual.getIdUsuario());
                if (usuario != null) {
                    guardarEnSesion(usuario);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error al recargar datos del usuario", e);
        }
    }

    /**
     * Obtiene la ruta de redirección según el rol del usuario.
     * 
     * @param rol rol del usuario
     * @return URL de redirección
     */
    private String obtenerRutaSegunRol(String rol) {
        switch (rol) {
            case AppConstants.ROLE_CLIENTE:
                return AppConstants.ROUTE_INDEX_CLIENTE;
            case AppConstants.ROLE_EMPLEADO:
                return AppConstants.ROUTE_INDEX_EMPLEADO;
            case AppConstants.ROLE_SUPERVISOR:
                return AppConstants.ROUTE_INDEX_SUPERVISOR;
            default:
                LOGGER.log(Level.WARNING, "Rol desconocido: " + rol);
                return null;
        }
    }
}
