/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.controladores;

import com.planit.smsrenta.modelos.SmsUsuario;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Desarrollo_Planit
 */
public class SesionActivaBean {
    
    private SmsUsuario Usuario;
    private HttpServletRequest httpServletRequest;   
    private FacesContext faceContext;
    private FacesMessage facesMessage;

    public SmsUsuario getUsuario() {
        return Usuario;
    }

    public void setUsuario(SmsUsuario Usuario) {
        this.Usuario = Usuario;
    }    
        
    public SesionActivaBean() {
        Usuario = new SmsUsuario();
        faceContext=FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest)faceContext.getExternalContext().getRequest();
        if(httpServletRequest.getSession().getAttribute("Sesion")!=null)
        {
            Usuario=(SmsUsuario)httpServletRequest.getSession().getAttribute("Sesion");
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Acceso Correcto", "Bienvenid@: " + Usuario.getUsuarioNombre());
            faceContext.addMessage(null, facesMessage);
        }
    }
    
    public String cerrarSession()
    {
        Usuario = new SmsUsuario();
        faceContext=FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        httpServletRequest.getSession(false);
        httpServletRequest.getSession().invalidate();
        return "Login";
    }
    
}
