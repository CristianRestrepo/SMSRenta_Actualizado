/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.controladores;

import com.planit.smsrenta.dao.ICategoriasServicioDao;
import com.planit.smsrenta.metodos.SendEmail;
import com.planit.smsrenta.modelos.SmsReservacion;
import com.planit.smsrenta.dao.ICiudadDao;
import com.planit.smsrenta.dao.ICostosServiciosDao;
import com.planit.smsrenta.dao.IEmpleadoDao;
import com.planit.smsrenta.dao.IEstadoDao;
import com.planit.smsrenta.dao.ILugarDao;
import com.planit.smsrenta.dao.IParametrosReservacionDao;
import com.planit.smsrenta.dao.IReservacionDao;
import com.planit.smsrenta.dao.IServicioDao;
import com.planit.smsrenta.dao.IUsuarioDao;
import com.planit.smsrenta.dao.IVehiculoDao;
import com.planit.smsrenta.dao.ImpCategoriasServicioDao;
import com.planit.smsrenta.dao.ImpCiudadDao;
import com.planit.smsrenta.dao.ImpCostosServiciosDao;
import com.planit.smsrenta.dao.ImpEmpleadoDao;
import com.planit.smsrenta.dao.ImpEstadoDao;
import com.planit.smsrenta.dao.ImpLugarDao;
import com.planit.smsrenta.dao.ImpParametrosReservacionDao;
import com.planit.smsrenta.dao.ImpReservacionDao;
import com.planit.smsrenta.dao.ImpServicioDao;
import com.planit.smsrenta.dao.ImpUsuarioDao;
import com.planit.smsrenta.dao.ImpVehiculoDao;
import com.planit.smsrenta.metodos.GenerarReportes;
import com.planit.smsrenta.metodos.Sesion;
import com.planit.smsrenta.modelos.SmsCategoria;
import com.planit.smsrenta.modelos.SmsCategoriasServicio;
import com.planit.smsrenta.modelos.SmsCostosservicios;
import com.planit.smsrenta.modelos.SmsEmpleado;
import com.planit.smsrenta.modelos.SmsEstado;
import com.planit.smsrenta.modelos.SmsLugares;
import com.planit.smsrenta.modelos.SmsMercado;
import com.planit.smsrenta.modelos.SmsParametrosReservacion;
import com.planit.smsrenta.modelos.SmsServicios;
import com.planit.smsrenta.modelos.SmsUsuario;
import com.planit.smsrenta.modelos.SmsVehiculo;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author Desarrollo_Planit
 */
public class ReservacionBean implements Serializable {

    private List<SmsReservacion> reservacionesListView;
    private List<SmsVehiculo> vehiculosListView;
    private List<SmsEmpleado> empleadoListView;
    private List<SmsReservacion> reservacionesCliente;

    private int categoriaServicio; //Controla el tipo de servicio elegido, modificando el comportamiento de la reservacion

    private SmsReservacion reservaView;
    private SmsReservacion modReservacionView;

    private SmsCostosservicios costoServicioView;
    private SmsCategoria categoriaView;
    private SmsEstado estadoView;
    private SmsEmpleado empleadoView;
    private SmsMercado mercadoSeleccionado;

    private SmsUsuario sesion; //objeto donde guardaremos los datos del usuario logueado

    private String horaInicio;
    private String horaEntrega;
    private String minutosInicio;
    private String minutosEntrega;
    private String fechaInicio;
    private String fechaEntrega;
    private String buscar;

    //variables para filtrar las reservaciones segun estado y mercado al que pertenencen
    private int estadoReservacion;
    private String mercadoReservacion;

    private boolean skip = false;//Controla la transicion entre las pestañas del panel de reserva
    //Controlan la seleccion de los vehiculos y los empleados
    private boolean SelecVeh;
    private boolean SelecCon;

    //controla la aparicion del boton siguiente en el proceso de reservacion
    private boolean siguiente;
    private boolean atras;

    //Clases
    SendEmail emailController;
    VehiculoBean vehiculoController;
    EmpleadoBean empleadoController;
    FacturaBean facturaController;
    Sesion sesionController;

    //VARIABLES PARA CREAR EL SCHEDULE DE PRIMEFACES
    private ScheduleModel eventoModelo;
    private ScheduleEvent evento;

    //Comunicacion con el dao
    ICiudadDao ciuDao;
    IEstadoDao estadoDao;
    IReservacionDao resDao;
    IUsuarioDao usuDao;
    IEmpleadoDao empleadoDao;
    ICategoriasServicioDao catServicioDao;
    IVehiculoDao vehiculoDao;
    IServicioDao servicioDao;
    ICostosServiciosDao costoDao;

    public ReservacionBean() {

        reservaView = new SmsReservacion();
        modReservacionView = new SmsReservacion();
        estadoView = new SmsEstado();
        costoServicioView = new SmsCostosservicios();
        empleadoView = new SmsEmpleado();
        categoriaServicio = 0;
        buscar = null;

        vehiculosListView = new ArrayList<>();
        empleadoListView = new ArrayList<>();
        reservacionesCliente = new ArrayList<>();

        emailController = new SendEmail();
        vehiculoController = new VehiculoBean();
        empleadoController = new EmpleadoBean();
        facturaController = new FacturaBean();
        sesionController = new Sesion();

        siguiente = true;
        atras = false;
        SelecVeh = false;
        SelecCon = false;

        //VARIABLES PARA CREAR EL CALENDARIO DE PRIMEFACES
        eventoModelo = new DefaultScheduleModel();
        evento = new DefaultScheduleEvent();

        //Conexion con los dao
        ciuDao = new ImpCiudadDao();
        resDao = new ImpReservacionDao();
        usuDao = new ImpUsuarioDao();
        servicioDao = new ImpServicioDao();
        empleadoDao = new ImpEmpleadoDao();
        costoDao = new ImpCostosServiciosDao();
        estadoDao = new ImpEstadoDao();
        catServicioDao = new ImpCategoriasServicioDao();
        vehiculoDao = new ImpVehiculoDao();

        mercadoSeleccionado = new SmsMercado();
        categoriaView = new SmsCategoria();

        estadoReservacion = 0;
        mercadoReservacion = "todas";
    }

    @PostConstruct
    public void init() {
        //Obtenemos la informacion de sesion del usuario autentificado 
        sesion = sesionController.obtenerSesion();

        addEventoCalendario();
        reservacionesListView = consultarReservacionesSegunUsuario();
    }

    public SmsCostosservicios getCostoServicioView() {
        return costoServicioView;
    }

    public void setCostoServicioView(SmsCostosservicios costoServicioView) {
        this.costoServicioView = costoServicioView;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraEntrega() {
        return horaEntrega;
    }

    public int getEstadoReservacion() {
        return estadoReservacion;
    }

    public void setEstadoReservacion(int estadoReservacion) {
        this.estadoReservacion = estadoReservacion;
    }

    public void setHoraEntrega(String horaEntrega) {
        this.horaEntrega = horaEntrega;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public boolean isSelecVeh() {
        return SelecVeh;
    }

    public void setSelecVeh(boolean SelecVeh) {
        this.SelecVeh = SelecVeh;
    }

    public boolean isSelecCon() {
        return SelecCon;
    }

    public void setSelecCon(boolean SelecCon) {
        this.SelecCon = SelecCon;
    }

    public ScheduleModel getEventoModelo() {
        return eventoModelo;
    }

    public void setEventoModelo(ScheduleModel eventoModelo) {
        this.eventoModelo = eventoModelo;
    }

    public ScheduleEvent getEvento() {
        return evento;
    }

    public void setEvento(ScheduleEvent evento) {
        this.evento = evento;
    }

    public List<SmsReservacion> getReservacionesListView() {
        return reservacionesListView;
    }

    public void setReservacionesListView(List<SmsReservacion> reservacionesListView) {
        this.reservacionesListView = reservacionesListView;
    }

    public SmsReservacion getReservaView() {
        return reservaView;
    }

    public void setReservaView(SmsReservacion reservaView) {
        this.reservaView = reservaView;
    }

    public String getMinutosInicio() {
        return minutosInicio;
    }

    public void setMinutosInicio(String minutosInicio) {
        this.minutosInicio = minutosInicio;
    }

    public String getMinutosEntrega() {
        return minutosEntrega;
    }

    public void setMinutosEntrega(String minutosEntrega) {
        this.minutosEntrega = minutosEntrega;
    }

    public SmsMercado getMercadoSeleccionado() {
        return mercadoSeleccionado;
    }

    public void setMercadoSeleccionado(SmsMercado mercadoSeleccionado) {
        this.mercadoSeleccionado = mercadoSeleccionado;
    }

    public List<SmsVehiculo> getVehiculosListView() {
        return vehiculosListView;
    }

    public void setVehiculosListView(List<SmsVehiculo> vehiculosListView) {
        this.vehiculosListView = vehiculosListView;
    }

    public List<SmsEmpleado> getEmpleadoListView() {
        return empleadoListView;
    }

    public void setEmpleadoListView(List<SmsEmpleado> empleadoListView) {
        this.empleadoListView = empleadoListView;
    }

    public SmsCategoria getCategoriaView() {
        return categoriaView;
    }

    public void setCategoriaView(SmsCategoria categoriaView) {
        this.categoriaView = categoriaView;
    }

    public SmsEmpleado getEmpleadoView() {
        return empleadoView;
    }

    public void setEmpleadoView(SmsEmpleado empleadoView) {
        this.empleadoView = empleadoView;
    }

    public SmsReservacion getModReservacionView() {
        return modReservacionView;
    }

    public void setModReservacionView(SmsReservacion modReservacionView) {
        this.modReservacionView = modReservacionView;
    }

    public int getCategoriaServicio() {
        return categoriaServicio;
    }

    public void setCategoriaServicio(int categoriaServicio) {
        this.categoriaServicio = categoriaServicio;
    }

    public boolean isSiguiente() {
        return siguiente;
    }

    public void setSiguiente(boolean siguiente) {
        this.siguiente = siguiente;
    }

    public boolean isAtras() {
        return atras;
    }

    public void setAtras(boolean atras) {
        this.atras = atras;
    }

    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }

    public String getMercadoReservacion() {
        return mercadoReservacion;
    }

    public void setMercadoReservacion(String mercadoReservacion) {
        this.mercadoReservacion = mercadoReservacion;
    }

    public List<SmsReservacion> getReservacionesCliente() {
        return reservacionesCliente;
    }

    public void setReservacionesCliente(List<SmsReservacion> reservacionesCliente) {
        this.reservacionesCliente = reservacionesCliente;
    }

    //Metodos    
    //CRUD
    public String registrarReservacion() throws JRException, IOException {

        estadoView.setEstadoNombre("Inactiva");
        reservaView.setSmsEstado(estadoDao.consultarEstado(estadoView));

        //Registro
        resDao.registrarReservacion(reservaView);
        facturaController.generarFacturaParaCorreo(reservaView);

        //Enviamos mensajes al administrador del sistema, el cliente y el conductor
        if (reservaView.getSmsEmpleado() != null) {
            emailController.sendEmailAdministrador(reservaView.getSmsEmpleado(), reservaView.getSmsVehiculo(), reservaView, reservaView.getSmsUsuario());
            emailController.sendEmailCliente(reservaView.getSmsEmpleado(), reservaView.getSmsVehiculo(), reservaView, reservaView.getSmsUsuario(), GenerarReportes.rutaDocumento);
            emailController.sendEmailConductor(reservaView.getSmsEmpleado(), reservaView.getSmsVehiculo(), reservaView, reservaView.getSmsUsuario());
        } else {
            emailController.sendEmailAdministradorWithout(reservaView.getSmsVehiculo(), reservaView, reservaView.getSmsUsuario());
            emailController.sendEmailClienteWithout(reservaView.getSmsVehiculo(), reservaView, reservaView.getSmsUsuario(), GenerarReportes.rutaDocumento);
        }

        //Recargamos las lista de reservaciones que se muestran en las vistas
        addEventoCalendario();

        //Habilitamos la seleccion de vehiculos y conductores
        SelecVeh = false;
        SelecCon = false;

        horaInicio = "";
        horaEntrega = "";
        fechaInicio = "";
        fechaEntrega = "";
        minutosEntrega = "";
        minutosInicio = "";

        String ruta = "";

        if (sesion.getSmsRol().getRolNombre().equalsIgnoreCase("Administrador Principal")) {
            ruta = "RegresarAdminPReservacion";
        } else if (sesion.getSmsRol().getRolNombre().equalsIgnoreCase("Cliente")) {
            ruta = "RegresarClienteReservacion";
        } else if (sesion.getSmsRol().getRolNombre().equalsIgnoreCase("Administrador Secundario")) {
            ruta = "RegresarAdminSReservacion";
        }

        //Limpieza de objetos 
        reservaView = new SmsReservacion();
        reservacionesListView = consultarReservacionesSegunUsuario();
        siguiente = true;
        atras = false;
        return ruta;
    }

    public String cancelarReservacion() {
        boolean valor = validarCancelarReservacion(modReservacionView);
        String Ruta = "";
        if (valor) {
            SmsEstado estado = new SmsEstado();
            estado.setEstadoNombre("Cancelada");
            estado = estadoDao.consultarEstado(estado);
            modReservacionView.setSmsEstado(estado);
            resDao.modificarReservacion(modReservacionView);

            //resDao.eliminarReservacion(modReservacionView);
            modReservacionView = new SmsReservacion();
            costoServicioView = new SmsCostosservicios();
            switch (sesion.getSmsRol().getRolNombre()) {
                case "Administrador Principal":
                    Ruta = "AdminPDashboard";
                    break;

                case "Administrador Secundario":
                    Ruta = "AdminSDashboard";
                    break;

                case "Cliente":
                    Ruta = "ClienteDashboard";
                    break;
            }

            consultarReservacionesSegunUsuario(); //Recargamos las lista de reservaciones que se muestran en las vistas
            addEventoCalendario();
            reservacionesListView = consultarReservacionesSegunUsuario();
            modReservacionView = new SmsReservacion();
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Imposible cancelar la reservacion", "La reservacion se hara valida en menos de dos horas");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

        return Ruta;
    }

    //Especificos 
    ///Controla el flujo de la vista de reservacion
    public void filtrarReservaciones() {
        if (buscar == null) {
            reservacionesListView = consultarReservacionesSegunUsuario();
        } else {
            if (mercadoReservacion.equalsIgnoreCase("todas")) {
                if (estadoReservacion == 0) {
                    reservacionesListView = resDao.filtrarReservacionSegunCliente(buscar);
                } else {
                    reservacionesListView = resDao.filtrarReservacionSegunClienteSegunEstado(buscar, estadoReservacion);
                }
            } else {
                if (estadoReservacion == 0) {
                    reservacionesListView = resDao.filtrarReservacionSegunClienteSegunMercado(buscar, mercadoReservacion);
                } else {
                    reservacionesListView = resDao.filtrarReservacionSegunClienteSegunEstadoyMercado(buscar, estadoReservacion, mercadoReservacion);
                }
            }
        }
    }

    public String flujoDeReservacion(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "Confirmacion";
        } else {
            switch (event.getNewStep()) {
                case "Agenda":
                    atras = false;
                    horaInicio = "";
                    horaEntrega = "";
                    minutosEntrega = "";
                    minutosInicio = "";
                    break;
                case "Vehiculo":
                    SelecVeh = false;
                    siguiente = true;
                    atras = true;

                    SimpleDateFormat formatTime;
                    SimpleDateFormat formatDate;
                    formatTime = new SimpleDateFormat("HH:mm:ss");
                    formatDate = new SimpleDateFormat("yyyy-MM-dd");
                    Date fechaActual = new Date();
                    Date mediaNoche = new Date();
                    Date umbral = new Date();

                    if (sesion.getSmsRol().getRolNombre().equalsIgnoreCase("Cliente")) {
                        //si el usuario logueado es de tipo cliente asignanos su informacion al objeto cliente
                        reservaView.setSmsUsuario(sesion);
                    } else {
                        //Si el usuario no es cliente, se asigna como cliente al usuario seleccionado en el panel de reservacion
                        reservaView.setSmsUsuario(usuDao.consultarUsuario(reservaView.getSmsUsuario()));
                    }

                    reservaView.setSmsVehiculo(new SmsVehiculo());//Se instancia un nuevo objeto vehiculo para recibir el vehiculo elegido en la reservacion
                    reservaView.setSmsServicios(servicioDao.ConsultarServicio(reservaView.getSmsServicios()));//Consulta de servicio

                    //Consultamos la informacion completa de las ciudades y la categoria del servicio elegida
                    reservaView.setSmsCiudadByIdCiudadInicio(ciuDao.consultarCiudad(reservaView.getSmsCiudadByIdCiudadInicio()));
                    reservaView.setSmsCiudadByIdCiudadDestino(ciuDao.consultarCiudad(reservaView.getSmsCiudadByIdCiudadDestino()));
                    reservaView.setSmsCategoriasServicio(reservaView.getSmsServicios().getSmsCategoriasServicio());

                    switch (categoriaServicio) {

                        case 1: //tiempo
                            reservaView.setReservacionFechaInicio(fechaActual);
                            reservaView.setReservacionFechaLlegada(fechaActual);

                            try {
                                //Se formatea la hora de inicio elegida y se asigna a la reservacion
                                reservaView.setReservacionHoraInicio(formatTime.parse(horaInicio + ":" + minutosInicio));
                                mediaNoche = formatTime.parse("00:00:00");
                                umbral = formatTime.parse("23:30:00");

                                Calendar calInicio1 = Calendar.getInstance();
                                calInicio1.setTime(reservaView.getReservacionHoraInicio());

                                Calendar calFechaEntrega = Calendar.getInstance();

                                if (reservaView.getSmsServicios().getSmsTipoDuracion().getIdTipoDuracion() == 1) { //duracion minutos
                                    calInicio1.add(Calendar.MINUTE, reservaView.getSmsServicios().getServicioDuracion());
                                } else if (reservaView.getSmsServicios().getSmsTipoDuracion().getIdTipoDuracion() == 2) {//duracion horas
                                    calInicio1.add(Calendar.HOUR, reservaView.getSmsServicios().getServicioDuracion());
                                }

                                reservaView.setReservacionHoraLlegada(formatTime.parse(formatTime.format(calInicio1.getTime())));
                                calFechaEntrega.setTime(reservaView.getReservacionFechaLlegada());

                                if ((reservaView.getReservacionHoraInicio().equals(umbral) || reservaView.getReservacionHoraInicio().after(umbral)) && (reservaView.getReservacionHoraLlegada().after(mediaNoche) || reservaView.getReservacionHoraLlegada().equals(mediaNoche))) {
                                    calFechaEntrega.add(Calendar.DATE, 1);
                                    reservaView.setReservacionFechaLlegada(formatDate.parse(formatDate.format(calFechaEntrega.getTime())));
                                }

                            } catch (ParseException pe) {
                                pe.getMessage();
                            }

                            fechaInicio = formatDate.format(reservaView.getReservacionFechaInicio());
                            fechaEntrega = formatDate.format(reservaView.getReservacionFechaLlegada());
                            horaInicio = formatTime.format(reservaView.getReservacionHoraInicio());
                            horaEntrega = formatTime.format(reservaView.getReservacionHoraLlegada());
                            break;
                        case 2://traslado                            

                            reservaView.setReservacionFechaInicio(fechaActual);
                            reservaView.setReservacionFechaLlegada(fechaActual);

                            try {
                                reservaView.setReservacionHoraInicio(formatTime.parse(horaInicio + ":" + minutosInicio));
                                mediaNoche = formatTime.parse("00:00:00");
                                umbral = formatTime.parse("23:30:00");

                                Calendar calInicio1 = Calendar.getInstance();
                                calInicio1.setTime(reservaView.getReservacionHoraInicio());

                                Calendar calFechaEntrega = Calendar.getInstance();

                                if (reservaView.getSmsServicios().getSmsTipoDuracion().getIdTipoDuracion() == 1) {//duracion minutos
                                    calInicio1.add(Calendar.MINUTE, reservaView.getSmsServicios().getServicioDuracion());
                                } else if (reservaView.getSmsServicios().getSmsTipoDuracion().getIdTipoDuracion() == 2) {//duracion horas
                                    calInicio1.add(Calendar.HOUR, reservaView.getSmsServicios().getServicioDuracion());
                                }

                                reservaView.setReservacionHoraLlegada(formatTime.parse(formatTime.format(calInicio1.getTime())));

                                calFechaEntrega.setTime(reservaView.getReservacionFechaLlegada());

                                if ((reservaView.getReservacionHoraInicio().equals(umbral) || reservaView.getReservacionHoraInicio().after(umbral)) && (reservaView.getReservacionHoraLlegada().after(mediaNoche) || reservaView.getReservacionHoraLlegada().equals(mediaNoche))) {
                                    calFechaEntrega.add(Calendar.DATE, 1);
                                    reservaView.setReservacionFechaLlegada(formatDate.parse(formatDate.format(calFechaEntrega.getTime())));
                                }

                            } catch (ParseException pe) {
                                pe.getMessage();
                            }

                            fechaInicio = formatDate.format(fechaActual);
                            fechaEntrega = formatDate.format(reservaView.getReservacionFechaLlegada());
                            horaInicio = formatTime.format(reservaView.getReservacionHoraInicio());
                            horaEntrega = formatTime.format(reservaView.getReservacionHoraLlegada());
                            break;
                        case 3://renta
                            try {
                                reservaView.setReservacionHoraInicio(formatTime.parse(horaInicio + ":" + minutosInicio));
                                reservaView.setReservacionHoraLlegada(formatTime.parse(horaEntrega + ":" + minutosEntrega));
                            } catch (ParseException pe) {
                                pe.getMessage();
                            }

                            fechaInicio = formatDate.format(reservaView.getReservacionFechaInicio());
                            fechaEntrega = formatDate.format(reservaView.getReservacionFechaLlegada());
                            horaInicio = formatTime.format(reservaView.getReservacionHoraInicio());
                            horaEntrega = formatTime.format(reservaView.getReservacionHoraLlegada());
                            break;
                    }

                    if (resDao.mostrarReservaciones().isEmpty()) {
                        vehiculosListView = vehiculoDao.mostrarVehiculo();
                    } else {
                        vehiculosListView = vehiculoController.consultarVehiculosDisponible(reservaView, mercadoSeleccionado);
                    }
                    break;
                case "Conductor":
                    siguiente = true;
                    atras = true;
                    if (reservaView.getSmsServicios().getServicioConductor() == 1) {
                        reservaView.setSmsEmpleado(new SmsEmpleado());
                    }
                    SelecCon = false;
                    empleadoListView = empleadoDao.consultarEmpleadosSegunVehiculo(reservaView.getSmsVehiculo());
                    reservaView.setReservacionCosto(calcularCostoReservacion(reservaView));
                    break;
                case "Confirmacion":
                    siguiente = false;
                    atras = true;
                    if (empleadoView.getIdEmpleado() != null) {
                        reservaView.setSmsEmpleado(empleadoView);
                    }
                    break;
            }

            String newtab;
            newtab = event.getNewStep();

            if (event.getOldStep().equalsIgnoreCase("Conductor") && event.getNewStep().equalsIgnoreCase("Confirmacion") && reservaView.getSmsServicios().getServicioConductor() == 1 && reservaView.getSmsEmpleado().getIdEmpleado() == null) {
                siguiente = true;
                atras = true;
                newtab = "Conductor";
            }

            if (event.getOldStep().equalsIgnoreCase("Vehiculo") && event.getNewStep().equalsIgnoreCase("Conductor") && reservaView.getSmsServicios().getServicioConductor() == 0) {// 0 = sin conductor 
                siguiente = false;
                atras = true;
                newtab = "Confirmacion";
            }

            if (event.getOldStep().equalsIgnoreCase("Confirmacion") && event.getNewStep().equalsIgnoreCase("Conductor") && reservaView.getSmsServicios().getServicioConductor() == 0) {
                SelecVeh = false;
                siguiente = true;
                atras = true;
                SimpleDateFormat formatTime;
                SimpleDateFormat formatDate;
                formatTime = new SimpleDateFormat("HH:mm:ss");
                formatDate = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaActual = new Date();
                Date mediaNoche = new Date();
                Date umbral = new Date();

                if (sesion.getSmsRol().getRolNombre().equalsIgnoreCase("Cliente")) {//si el usuario logueado es de tipo cliente asignanos su informacion al objeto cliente
                    reservaView.setSmsUsuario(sesion);
                } else {
                    reservaView.setSmsUsuario(usuDao.consultarUsuario(reservaView.getSmsUsuario()));
                }
                reservaView.setSmsVehiculo(new SmsVehiculo());
                reservaView.setSmsServicios(servicioDao.ConsultarServicio(reservaView.getSmsServicios()));//Consulta de servicio

                reservaView.setSmsCiudadByIdCiudadInicio(ciuDao.consultarCiudad(reservaView.getSmsCiudadByIdCiudadInicio()));
                reservaView.setSmsCiudadByIdCiudadDestino(ciuDao.consultarCiudad(reservaView.getSmsCiudadByIdCiudadDestino()));
                reservaView.setSmsCategoriasServicio(reservaView.getSmsServicios().getSmsCategoriasServicio());

                switch (categoriaServicio) {

                    case 1: //tiempo
                        reservaView.setReservacionFechaInicio(fechaActual);
                        reservaView.setReservacionFechaLlegada(fechaActual);

                        try {
                            reservaView.setReservacionHoraInicio(formatTime.parse(horaInicio + ":" + minutosInicio));
                            mediaNoche = formatTime.parse("00:00:00");
                            umbral = formatTime.parse("23:30:00");

                            Calendar calInicio1 = Calendar.getInstance();
                            calInicio1.setTime(reservaView.getReservacionHoraInicio());

                            Calendar calFechaEntrega = Calendar.getInstance();

                            if (reservaView.getSmsServicios().getSmsTipoDuracion().getIdTipoDuracion() == 1) { //duracion minutos
                                calInicio1.add(Calendar.MINUTE, reservaView.getSmsServicios().getServicioDuracion());
                            } else if (reservaView.getSmsServicios().getSmsTipoDuracion().getIdTipoDuracion() == 2) {//duracion horas
                                calInicio1.add(Calendar.HOUR, reservaView.getSmsServicios().getServicioDuracion());
                            }

                            reservaView.setReservacionHoraLlegada(formatTime.parse(formatTime.format(calInicio1.getTime())));

                            calFechaEntrega.setTime(reservaView.getReservacionFechaLlegada());

                            if ((reservaView.getReservacionHoraInicio().equals(umbral) || reservaView.getReservacionHoraInicio().after(umbral)) && (reservaView.getReservacionHoraLlegada().after(mediaNoche) || reservaView.getReservacionHoraLlegada().equals(mediaNoche))) {
                                calFechaEntrega.add(Calendar.DATE, 1);
                                reservaView.setReservacionFechaLlegada(formatDate.parse(formatDate.format(calFechaEntrega.getTime())));
                            }

                        } catch (ParseException pe) {
                            pe.getMessage();
                        }

                        fechaInicio = formatDate.format(fechaActual);
                        fechaEntrega = formatDate.format(reservaView.getReservacionFechaLlegada());

                        horaInicio = formatTime.format(reservaView.getReservacionHoraInicio());
                        horaEntrega = formatTime.format(reservaView.getReservacionHoraLlegada());
                        break;
                    case 2://traslado
                        reservaView.setReservacionFechaInicio(fechaActual);
                        reservaView.setReservacionFechaLlegada(fechaActual);

                        try {
                            reservaView.setReservacionHoraInicio(formatTime.parse(horaInicio + ":" + minutosInicio));
                            mediaNoche = formatTime.parse("00:00:00");
                            umbral = formatTime.parse("23:30:00");

                            Calendar calInicio1 = Calendar.getInstance();
                            calInicio1.setTime(reservaView.getReservacionHoraInicio());

                            Calendar calFechaEntrega = Calendar.getInstance();

                            if (reservaView.getSmsServicios().getSmsTipoDuracion().getIdTipoDuracion() == 1) {//duracion minutos
                                calInicio1.add(Calendar.MINUTE, reservaView.getSmsServicios().getServicioDuracion());
                            } else if (reservaView.getSmsServicios().getSmsTipoDuracion().getIdTipoDuracion() == 2) {//duracion horas
                                calInicio1.add(Calendar.HOUR, reservaView.getSmsServicios().getServicioDuracion());
                            }

                            reservaView.setReservacionHoraLlegada(formatTime.parse(formatTime.format(calInicio1.getTime())));
                            calFechaEntrega.setTime(reservaView.getReservacionFechaLlegada());

                            if ((reservaView.getReservacionHoraInicio().equals(umbral) || reservaView.getReservacionHoraInicio().after(umbral)) && (reservaView.getReservacionHoraLlegada().after(mediaNoche) || reservaView.getReservacionHoraLlegada().equals(mediaNoche))) {
                                calFechaEntrega.add(Calendar.DATE, 1);
                                reservaView.setReservacionFechaLlegada(formatDate.parse(formatDate.format(calFechaEntrega.getTime())));
                            }

                        } catch (ParseException pe) {
                            pe.getMessage();
                        }

                        fechaInicio = formatDate.format(fechaActual);
                        fechaEntrega = formatDate.format(reservaView.getReservacionFechaLlegada());

                        horaInicio = formatTime.format(reservaView.getReservacionHoraInicio());
                        horaEntrega = formatTime.format(reservaView.getReservacionHoraLlegada());
                        break;
                    case 3://renta
                        try {
                            reservaView.setReservacionHoraInicio(formatTime.parse(horaInicio + ":" + minutosInicio));
                            reservaView.setReservacionHoraLlegada(formatTime.parse(horaEntrega + ":" + minutosEntrega));
                        } catch (ParseException pe) {
                            pe.getMessage();
                        }

                        fechaInicio = formatDate.format(reservaView.getReservacionFechaInicio());
                        fechaEntrega = formatDate.format(reservaView.getReservacionFechaLlegada());
                        horaInicio = formatTime.format(reservaView.getReservacionHoraInicio());
                        horaEntrega = formatTime.format(reservaView.getReservacionHoraLlegada());
                        break;

                }

                if (resDao.mostrarReservaciones().isEmpty()) {
                    vehiculosListView = vehiculoDao.mostrarVehiculo();
                } else {
                    vehiculosListView = vehiculoController.consultarVehiculosDisponible(reservaView, mercadoSeleccionado);
                }
                newtab = "Vehiculo";
            }
            return newtab;
        }
    }

    public void seleccionar() {
        reservaView = new SmsReservacion();
    }

    public void seleccionarVehiculo() {
        SelecVeh = true;
    }

    public void seleccionarConductor() {
        SelecCon = true;
    }

    public void filtrar() {
        if (categoriaView != null && categoriaView.getCategoriaNombre().isEmpty()) {
            if (resDao.mostrarReservaciones().isEmpty()) {
                vehiculosListView = new ArrayList<>();
                vehiculosListView = vehiculoDao.consultarVehiculosCiudad(reservaView.getSmsCiudadByIdCiudadInicio());
            } else {
                vehiculosListView = new ArrayList<>();
                vehiculosListView = vehiculoController.consultarVehiculosDisponible(reservaView, mercadoSeleccionado);
            }
        } else {
            if (resDao.mostrarReservaciones().isEmpty()) {
                vehiculosListView = new ArrayList<>();
                vehiculosListView = vehiculoDao.filtrarVehiculosCiudad(reservaView.getSmsCiudadByIdCiudadInicio(), categoriaView.getCategoriaNombre());
            } else {
                vehiculosListView = new ArrayList<>();
                vehiculosListView = vehiculoController.filtrarVehiculosDisponibles(reservaView, categoriaView, mercadoSeleccionado);
            }
        }
    }

    public void filtrarVehiculoSegunPlaca() {
        if (buscar == null) {
            if (resDao.mostrarReservaciones().isEmpty()) {
                vehiculosListView = new ArrayList<>();
                vehiculosListView = vehiculoDao.consultarVehiculosCiudad(reservaView.getSmsCiudadByIdCiudadInicio());
            } else {
                vehiculosListView = new ArrayList<>();
                vehiculosListView = vehiculoController.consultarVehiculosDisponible(reservaView, mercadoSeleccionado);
            }
        } else {
            vehiculosListView = vehiculoController.buscarVehiculoSegunPlaca(reservaView, mercadoSeleccionado, buscar);
        }
    }

    // CONTROLADOR PARA SACAR DATOS DE RESERVACION 
    public List<SmsReservacion> consultarReservacionesSegunUsuario() { //carga la agendas de las reservaciones hechan en el sistema segun el tipo de usuario conectado

        List<SmsReservacion> listaReservaciones = new ArrayList<>();
        if (mercadoReservacion.equalsIgnoreCase("todas")) {
            if (estadoReservacion == 0) {
                switch (sesion.getSmsRol().getRolNombre()) {

                    case "Administrador Principal":
                        listaReservaciones = resDao.mostrarReservaciones();
                        break;
                    case "Administrador Secundario":
                        listaReservaciones = resDao.mostrarReservaciones();
                        break;
                    case "Cliente":
                        listaReservaciones = resDao.mostrarReservacionCliente(sesion);
                        break;
                    case "Conductor":
                        reservaView.setSmsEmpleado(empleadoDao.consultarEmpleado(sesion));//Consultamos la informacion de usuario correspondiente al conductor
                        listaReservaciones = resDao.mostrarReservacionConductores(reservaView.getSmsEmpleado());
                        break;
                }
            } else {
                switch (sesion.getSmsRol().getRolNombre()) {

                    case "Administrador Principal":
                        listaReservaciones = resDao.consultarReservacionesSegunEstado(estadoReservacion);
                        break;
                    case "Administrador Secundario":
                        listaReservaciones = resDao.consultarReservacionesSegunEstado(estadoReservacion);
                        break;
                    case "Cliente":
                        listaReservaciones = resDao.mostrarReservacionClienteSegunEstado(sesion, estadoReservacion);
                        break;
                    case "Conductor":
                        reservaView.setSmsEmpleado(empleadoDao.consultarEmpleado(sesion));//Consultamos la informacion de usuario correspondiente al conductor
                        listaReservaciones = resDao.mostrarReservacionConductoresSegunEstado(reservaView.getSmsEmpleado(), estadoReservacion);
                        break;
                }
            }
        } else {
            if (estadoReservacion == 0) {
                switch (sesion.getSmsRol().getRolNombre()) {

                    case "Administrador Principal":
                        listaReservaciones = resDao.mostrarReservacionesSegunMercado(mercadoReservacion);
                        break;
                    case "Administrador Secundario":
                        listaReservaciones = resDao.mostrarReservacionesSegunMercado(mercadoReservacion);
                        break;
                    case "Cliente":
                        listaReservaciones = resDao.mostrarReservacionClienteSegunMercado(sesion, mercadoReservacion);
                        break;
                    case "Conductor":
                        reservaView.setSmsEmpleado(empleadoDao.consultarEmpleado(sesion));//Consultamos la informacion de usuario correspondiente al conductor
                        listaReservaciones = resDao.mostrarReservacionConductoresSegunMercado(reservaView.getSmsEmpleado(), mercadoReservacion);
                        break;
                }
            } else {
                switch (sesion.getSmsRol().getRolNombre()) {

                    case "Administrador Principal":
                        listaReservaciones = resDao.consultarReservacionesSegunEstadoyMercado(estadoReservacion, mercadoReservacion);
                        break;
                    case "Administrador Secundario":
                        listaReservaciones = resDao.consultarReservacionesSegunEstadoyMercado(estadoReservacion, mercadoReservacion);
                        break;
                    case "Cliente":
                        listaReservaciones = resDao.mostrarReservacionClienteSegunEstadoyMercado(sesion, estadoReservacion, mercadoReservacion);
                        break;
                    case "Conductor":
                        reservaView.setSmsEmpleado(empleadoDao.consultarEmpleado(sesion));//Consultamos la informacion de usuario correspondiente al conductor
                        listaReservaciones = resDao.mostrarReservacionConductoresSegunEstadoyMercado(reservaView.getSmsEmpleado(), estadoReservacion, mercadoReservacion);
                        break;
                }
            }
        }
        return listaReservaciones;
    }

//CREACION DEL CALENDARIO PRIMEFACAES TIPO SCHEDULE ************************
    public void addEventoCalendario() {
        //instanciar objeto de tipo controlador para sacar el metodo que arroja 
        //los datos de tipo DATE

        Date fechaInicio = new Date();
        Date fechaLlegada = new Date();

        SimpleDateFormat formatDate;
        SimpleDateFormat formatTime;
        SimpleDateFormat formatCompleteDate;
        formatDate = new SimpleDateFormat("yyyy-MM-dd");
        formatTime = new SimpleDateFormat("HH:mm:ss");
        formatCompleteDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        eventoModelo = new DefaultScheduleModel();
        List<SmsReservacion> reservaciones = consultarReservacionesSegunUsuario();
        for (int i = 0; i < reservaciones.size(); i++) {

            String FechaInicio = formatDate.format(reservaciones.get(i).getReservacionFechaInicio());
            String FechaLlegada = formatDate.format(reservaciones.get(i).getReservacionFechaLlegada());
            String HInicio = formatTime.format(reservaciones.get(i).getReservacionHoraInicio());
            String HLlegada = formatTime.format(reservaciones.get(i).getReservacionHoraLlegada());

            try {
                fechaInicio = formatCompleteDate.parse(FechaInicio + " " + HInicio);
                fechaLlegada = formatCompleteDate.parse(FechaLlegada + " " + HLlegada);
            } catch (ParseException pe) {
                pe.getMessage();
            }

            evento = new DefaultScheduleEvent("" + reservaciones.get(i).getIdReservacion(), fechaInicio, fechaLlegada);
            evento.setId("" + reservaciones.get(i).getIdReservacion());
            eventoModelo.addEvent(evento);
        }
    }

    public void onEventSelect(SelectEvent selectEvent) {
        evento = (ScheduleEvent) selectEvent.getObject();
    }

    public void actualizarListasReservaciones() {
        addEventoCalendario();
        reservacionesListView = consultarReservacionesSegunUsuario();
    }

    public String irVistaReserva() {
        String Ruta = "";
        modReservacionView.setIdReservacion(Integer.parseInt(evento.getTitle()));
        modReservacionView = resDao.consultarReservacionId(modReservacionView);
        SimpleDateFormat formatDate;
        SimpleDateFormat formatTime;
        formatDate = new SimpleDateFormat("yyyy-MM-dd");
        formatTime = new SimpleDateFormat("HH:mm:ss");

        fechaInicio = formatDate.format(modReservacionView.getReservacionFechaInicio());
        fechaEntrega = formatDate.format(modReservacionView.getReservacionFechaLlegada());
        horaInicio = formatTime.format(modReservacionView.getReservacionHoraInicio());
        horaEntrega = formatTime.format(modReservacionView.getReservacionHoraLlegada());
        switch (sesion.getSmsRol().getRolNombre()) {
            case "Administrador Principal":
                Ruta = "AdminPVistaReserva";
                break;

            case "Administrador Secundario":
                Ruta = "AdminSVistaReserva";
                break;

            case "Cliente":
                Ruta = "ClienteVistaReserva";
                break;

            case "Conductor":
                Ruta = "ConductorVistaReserva";
                break;
        }

        return Ruta;
    }

    public String irVistaReservaDesdeLista(SmsReservacion reservacion) {
        String Ruta = "";

        modReservacionView = resDao.consultarReservacionId(reservacion);
        SimpleDateFormat formatDate;
        SimpleDateFormat formatTime;
        formatDate = new SimpleDateFormat("yyyy-MM-dd");
        formatTime = new SimpleDateFormat("HH:mm:ss");

        fechaInicio = formatDate.format(modReservacionView.getReservacionFechaInicio());
        fechaEntrega = formatDate.format(modReservacionView.getReservacionFechaLlegada());
        horaInicio = formatTime.format(modReservacionView.getReservacionHoraInicio());
        horaEntrega = formatTime.format(modReservacionView.getReservacionHoraLlegada());
        switch (sesion.getSmsRol().getRolNombre()) {
            case "Administrador Principal":
                Ruta = "AdminPVistaReserva";
                break;

            case "Administrador Secundario":
                Ruta = "AdminSVistaReserva";
                break;

            case "Cliente":
                Ruta = "ClienteVistaReserva";
                break;

            case "Conductor":
                Ruta = "ConductorVistaReserva";
                break;
        }

        return Ruta;
    }

    public boolean validarCancelarReservacion(SmsReservacion reserva) {
        boolean valido = true;

        SimpleDateFormat formatDate;
        SimpleDateFormat formatTime;
        formatDate = new SimpleDateFormat("yyyy-MM-dd");
        formatTime = new SimpleDateFormat("HH:mm:ss");

        java.util.Date fechaActual = new Date();
        java.util.Date HoraActual = new Date();

        String FechaInicio = formatDate.format(fechaActual);
        String HActual = formatTime.format(fechaActual);

        try {
            HoraActual = formatTime.parse(HActual);
            fechaActual = formatDate.parse(FechaInicio);

        } catch (ParseException pe) {
            pe.getMessage();
        }

        Calendar fechaInicioAgenda = Calendar.getInstance();
        fechaInicioAgenda.setTime(reserva.getReservacionHoraInicio());
        fechaInicioAgenda.add(Calendar.HOUR, -1);
        fechaInicioAgenda.add(Calendar.MINUTE, -59);

        Date FInicioAgenda = fechaInicioAgenda.getTime();

        if (reserva.getReservacionFechaInicio().equals(fechaActual)) {

            if (FInicioAgenda.before(HoraActual)) {
                valido = false;
            }
        } else if (reserva.getReservacionFechaInicio().before(fechaActual)) {
            valido = false;
        }
        return valido;
    }

    //Metodos para la reservacion
    public int calcularCostoReservacion(SmsReservacion reserva) {
        //Instacia de variable propias del metodo
        int costo = 0;

        // Crear 2 instancias de Calendar
        Calendar calFechaInicio = Calendar.getInstance();
        Calendar calFechaLlegada = Calendar.getInstance();
        Calendar calHoraInicio = Calendar.getInstance();
        Calendar calHoraLlegada = Calendar.getInstance();

        //Variables necesarias para el calculo del costo de la reservacion       
        long milis1;
        long milis2;
        long diff;
        long diffHours;
        long diffDays;
        long diffWeek;
        long diffMonth;
        long diffHourDifferentDay;
        long diffMinutes;

        //asignamos a los objetos calendar la fecha de inicio con la hora de inicio y la fecha de llegada
        //con su hora de llegada
        calFechaInicio.setTime(reserva.getReservacionFechaInicio());
        calFechaLlegada.setTime(reserva.getReservacionFechaLlegada());
        calHoraInicio.setTime(reserva.getReservacionHoraInicio());
        calHoraLlegada.setTime(reserva.getReservacionHoraLlegada());

//id tipos duracion servicio
//        1 = minuto
//        2 = hora        
//        3 = dia
//        4 = semana
//        5 = mes       
        if (categoriaServicio == 1) { //Tiempo
            costoServicioView = costoDao.consultarCostoServicio(reservaView.getSmsServicios(), reservaView.getSmsVehiculo().getSmsCategoria());
            if (costoServicioView.getIdCostosServicio() == null) {
                costoServicioView.setCostoServicioPrecio(0);
            }
            milis1 = calFechaInicio.getTimeInMillis();
            milis2 = calFechaLlegada.getTimeInMillis();

            // calcular la diferencia en dias
            diff = milis2 - milis1;
            diffDays = diff / (24 * 60 * 60 * 1000);

            milis1 = calHoraInicio.getTimeInMillis();
            milis2 = calHoraLlegada.getTimeInMillis();

            diff = milis1 - milis2;
            diffHourDifferentDay = (diffDays * 24) - (diff / (60 * 60 * 1000));

            // calcular la diferencia en minutos
            diff = milis2 - milis1;
            diffMinutes = diff / (60 * 1000);

            diffDays = diffHourDifferentDay / 24;

            diffHours = diffHourDifferentDay - (diffDays * 24);

            // calcular la diferencia en horas
            if (reserva.getSmsServicios().getSmsTipoDuracion().getIdTipoDuracion() == 1) {
                costo = (((int) diffMinutes) / reservaView.getSmsServicios().getServicioDuracion()) * costoServicioView.getCostoServicioPrecio();
            } else if (reserva.getSmsServicios().getSmsTipoDuracion().getIdTipoDuracion() == 2) {
                costo = ((int) diffHours) / reservaView.getSmsServicios().getServicioDuracion() * costoServicioView.getCostoServicioPrecio();
            }

        } else if (categoriaServicio == 2) { // traslados
            SmsLugares lugarInicio = new SmsLugares();
            SmsLugares lugarDestino = new SmsLugares();

            ILugarDao lugarDao = new ImpLugarDao();
            lugarInicio.setLugarNombre(reservaView.getReservacionLugarLlegada());
            lugarInicio = lugarDao.consultarLugar(lugarInicio);
            lugarDestino.setLugarNombre(reservaView.getReservacionLugarDestino());
            lugarDestino = lugarDao.consultarLugar(lugarDestino);

            costoServicioView = costoDao.consultarCostoServicioTraslado(reservaView.getSmsServicios(), reservaView.getSmsVehiculo().getSmsCategoria(), lugarInicio, lugarDestino);
            if (costoServicioView.getIdCostosServicio() == null) {
                costoServicioView.setCostoServicioPrecio(0);
            }
            costo = costoServicioView.getCostoServicioPrecio();
        } else if (categoriaServicio == 3) { // Renta
            costoServicioView = costoDao.consultarCostoServicio(reservaView.getSmsServicios(), reservaView.getSmsVehiculo().getSmsCategoria());
            if (costoServicioView.getIdCostosServicio() == null) {
                costoServicioView.setCostoServicioPrecio(0);
            }
            if (reserva.getSmsServicios().getSmsTipoDuracion().getIdTipoDuracion() == 3) {

                milis1 = calFechaInicio.getTimeInMillis();
                milis2 = calFechaLlegada.getTimeInMillis();

                // calcular la diferencia en dias
                diff = milis2 - milis1;
                diffDays = diff / (24 * 60 * 60 * 1000);

                milis1 = calHoraInicio.getTimeInMillis();
                milis2 = calHoraLlegada.getTimeInMillis();

                diff = milis1 - milis2;
                diffHourDifferentDay = (diffDays * 24) - (diff / (60 * 60 * 1000));

                diffDays = diffHourDifferentDay / 24;

                // calcular la diferencia en horas
                diffHours = diffHourDifferentDay - (diffDays * 24);

                costo = (((int) diffDays) / reservaView.getSmsServicios().getServicioDuracion()) * costoServicioView.getCostoServicioPrecio();

                if ((int) diffHours > 4) {
                    //Obtenemos el costo de la hora
                    costo = costo + (costoServicioView.getCostoServicioPrecio() / 2);
                } else if ((int) diffHours > 12) {
                    costo = costo + (costoServicioView.getCostoServicioPrecio());
                }
            } else if (reserva.getSmsServicios().getSmsTipoDuracion().getIdTipoDuracion() == 4) {

                // conseguir la representacion de la fecha en milisegundos
                milis1 = calFechaInicio.getTimeInMillis();
                milis2 = calFechaLlegada.getTimeInMillis();

                // calcular la diferencia en dias
                diff = milis2 - milis1;
                diffDays = (diff / (24 * 60 * 60 * 1000));
                diffWeek = diffDays / 7;

                costo = ((int) diffWeek) * costoServicioView.getCostoServicioPrecio();

                diffDays = diffDays - (diffWeek * 7);

                milis1 = calHoraInicio.getTimeInMillis();
                milis2 = calHoraLlegada.getTimeInMillis();

                diff = milis1 - milis2;
                diffHourDifferentDay = (diffDays * 24) - (diff / (60 * 60 * 1000));

                diffDays = diffHourDifferentDay / 24;

                //Calculamos costo del dia
                if ((int) diffDays > 2) {
                    costo = costo + ((costoServicioView.getCostoServicioPrecio()) / 2);
                } else if ((int) diffDays > 4) {
                    costo = costo + ((costoServicioView.getCostoServicioPrecio()));
                }

            } else if (reserva.getSmsServicios().getSmsTipoDuracion().getIdTipoDuracion() == 5) {

                // conseguir la representacion de la fecha en milisegundos
                int diaInicio = reserva.getReservacionFechaInicio().getDay();
                int diaEntrega = reserva.getReservacionFechaLlegada().getDay();

                // calcular la diferencia en dias
                diffDays = diaEntrega - diaInicio;

                int startMes = (calFechaInicio.get(Calendar.YEAR) * 12) + calFechaInicio.get(Calendar.MONTH);
                int endMes = (calFechaLlegada.get(Calendar.YEAR) * 12) + calFechaLlegada.get(Calendar.MONTH);

                milis1 = calHoraInicio.getTimeInMillis();
                milis2 = calHoraLlegada.getTimeInMillis();

                diff = milis1 - milis2;
                diffHourDifferentDay = (diffDays * 24) - (diff / (60 * 60 * 1000));

                diffDays = diffHourDifferentDay / 24;

                //Diferencia en meses entre las dos fechas
                diffMonth = endMes - startMes;
                costo = (((int) diffMonth) / reservaView.getSmsServicios().getServicioDuracion()) * costoServicioView.getCostoServicioPrecio();

                if ((int) diffDays < 7) {
                    costo = costo + (costoServicioView.getCostoServicioPrecio() / 4);
                } else if ((int) diffDays > 7 && (int) diffDays < 15) {
                    costo = costo + (costoServicioView.getCostoServicioPrecio() / 3);
                } else if ((int) diffDays > 15 && (int) diffDays < 21) {
                    costo = costo + (costoServicioView.getCostoServicioPrecio() / 2);
                } else if ((int) diffDays > 29) {
                    costo = costo + (costoServicioView.getCostoServicioPrecio());
                }
            }
        }

        //Verificacion de incrementos
        IParametrosReservacionDao parametroDao = new ImpParametrosReservacionDao();
        SmsParametrosReservacion parametro = parametroDao.consultarParametroSegunMercado(mercadoSeleccionado);
        double valor = (double) costo * (((double) parametro.getParametroValorIncremento()) / 100);
        costo = costo + (int) valor;
        return costo;
    }

    public void consultarCategoria(SmsServicios servicio) { //Consulta la categoria del servicio desde la vista de la reservacion
        if (!servicio.getServicioNombre().isEmpty()) {
            SmsCategoriasServicio catServicio = servicioDao.ConsultarServicio(servicio).getSmsCategoriasServicio();
            if (catServicio.getCatNombre().equalsIgnoreCase("Renta")) {
                categoriaServicio = 3;
            } else if (catServicio.getCatNombre().equalsIgnoreCase("Tiempo")) {
                categoriaServicio = 1;
            } else if (catServicio.getCatNombre().equalsIgnoreCase("Traslado")) {
                categoriaServicio = 2;
            }
        } else {
            categoriaServicio = 0;
        }
    }

    public String iniciarProcesoReservacion() {
        reservaView = new SmsReservacion();
        modReservacionView = new SmsReservacion();
        estadoView = new SmsEstado();
        costoServicioView = new SmsCostosservicios();
        empleadoView = new SmsEmpleado();
        categoriaServicio = 0;
        SelecVeh = false;
        SelecCon = false;
        siguiente = true;
        atras = false;
        String ruta = "";
        if (sesion.getSmsRol().getRolNombre().equalsIgnoreCase("Administrador Principal")) {
            ruta = "AdminPReservaciones";
        } else if (sesion.getSmsRol().getRolNombre().equalsIgnoreCase("Administrador Secundario")) {
            ruta = "AdminSReservaciones";
        } else if (sesion.getSmsRol().getRolNombre().equalsIgnoreCase("Cliente")) {
            ruta = "ClienteReservaciones";
        }

        return ruta;

    }

    public void consultarReservacionesCliente(SmsUsuario cliente) {
        reservacionesCliente = new ArrayList<>();
        reservacionesCliente = resDao.mostrarReservacionCliente(cliente);
    }
}
