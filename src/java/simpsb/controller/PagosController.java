package simpsb.controller;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import simpsb.dao.*;
import simpsb.entidades.*;
import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

@Named
@RequestScoped
public class PagosController {

    @EJB
    private ComisionesFacadeLocal comisionesFacadeLocal;
    @EJB
    private EmpleadoFacadeLocal empleadoFacadeLocal;
    @EJB
    private UsuarioFacadeLocal usuarioFacadeLocal;
    @EJB
    private FacturaFacadeLocal facturaFacadeLocal;
    @EJB
    private PorcentajepagosFacadeLocal porcentajepagosFacadeLocal;

    private Porcentajepagos porcentajepagos;
    private Comisiones comisiones;
    private Empleado empleado;
    private Usuario usuario;
    private Factura factura;

    private List<Empleado> listEmpleado;
    private List<Factura> listFactura;

    @PostConstruct
    public void init() {
        comisiones = new Comisiones();
        empleado = new Empleado();
        usuario = new Usuario();
        factura = new Factura();
        porcentajepagos = new Porcentajepagos();
        listEmpleado = empleadoFacadeLocal.findAll();
        listFactura = facturaFacadeLocal.findAll();
    }

    //GETTER Y SETTERS CONTROLADOR
    public Porcentajepagos getPorcentajepagos() {
        return porcentajepagos;
    }

    public void setPorcentajepagos(Porcentajepagos porcentajepagos) {
        this.porcentajepagos = porcentajepagos;
    }

    public Comisiones getComisiones() {
        return comisiones;
    }

    public void setComisiones(Comisiones comisiones) {
        this.comisiones = comisiones;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public List<Empleado> getListEmpleado() {
        return listEmpleado;
    }

    public void setListEmpleado(List<Empleado> listEmpleado) {
        this.listEmpleado = listEmpleado;
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

    public void generarPorcentaje(){
        Factura bill = (Factura) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("factura");
        try {
            //Asigno el porcentaje
            int valorTotal = Integer.parseInt(bill.getValorTotal());
            int porcentaje = (int) (valorTotal * 0.15);
            porcentajepagos.setPorcentaje(porcentaje);
            
            //Asigno la fecha
            Date fechaHoyD = bill.getFecha();
            porcentajepagos.setFecha(fechaHoyD);
            
            //Asigno el empleado
            Empleado idEmp = bill.getIdCita().getIdEmpleado();
            empleado.setIdEmpleado(idEmp.getIdEmpleado());
            porcentajepagos.setIdEmpleadoFK(empleado);
            
            //Creo el registro
            porcentajepagosFacadeLocal.create(porcentajepagos);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Funciona correcto"));
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Ha ocurrido un error"));
        }

    }

    public void generarPago() {
        try {
            comisiones.setIdEmpleado(empleado);
            comisiones.setIdFactura(factura);
            comisionesFacadeLocal.create(comisiones);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Se ha generado exitosamente su pago"));
            FacesContext.getCurrentInstance().getExternalContext().redirect("consultarPago.xhtml");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Ha ocurrido un error al generar su pago"));
        }
    }

    public void eliminarPagos(Comisiones comisiones) {
        try {
            comisionesFacadeLocal.remove(comisiones);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", ""));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", ""));
        }
    }

    public List<Comisiones> listarPagos() {
        List<Comisiones> listPagos = null;
        try {
            listPagos = comisionesFacadeLocal.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listPagos;
    }

    public String consultarPago(Comisiones comi) {
        try {
            comisiones = comisionesFacadeLocal.find(comi.getIdComisiones());
            empleado = comisiones.getIdEmpleado();
            factura = comisiones.getIdFactura();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Correcto"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Ha ocurrido un error al modificar su cita"));
        }
        return "modificarPago";
    }

    public void modificarPago() {
        try {
            comisiones.setIdEmpleado(empleado);
            comisiones.setIdFactura(factura);
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
