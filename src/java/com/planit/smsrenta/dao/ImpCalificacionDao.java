/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsCalificacion;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Desarrollo_Planit
 */
public class ImpCalificacionDao implements ICalificacionDao {

    SessionFactory sessions = NewHibernateUtil.getSessionFactory();

    @Override
    public List<SmsCalificacion> mostrarCalificaciones() {
        Session session = null;
        List<SmsCalificacion> calificaciones = new ArrayList<>();

        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsCalificacion as calificacion "
                    + "left join fetch calificacion.smsReservacion as reservacion "
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
                    + "order by calificacion.idCalificacion desc");
            calificaciones = (List<SmsCalificacion>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return calificaciones;
    }

    @Override
    public void registrarCalificacion(SmsCalificacion calificacion) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.save(calificacion);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getMessage();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void modificarCalificacion(SmsCalificacion calificacion) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.update(calificacion);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getMessage();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void eliminarCalificacion(SmsCalificacion calificacion) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.delete(calificacion);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getMessage();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<SmsCalificacion> filtrarCalificaciones(String valor) {
        Session session = null;
        List<SmsCalificacion> calificaciones = new ArrayList<>();

        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsCalificacion as calificacion "
                    + "left join fetch calificacion.smsReservacion as reservacion "
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
                    + "where cliente.usuarioCc LIKE '%" + valor + "%' or "
                    + "cliente.usuarioNombre LIKE '%" + valor + "%' or "
                    + "cliente.usuarioEmail LIKE '%" + valor + "%' or "
                    + "calificacion.calificacionCalidadServicio LIKE '%" + valor + "%' or "
                    + "reservacion.idReservacion LIKE '%" + valor + "%' "
                    + "order by calificacion.idCalificacion desc");
            calificaciones = (List<SmsCalificacion>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return calificaciones;
    }

}
