/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.controladores;

import com.planit.smsrenta.dao.ITipoLugarDao;
import com.planit.smsrenta.dao.ImpTipoLugarDao;
import com.planit.smsrenta.modelos.SmsTipoLugar;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author Desarrollo_Planit
 */
public class TipoLugarBean {

    private List<String> nombresTipoLugarListView;
    private List<SmsTipoLugar> tipoLugarListView;

    ITipoLugarDao tipoLugarDao;

    public TipoLugarBean() {
        nombresTipoLugarListView = new ArrayList<>();
        tipoLugarListView = new ArrayList<>();
        
        tipoLugarDao = new ImpTipoLugarDao();
    }
    
    @PostConstruct
    public void init(){
        tipoLugarListView = tipoLugarDao.consultarTiposLugares();
    }

    public List<String> getNombresTipoLugarListView() {
        nombresTipoLugarListView = new ArrayList<>();
        tipoLugarListView = new ArrayList<>();
        tipoLugarListView = tipoLugarDao.consultarTiposLugares();
        for (int i = 0; i < tipoLugarListView.size(); i++) {
            nombresTipoLugarListView.add(tipoLugarListView.get(i).getTipoLugarNombre());
          
        }
        return nombresTipoLugarListView;
    }

    public void setNombresTipoLugarListView(List<String> nombresTipoLugarListView) {
        this.nombresTipoLugarListView = nombresTipoLugarListView;
    }

    public List<SmsTipoLugar> getTipoLugarListView() {
        return tipoLugarListView;
    }

    public void setTipoLugarListView(List<SmsTipoLugar> tipoLugarListView) {
        this.tipoLugarListView = tipoLugarListView;
    }

}
