/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsCalificacion;
import com.planit.smsrenta.modelos.SmsReferencia;
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
public class ImpReferenciaDao implements IReferenciaDao {

    private FacesMessage message;    
    SessionFactory sessions = NewHibernateUtil.getSessionFactory();
    
    @Override
    public List<SmsReferencia> mostrarReferencias() {
        Session session = null;
        List<SmsReferencia> referencias = null;
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsReferencia as referencia left join fetch referencia.smsMarca");
            referencias = (List<SmsReferencia>) query.list();

        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return referencias;
    }

    @Override
    public void registrarReferencia(SmsReferencia referencia) {
        Session session = null;
        try{
            session = sessions.openSession();
            session.beginTransaction();
            session.save(referencia);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Referencia registrada", "" + referencia.getReferenciaNombre() );
        }catch(HibernateException e){
            e.getMessage();
            session.getTransaction().rollback();
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Imposible realizar la operacion", null);
        }finally{
            if(session != null){
                session.close();
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    @Override
    public void modificarReferencia(SmsReferencia referencia) {
       Session session = null;
        try{
            session = sessions.openSession();
            session.beginTransaction();
            session.update(referencia);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Referencia modificada", "" + referencia.getReferenciaNombre() );
        }catch(HibernateException e){
            e.getMessage();
            session.getTransaction().rollback();
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Imposible realizar la operacion", null);
        }finally{
            if(session != null){
                session.close();
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    @Override
    public void eliminarReferencia(SmsReferencia referencia) {
        Session session = null;
        try{
            session = sessions.openSession();
            session.beginTransaction();
            session.delete(referencia);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Referencia eliminada", "" + referencia.getReferenciaNombre() );
        }catch(HibernateException e){
            e.getMessage();
            session.getTransaction().rollback();
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Imposible realizar la operacion", null);
        }finally{
            if(session != null){
                session.close();
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    @Override
    public SmsReferencia consultarReferencias(SmsReferencia referencia) {
        Session session = null;
        SmsReferencia referencias = new SmsReferencia();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsReferencia as referencia where"
                    + " referencia.referenciaNombre='" + referencia.getReferenciaNombre() + "'");
            referencias = (SmsReferencia) query.list().get(0);

        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return referencias;
    }

    @Override
    public List<SmsReferencia> filtrarReferencias(String dato) {
Session session = null;
        List<SmsReferencia> referencias = new ArrayList<>();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsReferencia as referencia left join fetch referencia.smsMarca where referencia.referenciaNombre LIKE '%" + dato + "%'");
            referencias = (List<SmsReferencia>) query.list();

        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return referencias;    }
    
    
}
