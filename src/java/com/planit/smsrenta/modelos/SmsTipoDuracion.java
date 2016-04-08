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
public class SmsTipoDuracion {

    private Integer idTipoDuracion;
    private String tipoDuracionNombre;
    private Set<SmsServicios> smsServicios = new HashSet<>(0);

    public SmsTipoDuracion() {
    }

    public SmsTipoDuracion(Integer idTipoDuracion, String tipoDuracionNombre, Set<SmsServicios> smsServicios) {
        this.idTipoDuracion = idTipoDuracion;
        this.tipoDuracionNombre = tipoDuracionNombre;
        this.smsServicios = smsServicios;
    }

    public Integer getIdTipoDuracion() {
        return idTipoDuracion;
    }

    public void setIdTipoDuracion(Integer idTipoDuracion) {
        this.idTipoDuracion = idTipoDuracion;
    }

    public String getTipoDuracionNombre() {
        return tipoDuracionNombre;
    }

    public void setTipoDuracionNombre(String tipoDuracionNombre) {
        this.tipoDuracionNombre = tipoDuracionNombre;
    }

    public Set<SmsServicios> getSmsServicios() {
        return smsServicios;
    }

    public void setSmsServicios(Set<SmsServicios> smsServicios) {
        this.smsServicios = smsServicios;
    }
}
