/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.validadores;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@FacesValidator("TimeValidator")
public class timeValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        SimpleDateFormat sdft = new SimpleDateFormat("HH:mm:ss");

        Date horaActual = new Date();
        Date horaInicioReservacion = new Date();

        String horaInicio;
        String shoraActual;
        horaInicio = ((String) component.getAttributes().get("horaInicio") + ":" + value);

        try {
            shoraActual = sdft.format(horaActual);

            horaActual = sdft.parse(shoraActual);
            horaInicioReservacion = sdft.parse(horaInicio);

        } catch (ParseException pe) {
            pe.getMessage();
        }

        if (horaInicioReservacion.before(horaActual)) {
            FacesMessage message = new FacesMessage();
            message.setSummary("La hora de inicio es anterior a la hora actual");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }

    }

}
