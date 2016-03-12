/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.SmsColor;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Desarrollo_Planit
 */
public class ImpColorDao implements IColorDao {

    @Override
    public List<SmsColor> consultarColores() {
        List<SmsColor> colores = new ArrayList<>();
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from smsColor");
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
    public List<SmsColor> consultarColor(SmsColor color) {
        List<SmsColor> colores = new ArrayList<>();
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from smsColor as color where color.colorNombre = '" + color.getColorNombre() + "'");
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

}
