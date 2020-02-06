package simpsb.controller;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import simpsb.dao.*;
import simpsb.entidades.*;

@Named
@RequestScoped
public class CitasController {

    MailController mailC = new MailController();

    @EJB
    private CitasFacadeLocal citasFacadeLocal;
    @EJB
    private ServiciosFacadeLocal serviciosFacadeLocal;
    @EJB
    private EmpleadoFacadeLocal empleadoFacadeLocal;
    @EJB
    private UsuarioFacadeLocal usuarioFacadeLocal;
    @EJB
    private ClienteFacadeLocal clienteFacadeLocal;
    @EJB
    private EstadoFacadeLocal estadoFacadeLocal;
    @EJB
    private HorasFacadeLocal horasFacadeLocal;
    @EJB
    private DisponibilidadFacadeLocal disponibilidadFacadeLocal;

    private Citas citas;
    private Empleado empleado;
    private Servicios servicios;
    private Usuario usuario;
    private Cliente cliente;
    private Estado estado;
    private Horas horas;
    private Disponibilidad disponibilidad;

    private List<Servicios> listServicios;
    private List<Empleado> listEmpleados;
    private List<Horas> listHoras;

    @PostConstruct
    public void init() {
        citas = new Citas();
        servicios = new Servicios();
        empleado = new Empleado();
        estado = new Estado();
        cliente = new Cliente();
        usuario = new Usuario();
        horas = new Horas();
        disponibilidad = new Disponibilidad();
        listServicios = serviciosFacadeLocal.findAll();
        listEmpleados = empleadoFacadeLocal.findAll();
        listHoras = disponibilidadFacadeLocal.disponibles();
        validarEstado();
        validarDia();
    }

    //GETTERS Y SETTERS CONTROLADOR
    public Disponibilidad getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(Disponibilidad disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public Horas getHoras() {
        return horas;
    }

    public void setHoras(Horas horas) {
        this.horas = horas;
    }

    public List<Horas> getListHoras() {
        return listHoras;
    }

    public void setListHoras(List<Horas> listHoras) {
        this.listHoras = listHoras;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Citas getCitas() {
        return citas;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setCitas(Citas citas) {
        this.citas = citas;
    }

    public Servicios getServicios() {
        return servicios;
    }

    public void setServicios(Servicios servicios) {
        this.servicios = servicios;
    }

    public List<Servicios> getListServicios() {
        return listServicios;
    }

    public void setListServicios(List<Servicios> listServicios) {
        this.listServicios = listServicios;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public List<Empleado> getListEmpleados() {
        return listEmpleados;
    }

    public void setListEmpleados(List<Empleado> listEmpleados) {
        this.listEmpleados = listEmpleados;
    }

    //FECHAS 
    Calendar date = Calendar.getInstance();
    int dia = date.get(Calendar.DATE);
    int mes = date.get(Calendar.MONTH) + 1;
    int año = date.get(Calendar.YEAR);

    String diaS;
    String mesS;

    //GETTERS Y SETTERS FECHA
    public String getMesS() {
        return mesS;
    }

    public void setMesS(String mesS) {
        this.mesS = mesS;
    }

    public String getDiaS() {
        return diaS;
    }

    public void setDiaS(String diaS) {
        this.diaS = diaS;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    private void calcularTiempoEstimado() {
    }

    public void validarDisponibilidad() {
        Citas ct = (Citas) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cita");
        String tiempoEstimado = ct.getIdServicio().getTiempoEstimado();
        Date fc = ct.getFecha();
        try {
            if (tiempoEstimado.equals(60)) {
                horas.setIdHoras(horas.getIdHoras() + 2);
                disponibilidad.setHoraFK(horas);
                disponibilidad.setFecha(fc);
                disponibilidad.setEstado("Agendada");
            }
        } catch (Exception e) {
        }
    }

    public void generarCita() {
        Cliente cl = null;
        Usuario us = null;
        try {
            //TRAIGO DATOS DEL USUARIO QUE AGENDA LA CITA
            us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
            cl = clienteFacadeLocal.getIdCl(us);
            citas.setIdCliente(cl);
            //ASIGNO LAS LLAVES FORANEAS DE LA CITA
            citas.setIdEmpleado(empleado);
            citas.setIdServicio(servicios);
            estado.setIdEstado(3);
            citas.setEstadoFK(estado);
            //CREO LA CITA
            citasFacadeLocal.create(citas);
            //CREO UNA VARIABLE DE SESIÓN CON EL OBJETO CITA
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cita", citas);
            //EJECUTO LOS METODOS EN EL BACKGROUND
            validarDisponibilidad();
            calcularTiempoEstimado();
            //ENVIO MENSAJES
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Se ha generado exitosamente la cita"));
            FacesContext.getCurrentInstance().getExternalContext().redirect("consultarCita.xhtml");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Ha ocurrido un error al generar la cita"));
        }
    }

    public void cancelarCita(Citas citas) {
        try {
            estado.setIdEstado(1);
            citas.setEstadoFK(estado);
            citasFacadeLocal.edit(citas);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Se ha cancelado exitosamente la cita"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Ha ocurrido un error al cancelar la cita"));
        }
    }

    //MÉTODO PARA LISTAR
    public List<Citas> listarCitas() {
        List<Citas> listCitas = null;
        try {
            listCitas = citasFacadeLocal.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCitas;
    }

    //METODO PARA CONSULTAR LA CITA
    public String consultarCita(Citas ct) {
        try {
            citas = citasFacadeLocal.find(ct.getIdCita());
            servicios = citas.getIdServicio();
            empleado = citas.getIdEmpleado();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Correcto"));
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Ha ocurrido un error al modificar su cita"));
        }
        return "modificarCita";

    }

    //METODO PARA MODIFICAR LA CITA
    public void modificarCita() {
        try {
            citas.setIdEmpleado(empleado);
            citas.setIdServicio(servicios);
            citasFacadeLocal.edit(citas);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Se ha generado exitosamente su cita"));
            FacesContext.getCurrentInstance().getExternalContext().redirect("consultarCita.xhtml");
        } catch (Exception e) {
            citasFacadeLocal.edit(citas);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Ha ocurrido un error al modificar su cita"));
        }
    }

    //METODO PARA VALIDAR EL ESTADO DE LA CITA
    private void validarEstado() {
        try {
            //CREO UNA LISTA PARA TRAER TODAS LAS FECHAS

            List<Citas> listaCitas = null;
            listaCitas = citasFacadeLocal.findAll();

            //CONVIERTO LAS FECHAS A STRING
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

            for (Citas ct : listaCitas) {
                String fechaString = sdf.format(ct.getFecha());
                String fechaActual = sdf.format(new Date());
                //CONVIERTO LAS FECHAS A INT PARA PODER COMPARARLAS
                int fechaInt = Integer.parseInt(fechaString);
                int fechaActualInt = Integer.parseInt(fechaActual);
                //VERIFICO QUE LA LISTA DE LAS CITAS NO ESTÉ VACIA
                if (listaCitas != null) {
                    //HAGO UNA CONDICION PARA CAMBIAR EL ESTADO DE LA CITA A VENCIDA SIEMPRE Y CUANDO ESTE ACTIVA 
                    int es = ct.getEstadoFK().getIdEstado();
                    if (fechaInt < fechaActualInt) {
                        if (es == 3) {
                            estado.setIdEstado(2);
                            ct.setEstadoFK(estado);
                            citasFacadeLocal.edit(ct);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "La lista de citas esta vacia"));
        }
    }

    //MÉTODO PARA VALIDAR SI EL DIA TIENE UN SOLO DIGITO
    private void validarDia() {
        diaS = dia + "";
        if (diaS.length() == 1) {
            diaS = "0" + diaS;
        }
        mesS = mes + "";
        if (mesS.length() == 1) {
            mesS = "0" + mesS;
        }
    }

    //MÉTODOS ESPECIALES PARA EL PERFIL CLIENTE
    public List<Citas> listarCitasUs() {
        Usuario us = null;
        Cliente cl = null;
        List<Citas> listCitas = null;
        us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        cl = clienteFacadeLocal.getIdCl(us);
        try {
            listCitas = citasFacadeLocal.citasCli(cl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCitas;
    }

}
