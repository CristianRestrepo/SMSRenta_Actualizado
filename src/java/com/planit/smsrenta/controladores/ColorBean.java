/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.controladores;

import com.planit.smsrenta.dao.IColorDao;
import com.planit.smsrenta.dao.ImpColorDao;
import com.planit.smsrenta.modelos.SmsColor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Desarrollo_Planit
 */
public class ColorBean implements Serializable {

    private List<SmsColor> colorListView;
    private List<String> nombresColores;
    IColorDao colorDao;

    public ColorBean() {
        colorDao = new ImpColorDao();
        colorListView = new ArrayList<>();
        nombresColores = new ArrayList<>();
    }

    public List<String> getNombresColores() {
        colorListView = new ArrayList<>();
        nombresColores = new ArrayList<>();

        colorListView = colorDao.consultarColores();
        for (int i = 0; i < colorListView.size(); i++) {
            nombresColores.add(colorListView.get(i).getColorNombre());
        }

        return nombresColores;
    }

    public void setNombresColores(List<String> nombresColores) {
        this.nombresColores = nombresColores;
    }

    public List<SmsColor> getColorListView() {
        return colorListView;
    }

    public void setColorListView(List<SmsColor> colorListView) {
        this.colorListView = colorListView;
    }

}
