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
import Modelo.SmsCategoria;
import Modelo.SmsCiudad;
import Modelo.SmsEstadovehiculo;
import Modelo.SmsProveedor;
import Modelo.SmsReferencia;
import Modelo.SmsReservacion;
import Modelo.SmsUsuario;
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
    private SmsVehiculo DVehiculoView;
    private List<SmsVehiculo> vehiculosListView;
    private List<String> PlacasVehiculosListView;
    private SmsUsuario usuarioView;

    private SmsCategoria categoriaView;
    private SmsCiudad ciudadView;
    private SmsProveedor proveedorView;
    private SmsReferencia referenciaView;
    private SmsEstadovehiculo estadoVehiculoView;

    private SmsVehiculo MvehiculoView;
    private SmsCategoria McategoriaView;
    private SmsCiudad MciudadView;
    private SmsProveedor MproveedorView;
    private SmsReferencia MreferenciaView;
    private SmsEstadovehiculo MestadoVehiculoView;
    private SmsUsuario MusuarioView;

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
        DVehiculoView = new SmsVehiculo();
        categoriaView = new SmsCategoria();
        ciudadView = new SmsCiudad();
        proveedorView = new SmsProveedor();
        referenciaView = new SmsReferencia();
        estadoVehiculoView = new SmsEstadovehiculo();

        MvehiculoView = new SmsVehiculo();
        McategoriaView = new SmsCategoria();
        MciudadView = new SmsCiudad();
        MproveedorView = new SmsProveedor();
        MreferenciaView = new SmsReferencia();
        MestadoVehiculoView = new SmsEstadovehiculo();
        MusuarioView = new SmsUsuario();

        estadoVehiculoController = new EstadoVehiculoBean();
        fileController = new Upload();
        usuarioView = new SmsUsuario();
       
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
    public SmsCategoria getCategoriaView() {
        return categoriaView;
    }

    public void setCategoriaView(SmsCategoria categoriaView) {
        this.categoriaView = categoriaView;
    }

    public SmsCiudad getCiudadView() {
        return ciudadView;
    }

    public void setCiudadView(SmsCiudad ciudadView) {
        this.ciudadView = ciudadView;
    }

    public SmsProveedor getProveedorView() {
        return proveedorView;
    }

    public void setProveedorView(SmsProveedor proveedorView) {
        this.proveedorView = proveedorView;
    }

    public SmsReferencia getReferenciaView() {
        return referenciaView;
    }

    public void setReferenciaView(SmsReferencia referenciaView) {
        this.referenciaView = referenciaView;
    }

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

    public List<String> getPlacasVehiculosListView() {
        return PlacasVehiculosListView;
    }

    public void setPlacasVehiculosListView(List<String> PlacasVehiculosListView) {
        this.PlacasVehiculosListView = PlacasVehiculosListView;
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

    public SmsUsuario getUsuarioView() {
        return usuarioView;
    }

    public void setUsuarioView(SmsUsuario usuarioView) {
        this.usuarioView = usuarioView;
    }

    public SmsVehiculo getDVehiculoView() {
        return DVehiculoView;
    }

    public void setDVehiculoView(SmsVehiculo DVehiculoView) {
        this.DVehiculoView = DVehiculoView;
    }

    public SmsVehiculo getMvehiculoView() {
        return MvehiculoView;
    }

    public void setMvehiculoView(SmsVehiculo MvehiculoView) {
        this.MvehiculoView = MvehiculoView;
    }

    public SmsCategoria getMcategoriaView() {
        return McategoriaView;
    }

    public void setMcategoriaView(SmsCategoria McategoriaView) {
        this.McategoriaView = McategoriaView;
    }

    public SmsCiudad getMciudadView() {
        return MciudadView;
    }

    public void setMciudadView(SmsCiudad MciudadView) {
        this.MciudadView = MciudadView;
    }

    public SmsProveedor getMproveedorView() {
        return MproveedorView;
    }

    public void setMproveedorView(SmsProveedor MproveedorView) {
        this.MproveedorView = MproveedorView;
    }

    public SmsReferencia getMreferenciaView() {
        return MreferenciaView;
    }

    public void setMrefenciaView(SmsReferencia MreferenciaView) {
        this.MreferenciaView = MreferenciaView;
    }

    public SmsEstadovehiculo getMestadoVehiculoView() {
        return MestadoVehiculoView;
    }

    public void setMestadoVehiculoView(SmsEstadovehiculo MestadoVehiculoView) {
        this.MestadoVehiculoView = MestadoVehiculoView;
    }

    public SmsUsuario getMusuarioView() {
        return MusuarioView;
    }

    public void setMusuarioView(SmsUsuario MusuarioView) {
        this.MusuarioView = MusuarioView;
    }

    //Definicion de metodos VEHICULO
    public void registrar() {

        //En caso de subir NO una fotografia del vehiculo, el sistema asigna al vehiculo una fotografia default
        if (vehiculoView.getVehFotoRuta() == null && vehiculoView.getVehFotoNombre() == null) {
            String ruta = fileController.getPathDefaultVehiculo();
            vehiculoView.setVehFotoRuta(ruta);
            vehiculoView.setVehFotoNombre("Default.png");
        }

        usuarioView = usuDao.consultarUsuario(usuarioView).get(0);

        //Consulta categoria      
        categoriaView = cateDao.consultarCategoria(categoriaView).get(0);
        vehiculoView.setSmsCategoria(categoriaView);//Reasigna Categoria

        //Consulta proveedor        
        proveedorView = provDao.consultarProveedorUsuario(usuarioView).get(0);
        vehiculoView.setSmsProveedor(proveedorView);//Reasigna Proveedor

        //Consulta ciudad        
        ciudadView = ciuDao.consultarCiudad(ciudadView).get(0);
        vehiculoView.setSmsCiudad(ciudadView);//Reasigna Ciudad

        //Consulta referencia        
        referenciaView = refDao.consultarReferencias(referenciaView).get(0);
        vehiculoView.setSmsReferencia(referenciaView);//Reasigna Referencia

        //Registramos el vehiculo
        vehDao.registrarVehiculo(vehiculoView);//Registra el Vehiculo
        
        //consultamos el vehiculo recien registrado
        vehiculoView = vehDao.consultarVehiculo(vehiculoView).get(0);
        estadoVehiculoView.setSmsVehiculo(vehiculoView); //relacionamos el vehiculo con los valores asignados en la seccion de estado

        estadoVehiculoController.registrarEstVeh(estadoVehiculoView);//registramos el estado

        //Reiniciamos valores para las variables llamadas desde las vista
        estadoArchivo = "Foto sin subir";

        //limpiamos objetos
        referenciaView = new SmsReferencia();
        categoriaView = new SmsCategoria();
        proveedorView = new SmsProveedor();
        ciudadView = new SmsCiudad();
        vehiculoView = new SmsVehiculo();
        usuarioView = new SmsUsuario();
        estadoVehiculoView = new SmsEstadovehiculo();

        //Actualizamos la lista que muestra los vehiculos registrados en el sistema
        vehiculosListView = vehDao.mostrarVehiculo();
    }

    public String modificar() {
        //Ejecutamos la modificacion del vehiculo
        MusuarioView = usuDao.consultarUsuario(MusuarioView).get(0);

        //Consulta categoria      
        McategoriaView = cateDao.consultarCategoria(McategoriaView).get(0);
        MvehiculoView.setSmsCategoria(McategoriaView);//Reasigna Categoria

        //Consulta proveedor        
        MproveedorView = provDao.consultarProveedorUsuario(MusuarioView).get(0);
        MvehiculoView.setSmsProveedor(MproveedorView);//Reasigna Proveedor

        //Consulta ciudad        
        MciudadView = ciuDao.consultarCiudad(MciudadView).get(0);
        MvehiculoView.setSmsCiudad(MciudadView);//Reasigna Ciudad

        //Consulta referencia        
        MreferenciaView = refDao.consultarReferencias(MreferenciaView).get(0);
        MvehiculoView.setSmsReferencia(referenciaView);//Reasigna Referencia
        
        vehDao.modificarVehiculo(MvehiculoView);//Se modifica el vehiculo
        
        //Consultamos el vehiculo recien modificado
        MvehiculoView = vehDao.consultarVehiculo(MvehiculoView).get(0);
        MestadoVehiculoView.setSmsVehiculo(MvehiculoView); //Relacionamos el estado de vehiculo con el vehiculo.
        estadoVehiculoController.registrarEstVeh(MestadoVehiculoView);//Registramos el estado del vehiculo

        //Reiniciamos valores para las variables llamadas desde las vista
        modEstadoArchivo = "Foto sin subir";

        MreferenciaView = new SmsReferencia();
        McategoriaView = new SmsCategoria();
        MproveedorView = new SmsProveedor();
        MciudadView = new SmsCiudad();
        MvehiculoView = new SmsVehiculo();
        MusuarioView = new SmsUsuario();
        MestadoVehiculoView = new SmsEstadovehiculo();

        //Actualizamos la lista que muestra los vehiculos registrados en el sistema
        vehiculosListView = vehDao.mostrarVehiculo();
        return "AdminPVehiculos";
    }

    public void eliminar() {
        //Eliminamos el vehiculo seleccionado
        vehDao.eliminarVehiculo(DVehiculoView);
        DVehiculoView = new SmsVehiculo();//Limpiamos el objeto que contenia el vehiculo a eliminar
        //Recargamos la lista de vehiculos
        vehiculosListView = vehDao.mostrarVehiculo();
    }

    //Metodos propios
    public String irModificarVehiculo() {
        //Asignamos a cada componente su correspondiente valor extraido del vehiculo seleccionado
        MciudadView = MvehiculoView.getSmsCiudad();
        MreferenciaView = MvehiculoView.getSmsReferencia();
        McategoriaView = MvehiculoView.getSmsCategoria();
        MproveedorView = MvehiculoView.getSmsProveedor();
        MusuarioView = MproveedorView.getSmsUsuario();

        //Consultamos el estado del vehiculo
        MestadoVehiculoView = estadoVehiculoController.consultarEstado(MvehiculoView).get(0);

        //Si el vehiculo tiene una foto asignada damos valores a nuestras variables para mostrar que foto esta asignada
        if (MvehiculoView.getVehFotoNombre() != null && MvehiculoView.getVehFotoRuta() != null) {
            modEstadoArchivo = "Foto subida:" + MvehiculoView.getVehFotoNombre();

        } else { //En caso de no existir fotografia, indicamos en la vista la posibilidad de subir una foto para el vehiculo
            modEstadoArchivo = "Foto sin subir";
        }
        return "AdminPEVehiculo";
    }

    public String regresar() {
        MvehiculoView = new SmsVehiculo();
        McategoriaView = new SmsCategoria();
        MciudadView = new SmsCiudad();
        MproveedorView = new SmsProveedor();
        MreferenciaView = new SmsReferencia();
        MestadoVehiculoView = new SmsEstadovehiculo();
        MusuarioView = new SmsUsuario();

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
                MvehiculoView.setVehFotoNombre(uploadedPhoto.getFileName());
                MvehiculoView.setVehFotoRuta(map.get("url") + uploadedPhoto.getFileName());

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
        ciudadView = c;
        vehiculosListView = vehDao.consultarVehiculosCiudad(ciudadView);
        return vehiculosListView;
    }

    public List<SmsVehiculo> filtrarVehiculosCiudad(SmsCiudad c, SmsCategoria cat) {
        vehiculosListView = new ArrayList<>();
        ciudadView = c;
        String categoriaVeh = cat.getCategoriaNombre();
        vehiculosListView = vehDao.filtrarVehiculosCiudad(ciudadView, categoriaVeh);
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
