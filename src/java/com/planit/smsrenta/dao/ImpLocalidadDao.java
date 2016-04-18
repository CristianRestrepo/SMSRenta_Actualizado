/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsCiudad;
import com.planit.smsrenta.modelos.SmsLocalidad;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Desarrollo_Planit
 */
public class ImpLocalidadDao implements ILocalidadDao {

    SessionFactory sessions = NewHibernateUtil.getSessionFactory();
    
    @Override
    public List<SmsLocalidad> consultarLocalidades() {
        Session session = null;
        List<SmsLocalidad> localidades = new ArrayList<>();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsLocalidad as localidad left join fetch localidad.smsCiudad");
            localidades = (List<SmsLocalidad>) query.list();

        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return localidades;
    }

    @Override
    public SmsLocalidad consultarLocalidad(SmsLocalidad localidad) {
        Session session = null;
        SmsLocalidad localidades = new SmsLocalidad();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsLocalidad as localidad "
                    + "left join fetch localidad.smsCiudad "
                    + "where localidad.idLocalidad = '" + localidad.getIdLocalidad() + "' or "
                    + "localidad.localidadNombre = '" + localidad.getLocalidadNombre() + "'");
            localidades = (SmsLocalidad) query.list().get(0);

        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return localidades;
    }

    @Override
    public List<SmsLocalidad> consultarLocalidadesSegunCiudad(SmsCiudad ciudad) {
        Session session = null;
        List<SmsLocalidad> localidades = new ArrayList<>();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsLocalidad as localidad left join fetch localidad.smsCiudad as ciudad where ciudad.ciudadNombre = '" + ciudad.getCiudadNombre() + "'");
            localidades = (List<SmsLocalidad>) query.list();

        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return localidades;
    }

}
