/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.controladores;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.swing.JOptionPane;

/**
 *
 * @author SISTEMAS
 */
public class carouselBean {

     private List<String> imagenes;
     
    @PostConstruct
    public void init() {
        imagenes = new ArrayList<String>();
        for (int i = 1; i <= 7; i++) {
            imagenes.add("img-" + i + ".jpg");
        }
    }
 
    public List<String> getImagenes() {
        return imagenes;
    }

}
