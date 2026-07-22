package simpsb.controller;

import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import simpsb.dao.ClienteFacadeLocal;
import simpsb.dao.EmpleadoFacadeLocal;
import simpsb.dao.RolesFacadeLocal;
import simpsb.dao.UsuarioFacadeLocal;
import simpsb.entidades.Cliente;
import simpsb.entidades.Empleado;
import simpsb.entidades.Roles;
import simpsb.entidades.Usuario;
import simpsb.utils.AppConstants;
import simpsb.utils.SessionUtil;
import simpsb.utils.ValidationUtil;
import simpsb.utils.ExceptionUtil;

/**
 * Controlador para gestión de usuarios.
 * Maneja operaciones CRUD de usuarios, roles y perfiles.
 * 
 * @author Sistema SIMPSB
 * @version 1.0
 */
@Named
@RequestScoped
public class UsuarioController {

    private static final Logger LOGGER = Logger.getLogger(UsuarioController.class.getName());
    private static final String DEFAULT_PROFILE_IMAGE = "/FotosPerfil/predeterminado.jpg";

    @EJB
    private UsuarioFacadeLocal usuarioFacadeLocal;
    
    @EJB
    private ClienteFacadeLocal clienteFacadeLocal;
    
    @EJB
    private EmpleadoFacadeLocal empleadoFacadeLocal;
    
    @EJB
    private RolesFacadeLocal rolesFacadeLocal;

    private Usuario usuario;
    private Roles roles;
    private Cliente cliente;
    private Empleado empleado;
    private UploadController uploadController;
    private String confirmarPassword;

    @PostConstruct
    public void init() {
        usuario = new Usuario();
        roles = new Roles();
        cliente = new Cliente();
        empleado = new Empleado();
        uploadController = new UploadController();
    }

    // ===== GETTERS Y SETTERS =====

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public UploadController getUploadController() {
        return uploadController;
    }

    public void setUploadController(UploadController uploadController) {
        this.uploadController = uploadController;
    }

    public String getConfirmarPassword() {
        return confirmarPassword;
    }

    public void setConfirmarPassword(String confirmarPassword) {
        this.confirmarPassword = confirmarPassword;
    }

    // ===== MÉTODOS DE REGISTRO =====

    /**
     * Registra un nuevo usuario en el sistema (desde formulario público).
     */
    public void registrarUsuario() {
        try {
            validarUsuarioParaRegistro();
            
            // Asignar rol de Cliente por defecto
            Roles rolCliente = rolesFacadeLocal.find(AppConstants.ROLE_CLIENTE);
            if (rolCliente == null) {
                LOGGER.log(Level.WARNING, "Rol Cliente no encontrado");
                SessionUtil.addErrorMessage("Error:", "No se puede asignar rol");
                return;
            }
            
            usuario.setIdRol(rolCliente);
            usuario.setFoto(DEFAULT_PROFILE_IMAGE);
            
            // Crear usuario
            usuarioFacadeLocal.create(usuario);
            
            // Crear cliente asociado
            cliente.setIdUsuario(usuario);
            clienteFacadeLocal.create(cliente);
            
            SessionUtil.addInfoMessage("Éxito:", AppConstants.MSG_CREADO_EXITOSO);
            LOGGER.log(Level.INFO, "Nuevo usuario registrado: " + usuario.getNombre());
            
        } catch (IllegalArgumentException e) {
            SessionUtil.addWarningMessage("Validación:", e.getMessage());
        } catch (Exception e) {
            ExceptionUtil.handleException("Error al registrar usuario", e);
        }
    }

    public void crearUsuario() {
        Usuario user = null;
        try {
            roles.setIdRol(3);
            usuario.setIdRol(roles);
            usuarioFacadeLocal.create(usuario);
            cliente.setIdUsuario(usuario);
            clienteFacadeLocal.create(cliente);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Se ha registrado exitosamente"));
            FacesContext.getCurrentInstance().getExternalContext().redirect("consultarUsuario.xhtml");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Ha ocurrido un error al registrarse"));
        }
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> listUsu = null;
        try {
            listUsu = usuarioFacadeLocal.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listUsu;
    }

    public void eliminarUsuario(Usuario user) {
        try {
            empleadoFacadeLocal.remove(empleado);
            clienteFacadeLocal.remove(cliente);
            usuarioFacadeLocal.remove(user);
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Ha ocurrido un error al eliminar"));
        }
    }

    public String consultarUsuario(Usuario user) {
        try {
            usuario = usuarioFacadeLocal.find(user.getIdUsuario());
            roles = usuario.getIdRol();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Funciona correcto"));
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Ha ocurrido un error"));
        }
        return "modificarUsuario";
    }

    public void modificarUsuario() {
        try {
            usuario.getPass();
            roles = rolesFacadeLocal.find(roles.getIdRol());
            usuario.setIdRol(roles);
            usuarioFacadeLocal.edit(usuario);
            FacesContext.getCurrentInstance().getExternalContext().redirect("consultarUsuario.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Ha ocurrido un error"));
        }
    }

    //MÉTODOS PARA LA EDICIÓN DEL PERFIL CLIENT SIDE
    public void cambiarContra() {
        try {
            String contra1 = usuario.getPass();
            Usuario us;
            if (contra1.equals(contra)) {
                us = usuarioFacadeLocal.getId(usuario.getNumDocumento());
                usuario = us;
                usuario.setPass(contra);
                usuarioFacadeLocal.edit(usuario);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Se ha cambiado su contraseña exitosamente"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Ha ocurrido un error"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editarPerfil() {
        Usuario us = null;
        try {
            usuario.setIdUsuario(us.getIdUsuario());
            usuarioFacadeLocal.edit(usuario);
        } catch (Exception e) {
        }
    }

    public void cambiarFoto() {
        Usuario user;
        try {
            imagen.subirImagenes();
            user = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
            usuario = usuarioFacadeLocal.find(user.getIdUsuario());
            user.setFoto(imagen.getPathReal());
            usuario.setFoto(imagen.getPathReal());
            usuarioFacadeLocal.edit(usuario);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String editarU(Usuario us) {
        Usuario user;

        try {
            user = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
            usuario = usuarioFacadeLocal.find(user.getIdUsuario());
            usuarioFacadeLocal.edit(usuario);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Correcto"));
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Ha ocurrido un error al consultar su cita"));
        }
        return "editarPerfil";

    }

}
