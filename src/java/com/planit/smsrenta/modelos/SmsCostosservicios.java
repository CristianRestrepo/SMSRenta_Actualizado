package com.planit.smsrenta.modelos;
// Generated 02-mar-2016 12:47:17 by Hibernate Tools 4.3.1



/**
 * SmsCostosservicios generated by hbm2java
 */
public class SmsCostosservicios  implements java.io.Serializable {


     private Integer idCostosServicio;
     private SmsCategoria smsCategoria;
     private SmsLugares smsLugaresByIdLugarInicio;
     private SmsLugares smsLugaresByIdLugarDestino;
     private SmsServicios smsServicios;
     private int costoServicioPrecio;

    public SmsCostosservicios() {
        this.smsCategoria = new SmsCategoria();        
        this.smsServicios = new SmsServicios();
        this.costoServicioPrecio = 0;
    }
	
    public SmsCostosservicios(SmsCategoria smsCategoria, SmsServicios smsServicios, int costoServicioPrecio) {
        this.smsCategoria = smsCategoria;
        this.smsServicios = smsServicios;
        this.costoServicioPrecio = costoServicioPrecio;
    }
    public SmsCostosservicios(SmsCategoria smsCategoria, SmsLugares smsLugaresByIdLugarInicio, SmsLugares smsLugaresByIdLugarDestino, SmsServicios smsServicios, int costoServicioPrecio) {
       this.smsCategoria = smsCategoria;
       this.smsLugaresByIdLugarInicio = smsLugaresByIdLugarInicio;
       this.smsLugaresByIdLugarDestino = smsLugaresByIdLugarDestino;
       this.smsServicios = smsServicios;
       this.costoServicioPrecio = costoServicioPrecio;
    }
   
    public Integer getIdCostosServicio() {
        return this.idCostosServicio;
    }
    
    public void setIdCostosServicio(Integer idCostosServicio) {
        this.idCostosServicio = idCostosServicio;
    }
    public SmsCategoria getSmsCategoria() {
        return this.smsCategoria;
    }
    
    public void setSmsCategoria(SmsCategoria smsCategoria) {
        this.smsCategoria = smsCategoria;
    }
    public SmsLugares getSmsLugaresByIdLugarInicio() {
        return this.smsLugaresByIdLugarInicio;
    }
    
    public void setSmsLugaresByIdLugarInicio(SmsLugares smsLugaresByIdLugarInicio) {
        this.smsLugaresByIdLugarInicio = smsLugaresByIdLugarInicio;
    }
    public SmsLugares getSmsLugaresByIdLugarDestino() {
        return this.smsLugaresByIdLugarDestino;
    }
    
    public void setSmsLugaresByIdLugarDestino(SmsLugares smsLugaresByIdLugarDestino) {
        this.smsLugaresByIdLugarDestino = smsLugaresByIdLugarDestino;
    }
    public SmsServicios getSmsServicios() {
        return this.smsServicios;
    }
    
    public void setSmsServicios(SmsServicios smsServicios) {
        this.smsServicios = smsServicios;
    }
    public int getCostoServicioPrecio() {
        return this.costoServicioPrecio;
    }
    
    public void setCostoServicioPrecio(int costoServicioPrecio) {
        this.costoServicioPrecio = costoServicioPrecio;
    }




}


