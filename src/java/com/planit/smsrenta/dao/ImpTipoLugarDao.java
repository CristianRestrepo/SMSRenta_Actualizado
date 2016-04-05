/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsDepartamento;
import com.planit.smsrenta.modelos.SmsTipoLugar;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Desarrollo_Planit
 */
public class ImpTipoLugarDao implements ITipoLugarDao {

    private FacesMessage message;

    @Override
    public List<SmsTipoLugar> consultarTiposLugares() {
        List<SmsTipoLugar> tiposLugar = new ArrayList<>();
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsTipoLugar");
            tiposLugar = (List<SmsTipoLugar>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return tiposLugar;
    }

    @Override
    public List<SmsTipoLugar> consultarTipoLugar(SmsTipoLugar tipoLugar) {
        List<SmsTipoLugar> tiposLugar = new ArrayList<>();
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsTipoLugar as tipo where tipo.tipoLugarNombre = '" + tipoLugar.getTipoLugarNombre() + "'");
            tiposLugar = (List<SmsTipoLugar>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return tiposLugar;

    }

    @Override
    public List<SmsTipoLugar> filtrarTipoLugar(String valor) {
        List<SmsTipoLugar> tiposLugar = new ArrayList<>();
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsTipoLugar as tipo where tipo.tipoLugarNombre LIKE '%" + valor + "%'");
            tiposLugar = (List<SmsTipoLugar>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return tiposLugar;
    }

}
