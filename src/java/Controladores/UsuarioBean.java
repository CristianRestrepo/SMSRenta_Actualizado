/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Funciones.MD5;
import Funciones.Upload;
import DAO.ICiudadDao;
import DAO.INacionalidadDao;
import DAO.IRolDao;
import DAO.IUsuarioDao;
import DAO.ImpCiudadDao;
import DAO.ImpNacionalidadDao;
import DAO.ImpRolDao;
import DAO.ImpUsuarioDao;
import static Funciones.Upload.getMapPathFotosUsuario;
import Modelo.SmsUsuario;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
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

    //Relacion con el controlador   
    protected Upload fileController;

    //Contexto
    private FacesMessage message;

    //Sesion
    protected SmsUsuario Usuario;

    //Conexion con el Dao
    ICiudadDao ciudadDao;
    IRolDao rolDao;
    
    INacionalidadDao nacionalidadDao;

    //Variables
    protected String password;
    protected String estadoFoto;

    //Contexto y sesion
    private HttpSession httpSession;

    public UsuarioBean() {

        usuarioView = new SmsUsuario();
        nombresUsuarios = new ArrayList<>();

        nuevaContraseña = "";
        repitaContraseña = "";

        Usuario = new SmsUsuario();
        estadoFoto = "";

        fileController = new Upload();
      
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

    //Metodos para iniciar Sesion
    public String iniciarSesion() {
        String ruta = "/login.xhtml";
        MD5 md = new MD5();
        SmsUsuario user;
        IUsuarioDao usuarioDao = new ImpUsuarioDao();       
               
        if (!usuarioDao.consultarDatosSesionUsuario(usuarioView).isEmpty()) {//valida si el usuario existe en la BD        
            user = usuarioDao.consultarUsuario(usuarioView).get(0);
            usuarioView.setUsuarioPassword(md.getMD5(usuarioView.getUsuarioPassword()));
            if (user.getUsuarioEstadoUsuario() == 1) {//Evalua el estado de la cuenta de usuario, si esta activa o inactiva
                if (user.getUsuarioEmail().equalsIgnoreCase(usuarioView.getUsuarioEmail()) && user.getUsuarioPassword().equalsIgnoreCase(usuarioView.getUsuarioPassword())) {
                    
                    httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                    httpSession.setAttribute("Sesion", user);

                    switch (user.getSmsRol().getRolNombre()) {
                        case "Administrador Principal":
                            ruta = "AdminPPrincipal";
                            break;
                        case "Administrador Secundario":
                            ruta = "AdminSGeneral";
                            break;
                        case "Cliente":
                            ruta = "ClienteDash";
                            break;
                        case "Conductor":
                            ruta = "ConductorDash";
                            break;
                        case "Proveedor":
                            ruta = "ProveedorDash";
                            break;
                    }

                    Usuario = (SmsUsuario) httpSession.getAttribute("Sesion");
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Acceso Correcto", "Bienvenid@: " + Usuario.getUsuarioNombre());
                } else {
                    message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario o contraseña incorrecto", null);
                    usuarioView = new SmsUsuario();
                }
            } else if (user.getUsuarioEstadoUsuario() == 0) {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario inactivo, imposible iniciar sesion", null);
            }
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario no existente", null);
            usuarioView = new SmsUsuario();
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
        usuarioView = new SmsUsuario();

        return ruta;
    }

    public String cerrarSesion() {
        usuarioView = new SmsUsuario();
        Usuario = new SmsUsuario();
        String ruta = "Login";
        httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        httpSession.invalidate();
        return ruta;
    }

    public String ir_editarPerfil() {
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
                //modEmpleadoView = empleadoController.consultarEmpleado(modUsuarioView).get(0);
                // hojavidaView = modEmpleadoView.getSmsHojavida();
                // estadoArchivo = "Hoja subida:" + hojavidaView.getHojaVidaNombre();
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
