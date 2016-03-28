/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funciones;

import Modelo.SmsFactura;
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
 
    public void generarFactura(SmsFactura factura) throws JRException, IOException{
    
        ConectarBD conexion = new ConectarBD();
        Map parametro = new HashMap();
        parametro.put("idFactura", factura.getIdFactura());

        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("Reportes_SMS/FacturaPrincipal.jasper"));
        JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), parametro, conexion.getConexion());

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename=Factura " + factura.getIdFactura() + ".pdf");
        ServletOutputStream stream = response.getOutputStream();

        JasperExportManager.exportReportToPdfStream(jp, stream);

        stream.flush();
        stream.close();
        FacesContext.getCurrentInstance().responseComplete();
        
    }
    
    public void generarFacturaPOS(SmsFactura factura) throws JRException, IOException{
    
        ConectarBD conexion = new ConectarBD();
        Map parametro = new HashMap();
        parametro.put("idFactura", factura.getIdFactura());

        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("Reportes_SMS/reportePrincipalMini.jasper"));
        JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), parametro, conexion.getConexion());

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename=Factura POS" + factura.getIdFactura() + ".pdf");
        ServletOutputStream stream = response.getOutputStream();

        JasperExportManager.exportReportToPdfStream(jp, stream);

        stream.flush();
        stream.close();
        FacesContext.getCurrentInstance().responseComplete();
        
    }
    
}
