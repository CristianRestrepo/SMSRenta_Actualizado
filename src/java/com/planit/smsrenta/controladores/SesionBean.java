/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.controladores;

import com.planit.smsrenta.dao.IUsuarioDao;
import com.planit.smsrenta.dao.ImpUsuarioDao;
import com.planit.smsrenta.metodos.MD5;
import com.planit.smsrenta.modelos.SmsUsuario;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Desarrollo_Planit
 */
public class SesionBean implements Serializable {

    private SmsUsuario usuarioView;

    //Contexto y sesion
    private final HttpServletRequest httpServletRequest;
    private final FacesContext faceContext;
    private FacesMessage message;

    public SesionBean() {
        usuarioView = new SmsUsuario();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
    }

    public SmsUsuario getUsuarioView() {
        return usuarioView;
    }

    public void setUsuarioView(SmsUsuario usuarioView) {
        this.usuarioView = usuarioView;
    }

    //Metodos para iniciar Sesion
    public String iniciarSesion() {
        String ruta = "/login.xhtml";
        MD5 md = new MD5();
        SmsUsuario user;
        IUsuarioDao usuarioDao = new ImpUsuarioDao();

        if (!usuarioDao.consultarDatosSesionUsuario(usuarioView).isEmpty()) {//valida si el usuario existe en la BD        
            user = usuarioDao.consultarUsuario(usuarioView).get(0);
            usuarioView.setUsuarioPassword(md.getMD5(usuarioView.getUsuarioPassword()));
            if (user.getUsuarioEstadoUsuario() == 1) {//Evalua el estado de la cuenta de usuario, si esta activa o inactiva
                if (user.getUsuarioEmail().equalsIgnoreCase(usuarioView.getUsuarioEmail()) && user.getUsuarioPassword().equalsIgnoreCase(usuarioView.getUsuarioPassword())) {

                    httpServletRequest.getSession().setAttribute("Sesion", user);

                    switch (user.getSmsRol().getRolNombre()) {
                        case "Administrador Principal":
                            ruta = "AdminPPrincipal";
                            break;
                        case "Administrador Secundario":
                            ruta = "AdminSGeneral";
                            break;
                        case "Cliente":
                            ruta = "ClienteDash";
                            break;
                        case "Conductor":
                            ruta = "ConductorDash";
                            break;
                        case "Proveedor":
                            ruta = "ProveedorDash";
                            break;
                    }

                } else {
                    message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario o contraseña incorrecto", null);
                    usuarioView = new SmsUsuario();
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            } else if (user.getUsuarioEstadoUsuario() == 0) {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario inactivo, imposible iniciar sesion", null);
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario no existente", null);
            usuarioView = new SmsUsuario();
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        usuarioView = new SmsUsuario();

        return ruta;
    }
}
