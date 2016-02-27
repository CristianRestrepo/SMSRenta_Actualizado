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
    private SmsLugares DLugarView;
    private List<SmsLugares> LugaresListView;
    private List<String> nombresLugaresListView;
    //Relaciones con otras clases
    private SmsCiudad CiudadView;

    //Conexion con el DAO
    ICiudadDao ciudadDao;
    ILugarDao lugarDao;

    //Variables
    private int estado; //Controla la operacion a realizar
    private String nombre;
    private String buscar;

    public LugarBean() {
        LugarView = new SmsLugares();
        DLugarView = new SmsLugares();
        LugaresListView = new ArrayList<>();
        nombresLugaresListView = new ArrayList<>();
        CiudadView = new SmsCiudad();

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

    public SmsCiudad getCiudadView() {
        return CiudadView;
    }

    public void setCiudadView(SmsCiudad CiudadView) {
        this.CiudadView = CiudadView;
    }

    public List<String> getNombresLugaresListView() {
        return nombresLugaresListView;
    }

    public void setNombresLugaresListView(List<String> nombresLugaresListView) {
        this.nombresLugaresListView = nombresLugaresListView;
    }

    public SmsLugares getDLugarView() {
        return DLugarView;
    }

    public void setDLugarView(SmsLugares DLugarView) {
        this.DLugarView = DLugarView;
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

        CiudadView = ciudadDao.consultarCiudad(CiudadView).get(0);
        LugarView.setSmsCiudad(CiudadView);
        
        lugarDao.registrarLugar(LugarView);
        LugaresListView = lugarDao.consultarLugares();
        LugarView = new SmsLugares();
        CiudadView = new SmsCiudad();
    }

    public void modificar() {

        CiudadView = ciudadDao.consultarCiudad(CiudadView).get(0);
        LugarView.setSmsCiudad(CiudadView);
        
        lugarDao.modificarLugar(LugarView);
        LugaresListView = lugarDao.consultarLugares();

        LugarView = new SmsLugares();
        CiudadView = new SmsCiudad();
    }

    public void eliminar() {
        lugarDao.eliminarLugar(DLugarView);

        if (LugarView.equals(DLugarView)) {
            LugarView = new SmsLugares();
            CiudadView = new SmsCiudad();
            nombre = "Registrar Lugar";
            estado = 0;
        }
        DLugarView = new SmsLugares();
        LugaresListView = lugarDao.consultarLugares();
    }

    //Metodos propios
    public void seleccionarCrud(int i) {
        estado = i;
        if (estado == 1) {
            CiudadView.setCiudadNombre(LugarView.getSmsCiudad().getCiudadNombre());
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

    public List<SmsLugares> consultarLugaresCiudades(String Ciudad) {
        LugaresListView = new ArrayList<>();
        LugaresListView = lugarDao.consultarLugarCiudad(Ciudad); //Relacionamos el lugar con una ciudad
        return LugaresListView;
    }
}
