/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.modelos;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Desarrollo_Planit
 */
public class SmsColor {
    
    private Integer idColor;
    private String colorNombre;
    private Set<SmsVehiculo> smsVehiculos = new HashSet<>(0);

    public SmsColor() {
    }

    public SmsColor(String colorNombre) {
        this.colorNombre = colorNombre;
    }
    
    public SmsColor(Set<SmsVehiculo> smsVehiculos){
        this.smsVehiculos = smsVehiculos;
    }

    public Integer getIdColor() {
        return idColor;
    }

    public void setIdColor(Integer idColor) {
        this.idColor = idColor;
    }

    public String getColorNombre() {
        return colorNombre;
    }

    public void setColorNombre(String colorNombre) {
        this.colorNombre = colorNombre;
    }

    public Set<SmsVehiculo> getSmsVehiculos() {
        return smsVehiculos;
    }

    public void setSmsVehiculos(Set<SmsVehiculo> smsVehiculos) {
        this.smsVehiculos = smsVehiculos;
    }
    
    
}
