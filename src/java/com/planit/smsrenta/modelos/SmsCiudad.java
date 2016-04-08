package com.planit.smsrenta.modelos;
// Generated 02-mar-2016 12:47:17 by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;

/**
 * SmsCiudad generated by hbm2java
 */
public class SmsCiudad implements java.io.Serializable {

    private Integer idCiudad;
    private SmsDepartamento smsDepartamento;
    private SmsTipoLugar smsTipoLugar;
    private String ciudadNombre;
    private Set<SmsLugares> smsLugareses = new HashSet<>(0);
    private Set<SmsReservacion> smsReservacionsForIdCiudadDestino = new HashSet<>(0);
    private Set<SmsVehiculo> smsVehiculos = new HashSet<>(0);
    private Set<SmsUsuario> smsUsuarios = new HashSet<>(0);
    private Set<SmsReservacion> smsReservacionsForIdCiudadInicio = new HashSet<>(0);
    private Set<SmsLocalidad> smsLocalidades = new HashSet<>(0);

    public SmsCiudad() {
        this.smsDepartamento = new SmsDepartamento();
        this.smsTipoLugar = new SmsTipoLugar();
    }

    public SmsCiudad(SmsDepartamento smsDepartamento, SmsTipoLugar smsTipoLugar, String ciudadNombre) {
        this.smsDepartamento = smsDepartamento;
        this.smsTipoLugar = smsTipoLugar;
        this.ciudadNombre = ciudadNombre;
    }

    public SmsCiudad(SmsDepartamento smsDepartamento, SmsTipoLugar smsTipoLugar, String ciudadNombre, Set<SmsLugares> smsLugareses, Set<SmsReservacion> smsReservacionsForIdCiudadDestino, Set<SmsVehiculo> smsVehiculos, Set<SmsUsuario> smsUsuarios, Set<SmsReservacion> smsReservacionsForIdCiudadInicio, Set<SmsLocalidad> smsLocalidades) {
        this.smsDepartamento = smsDepartamento;
        this.smsTipoLugar = smsTipoLugar;
        this.ciudadNombre = ciudadNombre;
        this.smsLugareses = smsLugareses;
        this.smsReservacionsForIdCiudadDestino = smsReservacionsForIdCiudadDestino;
        this.smsVehiculos = smsVehiculos;
        this.smsUsuarios = smsUsuarios;
        this.smsReservacionsForIdCiudadInicio = smsReservacionsForIdCiudadInicio;
        this.smsLocalidades = smsLocalidades;
    }

    public Integer getIdCiudad() {
        return this.idCiudad;
    }

    public void setIdCiudad(Integer idCiudad) {
        this.idCiudad = idCiudad;
    }

    public SmsDepartamento getSmsDepartamento() {
        return this.smsDepartamento;
    }

    public void setSmsDepartamento(SmsDepartamento smsDepartamento) {
        this.smsDepartamento = smsDepartamento;
    }

    public SmsTipoLugar getSmsTipoLugar() {
        return this.smsTipoLugar;
    }

    public void setSmsTipoLugar(SmsTipoLugar smsTipoLugar) {
        this.smsTipoLugar = smsTipoLugar;
    }

    public String getCiudadNombre() {
        return this.ciudadNombre;
    }

    public void setCiudadNombre(String ciudadNombre) {
        this.ciudadNombre = ciudadNombre;
    }

    public Set<SmsLugares> getSmsLugareses() {
        return this.smsLugareses;
    }

    public void setSmsLugareses(Set<SmsLugares> smsLugareses) {
        this.smsLugareses = smsLugareses;
    }

    public Set<SmsReservacion> getSmsReservacionsForIdCiudadDestino() {
        return this.smsReservacionsForIdCiudadDestino;
    }

    public void setSmsReservacionsForIdCiudadDestino(Set<SmsReservacion> smsReservacionsForIdCiudadDestino) {
        this.smsReservacionsForIdCiudadDestino = smsReservacionsForIdCiudadDestino;
    }

    public Set<SmsVehiculo> getSmsVehiculos() {
        return this.smsVehiculos;
    }

    public void setSmsVehiculos(Set<SmsVehiculo> smsVehiculos) {
        this.smsVehiculos = smsVehiculos;
    }

    public Set<SmsUsuario> getSmsUsuarios() {
        return this.smsUsuarios;
    }

    public void setSmsUsuarios(Set<SmsUsuario> smsUsuarios) {
        this.smsUsuarios = smsUsuarios;
    }

    public Set<SmsReservacion> getSmsReservacionsForIdCiudadInicio() {
        return this.smsReservacionsForIdCiudadInicio;
    }

    public void setSmsReservacionsForIdCiudadInicio(Set<SmsReservacion> smsReservacionsForIdCiudadInicio) {
        this.smsReservacionsForIdCiudadInicio = smsReservacionsForIdCiudadInicio;
    }

    public Set<SmsLocalidad> getSmsLocalidades() {
        return smsLocalidades;
    }

    public void setSmsLocalidades(Set<SmsLocalidad> smsLocalidades) {
        this.smsLocalidades = smsLocalidades;
    }
}
