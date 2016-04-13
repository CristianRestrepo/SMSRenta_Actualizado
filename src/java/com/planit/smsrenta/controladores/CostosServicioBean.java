/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.controladores;

import com.planit.smsrenta.dao.ICategoriaDao;
import com.planit.smsrenta.dao.ICostosServiciosDao;
import com.planit.smsrenta.dao.ILugarDao;
import com.planit.smsrenta.dao.IMercadoDao;
import com.planit.smsrenta.dao.IServicioDao;
import com.planit.smsrenta.dao.ImpCategoriaDao;
import com.planit.smsrenta.dao.ImpCostosServiciosDao;
import com.planit.smsrenta.dao.ImpLugarDao;
import com.planit.smsrenta.dao.ImpMercadoDao;
import com.planit.smsrenta.dao.ImpServicioDao;
import com.planit.smsrenta.modelos.SmsCiudad;
import com.planit.smsrenta.modelos.SmsCostosservicios;
import com.planit.smsrenta.modelos.SmsLugares;
import com.planit.smsrenta.modelos.SmsMercado;
import com.planit.smsrenta.modelos.SmsUsuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author CristianCamilo
 */
public class CostosServicioBean implements Serializable {

    //Objetos necesarios ne vista
    private SmsCostosservicios costoView;
    private SmsCiudad ciudadInicioView;
    private SmsCiudad ciudadDestinoView;
    private SmsLugares lugarInicioView;
    private SmsLugares lugarDestinoView;
    private SmsMercado mercadoView;

    private List<SmsCostosservicios> costosListView;

    //Conexion con el Dao
    ICategoriaDao catDao;
    IServicioDao serDao;
    ICostosServiciosDao cosDao;
    ILugarDao lugarDao;
    IMercadoDao mercadoDao;

    //Variables
    private int estado; //Controla la operacion a realizar
    private String nombre;
    private String buscar;

    private boolean habilitar;
    
    //Banderas    
    private boolean habilitarCancelar;

    public CostosServicioBean() {

        costoView = new SmsCostosservicios();
        ciudadInicioView = new SmsCiudad();
        ciudadDestinoView = new SmsCiudad();
        lugarInicioView = new SmsLugares();
        lugarDestinoView = new SmsLugares();
        mercadoView = new SmsMercado();

        costosListView = new ArrayList<>();

        buscar = null;
        estado = 0;

        nombre = "Registrar Costo Servicio";
        catDao = new ImpCategoriaDao();
        serDao = new ImpServicioDao();
        cosDao = new ImpCostosServiciosDao();
        mercadoDao = new ImpMercadoDao();
        lugarDao = new ImpLugarDao();

        habilitar = false;
        habilitarCancelar = true;
    }

    @PostConstruct
    public void init() {
        costosListView = cosDao.consultarCostos();
    }

    //Getters & Setters
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

    public SmsCostosservicios getCostoView() {
        return costoView;
    }

    public void setCostoView(SmsCostosservicios costoView) {
        this.costoView = costoView;
    }

    public SmsCiudad getCiudadInicioView() {
        return ciudadInicioView;
    }

    public void setCiudadInicioView(SmsCiudad ciudadInicioView) {
        this.ciudadInicioView = ciudadInicioView;
    }

    public SmsCiudad getCiudadDestinoView() {
        return ciudadDestinoView;
    }

    public void setCiudadDestinoView(SmsCiudad ciudadDestinoView) {
        this.ciudadDestinoView = ciudadDestinoView;
    }

    public List<SmsCostosservicios> getCostosListView() {
        return costosListView;
    }

    public void setCostosListView(List<SmsCostosservicios> costosListView) {
        this.costosListView = costosListView;
    }

    public boolean isHabilitar() {
        return habilitar;
    }

    public void setHabilitar(boolean habilitar) {
        this.habilitar = habilitar;
    }

    public SmsLugares getLugarInicioView() {

        return lugarInicioView;
    }

    public void setLugarInicioView(SmsLugares lugarInicioView) {
        this.lugarInicioView = lugarInicioView;
    }

    public SmsLugares getLugarDestinoView() {
        return lugarDestinoView;
    }

    public void setLugarDestinoView(SmsLugares lugarDestinoView) {
        this.lugarDestinoView = lugarDestinoView;
    }

    public SmsMercado getMercadoView() {
        return mercadoView;
    }

    public void setMercadoView(SmsMercado mercadoView) {
        this.mercadoView = mercadoView;
    }

    public boolean isHabilitarCancelar() {
        return habilitarCancelar;
    }

    public void setHabilitarCancelar(boolean habilitarCancelar) {
        this.habilitarCancelar = habilitarCancelar;
    }
    
    

    public void registrar() {
        //Consultamos la informacion completa de la categoria y el servicio elegido
        costoView.setSmsCategoria(catDao.consultarCategoria(costoView.getSmsCategoria()).get(0));
        costoView.setSmsServicios(serDao.ConsultarServicio(costoView.getSmsServicios()).get(0));

        if (lugarInicioView.getLugarNombre() != null && lugarDestinoView.getLugarNombre() != null) {
            costoView.setSmsLugaresByIdLugarInicio(lugarDao.consultarLugar(lugarInicioView).get(0));
            costoView.setSmsLugaresByIdLugarDestino(lugarDao.consultarLugar(lugarDestinoView).get(0));
        }

        //Registramos el costo
        cosDao.registrarCostoServicio(costoView);
        costosListView = cosDao.consultarCostos();//Recargamos la lista de costos

        //Limpiamos objetos
        costoView = new SmsCostosservicios();
        ciudadInicioView = new SmsCiudad();
        ciudadDestinoView = new SmsCiudad();
        mercadoView = new SmsMercado();
    }

    public void habilitarListas(String valor) {

        if (!valor.isEmpty()) {
            habilitar = true;
        } else {
            habilitar = false;
        }

    }

    public void modificar() {
        //Consultamos la informacion completa de la categoria y el servicio elegido
        costoView.setSmsCategoria(catDao.consultarCategoria(costoView.getSmsCategoria()).get(0));
        costoView.setSmsServicios(serDao.ConsultarServicio(costoView.getSmsServicios()).get(0));

        if (costoView.getSmsLugaresByIdLugarInicio() != null && costoView.getSmsLugaresByIdLugarDestino() != null) {
            costoView.setSmsLugaresByIdLugarInicio(lugarDao.consultarLugar(costoView.getSmsLugaresByIdLugarInicio()).get(0));
            costoView.setSmsLugaresByIdLugarDestino(lugarDao.consultarLugar(costoView.getSmsLugaresByIdLugarDestino()).get(0));
        }

        cosDao.modificarCostoServicio(costoView);//Modificamos el costo   
        costosListView = cosDao.consultarCostos();//Recargamos la lista de costos

        //Limpiamos objetos
        costoView = new SmsCostosservicios();
        ciudadInicioView = new SmsCiudad();
        ciudadDestinoView = new SmsCiudad();
        mercadoView = new SmsMercado();
    }

    public void filtrar() {
        costosListView = new ArrayList<>();
        if (buscar == null) {
            costosListView = cosDao.consultarCostos();
        } else {
            costosListView = cosDao.filtrarCostosServicios(buscar);
        }
    }

    public void eliminar() {
        //Eliminamos el costo
        cosDao.eliminarCostoServicio(costoView);

        //Si es asi limpiamos los objetos que contenian el costo a modificar
        costoView = new SmsCostosservicios();
        estado = 0;

        nombre = "Registrar Costo Servicio";

        costosListView = cosDao.consultarCostos();//Recargamos la lista de costos
        ciudadInicioView = new SmsCiudad();
        ciudadDestinoView = new SmsCiudad();
        mercadoView = new SmsMercado();
    }

    //Metodos Propios
    public void metodo() {
        if (estado == 0) {
            registrar();
        } else if (estado == 1) {
            modificar();
            estado = 0;
            habilitarCancelar = true;
            nombre = "Registrar Costo Servicio";
        }

    }

    public void seleccionarCRUD(int i) {
        estado = i;
        if (estado == 1) {
            nombre = "Modificar Costo Servicio";
            if (costoView.getSmsLugaresByIdLugarInicio() != null) {
                ciudadInicioView = costoView.getSmsLugaresByIdLugarInicio().getSmsCiudad();
                ciudadDestinoView = costoView.getSmsLugaresByIdLugarInicio().getSmsCiudad();
                lugarInicioView = costoView.getSmsLugaresByIdLugarInicio();
                lugarDestinoView = costoView.getSmsLugaresByIdLugarDestino();

            }
            mercadoView = costoView.getSmsServicios().getSmsMercado();
            habilitarCancelar = false;
        }
    }
    
    public void cancelar() {
        //Limpiamos objetos utilizados      
        
        habilitarCancelar = true;
        costoView = new SmsCostosservicios();
        ciudadInicioView = new SmsCiudad();
        ciudadDestinoView = new SmsCiudad();
        mercadoView = new SmsMercado();
        estado = 0;
        nombre = "Registrar Costo Servicio";
    }

}
