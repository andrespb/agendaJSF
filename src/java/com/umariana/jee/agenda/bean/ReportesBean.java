/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umariana.jee.agenda.bean;

import java.io.File;
import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
/**
 *
 * @author Andres
 */
@ManagedBean
@RequestScoped
public class ReportesBean {

    /**
     * Creates a new instance of ReportesBean
     */
    public ReportesBean() {
        
        
    }
    
    private Connection turismoConn;

    public void mostrarReportesSencillos(int formato, int reporte) {

        try {
            conectar();
            String nombreReporte = "reporte";
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reportes/usuario.jasper"));
            //reporte de usuario
            if (reporte == 1) {
                nombreReporte += "usuario";
                jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reportes/usuario.jasper"));
            } //reporte de destinos
            /*else if (reporte == 2) {
                nombreReporte += "Destinos";
                jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reportes/destinos.jasper"));
            } //reporte de tours
            else if (reporte == 3) {
                nombreReporte += "Tours";
                jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reportes/tours.jasper"));
            }else if (reporte == 4) {
                nombreReporte += "Transacciones";
                jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reportes/trx.jasper"));
            }else if (reporte == 5) {
                nombreReporte += "EstadoTransacciones";
                jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reportes/estadoTransacciones.jasper"));
            }*/

            byte[] bytes = null;
            JasperPrint jasperPrint = null;
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

            ServletOutputStream outStream = null;

            //PDF
            if (formato == 1) {
                bytes = JasperRunManager.runReportToPdf(jasper.getPath(), null, turismoConn);
                response.setContentType("application/pdf");
                response.setContentLength(bytes.length);
                outStream = response.getOutputStream();
                outStream.write(bytes, 0, bytes.length);
                //WORD    
            } else if (formato == 2) {
                jasperPrint = JasperFillManager.fillReport(jasper.getPath(), null, turismoConn);
                response.addHeader("Content-disposition", "attachment; filename=" + nombreReporte + ".doc");
                outStream = response.getOutputStream();

                JRDocxExporter exporter = new JRDocxExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outStream);
                exporter.exportReport();
                //EXCEL    
            } else if (formato == 3) {
                jasperPrint = JasperFillManager.fillReport(jasper.getPath(), null, turismoConn);
                response.addHeader("Content-disposition", "attachment; filename=" + nombreReporte + ".xls");
                outStream = response.getOutputStream();

                JRXlsExporter exporter = new JRXlsExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outStream);
                exporter.exportReport();
                //POWERT POINT    
            } else if (formato == 4) {
                jasperPrint = JasperFillManager.fillReport(jasper.getPath(), null, turismoConn);
                response.addHeader("Content-disposition", "attachment; filename=" + nombreReporte + ".ppt");
                outStream = response.getOutputStream();

                JRPptxExporter exporter = new JRPptxExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outStream);
                exporter.exportReport();
            }

            outStream.flush();
            outStream.close();

            desconectar();

            FacesContext.getCurrentInstance().responseComplete();

        } catch (IOException | JRException ex) {
            Logger.getLogger(ReportesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void pruebas() throws Exception {

        conectar();

        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reportes/usuario.jasper"));
        byte[] bytes = JasperRunManager.runReportToPdf(jasper.getPath(), null, turismoConn);
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.setContentType("application/pdf");
        response.setContentLength(bytes.length);
        ServletOutputStream outStream = response.getOutputStream();
        outStream.write(bytes, 0, bytes.length);
        outStream.flush();
        outStream.close();

        desconectar();

        FacesContext.getCurrentInstance().responseComplete();

    }

    public void conectar() {
        try {
            String driver = "com.mysql.jdbc.Driver";
            String conectionString = "jdbc:mysql";
            String bd = "dbappajendajava";
            String usuario = "root";
            String password = "";
            String servidor = "localhost";
            int puerto = 3306;

            Class.forName(driver).newInstance();

            this.turismoConn = DriverManager.getConnection(conectionString + ":" + "//" + servidor + ":" + puerto + "/" + bd, usuario, password);

        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(UsuarioView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void desconectar() {

    }
    
}
