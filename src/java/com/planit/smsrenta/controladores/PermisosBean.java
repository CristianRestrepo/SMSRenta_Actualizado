/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.controladores;


import com.planit.smsrenta.dao.IPermisosDao;
import com.planit.smsrenta.dao.ImpPermisosDao;
import com.planit.smsrenta.modelos.SmsPermisos;
import com.planit.smsrenta.modelos.SmsRol;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author Desarrollo_Planit
 */
public class PermisosBean implements Serializable {

    //Objetos de vista
    private List<SmsPermisos> permisosListView;
    private SmsPermisos permisoView;
    private SmsPermisos DPermisoView;
    private List<String> nombresPermisosListView;

    //Variables
    private int estado; //Controla la operacion a realizar
    private String nombre;
    private String buscar;

    //Dao
    IPermisosDao permisoDao;

    public PermisosBean() {
        permisoView = new SmsPermisos();
        DPermisoView = new SmsPermisos();
        permisosListView = new ArrayList<>();
        nombresPermisosListView = new ArrayList<>();
       
        buscar = null;
        estado = 0;
        nombre = "Registrar Permiso";

        permisoDao = new ImpPermisosDao();
    }

    @PostConstruct
    public void init() {
        permisosListView = permisoDao.mostrarPermisos();
    }

    public List<SmsPermisos> getPermisosListView() {
        return permisosListView;
    }

    public void setPermisosListView(List<SmsPermisos> permisosListView) {
        this.permisosListView = permisosListView;
    }

    public SmsPermisos getPermisoView() {
        return permisoView;
    }

    public void setPermisoView(SmsPermisos permisoView) {
        this.permisoView = permisoView;
    }

    public List<String> getNombresPermisosListView() {
        nombresPermisosListView = new ArrayList<>();
        permisosListView = permisoDao.mostrarPermisos();
        for (int i = 0; i < permisosListView.size(); i++) {
            nombresPermisosListView.add(permisosListView.get(i).getPermisosNombre());
        }
        return nombresPermisosListView;
    }

    public void setNombresPermisosListView(List<String> nombresPermisosListView) {
        this.nombresPermisosListView = nombresPermisosListView;
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

    public SmsPermisos getDPermisoView() {
        return DPermisoView;
    }

    public void setDPermisoView(SmsPermisos DPermisoView) {
        this.DPermisoView = DPermisoView;
    }

    //Definicion de metodos CRUD  
    public void registrar() {

        permisoDao.registrarPermiso(permisoView);
        permisosListView = permisoDao.mostrarPermisos();

    }

    public void modificar() {

        permisoDao.modificarPermiso(permisoView);
        permisosListView = permisoDao.mostrarPermisos();

    }

    public void eliminar() {
        permisoDao.eliminarPermiso(DPermisoView);
        if (permisoView.equals(DPermisoView)) {
            permisoView = new SmsPermisos();
            estado = 0;
            nombre = "Registrar Permiso";
        }
        DPermisoView = new SmsPermisos();
        permisosListView = permisoDao.mostrarPermisos();
    }

    public void filtrar() {
        permisosListView = new ArrayList<>();
        if (buscar == null) {
            permisosListView = permisoDao.mostrarPermisos();
        } else {
            permisosListView = filtrarPermiso(buscar);
        }
    }

    public String validarPermiso(SmsRol r, SmsPermisos p) {
        String permiso = null;
        for (SmsPermisos per : r.getSmsPermisoses()) {
            if (per.getPermisosNombre().equalsIgnoreCase(p.getPermisosNombre())) {
                permiso = per.getPermisosNombre();
            }
        }
        return permiso;
    }

    public List<SmsPermisos> filtrarPermiso(String valor) {
        permisosListView = new ArrayList<>();
        permisosListView = permisoDao.filtrarPermiso(valor);
        return permisosListView;
    }

    //Metodos propios

    public void seleccionarCrud(int i) {
        estado = i;
        if (estado == 1) {
            nombre = "Modificar Permiso";
        }
    }

    public void metodo() {
        if (estado == 0) {
            registrar();
        } else if (estado == 1) {
            modificar();
            estado = 0;
            nombre = "Registrar Permiso";
        }
    }

}
