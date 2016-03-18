/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.IMarcaDao;
import DAO.IReferenciaDao;
import DAO.ImpMarcaDao;
import DAO.ImpReferenciaDao;
import Modelo.SmsReferencia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author Desarrollo_Planit
 */
public class ReferenciaBean implements Serializable {

    //Objetos de vista
    private SmsReferencia referenciaView;
    private List<SmsReferencia> referenciasListView;
    private List<String> nombresReferenciaListView;
   
    //Variables
    private int estado; //Controla la operacion a realizar
    private String nombre;
    private String buscar;

    //Conexion con el DAO
    IReferenciaDao referenciaDao;
    IMarcaDao marcaDao;

    public ReferenciaBean() {
        referenciaView = new SmsReferencia();
        referenciasListView = new ArrayList<>();

        nombresReferenciaListView = new ArrayList<>();

        buscar = null;
        estado = 0;
        nombre = "Registrar Referencia";
     
        referenciaDao = new ImpReferenciaDao();
        marcaDao = new ImpMarcaDao();
    }

    @PostConstruct
    public void init() {
        referenciasListView = referenciaDao.mostrarReferencias();
    }

    //Getters & Setters
    public SmsReferencia getReferenciaView() {
        return referenciaView;
    }

    public void setReferenciaView(SmsReferencia referenciaView) {
        this.referenciaView = referenciaView;
    }

    public List<SmsReferencia> getReferenciasListView() {
        return referenciasListView;
    }

    public void setReferenciasListView(List<SmsReferencia> referenciasListView) {
        this.referenciasListView = referenciasListView;
    }

    public List<String> getNombresReferenciaListView() {
        nombresReferenciaListView = new ArrayList<>();
        referenciasListView = referenciaDao.mostrarReferencias();
        for (int i = 0; i < referenciasListView.size(); i++) {
            nombresReferenciaListView.add(referenciasListView.get(i).getReferenciaNombre());
        }
        return nombresReferenciaListView;
    }

    public void setNombresReferenciaListView(List<String> nombresReferenciaListView) {
        this.nombresReferenciaListView = nombresReferenciaListView;
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

    //Metodos
    public void modificar() {

        //Consultamos la informacion completa de la marca seleccionada
        referenciaView.setSmsMarca(marcaDao.consultarMarca(referenciaView.getSmsMarca()).get(0));//relacionamos la referencia con la marca

        referenciaDao.modificarReferencia(referenciaView);//modificar la referencia

        referenciaView = new SmsReferencia();//limpiamos objetos
        referenciasListView = referenciaDao.mostrarReferencias();//recargamos lista de referencias
    }

    public void eliminar() {
        //Eliminamos la referencia
        referenciaDao.eliminarReferencia(referenciaView);

        //Limpia objetos
        referenciaView = new SmsReferencia();
        nombre = "Registrar Referencia";
        estado = 0;

        referenciasListView = referenciaDao.mostrarReferencias(); //recargamos lista de referencias
    }

    public void registrar() {

        //Consultamos la informacion completa de la marca seleccionada
        referenciaView.setSmsMarca(marcaDao.consultarMarca(referenciaView.getSmsMarca()).get(0));//relacionamos la referencia con la marca
        referenciaDao.registrarReferencia(referenciaView);//Registramos la referencia

        referenciaView = new SmsReferencia();//Limpiamos objetos
        referenciasListView = referenciaDao.mostrarReferencias();//recargamos la lista de referencias
    }

    public void filtrar() {
        referenciasListView = new ArrayList<>();
        if (buscar == null) {
            referenciasListView = referenciaDao.mostrarReferencias();
        } else {
            referenciasListView = referenciaDao.filtrarReferencias(buscar);
        }
    }

    //Metodos Propios
    public void metodo() {
        if (estado == 0) {
            registrar();
        } else if (estado == 1) {
            modificar();
            estado = 0;
            nombre = "Registrar Referencia";
        }
    }

    public void seleccionarCRUD(int i) {
        estado = i;
        if (estado == 1) {
            nombre = "Modificar Referencia";
        }
    }

}
