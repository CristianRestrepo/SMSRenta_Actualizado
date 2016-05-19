/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.controladores;

import com.planit.smsrenta.metodos.Upload;
import com.planit.smsrenta.dao.ICategoriaDao;
import com.planit.smsrenta.dao.ICategoriasServicioDao;
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
import com.planit.smsrenta.dao.ImpCategoriasServicioDao;
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
import com.planit.smsrenta.modelos.SmsCategoriasServicio;
import com.planit.smsrenta.modelos.SmsCiudad;
import com.planit.smsrenta.modelos.SmsEmpleado;
import com.planit.smsrenta.modelos.SmsEstadovehiculo;
import com.planit.smsrenta.modelos.SmsMercado;
import com.planit.smsrenta.modelos.SmsProveedor;
import com.planit.smsrenta.modelos.SmsReservacion;
import com.planit.smsrenta.modelos.SmsVehiculo;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
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
    private List<SmsVehiculo> vehiculosSeleccionados;
    private List<String> placasVehiculos;
    private List<SmsVehiculo> vehiculos;

    //relacion con otros modelos
    private List<SmsCategoriasServicio> categoriasListView;
    private List<String> categoriasSeleccionados;
    private List<SmsEmpleado> conductores;
    private SmsCategoriasServicio categoria;

    //Relacion con otros controladores   
    Upload fileController;
    EstadoVehiculoBean estadoVehiculoController;

    //Variables   
    private String nombre;
    private String buscar;
    private String estadoArchivo1;
    private String estadoArchivo2;
    private String buscarEmpleado;
    private int operacion;
    private int operacionAdministracion;

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
    IEmpleadoDao empleadoDao;

    public VehiculoBean() {

        vehiculoView = new SmsVehiculo();
        proveedorView = new SmsProveedor();
        empleadoView = new SmsEmpleado();

        vehiculosListView = new ArrayList<>();
        vehiculosSeleccionados = new ArrayList<>();

        categoria = new SmsCategoriasServicio();
        categoriasListView = new ArrayList<>();
        categoriasSeleccionados = new ArrayList<>();

        estadoVehiculoView = new SmsEstadovehiculo();
        placasVehiculos = new ArrayList<>();

        habilitarCancelar = true;

        estadoVehiculoController = new EstadoVehiculoBean();
        fileController = new Upload();
        buscar = null;
        buscarEmpleado = null;

        cateDao = new ImpCategoriaDao();
        provDao = new ImpProveedorDao();
        ciuDao = new ImpCiudadDao();
        refDao = new ImpReferenciaDao();
        vehDao = new ImpVehiculoDao();
        usuDao = new ImpUsuarioDao();
        estadoDao = new ImpEstadoDao();
        colorDao = new ImpColorDao();
        estadoVehDao = new ImpEstadoVehiculoDao();
        empleadoDao = new ImpEmpleadoDao();

        nombre = "Registrar Vehiculo";
        operacion = 0;
        operacionAdministracion = 0;
        estadoArchivo1 = "Foto sin subir";
        estadoArchivo2 = "Foto sin subir";

        vehiculos = new ArrayList<>();
        conductores = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        vehiculosListView = vehDao.mostrarVehiculo();

    }

    //Getters & Setters 
    public SmsCategoriasServicio getCategoria() {
        return categoria;
    }

    public void setCategoria(SmsCategoriasServicio categoria) {
        this.categoria = categoria;
    }

    public List<SmsCategoriasServicio> getCategoriasListView() {
        return categoriasListView;
    }

    public void setCategoriasListView(List<SmsCategoriasServicio> categoriasListView) {
        this.categoriasListView = categoriasListView;
    }

    public List<String> getCategoriasSeleccionados() {
        return categoriasSeleccionados;
    }

    public void setCategoriasSeleccionados(List<String> categoriasSeleccionados) {
        this.categoriasSeleccionados = categoriasSeleccionados;
    }

    public List<SmsVehiculo> getVehiculosSeleccionados() {
        return vehiculosSeleccionados;
    }

    public void setVehiculosSeleccionados(List<SmsVehiculo> vehiculosSeleccionados) {
        this.vehiculosSeleccionados = vehiculosSeleccionados;
    }

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

    public List<SmsVehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<SmsVehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    public List<SmsEmpleado> getConductores() {
        return conductores;
    }

    public void setConductores(List<SmsEmpleado> conductores) {
        this.conductores = conductores;
    }

    public String getBuscarEmpleado() {
        return buscarEmpleado;
    }

    public void setBuscarEmpleado(String buscarEmpleado) {
        this.buscarEmpleado = buscarEmpleado;
    }

    public int getOperacionAdministracion() {
        return operacionAdministracion;
    }

    public void setOperacionAdministracion(int operacionAdministracion) {
        this.operacionAdministracion = operacionAdministracion;
    }

    //Definicion de metodos VEHICULO
    //Metodos CRUD y complementos
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

    public void cancelarCRUD() {
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

    //Metodos para filtrar o consultar vehiculos
    public List<SmsVehiculo> consultarVehiculosDisponible(SmsReservacion reserva, SmsMercado mercado, int categoriaServicio) {
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

        vehiculosListView = vehDao.consultarVehiculosDisponibles(FechaInicio, FechaLlegada, HoraInicio, HoraLlegada, ciudadVeh, espacioinicio, espacioLlegada, mercadoSeleccionado, categoriaServicio);
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

    public List<SmsVehiculo> filtrarVehiculosDisponibles(SmsReservacion reserva, SmsCategoria cat, SmsMercado mercado, int categoriaServicio) {
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

        vehiculosListView = vehDao.filtrarVehiculosDisponibles(FechaInicio, FechaLlegada, HoraInicio, HoraLlegada, ciudadVeh, categoriaVeh, espacioinicio, espacioLlegada, mercadoSeleccionado, categoriaServicio);
        return vehiculosListView;
    }

    public List<SmsVehiculo> buscarVehiculoSegunPlaca(SmsReservacion reserva, SmsMercado mercado, String placa, int categoriaServicio) {
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

        vehiculosListView = vehDao.consultarVehiculoDisponibleSegunPlaca(FechaInicio, FechaLlegada, HoraInicio, HoraLlegada, ciudadVeh, mercadoSeleccionado, placa, categoriaServicio);
        return vehiculosListView;
    }

    public void filtrarVehiculos() {
        vehiculosListView = new ArrayList<>();
        if (buscar == null) {
            vehiculosListView = vehDao.mostrarVehiculo();
        } else {
            vehiculosListView = vehDao.filtrarVehiculos(buscar);
        }
    }

    //Metodos para la asociacion de vehiculos con conductores
    public void asociarVehiculosConductores() {
        //obtenemos los objetos vehiculo y empleado con sus colecciones habilitadas
        vehiculoView = vehDao.consultarVehiculoConConductores(vehiculoView);
        empleadoView = empleadoDao.consultarEmpleadoConVehiculo(empleadoView);

        //asociamos vehiculo y conductor
        vehiculoView.getSmsEmpleados().add(empleadoView);
        empleadoView.getSmsVehiculos().add(vehiculoView);

        vehDao.modificarVehiculo(vehiculoView);

        vehiculoView = new SmsVehiculo();
        empleadoView = new SmsEmpleado();
        proveedorView = new SmsProveedor();
        vehiculos = new ArrayList<>();
        conductores = new ArrayList<>();
    }

    public void cargarVehiculoEmpleadosSegunProveedor() {
        if (proveedorView.getProveedorRazonSocial() != null) {
            vehiculos = vehDao.consultarVehiculosSegunProveedor(proveedorView);
            conductores = empleadoDao.consultarEmpleadosSegunProveedor(proveedorView);
        }
    }

    public void filtrarVehiculoSegunProveedor(SmsProveedor proveedor) {
        vehiculos = new ArrayList<>();
        if (buscar == null) {
            if (proveedor.getProveedorRazonSocial() != null) {
                vehiculos = vehDao.consultarVehiculosSegunProveedor(proveedor);
            }
        } else {
            if (proveedor.getProveedorRazonSocial() != null) {
                vehiculos = vehDao.filtrarVehiculoSegunProveedor(buscar, proveedor);
            }
        }
    }

    public void filtrarEmpleadoSegunProveedor(SmsProveedor proveedor) {
        conductores = new ArrayList<>();
        if (buscarEmpleado == null) {
            if (proveedor.getProveedorRazonSocial() != null) {
                conductores = empleadoDao.consultarEmpleadosSegunProveedor(proveedor);
            }
        } else {
            if (proveedor.getProveedorRazonSocial() != null) {
                conductores = empleadoDao.filtrarUsuariosEmpleadosSegunProveedor(buscarEmpleado, proveedor);
            }
        }
    }

    public void cancelarAsociacionVehiculoConductor() {
        vehiculoView = new SmsVehiculo();
        empleadoView = new SmsEmpleado();
        proveedorView = new SmsProveedor();
        vehiculos = new ArrayList<>();
        conductores = new ArrayList<>();
    }

    //metodos para asociacion de vehiculos con servicios
    public void asociar() {
        operacionAdministracion = 1;
        habilitarCancelar = false;
        vehiculos = new ArrayList<>();
        vehiculos = vehDao.mostrarVehiculo();
    }

    public void remover() {
        operacionAdministracion = 2;
        habilitarCancelar = false;
        vehiculos = new ArrayList<>();
    }

    public void cancelarAsociacionVehiculoServicios() {
        operacionAdministracion = 0;
        habilitarCancelar = true;
        categoriasSeleccionados = new ArrayList<>();
        vehiculosSeleccionados = new ArrayList<>();
        vehiculos = new ArrayList<>();
        categoria = new SmsCategoriasServicio();
        vehiculoView = new SmsVehiculo();
    }

    public void filtrarVehiculosConsultados() {
        if (operacionAdministracion == 1) {
            vehiculos = new ArrayList<>();
            if (buscar == null) {
                vehiculos = vehDao.mostrarVehiculo();
            } else {
                vehiculos = vehDao.filtrarVehiculos(buscar);
            }
        } else if (operacionAdministracion == 2) {
            vehiculos = new ArrayList<>();
            if (buscar == null) {
                vehiculos = vehDao.consultarVehiculosSegunCategoriaServicio(categoria);
            } else {
                vehiculos = vehDao.filtrarVehiculosSegunCategoriaServicio(buscar, categoria);
            }
        }
    }

    public void consultarVehiculoSegunCategoria() {
        vehiculos = new ArrayList<>();
        vehiculosSeleccionados = new ArrayList<>();

        ICategoriasServicioDao catSerDao = new ImpCategoriasServicioDao();
        categoria = catSerDao.consultarCategoriaServicio(categoria);

        vehiculos = vehDao.consultarVehiculosSegunCategoriaServicio(categoria);
    }

    public void asociarVehiculosServicios() {
        ICategoriasServicioDao catSerDao = new ImpCategoriasServicioDao();
        SmsCategoriasServicio catServicio;
        categoriasListView = new ArrayList<>();
        //Consultamos la informacion completa de las categorias seleccionadas
        for (int i = 0; i < categoriasSeleccionados.size(); i++) {
            catServicio = new SmsCategoriasServicio();
            catServicio.setCatNombre(categoriasSeleccionados.get(i));
            catServicio = catSerDao.consultarCategoriaServicioConVehiculos(catServicio);
            categoriasListView.add(catServicio);//Almacenamos los objetos completos en una nueva lista
        }

        List<SmsVehiculo> vehiculosParaAsociar = new ArrayList<>();

        //Consultamos los vehiculos seleccionados para habilitar la relacion con las categorias de servicios
        for (int i = 0; i < vehiculosSeleccionados.size(); i++) {
            vehiculosParaAsociar.add(vehDao.consultarVehiculoConCategorias(vehiculosSeleccionados.get(i)));
        }

        //Asociamos los objetos
        for (int i = 0; i < categoriasListView.size(); i++) {
            for (int j = 0; j < vehiculosParaAsociar.size(); j++) {
                boolean existeVeh = false;
                
                //Validamos que el vehiculo j no este asociado a la categoria i
                for (SmsVehiculo vehiculo : categoriasListView.get(i).getSmsVehiculos()) {
                    if (vehiculo.getIdVehiculo().equals(vehiculosParaAsociar.get(j).getIdVehiculo())) {
                        existeVeh = true;
                    }
                }
                if (!existeVeh) {
                    categoriasListView.get(i).getSmsVehiculos().add(vehiculosParaAsociar.get(j));
                }

                boolean existeCat = false;
                
                //validamos que la categoria i no este ya asociada al vehiculo j
                for (SmsCategoriasServicio cat : vehiculosParaAsociar.get(j).getSmsCategoriasServicios()) {
                    if (cat.getIdCategoriaServicio().equals(categoriasListView.get(i).getIdCategoriaServicio())) {
                        existeCat = true;
                    }
                }
                if (!existeCat) {
                    vehiculosParaAsociar.get(j).getSmsCategoriasServicios().add(categoriasListView.get(i));
                }
            }
        }

        for (int i = 0; i < vehiculosParaAsociar.size(); i++) {
            vehDao.asociarVehiculo(vehiculosParaAsociar.get(i));
        }

//        for (int i = 0; i < vehiculosParaAsociar.size(); i++) {
//            vehiculoView = new SmsVehiculo();
//            vehiculoView = vehiculosParaAsociar.get(i);
//            for (int j = 0; j < categoriasListView.size(); j++) {
//                catServicio = categoriasListView.get(j);
//                catServicio.getSmsVehiculos().add(vehiculoView);
//                vehiculoView.getSmsCategoriasServicios().add(catServicio);
//                vehDao.asociarVehiculo(vehiculoView);
//            }
//        }
        //Limpiamos objetos
        categoriasSeleccionados = new ArrayList<>();
        vehiculosSeleccionados = new ArrayList<>();
        vehiculoView = new SmsVehiculo();
        categoria = new SmsCategoriasServicio();

        //Enviamos mensaje de confirmacion a la vista
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Vehiculos asociados", "");

        FacesContext.getCurrentInstance()
                .addMessage(null, message);
    }

    public void removerVehiculosServicios() {

        List<SmsVehiculo> vehiculosParaRemover = new ArrayList<>();

        //Consultamos los vehiculos seleccionados para habilitar la relacion con las categorias de servicios
        for (int i = 0; i < vehiculosSeleccionados.size(); i++) {
            vehiculosParaRemover.add(vehDao.consultarVehiculoConCategorias(vehiculosSeleccionados.get(i)));
        }

        //Removemos los objetos
        for (int i = 0; i < vehiculosParaRemover.size(); i++) {

            SmsCategoriasServicio categoriaConVehiculos = new SmsCategoriasServicio();
            ICategoriasServicioDao catDao = new ImpCategoriasServicioDao();
            categoriaConVehiculos = catDao.consultarCategoriaServicioConVehiculos(categoria);

            for (SmsVehiculo veh : categoriaConVehiculos.getSmsVehiculos()) {
                if (veh.getIdVehiculo().equals(vehiculosParaRemover.get(i).getIdVehiculo())) {
                    vehiculoView = veh;
                }
            }

            SmsCategoriasServicio cat = new SmsCategoriasServicio();
            for (SmsCategoriasServicio categoriaServicio : vehiculoView.getSmsCategoriasServicios()) {
                if (categoriaConVehiculos.getIdCategoriaServicio().equals(categoriaServicio.getIdCategoriaServicio())) {
                    cat = categoriaServicio;
                }
            }

            categoriaConVehiculos.getSmsVehiculos().remove(vehiculoView);
            vehiculoView.getSmsCategoriasServicios().remove(cat);
            vehDao.asociarVehiculo(vehiculoView);
        }

        //Limpiamos objetos
        categoriasSeleccionados = new ArrayList<>();
        vehiculos = new ArrayList<>();
        categoria = new SmsCategoriasServicio();
        vehiculosSeleccionados = new ArrayList<>();
        vehiculoView = new SmsVehiculo();

        //Enviamos mensaje de confirmacion a la vista
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Vehiculos Desligados", "");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    //Metodos para subida de archivos  
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

}
