/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.ICategoriaDao;
import DAO.ICostosServiciosDao;
import DAO.ILugarDao;
import DAO.IServicioDao;
import DAO.ImpCategoriaDao;
import DAO.ImpCostosServiciosDao;
import DAO.ImpLugarDao;
import DAO.ImpServicioDao;
import Modelo.SmsCiudad;
import Modelo.SmsCostosservicios;
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
    private SmsCiudad ciudadView;

    private List<SmsCostosservicios> costosListView;

    //Conexion con el Dao
    ICategoriaDao catDao;
    IServicioDao serDao;
    ICostosServiciosDao cosDao;
    ILugarDao lugarDao;

    //Variables
    private int estado; //Controla la operacion a realizar
    private String nombre;
    private String buscar;

    private boolean habilitar;
    public CostosServicioBean() {

        costoView = new SmsCostosservicios();
        ciudadView = new SmsCiudad();

        costosListView = new ArrayList<>();
        lugarDao = new ImpLugarDao();

        buscar = null;
        estado = 0;

        nombre = "Registrar Costo Servicio";
        catDao = new ImpCategoriaDao();
        serDao = new ImpServicioDao();
        cosDao = new ImpCostosServiciosDao();
        
        habilitar = false;
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

    public SmsCiudad getCiudadView() {
        return ciudadView;
    }

    public void setCiudadView(SmsCiudad ciudadView) {
        this.ciudadView = ciudadView;
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

    
    //Metodos Crud
    public void registrar() {

        //Consultamos la informacion completa de la categoria y el servicio elegido
        costoView.setSmsCategoria(catDao.consultarCategoria(costoView.getSmsCategoria()).get(0));
        costoView.setSmsServicios(serDao.ConsultarServicio(costoView.getSmsServicios()).get(0));

        if (costoView.getSmsLugaresByIdLugarInicio().getLugarNombre() != null && costoView.getSmsLugaresByIdLugarDestino().getLugarNombre() != null) {
            costoView.setSmsLugaresByIdLugarInicio(lugarDao.consultarLugar(costoView.getSmsLugaresByIdLugarInicio()).get(0));
            costoView.setSmsLugaresByIdLugarDestino(lugarDao.consultarLugar(costoView.getSmsLugaresByIdLugarDestino()).get(0));
        }

        //Registramos el costo
        cosDao.registrarCostoServicio(costoView);
        costosListView = cosDao.consultarCostos();//Recargamos la lista de costos

        //Limpiamos objetos
        costoView = new SmsCostosservicios();
    }
    
    public void habilitarListas(String valor){
        
        if(!valor.isEmpty()){
        habilitar = true;
        }else{
        habilitar = false;
        }
    
    }

    public void modificar() {
        //Consultamos la informacion completa de la categoria y el servicio elegido
        costoView.setSmsCategoria(catDao.consultarCategoria(costoView.getSmsCategoria()).get(0));
        costoView.setSmsServicios(serDao.ConsultarServicio(costoView.getSmsServicios()).get(0));

        if (costoView.getSmsLugaresByIdLugarInicio().getLugarNombre() != null && costoView.getSmsLugaresByIdLugarDestino().getLugarNombre() != null) {
            costoView.setSmsLugaresByIdLugarInicio(lugarDao.consultarLugar(costoView.getSmsLugaresByIdLugarInicio()).get(0));
            costoView.setSmsLugaresByIdLugarDestino(lugarDao.consultarLugar(costoView.getSmsLugaresByIdLugarDestino()).get(0));
        }
        
        cosDao.modificarCostoServicio(costoView);//Modificamos el costo   
        costosListView = cosDao.consultarCostos();//Recargamos la lista de costos

        //Limpiamos objetos
        costoView = new SmsCostosservicios();
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
    }

    //Metodos Propios
    public void metodo() {
        if (estado == 0) {
            registrar();
        } else if (estado == 1) {
            modificar();
            estado = 0;
            nombre = "Registrar Costo Servicio";
        }

    }

    public void seleccionarCRUD(int i) {
        estado = i;
        if (estado == 1) {
            nombre = "Modificar Costo Servicio";
        }
    }

}
