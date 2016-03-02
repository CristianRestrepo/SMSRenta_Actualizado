package Modelo;
// Generated 02-mar-2016 12:47:17 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * SmsVehiculo generated by hbm2java
 */
public class SmsVehiculo  implements java.io.Serializable {


     private Integer idVehiculo;
     private SmsCategoria smsCategoria;
     private SmsCiudad smsCiudad;
     private SmsProveedor smsProveedor;
     private SmsReferencia smsReferencia;
     private String vehPlaca;
     private String vehModelo;
     private String vehColor;
     private Integer vehNumPersonas;
     private Integer vehNumMalGrande;
     private Integer vehNumMalPequeña;
     private String vehFotoNombre;
     private String vehFotoRuta;
     private String vehFoto2Nombre;
     private String vehFoto2Ruta;
     private Set<SmsCategoriasServicio> smsCategoriasServicios = new HashSet<>(0);
     private Set<SmsReservacion> smsReservacions = new HashSet<>(0);
     private Set<SmsEstadovehiculo> smsEstadovehiculos = new HashSet<>(0);

    public SmsVehiculo() {
    }

	
    public SmsVehiculo(SmsCategoria smsCategoria, SmsCiudad smsCiudad, SmsProveedor smsProveedor, SmsReferencia smsReferencia, String vehPlaca, String vehModelo) {
        this.smsCategoria = smsCategoria;
        this.smsCiudad = smsCiudad;
        this.smsProveedor = smsProveedor;
        this.smsReferencia = smsReferencia;
        this.vehPlaca = vehPlaca;
        this.vehModelo = vehModelo;
    }
    public SmsVehiculo(SmsCategoria smsCategoria, SmsCiudad smsCiudad, SmsProveedor smsProveedor, SmsReferencia smsReferencia, String vehPlaca, String vehModelo, String vehColor, Integer vehNumPersonas, Integer vehNumMalGrande, Integer vehNumMalPequeña, String vehFotoNombre, String vehFotoRuta, String vehFoto2Nombre, String vehFoto2Ruta, Set<SmsCategoriasServicio> smsCategoriasServicios, Set<SmsReservacion> smsReservacions, Set<SmsEstadovehiculo> smsEstadovehiculos) {
       this.smsCategoria = smsCategoria;
       this.smsCiudad = smsCiudad;
       this.smsProveedor = smsProveedor;
       this.smsReferencia = smsReferencia;
       this.vehPlaca = vehPlaca;
       this.vehModelo = vehModelo;
       this.vehColor = vehColor;
       this.vehNumPersonas = vehNumPersonas;
       this.vehNumMalGrande = vehNumMalGrande;
       this.vehNumMalPequeña = vehNumMalPequeña;
       this.vehFotoNombre = vehFotoNombre;
       this.vehFotoRuta = vehFotoRuta;
       this.vehFoto2Nombre = vehFoto2Nombre;
       this.vehFoto2Ruta = vehFoto2Ruta;
       this.smsCategoriasServicios = smsCategoriasServicios;
       this.smsReservacions = smsReservacions;
       this.smsEstadovehiculos = smsEstadovehiculos;
    }
   
    public Integer getIdVehiculo() {
        return this.idVehiculo;
    }
    
    public void setIdVehiculo(Integer idVehiculo) {
        this.idVehiculo = idVehiculo;
    }
    public SmsCategoria getSmsCategoria() {
        return this.smsCategoria;
    }
    
    public void setSmsCategoria(SmsCategoria smsCategoria) {
        this.smsCategoria = smsCategoria;
    }
    public SmsCiudad getSmsCiudad() {
        return this.smsCiudad;
    }
    
    public void setSmsCiudad(SmsCiudad smsCiudad) {
        this.smsCiudad = smsCiudad;
    }
    public SmsProveedor getSmsProveedor() {
        return this.smsProveedor;
    }
    
    public void setSmsProveedor(SmsProveedor smsProveedor) {
        this.smsProveedor = smsProveedor;
    }
    public SmsReferencia getSmsReferencia() {
        return this.smsReferencia;
    }
    
    public void setSmsReferencia(SmsReferencia smsReferencia) {
        this.smsReferencia = smsReferencia;
    }
    public String getVehPlaca() {
        return this.vehPlaca;
    }
    
    public void setVehPlaca(String vehPlaca) {
        this.vehPlaca = vehPlaca;
    }
    public String getVehModelo() {
        return this.vehModelo;
    }
    
    public void setVehModelo(String vehModelo) {
        this.vehModelo = vehModelo;
    }
    public String getVehColor() {
        return this.vehColor;
    }
    
    public void setVehColor(String vehColor) {
        this.vehColor = vehColor;
    }
    public Integer getVehNumPersonas() {
        return this.vehNumPersonas;
    }
    
    public void setVehNumPersonas(Integer vehNumPersonas) {
        this.vehNumPersonas = vehNumPersonas;
    }
    public Integer getVehNumMalGrande() {
        return this.vehNumMalGrande;
    }
    
    public void setVehNumMalGrande(Integer vehNumMalGrande) {
        this.vehNumMalGrande = vehNumMalGrande;
    }
    public Integer getVehNumMalPequeña() {
        return this.vehNumMalPequeña;
    }
    
    public void setVehNumMalPequeña(Integer vehNumMalPequeña) {
        this.vehNumMalPequeña = vehNumMalPequeña;
    }
    public String getVehFotoNombre() {
        return this.vehFotoNombre;
    }
    
    public void setVehFotoNombre(String vehFotoNombre) {
        this.vehFotoNombre = vehFotoNombre;
    }
    public String getVehFotoRuta() {
        return this.vehFotoRuta;
    }
    
    public void setVehFotoRuta(String vehFotoRuta) {
        this.vehFotoRuta = vehFotoRuta;
    }
    public String getVehFoto2Nombre() {
        return this.vehFoto2Nombre;
    }
    
    public void setVehFoto2Nombre(String vehFoto2Nombre) {
        this.vehFoto2Nombre = vehFoto2Nombre;
    }
    public String getVehFoto2Ruta() {
        return this.vehFoto2Ruta;
    }
    
    public void setVehFoto2Ruta(String vehFoto2Ruta) {
        this.vehFoto2Ruta = vehFoto2Ruta;
    }
    public Set<SmsCategoriasServicio> getSmsCategoriasServicios() {
        return this.smsCategoriasServicios;
    }
    
    public void setSmsCategoriasServicios(Set<SmsCategoriasServicio> smsCategoriasServicios) {
        this.smsCategoriasServicios = smsCategoriasServicios;
    }
    public Set<SmsReservacion> getSmsReservacions() {
        return this.smsReservacions;
    }
    
    public void setSmsReservacions(Set<SmsReservacion> smsReservacions) {
        this.smsReservacions = smsReservacions;
    }
    public Set<SmsEstadovehiculo> getSmsEstadovehiculos() {
        return this.smsEstadovehiculos;
    }
    
    public void setSmsEstadovehiculos(Set<SmsEstadovehiculo> smsEstadovehiculos) {
        this.smsEstadovehiculos = smsEstadovehiculos;
    }




}


