/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import DAO.IMarcaDao;
import DAO.ImpMarcaDao;
import Modelo.SmsMarca;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author Desarrollo_Planit
 */
public class MarcaBean implements Serializable {

    //Objetos de vista
    private SmsMarca marcaView;
    private SmsMarca DMarcaView;
    private List<SmsMarca> marcasListView;
    private List<String> nombresMarcaView;

        
    //Conexion con el DAO
    IMarcaDao marcaDao;
    
    //Variables
    private int estado; //Controla la operacion a realizar
    private String nombre;
    private String buscar;

    public MarcaBean() {
        marcaView = new SmsMarca();
        DMarcaView = new SmsMarca();
        marcasListView = new ArrayList<>();
        nombresMarcaView = new ArrayList<>();
      
        buscar = "";
        estado = 0;
        nombre = "Registrar Marca";
        
        marcaDao = new ImpMarcaDao();
    }

    @PostConstruct
    public void init() {
        marcasListView = marcaDao.mostrarMarcas();
    }

    /* GETTERS Y SETTERS **********************************************************************/
    public SmsMarca getMarcaView() {
        return marcaView;
    }

    public void setMarcaView(SmsMarca marcaView) {
        this.marcaView = marcaView;
    }

    public List<SmsMarca> getMarcasListView() {
        return marcasListView;
    }

    public void setMarcasListView(List<SmsMarca> marcasListView) {
        this.marcasListView = marcasListView;
    }

    public List<String> getNombresMarcaView() {
        nombresMarcaView = new ArrayList<>();
        marcasListView = marcaDao.mostrarMarcas();
        for (int i = 0; i < marcasListView.size(); i++) {
            nombresMarcaView.add(marcasListView.get(i).getMarcaNombre());
        }
        return nombresMarcaView;
    }

    public void setNombresMarcaView(List<String> nombresMarcaView) {
        this.nombresMarcaView = nombresMarcaView;
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

    public SmsMarca getDMarcaView() {
        return DMarcaView;
    }

    public void setDMarcaView(SmsMarca DMarcaView) {
        this.DMarcaView = DMarcaView;
    }
    
   //Metodos
   public void modificar() {
        marcaDao.modificarMarca(marcaView);
        marcaView = new SmsMarca();
        marcasListView = marcaDao.mostrarMarcas();
    }

    public void eliminar() {
        marcaDao.eliminarMarca(DMarcaView);
        if (marcaView.equals(DMarcaView)) {
            marcaView = new SmsMarca();
            nombre = "Registrar Marca";
            estado = 0;
        }
        DMarcaView = new SmsMarca();
        marcasListView = marcaDao.mostrarMarcas();
    }

    public void registrar() {
        marcaDao.registrarMarca(marcaView);
        marcaView = new SmsMarca();
        marcasListView = marcaDao.mostrarMarcas();
    }

    public void filtrar() {
        marcasListView = new ArrayList<>();
        if (buscar.equals("")) {
            marcasListView = marcaDao.mostrarMarcas();
        } else {
            marcasListView = marcaDao.filtrarMarca(buscar);
            marcaView = new SmsMarca();
        }
    }
    
    public List<SmsMarca> consultarMarca(SmsMarca marca){
        marcasListView = new ArrayList<>();
        marcasListView = marcaDao.consultarMarca(marca);
        return marcasListView;
    }

    //Metodos Propios
    public void metodo() {
        if (estado == 0) {
            registrar();
        } else if (estado == 1) {
            modificar();
            estado = 0;
            nombre = "Registrar Marca";
        }
    }

    public void seleccionarCRUD(int i) {
        estado = i;
        if (estado == 1) {
            nombre = "Modificar Marca";
        }
    }
}
