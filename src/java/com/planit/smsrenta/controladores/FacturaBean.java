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
import com.planit.smsrenta.modelos.SmsFactura;
import com.planit.smsrenta.modelos.SmsReservacion;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    public FacturaBean() {

        facturaDao = new ImpFacturaDao();
        reservacionDao = new ImpReservacionDao();
        tipo = "";
        descuento = 0;

        facturaView = new SmsFactura();
        facturaListView = new ArrayList<>();
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
        facturaView.setFacturaIva(facturaView.getFacturaTotal() * 0.16);        
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
        if (facturaDao.consultarFacturaSegunReservacion(reservacion).isEmpty()) {
            registrar(reservacion);
        }
        GenerarReportes reporte = new GenerarReportes();
        facturaView = facturaDao.consultarFacturaSegunReservacion(reservacion).get(0);
        reporte.generarFactura(facturaView);
    }

    public void generarFacturaPos(SmsReservacion reservacion) throws JRException, IOException {
        if (facturaDao.consultarFacturaSegunReservacion(reservacion).isEmpty()) {
            registrar(reservacion);
        }
        GenerarReportes reporte = new GenerarReportes();
        facturaView = facturaDao.consultarFacturaSegunReservacion(reservacion).get(0);
        reporte.generarFacturaPOS(facturaView);
    }

}
