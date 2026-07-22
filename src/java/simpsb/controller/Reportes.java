/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpsb.controller;

/**
 *
 * @author Gelmer Marquez
 */
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import simpsb.dao.*;
import simpsb.controller.*;
import simpsb.entidades.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

public class Reportes {

    public void getReporte(String ruta) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        Connection conexion;
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto", "root", "");

        //Se definen los parametros si es que el reporte necesita
        Map parameter = new HashMap();

        try {
            File file = new File(ruta);

            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

            httpServletResponse.setContentType("application/pdf");
            httpServletResponse.addHeader("Content-Type", "application/pdf");
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=ReporteSIMPSB.pdf");
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();

            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(file.getPath());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, conexion);

            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
            FacesContext.getCurrentInstance().responseComplete();

            JRExporter jrExporter = null;
            jrExporter = new JRPdfExporter();
            jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, httpServletResponse.getOutputStream());

            if (jrExporter != null) {
                try {
                    jrExporter.exportReport();
                } catch (JRException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void getCertificado(String ruta) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        int citas=us.getIdUsuario();
        Connection conexion;
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto", "root", "");
        //Se definen los parametros si es que el reporte necesita
        Map<String, Object> parametros = new HashMap<String, Object>();
        
        parametros.put("idUsuario", citas);
        try {

            File file = new File(ruta);

            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

            httpServletResponse.setContentType("application/pdf");
            httpServletResponse.addHeader("Content-Type", "application/pdf");
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=Certificado.pdf");
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();

            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(file.getPath());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conexion);

            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
            FacesContext.getCurrentInstance().responseComplete();

            JRExporter jrExporter = null;
            jrExporter = new JRPdfExporter();
            jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, httpServletResponse.getOutputStream());

            if (jrExporter != null) {
                try {
                    jrExporter.exportReport();
                } catch (JRException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void getFactura(String ruta) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        Connection conexion;
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Factura bill = (Factura) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("factura");
        int billf=bill.getIdFactura();
       
        
        conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto", "root", "");
        //Se definen los parametros si es que el reporte necesita
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("idFactura", billf);
        
        try {

            File file = new File(ruta);

            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

            httpServletResponse.setContentType("application/pdf");
            httpServletResponse.addHeader("Content-Type", "application/pdf");
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=FacturaSIMPSB.pdf");
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();

            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(file.getPath());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conexion);

            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
            FacesContext.getCurrentInstance().responseComplete();

            JRExporter jrExporter = null;
            jrExporter = new JRPdfExporter();
            jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, httpServletResponse.getOutputStream());

            if (jrExporter != null) {
                try {
                    jrExporter.exportReport();
                } catch (JRException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
