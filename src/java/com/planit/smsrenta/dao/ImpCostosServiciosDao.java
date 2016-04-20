/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsCategoria;
import com.planit.smsrenta.modelos.SmsCostosservicios;
import com.planit.smsrenta.modelos.SmsLugares;
import com.planit.smsrenta.modelos.SmsServicios;
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
 * @author CristianCamilo
 */
public class ImpCostosServiciosDao implements ICostosServiciosDao {

    private FacesMessage message;
    SessionFactory sessions = NewHibernateUtil.getSessionFactory();

    @Override
    public List<SmsCostosservicios> consultarCostos() {
        Session session = null;
        List<SmsCostosservicios> Costos = new ArrayList<>();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsCostosservicios as costo left join fetch costo.smsLugaresByIdLugarInicio as lugarInicio left join fetch lugarInicio.smsCiudad left join fetch costo.smsLugaresByIdLugarDestino as lugarDestino left join fetch lugarDestino.smsCiudad left join fetch costo.smsCategoria left join fetch costo.smsServicios as servicios left join fetch servicios.smsMercado as mercado");
            Costos = (List<SmsCostosservicios>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return Costos;
    }

    @Override
    public SmsCostosservicios consultarCostoServicio(SmsServicios servicio, SmsCategoria categoria) {
        Session session = null;
        SmsCostosservicios Costos = new SmsCostosservicios();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsCostosservicios as costo "
                    + "left join fetch costo.smsLugaresByIdLugarInicio as lugarInicio "
                    + "left join fetch lugarInicio.smsCiudad "
                    + "left join fetch costo.smsLugaresByIdLugarDestino as lugarDestino "
                    + "left join fetch lugarDestino.smsCiudad "
                    + "left join fetch costo.smsCategoria as categoria "
                    + "left join fetch costo.smsServicios as servicios "
                    + "where categoria.idCategoria = '" + categoria.getIdCategoria() + "' and "
                    + "servicios.idServicio = '" + servicio.getIdServicio() + "'");
            Costos = (SmsCostosservicios) query.list().get(0);
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return Costos;
    }

    @Override
    public SmsCostosservicios consultarCostoServicio(SmsServicios servicio) {
        Session session = null;
        SmsCostosservicios Costos = new SmsCostosservicios();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsCostosservicios as costo "
                    + "left join fetch costo.smsLugaresByIdLugarInicio as lugarInicio "
                    + "left join fetch lugarInicio.smsCiudad "
                    + "left join fetch costo.smsLugaresByIdLugarDestino as lugarDestino "
                    + "left join fetch lugarDestino.smsCiudad "
                    + "left join fetch costo.smsCategoria as categoria "
                    + "left join fetch costo.smsServicios as servicios "
                    + "where servicios = '" + servicio.getIdServicio() + "' ");
            Costos = (SmsCostosservicios) query.list().get(0);
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return Costos;
    }

    @Override
    public SmsCostosservicios consultarCostoServicioTraslado(SmsServicios servicio, SmsCategoria categoria, SmsLugares lugarInicio, SmsLugares lugarDestino) {
        Session session = null;
        SmsCostosservicios Costos = new SmsCostosservicios();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsCostosservicios as costo "
                    + "left join fetch costo.smsLugaresByIdLugarInicio as lugarInicio "
                    + "left join fetch lugarInicio.smsCiudad "
                    + "left join fetch costo.smsLugaresByIdLugarDestino as lugarDestino "
                    + "left join fetch lugarDestino.smsCiudad "
                    + "left join fetch costo.smsCategoria as categoria "
                    + "left join fetch costo.smsServicios as servicios where servicios.idServicio = '" + servicio.getIdServicio() + "' and "
                    + "categoria.idCategoria = '" + categoria.getIdCategoria() + "' and "
                    + "((lugarInicio.idLugar ='" + lugarInicio.getIdLugar() + "' and lugarDestino.idLugar = '" + lugarDestino.getIdLugar() + "') or (lugarInicio.idLugar ='" + lugarDestino.getIdLugar() + "' and lugarDestino.idLugar = '" + lugarInicio.getIdLugar() + "'))");
            Costos = (SmsCostosservicios) query.list().get(0);
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return Costos;
    }

    @Override
    public void registrarCostoServicio(SmsCostosservicios costo) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.save(costo);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Costo registrado", "");
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
    public void modificarCostoServicio(SmsCostosservicios costo) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.update(costo);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Costo modificado", "");
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
    public void eliminarCostoServicio(SmsCostosservicios costo) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.delete(costo);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Costo eliminado", "");
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
    public List<SmsCostosservicios> filtrarCostosServicios(String dato) {
        Session session = null;
        List<SmsCostosservicios> Costos = new ArrayList<>();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsCostosservicios as costo "
                    + "left join fetch costo.smsLugaresByIdLugarInicio as lugarInicio "
                    + "left join fetch lugarInicio.smsCiudad "
                    + "left join fetch costo.smsLugaresByIdLugarDestino as lugarDestino "
                    + "left join fetch lugarDestino.smsCiudad "
                    + "left join fetch costo.smsCategoria as categoria "
                    + "left join fetch costo.smsServicios as servicios "
                    + "where categoria.categoriaNombre LIKE '%" + dato + "%' or "
                    + "servicios.servicioNombre LIKE '%" + dato + "%' or "
                    + "lugarInicio.lugarNombre LIKE '%" + dato + "%' or "
                    + "lugarDestino.lugarNombre LIKE '%" + dato + "%'");
            Costos = (List<SmsCostosservicios>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return Costos;
    }

}
