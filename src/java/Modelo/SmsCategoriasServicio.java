package Modelo;
// Generated 23-feb-2016 11:25:04 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * SmsCategoriasServicio generated by hbm2java
 */
public class SmsCategoriasServicio  implements java.io.Serializable {


     private Integer idCategoriaServicio;
     private String catNombre;
     private String catDescripcion;
     private Set<SmsServicios> smsServicioses = new HashSet<SmsServicios>(0);
     private Set<SmsEmpleado> smsEmpleados = new HashSet<SmsEmpleado>(0);
     private Set<SmsVehiculo> smsVehiculos = new HashSet<SmsVehiculo>(0);
     private Set<SmsReservacion> smsReservacions = new HashSet<SmsReservacion>(0);

    public SmsCategoriasServicio() {
    }

	
    public SmsCategoriasServicio(String catNombre) {
        this.catNombre = catNombre;
    }
    public SmsCategoriasServicio(String catNombre, String catDescripcion, Set<SmsServicios> smsServicioses, Set<SmsEmpleado> smsEmpleados, Set<SmsVehiculo> smsVehiculos, Set<SmsReservacion> smsReservacions) {
       this.catNombre = catNombre;
       this.catDescripcion = catDescripcion;
       this.smsServicioses = smsServicioses;
       this.smsEmpleados = smsEmpleados;
       this.smsVehiculos = smsVehiculos;
       this.smsReservacions = smsReservacions;
    }
   
    public Integer getIdCategoriaServicio() {
        return this.idCategoriaServicio;
    }
    
    public void setIdCategoriaServicio(Integer idCategoriaServicio) {
        this.idCategoriaServicio = idCategoriaServicio;
    }
    public String getCatNombre() {
        return this.catNombre;
    }
    
    public void setCatNombre(String catNombre) {
        this.catNombre = catNombre;
    }
    public String getCatDescripcion() {
        return this.catDescripcion;
    }
    
    public void setCatDescripcion(String catDescripcion) {
        this.catDescripcion = catDescripcion;
    }
    public Set<SmsServicios> getSmsServicioses() {
        return this.smsServicioses;
    }
    
    public void setSmsServicioses(Set<SmsServicios> smsServicioses) {
        this.smsServicioses = smsServicioses;
    }
    public Set<SmsEmpleado> getSmsEmpleados() {
        return this.smsEmpleados;
    }
    
    public void setSmsEmpleados(Set<SmsEmpleado> smsEmpleados) {
        this.smsEmpleados = smsEmpleados;
    }
    public Set<SmsVehiculo> getSmsVehiculos() {
        return this.smsVehiculos;
    }
    
    public void setSmsVehiculos(Set<SmsVehiculo> smsVehiculos) {
        this.smsVehiculos = smsVehiculos;
    }
    public Set<SmsReservacion> getSmsReservacions() {
        return this.smsReservacions;
    }
    
    public void setSmsReservacions(Set<SmsReservacion> smsReservacions) {
        this.smsReservacions = smsReservacions;
    }




}


