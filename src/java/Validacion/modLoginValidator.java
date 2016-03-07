/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validacion;

import DAO.IUsuarioDao;
import DAO.ImpUsuarioDao;
import Modelo.SmsUsuario;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author CristianCamilo
 */
@FacesValidator("modLoginValidator")
public class modLoginValidator implements Validator {

    String user;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        user = (String) value;
       

    }

}
