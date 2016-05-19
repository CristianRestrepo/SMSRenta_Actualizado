/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.validadores;

import com.planit.smsrenta.dao.IServicioDao;
import com.planit.smsrenta.dao.ImpServicioDao;
import com.planit.smsrenta.modelos.SmsServicios;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
@FacesValidator("dateTimeValidator")
public class dateTimeValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        SimpleDateFormat sdft = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date fecha = new Date();
        Date hora = new Date();

        String horaActual;
        String fechaActual;

        Date fInicio = new Date();
        Date fEntrega = new Date();
        Date hInicio = new Date();
        Date hEntrega = new Date();
        Date hActual = new Date();
        Date fActual = new Date();

        String fechaInicio;
        String fechaEntrega;
        String horaInicio;
        String horaEntrega;
        String Servicio;

        Servicio = (String) component.getAttributes().get("servicio");
        IServicioDao servDao = new ImpServicioDao();
        SmsServicios servicioElegido = new SmsServicios();
        servicioElegido.setServicioNombre(Servicio);
        servicioElegido = servDao.ConsultarServicio(servicioElegido);

        fechaInicio = sdf.format((Date) component.getAttributes().get("fechaInicio"));
        fechaEntrega = sdf.format((Date) component.getAttributes().get("fechaEntrega"));
        horaInicio = ((String) component.getAttributes().get("horaInicio") + ":" + component.getAttributes().get("minutosInicio"));
        horaEntrega = ((String) component.getAttributes().get("horaEntrega") + ":" + value);

        try {
            horaActual = sdft.format(hora);
            fechaActual = sdf.format(fecha);

            fInicio = sdf.parse(fechaInicio);
            fEntrega = sdf.parse(fechaEntrega);
            hInicio = sdft.parse(horaInicio);
            hEntrega = sdft.parse(horaEntrega);
            hActual = sdft.parse(horaActual);
            fActual = sdf.parse(fechaActual);

        } catch (ParseException pe) {
            pe.getMessage();
        }

        long milis1;
        long milis2;

        Calendar calHoraInicio = Calendar.getInstance();
        calHoraInicio.setTime(hInicio);
        Calendar calHoraEntrega = Calendar.getInstance();
        calHoraEntrega.setTime(hEntrega);
        Calendar calFechaInicio = Calendar.getInstance();
        calFechaInicio.setTime(fInicio);
        Calendar calFechaEntrega = Calendar.getInstance();
        calFechaEntrega.setTime(fEntrega);

        // conseguir la representacion de la fecha en milisegundos
        milis1 = calFechaInicio.getTimeInMillis();
        milis2 = calFechaEntrega.getTimeInMillis();

        // calcular la diferencia en dias
        long diff = milis2 - milis1;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        milis1 = calHoraInicio.getTimeInMillis();
        milis2 = calHoraEntrega.getTimeInMillis();

        long diffHourDifferentDay;

        diff = milis1 - milis2;
        diffHourDifferentDay = (diffDays * 24) - (diff / (60 * 60 * 1000));

        Calendar calInicio = Calendar.getInstance();
        calInicio.setTime(hActual);
        calInicio.add(Calendar.MINUTE, 60);

        Date tiempoMinimo = new Date();

        try {
            tiempoMinimo = sdft.parse(sdft.format(calInicio.getTime()));
        } catch (ParseException pe) {
            pe.getMessage();
        }

        if (fInicio.before(fActual)) {
            FacesMessage message = new FacesMessage();
            message.setSummary("La fecha de inicio es anterior a la fecha actual");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        } else if (fInicio.after(fActual)) {
            if (fEntrega.after(fInicio)) {
                if (servicioElegido.getSmsTipoDuracion().getIdTipoDuracion() == 3 && diffHourDifferentDay < 24) {
                    FacesMessage message = new FacesMessage();
                    message.setSummary("El tiempo de reserva debe ser minimo 24 horas");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                } else if (servicioElegido.getSmsTipoDuracion().getIdTipoDuracion() == 4 && diffDays < 7) {
                    FacesMessage message = new FacesMessage();
                    message.setSummary("El tiempo de reserva debe ser minimo de 7 dias");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                } else if (servicioElegido.getSmsTipoDuracion().getIdTipoDuracion() == 5 && diffDays < 29) {
                    FacesMessage message = new FacesMessage();
                    message.setSummary("El tiempo de reserva debe ser minimo de 29 dias");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                }
            } else if (fEntrega.before(fInicio)) {
                FacesMessage message = new FacesMessage();
                message.setSummary("La fecha de entrega es anterior a la fecha de inicio");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        } else if (fInicio.equals(fActual)) {
            if (fEntrega.after(fInicio)) {
                if (hInicio.after(tiempoMinimo)) {
                } else if (servicioElegido.getSmsTipoDuracion().getIdTipoDuracion() == 3 && diffHourDifferentDay < 24) {
                    FacesMessage message = new FacesMessage();
                    message.setSummary("El tiempo de reserva debe ser minimo 24 horas");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                } else if (servicioElegido.getSmsTipoDuracion().getIdTipoDuracion() == 4 && diffDays < 7) {
                    FacesMessage message = new FacesMessage();
                    message.setSummary("El tiempo de reserva debe ser minimo de 7 dias");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                } else if (servicioElegido.getSmsTipoDuracion().getIdTipoDuracion() == 5 && diffDays < 29) {
                    FacesMessage message = new FacesMessage();
                    message.setSummary("El tiempo de reserva debe ser minimo de 29 dias");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                }
            } else if (hInicio.equals(tiempoMinimo)) {
                FacesMessage message = new FacesMessage();
                message.setSummary("La hora de inicio del servicio debe tener por lo menos 1 hora de antelacion");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            } else if (hInicio.before(tiempoMinimo) && hInicio.after(hActual)) {
                FacesMessage message = new FacesMessage();
                message.setSummary("La hora de inicio del servicio debe tener por lo menos 1 hora de antelacion");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            } else if (hInicio.before(hActual)) {
                FacesMessage message = new FacesMessage();
                message.setSummary("La hora de inicio es anterior a la hora actual");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        } else if (fEntrega.before(fInicio)) {
            FacesMessage message = new FacesMessage();
            message.setSummary("La fecha de entrega es anterior a la fecha de inicio");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
}
