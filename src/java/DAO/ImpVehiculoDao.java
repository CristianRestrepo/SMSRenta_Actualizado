/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.SmsCiudad;
import Modelo.SmsVehiculo;
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
public class ImpVehiculoDao implements IVehiculoDao {

    private FacesMessage message;

    @Override
    public List<SmsVehiculo> mostrarVehiculo() {
        Session session = null;
        List<SmsVehiculo> vehiculos = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsVehiculo as vehiculo left join fetch vehiculo.smsCategoria left join fetch vehiculo.smsCiudad "
                    + "left join fetch vehiculo.smsProveedor as proveedor left join fetch vehiculo.smsReferencia as referencia left join fetch referencia.smsMarca left join fetch vehiculo.smsEstado left join fetch vehiculo.smsColor");
            vehiculos = (List<SmsVehiculo>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return vehiculos;
    }

    @Override
    public void registrarVehiculo(SmsVehiculo vehiculo) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(vehiculo);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Vehiculo registrado", "" + vehiculo.getVehPlaca());
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
    public void modificarVehiculo(SmsVehiculo vehiculo) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(vehiculo);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Vehiculo modificado", "" + vehiculo.getVehPlaca());
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
    public void eliminarVehiculo(SmsVehiculo vehiculo) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(vehiculo);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Vehiculo eliminado", "" + vehiculo.getVehPlaca());
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
    public List<SmsVehiculo> consultarVehiculo(SmsVehiculo vehiculo) {
        Session session = null;
        List<SmsVehiculo> vehiculos = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsVehiculo as vehiculo left join fetch vehiculo.smsCategoria as categoria left join fetch vehiculo.smsCiudad as ciudad"
                    + " left join fetch vehiculo.smsProveedor as proveedor left join fetch vehiculo.smsReferencia as referencia where vehiculo.vehPlaca = '" + vehiculo.getVehPlaca() + "'");
            vehiculos = (List<SmsVehiculo>) query.list();

        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return vehiculos;
    }

    @Override
    public List<SmsVehiculo> consultarVehiculosDisponibles(String fechaInicio, String fechaLlegada, String horaInicio, String horaLlegada, String ciudad, String espacioInicio, String espacioLlegada) {
        Session session = null;
        List<SmsVehiculo> vehiculos = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsVehiculos as vehiculo where vehiculo.smsCiudad.ciudadNombre = '"+ciudad+"' and " +
                                                    "not exists(from SmsReservacion as reservacion where " +
                                                            "reservacion.reservacionFechaInicio = '"+fechaInicio+"' and " +
                                                            "reservacion.reservacionFechaLlegada = '"+fechaLlegada+"' and " +
                                                            "reservacion.reservacionHoraInicio = '"+horaInicio+"' and " +
                                                            "reservacion.reservacionHoraLlegada = '"+horaLlegada+"' and " +
                                                            "reservacion.smsVehiculo.idVehiculo = vehiculo.idVehiculo) " +
                                                            "and " +
                                                            "("
                                                                + "('"+fechaInicio+"' = '"+fechaLlegada+"' and not exists(from SmsReservacion as reservacion where " +
                                                                    "reservacion.smsVehiculo.idVehiculo = vehiculo.idVehiculo and " +
                                                                    "reservacion.reservacionFechaInicio = '"+fechaInicio+"' and " +
                                                                    "reservacion.reservacionFechaLlegada = '"+fechaLlegada+"' and " +
                                                                        "(reservacion.reservacionHoraInicio between '"+horaInicio+"' and '"+horaLlegada+"' " +
                                                                        "or " +
                                                                         "reservacion.reservacionHoraLlegada between '"+horaInicio+"' and '"+horaLlegada+"'))) " +
                                                            " or " +
                                                                "('"+fechaInicio+"' <> '"+fechaLlegada+"' and not exists(from SmsReservacion as reservacion where " +
                                                                    "reservacion.smsVehiculo.idVehiculo = vehiculo.idVehiculo and " +
                                                                    "("
                                                                        + "(reservacion.reservacionFechaInicio = '"+fechaInicio+"') or "
                                                                        + "(reservacion.reservacionFechaInicio between '"+fechaInicio+"' and '"+fechaLlegada+"')"
                                                                    + ") "
                                                                    + "and " +                    
                                                                    "("
                                                                     + "(reservacion.reservacionFechaLlegada = '"+fechaLlegada+"') or "
                                                                     + "(reservacion.reservacionFechaLlegada between '"+fechaInicio+"' and '"+fechaLlegada+"')"
                                                                  + ")"
                                                                  + "and " +
                                                                    "(reservacion.reservacionHoraInicio between '"+horaInicio+"' and '"+horaLlegada+"'" +
                                                                    "or " +
                                                                    "reservacion.reservacionHoraLlegada between '"+horaInicio+"' and '"+horaLlegada+"')))"
                                                         + ") "
                                                         + "group by vehiculo.idVehiculo");
            vehiculos = (List<SmsVehiculo>) query.list();

        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return vehiculos;
    }

    @Override
    public List<SmsVehiculo> consultarVehiculosCiudad(SmsCiudad ciudad) {
        Session session = null;
        List<SmsVehiculo> vehiculos = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsVehiculo as vehiculo left join fetch vehiculo.smsCiudad as ciudad left join fetch vehiculo.smsReferencia as referencia left join fetch referencia.smsMarca left join fetch vehiculo.smsEstado left join fetch vehiculo.smsColor where ciudad.ciudadNombre = '" + ciudad.getCiudadNombre() + "'");
            vehiculos = (List<SmsVehiculo>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return vehiculos;
    }

    @Override
    public List<SmsVehiculo> filtrarVehiculosDisponibles(String fechaInicio, String fechaLlegada, String horaInicio, String horaLlegada, String ciudad, String categoria, String espacioInicio, String espacioLlegada) {
        Session session = null;
        List<SmsVehiculo> vehiculos = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsVehiculos as vehiculo where vehiculo.smsCiudad.ciudadNombre = '"+ciudad+"' and " +
                                                    "vehiculo.smsCategoria.categoriaNombre = '" + categoria + "' and not exists(from SmsReservacion as reservacion where " +
                                                            "reservacion.reservacionFechaInicio = '"+fechaInicio+"' and " +
                                                            "reservacion.reservacionFechaLlegada = '"+fechaLlegada+"' and " +
                                                            "reservacion.reservacionHoraInicio = '"+horaInicio+"' and " +
                                                            "reservacion.reservacionHoraLlegada = '"+horaLlegada+"' and " +
                                                            "reservacion.smsVehiculo.idVehiculo = vehiculo.idVehiculo) " +
                                                            "and " +
                                                            "("
                                                                + "('"+fechaInicio+"' = '"+fechaLlegada+"' and not exists(from SmsReservacion as reservacion where " +
                                                                    "reservacion.smsVehiculo.idVehiculo = vehiculo.idVehiculo and " +
                                                                    "reservacion.reservacionFechaInicio = '"+fechaInicio+"' and " +
                                                                    "reservacion.reservacionFechaLlegada = '"+fechaLlegada+"' and " +
                                                                        "(reservacion.reservacionHoraInicio between '"+horaInicio+"' and '"+horaLlegada+"' " +
                                                                        "or " +
                                                                         "reservacion.reservacionHoraLlegada between '"+horaInicio+"' and '"+horaLlegada+"'))) " +
                                                            " or " +
                                                                "('"+fechaInicio+"' <> '"+fechaLlegada+"' and not exists(from SmsReservacion as reservacion where " +
                                                                    "reservacion.smsVehiculo.idVehiculo = vehiculo.idVehiculo and " +
                                                                    "("
                                                                        + "(reservacion.reservacionFechaInicio = '"+fechaInicio+"') or "
                                                                        + "(reservacion.reservacionFechaInicio between '"+fechaInicio+"' and '"+fechaLlegada+"')"
                                                                    + ") "
                                                                    + "and " +                    
                                                                    "("
                                                                     + "(reservacion.reservacionFechaLlegada = '"+fechaLlegada+"') or "
                                                                     + "(reservacion.reservacionFechaLlegada between '"+fechaInicio+"' and '"+fechaLlegada+"')"
                                                                  + ")"
                                                                  + "and " +
                                                                    "(reservacion.reservacionHoraInicio between '"+horaInicio+"' and '"+horaLlegada+"'" +
                                                                    "or " +
                                                                    "reservacion.reservacionHoraLlegada between '"+horaInicio+"' and '"+horaLlegada+"')))"
                                                         + ") "
                                                         + "group by vehiculo.idVehiculo");
            vehiculos = (List<SmsVehiculo>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return vehiculos;
    }

    @Override
    public List<SmsVehiculo> filtrarVehiculosCiudad(SmsCiudad ciudad, String categoria) {
        Session session = null;
        List<SmsVehiculo> vehiculos = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsVehiculo as vehiculo left join fetch vehiculo.smsCategoria left join fetch vehiculo.smsCiudad as ciudad "
                    + "left join fetch vehiculo.smsProveedor as proveedor left join fetch vehiculo.smsReferencia as referencia left join fetch referencia.smsMarca left join fetch vehiculo.smsEstado left join fetch vehiculo.smsColor where ciudad.ciudadNombre = '" + ciudad.getCiudadNombre() + "' and vehiculo.smsCategoria.categoriaNombre = '" + categoria + "'");
            vehiculos = (List<SmsVehiculo>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return vehiculos;
    }

}
