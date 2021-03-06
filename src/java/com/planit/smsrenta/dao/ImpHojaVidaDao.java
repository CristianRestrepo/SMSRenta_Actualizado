/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsHojavida;
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
public class ImpHojaVidaDao implements IHojaVidaDao {

    SessionFactory sessions = NewHibernateUtil.getSessionFactory();
    
    @Override
    public List<SmsHojavida> mostrarHojaVida() {
        Session session = null;
        List<SmsHojavida> HojasVida = new ArrayList<>();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsHojavida");
            HojasVida = (List<SmsHojavida>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return HojasVida;
    }

    @Override
    public void registrarHojaVida(SmsHojavida hojaVida) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.save(hojaVida);
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
    public void modificarHojaVida(SmsHojavida hojaVida) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.update(hojaVida);
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
    public void eliminarHojaVida(SmsHojavida hojaVida) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.delete(hojaVida);
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
    public SmsHojavida consultarHojaVida(SmsHojavida hojavida) {
        Session session = null;
        SmsHojavida HojasVida = new SmsHojavida();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsHojavida as hojavida "
                    + "where hojavida.hojaVidaNombre = '" + hojavida.getHojaVidaNombre() + "' or "
                    + "hojavida.hojaVidaRuta = '" + hojavida.getHojaVidaRuta() + "'");
            HojasVida = (SmsHojavida) query.list().get(0);
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return HojasVida;
    }
}

