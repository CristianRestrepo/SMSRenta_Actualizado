/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsTipoDuracion;
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
public class ImpTipoDuracionDao implements ITipoDuracionDao {

    SessionFactory sessions = NewHibernateUtil.getSessionFactory();
    
    @Override
    public List<SmsTipoDuracion> consultarTiposDuracion() {
        Session session = null;
        List<SmsTipoDuracion> tipos = new ArrayList<>();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsTipoDuracion");
            tipos = (List<SmsTipoDuracion>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return tipos;
    }

    @Override
    public SmsTipoDuracion consultarTipoDuracion(SmsTipoDuracion tipo) {
        Session session = null;
        SmsTipoDuracion tipos = new SmsTipoDuracion();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsTipoDuracion as tipo where "
                    + "tipo.idTipoDuracion = '" + tipo.getIdTipoDuracion() + "' or "
                    + "tipo.tipoDuracionNombre = '" + tipo.getTipoDuracionNombre() + "'");
            tipos = (SmsTipoDuracion) query.list().get(0);
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return tipos;
    }

}
