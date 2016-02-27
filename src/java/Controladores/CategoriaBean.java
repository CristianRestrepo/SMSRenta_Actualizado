    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.ICategoriaDao;
import DAO.ImpCategoriaDao;
import Modelo.SmsCategoria;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

public class CategoriaBean implements Serializable {

    //Objeto de vista
    private SmsCategoria categoriaView;
    private SmsCategoria DCategoriaView;//La D hace alucion a delete, este objeto se usara para eliminar los registros de la BD
    private List<SmsCategoria> categoriasListView;
    private List<String> nombresCategoriasListView;

    //Variables
    private int estado; //Controla la operacion a realizar
    private String nombre;
    private String buscar;

    //Conexion con el DAO
    ICategoriaDao catDao;

    public CategoriaBean() {//CONSTRUCTOR
        categoriaView = new SmsCategoria();
        DCategoriaView = new SmsCategoria();
        categoriasListView = new ArrayList<>();
        nombresCategoriasListView = new ArrayList<>();

        buscar = null;
        estado = 0;
        nombre = "Registrar Categoria";

        catDao = new ImpCategoriaDao();
    }

    @PostConstruct
    public void init() {
        categoriasListView = catDao.mostrarCategorias();
    }

    //Getters & Setters
    public SmsCategoria getCategoriaView() {
        return categoriaView;
    }

    public void setCategoriaView(SmsCategoria categoriaView) {
        this.categoriaView = categoriaView;
    }

    public List<SmsCategoria> getCategoriasListView() {
        return categoriasListView;
    }

    public void setCategoriasListView(List<SmsCategoria> categoriasListView) {
        this.categoriasListView = categoriasListView;
    }

    public List<String> getNombresCategoriasListView() {
        nombresCategoriasListView = new ArrayList<>();
        categoriasListView = catDao.mostrarCategorias();
        for (int i = 0; i < categoriasListView.size(); i++) {
            nombresCategoriasListView.add(categoriasListView.get(i).getCategoriaNombre());
        }
        return nombresCategoriasListView;
    }

    public void setNombresCategoriasListView(List<String> nombresCategoriasListView) {
        this.nombresCategoriasListView = nombresCategoriasListView;
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

    public SmsCategoria getDCategoriaView() {
        return DCategoriaView;
    }

    public void setDCategoriaView(SmsCategoria DCategoriaView) {
        this.DCategoriaView = DCategoriaView;
    }

    //METODOS QUE DEVUELVEN DATOS PARA VISTAS
    public void modificar() {
        //TRAER LA INFORMACION DE LA VISTA Y PASARLA AL PARAMETRO CORRESPODIENTE 
        //DE LA CLASE DEL PAQUETE CONTROLADOR
        catDao.modificarCategoria(categoriaView);
        categoriaView = new SmsCategoria();
        categoriasListView = catDao.mostrarCategorias();

    }

    public void registrar() {
        //TRAER LA INFORMACION DE LA VISTA Y PASARLA AL PARAMETRO CORRESPODIENTE 
        //DE LA CLASE DEL PAQUETE CONTROLADOR
        catDao.registrarCategoria(categoriaView);
        categoriaView = new SmsCategoria();
        categoriasListView = catDao.mostrarCategorias();

    }

    public void eliminar() {
        //TRAER LA INFORMACION DE LA VISTA Y PASARLA AL PARAMETRO CORRESPODIENTE 
        //DE LA CLASE DEL PAQUETE CONTROLADOR
        catDao.eliminarCategoria(DCategoriaView);
        if (categoriaView.equals(DCategoriaView)) {
            categoriaView = new SmsCategoria();
            nombre = "Registrar Categoria";
            estado = 0;
        }
        DCategoriaView = new SmsCategoria();
        categoriasListView = catDao.mostrarCategorias();
    }

    //Consultar categoria
    public List<SmsCategoria> consultarCategoria(SmsCategoria cat) {
        categoriasListView = new ArrayList<>();
        categoriasListView = catDao.consultarCategoria(cat);
        return categoriasListView;
    }

    public void filtrar() {
        categoriasListView = new ArrayList<>();
        if (buscar == null) {
            categoriasListView = catDao.mostrarCategorias();
        } else {
            categoriasListView = catDao.filtrarCategorias(buscar);
        }
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

}
