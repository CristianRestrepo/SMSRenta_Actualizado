/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Modelo.SmsEstadovehiculo;
import java.util.List;
import javax.inject.Named;
import DAO.IEstadoVehiculoDao;
import DAO.ImpEstadoVehiculoDao;
import Modelo.SmsVehiculo;
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
        //Obtenemos fecha actual en la cual se registra el estado del vehiculo
        java.util.Date fecha = new Date();
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

    public List<SmsEstadovehiculo> consultarEstado(SmsVehiculo v) {
        estVehiculosView = new ArrayList<>();
        estVehiculosView = estVehDao.consultarEstadoVehiculo(v);
        return estVehiculosView;
    }

}
