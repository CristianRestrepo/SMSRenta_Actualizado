/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.IReferenciaDao;
import DAO.ImpReferenciaDao;
import Modelo.SmsMarca;
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
    private SmsReferencia DReferenciaView;
    private List<SmsReferencia> referenciasListView;
    private List<String> nombresReferenciaListView;
    private SmsMarca marcaView;

    //Relacion con el controlador
    MarcaBean marcaController;
    //Variables
    private int estado; //Controla la operacion a realizar
    private String nombre;
    private String buscar;

    //Conexion con el DAO
    IReferenciaDao referenciaDao;

    public ReferenciaBean() {
        DReferenciaView = new SmsReferencia();
        referenciaView = new SmsReferencia();
        referenciasListView = new ArrayList<>();
       
        nombresReferenciaListView = new ArrayList<>();
        marcaView = new SmsMarca();

        buscar = null;
        estado = 0;
        nombre = "Registrar Referencia";

        marcaController = new MarcaBean();

        referenciaDao = new ImpReferenciaDao();
    }

    @PostConstruct
    public void init() {
        referenciasListView = referenciaDao.mostrarReferencias();
    }

    //Getters & Setters
    public SmsMarca getMarcaView() {
        return marcaView;
    }

    public void setMarcaView(SmsMarca marcaView) {
        this.marcaView = marcaView;
    }

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

    public SmsMarca getMarcasView() {
        return marcaView;
    }

    public void setMarcasView(SmsMarca marcaView) {
        this.marcaView = marcaView;
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

    public SmsReferencia getDReferenciaView() {
        return DReferenciaView;
    }

    public void setDReferenciaView(SmsReferencia DReferenciaView) {
        this.DReferenciaView = DReferenciaView;
    }

    //Metodos
    public void modificar() {

        //Consultamos la informacion completa de la marca seleccionada
        marcaView = marcaController.consultarMarca(marcaView).get(0);
        referenciaView.setSmsMarca(marcaView);//relacionamos la referencia con la marca

        referenciaDao.modificarReferencia(referenciaView);//modificar la referencia

        referenciaView = new SmsReferencia();//limpiamos objetos
        marcaView = new SmsMarca();
        referenciasListView = referenciaDao.mostrarReferencias();//recargamos lista de referencias
    }

    public void eliminar() {
        //Eliminamos la referencia
        referenciaDao.eliminarReferencia(DReferenciaView);

        //Verificamos que el objeto a eliminar no este en proceso de edicion
        if (referenciaView.equals(DReferenciaView)) {
            //si es asi, elimina el objeto y limpia el objeto que contenia la referencia a eliminar
            referenciaView = new SmsReferencia();
            nombre = "Registrar Referencia";
            estado = 0;
        }

        DReferenciaView = new SmsReferencia();//limpiamos objetos
        referenciasListView = referenciaDao.mostrarReferencias(); //recargamos lista de referencias
    }

    public void registrar() {
        
         //Consultamos la informacion completa de la marca seleccionada
        marcaView = marcaController.consultarMarca(marcaView).get(0);
        referenciaView.setSmsMarca(marcaView);//relacionamos la referencia con la marca

        referenciaDao.registrarReferencia(referenciaView);//Registramos la referencia

        referenciaView = new SmsReferencia();//Limpiamos objetos
        marcaView = new SmsMarca();
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
            marcaView = referenciaView.getSmsMarca();
            nombre = "Modificar Referencia";
        }
    }
    
   

}
