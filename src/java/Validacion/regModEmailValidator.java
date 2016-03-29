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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
@FacesValidator("regModEmailValidator")
public class regModEmailValidator implements Validator {

    Pattern pattern = Pattern.compile("[-0-9a-zA-Z.+_]+@[-0-9a-zA-Z.+_]+\\.[a-zA-Z]{2,4}");
    String user;
    int operacion;

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        user = (String) value;
        operacion = (int) component.getAttributes().get("operacion");

        Matcher matcher = pattern.matcher(value.toString());
        if (!matcher.matches()) {
            FacesMessage fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email invalido", "Por favor, ingrese un email valido");
            throw new ValidatorException(fmsg);
        }

        if (operacion == 0) {
            IUsuarioDao userDao = new ImpUsuarioDao();
            List<SmsUsuario> usuario = userDao.verificarEmailDisponible(user);
            if (!user.equalsIgnoreCase("")) {
                if (!usuario.isEmpty()) {

                    FacesMessage fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email invalido", "El email ya esta en uso");
                    throw new ValidatorException(fmsg);
                }
            }
        } else if (operacion == 1) {
            SmsUsuario usuario = (SmsUsuario) component.getAttributes().get("usuario");
            IUsuarioDao userDao = new ImpUsuarioDao();
            List<SmsUsuario> newLogin = new ArrayList<>();

            if (!usuario.getUsuarioEmail().equalsIgnoreCase(user)) {
                newLogin = userDao.verificarEmailDisponible(user);
                if (!newLogin.isEmpty()) {
                    FacesMessage fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email invalido", "El email ya esta en uso");
                    throw new ValidatorException(fmsg);
                }
            }
        }
    }

}
