/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

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
    private int facturaIva;
    private int facturaRetFuente;
    private int facturaRetIca;
    private int facturaRetIva;
    private int facturaSubtotal;
    private int facturaTotal;
    private int facturaDescuento;
    private int facturaNeto;

    public SmsFactura() {
        this.smsReservacion = new SmsReservacion();
    }

    public SmsFactura(Integer idFactura, SmsReservacion smsReservacion, Date facturaFecha, Date facturaFechaVencimiento, int facturaIva, int facturaRetFuente, int facturaRetIca, int facturaRetIva, int facturaSubtotal, int facturaTotal, int facturaDescuento, int facturaNeto) {
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

    public int getFacturaIva() {
        return facturaIva;
    }

    public void setFacturaIva(int facturaIva) {
        this.facturaIva = facturaIva;
    }

    public int getFacturaRetFuente() {
        return facturaRetFuente;
    }

    public void setFacturaRetFuente(int facturaRetFuente) {
        this.facturaRetFuente = facturaRetFuente;
    }

    public int getFacturaRetIca() {
        return facturaRetIca;
    }

    public void setFacturaRetIca(int facturaRetIca) {
        this.facturaRetIca = facturaRetIca;
    }

    public int getFacturaRetIva() {
        return facturaRetIva;
    }

    public void setFacturaRetIva(int facturaRetIva) {
        this.facturaRetIva = facturaRetIva;
    }

    public int getFacturaSubtotal() {
        return facturaSubtotal;
    }

    public void setFacturaSubtotal(int facturaSubtotal) {
        this.facturaSubtotal = facturaSubtotal;
    }

    public int getFacturaTotal() {
        return facturaTotal;
    }

    public void setFacturaTotal(int facturaTotal) {
        this.facturaTotal = facturaTotal;
    }

    public int getFacturaDescuento() {
        return facturaDescuento;
    }

    public void setFacturaDescuento(int facturaDescuento) {
        this.facturaDescuento = facturaDescuento;
    }

    public int getFacturaNeto() {
        return facturaNeto;
    }

    public void setFacturaNeto(int facturaNeto) {
        this.facturaNeto = facturaNeto;
    }

}
