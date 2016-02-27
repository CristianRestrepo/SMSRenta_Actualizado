/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;


import DAO.ICategoriaDao;
import DAO.ICostosServiciosRentaDao;
import DAO.ICostosServiciosTiempoDao;
import DAO.ICostosServiciosTrasladoDao;
import DAO.IServicioDao;
import DAO.ImpCategoriaDao;
import DAO.ImpCostosServiciosRentaDao;
import DAO.ImpCostosServiciosTiempoDao;
import DAO.ImpCostosServiciosTrasladoDao;
import DAO.ImpServicioDao;
import Modelo.SmsCategoria;
import Modelo.SmsCostoserviciosTiempo;
import Modelo.SmsCostoserviciosTraslado;
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
    private SmsCostosserviciosRenta costoRentaView;
    private SmsCostoserviciosTraslado costoTrasladoView;
    private SmsCostoserviciosTiempo costoTiempoView;
    
    private SmsCostosserviciosRenta DCostoview;
    private List<SmsCostosserviciosRenta> costosRentaListView;
    private List<SmsCostoserviciosTraslado> costosTrasladoListView;
    private List<SmsCostoserviciosTiempo> costosTiempoListView;

    private SmsCategoria categoriaView;
    private SmsServicios servicioView;
  
    //Conexion con el Dao
    ICategoriaDao catDao;
    IServicioDao serDao;
    ICostosServiciosRentaDao cosRentaDao;
    ICostosServiciosTrasladoDao cosTrasladoDao;
    ICostosServiciosTiempoDao cosTiempoDao;
    
    //Variables
    private int estado; //Controla la operacion a realizar
    private String nombre;
    private String buscar;

    public CostosServicioBean() {

        costoRentaView = new SmsCostosserviciosRenta();
        costoTiempoView = new SmsCostoserviciosTiempo();
        costoTrasladoView = new SmsCostoserviciosTraslado();
        
        DCostoview = new SmsCostosserviciosRenta();
        
        costosRentaListView = new ArrayList<>();
        costosTiempoListView = new ArrayList<>();
        costosTrasladoListView = new ArrayList<>();
        
        categoriaView = new SmsCategoria();
        servicioView = new SmsServicios();
    
        buscar = null;
        estado = 0;

        nombre = "Registrar Costo Servicio";
        catDao = new ImpCategoriaDao();
        serDao = new ImpServicioDao();
        cosRentaDao = new ImpCostosServiciosRentaDao();
        cosTrasladoDao = new ImpCostosServiciosTrasladoDao();
        cosTiempoDao = new ImpCostosServiciosTiempoDao();
    }

    @PostConstruct
    public void init() {
        costosRentaListView = cosRentaDao.consultarCostos();
        costosTiempoListView = cosTiempoDao.consultarCostos();
        costosTrasladoListView = cosTrasladoDao.consultarCostos();
    }
  
    //Getters & Setters
    public SmsCostosserviciosRenta getDCostoview() {
        return DCostoview;
    }

    public void setDCostoview(SmsCostosserviciosRenta DCostoview) {
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

    public SmsCostosserviciosRenta getCostoRentaView() {
        return costoRentaView;
    }

    public void setCostoRentaView(SmsCostosserviciosRenta costoRentaView) {
        this.costoRentaView = costoRentaView;
    }

    public SmsCostoserviciosTraslado getCostoTrasladoView() {
        return costoTrasladoView;
    }

    public void setCostoTrasladoView(SmsCostoserviciosTraslado costoTrasladoView) {
        this.costoTrasladoView = costoTrasladoView;
    }

    public SmsCostoserviciosTiempo getCostoTiempoView() {
        return costoTiempoView;
    }

    public void setCostoTiempoView(SmsCostoserviciosTiempo costoTiempoView) {
        this.costoTiempoView = costoTiempoView;
    }

    public List<SmsCostosserviciosRenta> getCostosRentaListView() {
        return costosRentaListView;
    }

    public void setCostosRentaListView(List<SmsCostosserviciosRenta> costosRentaListView) {
        this.costosRentaListView = costosRentaListView;
    }

    public List<SmsCostoserviciosTraslado> getCostosTrasladoListView() {
        return costosTrasladoListView;
    }

    public void setCostosTrasladoListView(List<SmsCostoserviciosTraslado> costosTrasladoListView) {
        this.costosTrasladoListView = costosTrasladoListView;
    }

    public List<SmsCostoserviciosTiempo> getCostosTiempoListView() {
        return costosTiempoListView;
    }

    public void setCostosTiempoListView(List<SmsCostoserviciosTiempo> costosTiempoListView) {
        this.costosTiempoListView = costosTiempoListView;
    }
        

    //Metodos Crud
    public void registrarCostoRenta() {
        
        //Consultamos la informacion completa de la categoria y el servicio elegido
        categoriaView = catDao.consultarCategoria(categoriaView).get(0);
        servicioView = serDao.ConsultarServicio(servicioView).get(0);

        //Asignamos la categoria y el servicio a nuestro costo
        costoRentaView.setSmsCategoria(categoriaView);
        costoRentaView.setSmsServicios(servicioView);

        //Registramos el costo
        cosRentaDao.registrarCostoServicio(costoRentaView);
        costosRentaListView = cosRentaDao.consultarCostos();//Recargamos la lista de costos
        
        //Limpiamos objetos
        costoRentaView = new SmsCostosserviciosRenta();
        categoriaView = new SmsCategoria();
        servicioView = new SmsServicios();
    }
    
    public void registrarCostoTiempo(){
        
    }
    
    public void registrarCostoTraslado(){
    
    }

    public void modificarCostoRenta() {
        //Consultamos la informacion completa de la categoria y el servicio elegido
        categoriaView = catDao.consultarCategoria(categoriaView).get(0);
        servicioView = serDao.ConsultarServicio(servicioView).get(0);

        //Asignamos la categoria y el servicio a nuestro costo
        costoRentaView.setSmsCategoria(categoriaView);
        costoRentaView.setSmsServicios(servicioView);

        cosRentaDao.modificarCostoServicio(costoRentaView);//Modificamos el costo   
        costosRentaListView = cosRentaDao.consultarCostos();//Recargamos la lista de costos
       
        //Limpiamos objetos
        costoRentaView = new SmsCostosserviciosRenta();
        categoriaView = new SmsCategoria();
        servicioView = new SmsServicios();
    }
    
    public void modificarCostoTraslado(){       
        
    }
   
    public void modificarCostoTiempo(){
    
    }
    
    public void filtrar(){
       costosRentaListView = new ArrayList<>();
        if (buscar == null) {
            costosRentaListView = cosRentaDao.consultarCostos();
        } else {
            costosRentaListView = cosRentaDao.filtrarCostosServicios(buscar);
        }
    }

    public void eliminarCostoRenta() {
        //Eliminamos el costo
        cosRentaDao.eliminarCostoServicio(DCostoview);
        
        //Comprobamos que el costo a eliminar no este en proceso de modificacion
        if (costoRentaView.equals(DCostoview)) {
            //Si es asi limpiamos los objetos que contenian el costo a modificar
            costoRentaView = new SmsCostosserviciosRenta();
            categoriaView = new SmsCategoria();
            servicioView = new SmsServicios();
            estado = 0;

            nombre = "Registrar Costo Servicio";
        }
        //Limpiamos los objetos
        DCostoview = new SmsCostosserviciosRenta();
        costosRentaListView = cosRentaDao.consultarCostos();//Recargamos la lista de costos
    }
    
    public void eliminarCostoTraslado(){
    
    }

    public void eliminarCostoTiempo(){
    
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
            categoriaView = costoRentaView.getSmsCategoria();
            servicioView = costoRentaView.getSmsServicios();
        }
    }

}
