/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.controladores;

import com.planit.smsrenta.dao.IMercadoDao;
import com.planit.smsrenta.dao.ImpMercadoDao;
import com.planit.smsrenta.metodos.Upload;
import static com.planit.smsrenta.metodos.Upload.getMapPathFotosMercado;
import com.planit.smsrenta.modelos.SmsMercado;
import com.planit.smsrenta.modelos.SmsUsuario;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Desarrollo_Planit
 */
public class MercadoBean implements Serializable {

    //Variables
    private SmsMercado mercadoView;
    private List<SmsMercado> mercadoListView;
    private List<String> nombresMercadosListView;

    private String buscar;
    private String estadoFoto;
    IMercadoDao mercadoDao;

    //Relacion con el controlador   
    Upload fileController;

    //Variables
    private boolean habilitarRegistro;
    private int estado; //Controla la operacion a realizar
    private String nombre;

    //Mensajes emergentes 
    private FacesMessage message;

    //Banderas    
    private boolean habilitarCancelar;

    public MercadoBean() {
        mercadoDao = new ImpMercadoDao();

        mercadoView = new SmsMercado();
        mercadoListView = new ArrayList<>();
        nombresMercadosListView = new ArrayList<>();

        fileController = new Upload();

        buscar = null;
        estadoFoto = "";
        buscar = null;
        estado = 0;
        nombre = "Registrar Mercado";

        habilitarRegistro = true;
        habilitarCancelar = true;

    }

    @PostConstruct
    public void init() {
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

    public List<String> getNombresMercadosListView() {
        mercadoListView = new ArrayList<>();
        nombresMercadosListView = new ArrayList<>();
        mercadoListView = mercadoDao.consultarMercados();
        for (int i = 0; i < mercadoListView.size(); i++) {
            nombresMercadosListView.add(mercadoListView.get(i).getMercadoNombre());
        }
        return nombresMercadosListView;
    }

    public void setNombresMercadosListView(List<String> nombresMercadosListView) {
        this.nombresMercadosListView = nombresMercadosListView;
    }

    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }

    public String getEstadoFoto() {
        return estadoFoto;
    }

    public void setEstadoFoto(String estadoFoto) {
        this.estadoFoto = estadoFoto;
    }

    public boolean isHabilitarRegistro() {
        return habilitarRegistro;
    }

    public void setHabilitarRegistro(boolean habilitarRegistro) {
        this.habilitarRegistro = habilitarRegistro;
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

    public boolean isHabilitarCancelar() {
        return habilitarCancelar;
    }

    public void setHabilitarCancelar(boolean habilitarCancelar) {
        this.habilitarCancelar = habilitarCancelar;
    }

    //Metodos
    public void registrarMercado() {

        mercadoDao.registrarMercado(mercadoView);
        mercadoListView = mercadoDao.consultarMercados();
        mercadoView = new SmsMercado();

        estadoFoto = "";
        habilitarRegistro = true;
    }

    public void modificarMercado() {
        mercadoDao.modificarMercado(mercadoView);
        mercadoListView = mercadoDao.consultarMercados();

        mercadoView = new SmsMercado();
        estadoFoto = "";
        habilitarRegistro = true;
    }

    public void eliminarMercado() {
        mercadoDao.eliminarMercado(mercadoView);
        mercadoListView = mercadoDao.consultarMercados();
        mercadoView = new SmsMercado();

        estadoFoto = "";
        nombre = "Registrar Mercado";
        habilitarRegistro = true;

    }

    public void filtrarMercados() {
        if (buscar == null) {
            mercadoListView = mercadoDao.consultarMercados();
        } else {
            mercadoListView = mercadoDao.filtrarMercados(buscar);
        }
    }

    //Subida de archivos
    public void uploadPhoto(FileUploadEvent e) throws IOException {
        try {
            UploadedFile uploadedPhoto = e.getFile();
            String destination;

            HashMap<String, String> map = getMapPathFotosMercado();
            destination = map.get("path");
            if (null != uploadedPhoto) {
                fileController.uploadFile(IOUtils.toByteArray(uploadedPhoto.getInputstream()), uploadedPhoto.getFileName(), destination);
                mercadoView.setMercadoFotoNombre(uploadedPhoto.getFileName());
                mercadoView.setMercadoFotoRuta(map.get("url") + uploadedPhoto.getFileName());
                estadoFoto = "Foto registrada con exito";

                habilitarRegistro = false;
            }
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Su foto (" + uploadedPhoto.getFileName() + ")  se ha guardado con exito.", ""));
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    //Metodos propios
    public void seleccionarCrud(int i) {
        estado = i;
        if (estado == 1) {
            habilitarCancelar = false;
            estadoFoto = "Foto de portada:" + mercadoView.getMercadoFotoNombre();
            nombre = "Modificar Mercado";
            habilitarRegistro = false;
        }
    }

    public void metodo() {
        if (estado == 0) {
            registrarMercado();
        } else if (estado == 1) {
            modificarMercado();
            habilitarCancelar = true;
            estado = 0;
            nombre = "Registrar Mercado";
        }
    }

    public void cancelar() {
        //Limpiamos objetos utilizados
        mercadoView = new SmsMercado();

        //Reiniciamos los objetos
        habilitarCancelar = true;
        estadoFoto = "";
        estado = 0;
        habilitarRegistro = true;
        nombre = "Registrar Mercado";
    }
}
