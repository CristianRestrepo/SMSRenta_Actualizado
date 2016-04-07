/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.controladores;

import com.planit.smsrenta.metodos.MD5;
import com.planit.smsrenta.metodos.Upload;
import com.planit.smsrenta.dao.ICiudadDao;
import com.planit.smsrenta.dao.INacionalidadDao;
import com.planit.smsrenta.dao.IRolDao;
import com.planit.smsrenta.dao.IUsuarioDao;
import com.planit.smsrenta.dao.ImpCiudadDao;
import com.planit.smsrenta.dao.ImpNacionalidadDao;
import com.planit.smsrenta.dao.ImpRolDao;
import com.planit.smsrenta.dao.ImpUsuarioDao;
import com.planit.smsrenta.metodos.Sesion;
import static com.planit.smsrenta.metodos.Upload.getMapPathFotosUsuario;
import com.planit.smsrenta.modelos.SmsUsuario;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Desarrollo_Planit
 */
//Bean para usuario
public class UsuarioBean implements Serializable {

    //Instanciacion de los objetos    
    protected SmsUsuario usuarioView;
    protected List<SmsUsuario> usuariosListView;
    protected List<String> nombresUsuarios;

    private String nuevaContraseña;
    private String repitaContraseña;

    //Controles de componentes
    boolean habilitado;

    //Relacion con clases  
    protected Upload fileController;  
    protected Sesion sesionController;
    //Sesion
    protected SmsUsuario Usuario;

    //Conexion con el Dao
    ICiudadDao ciudadDao;
    IRolDao rolDao;
    
    INacionalidadDao nacionalidadDao;

    //Variables
    protected String password;
    protected String estadoFoto;
   
    public UsuarioBean() {
        usuarioView = new SmsUsuario();
        nombresUsuarios = new ArrayList<>();

        nuevaContraseña = "";
        repitaContraseña = "";
        estadoFoto = "";

        fileController = new Upload();
        sesionController = new Sesion();
        
        ciudadDao = new ImpCiudadDao();
        rolDao = new ImpRolDao();
        nacionalidadDao = new ImpNacionalidadDao();

    }

    //Getters & Setters  
    public List<SmsUsuario> getUsuariosListView() {
        return usuariosListView;
    }

    public void setUsuariosListView(List<SmsUsuario> usuariosListView) {
        this.usuariosListView = usuariosListView;
    }

    public SmsUsuario getUsuarioView() {
        return usuarioView;
    }

    public void setUsuarioView(SmsUsuario usuarioView) {
        this.usuarioView = usuarioView;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public SmsUsuario getUsuario() {
        return Usuario;
    }

    public void setUsuario(SmsUsuario Usuario) {
        this.Usuario = Usuario;
    }

    public String getEstadoFoto() {
        return estadoFoto;
    }

    public void setEstadoFoto(String estadoFoto) {
        this.estadoFoto = estadoFoto;
    }

    public String getNuevaContraseña() {
        return nuevaContraseña;
    }

    public void setNuevaContraseña(String nuevaContraseña) {
        this.nuevaContraseña = nuevaContraseña;
    }

    public String getRepitaContraseña() {
        return repitaContraseña;
    }

    public void setRepitaContraseña(String repitaContraseña) {
        this.repitaContraseña = repitaContraseña;
    }

    public List<String> getNombresUsuarios() {
        return nombresUsuarios;
    }

    public void setNombresUsuarios(List<String> nombresUsuarios) {
        this.nombresUsuarios = nombresUsuarios;
    }    
    
    //Declaracion de metodos
    //Metodos CRUD
    public void modificarPerfil() {
        IUsuarioDao usuarioDao = new ImpUsuarioDao();
        MD5 md = new MD5();
        // en caso de modificar las contraseñas estas se encriptan de nuevo
        if (!nuevaContraseña.isEmpty()) {
            Usuario.setUsuarioPassword(md.getMD5(nuevaContraseña));
            Usuario.setUsuarioRememberToken(md.getMD5(repitaContraseña));
        }

        Usuario.setSmsCiudad(ciudadDao.consultarCiudad(Usuario.getSmsCiudad()).get(0));

        usuarioDao.modificarUsuario(Usuario);
        estadoFoto = "Foto subida:" + Usuario.getUsuarioFotoNombre();

        nuevaContraseña = "";
        repitaContraseña = "";
    }

    public String ir_editarPerfil() {
        Usuario = sesionController.obtenerSesion();
        estadoFoto = "Foto subida:" + Usuario.getUsuarioFotoNombre();
        
        String ruta = "";
        switch (Usuario.getSmsRol().getIdRol()) {
            case 1:
                ruta = "AdminPEditarPerfil";
                break;
            case 2:
                ruta = "AdminSEdicionPerfil";
                break;
            case 3:
                ruta = "ClienteEdicionPerfil";
                break;
            case 4:                
                ruta = "CondEdicionPerfil";
                break;
            case 5:
                ruta = "ProveedorEdicionPerfil";
                break;
        }
        return ruta;
    }

    //Subida de archivos
    public void upload(FileUploadEvent e) throws IOException {
        try {
            UploadedFile uploadedPhoto = e.getFile();
            String destination;

            HashMap<String, String> map = getMapPathFotosUsuario();
            destination = map.get("path");
            if (null != uploadedPhoto) {
                fileController.uploadFile(IOUtils.toByteArray(uploadedPhoto.getInputstream()), uploadedPhoto.getFileName(), destination);
                Usuario.setUsuarioFotoNombre(uploadedPhoto.getFileName());
                Usuario.setUsuarioFotoRuta(map.get("url") + uploadedPhoto.getFileName());
                estadoFoto = "Foto actualizada con exito";
            }
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Su foto (" + uploadedPhoto.getFileName() + ")  se ha guardado con exito.", ""));
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

}
