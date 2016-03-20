    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.ICategoriaDao;
import DAO.IMercadoDao;
import DAO.IProveedorDao;
import DAO.ImpCategoriaDao;
import DAO.ImpMercadoDao;
import DAO.ImpProveedorDao;
import Modelo.SmsCategoria;
import Modelo.SmsMercado;
import Modelo.SmsProveedor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

public class CategoriaBean implements Serializable {

    //Objeto de vista
    private SmsCategoria categoriaView;
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

    public List<String> getMercadosSeleccionados() {
        return mercadosSeleccionados;
    }

    public void setMercadosSeleccionados(List<String> mercadosSeleccionados) {
        this.mercadosSeleccionados = mercadosSeleccionados;
    }

    //METODOS QUE DEVUELVEN DATOS PARA VISTAS
    public void modificar() {
        boolean valor = false;
        for (int j = 0; j < mercadosSeleccionados.size(); j++) {
            for (SmsMercado mercado : categoriaView.getSmsMercados()) {
                if (mercado.getMercadoNombre().equals(mercadosSeleccionados.get(j))) {
                    valor = true;
                }
            }
            if (!valor) {
                mercadoView = mercadoDao.consultarMercado(mercadosSeleccionados.get(j)).get(0);
                mercadoView.getSmsCategorias().add(categoriaView);
                categoriaView.getSmsMercados().add(mercadoView);
            }
        }

        catDao.modificarCategoria(categoriaView);

        categoriaView = new SmsCategoria();
        categoriasListView = catDao.mostrarCategorias();
        mercadosSeleccionados = new ArrayList<>();
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
        catDao.eliminarCategoria(categoriaView);
        categoriaView = new SmsCategoria();
        nombre = "Registrar Categoria";
        estado = 0;
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

    public List<String> CategoriasSegunMercadoProv(String prov) {
        nombresCategoriasListView = new ArrayList<>();

        if (!prov.isEmpty()) {
            SmsProveedor proveedor = new SmsProveedor();
            IProveedorDao provDao = new ImpProveedorDao();

            proveedor.setProveedorRazonSocial(prov);
            proveedor = provDao.consultarProveedor(proveedor).get(0);

            List<SmsMercado> mercados = mercadoDao.consultarMercados();
            List<SmsMercado> m = new ArrayList<>();

            for (SmsMercado mercado : mercados) {
                for (SmsMercado mercadoProv : proveedor.getSmsMercados()) {
                    if (mercadoProv.getMercadoNombre().equalsIgnoreCase(mercado.getMercadoNombre())) {
                        m.add(mercado);
                    }
                }
            }

            categoriasListView = catDao.mostrarCategorias();
            boolean bandera = false;
            for (int i = 0; i < m.size(); i++) {
                for (int j = 0; j < categoriasListView.size(); j++) {
                    for (SmsMercado merc : categoriasListView.get(j).getSmsMercados()) {
                        if (m.get(i).getMercadoNombre().equalsIgnoreCase(merc.getMercadoNombre())) {
                            for (int k = 0; k < nombresCategoriasListView.size(); k++) {
                                if (nombresCategoriasListView.get(k).equalsIgnoreCase(categoriasListView.get(j).getCategoriaNombre())) {
                                    bandera = true;
                                }
                            }
                            if (!bandera) {
                                nombresCategoriasListView.add(categoriasListView.get(j).getCategoriaNombre());
                            }
                            bandera = false;
                        }

                    }

                }
            }
        }
        return nombresCategoriasListView;
    }

    public List<String> CategoriasSegunMercado(String merc) {
        nombresCategoriasListView = new ArrayList<>();

        if (!merc.isEmpty()) {
            SmsMercado m = mercadoDao.consultarMercado(merc).get(0);

            categoriasListView = catDao.mostrarCategorias();
            boolean bandera = false;
            for (int j = 0; j < categoriasListView.size(); j++) {
                for (SmsMercado mercado : categoriasListView.get(j).getSmsMercados()) {
                    if (m.getMercadoNombre().equalsIgnoreCase(mercado.getMercadoNombre())) {
                        for (int k = 0; k < nombresCategoriasListView.size(); k++) {
                            if (nombresCategoriasListView.get(k).equalsIgnoreCase(categoriasListView.get(j).getCategoriaNombre())) {
                                bandera = true;
                            }
                        }
                        if (!bandera) {
                            nombresCategoriasListView.add(categoriasListView.get(j).getCategoriaNombre());
                        }
                        bandera = false;
                    }

                }

            }
        }
        return nombresCategoriasListView;
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
        mercadosSeleccionados = new ArrayList<>();
        if (estado == 1) {
            nombre = "Modificar Categoria";
            List<SmsMercado> mercadoList = new ArrayList<>();
            mercadoList = mercadoDao.consultarMercados();

            for (SmsMercado mercado : categoriaView.getSmsMercados()) {
                for (int j = 0; j < mercadoList.size(); j++) {
                    if (mercado.getMercadoNombre().equals(mercadoList.get(j).getMercadoNombre())) {
                        mercadosSeleccionados.add(mercado.getMercadoNombre());
                    }
                }
            }

        }
    }

}
