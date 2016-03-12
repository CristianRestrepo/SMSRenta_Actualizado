/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Funciones.Upload;
import DAO.ICategoriaDao;
import DAO.ICiudadDao;
import DAO.IProveedorDao;
import DAO.IReferenciaDao;
import DAO.IUsuarioDao;
import DAO.IVehiculoDao;
import DAO.ImpCategoriaDao;
import DAO.ImpCiudadDao;
import DAO.ImpProveedorDao;
import DAO.ImpReferenciaDao;
import DAO.ImpUsuarioDao;
import DAO.ImpVehiculoDao;
import static Funciones.Upload.getPathDefaultVehiculo;
import Modelo.SmsCategoria;
import Modelo.SmsCiudad;
import Modelo.SmsEstadovehiculo;
import Modelo.SmsReservacion;
import Modelo.SmsVehiculo;
import java.io.IOException;
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
import org.primefaces.model.UploadedFile;

public class VehiculoBean {

    //Objetos de vista 
    private SmsVehiculo vehiculoView;
    private SmsEstadovehiculo estadoVehiculoView;
    private List<SmsVehiculo> vehiculosListView;
      
    
    //Relacion con el controlador   
    Upload fileController;
    EstadoVehiculoBean estadoVehiculoController;
    
    //Variables   
    private String nombre;
    private String buscar;
    private String modEstadoArchivo;
    private String estadoArchivo;
    private UploadedFile archivo;

    //Conexion con el DAO
    ICategoriaDao cateDao;
    IProveedorDao provDao;
    ICiudadDao ciuDao;
    IReferenciaDao refDao;
    IVehiculoDao vehDao;
    IUsuarioDao usuDao;
    
    public VehiculoBean() {       
        
        vehiculoView = new SmsVehiculo();    
        estadoVehiculoView = new SmsEstadovehiculo();
       
        estadoVehiculoController = new EstadoVehiculoBean();
        fileController = new Upload();        
        buscar = null;
        
        cateDao = new ImpCategoriaDao();
        provDao = new ImpProveedorDao();
        ciuDao = new ImpCiudadDao();
        refDao = new ImpReferenciaDao();
        vehDao = new ImpVehiculoDao();
        usuDao = new ImpUsuarioDao();

        nombre = "Registrar Vehiculo";
        modEstadoArchivo = "Foto sin subir";
        estadoArchivo = "Foto sin subir";
        vehDao = new ImpVehiculoDao();
    }

    @PostConstruct
    public void init() {
        vehiculosListView = vehDao.mostrarVehiculo();
    }

    //Getters & Setters 
    public SmsEstadovehiculo getEstadoVehiculoView() {
        return estadoVehiculoView;
    }

    public void setEstadoVehiculoView(SmsEstadovehiculo estadoVehiculoView) {
        this.estadoVehiculoView = estadoVehiculoView;
    }

    public List<SmsVehiculo> getVehiculosListView() {
        return vehiculosListView;
    }

    public void setVehiculosListView(List<SmsVehiculo> vehiculosListView) {
        this.vehiculosListView = vehiculosListView;
    }

    public SmsVehiculo getVehiculoView() {
        return vehiculoView;
    }

    public void setVehiculoView(SmsVehiculo veh) {
        this.vehiculoView = veh;
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

    public String getModEstadoArchivo() {
        return modEstadoArchivo;
    }

    public void setModEstadoArchivo(String modEstadoArchivo) {
        this.modEstadoArchivo = modEstadoArchivo;
    }

    public String getEstadoArchivo() {
        return estadoArchivo;
    }

    public void setEstadoArchivo(String estadoArchivo) {
        this.estadoArchivo = estadoArchivo;
    }

    public UploadedFile getArchivo() {
        return archivo;
    }

    public void setArchivo(UploadedFile archivo) {
        this.archivo = archivo;
    }    
    
    //Definicion de metodos VEHICULO
    public void registrar() {

        //En caso de subir NO una fotografia del vehiculo, el sistema asigna al vehiculo una fotografia default
        if (vehiculoView.getVehFotoRuta() == null && vehiculoView.getVehFotoNombre() == null) {
            String ruta = getPathDefaultVehiculo();
            vehiculoView.setVehFotoRuta(ruta);
            vehiculoView.setVehFotoNombre("Default.png");
        }
        vehiculoView.setSmsProveedor(provDao.consultarProveedor(vehiculoView.getSmsProveedor()).get(0));

        //Consulta categoria      
        vehiculoView.setSmsCategoria(cateDao.consultarCategoria(vehiculoView.getSmsCategoria()).get(0));
                
        //Consulta ciudad        
        vehiculoView.setSmsCiudad(ciuDao.consultarCiudad(vehiculoView.getSmsCiudad()).get(0));
        
        //Consulta referencia      
        vehiculoView.setSmsReferencia(refDao.consultarReferencias(vehiculoView.getSmsReferencia()).get(0));

        //Registramos el vehiculo
        vehDao.registrarVehiculo(vehiculoView);//Registra el Vehiculo
        
        //consultamos el vehiculo recien registrado
        vehiculoView = vehDao.consultarVehiculo(vehiculoView).get(0);
        estadoVehiculoView.setSmsVehiculo(vehiculoView); //relacionamos el vehiculo con los valores asignados en la seccion de estado

        estadoVehiculoController.registrarEstVeh(estadoVehiculoView);//registramos el estado

        //Reiniciamos valores para las variables llamadas desde las vista
        estadoArchivo = "Foto sin subir";

        //limpiamos objetos        
        vehiculoView = new SmsVehiculo();        
        estadoVehiculoView = new SmsEstadovehiculo();

        //Actualizamos la lista que muestra los vehiculos registrados en el sistema
        vehiculosListView = vehDao.mostrarVehiculo();
    }

    public String modificar() {
        //Consulta proveedor
        vehiculoView.setSmsProveedor(provDao.consultarProveedor(vehiculoView.getSmsProveedor()).get(0));

        //Consulta categoria      
        vehiculoView.setSmsCategoria(cateDao.consultarCategoria(vehiculoView.getSmsCategoria()).get(0));
                
        //Consulta ciudad        
        vehiculoView.setSmsCiudad(ciuDao.consultarCiudad(vehiculoView.getSmsCiudad()).get(0));
        
        //Consulta referencia      
        vehiculoView.setSmsReferencia(refDao.consultarReferencias(vehiculoView.getSmsReferencia()).get(0));

        
        vehDao.modificarVehiculo(vehiculoView);//Se modifica el vehiculo
        
        //Consultamos el vehiculo recien modificado
        vehiculoView = vehDao.consultarVehiculo(vehiculoView).get(0);
        estadoVehiculoView.setSmsVehiculo(vehiculoView); //Relacionamos el estado de vehiculo con el vehiculo.
        estadoVehiculoController.registrarEstVeh(estadoVehiculoView);//Registramos el estado del vehiculo

        //Reiniciamos valores para las variables llamadas desde las vista
        modEstadoArchivo = "Foto sin subir";

        
        vehiculoView = new SmsVehiculo();        
        estadoVehiculoView = new SmsEstadovehiculo();

        //Actualizamos la lista que muestra los vehiculos registrados en el sistema
        vehiculosListView = vehDao.mostrarVehiculo();
        return "AdminPVehiculos";
    }

    public void eliminar() {
        //Eliminamos el vehiculo seleccionado
        vehDao.eliminarVehiculo(vehiculoView);
        vehiculoView = new SmsVehiculo();//Limpiamos el objeto que contenia el vehiculo a eliminar
        //Recargamos la lista de vehiculos
        vehiculosListView = vehDao.mostrarVehiculo();
    }

    //Metodos propios
    public String irModificarVehiculo() {
        //Asignamos a cada componente su correspondiente valor extraido del vehiculo seleccionado
        
        //Consultamos el estado del vehiculo
        estadoVehiculoView = estadoVehiculoController.consultarEstado(vehiculoView).get(0);

        //Si el vehiculo tiene una foto asignada damos valores a nuestras variables para mostrar que foto esta asignada
        if (vehiculoView.getVehFotoNombre() != null && vehiculoView.getVehFotoRuta() != null) {
            modEstadoArchivo = "Foto subida:" + vehiculoView.getVehFotoNombre();

        } else { //En caso de no existir fotografia, indicamos en la vista la posibilidad de subir una foto para el vehiculo
            modEstadoArchivo = "Foto sin subir";
        }
        return "AdminPEVehiculo";
    }

    public String regresar() {
        vehiculoView = new SmsVehiculo();
        estadoVehiculoView = new SmsEstadovehiculo();      
        modEstadoArchivo = "Foto sin subir";
        return "AdminPVehiculos";
    }

    public void uploadPhoto(FileUploadEvent e) throws IOException {
        try {
            UploadedFile uploadedPhoto = e.getFile();
            String destination;

            HashMap<String, String> map = fileController.getMapPathFotosVehiculos();
            destination = map.get("path");
            if (null != uploadedPhoto) {
                fileController.uploadFile(IOUtils.toByteArray(uploadedPhoto.getInputstream()), uploadedPhoto.getFileName(), destination);
                vehiculoView.setVehFotoNombre(uploadedPhoto.getFileName());
                vehiculoView.setVehFotoRuta(map.get("url") + uploadedPhoto.getFileName());                

                estadoArchivo = "Foto Subida con exito";
                modEstadoArchivo = "Foto actualizada con exito";
            }

            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Su foto (" + uploadedPhoto.getFileName() + ")  se ha guardado con exito.", ""));
        } catch (Exception ex) {
            ex.getMessage();
        }
    }       
      

    public List<SmsVehiculo> consultarVehiculosDisponible(SmsReservacion reserva, SmsCiudad ciudad) {
        vehiculosListView = new ArrayList<>();
        String ciudadVeh = ciudad.getCiudadNombre();
        
        Calendar calInicio = Calendar.getInstance();
        calInicio.setTime(reserva.getReservacionHoraInicio());
        calInicio.add(Calendar.HOUR, -1);
        calInicio.add(Calendar.MINUTE, -59);

        Calendar calLlegada = Calendar.getInstance();
        calLlegada.setTime(reserva.getReservacionHoraLlegada());
        calLlegada.add(Calendar.HOUR, 2);

        Date hespacioInicio = calInicio.getTime();
        Date hespacioLlegada = calLlegada.getTime();

        SimpleDateFormat formatDate;
        SimpleDateFormat formatTime;
        formatDate = new SimpleDateFormat("yyyy-MM-dd");
        formatTime = new SimpleDateFormat("HH:mm:ss");

        String FechaInicio = formatDate.format(reserva.getReservacionFechaInicio());
        String FechaLlegada = formatDate.format(reserva.getReservacionFechaLlegada());
        String HoraInicio = formatTime.format(reserva.getReservacionHoraInicio());
        String HoraLlegada = formatTime.format(reserva.getReservacionHoraLlegada());
        String espacioinicio = formatTime.format(hespacioInicio);
        String espacioLlegada = formatTime.format(hespacioLlegada);

        vehiculosListView = vehDao.consultarVehiculosDisponibles(FechaInicio, FechaLlegada, HoraInicio, HoraLlegada, ciudadVeh, espacioinicio, espacioLlegada);
        return vehiculosListView;
    }

    public List<SmsVehiculo> consultarVehiculosCiudad(SmsCiudad c) {
        vehiculosListView = new ArrayList<>();
        vehiculoView.setSmsCiudad(c); 
        vehiculosListView = vehDao.consultarVehiculosCiudad(vehiculoView.getSmsCiudad());
        return vehiculosListView;
    }

    public List<SmsVehiculo> filtrarVehiculosCiudad(SmsCiudad c, SmsCategoria cat) {
        vehiculosListView = new ArrayList<>();
        vehiculoView.setSmsCiudad(c);
        String categoriaVeh = cat.getCategoriaNombre();
        vehiculosListView = vehDao.filtrarVehiculosCiudad(vehiculoView.getSmsCiudad(), vehiculoView.getSmsCategoria().getCategoriaNombre());
        return vehiculosListView;
    }

    public List<SmsVehiculo> filtrarVehiculosDisponibles(SmsReservacion reserva, SmsCategoria cat) {
        vehiculosListView = new ArrayList<>();        
        String categoriaVeh = cat.getCategoriaNombre();
        String ciudadVeh = reserva.getSmsCiudadByIdCiudadInicio().getCiudadNombre();

        Calendar calInicio = Calendar.getInstance();
        calInicio.setTime(reserva.getReservacionHoraInicio());
        calInicio.add(Calendar.HOUR, -1);
        calInicio.add(Calendar.MINUTE, -59);

        Calendar calLlegada = Calendar.getInstance();
        calLlegada.setTime(reserva.getReservacionHoraLlegada());
        calLlegada.add(Calendar.HOUR, 2);

        Date hespacioInicio = calInicio.getTime();
        Date hespacioLlegada = calLlegada.getTime();

        SimpleDateFormat formatDate;
        SimpleDateFormat formatTime;
        formatDate = new SimpleDateFormat("yyyy-MM-dd");
        formatTime = new SimpleDateFormat("HH:mm:ss");

        String FechaInicio = formatDate.format(reserva.getReservacionFechaInicio());
        String FechaLlegada = formatDate.format(reserva.getReservacionFechaLlegada());
        String HoraInicio = formatTime.format(reserva.getReservacionHoraInicio());
        String HoraLlegada = formatTime.format(reserva.getReservacionHoraLlegada());
        String espacioinicio = formatTime.format(hespacioInicio);
        String espacioLlegada = formatTime.format(hespacioLlegada);

        
        vehiculosListView = vehDao.filtrarVehiculosDisponibles(FechaInicio, FechaLlegada, HoraInicio, HoraLlegada, ciudadVeh, categoriaVeh, espacioinicio, espacioLlegada);
        return vehiculosListView;
    }
}
