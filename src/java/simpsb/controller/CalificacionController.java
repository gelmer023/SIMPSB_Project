/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpsb.controller;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import simpsb.dao.CalificacionFacadeLocal;
import simpsb.entidades.Calificacion;

/**
 *
 * @author Leonardo Lara
 */


@Named
@RequestScoped
public class CalificacionController {
    @EJB
    CalificacionFacadeLocal calificacionFacadeLocal;
    Calificacion calificacion;

    @PostConstruct
    public void init() {
        calificacion = new Calificacion();
    }

    public CalificacionFacadeLocal getCalificacionFacadeLocal() {
        return calificacionFacadeLocal;
    }

    public void setCalificacionFacadeLocal(CalificacionFacadeLocal calificacionFacadeLocal) {
        this.calificacionFacadeLocal = calificacionFacadeLocal;
    }

    public Calificacion getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Calificacion calificacion) {
        this.calificacion = calificacion;
    }
    
    public void registrarCalifiacion(){
        try {
            calificacionFacadeLocal.create(calificacion);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso:", "Registro Exitoso"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error:", "Error"));
            e.printStackTrace();
        }
    }
}
