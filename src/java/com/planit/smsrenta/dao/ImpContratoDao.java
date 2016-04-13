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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.query.JRJdbcQueryExecuterFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Desarrollo_Planit
 */
public class ImpContratoDao implements IContratoDao {

    @Override
    public List<SmsContrato> consultarContratos() {
        Session session = null;
        List<SmsContrato> contratos = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
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
    public List<SmsContrato> consultarContratoSegunReservacion(SmsReservacion reservacion) {
        Session session = null;
        List<SmsContrato> contratos = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsContrato as contrato "
                    + "left join fetch contrato.smsReservacion as reservacion "
                    + "where reservacion.idReservacion = '" + reservacion.getIdReservacion() + "'");
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
    public void registrarContrato(SmsContrato contrato) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
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

}
