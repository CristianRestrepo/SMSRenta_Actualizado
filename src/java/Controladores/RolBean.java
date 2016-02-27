/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.IPermisosDao;
import DAO.IRolDao;
import DAO.ImpPermisosDao;
import DAO.ImpRolDao;
import Modelo.SmsPermisos;
import Modelo.SmsRol;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author Desarrollo_Planit
 */
public class RolBean implements Serializable{

    //Instancia de objetos necesarios
    protected List<SmsRol> rolesListView;
    protected SmsRol rolView;
    protected SmsRol DRolView;
    protected List<String> permisosSeleccionados;
    protected List<SmsPermisos> permisosListView;

    //Relacion con el controlador
    PermisosBean permisoController;

    //Dao
    IPermisosDao permisoDao;
    IRolDao rolDao;

    //Variables
    private int estado; //Controla la operacion a realizar
    private String nombre;
    private String buscar;

    public RolBean() {
        rolView = new SmsRol();
        DRolView = new SmsRol();
        rolesListView = new ArrayList<>();
        
        permisoController = new PermisosBean();
        permisosSeleccionados = new ArrayList<>();
        buscar = null;
        estado = 0;
        nombre = "Registrar Rol";

        permisoDao = new ImpPermisosDao();
        rolDao = new ImpRolDao();
    }

    @PostConstruct
    public void init() {
        rolesListView = rolDao.mostrarRoles();
    }

    //Getters & Setters
    public List<SmsRol> getRolesListView() {
        return rolesListView;
    }

    public void setRolesListView(List<SmsRol> rolesListView) {
        this.rolesListView = rolesListView;
    }

    public SmsRol getRolView() {
        return rolView;
    }

    public void setRolView(SmsRol rolView) {
        this.rolView = rolView;
    }

    public List<String> getPermisosSeleccionados() {
        return permisosSeleccionados;
    }

    public void setPermisosSeleccionados(List<String> permisosSeleccionados) {
        this.permisosSeleccionados = permisosSeleccionados;
    }

    public List<SmsPermisos> getPermisosListView() {
        return permisosListView;
    }

    public void setPermisosListView(List<SmsPermisos> permisosListView) {
        this.permisosListView = permisosListView;
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

    public SmsRol getDRolView() {
        return DRolView;
    }

    public void setDRolView(SmsRol DRolView) {
        this.DRolView = DRolView;
    }
 

    //Definicion de motodos CRUD    
    public void registrar() {        
        rolDao.registrarRol(rolView);
        rolView = new SmsRol();
        permisosSeleccionados.clear();
        rolesListView = rolDao.mostrarRoles();
    }

    public void modificar() {
        rolDao.modificarRol(rolView);
        rolView = new SmsRol();
        rolesListView = rolDao.mostrarRoles();
        permisosSeleccionados.clear();
    }

    public void eliminar() {
        rolDao.eliminarRol(DRolView);
        if (rolView.equals(DRolView)) {
            rolView = new SmsRol();
            estado = 0;
            nombre = "Registrar Rol";
        }
        DRolView = new SmsRol();
        rolesListView = rolDao.mostrarRoles();        
    }

    public void filtrar() {
        rolesListView = new ArrayList<>();
        if (buscar == null) {
            rolesListView = rolDao.mostrarRoles();
        } else {
            rolesListView = filtrarRol(buscar);
        }
    }
    
     public List<SmsRol> filtrarRol(String valor) {
        rolesListView = new ArrayList<>();
        rolesListView = rolDao.filtrarRol(valor);
        return rolesListView;

    }
   

    //Metodos propios
    public void seleccionarCrud(int i) {
        estado = i;
        permisosSeleccionados.clear();
        if (estado == 1) {
            nombre = "Modificar Rol";
            List<SmsPermisos> permisos = permisoDao.mostrarPermisos();
            List<SmsRol> rolPermisos = rolDao.consultarRol(rolView);
            String valor;
            for (int b = 0; b < permisos.size(); b++) {
                valor = permisoController.validarPermiso(rolPermisos.get(0), permisos.get(b));
                if (valor != null) {
                    permisosSeleccionados.add(valor);
                }
            }
        }
    }

    public void metodo() {
        if (estado == 0) {
            registrar();
        } else if (estado == 1) {
            modificar();
            estado = 0;
            nombre = "Registrar Rol";
        } else if (estado == 2) {
            eliminar();
            estado = 0;
            nombre = "Registrar Rol";
        }
    }
}
