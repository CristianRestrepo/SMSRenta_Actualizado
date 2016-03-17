package Modelo;
// Generated 02-mar-2016 12:47:17 by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * SmsReservacion generated by hbm2java
 */
public class SmsReservacion  implements java.io.Serializable {


     private Integer idReservacion;
     private SmsCategoriasServicio smsCategoriasServicio;
     private SmsCiudad smsCiudadByIdCiudadDestino;
     private SmsCiudad smsCiudadByIdCiudadInicio;
     private SmsEmpleado smsEmpleado;
     private SmsEstado smsEstado;
     private SmsServicios smsServicios;
     private SmsUsuario smsUsuario;
     private SmsVehiculo smsVehiculo;
     private String reservacionLugarLlegada;
     private String reservacionLugarDestino;
     private String reservacionNotas;
     private Date reservacionFechaInicio;
     private Date reservacionFechaLlegada;
     private Date reservacionHoraInicio;
     private Date reservacionHoraLlegada;
     private Integer reservacionCosto;
     private Set<SmsCalificacion> smsCalificacions = new HashSet<>(0);

    public SmsReservacion() {
        this.smsCategoriasServicio = new SmsCategoriasServicio();
        this.smsCiudadByIdCiudadInicio = new SmsCiudad();
        this.smsCiudadByIdCiudadDestino = new SmsCiudad();
        this.smsEmpleado = new SmsEmpleado();
        this.smsEstado = new SmsEstado();
        this.smsVehiculo = new SmsVehiculo();
        this.smsUsuario = new SmsUsuario();
        this.smsServicios = new SmsServicios();
    }

	
    public SmsReservacion(SmsCategoriasServicio smsCategoriasServicio, SmsCiudad smsCiudadByIdCiudadInicio, SmsEstado smsEstado, SmsServicios smsServicios, SmsUsuario smsUsuario, SmsVehiculo smsVehiculo, String reservacionLugarLlegada, String reservacionLugarDestino, Date reservacionFechaInicio, Date reservacionHoraInicio) {
        this.smsCategoriasServicio = smsCategoriasServicio;
        this.smsCiudadByIdCiudadInicio = smsCiudadByIdCiudadInicio;
        this.smsEstado = smsEstado;
        this.smsServicios = smsServicios;
        this.smsUsuario = smsUsuario;
        this.smsVehiculo = smsVehiculo;
        this.reservacionLugarLlegada = reservacionLugarLlegada;
        this.reservacionLugarDestino = reservacionLugarDestino;
        this.reservacionFechaInicio = reservacionFechaInicio;
        this.reservacionHoraInicio = reservacionHoraInicio;
    }
    public SmsReservacion(SmsCategoriasServicio smsCategoriasServicio, SmsCiudad smsCiudadByIdCiudadDestino, SmsCiudad smsCiudadByIdCiudadInicio, SmsEmpleado smsEmpleado, SmsEstado smsEstado, SmsServicios smsServicios, SmsUsuario smsUsuario, SmsVehiculo smsVehiculo, String reservacionLugarLlegada, String reservacionLugarDestino, String reservacionNotas, Date reservacionFechaInicio, Date reservacionFechaLlegada, Date reservacionHoraInicio, Date reservacionHoraLlegada, Integer reservacionCosto, Set<SmsCalificacion> smsCalificacions) {
       this.smsCategoriasServicio = smsCategoriasServicio;
       this.smsCiudadByIdCiudadDestino = smsCiudadByIdCiudadDestino;
       this.smsCiudadByIdCiudadInicio = smsCiudadByIdCiudadInicio;
       this.smsEmpleado = smsEmpleado;
       this.smsEstado = smsEstado;
       this.smsServicios = smsServicios;
       this.smsUsuario = smsUsuario;
       this.smsVehiculo = smsVehiculo;
       this.reservacionLugarLlegada = reservacionLugarLlegada;
       this.reservacionLugarDestino = reservacionLugarDestino;
       this.reservacionNotas = reservacionNotas;
       this.reservacionFechaInicio = reservacionFechaInicio;
       this.reservacionFechaLlegada = reservacionFechaLlegada;
       this.reservacionHoraInicio = reservacionHoraInicio;
       this.reservacionHoraLlegada = reservacionHoraLlegada;
       this.reservacionCosto = reservacionCosto;
       this.smsCalificacions = smsCalificacions;
    }
   
    public Integer getIdReservacion() {
        return this.idReservacion;
    }
    
    public void setIdReservacion(Integer idReservacion) {
        this.idReservacion = idReservacion;
    }
    public SmsCategoriasServicio getSmsCategoriasServicio() {
        return this.smsCategoriasServicio;
    }
    
    public void setSmsCategoriasServicio(SmsCategoriasServicio smsCategoriasServicio) {
        this.smsCategoriasServicio = smsCategoriasServicio;
    }
    public SmsCiudad getSmsCiudadByIdCiudadDestino() {
        return this.smsCiudadByIdCiudadDestino;
    }
    
    public void setSmsCiudadByIdCiudadDestino(SmsCiudad smsCiudadByIdCiudadDestino) {
        this.smsCiudadByIdCiudadDestino = smsCiudadByIdCiudadDestino;
    }
    public SmsCiudad getSmsCiudadByIdCiudadInicio() {
        return this.smsCiudadByIdCiudadInicio;
    }
    
    public void setSmsCiudadByIdCiudadInicio(SmsCiudad smsCiudadByIdCiudadInicio) {
        this.smsCiudadByIdCiudadInicio = smsCiudadByIdCiudadInicio;
    }
    public SmsEmpleado getSmsEmpleado() {
        return this.smsEmpleado;
    }
    
    public void setSmsEmpleado(SmsEmpleado smsEmpleado) {
        this.smsEmpleado = smsEmpleado;
    }
    public SmsEstado getSmsEstado() {
        return this.smsEstado;
    }
    
    public void setSmsEstado(SmsEstado smsEstado) {
        this.smsEstado = smsEstado;
    }
    public SmsServicios getSmsServicios() {
        return this.smsServicios;
    }
    
    public void setSmsServicios(SmsServicios smsServicios) {
        this.smsServicios = smsServicios;
    }
    public SmsUsuario getSmsUsuario() {
        return this.smsUsuario;
    }
    
    public void setSmsUsuario(SmsUsuario smsUsuario) {
        this.smsUsuario = smsUsuario;
    }
    public SmsVehiculo getSmsVehiculo() {
        return this.smsVehiculo;
    }
    
    public void setSmsVehiculo(SmsVehiculo smsVehiculo) {
        this.smsVehiculo = smsVehiculo;
    }
    public String getReservacionLugarLlegada() {
        return this.reservacionLugarLlegada;
    }
    
    public void setReservacionLugarLlegada(String reservacionLugarLlegada) {
        this.reservacionLugarLlegada = reservacionLugarLlegada;
    }
    public String getReservacionLugarDestino() {
        return this.reservacionLugarDestino;
    }
    
    public void setReservacionLugarDestino(String reservacionLugarDestino) {
        this.reservacionLugarDestino = reservacionLugarDestino;
    }
    public String getReservacionNotas() {
        return this.reservacionNotas;
    }
    
    public void setReservacionNotas(String reservacionNotas) {
        this.reservacionNotas = reservacionNotas;
    }
    public Date getReservacionFechaInicio() {
        return this.reservacionFechaInicio;
    }
    
    public void setReservacionFechaInicio(Date reservacionFechaInicio) {
        this.reservacionFechaInicio = reservacionFechaInicio;
    }
    public Date getReservacionFechaLlegada() {
        return this.reservacionFechaLlegada;
    }
    
    public void setReservacionFechaLlegada(Date reservacionFechaLlegada) {
        this.reservacionFechaLlegada = reservacionFechaLlegada;
    }
    public Date getReservacionHoraInicio() {
        return this.reservacionHoraInicio;
    }
    
    public void setReservacionHoraInicio(Date reservacionHoraInicio) {
        this.reservacionHoraInicio = reservacionHoraInicio;
    }
    public Date getReservacionHoraLlegada() {
        return this.reservacionHoraLlegada;
    }
    
    public void setReservacionHoraLlegada(Date reservacionHoraLlegada) {
        this.reservacionHoraLlegada = reservacionHoraLlegada;
    }
    public Integer getReservacionCosto() {
        return this.reservacionCosto;
    }
    
    public void setReservacionCosto(Integer reservacionCosto) {
        this.reservacionCosto = reservacionCosto;
    }
    public Set<SmsCalificacion> getSmsCalificacions() {
        return this.smsCalificacions;
    }
    
    public void setSmsCalificacions(Set<SmsCalificacion> smsCalificacions) {
        this.smsCalificacions = smsCalificacions;
    }




}


