/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.controladores;

import com.planit.smsrenta.dao.IMarcaDao;
import com.planit.smsrenta.dao.ImpMarcaDao;
import com.planit.smsrenta.metodos.Upload;
import static com.planit.smsrenta.metodos.Upload.getMapPathFotosMarcas;
import static com.planit.smsrenta.metodos.Upload.getNameDefaultMarca;
import static com.planit.smsrenta.metodos.Upload.getPathDefaultMarca;
import com.planit.smsrenta.modelos.SmsMarca;
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
public class MarcaBean implements Serializable {

    //Objetos de vista
    private SmsMarca marcaView;
    private List<SmsMarca> marcasListView;
    private List<String> nombresMarcaView;

    //Conexion con el DAO
    IMarcaDao marcaDao;

    //Controladores
    Upload fileController;

    //Variables
    private int estado; //Controla la operacion a realizar
    private String nombre;
    private String buscar;
    protected String estadoFoto;

    //Banderas    
    private boolean habilitarCancelar;

    public MarcaBean() {
        marcaView = new SmsMarca();
        marcasListView = new ArrayList<>();
        nombresMarcaView = new ArrayList<>();

        buscar = "";
        estado = 0;
        nombre = "Registrar Marca";

        habilitarCancelar = true;

        marcaDao = new ImpMarcaDao();
        fileController = new Upload();
        estadoFoto = "Foto sin subir";
    }

    @PostConstruct
    public void init() {
        marcasListView = marcaDao.mostrarMarcas();
    }

    /* GETTERS Y SETTERS **********************************************************************/
    public SmsMarca getMarcaView() {
        return marcaView;
    }

    public void setMarcaView(SmsMarca marcaView) {
        this.marcaView = marcaView;
    }

    public List<SmsMarca> getMarcasListView() {
        return marcasListView;
    }

    public void setMarcasListView(List<SmsMarca> marcasListView) {
        this.marcasListView = marcasListView;
    }

    public List<String> getNombresMarcaView() {
        nombresMarcaView = new ArrayList<>();
        marcasListView = marcaDao.mostrarMarcas();
        for (int i = 0; i < marcasListView.size(); i++) {
            nombresMarcaView.add(marcasListView.get(i).getMarcaNombre());
        }
        return nombresMarcaView;
    }

    public void setNombresMarcaView(List<String> nombresMarcaView) {
        this.nombresMarcaView = nombresMarcaView;
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

    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }

    public String getEstadoFoto() {
        return estadoFoto;
    }

    public boolean isHabilitarCancelar() {
        return habilitarCancelar;
    }

    public void setHabilitarCancelar(boolean habilitarCancelar) {
        this.habilitarCancelar = habilitarCancelar;
    }

    public void setEstadoFoto(String estadoFoto) {
        this.estadoFoto = estadoFoto;
    }

    //Metodos
    public void modificar() {
        marcaDao.modificarMarca(marcaView);
        marcaView = new SmsMarca();
        marcasListView = marcaDao.mostrarMarcas();
    }

    public void eliminar() {
        marcaDao.eliminarMarca(marcaView);

        marcaView = new SmsMarca();
        nombre = "Registrar Marca";
        estadoFoto = "Foto sin subir";
        estado = 0;

        marcasListView = marcaDao.mostrarMarcas();
    }

    public void registrar() {
        if (marcaView.getMarcaFotoRuta() == null) {
            marcaView.setMarcaFotoRuta(getPathDefaultMarca());
            marcaView.setMarcaFotoNombre(getNameDefaultMarca());
        }

        marcaDao.registrarMarca(marcaView);
        marcaView = new SmsMarca();
        marcasListView = marcaDao.mostrarMarcas();
    }

    public void filtrar() {
        marcasListView = new ArrayList<>();
        if (buscar.equals("")) {
            marcasListView = marcaDao.mostrarMarcas();
        } else {
            marcasListView = marcaDao.filtrarMarca(buscar);
            marcaView = new SmsMarca();
        }
    }

    public List<SmsMarca> consultarMarca(SmsMarca marca) {
        marcasListView = new ArrayList<>();
        marcasListView = marcaDao.consultarMarca(marca);
        return marcasListView;
    }

    //Metodos Propios
    public void metodo() {
        if (estado == 0) {
            registrar();
            estadoFoto = "Foto sin subir";
        } else if (estado == 1) {
            modificar();
            estadoFoto = "Foto sin subir";
            habilitarCancelar = true;
            estado = 0;
            nombre = "Registrar Marca";
        }
    }

    public void seleccionarCRUD(int i) {
        estado = i;
        if (estado == 1) {
            habilitarCancelar = false;
            nombre = "Modificar Marca";
            estadoFoto = "Foto subida:" + marcaView.getMarcaFotoNombre();
        }
    }

    //Subida de archivos
    public void uploadPhoto(FileUploadEvent e) throws IOException {
        try {
            UploadedFile uploadedPhoto = e.getFile();
            String destination;

            HashMap<String, String> map = getMapPathFotosMarcas();
            destination = map.get("path");
            if (null != uploadedPhoto) {
                fileController.uploadFile(IOUtils.toByteArray(uploadedPhoto.getInputstream()), uploadedPhoto.getFileName(), destination);
                marcaView.setMarcaFotoNombre(uploadedPhoto.getFileName());
                marcaView.setMarcaFotoRuta(map.get("url") + uploadedPhoto.getFileName());
                estadoFoto = "Foto actualizada con exito";
            }
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Su foto (" + uploadedPhoto.getFileName() + ")  se ha guardado con exito.", ""));
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    public void cancelar() {
        //Limpiamos objetos utilizados
        marcaView = new SmsMarca();
        estado = 0;
        //Reiniciamos los objetos
        habilitarCancelar = true;
        estadoFoto = "Foto sin subir";
        nombre = "Registrar Marca";
    }

}
