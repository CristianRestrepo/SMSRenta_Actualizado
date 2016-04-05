/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.controladores;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author SISTEMAS
 */
@Named(value = "carouselDashBean")
@Dependent
public class CarouselDashBean {

    private List<String> imagenes;
    /**
     * Creates a new instance of CarouselDashBean
     */
    public CarouselDashBean() {
    }


    @PostConstruct
    public void init() {
        imagenes = new ArrayList<String>();
        for (int i = 1; i <= 5; i++) {

            if (imagenes.isEmpty()) {
                imagenes.add("img-a-" + i + ".jpg");
            } 
            else if (imagenes.equals("img-m") == true) {
                imagenes.add("img-m-" + i + ".jpg");
            } 
            else if (imagenes.equals("img-t") == true) {
                imagenes.add("img-t-" + i + ".jpg");
            }
        }
    }

    public List<String> getImagenes() {
        return imagenes;
    }

}
