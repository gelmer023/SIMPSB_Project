package simpsb.controller;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;
import simpsb.entidades.*;
import simpsb.dao.*;

/**
 *
 * @author Leonardo Lara
 */
@Named
@RequestScoped
public class PagosController {

    @EJB
    PorcentajepagosFacadeLocal porcentajePagosFacadeLocal;
    @EJB
    ComisionesFacadeLocal comisionesFacadeLocal;
    @EJB
    FacturaFacadeLocal facturaFacadeLocal;
    @EJB
    EmpleadoFacadeLocal empleadoFacadeLocal;
    @EJB
    CitasFacadeLocal citasFacadeLocal;

    Porcentajepagos porcentajePagos;
    Comisiones comisiones;
    Factura factura;
    Empleado empleado;
    Citas citas;

    private List<Factura> listFactura;
    private List<Empleado> listEmpleado;
    private List<Citas> listCitas;

    @PostConstruct
    public void init() {
        porcentajePagos = new Porcentajepagos();
        comisiones = new Comisiones();
        factura = new Factura();
        empleado = new Empleado();
        citas = new Citas();
        listEmpleado = empleadoFacadeLocal.findAll();
        listFactura = facturaFacadeLocal.findAll();
        listCitas = citasFacadeLocal.findAll();
    }

    public Porcentajepagos getPorcentajePagos() {
        return porcentajePagos;
    }

    public void setPorcentajePagos(Porcentajepagos porcentajePagos) {
        this.porcentajePagos = porcentajePagos;
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

    public List<Empleado> getListEmpleado() {
        return listEmpleado;
    }

    public void setListEmpleado(List<Empleado> listEmpleado) {
        this.listEmpleado = listEmpleado;
    }

    //FECHAS 
    Calendar date = Calendar.getInstance();
    int dia = date.get(Calendar.DATE);
    int mes = date.get(Calendar.MONTH) + 1;
    int año = date.get(Calendar.YEAR);

    public void generarPorcentaje() {
        Factura bill = (Factura) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("factura");
        try {
            //Asigno el porcentaje
            int valorTotal = Integer.parseInt(bill.getValorTotal());
            int porcentaje = (int) (valorTotal * 0.15);
            porcentajePagos.setPorcentaje(porcentaje);

            //Asigno la fecha
            Date fechaHoyD = bill.getFecha();
            porcentajePagos.setFecha(fechaHoyD);

            //Asigno el empleado
            Empleado idEmp = bill.getIdCita().getIdEmpleado();
            empleado.setIdEmpleado(idEmp.getIdEmpleado());
            porcentajePagos.setIdEmpleadoFK(empleado);

            //Creo el registro
            porcentajePagosFacadeLocal.create(porcentajePagos);
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
