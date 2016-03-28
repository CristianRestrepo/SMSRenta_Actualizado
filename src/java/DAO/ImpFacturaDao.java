/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.SmsFactura;
import Modelo.SmsReservacion;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author CristianCamilo
 */
public class ImpFacturaDao implements IFacturaDao {

    private FacesMessage message;

    @Override
    public List<SmsFactura> consultarFacturas() {
        Session session = null;
        List<SmsFactura> facturas = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsFactura as factura left join fetch factura.smsReservacion");
            facturas = (List<SmsFactura>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return facturas;
    }

    @Override
    public void registrarFactura(SmsFactura factura) {
        boolean bandera = false;
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(factura);
            bandera = true;
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getMessage();
            session.getTransaction().rollback();
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Imposible registrar la factura", null);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        if (!bandera) {
            FacesContext.getCurrentInstance().addMessage(null, message);

        }
    }

    @Override
    public void eliminarFactura(SmsFactura factura) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(factura);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getMessage();
            session.getTransaction().rollback();
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Imposible eliminar la factura", null);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    @Override
    public List<SmsFactura> consultarFacturaSegunReservacion(SmsReservacion reservacion) {
        Session session = null;
        List<SmsFactura> facturas = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsFactura as factura left join fetch factura.smsReservacion as reservacion where reservacion.idReservacion = '" + reservacion.getIdReservacion() + "'");
            facturas = (List<SmsFactura>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return facturas;
    }

}
