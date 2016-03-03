/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.IDepartamentoDao;
import DAO.IPaisDao;
import DAO.ImpDepartamentoDao;
import DAO.ImpPaisDao;
import Modelo.SmsDepartamento;
import Modelo.SmsPais;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author Desarrollo_Planit
 */
public class DepartamentoBean {

    private SmsDepartamento departamentoView;
    private SmsDepartamento EDepartamentoView;//Objeto para eliminar el registro
    
    private List<SmsDepartamento> departamentoListView;
    private List<String> nombresDepartamentoListView;
    private SmsPais paisView;

    IDepartamentoDao departamentoDao;
    IPaisDao paisDao;

    //Variables
    private int estado; //Controla la operacion a realizar
    private String nombre;
    private String buscar;
    
    public DepartamentoBean() {

        departamentoDao = new ImpDepartamentoDao();
        paisDao = new ImpPaisDao();

        departamentoView = new SmsDepartamento();
        EDepartamentoView = new SmsDepartamento();
        departamentoListView = new ArrayList<>();
        nombresDepartamentoListView = new ArrayList<>();
        
        buscar = null;
        estado = 0;
        nombre = "Registrar Pais";

    }
    
    @PostConstruct
    public void init(){
        departamentoListView = departamentoDao.consultarDepartamentos();
    }

    public SmsDepartamento getDepartamentoView() {
        return departamentoView;
    }

    public void setDepartamentoView(SmsDepartamento departamentoView) {
        this.departamentoView = departamentoView;
    }

    public List<SmsDepartamento> getDepartamentoListView() {
        return departamentoListView;
    }

    public void setDepartamentoListView(List<SmsDepartamento> departamentoListView) {
        this.departamentoListView = departamentoListView;
    }

    public List<String> getNombresDepartamentoListView() {
        departamentoListView = new ArrayList<>();
        nombresDepartamentoListView = new ArrayList<>();
        departamentoListView = departamentoDao.consultarDepartamentos();
        for (int i = 0; i < departamentoListView.size(); i++) {
            nombresDepartamentoListView.add(departamentoListView.get(i).getDepartamentoNombre());
        }
        return nombresDepartamentoListView;
    }

    public void setNombresDepartamentoListView(List<String> nombresDepartamentoListView) {
        this.nombresDepartamentoListView = nombresDepartamentoListView;
    }

    public SmsPais getPaisView() {
        return paisView;
    }

    public void setPaisView(SmsPais paisView) {
        this.paisView = paisView;
    }

    public SmsDepartamento getEDepartamentoView() {
        return EDepartamentoView;
    }

    public void setEDepartamentoView(SmsDepartamento EDepartamentoView) {
        this.EDepartamentoView = EDepartamentoView;
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
    
    public void registrarDepartamento(){
        
        //Se consulta la informacion completa del pais al que pertenece el departamento
        paisView = paisDao.consultarPais(paisView).get(0);
        //Se relaciona al departamento
        departamentoView.setSmsPais(paisView);
        
        //Se registra el departamento
        departamentoDao.registrarDepartamento(departamentoView);
        
        //Recargamos la lista de departamentos
        departamentoListView = departamentoDao.consultarDepartamentos();
        
        //limpiamos objetos
        paisView = new SmsPais();
        departamentoView = new SmsDepartamento();        
    }
    
    public void ModificarDepartamento(){
        
        //Se consulta la informacion completa del pais al que pertenece el departamento
        paisView = paisDao.consultarPais(paisView).get(0);
        //Se relaciona al departamento
        departamentoView.setSmsPais(paisView);
        
        //Se modifica el departamento
        departamentoDao.modificarDepartamento(departamentoView);
        
        //Recargamos la lista de departamentos
        departamentoListView = departamentoDao.consultarDepartamentos();
        
        //limpiamos objetos
        paisView = new SmsPais();
        departamentoView = new SmsDepartamento();        
    }
    
    public void eliminarDepartamento(){
        //Se registra el departamento
        departamentoDao.eliminarDepartamento(EDepartamentoView);
        
        if(departamentoView.equals(EDepartamentoView)){
        departamentoView = new SmsDepartamento();
        paisView = new SmsPais();
        }
        //Recargamos la lista de departamentos
        departamentoListView = departamentoDao.consultarDepartamentos();
        
        //limpiamos objetos     
        departamentoView = new SmsDepartamento();        
    }
    
    //Metodos para controlar la vista
    //Metodos Propios
    public void seleccionarCrud(int i) {
        estado = i;
        if (estado == 1) {
            nombre = "Modificar departamento";
        }
    }

    public void metodo() {
        if (estado == 0) {
            registrarDepartamento();
        } else if (estado == 1) {
            ModificarDepartamento();
            estado = 0;
            nombre = "Registrar departamento";
        }
    }
}
