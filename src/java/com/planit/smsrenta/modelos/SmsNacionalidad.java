/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.modelos;

import java.util.HashSet;
import java.util.Set;


public class SmsNacionalidad {

    private Integer idNacionalidad;
    private String nacionalidadNombre;
    private Set<SmsUsuario> smsUsuarios = new HashSet<>();

    public SmsNacionalidad() {
    }

    public SmsNacionalidad(String nacionalidadNombre) {
        this.nacionalidadNombre = nacionalidadNombre;
    }

    public SmsNacionalidad(String nacionalidadNombre, Set<SmsUsuario> smsUsuarios) {
        this.nacionalidadNombre = nacionalidadNombre;
        this.smsUsuarios = smsUsuarios;
    }
     
    
    public Integer getIdNacionalidad() {
        return idNacionalidad;
    }

    public void setIdNacionalidad(Integer idNacionalidad) {
        this.idNacionalidad = idNacionalidad;
    }

    public String getNacionalidadNombre() {
        return nacionalidadNombre;
    }

    public void setNacionalidadNombre(String nacionalidadNombre) {
        this.nacionalidadNombre = nacionalidadNombre;
    }

    public Set<SmsUsuario> getSmsUsuarios() {
        return smsUsuarios;
    }

    public void setSmsUsuarios(Set<SmsUsuario> smsUsuarios) {
        this.smsUsuarios = smsUsuarios;
    }
    
    
    
}
