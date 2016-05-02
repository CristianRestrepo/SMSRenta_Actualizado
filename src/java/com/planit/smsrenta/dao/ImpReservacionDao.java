/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsEmpleado;
import com.planit.smsrenta.modelos.SmsReservacion;
import com.planit.smsrenta.modelos.SmsUsuario;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Desarrollo_Planit
 */
public class ImpReservacionDao implements IReservacionDao {

    private FacesMessage message;
    SessionFactory sessions = NewHibernateUtil.getSessionFactory();

    @Override
    public List<SmsReservacion> mostrarReservaciones() {
        Session session = null;
        List<SmsReservacion> reservaciones = new ArrayList<>();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsReservacion as reservacion "
                    + "left join fetch reservacion.smsCategoriasServicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadInicio as CiudadInicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadDestino as CiudadDestino "
                    + "left join fetch reservacion.smsEmpleado as empleado "
                    + "left join fetch empleado.smsUsuario "
                    + "left join fetch empleado.smsProveedor "
                    + "left join fetch reservacion.smsEstado as estado "
                    + "left join fetch reservacion.smsServicios as servicio "
                    + "left join fetch reservacion.smsUsuario as cliente "
                    + "left join fetch servicio.smsMercado as mercado "
                    + "left join fetch reservacion.smsVehiculo as vehiculo "
                    + "left join fetch vehiculo.smsReferencia as referencia "
                    + "left join fetch vehiculo.smsColor "
                    + "left join fetch referencia.smsMarca "
                    + "order by reservacion.idReservacion desc");
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
            session = sessions.openSession();
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
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    @Override
    public void modificarReservacion(SmsReservacion reservacion) {
        Session session = null;
        try {
            session = sessions.openSession();
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
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    @Override
    public void eliminarReservacion(SmsReservacion reservacion) {
        Session session = null;
        try {
            session = sessions.openSession();
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
    public SmsReservacion consultarReserva(SmsReservacion reserva) {
        Session session = null;
        SmsReservacion reservaciones = new SmsReservacion();
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
            session = sessions.openSession();
            Query query = session.createQuery("from SmsReservacion as reservacion "
                    + "left join fetch reservacion.smsCategoriasServicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadInicio as CiudadInicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadDestino as CiudadDestino "
                    + "left join fetch reservacion.smsEmpleado as empleado "
                    + "left join fetch empleado.smsUsuario "
                    + "left join fetch empleado.smsProveedor "
                    + "left join fetch reservacion.smsEstado as estado "
                    + "left join fetch reservacion.smsServicios as servicio "
                    + "left join fetch reservacion.smsUsuario as cliente "
                    + "left join fetch reservacion.smsVehiculo as vehiculo "
                    + "left join fetch vehiculo.smsReferencia as referencia "
                    + "left join fetch vehiculo.smsColor "
                    + "left join fetch referencia.smsMarca where "
                    + "cliente.idUsuario = '" + reserva.getSmsUsuario().getIdUsuario() + "' and "
                    + "vehiculo.idVehiculo = '" + reserva.getSmsVehiculo().getIdVehiculo() + "' and "
                    + "empleado.idEmpleado = '" + reserva.getSmsEmpleado().getIdEmpleado() + "' and "
                    + "reservacion.reservacionFechaInicio = '" + FechaInicio + "' and "
                    + "reservacion.reservacionFechaLlegada = '" + FechaLlegada + "' and "
                    + "reservacion.reservacionHoraInicio = '" + HoraInicio + "' and "
                    + "reservacion.reservacionHoraLlegada = '" + HoraLlegada + "'");
            reservaciones = (SmsReservacion) query.list().get(0);
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
            session = sessions.openSession();
            Query query = session.createQuery("from SmsReservacion as reservacion "
                    + "left join fetch reservacion.smsCategoriasServicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadInicio as CiudadInicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadDestino as CiudadDestino "
                    + "left join fetch reservacion.smsEmpleado as empleado "
                    + "left join fetch empleado.smsUsuario "
                    + "left join fetch empleado.smsProveedor "
                    + "left join fetch reservacion.smsEstado as estado "
                    + "left join fetch reservacion.smsServicios as servicio "
                    + "left join fetch reservacion.smsUsuario as cliente "
                    + "left join fetch reservacion.smsVehiculo as vehiculo "
                    + "left join fetch vehiculo.smsReferencia as referencia "
                    + "left join fetch vehiculo.smsColor "
                    + "left join fetch referencia.smsMarca  where "
                    + "cliente.idUsuario = '" + usuario.getIdUsuario() + "' order by reservacion.idReservacion desc");
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
            session = sessions.openSession();
            Query query = session.createQuery("from SmsReservacion as reservacion "
                    + "left join fetch reservacion.smsCategoriasServicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadInicio as CiudadInicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadDestino as CiudadDestino "
                    + "left join fetch reservacion.smsEmpleado as empleado "
                    + "left join fetch empleado.smsUsuario left join fetch empleado.smsProveedor "
                    + "left join fetch reservacion.smsEstado as estado "
                    + "left join fetch reservacion.smsServicios as servicio "
                    + "left join fetch reservacion.smsUsuario as cliente "
                    + "left join fetch reservacion.smsVehiculo as vehiculo "
                    + "left join fetch vehiculo.smsReferencia as referencia "
                    + "left join fetch vehiculo.smsColor "
                    + "left join fetch referencia.smsMarca where "
                    + "empleado.idEmpleado = '" + conductor.getIdEmpleado() + "' order by reservacion.idReservacion desc");
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
    public SmsReservacion consultarReservacionId(SmsReservacion reserva) {
        Session session = null;
        SmsReservacion reservas = new SmsReservacion();

        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsReservacion as reservacion "
                    + "left join fetch reservacion.smsCategoriasServicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadInicio as CiudadInicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadDestino as CiudadDestino "
                    + "left join fetch reservacion.smsEmpleado as empleado "
                    + "left join fetch empleado.smsUsuario "
                    + "left join fetch empleado.smsProveedor "
                    + "left join fetch reservacion.smsEstado as estado "
                    + "left join fetch reservacion.smsServicios as servicio "
                    + "left join fetch reservacion.smsUsuario as cliente "
                    + "left join fetch reservacion.smsVehiculo as vehiculo "
                    + "left join fetch vehiculo.smsReferencia as referencia "
                    + "left join fetch vehiculo.smsColor "
                    + "left join fetch referencia.smsMarca "
                    + "where reservacion.idReservacion = '" + reserva.getIdReservacion() + "'");
            reservas = (SmsReservacion) query.list().get(0);
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
    public SmsReservacion consultarReservacionSinEmpleado(SmsReservacion reserva) {
        Session session = null;
        SmsReservacion reservas = new SmsReservacion();
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
            session = sessions.openSession();
            Query query = session.createQuery("from SmsReservacion as reservacion "
                    + "left join fetch reservacion.smsCategoriasServicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadInicio as CiudadInicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadDestino as CiudadDestino "
                    + "left join fetch reservacion.smsEmpleado as empleado "
                    + "left join fetch empleado.smsUsuario "
                    + "left join fetch empleado.smsProveedor "
                    + "left join fetch reservacion.smsEstado as estado "
                    + "left join fetch reservacion.smsServicios as servicio "
                    + "left join fetch reservacion.smsUsuario as cliente "
                    + "left join fetch reservacion.smsVehiculo as vehiculo "
                    + "left join fetch vehiculo.smsReferencia as referencia "
                    + "left join fetch vehiculo.smsColor "
                    + "left join fetch referencia.smsMarca where "
                    + "cliente.idUsuario = '" + reserva.getSmsUsuario().getIdUsuario() + "' and "
                    + "vehiculo.idVehiculo = '" + reserva.getSmsVehiculo().getIdVehiculo() + "' and "
                    + "reservacion.reservacionFechaInicio = '" + FechaInicio + "' and "
                    + "reservacion.reservacionFechaLlegada = '" + FechaLlegada + "' and "
                    + "reservacion.reservacionHoraInicio = '" + HoraInicio + "' and "
                    + "reservacion.reservacionHoraLlegada = '" + HoraLlegada + "' "
                    + "order by reservacion.idReservacion desc");
            reservas = (SmsReservacion) query.list().get(0);
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
    public List<SmsReservacion> filtrarReservacionSegunCliente(String valor) {
        Session session = null;
        List<SmsReservacion> reservas = new ArrayList<>();

        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsReservacion as reservacion "
                    + "left join fetch reservacion.smsCategoriasServicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadInicio as CiudadInicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadDestino as CiudadDestino "
                    + "left join fetch reservacion.smsEmpleado as empleado "
                    + "left join fetch empleado.smsUsuario "
                    + "left join fetch empleado.smsProveedor "
                    + "left join fetch reservacion.smsEstado as estado "
                    + "left join fetch reservacion.smsServicios as servicio "
                    + "left join fetch reservacion.smsUsuario as cliente "
                    + "left join fetch reservacion.smsVehiculo as vehiculo "
                    + "left join fetch vehiculo.smsReferencia as referencia "
                    + "left join fetch vehiculo.smsColor "
                    + "left join fetch referencia.smsMarca "
                    + "where cliente.usuarioCc LIKE '%" + valor + "%' or "
                    + "cliente.usuarioNombre LIKE '%" + valor + "%' or "
                    + "cliente.usuarioEmail LIKE '" + valor + "' "
                    + "order by reservacion.idReservacion desc");
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
    public List<SmsReservacion> consultarReservacionesSegunEstado(int estado) {
        Session session = null;
        List<SmsReservacion> reservaciones = new ArrayList<>();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsReservacion as reservacion "
                    + "left join fetch reservacion.smsCategoriasServicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadInicio as CiudadInicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadDestino as CiudadDestino "
                    + "left join fetch reservacion.smsEmpleado as empleado "
                    + "left join fetch empleado.smsUsuario "
                    + "left join fetch empleado.smsProveedor "
                    + "left join fetch reservacion.smsEstado as estado "
                    + "left join fetch reservacion.smsServicios as servicio "
                    + "left join fetch reservacion.smsUsuario as cliente "
                    + "left join fetch servicio.smsMercado as mercado "
                    + "left join fetch reservacion.smsVehiculo as vehiculo "
                    + "left join fetch vehiculo.smsReferencia as referencia "
                    + "left join fetch vehiculo.smsColor "
                    + "left join fetch referencia.smsMarca where "
                    + "estado.idEstado = '" + estado + "' "
                    + "order by reservacion.idReservacion desc");
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
    public List<SmsReservacion> mostrarReservacionClienteSegunEstado(SmsUsuario usuario, int estado) {
        Session session = null;
        List<SmsReservacion> resevacionesHechas = null;

        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsReservacion as reservacion "
                    + "left join fetch reservacion.smsCategoriasServicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadInicio as CiudadInicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadDestino as CiudadDestino "
                    + "left join fetch reservacion.smsEmpleado as empleado "
                    + "left join fetch empleado.smsUsuario "
                    + "left join fetch empleado.smsProveedor "
                    + "left join fetch reservacion.smsEstado as estado "
                    + "left join fetch reservacion.smsServicios as servicio "
                    + "left join fetch reservacion.smsUsuario as cliente "
                    + "left join fetch reservacion.smsVehiculo as vehiculo "
                    + "left join fetch vehiculo.smsReferencia as referencia "
                    + "left join fetch vehiculo.smsColor "
                    + "left join fetch referencia.smsMarca  where "
                    + "cliente.idUsuario = '" + usuario.getIdUsuario() + "' and "
                    + "estado.idEstado = '" + estado + "' "
                    + "order by reservacion.idReservacion desc");
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
    public List<SmsReservacion> mostrarReservacionConductoresSegunEstado(SmsEmpleado conductor, int estado) {
        Session session = null;
        List<SmsReservacion> resevacionesHechas = null;

        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsReservacion as reservacion "
                    + "left join fetch reservacion.smsCategoriasServicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadInicio as CiudadInicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadDestino as CiudadDestino "
                    + "left join fetch reservacion.smsEmpleado as empleado "
                    + "left join fetch empleado.smsUsuario left join fetch empleado.smsProveedor "
                    + "left join fetch reservacion.smsEstado as estado "
                    + "left join fetch reservacion.smsServicios as servicio "
                    + "left join fetch reservacion.smsUsuario as cliente "
                    + "left join fetch reservacion.smsVehiculo as vehiculo "
                    + "left join fetch vehiculo.smsReferencia as referencia "
                    + "left join fetch vehiculo.smsColor "
                    + "left join fetch referencia.smsMarca where "
                    + "empleado.idEmpleado = '" + conductor.getIdEmpleado() + "' and estado.idEstado = '" + estado + "' "
                    + "order by reservacion.idReservacion desc");
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
    public List<SmsReservacion> filtrarReservacionSegunClienteSegunEstado(String valor, int estado) {
        Session session = null;
        List<SmsReservacion> reservas = new ArrayList<>();

        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsReservacion as reservacion "
                    + "left join fetch reservacion.smsCategoriasServicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadInicio as CiudadInicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadDestino as CiudadDestino "
                    + "left join fetch reservacion.smsEmpleado as empleado "
                    + "left join fetch empleado.smsUsuario "
                    + "left join fetch empleado.smsProveedor "
                    + "left join fetch reservacion.smsEstado as estado "
                    + "left join fetch reservacion.smsServicios as servicio "
                    + "left join fetch reservacion.smsUsuario as cliente "
                    + "left join fetch reservacion.smsVehiculo as vehiculo "
                    + "left join fetch vehiculo.smsReferencia as referencia "
                    + "left join fetch vehiculo.smsColor "
                    + "left join fetch referencia.smsMarca "
                    + "where estado.idEstado = '" + estado + "' and "
                    + "(cliente.usuarioCc LIKE '%" + valor + "%' or "
                    + "cliente.usuarioNombre LIKE '%" + valor + "%' or "
                    + "cliente.usuarioEmail LIKE '" + valor + "') order by reservacion.idReservacion desc");
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
    public List<SmsReservacion> consultarReservacionesSegunEstadoyMercado(int estado, String mercado) {
        Session session = null;
        List<SmsReservacion> reservaciones = new ArrayList<>();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsReservacion as reservacion "
                    + "left join fetch reservacion.smsCategoriasServicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadInicio as CiudadInicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadDestino as CiudadDestino "
                    + "left join fetch reservacion.smsEmpleado as empleado "
                    + "left join fetch empleado.smsUsuario "
                    + "left join fetch empleado.smsProveedor "
                    + "left join fetch reservacion.smsEstado as estado "
                    + "left join fetch reservacion.smsServicios as servicio "
                    + "left join fetch reservacion.smsUsuario as cliente "
                    + "left join fetch servicio.smsMercado as mercado "
                    + "left join fetch reservacion.smsVehiculo as vehiculo "
                    + "left join fetch vehiculo.smsReferencia as referencia "
                    + "left join fetch vehiculo.smsColor left join fetch referencia.smsMarca where "
                    + "estado.idEstado = '" + estado + "' and "
                    + "mercado.mercadoNombre = '" + mercado + "' "
                    + "order by reservacion.idReservacion desc");
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
    public List<SmsReservacion> filtrarReservacionSegunClienteSegunEstadoyMercado(String valor, int estado, String mercado) {
        Session session = null;
        List<SmsReservacion> reservas = new ArrayList<>();

        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsReservacion as reservacion "
                    + "left join fetch reservacion.smsCategoriasServicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadInicio as CiudadInicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadDestino as CiudadDestino "
                    + "left join fetch reservacion.smsEmpleado as empleado "
                    + "left join fetch empleado.smsUsuario "
                    + "left join fetch empleado.smsProveedor "
                    + "left join fetch reservacion.smsEstado as estado "
                    + "left join fetch reservacion.smsServicios as servicio "
                    + "left join fetch servicio.smsMercado as mercado "
                    + "left join fetch reservacion.smsUsuario as cliente "
                    + "left join fetch reservacion.smsVehiculo as vehiculo "
                    + "left join fetch vehiculo.smsReferencia as referencia "
                    + "left join fetch vehiculo.smsColor "
                    + "left join fetch referencia.smsMarca "
                    + "where estado.idEstado = '" + estado + "' and "
                    + "mercado.mercadoNombre = '" + mercado + "' and "
                    + "(cliente.usuarioCc LIKE '%" + valor + "%' or "
                    + "cliente.usuarioNombre LIKE '%" + valor + "%' or "
                    + "cliente.usuarioEmail LIKE '" + valor + "') order by reservacion.idReservacion desc");
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
    public List<SmsReservacion> mostrarReservacionClienteSegunEstadoyMercado(SmsUsuario usuario, int estado, String mercado) {
        Session session = null;
        List<SmsReservacion> resevacionesHechas = null;

        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsReservacion as reservacion "
                    + "left join fetch reservacion.smsCategoriasServicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadInicio as CiudadInicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadDestino as CiudadDestino "
                    + "left join fetch reservacion.smsEmpleado as empleado "
                    + "left join fetch empleado.smsUsuario "
                    + "left join fetch empleado.smsProveedor "
                    + "left join fetch reservacion.smsEstado as estado "
                    + "left join fetch reservacion.smsServicios as servicio "
                    + "left join fetch servicio.smsMercado as mercado "
                    + "left join fetch reservacion.smsUsuario as cliente "
                    + "left join fetch reservacion.smsVehiculo as vehiculo "
                    + "left join fetch vehiculo.smsReferencia as referencia "
                    + "left join fetch vehiculo.smsColor "
                    + "left join fetch referencia.smsMarca  where "
                    + "mercado.mercadoNombre = '" + mercado + "' and "
                    + "cliente.idUsuario = '" + usuario.getIdUsuario() + "' and "
                    + "estado.idEstado = '" + estado + "' "
                    + "order by reservacion.idReservacion desc");
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
    public List<SmsReservacion> mostrarReservacionConductoresSegunEstadoyMercado(SmsEmpleado conductor, int estado, String mercado) {
        Session session = null;
        List<SmsReservacion> resevacionesHechas = null;

        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsReservacion as reservacion "
                    + "left join fetch reservacion.smsCategoriasServicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadInicio as CiudadInicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadDestino as CiudadDestino "
                    + "left join fetch reservacion.smsEmpleado as empleado "
                    + "left join fetch empleado.smsUsuario left join fetch empleado.smsProveedor "
                    + "left join fetch reservacion.smsEstado as estado "
                    + "left join fetch reservacion.smsServicios as servicio "
                    + "left join fetch servicio.smsMercado as mercado "
                    + "left join fetch reservacion.smsUsuario as cliente "
                    + "left join fetch reservacion.smsVehiculo as vehiculo "
                    + "left join fetch vehiculo.smsReferencia as referencia "
                    + "left join fetch vehiculo.smsColor "
                    + "left join fetch referencia.smsMarca where "
                    + "mercado.mercadoNombre = '" + mercado + "' and "
                    + "empleado.idEmpleado = '" + conductor.getIdEmpleado() + "' and "
                    + "estado.idEstado = '" + estado + "' "
                    + "order by reservacion.idReservacion desc");
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
    public List<SmsReservacion> mostrarReservacionesSegunMercado(String mercado) {
        Session session = null;
        List<SmsReservacion> reservaciones = new ArrayList<>();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsReservacion as reservacion "
                    + "left join fetch reservacion.smsCategoriasServicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadInicio as CiudadInicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadDestino as CiudadDestino "
                    + "left join fetch reservacion.smsEmpleado as empleado "
                    + "left join fetch empleado.smsUsuario "
                    + "left join fetch empleado.smsProveedor "
                    + "left join fetch reservacion.smsEstado as estado "
                    + "left join fetch reservacion.smsServicios as servicio "
                    + "left join fetch reservacion.smsUsuario as cliente "
                    + "left join fetch servicio.smsMercado as mercado "
                    + "left join fetch reservacion.smsVehiculo as vehiculo "
                    + "left join fetch vehiculo.smsReferencia as referencia "
                    + "left join fetch vehiculo.smsColor "
                    + "left join fetch referencia.smsMarca where "
                    + "mercado.mercadoNombre = '" + mercado + "' "
                    + "order by reservacion.idReservacion desc");
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
    public List<SmsReservacion> filtrarReservacionSegunClienteSegunMercado(String valor, String mercado) {
        Session session = null;
        List<SmsReservacion> reservas = new ArrayList<>();

        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsReservacion as reservacion "
                    + "left join fetch reservacion.smsCategoriasServicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadInicio as CiudadInicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadDestino as CiudadDestino "
                    + "left join fetch reservacion.smsEmpleado as empleado "
                    + "left join fetch empleado.smsUsuario "
                    + "left join fetch empleado.smsProveedor "
                    + "left join fetch reservacion.smsEstado as estado "
                    + "left join fetch reservacion.smsServicios as servicio "
                    + "left join fetch servicio.smsMercado as mercado "
                    + "left join fetch reservacion.smsUsuario as cliente "
                    + "left join fetch reservacion.smsVehiculo as vehiculo "
                    + "left join fetch vehiculo.smsReferencia as referencia "
                    + "left join fetch vehiculo.smsColor "
                    + "left join fetch referencia.smsMarca "
                    + "where mercado.mercadoNombre = '" + mercado + "' and "
                    + "cliente.usuarioCc LIKE '%" + valor + "%' or "
                    + "cliente.usuarioNombre LIKE '%" + valor + "%' or "
                    + "cliente.usuarioEmail LIKE '" + valor + "' order by reservacion.idReservacion desc");
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
    public List<SmsReservacion> mostrarReservacionClienteSegunMercado(SmsUsuario usuario, String mercado) {
        Session session = null;
        List<SmsReservacion> resevacionesHechas = null;

        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsReservacion as reservacion "
                    + "left join fetch reservacion.smsCategoriasServicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadInicio as CiudadInicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadDestino as CiudadDestino "
                    + "left join fetch reservacion.smsEmpleado as empleado "
                    + "left join fetch empleado.smsUsuario "
                    + "left join fetch empleado.smsProveedor "
                    + "left join fetch reservacion.smsEstado as estado "
                    + "left join fetch reservacion.smsServicios as servicio "
                    + "left join fetch servicio.smsMercado as mercado"
                    + "left join fetch reservacion.smsUsuario as cliente "
                    + "left join fetch reservacion.smsVehiculo as vehiculo "
                    + "left join fetch vehiculo.smsReferencia as referencia "
                    + "left join fetch vehiculo.smsColor "
                    + "left join fetch referencia.smsMarca where "
                    + "mercado.mercadoNombre = '" + mercado + "' and "
                    + "cliente.idUsuario = '" + usuario.getIdUsuario() + "' "
                    + "order by reservacion.idReservacion desc");
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
    public List<SmsReservacion> mostrarReservacionConductoresSegunMercado(SmsEmpleado conductor, String mercado) {
        Session session = null;
        List<SmsReservacion> resevacionesHechas = null;

        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsReservacion as reservacion "
                    + "left join fetch reservacion.smsCategoriasServicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadInicio as CiudadInicio "
                    + "left join fetch reservacion.smsCiudadByIdCiudadDestino as CiudadDestino "
                    + "left join fetch reservacion.smsEmpleado as empleado "
                    + "left join fetch empleado.smsUsuario left join fetch empleado.smsProveedor "
                    + "left join fetch reservacion.smsEstado as estado "
                    + "left join fetch reservacion.smsServicios as servicio "
                    + "left join fetch servicio.smsMercado as mercado "
                    + "left join fetch reservacion.smsUsuario as cliente "
                    + "left join fetch reservacion.smsVehiculo as vehiculo "
                    + "left join fetch vehiculo.smsReferencia as referencia "
                    + "left join fetch vehiculo.smsColor "
                    + "left join fetch referencia.smsMarca where "
                    + "mercado.mercadoNombre = '" + mercado + "' and "
                    + "empleado.idEmpleado = '" + conductor.getIdEmpleado() + "' order by reservacion.idReservacion desc");
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

}
