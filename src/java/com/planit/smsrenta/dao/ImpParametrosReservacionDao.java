/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsMercado;
import com.planit.smsrenta.modelos.SmsParametrosReservacion;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Desarrollo_Planit
 */
public class ImpParametrosReservacionDao implements IParametrosReservacionDao {

    private FacesMessage message;
    SessionFactory sessions = NewHibernateUtil.getSessionFactory();

    @Override
    public void registrarParametroReservacion(SmsParametrosReservacion parametro) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.save(parametro);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Parametro registrado", null);
        } catch (HibernateException e) {
            e.getMessage();
            session.getTransaction().rollback();
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Imposible registrar Parametro de reservacion", null);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    @Override
    public void modificarParametroReservacion(SmsParametrosReservacion parametro) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.update(parametro);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Parametro modificado", null);
        } catch (HibernateException e) {
            e.getMessage();
            session.getTransaction().rollback();
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Imposible modificar Parametro de reservacion", null);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    @Override
    public void eliminarParametroReservacion(SmsParametrosReservacion parametro) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.delete(parametro);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Parametro eliminado", null);
        } catch (HibernateException e) {
            e.getMessage();
            session.getTransaction().rollback();
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Imposible eliminar Parametro de reservacion", null);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    @Override
    public SmsParametrosReservacion consultarParametroReservacion(SmsParametrosReservacion parametro) {
        Session session = null;
        SmsParametrosReservacion parametroReservacion = new SmsParametrosReservacion();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsParametrosReservacion as parametro "
                    + "left join fetch parametro.smsMercado as mercado where "
                    + "mercado.mercadoNombre = '" + parametro.getSmsMercado().getMercadoNombre() + "' or "
                    + "parametro.idParametro = '" + parametro.getIdParametro() + "'");
            if (!query.list().isEmpty()) {
                parametroReservacion = (SmsParametrosReservacion) query.list().get(0);
            }
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return parametroReservacion;
    }

    @Override
    public List<SmsParametrosReservacion> consultarParametrosReservacion() {
        Session session = null;
        List<SmsParametrosReservacion> parametrosReservacion = new ArrayList<>();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsParametrosReservacion as parametro "
                    + "left join fetch parametro.smsMercado as mercado");
            if (!query.list().isEmpty()) {
                parametrosReservacion = (List<SmsParametrosReservacion>) query.list();
            }
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return parametrosReservacion;
    }

    @Override
    public List<SmsParametrosReservacion> filtrarParametrosReservacion(String valor) {
        Session session = null;
        List<SmsParametrosReservacion> parametrosReservacion = new ArrayList<>();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsParametrosReservacion as parametro "
                    + "left join fetch parametro.smsMercado as mercado where "
                    + "mercado.mercadoNombre LIKE '%" + valor + "%' or "
                    + "parametro.parametroValorIncremento LIKE '%" + valor + "%'");
            if (!query.list().isEmpty()) {
                parametrosReservacion = (List<SmsParametrosReservacion>) query.list();
            }
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return parametrosReservacion;
    }

    @Override
    public SmsParametrosReservacion consultarParametroSegunMercado(SmsMercado mercado) {
       Session session = null;
        SmsParametrosReservacion parametroReservacion = new SmsParametrosReservacion();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsParametrosReservacion as parametro "
                    + "left join fetch parametro.smsMercado as mercado where "
                    + "mercado.idMercado = '" + mercado.getIdMercado() + "'");
            if (!query.list().isEmpty()) {
                parametroReservacion = (SmsParametrosReservacion) query.list().get(0);
            }
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return parametroReservacion;
    }

}
