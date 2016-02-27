/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.IMercadoDao;
import DAO.ImpMercadoDao;
import Modelo.SmsMercado;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author Desarrollo_Planit
 */
public class MercadoBean {

    private SmsMercado mercadoView;
    private List<SmsMercado> mercadoListView;
    
    private String buscar;
    IMercadoDao mercadoDao;
    
    public MercadoBean() {
        mercadoDao = new ImpMercadoDao();
        mercadoView = new SmsMercado();
        mercadoListView = new ArrayList<>();
        
        buscar = null;
    }
    
    @PostConstruct
    public void init(){
        mercadoListView = mercadoDao.consultarMercados();
    }

    public SmsMercado getMercadoView() {
        return mercadoView;
    }

    public void setMercadoView(SmsMercado mercadoView) {
        this.mercadoView = mercadoView;
    }

    public List<SmsMercado> getMercadoListView() {
        return mercadoListView;
    }

    public void setMercadoListView(List<SmsMercado> mercadoListView) {
        this.mercadoListView = mercadoListView;
    }
    
    //Metodos
    public void registrarMercado(){
        mercadoDao.registrarMercado(mercadoView);
        mercadoListView = mercadoDao.consultarMercados();
    }
    
    public void modificarMercado(){
        mercadoDao.modificarMercado(mercadoView);
        mercadoListView = mercadoDao.consultarMercados();
    
    }
    
    public void eliminarMercado(){
        mercadoDao.eliminarMercado(mercadoView);
        mercadoListView= mercadoDao.consultarMercados();
    }
    
    public void filtrarMercados(){
        if(buscar == null){
           mercadoListView = mercadoDao.consultarMercados();
        }else{
           mercadoListView = mercadoDao.filtrarMercados(buscar);
        }
    }
    
    
}
