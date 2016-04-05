/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.modelos;

import java.util.Date;

/**
 *
 * @author Desarrollo_Planit
 */
public class SmsFactura {

    private Integer idFactura;
    private SmsReservacion smsReservacion;
    private Date facturaFecha;
    private Date facturaFechaVencimiento;
    private double facturaIva;
    private double facturaRetFuente;
    private double facturaRetIca;
    private double facturaRetIva;
    private double facturaSubtotal;
    private double facturaTotal;
    private double facturaDescuento;
    private double facturaNeto;

    public SmsFactura() {
        this.smsReservacion = new SmsReservacion();
        this.facturaIva = 0;
        this.facturaDescuento = 0;
        this.facturaRetFuente = 0;
        this.facturaNeto = 0;
        this.facturaRetIca = 0;
        this.facturaRetIva = 0;
        this.facturaSubtotal = 0;
        this.facturaTotal = 0;
    }

    public SmsFactura(Integer idFactura, SmsReservacion smsReservacion, Date facturaFecha, Date facturaFechaVencimiento, double facturaIva, double facturaRetFuente, double facturaRetIca, double facturaRetIva, double facturaSubtotal, double facturaTotal, double facturaDescuento, double facturaNeto) {
        this.idFactura = idFactura;
        this.smsReservacion = smsReservacion;
        this.facturaFecha = facturaFecha;
        this.facturaFechaVencimiento = facturaFechaVencimiento;
        this.facturaIva = facturaIva;
        this.facturaRetFuente = facturaRetFuente;
        this.facturaRetIca = facturaRetIca;
        this.facturaRetIva = facturaRetIva;
        this.facturaSubtotal = facturaSubtotal;
        this.facturaTotal = facturaTotal;
        this.facturaDescuento = facturaDescuento;
        this.facturaNeto = facturaNeto;
    }

    public Integer getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }

    public SmsReservacion getSmsReservacion() {
        return smsReservacion;
    }

    public void setSmsReservacion(SmsReservacion smsReservacion) {
        this.smsReservacion = smsReservacion;
    }

    public Date getFacturaFecha() {
        return facturaFecha;
    }

    public void setFacturaFecha(Date facturaFecha) {
        this.facturaFecha = facturaFecha;
    }

    public Date getFacturaFechaVencimiento() {
        return facturaFechaVencimiento;
    }

    public void setFacturaFechaVencimiento(Date facturaFechaVencimiento) {
        this.facturaFechaVencimiento = facturaFechaVencimiento;
    }

    public double getFacturaIva() {
        return facturaIva;
    }

    public void setFacturaIva(double facturaIva) {
        this.facturaIva = facturaIva;
    }

    public double getFacturaRetFuente() {
        return facturaRetFuente;
    }

    public void setFacturaRetFuente(double facturaRetFuente) {
        this.facturaRetFuente = facturaRetFuente;
    }

    public double getFacturaRetIca() {
        return facturaRetIca;
    }

    public void setFacturaRetIca(double facturaRetIca) {
        this.facturaRetIca = facturaRetIca;
    }

    public double getFacturaRetIva() {
        return facturaRetIva;
    }

    public void setFacturaRetIva(double facturaRetIva) {
        this.facturaRetIva = facturaRetIva;
    }

    public double getFacturaSubtotal() {
        return facturaSubtotal;
    }

    public void setFacturaSubtotal(double facturaSubtotal) {
        this.facturaSubtotal = facturaSubtotal;
    }

    public double getFacturaTotal() {
        return facturaTotal;
    }

    public void setFacturaTotal(double facturaTotal) {
        this.facturaTotal = facturaTotal;
    }

    public double getFacturaDescuento() {
        return facturaDescuento;
    }

    public void setFacturaDescuento(double facturaDescuento) {
        this.facturaDescuento = facturaDescuento;
    }

    public double getFacturaNeto() {
        return facturaNeto;
    }

    public void setFacturaNeto(double facturaNeto) {
        this.facturaNeto = facturaNeto;
    }

    

}
