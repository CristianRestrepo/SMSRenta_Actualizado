package Modelo;
// Generated 23-feb-2016 11:25:04 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * SmsPermisos generated by hbm2java
 */
public class SmsPermisos  implements java.io.Serializable {


     private Integer idPermisos;
     private String permisosNombre;
     private String permisosDescripcion;
     private Set<SmsRol> smsRols = new HashSet<SmsRol>(0);

    public SmsPermisos() {
    }

    public SmsPermisos(String permisosNombre, String permisosDescripcion, Set<SmsRol> smsRols) {
       this.permisosNombre = permisosNombre;
       this.permisosDescripcion = permisosDescripcion;
       this.smsRols = smsRols;
    }
   
    public Integer getIdPermisos() {
        return this.idPermisos;
    }
    
    public void setIdPermisos(Integer idPermisos) {
        this.idPermisos = idPermisos;
    }
    public String getPermisosNombre() {
        return this.permisosNombre;
    }
    
    public void setPermisosNombre(String permisosNombre) {
        this.permisosNombre = permisosNombre;
    }
    public String getPermisosDescripcion() {
        return this.permisosDescripcion;
    }
    
    public void setPermisosDescripcion(String permisosDescripcion) {
        this.permisosDescripcion = permisosDescripcion;
    }
    public Set<SmsRol> getSmsRols() {
        return this.smsRols;
    }
    
    public void setSmsRols(Set<SmsRol> smsRols) {
        this.smsRols = smsRols;
    }




}


