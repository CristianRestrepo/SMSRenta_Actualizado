/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.validadores;

import com.planit.smsrenta.dao.IUsuarioDao;
import com.planit.smsrenta.dao.ImpUsuarioDao;
import com.planit.smsrenta.modelos.SmsUsuario;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Desarrollo_Planit
 */
@FacesValidator("emailExistenteValidator")
public class EmailExistenteValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        SmsUsuario usuario = new SmsUsuario();
        usuario.setUsuarioEmail((String) value);
        IUsuarioDao usuarioDao = new ImpUsuarioDao();

        if (!usuarioDao.consultarExistenciaUsuario(usuario)) {
            FacesMessage fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Correo inexistente en el sistema", "Por favor, ingrese un email valido");
            throw new ValidatorException(fmsg);
        }
    }

}
