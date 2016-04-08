/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.controladores;

import com.planit.smsrenta.dao.ILocalidadDao;
import com.planit.smsrenta.dao.ImpLocalidadDao;
import com.planit.smsrenta.modelos.SmsCiudad;
import com.planit.smsrenta.modelos.SmsLocalidad;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author Desarrollo_Planit
 */
public class LocalidadBean {

    private SmsLocalidad localidadView;
    private List<SmsLocalidad> localidadesListView;
    private List<String> nombresLocalidades;
    
    ILocalidadDao localidadDao;
    public LocalidadBean() {
        localidadView = new SmsLocalidad();
        localidadesListView = new ArrayList<>();
        nombresLocalidades = new ArrayList<>();
        
        localidadDao = new ImpLocalidadDao();
    }
    
    @PostConstruct
    public void init(){
        localidadesListView = localidadDao.consultarLocalidades();
    }

    public SmsLocalidad getLocalidadView() {
        return localidadView;
    }

    public void setLocalidadView(SmsLocalidad localidadView) {
        this.localidadView = localidadView;
    }

    public List<SmsLocalidad> getLocalidadesListView() {
        return localidadesListView;
    }

    public void setLocalidadesListView(List<SmsLocalidad> localidadesListView) {
        this.localidadesListView = localidadesListView;
    }

    public List<String> getNombresLocalidades() {
        return nombresLocalidades;
    }

    public void setNombresLocalidades(List<String> nombresLocalidades) {
        this.nombresLocalidades = nombresLocalidades;
    }
    
    public List<String> consultarLocalidadesSegunCiudad(SmsCiudad ciudad){
        nombresLocalidades = new ArrayList<>();
        localidadesListView = localidadDao.consultarLocalidadesSegunCiudad(ciudad);
        for (int i = 0; i < localidadesListView.size(); i++) {
            nombresLocalidades.add(localidadesListView.get(i).getLocalidadNombre());
        }
        return nombresLocalidades;
        
    }
    
}
