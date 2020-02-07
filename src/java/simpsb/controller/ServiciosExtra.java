/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpsb.controller;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import simpsb.dao.*;
import simpsb.entidades.*;

/**
 *
 * @author Leonardo Lara
 */

@Named
@RequestScoped
public class ServiciosExtra {
    
    @EJB
    ServiciosextraFacadeLocal serviciosExtraFacadeLocal;
    Serviciosextra serviciosExtra;
    
    @EJB
    ServiciosFacadeLocal serviciosFacadeLocal;        
    Servicios servicios;
   
    private List<Servicios> listServicios;

    @PostConstruct
    public void init(){
        serviciosExtra = new Serviciosextra();
        servicios = new Servicios();
        listServicios = serviciosFacadeLocal.findAll();

    }

    public ServiciosextraFacadeLocal getServiciosExtraFacadeLocal() {
        return serviciosExtraFacadeLocal;
    }

    public void setServiciosExtraFacadeLocal(ServiciosextraFacadeLocal serviciosExtraFacadeLocal) {
        this.serviciosExtraFacadeLocal = serviciosExtraFacadeLocal;
    }

    public Serviciosextra getServiciosExtra() {
        return serviciosExtra;
    }

    public void setServiciosExtra(Serviciosextra serviciosExtra) {
        this.serviciosExtra = serviciosExtra;
    }
    
    public void registrarServEx(){
        try {
            serviciosExtra.setIdServicio(servicios);
            serviciosExtraFacadeLocal.create(serviciosExtra);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso: ", "Registro Exitoso"));
            FacesContext.getCurrentInstance().getExternalContext().redirect("facturarCita.xhtml");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error: ", "No conexion"));
            e.printStackTrace();
        }
    }
}
