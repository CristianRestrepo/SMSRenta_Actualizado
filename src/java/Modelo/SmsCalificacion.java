package Modelo;
// Generated 02-mar-2016 12:47:17 by Hibernate Tools 4.3.1



/**
 * SmsCalificacion generated by hbm2java
 */
public class SmsCalificacion  implements java.io.Serializable {


     private Integer idCalificacion;
     private SmsReservacion smsReservacion;
     private Integer calificacionPuntualidad;
     private Integer calificacionTratoConductor;
     private Integer calificacionEstadoVehiculo;
     private Integer calificacionCalidadServicio;

    public SmsCalificacion() {
    }

	
    public SmsCalificacion(SmsReservacion smsReservacion) {
        this.smsReservacion = smsReservacion;
    }
    public SmsCalificacion(SmsReservacion smsReservacion, Integer calificacionPuntualidad, Integer calificacionTratoConductor, Integer calificacionEstadoVehiculo, Integer calificacionCalidadServicio) {
       this.smsReservacion = smsReservacion;
       this.calificacionPuntualidad = calificacionPuntualidad;
       this.calificacionTratoConductor = calificacionTratoConductor;
       this.calificacionEstadoVehiculo = calificacionEstadoVehiculo;
       this.calificacionCalidadServicio = calificacionCalidadServicio;
    }
   
    public Integer getIdCalificacion() {
        return this.idCalificacion;
    }
    
    public void setIdCalificacion(Integer idCalificacion) {
        this.idCalificacion = idCalificacion;
    }
    public SmsReservacion getSmsReservacion() {
        return this.smsReservacion;
    }
    
    public void setSmsReservacion(SmsReservacion smsReservacion) {
        this.smsReservacion = smsReservacion;
    }
    public Integer getCalificacionPuntualidad() {
        return this.calificacionPuntualidad;
    }
    
    public void setCalificacionPuntualidad(Integer calificacionPuntualidad) {
        this.calificacionPuntualidad = calificacionPuntualidad;
    }
    public Integer getCalificacionTratoConductor() {
        return this.calificacionTratoConductor;
    }
    
    public void setCalificacionTratoConductor(Integer calificacionTratoConductor) {
        this.calificacionTratoConductor = calificacionTratoConductor;
    }
    public Integer getCalificacionEstadoVehiculo() {
        return this.calificacionEstadoVehiculo;
    }
    
    public void setCalificacionEstadoVehiculo(Integer calificacionEstadoVehiculo) {
        this.calificacionEstadoVehiculo = calificacionEstadoVehiculo;
    }
    public Integer getCalificacionCalidadServicio() {
        return this.calificacionCalidadServicio;
    }
    
    public void setCalificacionCalidadServicio(Integer calificacionCalidadServicio) {
        this.calificacionCalidadServicio = calificacionCalidadServicio;
    }




}


