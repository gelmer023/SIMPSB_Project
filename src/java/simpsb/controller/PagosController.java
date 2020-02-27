package simpsb.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import simpsb.dao.*;
import simpsb.entidades.*;

@Named
@RequestScoped
public class PagosController {

    @EJB
    ComisionesFacadeLocal comisionesFacadeLocal;
    @EJB
    PorcentajepagosFacadeLocal porcentajepagosFacadeLocal;
    @EJB
    FacturaFacadeLocal facturaFacadeLocal;
    @EJB
    EmpleadoFacadeLocal empleadoFacadeLocal;

    private Comisiones comisiones;
    private Porcentajepagos porcentajepagos;
    private Factura factura;
    private Empleado empleado;
    private Usuario usuario;

    private List<Empleado> listEmpleado;
    @PostConstruct
    public void init() {
        comisiones = new Comisiones();
        porcentajepagos = new Porcentajepagos();
        factura = new Factura();
        empleado = new Empleado();
        usuario = new Usuario();
        listEmpleado = empleadoFacadeLocal.findAll();
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

    public Porcentajepagos getPorcentajepagos() {
        return porcentajepagos;
    }

    public void setPorcentajepagos(Porcentajepagos porcentajepagos) {
        this.porcentajepagos = porcentajepagos;
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

    public void generarPorcentaje() {
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

    public void registrarPago() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Porcentajepagos> listPagos;
        try {
            String fecha = año + "-" + mes + "-" + dia;
            Date fechaActual = sdf.parse(fecha);
            comisiones.setFecha(fechaActual);
            
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
}
