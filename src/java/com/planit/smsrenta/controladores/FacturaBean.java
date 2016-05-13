/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.controladores;

import com.planit.smsrenta.dao.IFacturaDao;
import com.planit.smsrenta.dao.IReservacionDao;
import com.planit.smsrenta.dao.ImpFacturaDao;
import com.planit.smsrenta.dao.ImpReservacionDao;
import com.planit.smsrenta.metodos.GenerarReportes;
import com.planit.smsrenta.metodos.Upload;
import com.planit.smsrenta.modelos.SmsFactura;
import com.planit.smsrenta.modelos.SmsReservacion;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author CristianCamilo
 */
public class FacturaBean {

    private SmsFactura facturaView;
    private List<SmsFactura> facturaListView;
    private double descuento;
    private String tipo;
    IFacturaDao facturaDao;
    IReservacionDao reservacionDao;
    private static String rutaFactura;

    public FacturaBean() {

        facturaDao = new ImpFacturaDao();
        reservacionDao = new ImpReservacionDao();
        tipo = "";

        descuento = 0;
        facturaView = new SmsFactura();
        facturaListView = new ArrayList<>();
    }

    public static String getRutaFactura() {
        return rutaFactura;
    }

    public SmsFactura getFacturaView() {
        return facturaView;
    }

    public void setFacturaView(SmsFactura facturaView) {
        this.facturaView = facturaView;
    }

    public List<SmsFactura> getFacturaListView() {
        return facturaListView;
    }

    public void setFacturaListView(List<SmsFactura> facturaListView) {
        this.facturaListView = facturaListView;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    //Metodos
    public void registrar(SmsReservacion reservacion) {
        Date fecha = new Date();
        facturaView.setFacturaFecha(fecha);
        facturaView.setFacturaFechaVencimiento(fecha);

        facturaView.setFacturaSubtotal(reservacion.getReservacionCosto());
        facturaView.setFacturaDescuento((facturaView.getFacturaSubtotal() * descuento) / 100);
        facturaView.setFacturaTotal(facturaView.getFacturaSubtotal() - facturaView.getFacturaDescuento());
        facturaView.setFacturaIva(facturaView.getFacturaTotal() * 0);
        facturaView.setFacturaNeto((facturaView.getFacturaTotal()) + facturaView.getFacturaIva());
        facturaView.setFacturaTotal(facturaView.getFacturaNeto());

        facturaView.setSmsReservacion(reservacion);
        facturaDao.registrarFactura(facturaView);
        facturaView = new SmsFactura();
    }

    public void eliminar() {
        facturaDao.eliminarFactura(facturaView);
        facturaView = new SmsFactura();
    }

    public void generarFactura(SmsReservacion reservacion) throws JRException, IOException {
        facturaView = facturaDao.consultarFacturaSegunReservacion(reservacion);
        if (facturaView.getIdFactura() == null) {
            registrar(reservacion);
        }
        GenerarReportes reporte = new GenerarReportes();
        facturaView = facturaDao.consultarFacturaSegunReservacion(reservacion);
        reporte.generarFactura(facturaView);
    }

    public void generarFacturaPos(SmsReservacion reservacion) throws JRException, IOException {
        facturaView = facturaDao.consultarFacturaSegunReservacion(reservacion);
        if (facturaView.getIdFactura() == null) {
            registrar(reservacion);
        }
        GenerarReportes reporte = new GenerarReportes();
        facturaView = facturaDao.consultarFacturaSegunReservacion(reservacion);
        reporte.generarFacturaPOS(facturaView);
    }

    public void verificarFacturaEnSistema(SmsReservacion reservacion) {
        FacesMessage message;
        facturaView = facturaDao.consultarFacturaSegunReservacion(reservacion);
        String ruta = Upload.getPathDocumentos() + "Factura " + facturaView.getIdFactura() + ".pdf";
        File fichero = new File(ruta);
        if (!fichero.exists()) {
            if (facturaView.getIdFactura() == null) {
                registrar(reservacion);
            }
            GenerarReportes reporte = new GenerarReportes();
            facturaView = facturaDao.consultarFacturaSegunReservacion(reservacion);
            reporte.generarFacturaEnContexto(facturaView);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "La factura ha sido generada,", "Puede verla si asi lo desea.");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "La factura ya esta creada en el sistema", "Puede verla si lo desea");
        }
        GenerarReportes.rutaDocumento = Upload.getPathDefaultDocumentos() + "Factura " + facturaView.getIdFactura() + ".pdf";
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void verificarFacturaPosEnSistema(SmsReservacion reservacion) throws JRException, IOException {
        FacesMessage message;
        facturaView = facturaDao.consultarFacturaSegunReservacion(reservacion);
        String ruta = Upload.getPathDocumentos() + "Factura POS" + facturaView.getIdFactura() + ".pdf";
        File fichero = new File(ruta);
        if (!fichero.exists()) {
            if (facturaView.getIdFactura() == null) {
                registrar(reservacion);
            }
            GenerarReportes reporte = new GenerarReportes();
            facturaView = facturaDao.consultarFacturaSegunReservacion(reservacion);
            reporte.generarFacturaPOSEnContexto(facturaView);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "La factura ha sido generada,", "Puede verla si asi lo desea.");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "La factura ya esta creada en el sistema", "Puede verla si lo desea");
        }
        GenerarReportes.rutaDocumento = Upload.getPathDefaultDocumentos() + "Factura POS" + facturaView.getIdFactura() + ".pdf";
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String verFacturaPC(SmsReservacion reservacion) {
        FacesMessage message;
        facturaView = facturaDao.consultarFacturaSegunReservacion(reservacion);
        String ruta = Upload.getPathDocumentos() + "Factura " + facturaView.getIdFactura() + ".pdf";
        File fichero = new File(ruta);
        if (fichero.exists()) {
            GenerarReportes.rutaDocumento = Upload.getPathDefaultDocumentos() + "Factura " + facturaView.getIdFactura() + ".pdf";
            return "AdminPDocumentos";
        }
        message = new FacesMessage(FacesMessage.SEVERITY_WARN, "La factura no ha sido generada,", "Por favor genere la factura en formato PC.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        return "";
    }

    public String verFacturaPOS(SmsReservacion reservacion) {
        FacesMessage message;
        facturaView = facturaDao.consultarFacturaSegunReservacion(reservacion);
        String ruta = Upload.getPathDocumentos() + "Factura POS" + facturaView.getIdFactura() + ".pdf";
        File fichero = new File(ruta);
        if (fichero.exists()) {
            GenerarReportes.rutaDocumento = Upload.getPathDefaultDocumentos() + "Factura POS" + facturaView.getIdFactura() + ".pdf";
            return "AdminPDocumentos";
        }
        message = new FacesMessage(FacesMessage.SEVERITY_WARN, "La factura no ha sido generada,", "Por favor genere la factura en formato POS.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        return "";
    }

}
