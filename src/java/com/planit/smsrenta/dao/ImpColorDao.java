/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsColor;
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
public class ImpColorDao implements IColorDao {

    SessionFactory sessions = NewHibernateUtil.getSessionFactory();
    
    @Override
    public List<SmsColor> consultarColores() {
        List<SmsColor> colores = new ArrayList<>();
        Session session = null;
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsColor");
            colores = (List<SmsColor>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return colores;
    }

    @Override
    public SmsColor consultarColor(SmsColor color) {
        SmsColor colores = new SmsColor();
        Session session = null;
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsColor as color where "
                    + "color.colorNombre = '" + color.getColorNombre() + "'");
            colores = (SmsColor) query.list().get(0);
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return colores;
    }

}
