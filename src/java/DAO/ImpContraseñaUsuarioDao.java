/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.SmsContraseñaUsuario;
import Modelo.SmsUsuario;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Desarrollo_Planit
 */
public class ImpContraseñaUsuarioDao implements IContraseñaUsuarioDao {

    @Override
    public void registrarContraseñaUsuario(SmsContraseñaUsuario contraUsuario) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(contraUsuario);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getMessage();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void modificarContraseñaUsuario(SmsContraseñaUsuario contraUsuario) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(contraUsuario);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getMessage();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<SmsContraseñaUsuario> consultarContraseñaUsuario(SmsUsuario usuario) {
        Session session = null;
        List<SmsContraseñaUsuario> contraseñas = new ArrayList<>();

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsContraseñaUsuario as contraseña where contraseña.smsUsuario.idUsuario = '" + usuario.getIdUsuario() + "'");
            contraseñas = (List<SmsContraseñaUsuario>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return contraseñas;
    }

}
