/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.IEmpleadoDao;
import DAO.IHojaVidaDao;
import DAO.IProveedorDao;
import DAO.ImpEmpleadoDao;
import DAO.ImpHojaVidaDao;
import DAO.ImpProveedorDao;
import Funciones.GenerarPassword;
import Funciones.MD5;
import Funciones.SendEmail;
import static Funciones.Upload.getMapPathFotosUsuario;
import static Funciones.Upload.getMapPathHojasVida;
import static Funciones.Upload.getNameDefaultHojasVida;
import static Funciones.Upload.getNameDefaultUsuario;
import static Funciones.Upload.getPathDefaultHojasVida;
import static Funciones.Upload.getPathDefaultUsuario;
import Modelo.SmsCiudad;
import Modelo.SmsEmpleado;
import Modelo.SmsHojavida;
import Modelo.SmsProveedor;
import Modelo.SmsReservacion;
import Modelo.SmsRol;
import Modelo.SmsUsuario;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class EmpleadoBean extends UsuarioBean implements Serializable {

    //objetos necesarios en vista
    private SmsEmpleado empleadoView;
    private SmsProveedor proveedorView;

    private SmsHojavida hojavidaView;
    private List<SmsEmpleado> empleadosListView;

    //Variables   
    private String buscar;
    private StreamedContent file;//Variable que permite descargar la hoja de vida del empleado
    private UploadedFile archivo;
    private UploadedFile foto;

    private String estadoArchivo;

    private int operacion; //Controla la operacion a realizar
    private String nombreOperacion;

    //Banderas    
    private boolean habilitarCancelar;
    private boolean hojavidaActualizada;

    //Conexion con el dao
    IEmpleadoDao empleadoDao;
    IHojaVidaDao hojaDao;
    IProveedorDao proveedorDao;

    public EmpleadoBean() {
        super();
        hojavidaView = new SmsHojavida();
        proveedorView = new SmsProveedor();
        empleadoView = new SmsEmpleado();
        empleadosListView = new ArrayList<>();

        buscar = null;
        habilitarCancelar = true;
        hojavidaActualizada = false;

        operacion = 0;
        nombreOperacion = "Registrar Empleado";

        estadoFoto = "Foto sin subir";
        estadoArchivo = "Hoja de vida sin subir";

        empleadoDao = new ImpEmpleadoDao();
        hojaDao = new ImpHojaVidaDao();
        proveedorDao = new ImpProveedorDao();
    }

    @PostConstruct
    public void init() {
        empleadosListView = empleadoDao.mostrarEmpleados();
    }

    //Getters & Setters   
    public SmsEmpleado getEmpleadoView() {
        return empleadoView;
    }

    public void setEmpleadoView(SmsEmpleado empleadoView) {
        this.empleadoView = empleadoView;
    }

    public SmsHojavida getHojavidaView() {
        return hojavidaView;
    }

    public void setHojavidaView(SmsHojavida hojavidaView) {
        this.hojavidaView = hojavidaView;
    }

    public UploadedFile getArchivo() {
        return archivo;
    }

    public void setArchivo(UploadedFile archivo) {
        this.archivo = archivo;
    }

    public UploadedFile getFoto() {
        return foto;
    }

    public void setFoto(UploadedFile foto) {
        this.foto = foto;
    }

    public List<SmsEmpleado> getEmpleadosListView() {
        return empleadosListView;
    }

    public void setEmpleadosListView(List<SmsEmpleado> empleadosListView) {
        this.empleadosListView = empleadosListView;
    }

    public SmsProveedor getProveedorView() {
        return proveedorView;
    }

    public void setProveedorView(SmsProveedor proveedorView) {
        this.proveedorView = proveedorView;
    }

    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }

    public String getEstadoArchivo() {
        return estadoArchivo;
    }

    public void setEstadoArchivo(String estadoArchivo) {
        this.estadoArchivo = estadoArchivo;
    }

    public String getNombreOperacion() {
        return nombreOperacion;
    }

    public void setNombreOperacion(String nombreOperacion) {
        this.nombreOperacion = nombreOperacion;
    }

    public boolean isHabilitarCancelar() {
        return habilitarCancelar;
    }

    public void setHabilitarCancelar(boolean habilitarCancelar) {
        this.habilitarCancelar = habilitarCancelar;
    }

    //Metodos que se comunican con el controlador    
    public void registrarEmpleado() {
        rolView.setRolNombre("Conductor");

        //Si el usuario no registra foto y hoja de vida se asignas unas default
        if (usuarioView.getUsuarioFotoRuta() == null && hojavidaView.getHojaVidaRuta() == null) {
            //asignamos al usuario la imagen de perfil default
            usuarioView.setUsuarioFotoRuta(getPathDefaultUsuario());
            usuarioView.setUsuarioFotoNombre(getNameDefaultUsuario());
            hojavidaView.setHojaVidaNombre(getNameDefaultHojasVida());
            hojavidaView.setHojaVidaRuta(getPathDefaultHojasVida());
        }

        //Se genera un login y un pass aleatorio que se le envia al proveedor
        MD5 md = new MD5();
        GenerarPassword pass = new GenerarPassword();
        SendEmail email = new SendEmail();

        password = pass.generarPass(6);//Generamos pass aleatorio
        //Asignamos email como nombre de sesion
        usuarioView.setUsuarioLogin(usuarioView.getUsuarioEmail());

        //Encriptamos las contraseñas
        usuarioView.setUsuarioPassword(md.getMD5(password));//Se encripta la contreseña
        usuarioView.setUsuarioRememberToken(md.getMD5(password));

        registrarHojaVida(hojavidaView);

        ciudadView = ciudadDao.consultarCiudad(ciudadView).get(0);
        usuarioView.setSmsCiudad(ciudadView);//Asociamos una ciudad a un usuario

        rolView = rolDao.consultarRol(rolView).get(0);
        usuarioView.setSmsRol(rolView);//Asociamos un rol a un usuario

        usuarioView.setUsuarioEstadoUsuario(1);//Asignamos un estado de cuenta

        //registramos el usuario
        usuarioDao.registrarUsuario(usuarioView);

        //Se consulta toda la informacion de usuario y proveedor y se relaciona al empleado
        proveedorView = proveedorDao.consultarProveedor(proveedorView).get(0);
        usuarioView = usuarioDao.consultarUsuario(usuarioView).get(0);
       

        empleadoView.setSmsUsuario(usuarioView);
        empleadoView.setSmsProveedor(proveedorView);

        //se verifica si se registro una hoja de vida
        if (hojavidaView.getHojaVidaNombre() != null && hojavidaView.getHojaVidaRuta() != null) {
            hojavidaView = hojaDao.consultarHojaVida(hojavidaView).get(0);
            empleadoView.setSmsHojavida(hojavidaView);//se relaciona la hoja de vida al empleado
        }

        empleadoDao.registrarEmpleado(empleadoView);//Registramos al usuario como empleado
        email.sendEmailConductor(usuarioView, password);//Se envia correo con datos de sesion a conductor

        //Se limpian objetos y se reinician banderas
        estadoFoto = "Foto sin subir";
        estadoArchivo = "Hoja de vida sin subir";

        usuarioView = new SmsUsuario();
        proveedorView = new SmsProveedor();
        ciudadView = new SmsCiudad();
        rolView = new SmsRol();
        empleadoView = new SmsEmpleado();
        hojavidaView = new SmsHojavida();
        empleadosListView = empleadoDao.mostrarEmpleados();
    }

    public void modificarPerfilEmpleado() {
        MD5 md = new MD5();

        usuarioView.setUsuarioPassword(md.getMD5(usuarioView.getUsuarioPassword()));
        usuarioView.setUsuarioRememberToken(md.getMD5(usuarioView.getUsuarioRememberToken()));

        ciudadView = ciudadDao.consultarCiudad(ciudadView).get(0);
        usuarioView.setSmsCiudad(ciudadView);//Asociamos una ciudad a un usuario

        usuarioDao.modificarUsuario(usuarioView);
        empleadoView.setSmsUsuario(usuarioView);

        if (hojavidaView.getHojaVidaNombre() != null && hojavidaView.getHojaVidaRuta() != null) {

            hojavidaView = hojaDao.consultarHojaVida(hojavidaView).get(0);
            empleadoView.setSmsHojavida(hojavidaView);
        }

        empleadoDao.modificarEmpleado(empleadoView);
        estadoArchivo = "Hoja subida:" + hojavidaView.getHojaVidaNombre();

    }

    public void modificarEmpleado() {
        rolView.setRolNombre("Empleado");

        usuarioDao.modificarUsuario(usuarioView);
        empleadoView.setSmsUsuario(usuarioView);

        if (hojavidaView.getHojaVidaNombre() != null && hojavidaView.getHojaVidaRuta() != null) {
            hojavidaView = hojaDao.consultarHojaVida(hojavidaView).get(0);
            empleadoView.setSmsHojavida(hojavidaView);
        }

        empleadoDao.modificarEmpleado(empleadoView);

        estadoFoto = "Foto sin subir";
        estadoArchivo = "Hoja de vida sin subir";

        usuarioView = new SmsUsuario();
        ciudadView = new SmsCiudad();
        rolView = new SmsRol();
        hojavidaView = new SmsHojavida();
        proveedorView = new SmsProveedor();
        empleadosListView = empleadoDao.mostrarEmpleados();
    }

    public void eliminarEmpleado() {
        usuarioView = empleadoView.getSmsUsuario();
        hojavidaView = empleadoView.getSmsHojavida();
        
        usuarioDao.eliminarUsuario(usuarioView);
        hojaDao.eliminarHojaVida(hojavidaView);
        
        usuarioView = new SmsUsuario();
        ciudadView = new SmsCiudad();
        rolView = new SmsRol();
        empleadoView = new SmsEmpleado();
        hojavidaView = new SmsHojavida();
        proveedorView = new SmsProveedor();
        empleadosListView = empleadoDao.mostrarEmpleados();
    }

    public void filtrarEmpleados() {
        usuariosListView = new ArrayList<>();
        if (buscar == null) {
            usuariosListView = empleadoDao.consultarUsuariosEmpleados();
        } else {
            usuariosListView = empleadoDao.filtrarUsuariosEmpleados(buscar);
        }
    }

    public void registrarHojaVida(SmsHojavida h) {
        hojaDao.registrarHojaVida(h);
    }

    //Metodos Propios
    public void metodo() {
        if (operacion == 0) {
            registrarEmpleado();
        } else if (operacion == 1) {
            modificarEmpleado();
            //Reiniciamos banderas

            habilitarCancelar = true;
            operacion = 0;
            hojavidaActualizada = false;
            nombreOperacion = "Registrar Empleado";

        }
    }

    public void seleccionarCRUD(int i) {
        operacion = i;
        if (operacion == 1) {

            usuarioView = empleadoView.getSmsUsuario();
            usuarioView = usuarioDao.consultarUsuario(usuarioView).get(0);
            if (empleadoView.getSmsHojavida() != null) {
                hojavidaView = empleadoView.getSmsHojavida();
            }

            if (usuarioView.getUsuarioFotoNombre() != null || usuarioView.getUsuarioFotoRuta() != null) {
                estadoFoto = "Foto subida:" + usuarioView.getUsuarioFotoNombre();
            }

            if (hojavidaView.getHojaVidaNombre() != null && hojavidaView.getHojaVidaRuta() != null) {
                estadoArchivo = "Hoja subida:" + hojavidaView.getHojaVidaNombre();
            }

            proveedorView = empleadoView.getSmsProveedor();
            ciudadView = usuarioView.getSmsCiudad();
            rolView = usuarioView.getSmsRol();

            habilitarCancelar = false;
            nombreOperacion = "Modificar Empleado";

        }
    }

    public void cancelar() {
        //Limpiamos objetos utilizados
        proveedorView = new SmsProveedor();
        empleadoView = new SmsEmpleado();
        usuarioView = new SmsUsuario();
        usuarioView = new SmsUsuario();
        ciudadView = new SmsCiudad();
        rolView = new SmsRol();

        //Reiniciamos los objetos
        habilitarCancelar = true;
        nombreOperacion = "Registrar Proveedor";
        estadoFoto = "Foto sin subir";
        estadoArchivo = "Hoja de vida sin subir";

    }

    //Subida de archivos
    public void uploadDoc(FileUploadEvent e) throws IOException {
        try {
            UploadedFile uploadedDoc = e.getFile();
            String destination;

            HashMap<String, String> map = getMapPathHojasVida();
            destination = map.get("path");
            if (null != uploadedDoc) {
                fileController.uploadFile(IOUtils.toByteArray(uploadedDoc.getInputstream()), uploadedDoc.getFileName(), destination);
                hojavidaView.setHojaVidaNombre(uploadedDoc.getFileName());
                hojavidaView.setHojaVidaRuta(map.get("url") + uploadedDoc.getFileName());
                if (operacion == 0) {
                    estadoArchivo = "Hoja de vida subida con exito";
                } else if (operacion == 1) {
                    estadoArchivo = "Hoja de vida actualizada con exito";
                    hojavidaActualizada = true;
                }
                
            if(hojavidaActualizada){
                registrarHojaVida(hojavidaView);
            }
                
            }

            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Su Hoja de vida (" + uploadedDoc.getFileName() + ")  se ha guardado con exito.", ""));
        } catch (Exception ex) {
            ex.getMessage();
        }
    }
    
    //Subida de archivos
    public void uploadPhoto(FileUploadEvent e) throws IOException {
        try {
            UploadedFile uploadedPhoto = e.getFile();
            String destination;

            HashMap<String, String> map = getMapPathFotosUsuario();
            destination = map.get("path");
            if (null != uploadedPhoto) {
                fileController.uploadFile(IOUtils.toByteArray(uploadedPhoto.getInputstream()), uploadedPhoto.getFileName(), destination);
                usuarioView.setUsuarioFotoNombre(uploadedPhoto.getFileName());
                usuarioView.setUsuarioFotoRuta(map.get("url") + uploadedPhoto.getFileName());
                estadoFoto = "Foto actualizada con exito";
            }
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Su foto (" + uploadedPhoto.getFileName() + ")  se ha guardado con exito.", ""));
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    public List<SmsEmpleado> consultarEmpleadosDisponibles(SmsReservacion reserva, SmsCiudad ciudad) {

        empleadosListView = new ArrayList<>();

        SimpleDateFormat formatDate;
        SimpleDateFormat formatTime;
        formatDate = new SimpleDateFormat("yyyy-MM-dd");
        formatTime = new SimpleDateFormat("HH:mm:ss");

        String FechaInicio = formatDate.format(reserva.getReservacionFechaInicio());
        String FechaLlegada = formatDate.format(reserva.getReservacionFechaLlegada());
        String HoraInicio = formatTime.format(reserva.getReservacionHoraInicio());
        String HoraLlegada = formatTime.format(reserva.getReservacionHoraLlegada());

        Calendar calInicio = Calendar.getInstance();
        calInicio.setTime(reserva.getReservacionHoraInicio());
        calInicio.add(Calendar.HOUR, -1);
        calInicio.add(Calendar.MINUTE, -59);

        Calendar calLlegada = Calendar.getInstance();
        calLlegada.setTime(reserva.getReservacionHoraLlegada());
        calLlegada.add(Calendar.HOUR, 1);
        calLlegada.add(Calendar.MINUTE, 59);

        Date hespacioInicio = calInicio.getTime();
        Date hespacioLlegada = calLlegada.getTime();

        String espacioinicio = formatTime.format(hespacioInicio);
        String espacioLlegada = formatTime.format(hespacioLlegada);

        empleadosListView = empleadoDao.consultarEmpleadosDisponibles(FechaInicio, FechaLlegada, HoraInicio, HoraLlegada, ciudad.getCiudadNombre(), espacioinicio, espacioLlegada);
        List<SmsEmpleado> lista = new ArrayList<>();
        for (int i = 0; i < empleadosListView.size(); i++) {
            lista.add(empleadoDao.consultarEmpleado(empleadosListView.get(i).getSmsUsuario()).get(0));
        }
        return lista;
    }

}
