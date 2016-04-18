/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.controladores;

import com.planit.smsrenta.dao.IDepartamentoDao;
import com.planit.smsrenta.dao.IPaisDao;
import com.planit.smsrenta.dao.ImpDepartamentoDao;
import com.planit.smsrenta.dao.ImpPaisDao;
import com.planit.smsrenta.modelos.SmsDepartamento;
import com.planit.smsrenta.modelos.SmsPais;
import com.planit.smsrenta.modelos.SmsUsuario;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author Desarrollo_Planit
 */
public class DepartamentoBean {

    private SmsDepartamento departamentoView;   
    
    private List<SmsDepartamento> departamentoListView;
    private List<String> nombresDepartamentoListView;
    private SmsPais paisView;

    IDepartamentoDao departamentoDao;
    IPaisDao paisDao;

    //Banderas    
    private boolean habilitarCancelar;
    
    //Variables
    private int estado; //Controla la operacion a realizar
    private String nombre;
    private String buscar;
    
    public DepartamentoBean() {

        departamentoDao = new ImpDepartamentoDao();
        paisDao = new ImpPaisDao();

        departamentoView = new SmsDepartamento();       
        departamentoListView = new ArrayList<>();
        nombresDepartamentoListView = new ArrayList<>();
        
        habilitarCancelar = true;
        
        buscar = null;
        estado = 0;
        nombre = "Registrar Departamento";

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

    public boolean isHabilitarCancelar() {
        return habilitarCancelar;
    }

    public void setHabilitarCancelar(boolean habilitarCancelar) {
        this.habilitarCancelar = habilitarCancelar;
    }      
    
    //Metodos
    public void registrarDepartamento(){
        
        //Se consulta la informacion completa del pais al que pertenece el departamento
        departamentoView.setSmsPais(paisDao.consultarPais(departamentoView.getSmsPais()));
                
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
        departamentoView.setSmsPais(paisDao.consultarPais(departamentoView.getSmsPais()));
                  
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
        departamentoDao.eliminarDepartamento(departamentoView);
                
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
            nombre = "Modificar Departamento";
            habilitarCancelar = false;
        }
    }

    public void metodo() {
        if (estado == 0) {
            registrarDepartamento();
        } else if (estado == 1) {
            ModificarDepartamento();
            habilitarCancelar = true;
            estado = 0;
            nombre = "Registrar Departamento";
        }
    }
    
     public void filtrar() {
        departamentoListView = new ArrayList<>();
        if (buscar == null) {
            departamentoListView = departamentoDao.consultarDepartamentos();
        } else {
            departamentoListView = departamentoDao.filtrarDepartamentos(buscar);
        }
    }
     
     public void cancelar() {
        //Limpiamos objetos utilizados
        departamentoView = new SmsDepartamento();
        estado = 0;
        //Reiniciamos los objetos
        habilitarCancelar = true;
        nombre = "Registrar Departamento";
    }
}
