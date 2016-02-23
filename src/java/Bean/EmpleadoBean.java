/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Funciones.Upload;
import Controlador.Empleado;
import DAO.IHojaVidaDao;
import DAO.ImpHojaVidaDao;
import Modelo.SmsCiudad;
import Modelo.SmsEmpleado;
import Modelo.SmsHojavida;
import Modelo.SmsRol;
import Modelo.SmsUsuario;
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
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Desarrollo_Planit
 */
public class EmpleadoBean implements Serializable {

    //objetos necesarios en vista
    protected SmsUsuario usuarioView;
    protected SmsUsuario modUsuarioView;
    protected SmsEmpleado empleadoView;
    protected SmsUsuario DUsuarioView;
    protected SmsCiudad ciudadView;
    protected SmsRol rolView;
    protected SmsHojavida hojavidaView;
    protected List<SmsUsuario> empleadosListView;

    //Relacion con el controlador
    protected Empleado empleadoController;
    protected Upload fileController;

    //Variables
    private int estado; //Controla la operacion a realizar   

    private String buscar;
    private Boolean habilitarEditarSesion;   
    private String pass;
   
    private StreamedContent file;//Variable que permite descargar la hoja de vida del empleado

    private Boolean habilitarSubirFoto;
    private String subirFoto;
    private String estadoFoto;

    private UploadedFile archivo;
    private UploadedFile foto;

    private Boolean habilitarSubirArchivo;
    private String subirArchivo;
    private String estadoArchivo;
    private Boolean registroHojaVida;

    public EmpleadoBean() {
        usuarioView = new SmsUsuario();
        modUsuarioView = new SmsUsuario();
        empleadoView = new SmsEmpleado();
        DUsuarioView = new SmsUsuario();
        ciudadView = new SmsCiudad();
        rolView = new SmsRol();
        hojavidaView = new SmsHojavida();

        fileController = new Upload();
        empleadoController = new Empleado();
      
        buscar = null;
        habilitarEditarSesion = false;       
        estado = 0;

        habilitarSubirArchivo = false;
        subirFoto = "Subir fotografia";
        estadoFoto = "Foto sin subir";

        habilitarSubirFoto = false;
        subirArchivo = "Subir Hoja de vida";
        estadoArchivo = "Hoja de vida sin subir";

        registroHojaVida = false;
    }

    @PostConstruct
    public void init() {
        empleadosListView = empleadoController.consultarEmpleados();
    }

    //Getters & Setters
    public SmsUsuario getUsuarioView() {
        return usuarioView;
    }

    public void setUsuarioView(SmsUsuario usuarioView) {
        this.usuarioView = usuarioView;
    }

    public SmsEmpleado getEmpleadoView() {
        return empleadoView;
    }

    public void setEmpleadoView(SmsEmpleado empleadoView) {
        this.empleadoView = empleadoView;
    }

    public SmsCiudad getCiudadView() {
        return ciudadView;
    }

    public void setCiudadView(SmsCiudad ciudadView) {
        this.ciudadView = ciudadView;
    }

    public SmsRol getRolView() {
        return rolView;
    }

    public void setRolView(SmsRol rolView) {
        this.rolView = rolView;
    }

    public SmsHojavida getHojavidaView() {
        return hojavidaView;
    }

    public void setHojavidaView(SmsHojavida hojavidaView) {
        this.hojavidaView = hojavidaView;
    }

    public Boolean getRegistroHojaVida() {
        return registroHojaVida;
    }

    public void setRegistroHojaVida(Boolean registroHojaVida) {
        this.registroHojaVida = registroHojaVida;
    }

    public UploadedFile getArchivo() {
        return archivo;
    }

    public void setArchivo(UploadedFile archivo) {
        this.archivo = archivo;
    }

    public Empleado getEmpleadoController() {
        return empleadoController;
    }

    public void setEmpleadoController(Empleado empleadoController) {
        this.empleadoController = empleadoController;
    }

    public Upload getFileController() {
        return fileController;
    }

    public void setFileController(Upload fileController) {
        this.fileController = fileController;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }

    public Boolean getHabilitarSubirFoto() {
        return habilitarSubirFoto;
    }

    public void setHabilitarSubirFoto(Boolean habilitarSubirFoto) {
        this.habilitarSubirFoto = habilitarSubirFoto;
    }

    public String getSubirFoto() {
        return subirFoto;
    }

    public void setSubirFoto(String subirFoto) {
        this.subirFoto = subirFoto;
    }

    public String getEstadoFoto() {
        return estadoFoto;
    }

    public void setEstadoFoto(String estadoFoto) {
        this.estadoFoto = estadoFoto;
    }

    public UploadedFile getFoto() {
        return foto;
    }

    public void setFoto(UploadedFile foto) {
        this.foto = foto;
    }

    public Boolean getHabilitarSubirArchivo() {
        return habilitarSubirArchivo;
    }

    public void setHabilitarSubirArchivo(Boolean habilitarSubirArchivo) {
        this.habilitarSubirArchivo = habilitarSubirArchivo;
    }

    public String getSubirArchivo() {
        return subirArchivo;
    }

    public void setSubirArchivo(String subirArchivo) {
        this.subirArchivo = subirArchivo;
    }

    public String getEstadoArchivo() {
        return estadoArchivo;
    }

    public void setEstadoArchivo(String estadoArchivo) {
        this.estadoArchivo = estadoArchivo;
    }

    public List<SmsUsuario> getEmpleadosListView() {
        return empleadosListView;
    }

    public void setEmpleadosListView(List<SmsUsuario> empleadosListView) {
        this.empleadosListView = empleadosListView;
    }

    public SmsUsuario getModUsuarioView() {
        return modUsuarioView;
    }

    public void setModUsuarioView(SmsUsuario modUsuarioView) {
        this.modUsuarioView = modUsuarioView;
    }

    public Boolean getHabilitarEditarSesion() {
        return habilitarEditarSesion;
    }

    public void setHabilitarEditarSesion(Boolean habilitarEditarSesion) {
        this.habilitarEditarSesion = habilitarEditarSesion;
    }

    public SmsUsuario getDUsuarioView() {
        return DUsuarioView;
    }

    public void setDUsuarioView(SmsUsuario DUsuarioView) {
        this.DUsuarioView = DUsuarioView;
    }
    
    

    //Metodos que se comunican con el controlador    
    public void registrar() {

        //Asignamos al conductor los registro de la empresa
        usuarioView.setUsuarioRazonSocial("SMS Renta");
        usuarioView.setUsuarioNit("");
        rolView.setRolNombre("Empleado");
        
        if(usuarioView.getUsuarioFotoRuta()== null && hojavidaView.getHojaVidaRuta() == null){
        //asignamos al usuario la imagen de perfil default
        usuarioView.setUsuarioFotoRuta(fileController.getPathDefaultUsuario());
        usuarioView.setUsuarioFotoNombre(fileController.getNameDefaultUsuario());
        hojavidaView.setHojaVidaNombre(fileController.getNameDefaultHojasVida());
        hojavidaView.setHojaVidaRuta(fileController.getPathDefaultHojasVida());
        }
        
        registrarHojaVida(hojavidaView);
        empleadoController.registrarUsuario(usuarioView, ciudadView, rolView);
        empleadoController.registrarEmpleado(usuarioView, hojavidaView);

        estadoFoto = "Foto sin subir";
        subirFoto = "Subir Fotografia";
        habilitarSubirFoto = false;

        estadoArchivo = "Hoja de vida sin subir";
        subirArchivo = "Subir Hoja de vida";
        habilitarSubirArchivo = false;
        registroHojaVida = false;

        usuarioView = new SmsUsuario();
        ciudadView = new SmsCiudad();
        rolView = new SmsRol();
        empleadoView = new SmsEmpleado();
        hojavidaView = new SmsHojavida();
        empleadosListView = empleadoController.consultarEmpleados();
    }

    public String modificar() {
        rolView.setRolNombre("Empleado");
        empleadoController.modificarUsuario(modUsuarioView, ciudadView, rolView);
        empleadoController.modificarEmpleado(modUsuarioView, hojavidaView, empleadoView);

        estadoFoto = "Foto sin subir";
        subirFoto = "Subir Fotografia";
        habilitarSubirFoto = false;

        estadoArchivo = "Hoja de vida sin subir";
        subirArchivo = "Subir Hoja de vida";
        habilitarSubirArchivo = false;
        registroHojaVida = false;

        modUsuarioView = new SmsUsuario();
        ciudadView = new SmsCiudad();
        rolView = new SmsRol();
        empleadoView = new SmsEmpleado();
        hojavidaView = new SmsHojavida();

        empleadosListView = empleadoController.consultarEmpleados();
        estado = 0;
        String ruta = "RAdminPEmpleado";
        return ruta;
    }

    public void eliminar() {
        empleadoController.eliminarEmpleado(DUsuarioView);
        
        DUsuarioView = new SmsUsuario();
        ciudadView = new SmsCiudad();
        rolView = new SmsRol();
        empleadoView = new SmsEmpleado();
        hojavidaView = new SmsHojavida();

        empleadosListView = empleadoController.consultarEmpleados();
    }

    public void filtrar() {
        empleadosListView = new ArrayList<>();
        if (buscar == null) {
            empleadosListView = empleadoController.consultarEmpleados();
        } else {
            empleadosListView = empleadoController.filtrarEmpleados(buscar);
        }
    }
    
    public void registrarHojaVida(SmsHojavida h){
        IHojaVidaDao hojaDao = new ImpHojaVidaDao();
        hojaDao.registrarHojaVida(h);
    }

    //Metodos propios
    public String irModificarEmpleados() {
        estado = 1;

        ciudadView = modUsuarioView.getSmsCiudad();
        rolView = modUsuarioView.getSmsRol();
        empleadoView = empleadoController.consultarEmpleado(modUsuarioView).get(0);

        if (empleadoView.getSmsHojavida() != null) {
            hojavidaView = empleadoView.getSmsHojavida();
        }

        if (modUsuarioView.getUsuarioFotoNombre() != null || modUsuarioView.getUsuarioFotoRuta() != null) {
            subirFoto = "Modificar Fotografia";
            estadoFoto = "Foto subida:" + modUsuarioView.getUsuarioFotoNombre();
            habilitarSubirFoto = false;
        } else {
            subirFoto = "Subir Fotografia";
            habilitarSubirFoto = false;
        }

        if (hojavidaView.getHojaVidaNombre() != null && hojavidaView.getHojaVidaRuta() != null) {
            subirArchivo = "Modificar Hoja de vida";
            estadoArchivo = "Hoja subida:" + hojavidaView.getHojaVidaNombre();
            habilitarSubirArchivo = false;
        } else {
            subirArchivo = "Subir Hoja vida";
            habilitarSubirArchivo = false;
        }
        String ruta = "AdminPEEmpleado";
        return ruta;
    }

    public String regresar() {
        modUsuarioView = new SmsUsuario();
        ciudadView = new SmsCiudad();
        hojavidaView = new SmsHojavida();

        estadoFoto = "Foto sin subir";
        subirFoto = "Subir Fotografia";
        habilitarSubirFoto = false;

        estadoArchivo = "Hoja de vida sin subir";
        subirArchivo = "Subir Hoja de vida";
        habilitarSubirArchivo = false;
        registroHojaVida = false;

        habilitarEditarSesion = false;
        String ruta = "AdminPEmpleado";
        return ruta;
    }

    public void habilitarEdicion() {
        habilitarEditarSesion = true;
        pass = modUsuarioView.getUsuarioPassword();
        modUsuarioView.setUsuarioPassword(null);
        modUsuarioView.setUsuarioRememberToken(null);
    }

    public void deshabilitarEdicion() {
        habilitarEditarSesion = false;
        modUsuarioView.setUsuarioPassword(pass);
        modUsuarioView.setUsuarioRememberToken(pass);
    }
   

    //Subida de archivos
    public void uploadPhoto(FileUploadEvent e) throws IOException {
        try {
            UploadedFile uploadedPhoto = e.getFile();
            String destination;

            HashMap<String, String> map = fileController.getMapPathFotosUsuario();
            destination = map.get("path");
            if (null != uploadedPhoto) {
                fileController.uploadFile(IOUtils.toByteArray(uploadedPhoto.getInputstream()), uploadedPhoto.getFileName(), destination);
                usuarioView.setUsuarioFotoNombre(uploadedPhoto.getFileName());
                usuarioView.setUsuarioFotoRuta(map.get("url") + uploadedPhoto.getFileName());
                modUsuarioView.setUsuarioFotoNombre(uploadedPhoto.getFileName());
                modUsuarioView.setUsuarioFotoRuta(map.get("url") + uploadedPhoto.getFileName());
                habilitarSubirFoto = true;
                if (estado == 0) {
                    estadoFoto = "Foto Subida con exito";
                } else if (estado == 1) {
                    estadoFoto = "Foto actualizada con exito";
                }
            }
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Su foto (" + uploadedPhoto.getFileName() + ")  se ha guardado con exito.", ""));
        } catch (Exception ex) {
            ex.getMessage();
        }

    }

    //Subida de archivos
    public void uploadDoc(FileUploadEvent e) throws IOException {
        try {
            UploadedFile uploadedDoc = e.getFile();
            String destination;

            HashMap<String, String> map = fileController.getMapPathHojasVida();
            destination = map.get("path");
            if (null != uploadedDoc) {
                fileController.uploadFile(IOUtils.toByteArray(uploadedDoc.getInputstream()), uploadedDoc.getFileName(), destination);
                hojavidaView.setHojaVidaNombre(uploadedDoc.getFileName());
                hojavidaView.setHojaVidaRuta(map.get("url") + uploadedDoc.getFileName());
                habilitarSubirArchivo = true;
                if (estado == 0) {
                    estadoArchivo = "Hoja de vida subida con exito";
                } else if (estado == 1) {
                    estadoArchivo = "Hoja de vida actualizada con exito";
                }

                registrarHojaVida(hojavidaView);
                registroHojaVida = true;
            }

            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Su Hoja de vida (" + uploadedDoc.getFileName() + ")  se ha guardado con exito.", ""));
        } catch (Exception ex) {
            ex.getMessage();
        }
    }
    
    
}
