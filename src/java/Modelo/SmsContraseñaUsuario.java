/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Desarrollo_Planit
 */
public class SmsContraseñaUsuario {

    private Integer idContraseñaUsuario;
    private String password;
    private SmsUsuario smsUsuario;

    public SmsContraseñaUsuario() {
    }

    public SmsContraseñaUsuario(SmsUsuario smsUsuario) {
        this.smsUsuario = smsUsuario;
    }

    public SmsContraseñaUsuario(Integer idContraseñaUsuario, String password, SmsUsuario idUsuario) {
        this.idContraseñaUsuario = idContraseñaUsuario;
        this.password = password;
        this.smsUsuario = idUsuario;
    }

    public Integer getIdContraseñaUsuario() {
        return idContraseñaUsuario;
    }

    public void setIdContraseñaUsuario(Integer idContraseñaUsuario) {
        this.idContraseñaUsuario = idContraseñaUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SmsUsuario getSmsUsuario() {
        return smsUsuario;
    }

    public void setSmsUsuario(SmsUsuario smsUsuario) {
        this.smsUsuario = smsUsuario;
    }
}
