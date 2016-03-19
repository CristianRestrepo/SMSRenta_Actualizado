/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.ICiudadDao;
import DAO.ILugarDao;
import DAO.ImpCiudadDao;
import DAO.ImpLugarDao;
import Modelo.SmsCiudad;
import Modelo.SmsLugares;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author Desarrollo_Planit
 */
public class LugarBean implements Serializable {

    //Objetos necesarios en vista
    private SmsLugares LugarView;
    private List<SmsLugares> LugaresListView;
    private List<String> nombresLugaresListView;

    //Conexion con el DAO
    ICiudadDao ciudadDao;
    ILugarDao lugarDao;

    //Variables
    private int estado; //Controla la operacion a realizar
    private String nombre;
    private String buscar;

    public LugarBean() {
        LugarView = new SmsLugares();
        LugaresListView = new ArrayList<>();
        nombresLugaresListView = new ArrayList<>();

        buscar = null;
        estado = 0;
        nombre = "Registrar Lugar";

        ciudadDao = new ImpCiudadDao();
        lugarDao = new ImpLugarDao();
    }

    @PostConstruct
    public void init() {
        LugaresListView = new ArrayList<>();
        LugaresListView = lugarDao.consultarLugares();
    }

    public SmsLugares getLugarView() {
        return LugarView;
    }

    public void setLugarView(SmsLugares LugarView) {
        this.LugarView = LugarView;
    }

    public List<SmsLugares> getLugaresListView() {
        return LugaresListView;
    }

    public void setLugaresListView(List<SmsLugares> LugaresListView) {
        this.LugaresListView = LugaresListView;
    }

    public List<String> getNombresLugaresListView() {
        LugaresListView = new ArrayList<>();
        nombresLugaresListView = new ArrayList<>();
        
        LugaresListView = lugarDao.consultarLugares();
        for (int i = 0; i < LugaresListView.size(); i++) {
            nombresLugaresListView.add(LugaresListView.get(i).getLugarNombre());
        }
        
        
        return nombresLugaresListView;
    }

    public void setNombresLugaresListView(List<String> nombresLugaresListView) {
        this.nombresLugaresListView = nombresLugaresListView;
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

    //Metodos CRUD
    public void registrar() {

        LugarView.setSmsCiudad(ciudadDao.consultarCiudad(LugarView.getSmsCiudad()).get(0));

        lugarDao.registrarLugar(LugarView);
        LugaresListView = lugarDao.consultarLugares();
        LugarView = new SmsLugares();
    }

    public void modificar() {

        LugarView.setSmsCiudad(ciudadDao.consultarCiudad(LugarView.getSmsCiudad()).get(0));

        lugarDao.modificarLugar(LugarView);
        LugaresListView = lugarDao.consultarLugares();

        LugarView = new SmsLugares();
    }

    public void eliminar() {
        lugarDao.eliminarLugar(LugarView);

        LugarView = new SmsLugares();
        nombre = "Registrar Lugar";
        estado = 0;
        LugaresListView = lugarDao.consultarLugares();
    }

    //Metodos propios
    public void seleccionarCrud(int i) {
        estado = i;
        if (estado == 1) {
            nombre = "Modificar Lugar";
        }
    }

    public void metodo() {
        if (estado == 0) {
            registrar();
        } else if (estado == 1) {
            modificar();
            estado = 0;
            nombre = "Registrar Lugar";
        }
    }

    public void filtrar() {
        LugaresListView = new ArrayList<>();
        if (buscar == null) {
            LugaresListView = lugarDao.consultarLugares();
        } else {
            LugaresListView = lugarDao.filtrarLugar(buscar);
        }
    }

    public List<String> consultarLugaresCiudades(SmsCiudad Ciudad) {
        LugaresListView = new ArrayList<>();
        nombresLugaresListView = new ArrayList<>();
        LugaresListView = lugarDao.consultarLugarCiudad(Ciudad.getCiudadNombre()); //Relacionamos el lugar con una ciudad
        for (int i = 0; i < LugaresListView.size(); i++) {
            nombresLugaresListView.add(LugaresListView.get(i).getLugarNombre());
        }
        return nombresLugaresListView;
    }
}
