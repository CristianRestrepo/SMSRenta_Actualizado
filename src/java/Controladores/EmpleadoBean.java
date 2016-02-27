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
import Funciones.MD5;
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
    private int estado; //Controla la operacion a realizar  
    private String buscar;    
    private StreamedContent file;//Variable que permite descargar la hoja de vida del empleado
    private UploadedFile archivo;
    private UploadedFile foto;
  
    private String estadoArchivo;
    private Boolean registroHojaVida;

    //Conexion con el dao
    IEmpleadoDao empleadoDao;
    IHojaVidaDao hojaDao;
    IProveedorDao proveedorDao;

    public EmpleadoBean() {
        super();        
        hojavidaView = new SmsHojavida();
        proveedorView = new SmsProveedor();
         
        buscar = null;        
        estado = 0;
       
        estadoFoto = "Foto sin subir";       
        estadoArchivo = "Hoja de vida sin subir";

        registroHojaVida = false;       

        empleadoDao = new ImpEmpleadoDao();
        hojaDao = new ImpHojaVidaDao();
        proveedorDao = new ImpProveedorDao();
    }

    @PostConstruct
    public void init() {
      usuariosListView = empleadoDao.consultarUsuariosEmpleados();
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

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
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
    
    
         

    //Metodos que se comunican con el controlador    
    public void registrarEmpleado() {
       rolView.setRolNombre("Empleado");
       
       if (usuarioView.getUsuarioFotoRuta() == null && hojavidaView.getHojaVidaRuta() == null) {
            //asignamos al usuario la imagen de perfil default
            usuarioView.setUsuarioFotoRuta(fileController.getPathDefaultUsuario());
            usuarioView.setUsuarioFotoNombre(fileController.getNameDefaultUsuario());
            hojavidaView.setHojaVidaNombre(fileController.getNameDefaultHojasVida());
            hojavidaView.setHojaVidaRuta(fileController.getPathDefaultHojasVida());
        }

        registrarHojaVida(hojavidaView);
        
        ciudadView = ciudadDao.consultarCiudad(ciudadView).get(0);
        usuarioView.setSmsCiudad(ciudadView);//Asociamos una ciudad a un usuario

        rolView = rolDao.consultarRol(rolView).get(0);
        usuarioView.setSmsRol(rolView);//Asociamos un rol a un usuario

        usuarioView.setUsuarioEstadoUsuario(1);//Asignamos un estado de cuenta

        //registramos el usuario y recargamos la lista de clientes
        usuarioDao.registrarUsuario(usuarioView);
        
        //Se consulta toda la informacion de usuario y se relaciona al empleado
        usuarioView = usuarioDao.consultarUsuario(usuarioView).get(0);
        proveedorView.setSmsUsuario(usuarioDao.consultarUsuario(proveedorView.getSmsUsuario()).get(0));
        empleadoView.setSmsUsuario(usuarioView);
        
        if (hojavidaView.getHojaVidaNombre() != null && hojavidaView.getHojaVidaRuta() != null) {

            hojavidaView = hojaDao.consultarHojaVida(hojavidaView).get(0);
            empleadoView.setSmsHojavida(hojavidaView);
        }
        
        empleadoDao.registrarEmpleado(empleadoView);//Registramos al usuario como empleado

        estadoFoto = "Foto sin subir";     
        estadoArchivo = "Hoja de vida sin subir";        
        registroHojaVida = false;

        usuarioView = new SmsUsuario();
        ciudadView = new SmsCiudad();
        rolView = new SmsRol();
        empleadoView = new SmsEmpleado();
        hojavidaView = new SmsHojavida();
        usuariosListView = empleadoDao.consultarUsuariosEmpleados();
    }

    public void modificarPerfilEmpleado() {
        MD5 md = new MD5();
        
        modUsuarioView.setUsuarioPassword(md.getMD5(modUsuarioView.getUsuarioPassword()));
        modUsuarioView.setUsuarioRememberToken(md.getMD5(modUsuarioView.getUsuarioRememberToken()));
      
        ciudadView = ciudadDao.consultarCiudad(ciudadView).get(0);
        modUsuarioView.setSmsCiudad(ciudadView);//Asociamos una ciudad a un usuario

        usuarioDao.modificarUsuario(modUsuarioView);
        modEmpleadoView.setSmsUsuario(usuarioView);

        if (hojavidaView.getHojaVidaNombre() != null && hojavidaView.getHojaVidaRuta() != null) {
            
            hojavidaView = hojaDao.consultarHojaVida(hojavidaView).get(0);
            modEmpleadoView.setSmsHojavida(hojavidaView);
        } 
        
        empleadoDao.modificarEmpleado(modEmpleadoView);
        estadoArchivo = "Hoja subida:" + hojavidaView.getHojaVidaNombre();
        
    }

    public String modificarEmpleado() {
        rolView.setRolNombre("Empleado");
        
        modEmpleadoView.setSmsUsuario(modUsuarioView);
       // modificar();        
        
        
        if(hojavidaView.getHojaVidaNombre() != null && hojavidaView.getHojaVidaRuta() != null){
            hojavidaView = hojaDao.consultarHojaVida(hojavidaView).get(0);
            modEmpleadoView.setSmsHojavida(hojavidaView);
        }
                        
        empleadoDao.modificarEmpleado(modEmpleadoView);

        estadoFoto = "Foto sin subir";       

        estadoArchivo = "Hoja de vida sin subir";       
        registroHojaVida = false;

        modUsuarioView = new SmsUsuario();
        ciudadView = new SmsCiudad();
        rolView = new SmsRol();
        hojavidaView = new SmsHojavida();

        usuariosListView = empleadoDao.consultarUsuariosEmpleados();
        estado = 0;
        String ruta = "RAdminPEmpleado";
        return ruta;
    }

    public void eliminarEmpleado() {
        usuarioDao.eliminarUsuario(DUsuarioView);

        DUsuarioView = new SmsUsuario();
        ciudadView = new SmsCiudad();
        rolView = new SmsRol();
        empleadoView = new SmsEmpleado();
        hojavidaView = new SmsHojavida();

        usuariosListView = empleadoDao.consultarUsuariosEmpleados();
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

    //Metodos propios
    public String irModificarEmpleados() {
        estado = 1;

        ciudadView = modUsuarioView.getSmsCiudad();
        rolView = modUsuarioView.getSmsRol();
        empleadoView = empleadoDao.consultarEmpleado(modUsuarioView).get(0);

        if (empleadoView.getSmsHojavida() != null) {
            hojavidaView = empleadoView.getSmsHojavida();
        }

        if (modUsuarioView.getUsuarioFotoNombre() != null || modUsuarioView.getUsuarioFotoRuta() != null) {           
            estadoFoto = "Foto subida:" + modUsuarioView.getUsuarioFotoNombre();           
        } 

        if (hojavidaView.getHojaVidaNombre() != null && hojavidaView.getHojaVidaRuta() != null) {
            estadoArchivo = "Hoja subida:" + hojavidaView.getHojaVidaNombre();            
        } 
        String ruta = "AdminPEEmpleado";
        return ruta;
    }

    public String regresar_Empleado() {
        modUsuarioView = new SmsUsuario();
        ciudadView = new SmsCiudad();
        hojavidaView = new SmsHojavida();

        estadoFoto = "Foto sin subir";      
        estadoArchivo = "Hoja de vida sin subir";       
        registroHojaVida = false;

       
        String ruta = "AdminPEmpleado";
        return ruta;
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
        for(int i = 0; i<empleadosListView.size() ; i++){
        lista.add(empleadoDao.consultarEmpleado(empleadosListView.get(i).getSmsUsuario()).get(0));
        }
        return lista;
    }

}
