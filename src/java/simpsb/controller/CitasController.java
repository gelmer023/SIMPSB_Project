package simpsb.controller;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;
import javax.persistence.Query;
import javax.servlet.ServletContext;
import simpsb.dao.*;
import simpsb.entidades.*;

@Named
@ApplicationScoped
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
    @EJB
    private CalificacionFacadeLocal calificacionFacadeLocal;
    @EJB
    private ServiciosextraFacadeLocal serviciosExtraFacadeLocal;
    @EJB
    private FacturaFacadeLocal facturaFacadeLocal;
    @EJB
    private PorcentajepagosFacadeLocal porcentajepagosFacadeLocal;

    private Citas citas;
    private Empleado empleado;
    private Servicios servicios;
    private Usuario usuario;
    private Cliente cliente;
    private Estado estado;
    private Horas horas;
    private Factura factura;
    private Disponibilidad disponibilidad;
    private Calificacion calificacion;
    private Serviciosextra serviciosExtra;
    private Porcentajepagos porcentajepagos;

    private List<Servicios> listServicios;
    private List<Empleado> listEmpleados;
    private List<Horas> listHoras;
    private List<Citas> listFactura;

    @PostConstruct
    public void init() {
//        listFactura = citasFacadeLocal.listarFacturas();
        citas = new Citas();
        servicios = new Servicios();
        empleado = new Empleado();
        estado = new Estado();
        cliente = new Cliente();
        usuario = new Usuario();
        horas = new Horas();
        factura = new Factura();
        porcentajepagos = new Porcentajepagos();

        serviciosExtra = new Serviciosextra();
        calificacion = new Calificacion();
        disponibilidad = new Disponibilidad();
        listServicios = serviciosFacadeLocal.servActivos();
        listEmpleados = empleadoFacadeLocal.findAll();

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

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public List<Citas> getListFactura() {
        return listFactura;
    }

    public void setListFactura(List<Citas> listFactura) {
        this.listFactura = listFactura;
    }

    public Calificacion getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Calificacion calificacion) {
        this.calificacion = calificacion;
    }

    public Serviciosextra getServiciosExtra() {
        return serviciosExtra;
    }

    public void setServiciosExtra(Serviciosextra serviciosExtra) {
        this.serviciosExtra = serviciosExtra;
    }

    public Porcentajepagos getPorcentajepagos() {
        return porcentajepagos;
    }

    public void setPorcentajepagos(Porcentajepagos porcentajepagos) {
        this.porcentajepagos = porcentajepagos;
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
    SimpleDateFormat sdfm = new SimpleDateFormat("yyyy-MM-dd");

    String diaS;
    String diaMax;
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

    public String getDiaMax() {
        return diaMax;
    }

    public void setDiaMax(String diaMax) {
        this.diaMax = diaMax;
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

    public void consultarFecha() {
        try {
            if (citas.getFecha() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error:", "Debe seleccionar una fecha"));
            } else {
                citas.getFecha();
                listHoras = disponibilidadFacadeLocal.disponibles(citas);
                FacesContext.getCurrentInstance().getExternalContext().redirect("Cita.xhtml");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void generarCita() {
        Cliente cl = null;
        Usuario us = null;
        Citas ct = null;
        List<Disponibilidad> lista = disponibilidadFacadeLocal.findAll();
        try {
            //TRAIGO DATOS DEL USUARIO QUE AGENDA LA CITA
            us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
            cl = clienteFacadeLocal.getIdCl(us.getIdUsuario());
            citas.setIdCliente(cl);

            //ASIGNO LAS LLAVES FORANEAS DE LA CITA
            citas.setIdServicio(servicios);
            citas.setIdEmpleado(empleado);
            estado.setIdEstado(3);
            citas.setEstadoFK(estado);
            int hor = horas.getIdHoras();
            int hora = 0;
            Date dfech = null;
            for (Disponibilidad di : lista) {
                hora = di.getHoraFK().getIdHoras();
                dfech = di.getFecha();
            }
            if (hora == hor) {
                if (dfech.equals(citas.getFecha())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Ya existe una cita agendada en este mismo horario"));
                }
            } else {
                citas.setHoraFK(horas);
                //CREO LA CITA
                citasFacadeLocal.create(citas);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("citas", citas);

                //CREO DATOS DE LA TABLA DISPONIBILIDAD
                disponibilidad.setEstado("Agendada");
                disponibilidad.setCitaFK(citas);
                Date f = citas.getFecha();
                disponibilidad.setFecha(f);
                disponibilidad.setHoraFK(horas);
                disponibilidadFacadeLocal.create(disponibilidad);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("fecha");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Se ha generado exitosamente la cita"));
                if (us.getIdRol().getRol().equals("Cliente")) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("../../Cliente/Citas/consultarCitasCli.xhtml");
                } else {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("consultarCita.xhtml");
                }
                mailC.citas(citas);
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Ha ocurrido un error al generar la cita"));
        }
    }

    public void cancelarCita(Citas citas) {
        try {
            if (citas.getEstadoFK().getIdEstado() != 3) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error:", "Esta cita no se puede cancelar"));
            } else {
                estado.setIdEstado(1);
                citas.setEstadoFK(estado);
                citasFacadeLocal.edit(citas);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Se ha cancelado exitosamente la cita"));
            }
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

    //MÉTODO PARA LISTAR FACTURAS
    public List<Citas> listarFacturas() {
        List<Citas> listFactura = null;
        try {
            listFactura = citasFacadeLocal.listarFacturas();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listFactura;
    }

    //METODO PARA CONSULTAR LA CITA
    public void consultarCita(Citas ct) {
        Usuario us = null;
        us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        if (ct.getEstadoFK().getIdEstado() != 3) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error:", "Esta cita no se puede modificar"));
        }
        try {
            citas = citasFacadeLocal.find(ct.getIdCita());
            servicios = citas.getIdServicio();
            empleado = citas.getIdEmpleado();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Correcto"));

            if (us.getIdRol().getRol().equals("Cliente")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("../../Supervisor/Citas/modificarCita.xhtml");
            } else {
                FacesContext.getCurrentInstance().getExternalContext().redirect("modificarCita.xhtml");

            }
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Ha ocurrido un error al consultar su cita"));
        }
    }

    //METODO PARA CONSULTAR LA CALIFICACION
    public void consultarCC(Citas ct) {
        try {
            citas = citasFacadeLocal.find(ct.getIdCita());
            empleado = citas.getIdEmpleado();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Correcto"));
            FacesContext.getCurrentInstance().getExternalContext().redirect("calificar.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Ha ocurrido un error al consultar su cita"));
        }

    }

    public void calificar() {
        try {
            citas.getIdCita();
            calificacion.setCitaFK(citas);
            String fe = año + "-" + mes + "-" + dia;
            Date fechaHoyD = sdfm.parse(fe);
            calificacion.setFecha(fechaHoyD);
            //Creo la calificacion
            calificacionFacadeLocal.create(calificacion);
            estado.setIdEstado(5);
            citas.setEstadoFK(estado);
            citasFacadeLocal.edit(citas);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Se ha calificado exitosamente"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String hacerFactura(Citas ct) {
        Citas cv = null;
        try {
            citas = citasFacadeLocal.find(ct.getIdCita());

            servicios = citas.getIdServicio();
            empleado = citas.getIdEmpleado();
            Servicios serv = serviciosFacadeLocal.obtenerValor(citas.getIdServicio().getIdServicio());
            String valor = serv.getValor();
            citas.setValorTotal(valor);
            citas.setIdEmpleado(empleado);
            citas.setIdServicio(servicios);
            citasFacadeLocal.edit(citas);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cta", citas);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Correcto"));
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Ha ocurrido un error al consultar su cita"));
        }
        return "crearFactura";

    }

    //METODO PARA MODIFICAR LA CITA
    public void modificarCita() {
        Usuario us = null;
        us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");

        try {
            citas.setIdEmpleado(empleado);
            citas.setIdServicio(servicios);
            citasFacadeLocal.edit(citas);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Se ha generado exitosamente su cita"));
            if (us.getIdRol().getRol().equals("Cliente")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("../../Cliente/Citas/consultarCitasCli.xhtml");
            } else {
                FacesContext.getCurrentInstance().getExternalContext().redirect("consultarCita.xhtml");

            }
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

        int a;
        a = dia + 1;
        diaMax = a + "";
        if (diaMax.length() == 1) {
            diaMax = "0" + diaMax;
        }
    }

    //MÉTODOS ESPECIALES PARA EL PERFIL CLIENTE
    public List<Citas> listarCitasUs() {
        Usuario us = null;
        Cliente cl = null;
        List<Citas> listCitas = null;
        us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        cl = clienteFacadeLocal.getIdCl(us.getIdUsuario());
        int idCliente = cl.getIdCliente();
        try {
            listCitas = citasFacadeLocal.citasCli(idCliente);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCitas;
    }

    //LISTAR CITAS DEL CLIENTE PARA CALIFICAR
    public List<Citas> listarCitasCal() {
        Usuario us = null;
        Cliente cl = null;
        List<Citas> listCitas = null;
        us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        cl = clienteFacadeLocal.getIdCl(us.getIdUsuario());
        int idCliente = cl.getIdCliente();
        try {
            listCitas = citasFacadeLocal.citasCliCal(idCliente);
            if (listCitas.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error:", "No tiene citas para calificar"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCitas;
    }

    //MÉTODOS ESPECIALES PARA EL PERFIL EMPLEADO
    public List<Citas> listarCitasEmp() {
        Usuario us = null;
        Empleado emp = null;
        List<Citas> listCitas = null;
        us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        emp = empleadoFacadeLocal.getIdEmp(us.getIdUsuario());
        int idEmp = emp.getIdEmpleado();
        try {
            listCitas = citasFacadeLocal.citasEmp(idEmp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCitas;
    }

    public void agregarServicios() {
        try {
            Citas ct = (Citas) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cta");
            citas.setIdCita(ct.getIdCita());
            serviciosExtra.setIdServicio(servicios);
            serviciosExtra.setIdCita(citas);
            serviciosExtraFacadeLocal.create(serviciosExtra);
            Servicios sev = serviciosFacadeLocal.obtenerValor(serviciosExtra.getIdServicio().getIdServicio());
            int valorServE = Integer.parseInt(sev.getValor());
            Citas cit = citasFacadeLocal.getIdCita(citas.getIdCita());
            int valorServC = Integer.parseInt(cit.getValorTotal());
            int valorT = valorServE + valorServC;

            String valorTl = Integer.toString(valorT);
            cit.setValorTotal(valorTl);

            citasFacadeLocal.edit(cit);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Se ha registrado el Servicio"));
            consultarFactura(cit);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Ha ocurrido un error"));
        }
    }

    public void consultarFactura(Citas cit) {
        Citas cv = null;
        try {
            citas = citasFacadeLocal.find(cit.getIdCita());
            servicios = citas.getIdServicio();
            empleado = citas.getIdEmpleado();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Correcto"));
            FacesContext.getCurrentInstance().getExternalContext().redirect("crearFactura.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Ha ocurrido un error al consultar su cita"));
        }
    }
//Metodo para invocar el reporte y enviarle los parametros si es que necesita

    public void verFactura() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        //Instancia hacia la clase reporteClientes        
        Reportes rCliente = new Reportes();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("reportes/factura.jasper");

        rCliente.getFactura(ruta);
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void generarFactura() {
        try {
            //Asigno fecha actual
            String fechaHoy = año + "-" + mes + "-" + dia;
            Date fechaHoyD = new SimpleDateFormat("yyyy-MM-dd").parse(fechaHoy);
            factura.setFecha(fechaHoyD);

            //Asigno ID CITA
            Citas ct = (Citas) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cta");
            ct.getIdCita();
            factura.setIdCita(ct);

            //Asigno total
            factura.setValorTotal(citas.getValorTotal());

            //Asigno IVA
            int valorTotal = Integer.parseInt(citas.getValorTotal());
            int valorIVA = (int) (valorTotal * 0.19);
            String IVA = Integer.toString(valorIVA);
            factura.setIva(IVA);

            //Asigno subtotal
            int subtotal = valorTotal - valorIVA;
            String subTotal = Integer.toString(subtotal);
            factura.setSubTotal(subTotal);

            //Creo la factura
            facturaFacadeLocal.create(factura);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("factura", factura);

            //Cambio el estado de la cita a completada
            estado.setIdEstado(4);
            ct.setEstadoFK(estado);
            citasFacadeLocal.edit(ct);

            //Ejecuto el metodo para calcular el porcentaje
            generarPorcentaje();
            verFactura();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Correcto"));
            FacesContext.getCurrentInstance().getExternalContext().redirect("calificacion.xhtml");

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Ha ocurrido un error al generar la factura"));

        }
    }

    //Metodo para invocar el reporte y enviarle los parametros si es que necesita
    public void verReporte() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        //Instancia hacia la clase reporteClientes        
        Reportes rCliente = new Reportes();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("reportes/reporteGrafico.jasper");

        rCliente.getReporte(ruta);
        FacesContext.getCurrentInstance().responseComplete();
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
}
