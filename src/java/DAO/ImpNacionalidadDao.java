/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.SmsNacionalidad;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Desarrollo_Planit
 */
public class ImpNacionalidadDao implements INacionalidadDao {

    @Override
    public List<SmsNacionalidad> consultarNacionalidades() {
        List<SmsNacionalidad> nacionalidades = new ArrayList<>();
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsNacionalidad");
            nacionalidades = (List<SmsNacionalidad>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return nacionalidades;
    }

    @Override
    public List<SmsNacionalidad> consultarNacionalidad(SmsNacionalidad nacionalidad) {
        List<SmsNacionalidad> nacionalidades = new ArrayList<>();
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsNacionalidad as nacionalidad where nacionalidad.nacionalidadNombre = '" + nacionalidad.getNacionalidadNombre() + "' ");
            nacionalidades = (List<SmsNacionalidad>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return nacionalidades;
    }

}
