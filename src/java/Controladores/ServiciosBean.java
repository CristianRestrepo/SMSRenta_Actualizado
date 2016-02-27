/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.IServicioDao;
import DAO.ImpServicioDao;
import Modelo.SmsCategoriasServicio;
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
public class ServiciosBean implements Serializable{

    //Objetos necesarios
    protected SmsServicios servicioView;
    protected SmsServicios DServicioView;
    private SmsCategoriasServicio categoriaServicioView;
    private SmsMercado mercadoView;
    protected List<SmsServicios> serviciosListView;
    protected List<String> nombreServiciosListView;

    //Conexion con el DAO
    IServicioDao servicioDao;

    //Variables
    private int estado; //Controla la operacion a realizar
    private String nombre;
    private String buscar;

    public ServiciosBean() {
        servicioView = new SmsServicios();
        serviciosListView = new ArrayList<>();
        categoriaServicioView = new SmsCategoriasServicio();
        nombreServiciosListView = new ArrayList<>();
        DServicioView = new SmsServicios();
        buscar = null;
        estado = 0;
        nombre = "Registrar Servicio";

        servicioDao = new ImpServicioDao();
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

    public SmsServicios getDServicioView() {
        return DServicioView;
    }

    public void setDServicioView(SmsServicios DServicioView) {
        this.DServicioView = DServicioView;
    }

    public SmsCategoriasServicio getCategoriaServicioView() {
        return categoriaServicioView;
    }

    public void setCategoriaServicioView(SmsCategoriasServicio categoriaServicioView) {
        this.categoriaServicioView = categoriaServicioView;
    }

    public SmsMercado getMercadoView() {
        return mercadoView;
    }

    public void setMercadoView(SmsMercado mercadoView) {
        this.mercadoView = mercadoView;
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
        servicioDao.registrarServicio(servicioView);
        servicioView = new SmsServicios();
        serviciosListView = servicioDao.mostrarServicios();
    }

    public void modificar() {
        servicioDao.modificarServicio(servicioView);
        servicioView = new SmsServicios();
        serviciosListView = servicioDao.mostrarServicios();
    }

    public void eliminar() {
        servicioDao.eliminarServicio(DServicioView);

        if (servicioView.equals(DServicioView)) {
            nombre = "Modificar Servicio";
            estado = 0;
            servicioView = new SmsServicios();
        }
        DServicioView = new SmsServicios();

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

}
