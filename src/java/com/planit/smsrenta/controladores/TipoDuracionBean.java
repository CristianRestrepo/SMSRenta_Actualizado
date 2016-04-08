/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.controladores;

import com.planit.smsrenta.dao.ITipoDuracionDao;
import com.planit.smsrenta.dao.ImpTipoDuracionDao;
import com.planit.smsrenta.modelos.SmsTipoDuracion;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author Desarrollo_Planit
 */
public class TipoDuracionBean {

    private SmsTipoDuracion tipoDuracionView;
    private List<SmsTipoDuracion> tipoDuracionListView;
    private List<String> nombresTipoDuracion;
    ITipoDuracionDao tipoDao;
    
    public TipoDuracionBean() {
        tipoDuracionView = new SmsTipoDuracion();
        tipoDuracionListView = new ArrayList<>();
        nombresTipoDuracion = new ArrayList<>();
        tipoDao = new ImpTipoDuracionDao();
    }
    
    @PostConstruct
    public void init(){
        
    }

    public SmsTipoDuracion getTipoDuracionView() {
        return tipoDuracionView;
    }

    public void setTipoDuracionView(SmsTipoDuracion tipoDuracionView) {
        this.tipoDuracionView = tipoDuracionView;
    }

    public List<SmsTipoDuracion> getTipoDuracionListView() {
        return tipoDuracionListView;
    }

    public void setTipoDuracionListView(List<SmsTipoDuracion> tipoDuracionListView) {
        this.tipoDuracionListView = tipoDuracionListView;
    }

    public List<String> getNombresTipoDuracion() {
        nombresTipoDuracion = new ArrayList<>();
        tipoDuracionListView = new ArrayList<>();
        tipoDuracionListView = tipoDao.consultarTiposDuracion();
        for (int i = 0; i < tipoDuracionListView.size(); i++) {
            nombresTipoDuracion.add(tipoDuracionListView.get(i).getTipoDuracionNombre());
        }
        return nombresTipoDuracion;
    }

    public void setNombresTipoDuracion(List<String> nombresTipoDuracion) {
        this.nombresTipoDuracion = nombresTipoDuracion;
    }

    
    
}
