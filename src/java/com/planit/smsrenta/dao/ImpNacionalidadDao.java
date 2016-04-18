/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsNacionalidad;
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
public class ImpNacionalidadDao implements INacionalidadDao {

    SessionFactory sessions = NewHibernateUtil.getSessionFactory();
    
    @Override
    public List<SmsNacionalidad> consultarNacionalidades() {
        List<SmsNacionalidad> nacionalidades = new ArrayList<>();
        Session session = null;
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsNacionalidad");
            nacionalidades = (List<SmsNacionalidad>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return nacionalidades;
    }

    @Override
    public SmsNacionalidad consultarNacionalidad(SmsNacionalidad nacionalidad) {
        SmsNacionalidad nacionalidades = new SmsNacionalidad();
        Session session = null;
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsNacionalidad as nacionalidad "
                    + "where nacionalidad.nacionalidadNombre = '" + nacionalidad.getNacionalidadNombre() + "'");
            nacionalidades = (SmsNacionalidad) query.list().get(0);
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return nacionalidades;
    }

}
