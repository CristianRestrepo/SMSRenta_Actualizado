/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.controladores;

import com.planit.smsrenta.dao.INacionalidadDao;
import com.planit.smsrenta.dao.ImpNacionalidadDao;
import com.planit.smsrenta.modelos.SmsNacionalidad;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Desarrollo_Planit
 */
public class NacionalidadBean {

    private List<SmsNacionalidad> nacionalidadListView;
    private List<String> nombresNacionalidades;
    
    INacionalidadDao nacionalidadDao;
    
    public NacionalidadBean() {
        
        nacionalidadListView = new ArrayList<>();
        nombresNacionalidades = new ArrayList<>();
    
        nacionalidadDao = new ImpNacionalidadDao();
    }

    public List<SmsNacionalidad> getNacionalidadListView() {
        return nacionalidadListView;
    }

    public void setNacionalidadListView(List<SmsNacionalidad> nacionalidadListView) {
        this.nacionalidadListView = nacionalidadListView;
    }

    public List<String> getNombresNacionalidades() {
        nacionalidadListView = new ArrayList<>();
        nombresNacionalidades = new ArrayList<>();
        
        nacionalidadListView = nacionalidadDao.consultarNacionalidades();
        for (int i = 0; i < nacionalidadListView.size(); i++) {
            nombresNacionalidades.add(nacionalidadListView.get(i).getNacionalidadNombre());
        }        
        return nombresNacionalidades;
    }

    public void setNombresNacionalidades(List<String> nombresNacionalidades) {
        this.nombresNacionalidades = nombresNacionalidades;
    }
    
    
    
    
}
