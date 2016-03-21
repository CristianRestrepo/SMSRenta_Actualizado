/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.ICategoriasServicioDao;
import DAO.IMercadoDao;
import DAO.ImpCategoriasServicioDao;
import DAO.ImpMercadoDao;
import Modelo.SmsCategoriasServicio;
import Modelo.SmsCiudad;
import Modelo.SmsMercado;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author Desarrollo_Planit
 */
public class CategoriasServiciosBean {

    private SmsCategoriasServicio categoriaServiciosView;
    private List<SmsCategoriasServicio> categoriasServicioListView;
    private List<String> nombresCategoriasServicios;

    
    
    ICategoriasServicioDao catServicioDao;
    IMercadoDao mercadoDao;

    //Variables
    private int estado; //Controla la operacion a realizar
    private String nombre;
    private String buscar;

    public CategoriasServiciosBean() {
        categoriaServiciosView = new SmsCategoriasServicio();
        categoriasServicioListView = new ArrayList<>();
        nombresCategoriasServicios = new ArrayList<>();

        catServicioDao = new ImpCategoriasServicioDao();
        mercadoDao = new ImpMercadoDao();
        
        nombre = "Registrar Categoria";
       
    }

    @PostConstruct
    public void init() {
        categoriasServicioListView = catServicioDao.consultarCategoriasServicios();
    }

    public SmsCategoriasServicio getCategoriaServiciosView() {
        return categoriaServiciosView;
    }

    public void setCategoriaServiciosView(SmsCategoriasServicio categoriaServiciosView) {
        this.categoriaServiciosView = categoriaServiciosView;
    }

    public List<SmsCategoriasServicio> getCategoriasServicioListView() {
        return categoriasServicioListView;
    }

    public void setCategoriasServicioListView(List<SmsCategoriasServicio> categoriasServicioListView) {
        this.categoriasServicioListView = categoriasServicioListView;
    }

    public List<String> getNombresCategoriasServicios() {
        nombresCategoriasServicios = new ArrayList<>();
        categoriasServicioListView = new ArrayList<>();
        categoriasServicioListView = catServicioDao.consultarCategoriasServicios();
        for (int i = 0; i < categoriasServicioListView.size(); i++) {
            nombresCategoriasServicios.add(categoriasServicioListView.get(i).getCatNombre());
        }
        return nombresCategoriasServicios;
    }

    public void setNombresCategoriasServicios(List<String> nombresCategoriasServicios) {
        this.nombresCategoriasServicios = nombresCategoriasServicios;
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

   

    
    //Metodos
    public void registrar() {
        categoriaServiciosView.setSmsMercado(mercadoDao.consultarMercadoConCategorias(categoriaServiciosView.getSmsMercado()).get(0));
        catServicioDao.registrarCategoriaServicio(categoriaServiciosView);
        
        categoriaServiciosView = new SmsCategoriasServicio();
        categoriasServicioListView = catServicioDao.consultarCategoriasServicios();
    }

    public void modificar() {
        categoriaServiciosView.setSmsMercado(mercadoDao.consultarMercadoConCategorias(categoriaServiciosView.getSmsMercado()).get(0));
        catServicioDao.modificarCategoriaServicio(categoriaServiciosView);
        
        categoriaServiciosView = new SmsCategoriasServicio();
        categoriasServicioListView = catServicioDao.consultarCategoriasServicios();
    
    }

    public void eliminar() {
        catServicioDao.eliminarCategoriaServicio(categoriaServiciosView);
        
        categoriaServiciosView = new SmsCategoriasServicio();
        categoriasServicioListView = catServicioDao.consultarCategoriasServicios();
    
    }

    //Metodos Propios
    public void metodo() {
        if (estado == 0) {
            registrar();
        } else if (estado == 1) {
            modificar();
            estado = 0;
            nombre = "Registrar Categoria";
        }
    }

    public void seleccionarCRUD(int i) {
        estado = i;
        if (estado == 1) {
            nombre = "Modificar Categoria";
        }
    }
    
    public List<String> consultarCategoriasSegunMercado(SmsMercado mercado){
        nombresCategoriasServicios = new ArrayList<>();
        categoriasServicioListView = new ArrayList<>();
        categoriasServicioListView = catServicioDao.consultarCategoriasServiciosSegunMercado(mercado);
        for (int i = 0; i < categoriasServicioListView.size(); i++) {
            nombresCategoriasServicios.add(categoriasServicioListView.get(i).getCatNombre());
        }
        return nombresCategoriasServicios;
    }

}
