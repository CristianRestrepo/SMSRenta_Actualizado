/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.metodos;

import static com.planit.smsrenta.metodos.Upload.getPath;
import com.planit.smsrenta.modelos.SmsContrato;
import com.planit.smsrenta.modelos.SmsFactura;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author CristianCamilo
 */
public class GenerarReportes {

    public static String rutaDocumento;

    public static String getRutaDocumento() {
        return rutaDocumento;
    }

    public static void setRutaDocumento(String rutaDocumento) {
        GenerarReportes.rutaDocumento = rutaDocumento;
    }

    public void generarFactura(SmsFactura factura) throws JRException, IOException {

        ConectarBD conexion = new ConectarBD();
        Map parametro = new HashMap();
        parametro.put("idFactura", factura.getIdFactura());

        File jasper = new File(getPath() + "/resources/reportes/FacturaPrincipal.jasper");
        JasperPrint jp = JasperFillManager.fillReport(jasper.getAbsolutePath(), parametro, conexion.getConexion());

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename=Factura " + factura.getIdFactura() + ".pdf");
        ServletOutputStream stream = response.getOutputStream();

        JasperExportManager.exportReportToPdfStream(jp, stream);

        stream.flush();
        stream.close();
        FacesContext.getCurrentInstance().responseComplete();
    }

     public void generarFacturaPOS(SmsFactura factura) throws JRException, IOException {

        ConectarBD conexion = new ConectarBD();
        Map parametro = new HashMap();
        parametro.put("idFactura", factura.getIdFactura());

        File jasper = new File(getPath() + "/resources/reportes/FacturaPrincipalMini.jasper");
        JasperPrint jp = JasperFillManager.fillReport(jasper.getAbsolutePath(), parametro, conexion.getConexion());

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename=POS " + factura.getIdFactura() + ".pdf");
        ServletOutputStream stream = response.getOutputStream();

        JasperExportManager.exportReportToPdfStream(jp, stream);

        stream.flush();
        stream.close();
        FacesContext.getCurrentInstance().responseComplete();
    }
    
     public void generarFUEC(SmsContrato contrato) throws JRException, IOException {

        ConectarBD conexion = new ConectarBD();
        Map parametro = new HashMap();
        parametro.put("idContrato", contrato.getIdContrato());

        File jasper = new File(getPath() + "/resources/reportes/FacturaFUEC.jasper");
        JasperPrint jp = JasperFillManager.fillReport(jasper.getAbsolutePath(), parametro, conexion.getConexion());

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename=FUEC " + contrato.getIdContrato() + ".pdf");
        ServletOutputStream stream = response.getOutputStream();

        JasperExportManager.exportReportToPdfStream(jp, stream);

        stream.flush();
        stream.close();
        FacesContext.getCurrentInstance().responseComplete();
    }
    
    public void generarFacturaEnContexto(SmsFactura factura) {

        try {
            String path = Upload.getPathDocumentos() + "Factura " + factura.getIdFactura() + ".pdf";
            ConectarBD conexion = new ConectarBD();
            Map parametro = new HashMap();
            parametro.put("idFactura", factura.getIdFactura());

            File jasper = new File(getPath() + "/resources/reportes/FacturaPrincipal.jasper");
            JasperPrint jp = JasperFillManager.fillReport(jasper.getAbsolutePath(), parametro, conexion.getConexion());

            JasperExportManager.exportReportToPdfFile(jp, path);
        } catch (JRException e) {
            e.getMessage();
        }
    }
    
    public void generarFacturaPOSEnContexto(SmsFactura factura) {

        try {
            String path = Upload.getPathDocumentos() + "POS " + factura.getIdFactura() + ".pdf";
            ConectarBD conexion = new ConectarBD();
            Map parametro = new HashMap();
            parametro.put("idFactura", factura.getIdFactura());

            File jasper = new File(getPath() + "/resources/reportes/FacturaPrincipalMini.jasper");
            JasperPrint jp = JasperFillManager.fillReport(jasper.getAbsolutePath(), parametro, conexion.getConexion());

            JasperExportManager.exportReportToPdfFile(jp, path);
        } catch (JRException e) {
            e.getMessage();
        }
    }
        
    public void generarFUECEnContexto(SmsContrato contrato) {

        try {
            String path = Upload.getPathDocumentos() + "FUEC " + contrato.getIdContrato() + ".pdf";
            ConectarBD conexion = new ConectarBD();
            Map parametro = new HashMap();
            parametro.put("idContrato", contrato.getIdContrato());

            File jasper = new File(getPath() + "/resources/reportes/FacturaFUEC.jasper");
            JasperPrint jp = JasperFillManager.fillReport(jasper.getAbsolutePath(), parametro, conexion.getConexion());

            JasperExportManager.exportReportToPdfFile(jp, path);
        } catch (JRException e) {
            e.getMessage();
        }
    }
}
