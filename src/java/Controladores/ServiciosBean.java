/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.ICategoriaDao;
import DAO.ICategoriasServicioDao;
import DAO.IMercadoDao;
import DAO.IServicioDao;
import DAO.ImpCategoriaDao;
import DAO.ImpCategoriasServicioDao;
import DAO.ImpMercadoDao;
import DAO.ImpServicioDao;
import Modelo.SmsCategoria;
import Modelo.SmsCiudad;
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
    private SmsCiudad ciudadView;

    private List<SmsServicios> serviciosListView;
    private List<String> nombreServiciosListView;

    private List<String> nombresCategoriasListView;
    private List<SmsCategoria> categoriasListView;

    //Relacion con controladores
    CostosServicioBean costosController;

    //Conexion con el DAO
    IServicioDao servicioDao;
    ICategoriaDao categoriaDao;
    ICategoriasServicioDao catServicioDao;
    IMercadoDao mercadoDao;

    //Variables
    private int estado; //Controla la operacion a realizar
    private String nombre;
    private String buscar;

    public ServiciosBean() {
        servicioView = new SmsServicios();
        serviciosListView = new ArrayList<>();
        nombreServiciosListView = new ArrayList<>();

        mercadoView = new SmsMercado();
        ciudadView = new SmsCiudad();

        buscar = null;
        estado = 0;
        nombre = "Registrar Servicio";

        servicioDao = new ImpServicioDao();
        categoriaDao = new ImpCategoriaDao();
        catServicioDao = new ImpCategoriasServicioDao();  
        mercadoDao = new ImpMercadoDao();

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

    public SmsCiudad getCiudadView() {
        return ciudadView;
    }

    public void setCiudadView(SmsCiudad ciudadView) {
        this.ciudadView = ciudadView;
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
            mercadoView = servicioView.getSmsCategoriasServicio().getSmsMercado();
            nombre = "Modificar Servicio";
        }
    }

    //Metodos  
    public void registrar() {

        //Se consulta la informacion de la categoria del servicio seleccionado
        servicioView.getSmsCategoriasServicio().setSmsMercado(mercadoDao.consultarMercadoConCategorias(mercadoView).get(0));
        servicioView.setSmsCategoriasServicio(catServicioDao.consultarCategoriaServicio(servicioView.getSmsCategoriasServicio()).get(0));
        servicioDao.registrarServicio(servicioView);

        servicioView = new SmsServicios();
        mercadoView = new SmsMercado();

        serviciosListView = servicioDao.mostrarServicios();
    }

    public void modificar() {
        servicioDao.modificarServicio(servicioView);
        servicioView = new SmsServicios();
        mercadoView = new SmsMercado();
        serviciosListView = servicioDao.mostrarServicios();
    }

    public void eliminar() {
        servicioDao.eliminarServicio(servicioView);

        nombre = "Registrar Servicio";
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

    public List<String> seleccionarServiciosSegunMercado(SmsMercado mercado) {
        serviciosListView = new ArrayList<>();
        serviciosListView = servicioDao.mostrarServicios();
        boolean bandera = false;
        for (int i = 0; i < serviciosListView.size(); i++) {
            if (serviciosListView.get(i).getSmsCategoriasServicio().getSmsMercado().getMercadoNombre().equalsIgnoreCase(mercado.getMercadoNombre())) {
                for (int j = 0; j < nombreServiciosListView.size(); j++) {
                    if (nombreServiciosListView.get(j).equalsIgnoreCase(serviciosListView.get(i).getServicioNombre())) {
                        bandera = true;
                    }
                }
                if (!bandera) {
                    nombreServiciosListView.add(serviciosListView.get(i).getServicioNombre());
                }
            }

            bandera = false;
        }
        return nombreServiciosListView;
    }

}
