/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.SmsEmpleado;
import Modelo.SmsReservacion;
import Modelo.SmsUsuario;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Desarrollo_Planit
 */
public class ImpReservacionDao implements IReservacionDao {

    private FacesMessage message;

    @Override
    public List<SmsReservacion> mostrarReservaciones() {
        Session session = null;
        List<SmsReservacion> reservaciones = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsReservacion as reservacion left join fetch reservacion.smsVehiculo as vehiculo left join fetch reservacion.smsEmpleado as conductor left join fetch reservacion.smsUsuario as cliente left join fetch reservacion.smsCiudad as ciudad");
            reservaciones = (List<SmsReservacion>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return reservaciones;
    }

    @Override
    public void registrarReservacion(SmsReservacion reservacion) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(reservacion);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Reservacion registrada", "");
        } catch (HibernateException e) {
            e.getMessage();
            session.getTransaction().rollback();
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Imposible realizar la operacion", null);
        } finally {
            if (session != null) {
                session.close();
            }
        }FacesContext.getCurrentInstance().addMessage(null, message);
    }

    @Override
    public void modificarReservacion(SmsReservacion reservacion) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(reservacion);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Reservacion modificada", "");
        } catch (HibernateException e) {
            e.getMessage();
            session.getTransaction().rollback();
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Imposible realizar la operacion", null);
        } finally {
            if (session != null) {
                session.close();
            }
        }FacesContext.getCurrentInstance().addMessage(null, message);
    }

    @Override
    public void eliminarReservacion(SmsReservacion reservacion) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(reservacion);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Reservacion eliminada", "");
        } catch (HibernateException e) {
            e.getMessage();
            session.getTransaction().rollback();
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Imposible realizar la operacion", null);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    @Override
    public List<SmsReservacion> consultarReserva(SmsReservacion reserva) {
        Session session = null;
        List<SmsReservacion> reservaciones = new ArrayList<>();
        SimpleDateFormat formatDate;
        SimpleDateFormat formatTime;
        formatDate = new SimpleDateFormat("yyyy-MM-dd");
        formatTime = new SimpleDateFormat("HH:mm:ss");

        //Se formatean las fechas y horas
        String FechaInicio = formatDate.format(reserva.getReservacionFechaInicio());
        String FechaLlegada = formatDate.format(reserva.getReservacionFechaLlegada());
        String HoraInicio = formatTime.format(reserva.getReservacionHoraInicio());
        String HoraLlegada = formatTime.format(reserva.getReservacionHoraLlegada());

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsReservacion as reservacion left join reservacion.smsCiudad left join fetch reservacion.smsVehiculo as vehiculo left join fetch reservacion.smsEmpleado as empleado left join fetch reservacion.smsUsuario as cliente where cliente.idUsuario = '" + reserva.getSmsUsuario().getIdUsuario() + "' and vehiculo.idVehiculo = '" + reserva.getSmsVehiculo().getIdVehiculo() + "' and empleado.idEmpleado = '" + reserva.getSmsEmpleado().getIdEmpleado() + "' and "
                    + "reservacion.reservacionFechaInicio = '" + FechaInicio + "' and reservacion.reservacionFechaLlegada = '" + FechaLlegada + "' and reservacion.reservacionHoraInicio = '" + HoraInicio + "' and "
                    + "reservacion.reservacionHoraLlegada = '" + HoraLlegada + "'");
            reservaciones = (List<SmsReservacion>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return reservaciones;
    }

    //QUERY PARA SACAR DATOS DE RESERVA HECHA
    @Override
    public List<SmsReservacion> mostrarReservacionCliente(SmsUsuario usuario) {
        Session session = null;
        List<SmsReservacion> resevacionesHechas = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("FROM SmsReservacion as reservacion left join fetch reservacion.smsUsuario left join fetch reservacion.smsVehiculo as vehiculo left join fetch reservacion.smsEmpleado as conductor left join fetch reservacion.smsUsuario as cliente WHERE cliente.idUsuario = '" + usuario.getIdUsuario() + "'");
            resevacionesHechas = (List<SmsReservacion>) query.list();

        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return resevacionesHechas;
    }

    @Override
    public List<SmsReservacion> mostrarReservacionConductores(SmsEmpleado conductor) {
        Session session = null;
        List<SmsReservacion> resevacionesHechas = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("FROM SmsReservacion as reservacion left join fetch reservacion.smsUsuario as cliente left join fetch reservacion.smsCiudad as ciudad left join fetch reservacion.smsVehiculo as vehiculo left join fetch reservacion.smsEmpleado as empleado WHERE empleado.idEmpleado = '" + conductor.getIdEmpleado() + "'");
            resevacionesHechas = (List<SmsReservacion>) query.list();

        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return resevacionesHechas;
    }

    @Override
    public List<SmsReservacion> consultarReservacionId(SmsReservacion reserva) {
        Session session = null;
        List<SmsReservacion> reservas = new ArrayList<>();

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsReservacion as reservacion left join fetch reservacion.smsUsuario as cliente left join fetch reservacion.smsCiudad as ciudad left join fetch reservacion.smsVehiculo as vehiculo left join fetch reservacion.smsEmpleado as empleado where reservacion.idReservacion = '" + reserva.getIdReservacion() + "'");
            reservas = (List<SmsReservacion>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return reservas;
    }

    @Override
    public List<SmsReservacion> consultarReservacionSinEmpleado(SmsReservacion reserva) {
        Session session = null;
        List<SmsReservacion> reservas = new ArrayList<>();
        SimpleDateFormat formatDate;
        SimpleDateFormat formatTime;
        formatDate = new SimpleDateFormat("yyyy-MM-dd");
        formatTime = new SimpleDateFormat("HH:mm:ss");

        //Se formatean las fechas y horas
        String FechaInicio = formatDate.format(reserva.getReservacionFechaInicio());
        String FechaLlegada = formatDate.format(reserva.getReservacionFechaLlegada());
        String HoraInicio = formatTime.format(reserva.getReservacionHoraInicio());
        String HoraLlegada = formatTime.format(reserva.getReservacionHoraLlegada());

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsReservacion as reservacion left join fetch reservacion.smsCiudad as ciudad left join fetch reservacion.smsUsuario as cliente left join fetch agenda.smsVehiculo as vehiculo where cliente.idUsuario = '" + reserva.getSmsUsuario().getIdUsuario() + "' and vehiculo.idVehiculo = '" + reserva.getSmsVehiculo().getIdVehiculo() + "' and "
                    + "reservacion.reservacionFechaInicio = '" + FechaInicio + "' and reservacion.reservacionFechaLlegada = '" + FechaLlegada + "' and reservacion.reservacionHoraInicio = '" + HoraInicio + "' and "
                    + "reservacion.reservacionHoraLlegada = '" + HoraLlegada + "'");
            reservas = (List<SmsReservacion>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return reservas;
    }

}
