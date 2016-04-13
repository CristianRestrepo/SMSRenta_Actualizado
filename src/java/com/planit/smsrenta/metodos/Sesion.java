/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.metodos;

import com.planit.smsrenta.modelos.SmsUsuario;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Desarrollo_Planit
 */
public class Sesion {

    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;

    public Sesion() {
    }

    public SmsUsuario obtenerSesion() {
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        SmsUsuario usuario;
        usuario = (SmsUsuario) httpServletRequest.getSession().getAttribute("Sesion");
        return usuario;
    }

}
