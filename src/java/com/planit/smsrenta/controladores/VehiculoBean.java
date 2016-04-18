/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.controladores;

import com.planit.smsrenta.metodos.Upload;
import com.planit.smsrenta.dao.ICategoriaDao;
import com.planit.smsrenta.dao.ICiudadDao;
import com.planit.smsrenta.dao.IColorDao;
import com.planit.smsrenta.dao.IEmpleadoDao;
import com.planit.smsrenta.dao.IEstadoDao;
import com.planit.smsrenta.dao.IEstadoVehiculoDao;
import com.planit.smsrenta.dao.IProveedorDao;
import com.planit.smsrenta.dao.IReferenciaDao;
import com.planit.smsrenta.dao.IUsuarioDao;
import com.planit.smsrenta.dao.IVehiculoDao;
import com.planit.smsrenta.dao.ImpCategoriaDao;
import com.planit.smsrenta.dao.ImpCiudadDao;
import com.planit.smsrenta.dao.ImpColorDao;
import com.planit.smsrenta.dao.ImpEmpleadoDao;
import com.planit.smsrenta.dao.ImpEstadoDao;
import com.planit.smsrenta.dao.ImpEstadoVehiculoDao;
import com.planit.smsrenta.dao.ImpProveedorDao;
import com.planit.smsrenta.dao.ImpReferenciaDao;
import com.planit.smsrenta.dao.ImpUsuarioDao;
import com.planit.smsrenta.dao.ImpVehiculoDao;
import static com.planit.smsrenta.metodos.Upload.getMapPathFotosVehiculos;
import static com.planit.smsrenta.metodos.Upload.getPathDefaultVehiculo;
import com.planit.smsrenta.modelos.SmsCategoria;
import com.planit.smsrenta.modelos.SmsCiudad;
import com.planit.smsrenta.modelos.SmsEmpleado;
import com.planit.smsrenta.modelos.SmsEstadovehiculo;
import com.planit.smsrenta.modelos.SmsMercado;
import com.planit.smsrenta.modelos.SmsProveedor;
import com.planit.smsrenta.modelos.SmsReservacion;
import com.planit.smsrenta.modelos.SmsVehiculo;
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
    private SmsProveedor proveedorView;
    private SmsEmpleado empleadoView;
    private List<SmsVehiculo> vehiculosListView;
    private List<String> placasVehiculos;

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
    IEstadoVehiculoDao estadoVehDao;

    public VehiculoBean() {

        vehiculoView = new SmsVehiculo();
        proveedorView = new SmsProveedor();
        empleadoView = new SmsEmpleado();

        vehiculosListView = new ArrayList<>();
        estadoVehiculoView = new SmsEstadovehiculo();
        placasVehiculos = new ArrayList<>();

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
        estadoVehDao = new ImpEstadoVehiculoDao();

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

    public List<String> getPlacasVehiculos() {
        return placasVehiculos;
    }

    public void setPlacasVehiculos(List<String> placasVehiculos) {
        this.placasVehiculos = placasVehiculos;
    }

    public SmsProveedor getProveedorView() {
        return proveedorView;
    }

    public void setProveedorView(SmsProveedor proveedorView) {
        this.proveedorView = proveedorView;
    }

    public SmsEmpleado getEmpleadoView() {
        return empleadoView;
    }

    public void setEmpleadoView(SmsEmpleado empleadoView) {
        this.empleadoView = empleadoView;
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
        vehiculoView.setSmsEstado(estadoDao.consultarEstado(vehiculoView.getSmsEstado()));

        //Consulta proveedor    
        vehiculoView.setSmsProveedor(provDao.consultarProveedor(vehiculoView.getSmsProveedor()));

        //Consulta categoria      
        vehiculoView.setSmsCategoria(cateDao.consultarCategoria(vehiculoView.getSmsCategoria()));

        //Consulta ciudad        
        vehiculoView.setSmsCiudad(ciuDao.consultarCiudad(vehiculoView.getSmsCiudad()));

        //Consulta referencia      
        vehiculoView.setSmsReferencia(refDao.consultarReferencias(vehiculoView.getSmsReferencia()));

        //Consultar color
        vehiculoView.setSmsColor(colorDao.consultarColor(vehiculoView.getSmsColor()));

        //Registramos el vehiculo
        vehDao.registrarVehiculo(vehiculoView);//Registra el Vehiculo

        //Reiniciamos valores para las variables llamadas desde las vista
        estadoArchivo1 = "Foto sin subir";
        estadoArchivo2 = "Foto sin subir";

        estadoVehiculoView.setSmsVehiculo(vehiculoView); //relacionamos el vehiculo con los valores asignados en la seccion de estado
        estadoVehiculoController.registrarEstVeh(estadoVehiculoView);//registramos el estado

        //limpiamos objetos        
        vehiculoView = new SmsVehiculo();
        estadoVehiculoView = new SmsEstadovehiculo();

        //Actualizamos la lista que muestra los vehiculos registrados en el sistema
        vehiculosListView = vehDao.mostrarVehiculo();
    }

    public void modificar() {
        //Consulta estado
        vehiculoView.setSmsEstado(estadoDao.consultarEstado(vehiculoView.getSmsEstado()));

        //Consulta proveedor    
        vehiculoView.setSmsProveedor(provDao.consultarProveedor(vehiculoView.getSmsProveedor()));

        //Consulta categoria      
        vehiculoView.setSmsCategoria(cateDao.consultarCategoria(vehiculoView.getSmsCategoria()));

        //Consulta ciudad        
        vehiculoView.setSmsCiudad(ciuDao.consultarCiudad(vehiculoView.getSmsCiudad()));

        //Consulta referencia      
        vehiculoView.setSmsReferencia(refDao.consultarReferencias(vehiculoView.getSmsReferencia()));

        //Consultar color
        vehiculoView.setSmsColor(colorDao.consultarColor(vehiculoView.getSmsColor()));

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
        operacion = 0;
        habilitarCancelar = true;
        nombre = "Registrar Vehiculo";
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
            estadoVehiculoView = estadoVehDao.consultarEstadoVehiculo(vehiculoView);

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
        operacion = 0;

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

    public List<SmsVehiculo> consultarVehiculosDisponible(SmsReservacion reserva, SmsMercado mercado) {
        vehiculosListView = new ArrayList<>();
        String ciudadVeh = reserva.getSmsCiudadByIdCiudadInicio().getCiudadNombre();
        String mercadoSeleccionado = mercado.getMercadoNombre();

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

        vehiculosListView = vehDao.consultarVehiculosDisponibles(FechaInicio, FechaLlegada, HoraInicio, HoraLlegada, ciudadVeh, espacioinicio, espacioLlegada, mercadoSeleccionado);
        return vehiculosListView;
    }

    public List<SmsVehiculo> consultarVehiculosCiudad(SmsCiudad c) {
        vehiculosListView = new ArrayList<>();
        vehiculoView.setSmsCiudad(c);
        vehiculosListView = vehDao.consultarVehiculosCiudad(vehiculoView.getSmsCiudad());
        return vehiculosListView;
    }

    public List<String> consultarVehiculosSegunProveedor(SmsProveedor proveedor) {
        vehiculosListView = new ArrayList<>();
        placasVehiculos = new ArrayList<>();
        vehiculosListView = vehDao.consultarVehiculosSegunProveedor(proveedor);
        for (int i = 0; i < vehiculosListView.size(); i++) {
            placasVehiculos.add(vehiculosListView.get(i).getVehPlaca());
        }
        return placasVehiculos;
    }

    public List<SmsVehiculo> filtrarVehiculosCiudad(SmsCiudad c, SmsCategoria cat) {
        vehiculosListView = new ArrayList<>();
        vehiculoView.setSmsCiudad(c);
        String categoriaVeh = cat.getCategoriaNombre();
        vehiculosListView = vehDao.filtrarVehiculosCiudad(vehiculoView.getSmsCiudad(), vehiculoView.getSmsCategoria().getCategoriaNombre());
        return vehiculosListView;
    }

    public List<SmsVehiculo> filtrarVehiculosDisponibles(SmsReservacion reserva, SmsCategoria cat, SmsMercado mercado) {
        vehiculosListView = new ArrayList<>();
        String categoriaVeh = cat.getCategoriaNombre();
        String ciudadVeh = reserva.getSmsCiudadByIdCiudadInicio().getCiudadNombre();
        String mercadoSeleccionado = mercado.getMercadoNombre();

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

        vehiculosListView = vehDao.filtrarVehiculosDisponibles(FechaInicio, FechaLlegada, HoraInicio, HoraLlegada, ciudadVeh, categoriaVeh, espacioinicio, espacioLlegada, mercadoSeleccionado);
        return vehiculosListView;
    }
    
    public List<SmsVehiculo> buscarVehiculoSegunPlaca(SmsReservacion reserva, SmsMercado mercado, String placa) {
        vehiculosListView = new ArrayList<>();       
        String ciudadVeh = reserva.getSmsCiudadByIdCiudadInicio().getCiudadNombre();
        String mercadoSeleccionado = mercado.getMercadoNombre();
     
        SimpleDateFormat formatDate;
        SimpleDateFormat formatTime;
        formatDate = new SimpleDateFormat("yyyy-MM-dd");
        formatTime = new SimpleDateFormat("HH:mm:ss");

        String FechaInicio = formatDate.format(reserva.getReservacionFechaInicio());
        String FechaLlegada = formatDate.format(reserva.getReservacionFechaLlegada());
        String HoraInicio = formatTime.format(reserva.getReservacionHoraInicio());
        String HoraLlegada = formatTime.format(reserva.getReservacionHoraLlegada());

        vehiculosListView = vehDao.consultarVehiculoDisponibleSegunPlaca(FechaInicio, FechaLlegada, HoraInicio, HoraLlegada, ciudadVeh, mercadoSeleccionado, placa);
        return vehiculosListView;
    }

    public void asociarVehiculo() {
        //Consultamos objetos
        vehiculoView = vehDao.consultarVehiculo(vehiculoView);
        IEmpleadoDao empleadoDao = new ImpEmpleadoDao();
        empleadoView = empleadoDao.consultarEmpleado(empleadoView.getSmsUsuario());

        //asociamos vehiculo y conductor
        vehiculoView.getSmsEmpleados().add(empleadoView);
        empleadoView.getSmsVehiculos().add(vehiculoView);

        vehDao.modificarVehiculo(vehiculoView);

        vehiculoView = new SmsVehiculo();
        empleadoView = new SmsEmpleado();
        proveedorView = new SmsProveedor();
    }

    public List<String> filtrarVehiculo(SmsProveedor proveedor) {
        placasVehiculos = new ArrayList<>();
        vehiculosListView = new ArrayList<>();
        if (buscar == null) {
            if (proveedor.getProveedorRazonSocial() != null) {
                vehiculosListView = vehDao.consultarVehiculosSegunProveedor(proveedor);
                for (int i = 0; i < vehiculosListView.size(); i++) {
                    placasVehiculos.add(vehiculosListView.get(i).getVehPlaca());
                }
            }
        } else {
            if (proveedor.getProveedorRazonSocial() != null) {
                vehiculosListView = vehDao.filtrarVehiculoSegunProveedor(buscar, proveedor);
                for (int i = 0; i < vehiculosListView.size(); i++) {
                    placasVehiculos.add(vehiculosListView.get(i).getVehPlaca());
                }
            }
        }
        return placasVehiculos;
    }
}
