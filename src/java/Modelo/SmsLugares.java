package Modelo;
// Generated 02-mar-2016 12:47:17 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * SmsLugares generated by hbm2java
 */
public class SmsLugares  implements java.io.Serializable {


     private Integer idLugar;
     private SmsCiudad smsCiudad;
     private String lugarNombre;
     private String lugarDireccion;
     private Set<SmsCostosservicios> smsCostosserviciosesForIdLugarInicio = new HashSet<>(0);
     private Set<SmsCostosservicios> smsCostosserviciosesForIdLugarDestino = new HashSet<>(0);

    public SmsLugares() {
    }

	
    public SmsLugares(SmsCiudad smsCiudad, String lugarNombre) {
        this.smsCiudad = smsCiudad;
        this.lugarNombre = lugarNombre;
    }
    public SmsLugares(SmsCiudad smsCiudad, String lugarNombre, String lugarDireccion, Set<SmsCostosservicios> smsCostosserviciosesForIdLugarInicio, Set<SmsCostosservicios> smsCostosserviciosesForIdLugarDestino) {
       this.smsCiudad = smsCiudad;
       this.lugarNombre = lugarNombre;
       this.lugarDireccion = lugarDireccion;
       this.smsCostosserviciosesForIdLugarInicio = smsCostosserviciosesForIdLugarInicio;
       this.smsCostosserviciosesForIdLugarDestino = smsCostosserviciosesForIdLugarDestino;
    }
   
    public Integer getIdLugar() {
        return this.idLugar;
    }
    
    public void setIdLugar(Integer idLugar) {
        this.idLugar = idLugar;
    }
    public SmsCiudad getSmsCiudad() {
        return this.smsCiudad;
    }
    
    public void setSmsCiudad(SmsCiudad smsCiudad) {
        this.smsCiudad = smsCiudad;
    }
    public String getLugarNombre() {
        return this.lugarNombre;
    }
    
    public void setLugarNombre(String lugarNombre) {
        this.lugarNombre = lugarNombre;
    }
    public String getLugarDireccion() {
        return this.lugarDireccion;
    }
    
    public void setLugarDireccion(String lugarDireccion) {
        this.lugarDireccion = lugarDireccion;
    }
    public Set<SmsCostosservicios> getSmsCostosserviciosesForIdLugarInicio() {
        return this.smsCostosserviciosesForIdLugarInicio;
    }
    
    public void setSmsCostosserviciosesForIdLugarInicio(Set<SmsCostosservicios> smsCostosserviciosesForIdLugarInicio) {
        this.smsCostosserviciosesForIdLugarInicio = smsCostosserviciosesForIdLugarInicio;
    }
    public Set<SmsCostosservicios> getSmsCostosserviciosesForIdLugarDestino() {
        return this.smsCostosserviciosesForIdLugarDestino;
    }
    
    public void setSmsCostosserviciosesForIdLugarDestino(Set<SmsCostosservicios> smsCostosserviciosesForIdLugarDestino) {
        this.smsCostosserviciosesForIdLugarDestino = smsCostosserviciosesForIdLugarDestino;
    }




}


