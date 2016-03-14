package Modelo;
// Generated 02-mar-2016 12:47:17 by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;

/**
 * SmsUsuario generated by hbm2java
 */
public class SmsUsuario implements java.io.Serializable {

    private Integer idUsuario;
    private SmsCiudad smsCiudad;
    private SmsRol smsRol;
    private SmsNacionalidad smsNacionalidad;
    private String usuarioNombre;
    private String usuarioCc;
    private String usuarioPasaporte;
    private String usuarioTelefono;
    private String usuarioEmail;
    private String usuarioLogin;
    private String usuarioPassword;
    private String usuarioRememberToken;
    private Integer usuarioEstadoUsuario;
    private String usuarioFotoNombre;
    private String usuarioFotoRuta;
    private Set<SmsEmpleado> smsEmpleados = new HashSet<>(0);
    private Set<SmsProveedor> smsProveedors = new HashSet<>(0);
    private Set<SmsReservacion> smsReservacions = new HashSet<>(0);

    public SmsUsuario() {
        this.smsCiudad = new SmsCiudad();
        this.smsNacionalidad = new SmsNacionalidad();
        this.smsRol = new SmsRol();
    }

    public SmsUsuario(SmsCiudad smsCiudad, SmsRol smsRol, SmsNacionalidad smsNacionalidad, String usuarioNombre, String usuarioEmail) {
        this.smsCiudad = smsCiudad;
        this.smsRol = smsRol;
        this.smsNacionalidad = smsNacionalidad;
        this.usuarioNombre = usuarioNombre;
        this.usuarioEmail = usuarioEmail;
    }

    public SmsUsuario(SmsCiudad smsCiudad, SmsRol smsRol, SmsNacionalidad smsNacionalidad, String usuarioNombre, String usuarioCc, String usuarioPasaporte, String usuarioTelefono, String usuarioEmail, String usuarioLogin, String usuarioPassword, String usuarioRememberToken, Integer usuarioEstadoUsuario, String usuarioFotoNombre, String usuarioFotoRuta, Set<SmsEmpleado> smsEmpleados, Set<SmsProveedor> smsProveedors, Set<SmsReservacion> smsReservacions) {
        this.smsCiudad = smsCiudad;
        this.smsRol = smsRol;
        this.smsNacionalidad = smsNacionalidad;
        this.usuarioNombre = usuarioNombre;
        this.usuarioCc = usuarioCc;
        this.usuarioPasaporte = usuarioPasaporte;
        this.usuarioTelefono = usuarioTelefono;
        this.usuarioEmail = usuarioEmail;
        this.usuarioLogin = usuarioLogin;
        this.usuarioPassword = usuarioPassword;
        this.usuarioRememberToken = usuarioRememberToken;
        this.usuarioEstadoUsuario = usuarioEstadoUsuario;
        this.usuarioFotoNombre = usuarioFotoNombre;
        this.usuarioFotoRuta = usuarioFotoRuta;
        this.smsEmpleados = smsEmpleados;
        this.smsProveedors = smsProveedors;
        this.smsReservacions = smsReservacions;
    }

    public Integer getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public SmsCiudad getSmsCiudad() {
        return this.smsCiudad;
    }

    public void setSmsCiudad(SmsCiudad smsCiudad) {
        this.smsCiudad = smsCiudad;
    }

    public SmsRol getSmsRol() {
        return this.smsRol;
    }

    public void setSmsRol(SmsRol smsRol) {
        this.smsRol = smsRol;
    }

    public String getUsuarioNombre() {
        return this.usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public String getUsuarioCc() {
        return this.usuarioCc;
    }

    public void setUsuarioCc(String usuarioCc) {
        this.usuarioCc = usuarioCc;
    }

    public String getUsuarioTelefono() {
        return this.usuarioTelefono;
    }

    public void setUsuarioTelefono(String usuarioTelefono) {
        this.usuarioTelefono = usuarioTelefono;
    }

    public String getUsuarioEmail() {
        return this.usuarioEmail;
    }

    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }

    public String getUsuarioLogin() {
        return this.usuarioLogin;
    }

    public void setUsuarioLogin(String usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public String getUsuarioPassword() {
        return this.usuarioPassword;
    }

    public void setUsuarioPassword(String usuarioPassword) {
        this.usuarioPassword = usuarioPassword;
    }

    public String getUsuarioRememberToken() {
        return this.usuarioRememberToken;
    }

    public void setUsuarioRememberToken(String usuarioRememberToken) {
        this.usuarioRememberToken = usuarioRememberToken;
    }

    public Integer getUsuarioEstadoUsuario() {
        return this.usuarioEstadoUsuario;
    }

    public void setUsuarioEstadoUsuario(Integer usuarioEstadoUsuario) {
        this.usuarioEstadoUsuario = usuarioEstadoUsuario;
    }

    public String getUsuarioFotoNombre() {
        return this.usuarioFotoNombre;
    }

    public void setUsuarioFotoNombre(String usuarioFotoNombre) {
        this.usuarioFotoNombre = usuarioFotoNombre;
    }

    public String getUsuarioFotoRuta() {
        return this.usuarioFotoRuta;
    }

    public void setUsuarioFotoRuta(String usuarioFotoRuta) {
        this.usuarioFotoRuta = usuarioFotoRuta;
    }

    public Set<SmsEmpleado> getSmsEmpleados() {
        return this.smsEmpleados;
    }

    public void setSmsEmpleados(Set<SmsEmpleado> smsEmpleados) {
        this.smsEmpleados = smsEmpleados;
    }

    public Set<SmsProveedor> getSmsProveedors() {
        return this.smsProveedors;
    }

    public void setSmsProveedors(Set<SmsProveedor> smsProveedors) {
        this.smsProveedors = smsProveedors;
    }

    public Set<SmsReservacion> getSmsReservacions() {
        return this.smsReservacions;
    }

    public void setSmsReservacions(Set<SmsReservacion> smsReservacions) {
        this.smsReservacions = smsReservacions;
    }

    public String getUsuarioPasaporte() {
        return usuarioPasaporte;
    }

    public void setUsuarioPasaporte(String usuarioPasaporte) {
        this.usuarioPasaporte = usuarioPasaporte;
    }

    public SmsNacionalidad getSmsNacionalidad() {
        return smsNacionalidad;
    }

    public void setSmsNacionalidad(SmsNacionalidad smsNacionalidad) {
        this.smsNacionalidad = smsNacionalidad;
    }

}
