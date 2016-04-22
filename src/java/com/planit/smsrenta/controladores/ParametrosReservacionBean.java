/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.controladores;

import com.planit.smsrenta.dao.IMercadoDao;
import com.planit.smsrenta.dao.IParametrosReservacionDao;
import com.planit.smsrenta.dao.ImpMercadoDao;
import com.planit.smsrenta.dao.ImpParametrosReservacionDao;
import com.planit.smsrenta.modelos.SmsParametrosReservacion;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author Desarrollo_Planit
 */
public class ParametrosReservacionBean {

    private SmsParametrosReservacion parametroView;
    private List<SmsParametrosReservacion> parametrosListView;

    //Variables para la vista
    private int operacion;
    private String nombreOperacion;
    private String buscar;

    //Banderas    
    private boolean habilitarCancelar;

    //Conexion con los dao
    IParametrosReservacionDao parametroDao;
    IMercadoDao mercadoDao;

    public ParametrosReservacionBean() {
        parametroView = new SmsParametrosReservacion();
        parametrosListView = new ArrayList<>();

        parametroDao = new ImpParametrosReservacionDao();
        mercadoDao = new ImpMercadoDao();

        operacion = 0;
        nombreOperacion = "Registrar Valores";
        buscar = null;

        habilitarCancelar = true;
    }

    @PostConstruct
    public void init() {
        parametrosListView = parametroDao.consultarParametrosReservacion();
    }

    public SmsParametrosReservacion getParametroView() {
        return parametroView;
    }

    public void setParametroView(SmsParametrosReservacion parametroView) {
        this.parametroView = parametroView;
    }

    public List<SmsParametrosReservacion> getParametrosListView() {
        return parametrosListView;
    }

    public void setParametrosListView(List<SmsParametrosReservacion> parametrosListView) {
        this.parametrosListView = parametrosListView;
    }

    public int getOperacion() {
        return operacion;
    }

    public void setOperacion(int operacion) {
        this.operacion = operacion;
    }

    public String getNombreOperacion() {
        return nombreOperacion;
    }

    public void setNombreOperacion(String nombreOperacion) {
        this.nombreOperacion = nombreOperacion;
    }

    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }

    public boolean isHabilitarCancelar() {
        return habilitarCancelar;
    }

    public void setHabilitarCancelar(boolean habilitarCancelar) {
        this.habilitarCancelar = habilitarCancelar;
    }

    //Metodos    
    public void registrar() {
        parametroView.setSmsMercado(mercadoDao.consultarMercado(parametroView.getSmsMercado()));
        parametroDao.registrarParametroReservacion(parametroView);
        parametroView = new SmsParametrosReservacion();
        parametrosListView = parametroDao.consultarParametrosReservacion();
    }

    public void modificar() {
        parametroView.setSmsMercado(mercadoDao.consultarMercado(parametroView.getSmsMercado()));
        parametroDao.modificarParametroReservacion(parametroView);
        parametroView = new SmsParametrosReservacion();
        parametrosListView = parametroDao.consultarParametrosReservacion();
    }

    public void eliminar() {
        parametroDao.eliminarParametroReservacion(parametroView);
        parametroView = new SmsParametrosReservacion();
        operacion = 0;
        nombreOperacion = "Registrar Valores";
        parametrosListView = parametroDao.consultarParametrosReservacion();
    }

    //Metodos para la vista
    public void seleccionarCRUD(int valor) {
        operacion = valor;
        if (operacion == 1) {
            nombreOperacion = "Modificar Valores";
            habilitarCancelar = false;
        }
    }

    public void metodo() {
        if (operacion == 0) {
            registrar();
        } else if (operacion == 1) {
            modificar();
            operacion = 0;
            habilitarCancelar = true;
            nombreOperacion = "Registrar Valores";
        }
    }

    public void cancelar() {
        parametroView = new SmsParametrosReservacion();
        operacion = 0;
        nombreOperacion = "Registrar Valores";
        habilitarCancelar = true;
    }

    public void filtrar() {
        parametrosListView = new ArrayList<>();
        if (buscar == null) {
            parametrosListView = parametroDao.consultarParametrosReservacion();
        } else {
            parametrosListView = parametroDao.filtrarParametrosReservacion(buscar);
        }
    }

}
