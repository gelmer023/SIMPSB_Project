package simpsb.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import simpsb.dao.*;
import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import javax.servlet.ServletContext;
import simpsb.entidades.*;

/**
 *
 * @author Leonardo Lara
 */
@Named
@RequestScoped

public class PagosController {
    @EJB
    PorcentajepagosFacadeLocal porcentajepagosFacadeLocal;
    @EJB
    ComisionesFacadeLocal comisionesFacadeLocal;
    @EJB
    FacturaFacadeLocal facturaFacadeLocal;
    @EJB
    EmpleadoFacadeLocal empleadoFacadeLocal;
    @EJB

    CitasFacadeLocal citasFacadeLocal;

    private Comisiones comisiones;
    private Porcentajepagos porcentajepagos;
    private Factura factura;
    private Empleado empleado;
    private Usuario usuario;
    private Citas citas;

    private List<Empleado> listEmpleado;
    private List<Factura> listFactura;
    @PostConstruct
    public void init() {
        comisiones = new Comisiones();
        porcentajepagos = new Porcentajepagos();
        factura = new Factura();
        empleado = new Empleado();
        usuario = new Usuario();
        citas = new Citas();
        empleado = new Empleado();
        factura = new Factura();
        porcentajepagos = new Porcentajepagos();
        listEmpleado = empleadoFacadeLocal.findAll();
        listFactura = facturaFacadeLocal.findAll();
    }

    public List<Empleado> getListEmpleado() {
        return listEmpleado;
    }

    public void setListEmpleado(List<Empleado> listEmpleado) {
        this.listEmpleado = listEmpleado;
    }
    

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Comisiones getComisiones() {
        return comisiones;
    }

    public void setComisiones(Comisiones comisiones) {
        this.comisiones = comisiones;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Citas getCitas() {
        return citas;
    }

    public void setCitas(Citas citas) {
        this.citas = citas;
    }

    public List<Factura> getListFactura() {
        return listFactura;
    }

    public void setListFactura(List<Factura> listFactura) {
        this.listFactura = listFactura;
    }

    //FECHAS 
    Calendar date = Calendar.getInstance();
    int dia = date.get(Calendar.DATE);
    int mes = date.get(Calendar.MONTH) + 1;
    int año = date.get(Calendar.YEAR);


    
    //Variables para guardar las fechas
    private Date fechaInicial; 
    private Date fechaFinal; 

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public void registrarPago() {
        List<Porcentajepagos> listPagos = null;
        try {
            comisiones.setIdEmpleado(empleado);
            
            //Asigno el valor
            //Primero se suman todos ls valores que me retorna la lista dependiendo de las fechas seleccionadas
            listPagos = porcentajepagosFacadeLocal.calcularPago(empleado.getIdEmpleado(), fechaInicial, fechaFinal);
            int pagos = 0;
            for (Porcentajepagos pago : listPagos) {
                pagos = pago.getPorcentaje();
                pagos = pagos + pagos;
            }
            //Ahora si asigno el valor 
            comisiones.setValor(pagos);
            
            //Asigno el usuario que generó el pago
            Usuario user = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
            usuario.setIdUsuario(user.getIdUsuario());
            comisiones.setUsuarioFK(usuario);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String consultarPago(Comisiones comi) {
        try {
            comisiones = comisionesFacadeLocal.find(comi.getIdComisiones());
            empleado = comisiones.getIdEmpleado();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Correcto"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Ha ocurrido un error al modificar su cita"));
        }
        return "modificarPago";
    }

    public void modificarPago() {
        try {
            comisiones.setIdEmpleado(empleado);
            comisionesFacadeLocal.edit(comisiones);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Se ha generado exitosamente su cita"));
            FacesContext.getCurrentInstance().getExternalContext().redirect("consultarPago.xhtml");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Ha ocurrido un error al modificar su cita"));
        }
    }

    //Metodo para invocar el reporte y enviarle los parametros si es que necesita
    public void verReporte2() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        //Instancia hacia la clase reporteClientes        
        Reportes rCliente = new Reportes();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("reportes/reporteGrafico.jasper");

        rCliente.getReporte(ruta);
        FacesContext.getCurrentInstance().responseComplete();
    }

    //Metodo para invocar el reporte y enviarle los parametros si es que necesita
    public void verReporte() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        //Instancia hacia la clase reporteClientes        
        Reportes rCliente = new Reportes();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("reportes/reportePagos.jasper");

        rCliente.getReporte(ruta);
        FacesContext.getCurrentInstance().responseComplete();
    }

}
