/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.controladores;

import com.planit.smsrenta.dao.ICategoriasServicioDao;
import com.planit.smsrenta.dao.IMercadoDao;
import com.planit.smsrenta.dao.IServicioDao;
import com.planit.smsrenta.dao.ITipoDuracionDao;
import com.planit.smsrenta.dao.ImpCategoriasServicioDao;
import com.planit.smsrenta.dao.ImpMercadoDao;
import com.planit.smsrenta.dao.ImpServicioDao;
import com.planit.smsrenta.dao.ImpTipoDuracionDao;
import com.planit.smsrenta.modelos.SmsMercado;
import com.planit.smsrenta.modelos.SmsServicios;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author Desarrollo_Planit
 */
public class ServiciosBean implements Serializable {

    //Objetos necesarios
    private SmsServicios servicioView;

    private List<SmsServicios> serviciosListView;
    private List<String> nombreServiciosListView;

    //Banderas    
    private boolean habilitarCancelar;

    //Conexion con el DAO
    IServicioDao servicioDao;
    ICategoriasServicioDao catServicioDao;
    IMercadoDao mercadoDao;
    ITipoDuracionDao tipoDuracionDao;

    //Variables
    private int estado; //Controla la operacion a realizar
    private String nombre;
    private String buscar;

    public ServiciosBean() {
        servicioView = new SmsServicios();
        serviciosListView = new ArrayList<>();
        nombreServiciosListView = new ArrayList<>();

        habilitarCancelar = true;

        buscar = null;
        estado = 0;
        nombre = "Registrar Servicio";

        servicioDao = new ImpServicioDao();
        catServicioDao = new ImpCategoriasServicioDao();
        mercadoDao = new ImpMercadoDao();
        tipoDuracionDao = new ImpTipoDuracionDao();
    }

    @PostConstruct
    public void init() {
        serviciosListView = servicioDao.mostrarServicios();
    }

    //Getters & Setters
    public SmsServicios getServicioView() {
        return servicioView;
    }

    public void setServicioView(SmsServicios servicioView) {
        this.servicioView = servicioView;
    }

    public List<SmsServicios> getServiciosListView() {
        return serviciosListView;
    }

    public void setServiciosListView(List<SmsServicios> serviciosListView) {
        this.serviciosListView = serviciosListView;
    }

    public List<String> getNombreServiciosListView() {
        nombreServiciosListView = new ArrayList<>();
        serviciosListView = new ArrayList<>();
        serviciosListView = servicioDao.mostrarServicios();
        for (int i = 0; i < serviciosListView.size(); i++) {
            nombreServiciosListView.add(serviciosListView.get(i).getServicioNombre());
        }
        return nombreServiciosListView;
    }

    public void setNombreServiciosListView(List<String> nombreServiciosListView) {
        this.nombreServiciosListView = nombreServiciosListView;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    //Metodos Propios
    public void metodo() {
        if (estado == 0) {
            registrar();
        } else if (estado == 1) {
            modificar();
            habilitarCancelar = true;
            estado = 0;
            nombre = "Registrar Servicio";
        }

    }

    public void seleccionarCRUD(int i) {
        estado = i;
        if (estado == 1) {
            habilitarCancelar = false;
            nombre = "Modificar Servicio";
        }
    }

    //Metodos  
    public void registrar() {
        //Se consulta la informacion de la categoria del servicio seleccionado
        servicioView.setSmsMercado(mercadoDao.consultarMercadoConCategorias(servicioView.getSmsMercado()));
        servicioView.setSmsCategoriasServicio(catServicioDao.consultarCategoriaServicio(servicioView.getSmsCategoriasServicio()));
        servicioView.setSmsTipoDuracion(tipoDuracionDao.consultarTipoDuracion(servicioView.getSmsTipoDuracion()));
        servicioDao.registrarServicio(servicioView);

        servicioView = new SmsServicios();
        serviciosListView = servicioDao.mostrarServicios();
    }

    public void modificar() {
        servicioView.setSmsMercado(mercadoDao.consultarMercadoConCategorias(servicioView.getSmsMercado()));
        servicioView.setSmsCategoriasServicio(catServicioDao.consultarCategoriaServicio(servicioView.getSmsCategoriasServicio()));
        servicioView.setSmsTipoDuracion(tipoDuracionDao.consultarTipoDuracion(servicioView.getSmsTipoDuracion()));
        servicioDao.modificarServicio(servicioView);

        servicioView = new SmsServicios();
        serviciosListView = servicioDao.mostrarServicios();
    }

    public void eliminar() {
        servicioDao.eliminarServicio(servicioView);
        nombre = "Registrar Servicio";
        estado = 0;
        servicioView = new SmsServicios();
        serviciosListView = servicioDao.mostrarServicios();
    }

    public void filtrar() {
        serviciosListView = new ArrayList<>();
        if (buscar == null) {
            serviciosListView = servicioDao.mostrarServicios();
        } else {
            serviciosListView = servicioDao.filtrarServicios(buscar);
        }
    }

    public List<String> seleccionarServiciosSegunMercado(SmsMercado mercado) {
        nombreServiciosListView = new ArrayList<>();
        serviciosListView = servicioDao.ConsultarServicioSegunMercado(mercado);
        for (int i = 0; i < serviciosListView.size(); i++) {
            nombreServiciosListView.add(serviciosListView.get(i).getServicioNombre());
        }
        return nombreServiciosListView;
    }
    
    public void cancelar() {
        //Limpiamos objetos utilizados
        servicioView = new SmsServicios();
        estado = 0;
        //Reiniciamos los objetos
        habilitarCancelar = true;
        nombre = "Registrar Servicio";
    }
}
