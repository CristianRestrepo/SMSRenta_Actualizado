/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.SmsDepartamento;
import Modelo.SmsTipoLugar;
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

    @Override
    public void registrarTipoLugar(SmsTipoLugar tipoLugar) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(tipoLugar);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Tipo de lugar registrado", "" + tipoLugar.getTipoLugarNombre());

        } catch (HibernateException e) {
            e.getMessage();
            session.getTransaction().rollback();
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Imposible realizar la operacion", null);

        } finally {
            if (session != null) {
                session.close();
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    @Override
    public void modificarTipoLugar(SmsTipoLugar tipoLugar) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(tipoLugar);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Tipo de lugar modificado", "" + tipoLugar.getTipoLugarNombre());

        } catch (HibernateException e) {
            e.getMessage();
            session.getTransaction().rollback();
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Imposible realizar la operacion", null);

        } finally {
            if (session != null) {
                session.close();
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    @Override
    public void eliminarTipoLugar(SmsTipoLugar tipoLugar) {

        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(tipoLugar);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Tipo de lugar eliminado", "" + tipoLugar.getTipoLugarNombre());

        } catch (HibernateException e) {
            e.getMessage();
            session.getTransaction().rollback();
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Imposible realizar la operacion", null);

        } finally {
            if (session != null) {
                session.close();
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
