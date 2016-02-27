/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;


import DAO.ICiudadDao;
import DAO.IPaisDao;
import DAO.ImpCiudadDao;
import DAO.ImpPaisDao;
import Modelo.SmsCiudad;
import Modelo.SmsPais;
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
    protected SmsPais paisView;
 
    //Variables
    private int estado; //Controla la operacion a realizar
    private String nombre;
    private String buscar;

    //Conexion con DAO
    IPaisDao paisDao;
    ICiudadDao ciudadDao;
    
    public CiudadBean() {
        ciudadView = new SmsCiudad();
        DCiudadView = new SmsCiudad();
        ciudadesListView = new ArrayList<>();
        nombresCiudadesListView = new ArrayList<>();
        paisView = new SmsPais();

        buscar = null;
        estado = 0;
        nombre = "Registrar Ciudad";
        
        paisDao = new ImpPaisDao();
        ciudadDao = new ImpCiudadDao();
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

    public SmsPais getPaisView() {
        return paisView;
    }

    public void setPaisView(SmsPais paisView) {
        this.paisView = paisView;
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
            paisView.setPaisNombre(ciudadView.getSmsPais().getPaisNombre());
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
        paisView = paisDao.consultarPais(paisView).get(0);
        ciudadView.setSmsPais(paisView);
        ciudadDao.registrarCiudad(ciudadView);
        
        ciudadView = new SmsCiudad();
        paisView = new SmsPais();
        ciudadesListView = ciudadDao.mostrarCiudades();
    }

    public void modificar() {
        paisView = paisDao.consultarPais(paisView).get(0);
        ciudadView.setSmsPais(paisView);
        
        ciudadDao.modificarCiudad(ciudadView);
        
        ciudadView = new SmsCiudad();
        paisView = new SmsPais();
        
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
