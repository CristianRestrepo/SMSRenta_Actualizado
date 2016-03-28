package Modelo;
// Generated 02-mar-2016 12:47:17 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * SmsCategoria generated by hbm2java
 */
public class SmsCategoria  implements java.io.Serializable {


     private Integer idCategoria;
     private String categoriaNombre;
     private String categoriaDescripcion;
     private Set<SmsCostosservicios> smsCostosservicioses = new HashSet<>(0);
     private Set<SmsVehiculo> smsVehiculos = new HashSet<>(0);
     private Set<SmsMercado> smsMercados = new HashSet<>(0);

    public SmsCategoria() {
        categoriaNombre = "";
    }

	
    public SmsCategoria(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }
    public SmsCategoria(String categoriaNombre, String categoriaDescripcion, Set<SmsCostosservicios> smsCostosservicioses, Set<SmsVehiculo> smsVehiculos, Set<SmsMercado> smsMercados) {
       this.categoriaNombre = categoriaNombre;
       this.categoriaDescripcion = categoriaDescripcion;
       this.smsCostosservicioses = smsCostosservicioses;
       this.smsVehiculos = smsVehiculos;
       this.smsMercados = smsMercados;
    }
   
    public Integer getIdCategoria() {
        return this.idCategoria;
    }
    
    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }
    public String getCategoriaNombre() {
        return this.categoriaNombre;
    }
    
    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }
    public String getCategoriaDescripcion() {
        return this.categoriaDescripcion;
    }
    
    public void setCategoriaDescripcion(String categoriaDescripcion) {
        this.categoriaDescripcion = categoriaDescripcion;
    }
    public Set<SmsCostosservicios> getSmsCostosservicioses() {
        return this.smsCostosservicioses;
    }
    
    public void setSmsCostosservicioses(Set<SmsCostosservicios> smsCostosservicioses) {
        this.smsCostosservicioses = smsCostosservicioses;
    }
    public Set<SmsVehiculo> getSmsVehiculos() {
        return this.smsVehiculos;
    }
    
    public void setSmsVehiculos(Set<SmsVehiculo> smsVehiculos) {
        this.smsVehiculos = smsVehiculos;
    }
    public Set<SmsMercado> getSmsMercados() {
        return this.smsMercados;
    }
    
    public void setSmsMercados(Set<SmsMercado> smsMercados) {
        this.smsMercados = smsMercados;
    }




}


