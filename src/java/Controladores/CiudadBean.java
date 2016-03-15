/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.ICiudadDao;
import DAO.IDepartamentoDao;
import DAO.ITipoLugarDao;
import DAO.ImpCiudadDao;
import DAO.ImpDepartamentoDao;
import DAO.ImpTipoLugarDao;
import Modelo.SmsCiudad;
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

    public CiudadBean() {
        ciudadView = new SmsCiudad();
        ciudadesListView = new ArrayList<>();
        nombresCiudadesListView = new ArrayList<>();

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

    //Metodos propios
    public void seleccionarCrud(int i) {
        estado = i;
        if (estado == 1) {
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
        //Consultamos los objetos completos del departamento y el tipo de lugar para asignarlos a nuestra ciudad
        ciudadView.setSmsDepartamento(departamentoDao.consultarDepartamento(ciudadView.getSmsDepartamento()).get(0));
        ciudadView.setSmsTipoLugar(tipoLugarDao.consultarTipoLugar(ciudadView.getSmsTipoLugar()).get(0));

        //Se registra la ciudad
        ciudadDao.registrarCiudad(ciudadView);

        //Limpiamos objetos
        ciudadView = new SmsCiudad();

        //Recargamos lista de ciudades
        ciudadesListView = ciudadDao.mostrarCiudades();
    }

    public void modificar() {
        //Consultamos los objetos completos del departamento y el tipo de lugar para asignarlos a nuestra ciudad
        ciudadView.setSmsDepartamento(departamentoDao.consultarDepartamento(ciudadView.getSmsDepartamento()).get(0));
        ciudadView.setSmsTipoLugar(tipoLugarDao.consultarTipoLugar(ciudadView.getSmsTipoLugar()).get(0));

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
}
