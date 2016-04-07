/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.controladores;

import com.planit.smsrenta.dao.ICategoriasServicioDao;
import com.planit.smsrenta.dao.ImpCategoriasServicioDao;
import com.planit.smsrenta.modelos.SmsCategoriasServicio;
import com.planit.smsrenta.modelos.SmsMercado;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author Desarrollo_Planit
 */
public class CategoriasServiciosBean {

    private SmsCategoriasServicio categoriaServiciosView;
    private List<SmsCategoriasServicio> categoriasServicioListView;
    private List<String> nombresCategoriasServicios;
       
    ICategoriasServicioDao catServicioDao; 
     
    public CategoriasServiciosBean() {
        categoriaServiciosView = new SmsCategoriasServicio();
        categoriasServicioListView = new ArrayList<>();
        nombresCategoriasServicios = new ArrayList<>();

        catServicioDao = new ImpCategoriasServicioDao();             
    }

    @PostConstruct
    public void init() {
        categoriasServicioListView = catServicioDao.consultarCategoriasServicios();
    }

    public SmsCategoriasServicio getCategoriaServiciosView() {
        return categoriaServiciosView;
    }

    public void setCategoriaServiciosView(SmsCategoriasServicio categoriaServiciosView) {
        this.categoriaServiciosView = categoriaServiciosView;
    }

    public List<SmsCategoriasServicio> getCategoriasServicioListView() {
        return categoriasServicioListView;
    }

    public void setCategoriasServicioListView(List<SmsCategoriasServicio> categoriasServicioListView) {
        this.categoriasServicioListView = categoriasServicioListView;
    }

    public List<String> getNombresCategoriasServicios() {
        nombresCategoriasServicios = new ArrayList<>();
        categoriasServicioListView = new ArrayList<>();
        categoriasServicioListView = catServicioDao.consultarCategoriasServicios();
        for (int i = 0; i < categoriasServicioListView.size(); i++) {
            nombresCategoriasServicios.add(categoriasServicioListView.get(i).getCatNombre());
        }
        return nombresCategoriasServicios;
    }

    public void setNombresCategoriasServicios(List<String> nombresCategoriasServicios) {
        this.nombresCategoriasServicios = nombresCategoriasServicios;
    }
}
