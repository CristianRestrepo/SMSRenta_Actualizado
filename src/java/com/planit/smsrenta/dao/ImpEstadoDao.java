/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsEstado;
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
public class ImpEstadoDao implements IEstadoDao {

    SessionFactory sessions = NewHibernateUtil.getSessionFactory();
    
    @Override
    public List<SmsEstado> consultarEstados() {
        List<SmsEstado> estados = new ArrayList<>();
        Session session = null;
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsEstado");
            estados = (List<SmsEstado>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return estados;
    }

    @Override
    public SmsEstado consultarEstado(SmsEstado estado) {
        SmsEstado estados = new SmsEstado();
        Session session = null;
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsEstado as estado "
                    + "where estado.estadoNombre = '" + estado.getEstadoNombre() + "'");
            estados = (SmsEstado) query.list().get(0);
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return estados;
    }

}
