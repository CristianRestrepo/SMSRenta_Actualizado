/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.ICategoriaDao;
import DAO.ICategoriasServicioDao;
import DAO.ICostosServiciosDao;
import DAO.ILugarDao;
import DAO.IServicioDao;
import DAO.ImpCategoriaDao;
import DAO.ImpCategoriasServicioDao;
import DAO.ImpCostosServiciosDao;
import DAO.ImpLugarDao;
import DAO.ImpServicioDao;
import Modelo.SmsCategoria;
import Modelo.SmsCostosservicios;
import Modelo.SmsMercado;
import Modelo.SmsServicios;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author Desarrollo_Planit
 */
public class ServiciosBean implements Serializable {

    //Objetos necesarios
    private SmsServicios servicioView;

    private SmsMercado mercadoView;
    private SmsCostosservicios costoView;

    private List<SmsServicios> serviciosListView;
    private List<String> nombreServiciosListView;

    private List<String> nombresCategoriasListView;
    private List<SmsCategoria> categoriasListView;

    //Relacion con controladores
    CostosServicioBean costosController;

    //Conexion con el DAO
    IServicioDao servicioDao;
    ICategoriaDao categoriaDao;
    ILugarDao lugarDao;
    ICategoriasServicioDao catServicioDao;
    ICostosServiciosDao costoDao;

    //Variables
    private int estado; //Controla la operacion a realizar
    private String nombre;
    private String buscar;

    //banderas
    private boolean habilitarLugar;

    public ServiciosBean() {
        servicioView = new SmsServicios();
        serviciosListView = new ArrayList<>();
        nombreServiciosListView = new ArrayList<>();
        costoView = new SmsCostosservicios();
        mercadoView = new SmsMercado();

        buscar = null;
        estado = 0;
        nombre = "Registrar Servicio";

        servicioDao = new ImpServicioDao();
        categoriaDao = new ImpCategoriaDao();
        catServicioDao = new ImpCategoriasServicioDao();
        costoDao = new ImpCostosServiciosDao();
        lugarDao = new ImpLugarDao();

        habilitarLugar = false;
        costosController = new CostosServicioBean();

    }

    @PostConstruct
    public void init() {
        serviciosListView = new ArrayList<>();
        serviciosListView = servicioDao.mostrarServicios();
    }

    //Getters & Setters
    public SmsServicios getServicioView() {
        return servicioView;
    }

    public void setServicioView(SmsServicios servicioView) {
        this.servicioView = servicioView;
    }

    public List<SmsServicios> getServiciosListView() {
        return serviciosListView;
    }

    public void setServiciosListView(List<SmsServicios> serviciosListView) {
        this.serviciosListView = serviciosListView;
    }

    public List<String> getNombreServiciosListView() {
        nombreServiciosListView = new ArrayList<>();
        serviciosListView = new ArrayList<>();
        serviciosListView = servicioDao.mostrarServicios();
        for (int i = 0; i < serviciosListView.size(); i++) {
            nombreServiciosListView.add(serviciosListView.get(i).getServicioNombre());
        }
        return nombreServiciosListView;
    }

    public void setNombreServiciosListView(List<String> nombreServiciosListView) {
        this.nombreServiciosListView = nombreServiciosListView;
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

    public SmsMercado getMercadoView() {
        return mercadoView;
    }

    public void setMercadoView(SmsMercado mercadoView) {
        this.mercadoView = mercadoView;
    }

    public SmsCostosservicios getCostoView() {
        return costoView;
    }

    public void setCostoView(SmsCostosservicios costoView) {
        this.costoView = costoView;
    }

    public List<String> getNombresCategoriasListView() {
        return nombresCategoriasListView;
    }

    public void setNombresCategoriasListView(List<String> nombresCategoriasListView) {
        this.nombresCategoriasListView = nombresCategoriasListView;
    }

    public List<SmsCategoria> getCategoriasListView() {
        return categoriasListView;
    }

    public void setCategoriasListView(List<SmsCategoria> categoriasListView) {
        this.categoriasListView = categoriasListView;
    }

    public boolean isHabilitarLugar() {
        return habilitarLugar;
    }

    public void setHabilitarLugar(boolean habilitarLugar) {
        this.habilitarLugar = habilitarLugar;
    }

    //Metodos Propios
    public void metodo() {
        if (estado == 0) {
            registrar();
        } else if (estado == 1) {
            modificar();
            estado = 0;
            nombre = "Registrar Servicio";
        }

    }

    public void seleccionarCRUD(int i) {
        estado = i;
        if (estado == 1) {
            nombre = "Modificar Servicio";
        }
    }

    //Metodos que se comunican con el controlador    
    public void registrar() {

        //Se consulta la informacion de la categoria del servicio seleccionado
        servicioView.setSmsCategoriasServicio(catServicioDao.consultarCategoriasServicios(servicioView.getSmsCategoriasServicio()).get(0));

        servicioDao.registrarServicio(servicioView);

        costoView.setSmsServicios(servicioDao.ConsultarServicio(servicioView).get(0));
        costoView.setSmsCategoria(categoriaDao.consultarCategoria(costoView.getSmsCategoria()).get(0));

        if (costoView.getSmsLugaresByIdLugarDestino().getLugarNombre() != null  && costoView.getSmsLugaresByIdLugarDestino().getLugarNombre() != null) {
            costoView.setSmsLugaresByIdLugarInicio(lugarDao.consultarLugar(costoView.getSmsLugaresByIdLugarInicio()).get(0));
            costoView.setSmsLugaresByIdLugarDestino(lugarDao.consultarLugar(costoView.getSmsLugaresByIdLugarDestino()).get(0));
        }
        costoDao.registrarCostoServicio(costoView);

        servicioView = new SmsServicios();
        costoView = new SmsCostosservicios();
        mercadoView = new SmsMercado();

        serviciosListView = servicioDao.mostrarServicios();
    }

    public void modificar() {
        servicioDao.modificarServicio(servicioView);
        servicioView = new SmsServicios();
        serviciosListView = servicioDao.mostrarServicios();
    }

    public void eliminar() {
        servicioDao.eliminarServicio(servicioView);

        nombre = "Modificar Servicio";
        estado = 0;
        servicioView = new SmsServicios();

        serviciosListView = servicioDao.mostrarServicios();
    }

    public void filtrar() {
        serviciosListView = new ArrayList<>();
        if (buscar == null) {
            serviciosListView = servicioDao.mostrarServicios();
        } else {
            serviciosListView = servicioDao.filtrarServicios(buscar);
        }
    }

    public void habilitarLugar() {
        if (servicioView.getSmsCategoriasServicio().getCatNombre().equalsIgnoreCase("Traslado")) {
            habilitarLugar = true;
        } else {
            habilitarLugar = false;
        }
    }
    
    public List<String> seleccionarServiciosSegunMercado(SmsMercado mercado){
        serviciosListView = new ArrayList<>();
        serviciosListView = servicioDao.mostrarServicios();
        boolean bandera = false;
        for (int i = 0; i < serviciosListView.size(); i++) {
            for(SmsMercado m : serviciosListView.get(i).getSmsMercados()){
                if(m.getMercadoNombre().equalsIgnoreCase(mercado.getMercadoNombre())){
                    for (int j = 0; j < nombreServiciosListView.size(); j++) {
                        if(nombreServiciosListView.get(j).equalsIgnoreCase(serviciosListView.get(i).getServicioNombre())){
                            bandera = true;
                        }                        
                    }
                    if (!bandera) {
                        nombreServiciosListView.add(serviciosListView.get(i).getServicioNombre());
                    }
                }
            }
            bandera = false;        
        }        
        return nombreServiciosListView;
    }

}
