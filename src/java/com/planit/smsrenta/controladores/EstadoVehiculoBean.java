/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.controladores;

import com.planit.smsrenta.modelos.SmsEstadovehiculo;
import java.util.List;
import javax.inject.Named;
import com.planit.smsrenta.dao.IEstadoVehiculoDao;
import com.planit.smsrenta.dao.IVehiculoDao;
import com.planit.smsrenta.dao.ImpEstadoVehiculoDao;
import com.planit.smsrenta.dao.ImpVehiculoDao;
import com.planit.smsrenta.modelos.SmsVehiculo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author SISTEMAS
 */
@Named(value = "estadoVehiculoBean")

public class EstadoVehiculoBean implements Serializable{

    private SmsEstadovehiculo estVehView;
    private List<SmsEstadovehiculo> estVehiculosView;

    IEstadoVehiculoDao estVehDao;

    public EstadoVehiculoBean() {
        estVehView = new SmsEstadovehiculo();
        estVehDao = new ImpEstadoVehiculoDao();
    }

    //Getter y setters
    public SmsEstadovehiculo getEstVehView() {
        return estVehView;
    }

    public void setEstVehView(SmsEstadovehiculo estVehView) {
        this.estVehView = estVehView;
    }

    public List<SmsEstadovehiculo> getEstVehiculosView() {
        IEstadoVehiculoDao linkDao = new ImpEstadoVehiculoDao();
        estVehiculosView = linkDao.mostrarEstadoVehiculo();
        return estVehiculosView;
    }

    public void setEstVehiculosView(List<SmsEstadovehiculo> estVehiculosView) {
        this.estVehiculosView = estVehiculosView;
    }

    //Metodos
    public void registrarEstVeh(SmsEstadovehiculo estV) {
        estVehView = estV;
        IVehiculoDao vehDao = new ImpVehiculoDao();
        //Obtenemos fecha actual en la cual se registra el estado del vehiculo
        java.util.Date fecha = new Date();
        estVehView.setSmsVehiculo(vehDao.consultarVehiculo(estVehView.getSmsVehiculo()));
        estVehView.setFechaEstadoVehiculo(fecha);
        estVehDao.registrarEstadoVehiculo(estVehView);
    }

    public void modificarEstVeh(SmsEstadovehiculo estV) {
        estVehView = estV;
        estVehDao.modificarEstadoVehiculo(estVehView);
    }

    public void eliminarEstVeh(SmsEstadovehiculo estV) {
        estVehView = estV;
        estVehDao.eliminarEstadoVehiculo(estVehView);
    }

  

}
