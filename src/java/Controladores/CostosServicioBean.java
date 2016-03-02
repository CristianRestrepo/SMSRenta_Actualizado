/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.ICategoriaDao;
import DAO.ICostosServiciosDao;
import DAO.IServicioDao;
import DAO.ImpCategoriaDao;
import DAO.ImpCostosServiciosDao;
import DAO.ImpServicioDao;
import Modelo.SmsCategoria;
import Modelo.SmsCostosservicios;
import Modelo.SmsServicios;
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

    private SmsCostosservicios DCostoview;
    private List<SmsCostosservicios> costosRentaListView;

    private SmsCategoria categoriaView;
    private SmsServicios servicioView;

    //Conexion con el Dao
    ICategoriaDao catDao;
    IServicioDao serDao;
    ICostosServiciosDao cosDao;

    //Variables
    private int estado; //Controla la operacion a realizar
    private String nombre;
    private String buscar;

    public CostosServicioBean() {

        costoView = new SmsCostosservicios();
        DCostoview = new SmsCostosservicios();

        costosRentaListView = new ArrayList<>();

        categoriaView = new SmsCategoria();
        servicioView = new SmsServicios();

        buscar = null;
        estado = 0;

        nombre = "Registrar Costo Servicio";
        catDao = new ImpCategoriaDao();
        serDao = new ImpServicioDao();
        cosDao = new ImpCostosServiciosDao();

    }

    @PostConstruct
    public void init() {
        costosRentaListView = cosDao.consultarCostos();
    }

    //Getters & Setters
    public SmsCostosservicios getDCostoview() {
        return DCostoview;
    }

    public void setDCostoview(SmsCostosservicios DCostoview) {
        this.DCostoview = DCostoview;
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

    public SmsCategoria getCategoriaView() {
        return categoriaView;
    }

    public void setCategoriaView(SmsCategoria categoriaView) {
        this.categoriaView = categoriaView;
    }

    public SmsServicios getServicioView() {
        return servicioView;
    }

    public void setServicioView(SmsServicios servicioView) {
        this.servicioView = servicioView;
    }

    public SmsCostosservicios getCostoView() {
        return costoView;
    }

    public void setCostoView(SmsCostosservicios costoView) {
        this.costoView = costoView;
    }

    public List<SmsCostosservicios> getCostosRentaListView() {
        return costosRentaListView;
    }

    public void setCostosRentaListView(List<SmsCostosservicios> costosRentaListView) {
        this.costosRentaListView = costosRentaListView;
    }

    
    //Metodos Crud
    public void registrarCostoRenta() {

        //Consultamos la informacion completa de la categoria y el servicio elegido
        categoriaView = catDao.consultarCategoria(categoriaView).get(0);
        servicioView = serDao.ConsultarServicio(servicioView).get(0);

        //Asignamos la categoria y el servicio a nuestro costo
        costoView.setSmsCategoria(categoriaView);
        costoView.setSmsServicios(servicioView);

        //Registramos el costo
        cosDao.registrarCostoServicio(costoView);
        costosRentaListView = cosDao.consultarCostos();//Recargamos la lista de costos

        //Limpiamos objetos
        costoView = new SmsCostosservicios();
        categoriaView = new SmsCategoria();
        servicioView = new SmsServicios();
    }

    public void registrarCostoTiempo() {

    }

    public void registrarCostoTraslado() {

    }

    public void modificarCostoRenta() {
        //Consultamos la informacion completa de la categoria y el servicio elegido
        categoriaView = catDao.consultarCategoria(categoriaView).get(0);
        servicioView = serDao.ConsultarServicio(servicioView).get(0);

        //Asignamos la categoria y el servicio a nuestro costo
        costoView.setSmsCategoria(categoriaView);
        costoView.setSmsServicios(servicioView);

        cosDao.modificarCostoServicio(costoView);//Modificamos el costo   
        costosRentaListView = cosDao.consultarCostos();//Recargamos la lista de costos

        //Limpiamos objetos
        costoView = new SmsCostosservicios();
        categoriaView = new SmsCategoria();
        servicioView = new SmsServicios();
    }

    public void modificarCostoTraslado() {

    }

    public void modificarCostoTiempo() {

    }

    public void filtrar() {
        costosRentaListView = new ArrayList<>();
        if (buscar == null) {
            costosRentaListView = cosDao.consultarCostos();
        } else {
            costosRentaListView = cosDao.filtrarCostosServicios(buscar);
        }
    }

    public void eliminarCostoRenta() {
        //Eliminamos el costo
        cosDao.eliminarCostoServicio(DCostoview);

        //Comprobamos que el costo a eliminar no este en proceso de modificacion
        if (costoView.equals(DCostoview)) {
            //Si es asi limpiamos los objetos que contenian el costo a modificar
            costoView = new SmsCostosservicios();
            categoriaView = new SmsCategoria();
            servicioView = new SmsServicios();
            estado = 0;

            nombre = "Registrar Costo Servicio";
        }
        //Limpiamos los objetos
        DCostoview = new SmsCostosservicios();
        costosRentaListView = cosDao.consultarCostos();//Recargamos la lista de costos
    }

    public void eliminarCostoTraslado() {

    }

    public void eliminarCostoTiempo() {

    }

    //Metodos Propios
    public void metodo() {
        if (estado == 0) {
            registrarCostoRenta();
        } else if (estado == 1) {
            modificarCostoRenta();
            estado = 0;
            nombre = "Registrar Costo Servicio";
        }

    }

    public void seleccionarCRUD(int i) {
        estado = i;
        if (estado == 1) {
            nombre = "Modificar Costo Servicio";
            categoriaView = costoView.getSmsCategoria();
            servicioView = costoView.getSmsServicios();
        }
    }

}
