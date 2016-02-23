/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validacion;

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
 * @author CristianCamilo
 */
@FacesValidator("emailValidator")
public class emailValidator implements Validator{
    
Pattern pattern = Pattern.compile("[-0-9a-zA-Z.+_]+@[-0-9a-zA-Z.+_]+\\.[a-zA-Z]{2,4}");
	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		Matcher matcher = pattern.matcher(value.toString());
		if (!matcher.matches()) {
			FacesMessage fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email invalido", "Por favor, ingrese un email valido");
			 throw new ValidatorException(fmsg);
		}
	}

    
}
