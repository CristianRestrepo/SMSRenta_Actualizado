/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;


import DAO.ICiudadDao;
import DAO.IDepartamentoDao;
import DAO.IPaisDao;
import DAO.ImpCiudadDao;
import DAO.ImpDepartamentoDao;
import DAO.ImpPaisDao;
import Modelo.SmsCiudad;
import Modelo.SmsDepartamento;
import Modelo.SmsPais;
import Modelo.SmsTipoLugar;
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
    private SmsCiudad DCiudadView;
    private List<SmsCiudad> ciudadesListView;
    private List<String> nombresCiudadesListView;
    private SmsDepartamento departamentoView;
    private SmsTipoLugar tipoView;
    
 
    //Variables
    private int estado; //Controla la operacion a realizar
    private String nombre;
    private String buscar;

    //Conexion con DAO
    IDepartamentoDao departamentoDao;
    IPaisDao paisDao;
    ICiudadDao ciudadDao;
    
    public CiudadBean() {
        ciudadView = new SmsCiudad();
        DCiudadView = new SmsCiudad();
        ciudadesListView = new ArrayList<>();
        nombresCiudadesListView = new ArrayList<>();
        departamentoView = new SmsDepartamento();
        tipoView = new SmsTipoLugar();
        
        buscar = null;
        estado = 0;
        nombre = "Registrar Ciudad";
        
        paisDao = new ImpPaisDao();
        ciudadDao = new ImpCiudadDao();
        departamentoDao = new ImpDepartamentoDao();
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

    public SmsDepartamento getDepartamentoView() {
        return departamentoView;
    }

    public void setDepartamentoView(SmsDepartamento departamentoView) {
        this.departamentoView = departamentoView;
    }

    public SmsTipoLugar getTipoView() {
        return tipoView;
    }

    public void setTipoView(SmsTipoLugar tipoView) {
        this.tipoView = tipoView;
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

    public SmsCiudad getDCiudadView() {
        return DCiudadView;
    }

    public void setDCiudadView(SmsCiudad DCiudadView) {
        this.DCiudadView = DCiudadView;
    }

    //Metodos propios
    public void seleccionarCrud(int i) {
        estado = i;
        if (estado == 1) {
            departamentoView.setDepartamentoNombre(ciudadView.getSmsDepartamento().getDepartamentoNombre());
            tipoView.setTipoLugarNombre(ciudadView.getSmsTipoLugar().getTipoLugarNombre());
            nombre = "Modificar Ciudad";
        }
    }

    public void metodo() {
        if (estado == 0) {
            registrar();
        } else if (estado == 1) {
            modificar();
            estado = 0;
            nombre = "Registrar Ciudad";
        }
    }

    //Definicion Metodos CRUD
    public void registrar() {
        departamentoView = departamentoDao.consultarDepartamento(departamentoView).get(0);
        ciudadView.setSmsDepartamento(departamentoView);
        ciudadDao.registrarCiudad(ciudadView);
        
        ciudadView = new SmsCiudad();
        departamentoView = new SmsDepartamento();
        tipoView = new SmsTipoLugar();
        ciudadesListView = ciudadDao.mostrarCiudades();
    }

    public void modificar() {
        //COnsultamos los objetos completos del departamento y el tipo de lugar para asignarlos a nuestra ciudad
        departamentoView = departamentoDao.consultarDepartamento(departamentoView).get(0);
        ciudadView.setSmsDepartamento(departamentoView);
        
        //Se modifica la ciudad
        ciudadDao.modificarCiudad(ciudadView);
        
        //Limpiamos objetos
        ciudadView = new SmsCiudad();
        departamentoView = new SmsDepartamento();
        tipoView = new SmsTipoLugar();
        
        
        ciudadesListView = ciudadDao.mostrarCiudades();
    }

    public void eliminar() {
        ciudadDao.eliminarCiudad(DCiudadView);
        
        if(ciudadView.equals(DCiudadView)){
        ciudadView = new SmsCiudad();
        nombre = "Registrar Ciudad";
        estado = 0;
        }
        
        DCiudadView = new SmsCiudad();
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
}
