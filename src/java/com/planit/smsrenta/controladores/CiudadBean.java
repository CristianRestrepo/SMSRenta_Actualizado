/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.controladores;

import com.planit.smsrenta.dao.ICiudadDao;
import com.planit.smsrenta.dao.IDepartamentoDao;
import com.planit.smsrenta.dao.ITipoLugarDao;
import com.planit.smsrenta.dao.ImpCiudadDao;
import com.planit.smsrenta.dao.ImpDepartamentoDao;
import com.planit.smsrenta.dao.ImpTipoLugarDao;
import com.planit.smsrenta.modelos.SmsCiudad;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author Desarrollo_Planit
 */
public class CiudadBean implements Serializable {

    //Objetos de vista
    private SmsCiudad ciudadView;
    private List<SmsCiudad> ciudadesListView;
    private List<String> nombresCiudadesListView;

    //Variables
    private int estado; //Controla la operacion a realizar
    private String nombre;
    private String buscar;

    //Conexion con DAO
    IDepartamentoDao departamentoDao;
    ITipoLugarDao tipoLugarDao;
    ICiudadDao ciudadDao;
    
     //Banderas    
    private boolean habilitarCancelar;

    public CiudadBean() {
        ciudadView = new SmsCiudad();
        ciudadesListView = new ArrayList<>();
        nombresCiudadesListView = new ArrayList<>();

        habilitarCancelar = true;
        
        buscar = null;
        estado = 0;
        nombre = "Registrar Ciudad";

        ciudadDao = new ImpCiudadDao();
        departamentoDao = new ImpDepartamentoDao();
        tipoLugarDao = new ImpTipoLugarDao();
    }

    @PostConstruct
    public void init() {
        ciudadesListView = ciudadDao.mostrarCiudades();
    }

    //Getters & Setters    
    public SmsCiudad getCiudadView() {
        return ciudadView;
    }

    public void setCiudadView(SmsCiudad ciu) {
        this.ciudadView = ciu;
    }

    public List<SmsCiudad> getCiudadesListView() {
        return ciudadesListView;
    }

    public void setCiudadesListView(List<SmsCiudad> ciudades) {
        this.ciudadesListView = ciudades;
    }

    public List<String> getNombresCiudadesListView() {
        nombresCiudadesListView = new ArrayList<>();
        ciudadesListView = ciudadDao.mostrarCiudades();
        for (int i = 0; i < ciudadesListView.size(); i++) {
            nombresCiudadesListView.add(ciudadesListView.get(i).getCiudadNombre());
        }
        return nombresCiudadesListView;
    }

    public void setNombresCiudadesListView(List<String> nombresCiudadesListView) {
        this.nombresCiudadesListView = nombresCiudadesListView;
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
    
    

    //Metodos propios
    public void seleccionarCrud(int i) {
        estado = i;
        if (estado == 1) {
            nombre = "Modificar Ciudad";
             habilitarCancelar = false;
        }
    }

    public void metodo() {
        if (estado == 0) {
            registrar();
        } else if (estado == 1) {
            modificar();
             habilitarCancelar = true;
            estado = 0;
            nombre = "Registrar Ciudad";
        }
    }

    //Definicion Metodos CRUD
    public void registrar() {
        //Consultamos los objetos completos del departamento y el tipo de lugar para asignarlos a nuestra ciudad
        ciudadView.setSmsDepartamento(departamentoDao.consultarDepartamento(ciudadView.getSmsDepartamento()));
        ciudadView.setSmsTipoLugar(tipoLugarDao.consultarTipoLugar(ciudadView.getSmsTipoLugar()));

        //Se registra la ciudad
        ciudadDao.registrarCiudad(ciudadView);

        //Limpiamos objetos
        ciudadView = new SmsCiudad();

        //Recargamos lista de ciudades
        ciudadesListView = ciudadDao.mostrarCiudades();
    }

    public void modificar() {
        //Consultamos los objetos completos del departamento y el tipo de lugar para asignarlos a nuestra ciudad
        ciudadView.setSmsDepartamento(departamentoDao.consultarDepartamento(ciudadView.getSmsDepartamento()));
        ciudadView.setSmsTipoLugar(tipoLugarDao.consultarTipoLugar(ciudadView.getSmsTipoLugar()));

        //Se modifica la ciudad
        ciudadDao.modificarCiudad(ciudadView);

        //Limpiamos objetos
        ciudadView = new SmsCiudad();

        //Recargamos lista de ciudades
        ciudadesListView = ciudadDao.mostrarCiudades();
    }

    public void eliminar() {
        ciudadDao.eliminarCiudad(ciudadView);

        nombre = "Registrar Ciudad";
        estado = 0;

        ciudadView = new SmsCiudad();
        ciudadesListView = ciudadDao.mostrarCiudades();
    }

    public void filtrar() {
        ciudadesListView = new ArrayList<>();
        if (buscar == null) {
            ciudadesListView = ciudadDao.mostrarCiudades();
        } else {
            ciudadesListView = ciudadDao.filtrarCiudad(buscar);
        }
    }
    
    public void cancelar() {
        //Limpiamos objetos utilizados
        ciudadView = new SmsCiudad();
        estado = 0;
        //Reiniciamos los objetos
        habilitarCancelar = true;
        nombre = "Registrar Ciudad";
    }
}
