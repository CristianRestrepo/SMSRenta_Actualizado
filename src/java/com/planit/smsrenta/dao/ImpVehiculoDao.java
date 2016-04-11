/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsCiudad;
import com.planit.smsrenta.modelos.SmsProveedor;
import com.planit.smsrenta.modelos.SmsVehiculo;
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
                    + " left join fetch vehiculo.smsProveedor as proveedor left join fetch vehiculo.smsReferencia as referencia left join fetch vehiculo.smsEmpleados where vehiculo.vehPlaca = '" + vehiculo.getVehPlaca() + "'");
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
    public List<SmsVehiculo> consultarVehiculosDisponibles(String fechaInicio, String fechaLlegada, String horaInicio, String horaLlegada, String ciudad, String espacioInicio, String espacioLlegada, String mercado) {
        Session session = null;
        List<SmsVehiculo> vehiculos = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsVehiculo as vehiculo left join fetch vehiculo.smsCategoria as categoria left join fetch vehiculo.smsCiudad "
                    + "left join fetch vehiculo.smsProveedor as proveedor left join fetch vehiculo.smsReferencia as referencia left join fetch referencia.smsMarca left join fetch vehiculo.smsEstado left join fetch vehiculo.smsColor "
                    + "where categoria in(select categoria from SmsMercado as mercado left outer join mercado.smsCategorias as categoria where mercado.mercadoNombre = '" + mercado + "') and "
                    + "vehiculo.smsCiudad.ciudadNombre = '" + ciudad + "' and "
                    + "not exists(from SmsReservacion as reservacion where "
                    + "reservacion.reservacionFechaInicio = '" + fechaInicio + "' and "
                    + "reservacion.reservacionFechaLlegada = '" + fechaLlegada + "' and "
                    + "reservacion.reservacionHoraLlegada = '" + horaLlegada + "' and "
                    + "reservacion.reservacionHoraInicio = '" + horaInicio + "' and "
                    + "reservacion.smsVehiculo.idVehiculo = vehiculo.idVehiculo) "
                    + "and "
                    + "(('" + fechaInicio + "' <> '" + fechaLlegada + "' and "
                    + "not exists(from SmsReservacion as reservacion where reservacion.smsVehiculo.idVehiculo = vehiculo.idVehiculo and "
                    + "(reservacion.reservacionFechaInicio >= '" + fechaInicio + "' and "
                    + "reservacion.reservacionFechaLlegada <= '" + fechaLlegada + "')) "
                    + "and "
                    + "not exists(from SmsReservacion as reservacion where reservacion.smsVehiculo.idVehiculo = vehiculo.idVehiculo and reservacion.reservacionFechaLlegada = '" + fechaInicio + "' and reservacion.reservacionHoraLlegada > '" + horaInicio + "') "
                    + "and "
                    + "not exists(from SmsReservacion as reservacion where reservacion.smsVehiculo.idVehiculo = vehiculo.idVehiculo and reservacion.reservacionFechaInicio = '" + fechaLlegada + "' and reservacion.reservacionHoraInicio < '" + horaLlegada + "') "
                    + "and "
                    + "not exists(from SmsReservacion as reservacion where reservacion.smsVehiculo.idVehiculo = vehiculo.idVehiculo and reservacion.reservacionFechaInicio = '" + fechaInicio + "' and (reservacion.reservacionHoraInicio >= '" + horaInicio + "' or reservacion.reservacionHoraLlegada >= '" + horaInicio + "'))"
                    + ")"
                    + "or "
                    + "('" + fechaInicio + "' = '" + fechaLlegada + "' and "
                    + "not exists(from SmsReservacion as reservacion where reservacion.smsVehiculo.idVehiculo = vehiculo.idVehiculo and "
                    + "(reservacion.reservacionHoraInicio >= '" + horaInicio + "' or "
                    + "reservacion.reservacionHoraLlegada <= '" + horaLlegada + "') and reservacion.reservacionFechaInicio = '" + fechaInicio + "')"
                    + "or "
                    + "not exists(from SmsReservacion as reservacion where reservacion.smsVehiculo.idVehiculo = vehiculo.idVehiculo and reservacion.reservacionFechaLlegada = '" + fechaInicio + "' and reservacion.reservacionHoraLlegada > '" + horaInicio + "') "
                    + "or "
                    + "not exists(from SmsReservacion as reservacion where reservacion.smsVehiculo.idVehiculo = vehiculo.idVehiculo and reservacion.reservacionFechaInicio = '" + fechaLlegada + "' and reservacion.reservacionHoraInicio < '" + horaLlegada + "') "
                    + "))");
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
    public List<SmsVehiculo> filtrarVehiculosDisponibles(String fechaInicio, String fechaLlegada, String horaInicio, String horaLlegada, String ciudad, String categoria, String espacioInicio, String espacioLlegada, String mercado) {
        Session session = null;
        List<SmsVehiculo> vehiculos = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsVehiculo as vehiculo left join fetch vehiculo.smsCategoria as categoria left join fetch vehiculo.smsCiudad "
                    + "left join fetch vehiculo.smsProveedor as proveedor left join fetch vehiculo.smsReferencia as referencia left join fetch referencia.smsMarca left join fetch vehiculo.smsEstado left join fetch vehiculo.smsColor "
                    + "where categoria in(select categoria from SmsMercado as mercado left outer join mercado.smsCategorias as categoria where mercado.mercadoNombre = '" + mercado + "') and " 
                    + "vehiculo.smsCiudad.ciudadNombre = '" + ciudad + "' and categoria.categoriaNombre = '" + categoria + "' and "
                    + "not exists(from SmsReservacion as reservacion where "
                    + "reservacion.reservacionFechaInicio = '" + fechaInicio + "' and "
                    + "reservacion.reservacionFechaLlegada = '" + fechaLlegada + "' and "
                    + "reservacion.reservacionHoraLlegada = '" + horaLlegada + "' and "
                    + "reservacion.reservacionHoraInicio = '" + horaInicio + "' and "
                    + "reservacion.smsVehiculo.idVehiculo = vehiculo.idVehiculo) "
                    + "and "
                    + "(('" + fechaInicio + "' <> '" + fechaLlegada + "' and "
                    + "not exists(from SmsReservacion as reservacion where reservacion.smsVehiculo.idVehiculo = vehiculo.idVehiculo and "
                    + "(reservacion.reservacionFechaInicio >= '" + fechaInicio + "' and "
                    + "reservacion.reservacionFechaLlegada <= '" + fechaLlegada + "')) "
                    + "and "
                    + "not exists(from SmsReservacion as reservacion where reservacion.smsVehiculo.idVehiculo = vehiculo.idVehiculo and reservacion.reservacionFechaLlegada = '" + fechaInicio + "' and reservacion.reservacionHoraLlegada > '" + horaInicio + "') "
                    + "and "
                    + "not exists(from SmsReservacion as reservacion where reservacion.smsVehiculo.idVehiculo = vehiculo.idVehiculo and reservacion.reservacionFechaInicio = '" + fechaLlegada + "' and reservacion.reservacionHoraInicio < '" + horaLlegada + "') "
                    + "and "
                    + "not exists(from SmsReservacion as reservacion where reservacion.smsVehiculo.idVehiculo = vehiculo.idVehiculo and reservacion.reservacionFechaInicio = '" + fechaInicio + "' and (reservacion.reservacionHoraInicio >= '" + horaInicio + "' or reservacion.reservacionHoraLlegada >= '" + horaInicio + "'))"
                    + ")"
                    + "or "
                    + "('" + fechaInicio + "' = '" + fechaLlegada + "' and "
                    + "not exists(from SmsReservacion as reservacion where reservacion.smsVehiculo.idVehiculo = vehiculo.idVehiculo and "
                    + "(reservacion.reservacionHoraInicio >= '" + horaInicio + "' or "
                    + "reservacion.reservacionHoraLlegada <= '" + horaLlegada + "') and reservacion.reservacionFechaInicio = '" + fechaInicio + "')"
                    + "or "
                    + "not exists(from SmsReservacion as reservacion where reservacion.smsVehiculo.idVehiculo = vehiculo.idVehiculo and reservacion.reservacionFechaLlegada = '" + fechaInicio + "' and reservacion.reservacionHoraLlegada > '" + horaInicio + "') "
                    + "or "
                    + "not exists(from SmsReservacion as reservacion where reservacion.smsVehiculo.idVehiculo = vehiculo.idVehiculo and reservacion.reservacionFechaInicio = '" + fechaLlegada + "' and reservacion.reservacionHoraInicio < '" + horaLlegada + "') "
                    + "))");
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

    @Override
    public List<SmsVehiculo> consultarVehiculosSegunProveedor(SmsProveedor proveedor) {
        Session session = null;
        List<SmsVehiculo> vehiculos = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsVehiculo as vehiculo left join fetch vehiculo.smsCategoria left join fetch vehiculo.smsCiudad "
                    + "left join fetch vehiculo.smsProveedor as proveedor left join fetch vehiculo.smsReferencia as referencia left join fetch referencia.smsMarca left join fetch vehiculo.smsEstado left join fetch vehiculo.smsColor where "
                    + "proveedor.proveedorRazonSocial = '" + proveedor.getProveedorRazonSocial() + "'");
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
    public List<SmsVehiculo> filtrarVehiculoSegunProveedor(String valor,SmsProveedor proveedor) {
        Session session = null;
        List<SmsVehiculo> vehiculos = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsVehiculo as vehiculo left join fetch vehiculo.smsCategoria left join fetch vehiculo.smsCiudad as ciudad "
                    + "left join fetch vehiculo.smsProveedor as proveedor left join fetch vehiculo.smsReferencia as referencia left join fetch referencia.smsMarca left join fetch vehiculo.smsEstado left join fetch vehiculo.smsColor where vehiculo.vehPlaca LIKE '%" + valor + "%' and proveedor.proveedorRazonSocial = '" + proveedor.getProveedorRazonSocial() + "'");
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
