package com.planit.smsrenta.modelos;
// Generated 02-mar-2016 12:47:17 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * SmsTipoLugar generated by hbm2java
 */
public class SmsTipoLugar  implements java.io.Serializable {


     private Integer idTipoLugar;
     private String tipoLugarNombre;
     private Set<SmsCiudad> smsCiudads = new HashSet<>(0);

    public SmsTipoLugar() {
    }

	
    public SmsTipoLugar(String tipoLugarNombre) {
        this.tipoLugarNombre = tipoLugarNombre;
    }
    public SmsTipoLugar(String tipoLugarNombre, Set<SmsCiudad> smsCiudads) {
       this.tipoLugarNombre = tipoLugarNombre;
       this.smsCiudads = smsCiudads;
    }
   
    public Integer getIdTipoLugar() {
        return this.idTipoLugar;
    }
    
    public void setIdTipoLugar(Integer idTipoLugar) {
        this.idTipoLugar = idTipoLugar;
    }
    public String getTipoLugarNombre() {
        return this.tipoLugarNombre;
    }
    
    public void setTipoLugarNombre(String tipoLugarNombre) {
        this.tipoLugarNombre = tipoLugarNombre;
    }
    public Set<SmsCiudad> getSmsCiudads() {
        return this.smsCiudads;
    }
    
    public void setSmsCiudads(Set<SmsCiudad> smsCiudads) {
        this.smsCiudads = smsCiudads;
    }




}

