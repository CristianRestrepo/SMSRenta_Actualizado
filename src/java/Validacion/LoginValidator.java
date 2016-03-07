/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validacion;

import DAO.IUsuarioDao;
import DAO.ImpUsuarioDao;
import Modelo.SmsUsuario;
import static com.sun.codemodel.JExpr.component;
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
@FacesValidator("LoginValidator")
public class LoginValidator implements Validator {

    String user;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        int operacion = (int) component.getAttributes().get("operacion");
        user = (String) value;

        if (operacion == 0) { //Registro
            IUsuarioDao userDao = new ImpUsuarioDao();
            List<SmsUsuario> usuario = userDao.verificarLoginDisponible(user);
            if (!user.equalsIgnoreCase("")) {
                if (!usuario.isEmpty()) {

                    FacesMessage fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login invalido", "El nombre de usuario ya esta en uso");
                    throw new ValidatorException(fmsg);
                }
            }
        } else if (operacion == 1) {//Modificacion
            SmsUsuario usuario = (SmsUsuario) component.getAttributes().get("usuario");

            IUsuarioDao userDao = new ImpUsuarioDao();           
            List<SmsUsuario> newLogin = new ArrayList<>();

            if (!usuario.getUsuarioLogin().equalsIgnoreCase(user)) {
                newLogin = userDao.verificarLoginDisponible(user);
                if (!newLogin.isEmpty()) {
                    FacesMessage fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login invalido", "El nombre de usuario ya esta en uso");
                    throw new ValidatorException(fmsg);
                }
            }
        }

    }

}
