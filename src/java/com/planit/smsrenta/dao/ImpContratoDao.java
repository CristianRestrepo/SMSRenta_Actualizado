/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsContrato;
import com.planit.smsrenta.modelos.SmsReservacion;
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
public class ImpContratoDao implements IContratoDao {

    SessionFactory sessions = NewHibernateUtil.getSessionFactory();

    @Override
    public List<SmsContrato> consultarContratos() {
        Session session = null;
        List<SmsContrato> contratos = new ArrayList<>();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsContrato as contrato "
                    + "left join fetch contrato.smsReservacion");
            contratos = (List<SmsContrato>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return contratos;
    }

    @Override
    public SmsContrato consultarContratoSegunReservacion(SmsReservacion reservacion) {
        Session session = null;
        SmsContrato contratos = new SmsContrato();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsContrato as contrato "
                    + "left join fetch contrato.smsReservacion as reservacion "
                    + "where reservacion.idReservacion = '" + reservacion.getIdReservacion() + "'");
            if (!query.list().isEmpty()) {
                contratos = (SmsContrato) query.list().get(0);
            }
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return contratos;
    }

    @Override
    public void registrarContrato(SmsContrato contrato) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.save(contrato);
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
    public int consultarMaxIndicativo() {
        Session session = null;
        int maximo = 0;
        try {
            session = sessions.openSession();
            Query query = session.createQuery("select MAX(contrato.contratoIndicativo) from SmsContrato as contrato");
            if (query.list().get(0) != null) {
                maximo = (int) query.list().get(0);
            }
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return maximo;
    }

}
