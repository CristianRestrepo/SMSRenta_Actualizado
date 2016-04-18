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
            Query query = session.createQuery("from SmsCalificacion");
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

}
