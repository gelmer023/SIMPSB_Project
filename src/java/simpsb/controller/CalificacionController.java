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
import simpsb.dao.CalificacionFacadeLocal;
import simpsb.entidades.Calificacion;
import simpsb.entidades.Citas;

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
    public Calificacion getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Calificacion calificacion) {
        this.calificacion = calificacion;
    }
    
    //MÉTODO PARA LISTAR
    public List<Calificacion> listarCalificaciones() {
        List<Calificacion> listCal = null;
        try {
            listCal = calificacionFacadeLocal.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCal;
    }
    
}
