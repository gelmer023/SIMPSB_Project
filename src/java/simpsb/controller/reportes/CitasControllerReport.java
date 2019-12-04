/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpsb.controller.reportes;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import simpsb.dao.*;
import simpsb.entidades.*;

@Named
@RequestScoped
public class CitasControllerReport {

    @EJB
    private ComisionesFacadeLocal comisionesFacadeLocal;
    private List<Comisiones> listComisiones;

    @EJB
    private CitasFacadeLocal citasFacadeLocal;
    private List<Citas> listcCitases;
    @PostConstruct
    private void init() {
        listComisiones = comisionesFacadeLocal.findAll();
    }

    public CitasControllerReport() {
    }

    public List<Citas> listarCitas() {
        listcCitases = citasFacadeLocal.findAll();
        return listcCitases;
    }

    public void genenarPDF(ActionEvent actionEvent) {
        //Genero un Hash Map para los parametros del reporte
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("idCita", 1);

        //Genero la lista para los Fields del reporte
        listarCitas();
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listcCitases);

        //Traer la ruta del Jasper  
        String ruta = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reportes/");
        try {
            //Generar el Reporte
            JasperPrint jasperPrint = JasperFillManager.fillReport(ruta + "/ReporteCitas.jasper", parametros, beanCollectionDataSource);

            //Con estas lineas mi navegador puede leer el PDF y lo puede descargar
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=ReporteCitas.pdf");
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();

            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ComisionesFacadeLocal getComisionesFacadeLocal() {
        return comisionesFacadeLocal;
    }

    public void setComisionesFacadeLocal(ComisionesFacadeLocal comisionesFacadeLocal) {
        this.comisionesFacadeLocal = comisionesFacadeLocal;
    }

    public List<Comisiones> getListComisiones() {
        return listComisiones;
    }

    public void setListComisiones(List<Comisiones> listComisiones) {
        this.listComisiones = listComisiones;
    }

    public CitasFacadeLocal getCitasFacadeLocal() {
        return citasFacadeLocal;
    }

    public void setCitasFacadeLocal(CitasFacadeLocal citasFacadeLocal) {
        this.citasFacadeLocal = citasFacadeLocal;
    }

    public List<Citas> getListcCitases() {
        return listcCitases;
    }

    public void setListcCitases(List<Citas> listcCitases) {
        this.listcCitases = listcCitases;
    }

}
