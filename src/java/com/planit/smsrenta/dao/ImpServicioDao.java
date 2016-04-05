/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsMercado;
import com.planit.smsrenta.modelos.SmsServicios;
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
public class ImpServicioDao implements IServicioDao {

    private FacesMessage message;

    @Override
    public List<SmsServicios> mostrarServicios() {
        Session session = null;
        List<SmsServicios> servicios = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsServicios as servicios left join fetch servicios.smsCategoriasServicio as categoria left join fetch categoria.smsMercado");
            servicios = (List<SmsServicios>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return servicios;
    }

    @Override
    public void registrarServicio(SmsServicios servicio) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(servicio);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Servicio registrado", "" + servicio.getServicioNombre());
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
    public void modificarServicio(SmsServicios servicio) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(servicio);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Servicio modificado", "" + servicio.getServicioNombre());
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
    public void eliminarServicio(SmsServicios servicio) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(servicio);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Servicio eliminado", "" + servicio.getServicioNombre());
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
    public List<SmsServicios> filtrarServicios(String dato) {
        Session session = null;
        List<SmsServicios> servicios = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsServicios as servicio left join fetch servicio.smsCategoriasServicio as cat left join fetch cat.smsMercado where servicio.serviciosNombre LIKE '%" + dato + "%' or servicio.servicioDescripcion LIKE '%" + dato + "%'");
            servicios = (List<SmsServicios>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return servicios;
    }

    @Override
    public List<SmsServicios> ConsultarServicio(SmsServicios servicio) {
        Session session = null;
        List<SmsServicios> servicios = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsServicios as servicio left join fetch servicio.smsCategoriasServicio as cat left join fetch cat.smsMercado where servicio.servicioNombre = '" + servicio.getServicioNombre() + "' or servicio.idServicio = '" + servicio.getIdServicio() + "'");
            servicios = (List<SmsServicios>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return servicios;
    }

    @Override
    public List<SmsServicios> ConsultarServicioSegunMercado(SmsMercado mercado) {
        Session session = null;
        List<SmsServicios> servicios = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsServicios as servicio left join fetch servicio.smsCategoriasServicio as cat left join fetch cat.smsMercado as mercado where mercado.idMercado = '" + mercado.getIdMercado() + "'");
            servicios = (List<SmsServicios>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return servicios;
    }

}
