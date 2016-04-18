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

        diff = milis2 - milis1;

        long diffHourEqualDay = (diff / (60 * 60 * 1000));
        long diffHourDifferentDay;

        diff = milis1 - milis2;
        diffHourDifferentDay = (diffDays * 24) - (diff / (60 * 60 * 1000));

        Calendar calInicio1 = Calendar.getInstance();
        calInicio1.setTime(hInicio);
        calInicio1.add(Calendar.MINUTE, 15);

        Calendar calInicio2 = Calendar.getInstance();
        calInicio2.setTime(hInicio);
        calInicio2.add(Calendar.MINUTE, 30);
        
        Calendar calInicio3 = Calendar.getInstance();
        calInicio3.setTime(hActual);
        calInicio3.add(Calendar.MINUTE, 15);

        Date hInicioMod1 = new Date();
        Date hInicioMod2 = new Date();
        Date hInicioMod3 = new Date();

        try {
            hInicioMod1 = sdft.parse(sdft.format(calInicio1.getTime()));
            hInicioMod2 = sdft.parse(sdft.format(calInicio2.getTime()));
            hInicioMod3 = sdft.parse(sdft.format(calInicio3.getTime()));
        } catch (ParseException pe) {
            pe.getMessage();
        }

        if (fInicio.before(fActual)) {
            FacesMessage message = new FacesMessage();
            message.setSummary("La fecha de inicio es anterior a la fecha actual");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        } else if (fInicio.after(fActual)) {
            if (fEntrega.equals(fInicio)) {
                if (hEntrega.before(hInicio) || hEntrega.equals(hInicio)) {
                    FacesMessage message = new FacesMessage();
                    message.setSummary("La hora de entrega es anterior o igual a la hora de inicio");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                } else if (hEntrega.after(hInicio) && hEntrega.before(hInicioMod2) && servicioElegido.getServicioDuracion() == 0) {
                    FacesMessage message = new FacesMessage();
                    message.setSummary("El tiempo de reserva debe ser minimo de 15 minutos");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                } else if (hEntrega.after(hInicio) && diffHourEqualDay < 24 && servicioElegido.getServicioDuracion() >= 1) {
                    FacesMessage message = new FacesMessage();
                    message.setSummary("El tiempo de reserva debe ser minimo 24 horas");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                }
            } else if (fEntrega.after(fInicio)) {
                if (servicioElegido.getServicioDuracion() >= 1 && diffHourDifferentDay < 24) {
                    FacesMessage message = new FacesMessage();
                    message.setSummary("El tiempo de reserva debe ser minimo 24 horas");
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
            if (fEntrega.equals(fInicio)) {
                if (hInicio.after(hInicioMod3)) {                  
                        if (hEntrega.before(hInicio) || hEntrega.equals(hInicio)) {
                            FacesMessage message = new FacesMessage();
                            message.setSummary("La hora de entrega es anterior o igual a la hora de inicio");
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            throw new ValidatorException(message);
                        } else if (hEntrega.after(hInicio) && hEntrega.before(hInicioMod2) && servicioElegido.getServicioDuracion() == 0) {
                            FacesMessage message = new FacesMessage();
                            message.setSummary("El tiempo de reserva debe ser minimo de 30 minutos");
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            throw new ValidatorException(message);
                        } else if (hEntrega.after(hInicio) && diffHourEqualDay < 24 && servicioElegido.getServicioDuracion() >= 1) {
                            FacesMessage message = new FacesMessage();
                            message.setSummary("El tiempo de reserva debe ser minimo 24 horas");
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            throw new ValidatorException(message);
                        }                    
                } else if (hInicio.equals(hInicioMod3)) {
                    FacesMessage message = new FacesMessage();
                    message.setSummary("La hora de recojida debe tener por lo menos 15 minutos de antelacion");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                } else if (hInicio.before(hInicioMod3) && hInicio.after(hActual)) {
                    FacesMessage message = new FacesMessage();
                    message.setSummary("La hora de recojida debe tener por lo menos 15 minutos de antelacion");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                } else if (hInicio.before(hActual)) {
                    FacesMessage message = new FacesMessage();
                    message.setSummary("La hora de inicio es anterior a la hora actual");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                }
            } else if (fEntrega.after(fInicio)) {
                if (hEntrega.after(hInicio) && diffHourEqualDay < 24 && servicioElegido.getServicioDuracion() >= 1) {
                    FacesMessage message = new FacesMessage();
                    message.setSummary("El tiempo de reserva debe ser minimo 24 horas");
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
}
