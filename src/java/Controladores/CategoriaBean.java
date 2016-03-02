    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.ICategoriaDao;
import DAO.IMercadoDao;
import DAO.ImpCategoriaDao;
import DAO.ImpMercadoDao;
import Modelo.SmsCategoria;
import Modelo.SmsMercado;
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
    private List<String> mercadosSeleccionados;
    private SmsMercado mercadoView;
    //Variables
    private int estado; //Controla la operacion a realizar
    private String nombre;
    private String buscar;

    //Conexion con el DAO
    ICategoriaDao catDao;
    IMercadoDao mercadoDao;

    public CategoriaBean() {//CONSTRUCTOR
        categoriaView = new SmsCategoria();
        DCategoriaView = new SmsCategoria();
        categoriasListView = new ArrayList<>();
        nombresCategoriasListView = new ArrayList<>();
        mercadoView = new SmsMercado();

        buscar = null;
        estado = 0;
        nombre = "Registrar Categoria";

        catDao = new ImpCategoriaDao();
        mercadoDao = new ImpMercadoDao();
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

    public List<String> getMercadosSeleccionados() {
        return mercadosSeleccionados;
    }

    public void setMercadosSeleccionados(List<String> mercadosSeleccionados) {
        this.mercadosSeleccionados = mercadosSeleccionados;
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
        catDao.registrarCategoria(categoriaView);//Se registra la categoria        
        categoriaView = catDao.consultarCategoria(categoriaView).get(0);//Consultamos la categoria recien registrada

        for (int i = 0; i < mercadosSeleccionados.size(); i++) { //Relacionamos la categoria con los mercados seleccionados
            mercadoView = mercadoDao.consultarMercado(mercadosSeleccionados.get(i)).get(0);
            mercadoView.getSmsCategorias().add(categoriaView);//Se relaciona la categoria al mercado 
            categoriaView.getSmsMercados().add(mercadoView);//Se relaciona el mercado a la categoria
        }
        catDao.agregarMercadosCategoria(categoriaView);//se registra la relacion entre la categoria y los mercados
        
        //Limpiamos objetos
        categoriaView = new SmsCategoria();
        categoriasListView = catDao.mostrarCategorias();
        mercadosSeleccionados = new ArrayList<>();
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
