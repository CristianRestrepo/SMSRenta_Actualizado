package Modelo;
// Generated 23-feb-2016 11:25:04 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * SmsMunicipios generated by hbm2java
 */
public class SmsMunicipios  implements java.io.Serializable {


     private Integer idMunicipio;
     private SmsPais smsPais;
     private String municipioNombre;
     private Set<SmsCiudad> smsCiudads = new HashSet<SmsCiudad>(0);

    public SmsMunicipios() {
    }

	
    public SmsMunicipios(SmsPais smsPais, String municipioNombre) {
        this.smsPais = smsPais;
        this.municipioNombre = municipioNombre;
    }
    public SmsMunicipios(SmsPais smsPais, String municipioNombre, Set<SmsCiudad> smsCiudads) {
       this.smsPais = smsPais;
       this.municipioNombre = municipioNombre;
       this.smsCiudads = smsCiudads;
    }
   
    public Integer getIdMunicipio() {
        return this.idMunicipio;
    }
    
    public void setIdMunicipio(Integer idMunicipio) {
        this.idMunicipio = idMunicipio;
    }
    public SmsPais getSmsPais() {
        return this.smsPais;
    }
    
    public void setSmsPais(SmsPais smsPais) {
        this.smsPais = smsPais;
    }
    public String getMunicipioNombre() {
        return this.municipioNombre;
    }
    
    public void setMunicipioNombre(String municipioNombre) {
        this.municipioNombre = municipioNombre;
    }
    public Set<SmsCiudad> getSmsCiudads() {
        return this.smsCiudads;
    }
    
    public void setSmsCiudads(Set<SmsCiudad> smsCiudads) {
        this.smsCiudads = smsCiudads;
    }




}


