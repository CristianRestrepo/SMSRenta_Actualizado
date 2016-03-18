/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Funciones.Upload;
import DAO.ICategoriaDao;
import DAO.ICiudadDao;
import DAO.IColorDao;
import DAO.IEstadoDao;
import DAO.IProveedorDao;
import DAO.IReferenciaDao;
import DAO.IUsuarioDao;
import DAO.IVehiculoDao;
import DAO.ImpCategoriaDao;
import DAO.ImpCiudadDao;
import DAO.ImpColorDao;
import DAO.ImpEstadoDao;
import DAO.ImpProveedorDao;
import DAO.ImpReferenciaDao;
import DAO.ImpUsuarioDao;
import DAO.ImpVehiculoDao;
import static Funciones.Upload.getMapPathFotosVehiculos;
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
    private String estadoArchivo1;
    private String estadoArchivo2;
    private int operacion;

    //Banderas    
    private boolean habilitarCancelar;

    //Conexion con el DAO
    ICategoriaDao cateDao;
    IProveedorDao provDao;
    ICiudadDao ciuDao;
    IReferenciaDao refDao;
    IVehiculoDao vehDao;
    IUsuarioDao usuDao;
    IEstadoDao estadoDao;
    IColorDao colorDao;

    public VehiculoBean() {

        vehiculoView = new SmsVehiculo();
        vehiculosListView = new ArrayList<>();
        estadoVehiculoView = new SmsEstadovehiculo();

        habilitarCancelar = true;

        estadoVehiculoController = new EstadoVehiculoBean();
        fileController = new Upload();
        buscar = null;

        cateDao = new ImpCategoriaDao();
        provDao = new ImpProveedorDao();
        ciuDao = new ImpCiudadDao();
        refDao = new ImpReferenciaDao();
        vehDao = new ImpVehiculoDao();
        usuDao = new ImpUsuarioDao();
        estadoDao = new ImpEstadoDao();
        colorDao = new ImpColorDao();

        nombre = "Registrar Vehiculo";
        operacion = 0;
        estadoArchivo1 = "Foto sin subir";
        estadoArchivo2 = "Foto sin subir";
        
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

    public boolean isHabilitarCancelar() {
        return habilitarCancelar;
    }

    public void setHabilitarCancelar(boolean habilitarCancelar) {
        this.habilitarCancelar = habilitarCancelar;
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

    public int getOperacion() {
        return operacion;
    }

    public void setOperacion(int operacion) {
        this.operacion = operacion;
    }

    public String getEstadoArchivo1() {
        return estadoArchivo1;
    }

    public void setEstadoArchivo1(String estadoArchivo1) {
        this.estadoArchivo1 = estadoArchivo1;
    }

    public String getEstadoArchivo2() {
        return estadoArchivo2;
    }

    public void setEstadoArchivo2(String estadoArchivo2) {
        this.estadoArchivo2 = estadoArchivo2;
    }

    //Definicion de metodos VEHICULO
    public void registrar() {

        //En caso de subir NO una fotografia del vehiculo, el sistema asigna al vehiculo una fotografia default
        if (vehiculoView.getVehFotoRuta() == null && vehiculoView.getVehFotoNombre() == null) {
            String ruta = getPathDefaultVehiculo();
            vehiculoView.setVehFotoRuta(ruta);
            vehiculoView.setVehFotoNombre("Default.png");
            vehiculoView.setVehFoto2Ruta(ruta);
            vehiculoView.setVehFoto2Nombre("Default.png");
        }

        vehiculoView.getSmsEstado().setEstadoNombre("Disponible");

        //Consulta estado
        vehiculoView.setSmsEstado(estadoDao.consultarEstado(vehiculoView.getSmsEstado()).get(0));

        //Consulta proveedor    
        vehiculoView.setSmsProveedor(provDao.consultarProveedor(vehiculoView.getSmsProveedor()).get(0));

        //Consulta categoria      
        vehiculoView.setSmsCategoria(cateDao.consultarCategoria(vehiculoView.getSmsCategoria()).get(0));

        //Consulta ciudad        
        vehiculoView.setSmsCiudad(ciuDao.consultarCiudad(vehiculoView.getSmsCiudad()).get(0));

        //Consulta referencia      
        vehiculoView.setSmsReferencia(refDao.consultarReferencias(vehiculoView.getSmsReferencia()).get(0));

        //Consultar color
        vehiculoView.setSmsColor(colorDao.consultarColor(vehiculoView.getSmsColor()).get(0));

        //Registramos el vehiculo
        vehDao.registrarVehiculo(vehiculoView);//Registra el Vehiculo

        //consultamos el vehiculo recien registrado
        vehiculoView = vehDao.consultarVehiculo(vehiculoView).get(0);
        estadoVehiculoView.setSmsVehiculo(vehiculoView); //relacionamos el vehiculo con los valores asignados en la seccion de estado

        estadoVehiculoController.registrarEstVeh(estadoVehiculoView);//registramos el estado

        //Reiniciamos valores para las variables llamadas desde las vista
        estadoArchivo1 = "Foto sin subir";
        estadoArchivo2 = "Foto sin subir";

        //limpiamos objetos        
        vehiculoView = new SmsVehiculo();
        estadoVehiculoView = new SmsEstadovehiculo();

        //Actualizamos la lista que muestra los vehiculos registrados en el sistema
        vehiculosListView = vehDao.mostrarVehiculo();
    }

    public void modificar() {
      //Consulta estado
        vehiculoView.setSmsEstado(estadoDao.consultarEstado(vehiculoView.getSmsEstado()).get(0));

        //Consulta proveedor    
        vehiculoView.setSmsProveedor(provDao.consultarProveedor(vehiculoView.getSmsProveedor()).get(0));

        //Consulta categoria      
        vehiculoView.setSmsCategoria(cateDao.consultarCategoria(vehiculoView.getSmsCategoria()).get(0));

        //Consulta ciudad        
        vehiculoView.setSmsCiudad(ciuDao.consultarCiudad(vehiculoView.getSmsCiudad()).get(0));

        //Consulta referencia      
        vehiculoView.setSmsReferencia(refDao.consultarReferencias(vehiculoView.getSmsReferencia()).get(0));

        //Consultar color
        vehiculoView.setSmsColor(colorDao.consultarColor(vehiculoView.getSmsColor()).get(0));
        
        vehDao.modificarVehiculo(vehiculoView);//Se modifica el vehiculo

        //Consultamos el vehiculo recien modificado       
        estadoVehiculoView.setSmsVehiculo(vehiculoView); //Relacionamos el estado de vehiculo con el vehiculo.
        estadoVehiculoController.registrarEstVeh(estadoVehiculoView);//Registramos el estado del vehiculo

        //Reiniciamos valores para las variables llamadas desde las vista
        estadoArchivo2 = "Foto sin subir";
        estadoArchivo1 = "Foto sin subir";

        vehiculoView = new SmsVehiculo();
        estadoVehiculoView = new SmsEstadovehiculo();

        //Actualizamos la lista que muestra los vehiculos registrados en el sistema
        vehiculosListView = vehDao.mostrarVehiculo();       
    }

    public void eliminar() {
        //Eliminamos el vehiculo seleccionado
        vehDao.eliminarVehiculo(vehiculoView);
        vehiculoView = new SmsVehiculo();//Limpiamos el objeto que contenia el vehiculo a eliminar
        //Recargamos la lista de vehiculos
        vehiculosListView = vehDao.mostrarVehiculo();
    }

    //Metodos Propios
    public void metodo() {
        if (operacion == 0) {
            registrar();
        } else if (operacion == 1) {
            modificar();
            operacion = 0;
            habilitarCancelar = true;
            nombre = "Registrar Vehiculo";
        }
    }

    public void seleccionarCRUD(int i) {
        operacion = i;
        if (operacion == 1) {
            habilitarCancelar = false;
            nombre = "Editar Vehiculo";
            //Consultamos el estado del vehiculo
            estadoVehiculoView = estadoVehiculoController.consultarEstado(vehiculoView).get(0);

            //Si el vehiculo tiene una foto asignada damos valores a nuestras variables para mostrar que foto esta asignada
            if (vehiculoView.getVehFotoNombre() != null && vehiculoView.getVehFotoRuta() != null) {
                estadoArchivo1 = "Foto subida:" + vehiculoView.getVehFotoNombre();

            } else { //En caso de no existir fotografia, indicamos en la vista la posibilidad de subir una foto para el vehiculo
                estadoArchivo1 = "Foto sin subir";
            }

            if (vehiculoView.getVehFoto2Nombre() != null && vehiculoView.getVehFoto2Ruta() != null) {
                estadoArchivo2 = "Foto subida:" + vehiculoView.getVehFotoNombre();

            } else { //En caso de no existir fotografia, indicamos en la vista la posibilidad de subir una foto para el vehiculo
                estadoArchivo2 = "Foto sin subir";
            }

        }
    }

    //Metodos propios
    public void cancelar() {
        //Limpiamos objetos utilizados
        vehiculoView = new SmsVehiculo();
        estadoVehiculoView = new SmsEstadovehiculo();
        estadoArchivo1 = "Foto sin subir";
        estadoArchivo2 = "Foto sin subir";

        //Reiniciamos los objetos
        habilitarCancelar = true;
        nombre = "Registrar Vehiculo";
    }

    public void uploadPhoto1(FileUploadEvent e) throws IOException {
        try {
            UploadedFile uploadedPhoto = e.getFile();
            String destination;

            HashMap<String, String> map = getMapPathFotosVehiculos();
            destination = map.get("path");
            if (null != uploadedPhoto) {
                fileController.uploadFile(IOUtils.toByteArray(uploadedPhoto.getInputstream()), uploadedPhoto.getFileName(), destination);
                vehiculoView.setVehFotoNombre(uploadedPhoto.getFileName());
                vehiculoView.setVehFotoRuta(map.get("url") + uploadedPhoto.getFileName());

                if (operacion == 0) {
                    estadoArchivo1 = "Foto Subida con exito";
                } else if (operacion == 1) {
                    estadoArchivo1 = "Foto actualizada con exito";

                }
            }
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Su foto (" + uploadedPhoto.getFileName() + ")  se ha guardado con exito.", ""));
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    public void uploadPhoto2(FileUploadEvent e) throws IOException {
        try {
            UploadedFile uploadedPhoto = e.getFile();
            String destination;

            HashMap<String, String> map = getMapPathFotosVehiculos();
            destination = map.get("path");
            if (null != uploadedPhoto) {
                fileController.uploadFile(IOUtils.toByteArray(uploadedPhoto.getInputstream()), uploadedPhoto.getFileName(), destination);
                vehiculoView.setVehFoto2Nombre(uploadedPhoto.getFileName());
                vehiculoView.setVehFoto2Ruta(map.get("url") + uploadedPhoto.getFileName());

                if (operacion == 0) {
                    estadoArchivo2 = "Foto Subida con exito";
                } else if (operacion == 1) {
                    estadoArchivo2 = "Foto actualizada con exito";

                }
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
