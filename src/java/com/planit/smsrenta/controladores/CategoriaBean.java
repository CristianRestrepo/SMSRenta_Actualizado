    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.controladores;

import com.planit.smsrenta.dao.ICategoriaDao;
import com.planit.smsrenta.dao.IMercadoDao;
import com.planit.smsrenta.dao.IProveedorDao;
import com.planit.smsrenta.dao.ImpCategoriaDao;
import com.planit.smsrenta.dao.ImpMercadoDao;
import com.planit.smsrenta.dao.ImpProveedorDao;
import com.planit.smsrenta.modelos.SmsCategoria;
import com.planit.smsrenta.modelos.SmsMercado;
import com.planit.smsrenta.modelos.SmsProveedor;
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
    private List<SmsMercado> mercadoList;
    private SmsMercado mercadoView;
    //Variables
    private int estado; //Controla la operacion a realizar
    private String nombre;
    private String buscar;

    //Conexion con el DAO
    ICategoriaDao catDao;
    IMercadoDao mercadoDao;

    //Banderas    
    private boolean habilitarCancelar;

    public CategoriaBean() {//CONSTRUCTOR
        categoriaView = new SmsCategoria();
        categoriasListView = new ArrayList<>();
        nombresCategoriasListView = new ArrayList<>();
        mercadoList = new ArrayList<>();
        mercadoView = new SmsMercado();

        buscar = null;
        estado = 0;
        nombre = "Registrar Categoria";

        habilitarCancelar = true;

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

    public boolean isHabilitarCancelar() {
        return habilitarCancelar;
    }

    public void setHabilitarCancelar(boolean habilitarCancelar) {
        this.habilitarCancelar = habilitarCancelar;
    }

    //METODOS QUE DEVUELVEN DATOS PARA VISTAS
    public void modificar() {
        boolean valor = false;
        boolean noexiste = false;
        mercadoList = mercadoDao.consultarMercadoSegunCategoria(categoriaView);
        List<SmsMercado> mercadoEliminadoList = new ArrayList<>();

        //identificamos mercados deseleccionados
        for (SmsMercado mercado : mercadoList) {
            for (int i = 0; i < mercadosSeleccionados.size(); i++) {
                if (mercado.getMercadoNombre().equals(mercadosSeleccionados.get(i))) {
                    noexiste = true;
                }
            }
            if (!noexiste) {
                mercadoEliminadoList.add(mercado);
            }
            noexiste = false;
        }

        //Agregamos nuevos mercados seleccionados
        for (int j = 0; j < mercadosSeleccionados.size(); j++) {
            for (SmsMercado mercado : mercadoList) {
                if (mercado.getMercadoNombre().equals(mercadosSeleccionados.get(j))) {
                    valor = true;
                }
            }
            if (!valor) {
                mercadoView.setMercadoNombre(mercadosSeleccionados.get(j));
                mercadoView = mercadoDao.consultarMercadoConCategorias(mercadoView);
                mercadoView.getSmsCategorias().add(categoriaView);
                categoriaView.getSmsMercados().add(mercadoView);
            }
            valor = false;
            mercadoView = new SmsMercado();
        }

        //Removemos mercados deseleccionados
        for (int i = 0; i < mercadoEliminadoList.size(); i++) {
            SmsCategoria cat = new SmsCategoria();

            //Identificamos categoria a eliminar
            for (SmsCategoria categoria : mercadoEliminadoList.get(i).getSmsCategorias()) {
                if (categoria.getIdCategoria().equals(categoriaView.getIdCategoria())) {
                    cat = categoria;
                }
            }

            //Identificamos mercado a eliminar
            for (SmsMercado mercado : categoriaView.getSmsMercados()) {
                if (mercado.getIdMercado().equals(mercadoEliminadoList.get(i).getIdMercado())) {
                    mercadoView = mercado;
                }
            }

            //removemos asociaciones
            mercadoEliminadoList.get(i).getSmsCategorias().remove(cat);
            categoriaView.getSmsMercados().remove(mercadoView);

        }
        
        //modificamos categoria
        catDao.modificarCategoria(categoriaView);

        categoriaView = new SmsCategoria();
        categoriasListView = catDao.mostrarCategorias();
        mercadosSeleccionados = new ArrayList<>();
    }

    public void registrar() {
        catDao.registrarCategoria(categoriaView);//Se registra la categoria        
        //categoriaView = catDao.consultarCategoria(categoriaView).get(0);//Consultamos la categoria recien registrada

        List<SmsMercado> mercados = new ArrayList();
        for (int i = 0; i < mercadosSeleccionados.size(); i++) {
            mercadoView.setMercadoNombre(mercadosSeleccionados.get(i));
            mercadoView = mercadoDao.consultarMercadoConCategorias(mercadoView);
            mercados.add(mercadoView);
            mercadoView = new SmsMercado();
        }

        //Relacionamos la categoria con los mercados seleccionados
        for (int i = 0; i < mercados.size(); i++) {
            mercados.get(i).getSmsCategorias().add(categoriaView);
            categoriaView.getSmsMercados().add(mercados.get(i));
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
        mercadosSeleccionados = new ArrayList<>();

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
            proveedor = provDao.consultarProveedor(proveedor);

            List<SmsMercado> mercados = mercadoDao.consultarMercadosSegunProveedor(proveedor);

            for (int i = 0; i < mercados.size(); i++) {
                CategoriasSegunMercado(mercados.get(i));
            }
        }
        return nombresCategoriasListView;
    }

    public List<String> CategoriasSegunMercado(SmsMercado merc) {
        nombresCategoriasListView = new ArrayList<>();

        if (merc.getMercadoNombre() != null) {
            mercadoView = mercadoDao.consultarMercadoConCategorias(merc);
            categoriasListView = catDao.consultarCategoriasSegunMercado(mercadoView);
            boolean existe = false;
            for (int j = 0; j < categoriasListView.size(); j++) {
                for (int i = 0; i < nombresCategoriasListView.size(); i++) {
                    if (categoriasListView.get(j).getCategoriaNombre().equalsIgnoreCase(nombresCategoriasListView.get(i))) {
                        existe = true;
                    }
                }
                if (!existe) {
                    nombresCategoriasListView.add(categoriasListView.get(j).getCategoriaNombre());
                }
                existe = false;
            }
        }
        return nombresCategoriasListView;
    }

//Metodos Propios
    public void metodo() {
        if (estado == 0) {
            registrar();
        } else if (estado == 1) {
            habilitarCancelar = true;
            modificar();
            estado = 0;
            nombre = "Registrar Categoria";
        }
    }

    public void seleccionarCRUD(int i) {
        estado = i;
        mercadosSeleccionados = new ArrayList<>();
        if (estado == 1) {
            habilitarCancelar = false;
            nombre = "Modificar Categoria";
            mercadoList = mercadoDao.consultarMercadoSegunCategoria(categoriaView);
            for (int j = 0; j < mercadoList.size(); j++) {
                mercadosSeleccionados.add(mercadoList.get(j).getMercadoNombre());
            }
        }
    }

    public void cancelar() {
        //Limpiamos objetos utilizados
        categoriaView = new SmsCategoria();
        mercadosSeleccionados = new ArrayList<>();
        estado = 0;
        //Reiniciamos los objetos
        habilitarCancelar = true;
        nombre = "Registrar Proveedor";
    }
}
