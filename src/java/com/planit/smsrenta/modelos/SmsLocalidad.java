/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.modelos;

import java.util.HashSet;
import java.util.Set;

public class SmsLocalidad {

    private Integer idLocalidad;
    private String localidadNombre;
    private SmsCiudad smsCiudad;
    private Set<SmsLugares> smsLugares = new HashSet<>();

    public SmsLocalidad() {
        this.smsCiudad = new SmsCiudad();
    }

    public SmsLocalidad(String localidadNombre, SmsCiudad smsCiudad, Set<SmsLugares> smsLugares) {
        this.localidadNombre = localidadNombre;
        this.smsCiudad = smsCiudad;
        this.smsLugares = smsLugares;
    }

    public Integer getIdLocalidad() {
        return idLocalidad;
    }

    public void setIdLocalidad(Integer idLocalidad) {
        this.idLocalidad = idLocalidad;
    }

    public String getLocalidadNombre() {
        return localidadNombre;
    }

    public void setLocalidadNombre(String localidadNombre) {
        this.localidadNombre = localidadNombre;
    }

    public SmsCiudad getSmsCiudad() {
        return smsCiudad;
    }

    public void setSmsCiudad(SmsCiudad smsCiudad) {
        this.smsCiudad = smsCiudad;
    }

    public Set<SmsLugares> getSmsLugares() {
        return smsLugares;
    }

    public void setSmsLugares(Set<SmsLugares> smsLugares) {
        this.smsLugares = smsLugares;
    }

     

    
    
}
