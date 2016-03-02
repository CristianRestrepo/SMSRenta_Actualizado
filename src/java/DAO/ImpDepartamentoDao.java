/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.SmsDepartamento;
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
public class ImpDepartamentoDao implements IDepartamentoDao {

    private FacesMessage message;

    @Override
    public List<SmsDepartamento> consultarDepartamentos() {
        List<SmsDepartamento> departamentos = new ArrayList<>();
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsDepartamento");
            departamentos = (List<SmsDepartamento>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return departamentos;
    }

    @Override
    public List<SmsDepartamento> consultarDepartamento(SmsDepartamento departamento) {
        List<SmsDepartamento> departamentos = new ArrayList<>();
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsDepartamento as departamento where departamento.departamentoNombre = '" + departamento.getDepartamentoNombre() + "'");
            departamentos = (List<SmsDepartamento>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return departamentos;

    }

    @Override
    public List<SmsDepartamento> filtrarDepartamentos(String valor) {
        List<SmsDepartamento> departamentos = new ArrayList<>();
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsDepartamento as departamento where departamento.departamentoNombre LIKE '%" + valor + "%'");
            departamentos = (List<SmsDepartamento>) query.list();
        } catch (HibernateException e) {
            e.getMessage();

        } finally {
            if (session != null) {
                session.close();
            }
        }
        return departamentos;
    }

    @Override
    public void registrarDepartamento(SmsDepartamento departamento) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(departamento);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Departamento registrado", "" + departamento.getDepartamentoNombre());

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
    public void modificarDepartamento(SmsDepartamento departamento) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(departamento);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Departamento modificado", "" + departamento.getDepartamentoNombre());

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
    public void eliminarDepartamento(SmsDepartamento departamento) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(departamento);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Departamento eliminado", "" + departamento.getDepartamentoNombre());

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
