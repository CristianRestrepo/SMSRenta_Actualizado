/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;


import DAO.ICategoriaDao;
import DAO.ICostosServiciosRentaDao;
import DAO.IServicioDao;
import DAO.ImpCategoriaDao;
import DAO.ImpCostosServiciosRentaDao;
import DAO.ImpServicioDao;
import Modelo.SmsCategoria;
import Modelo.SmsCostosserviciosRenta;
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
    private SmsCostosserviciosRenta costoView;
    private SmsCostosserviciosRenta DCostoview;
    private List<SmsCostosserviciosRenta> costosListView;

    private SmsCategoria categoriaView;
    private SmsServicios servicioView;
  
    //Conexion con el Dao
    ICategoriaDao catDao;
    IServicioDao serDao;
    ICostosServiciosRentaDao cosDao;
    
    //Variables
    private int estado; //Controla la operacion a realizar
    private String nombre;
    private String buscar;

    public CostosServicioBean() {

        costoView = new SmsCostosserviciosRenta();
        DCostoview = new SmsCostosserviciosRenta();
        costosListView = new ArrayList<>();

        categoriaView = new SmsCategoria();
        servicioView = new SmsServicios();
    
        buscar = null;
        estado = 0;

        nombre = "Registrar Costo Servicio";
        catDao = new ImpCategoriaDao();
        serDao = new ImpServicioDao();
        cosDao = new ImpCostosServiciosRentaDao();
    }

    @PostConstruct
    public void init() {
        costosListView = new ArrayList<>();
        costosListView = cosDao.mostrarCostosServicios();
    }

    public SmsCostosserviciosRenta getCostoView() {
        return costoView;
    }

    public void setCostoView(SmsCostosserviciosRenta costoView) {
        this.costoView = costoView;
    }

    public SmsCostosserviciosRenta getDCostoview() {
        return DCostoview;
    }

    public void setDCostoview(SmsCostosserviciosRenta DCostoview) {
        this.DCostoview = DCostoview;
    }

    public List<SmsCostosserviciosRenta> getCostosListView() {
        return costosListView;
    }

    public void setCostosListView(List<SmsCostosserviciosRenta> costosListView) {
        this.costosListView = costosListView;
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

    //Metodos Crud
    public void registrar() {
        
        //Consultamos la informacion completa de la categoria y el servicio elegido
        categoriaView = catDao.consultarCategoria(categoriaView).get(0);
        servicioView = serDao.ConsultarServicio(servicioView).get(0);

        //Asignamos la categoria y el servicio a nuestro costo
        costoView.setSmsCategoria(categoriaView);
        costoView.setSmsServicios(servicioView);

        //Registramos el cosot
        cosDao.registrarCostoServicio(costoView);
        costosListView = cosDao.mostrarCostosServicios();//Recargamos la lista de costos
        
        //Limpiamos objetos
        costoView = new SmsCostosserviciosRenta();
        categoriaView = new SmsCategoria();
        servicioView = new SmsServicios();
    }

    public void modificar() {
        //Consultamos la informacion completa de la categoria y el servicio elegido
        categoriaView = catDao.consultarCategoria(categoriaView).get(0);
        servicioView = serDao.ConsultarServicio(servicioView).get(0);

        //Asignamos la categoria y el servicio a nuestro costo
        costoView.setSmsCategoria(categoriaView);
        costoView.setSmsServicios(servicioView);

        cosDao.modificarCostoServicio(costoView);//Modificamos el costo   
        costosListView = cosDao.mostrarCostosServicios();//Recargamos la lista de costos
       
        //Limpiamos objetos
        costoView = new SmsCostosserviciosRenta();
        categoriaView = new SmsCategoria();
        servicioView = new SmsServicios();
    }
    
    public void filtrar(){
       costosListView = new ArrayList<>();
        if (buscar == null) {
            costosListView = cosDao.mostrarCostosServicios();
        } else {
            costosListView = cosDao.filtrarCostosServicios(buscar);
        }
    }

    public void eliminar() {
        //Eliminamos el costo
        cosDao.eliminarCostoServicio(DCostoview);
        
        //Comprobamos que el costo a eliminar no este en proceso de modificacion
        if (costoView.equals(DCostoview)) {
            //Si es asi limpiamos los objetos que contenian el costo a modificar
            costoView = new SmsCostosserviciosRenta();
            categoriaView = new SmsCategoria();
            servicioView = new SmsServicios();
            estado = 0;

            nombre = "Registrar Costo Servicio";
        }
        //Limpiamos los objetos
        DCostoview = new SmsCostosserviciosRenta();
        costosListView = cosDao.mostrarCostosServicios();//Recargamos la lista de costos

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
            categoriaView = costoView.getSmsCategoria();
            servicioView = costoView.getSmsServicios();
        }
    }

}
