/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsCategoria;
import com.planit.smsrenta.modelos.SmsMercado;
import com.planit.smsrenta.modelos.SmsProveedor;
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
public class ImpMercadoDao implements IMercadoDao {

    private FacesMessage message;
    SessionFactory sessions = NewHibernateUtil.getSessionFactory();

    @Override
    public List<SmsMercado> consultarMercados() {
        Session session = null;
        List<SmsMercado> mercados = new ArrayList<>();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsMercado as mercado");
            if (!query.list().isEmpty()) {
                mercados = (List<SmsMercado>) query.list();
            }
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return mercados;
    }

    @Override
    public void registrarMercado(SmsMercado mercado) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.save(mercado);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mercado registrado", "" + mercado.getMercadoNombre());
        } catch (Exception e) {
            e.getMessage();
            session.getTransaction().rollback();
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Imposible realizar operacion", "");

        } finally {
            if (session != null) {
                session.close();
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    @Override
    public void modificarMercado(SmsMercado mercado) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.update(mercado);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mercado actualizado", "" + mercado.getMercadoNombre());
        } catch (Exception e) {
            e.getMessage();
            session.getTransaction().rollback();
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Imposible realizar operacion", "");

        } finally {
            if (session != null) {
                session.close();
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    @Override
    public void eliminarMercado(SmsMercado mercado) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.delete(mercado);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mercado Eliminado", "" + mercado.getMercadoNombre());
        } catch (Exception e) {
            e.getMessage();
            session.getTransaction().rollback();
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Imposible realizar operacion", "");

        } finally {
            if (session != null) {
                session.close();
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    @Override
    public List<SmsMercado> filtrarMercados(String valor) {
        Session session = null;
        List<SmsMercado> mercados = new ArrayList<>();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsMercado as mercado where mercado.mercadoNombre LIKE '%" + valor + "%' or mercado.mercadoDescripcion LIKE '%" + valor + "%'");
            mercados = (List<SmsMercado>) query.list();

        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return mercados;
    }

    @Override
    public SmsMercado consultarMercadoConCategorias(SmsMercado mercado) {
        Session session = null;
        SmsMercado mercados = new SmsMercado();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsMercado as mercado "
                    + "left join fetch mercado.smsCategorias as categorias "
                    + "left join fetch categorias.smsMercados where mercado.mercadoNombre = '" + mercado.getMercadoNombre() + "'");
            mercados = (SmsMercado) query.list().get(0);

        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return mercados;
    }

    @Override
    public SmsMercado consultarMercadoConServicios(SmsMercado mercado) {
        Session session = null;
        SmsMercado mercados = new SmsMercado();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsMercado as mercado "
                    + "left join fetch mercado.smsServicioses as servicios "
                    + "where mercado.mercadoNombre = '" + mercado.getMercadoNombre() + "'");
            mercados = (SmsMercado) query.list().get(0);

        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return mercados;
    }

    @Override
    public SmsMercado consultarMercadoConProveedores(SmsMercado mercado) {
        Session session = null;
        SmsMercado mercados = new SmsMercado();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsMercado as mercado "
                    + "left join fetch mercado.smsProveedors as proveedores "
                    + "left join fetch proveedores.smsMercados where mercado.mercadoNombre = '" + mercado.getMercadoNombre() + "'");
            mercados = (SmsMercado) query.list().get(0);

        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return mercados;
    }

    @Override
    public List<SmsMercado> consultarMercadoSegunCategoria(SmsCategoria categoria) {
        Session session = null;
        List<SmsMercado> mercados = new ArrayList<>();
        int id = categoria.getIdCategoria();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("select mercado from SmsCategoria as cat "
                    + "left outer join cat.smsMercados as mercado "
                    + "where cat.idCategoria ='" + id + "'");
            mercados = (List<SmsMercado>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return mercados;
    }

    @Override
    public List<SmsMercado> consultarMercadosSegunProveedor(SmsProveedor proveedor) {
        Session session = null;
        List<SmsMercado> mercados = new ArrayList<>();
        int id = proveedor.getIdProveedor();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("select mercado from SmsProveedor as prov "
                    + "left outer join prov.smsMercados as mercado "
                    + "where prov.idProveedor = '" + id + "'");
            mercados = (List<SmsMercado>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return mercados;
    }

    @Override
    public SmsMercado consultarMercado(SmsMercado mercado) {
        Session session = null;
        SmsMercado mercados = new SmsMercado();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsMercado as mercado "
                    + "where mercado.mercadoNombre = '" + mercado.getMercadoNombre() + "'");
            mercados = (SmsMercado) query.list().get(0);

        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return mercados;
    }

    @Override
    public SmsMercado consultarMercadoConCategoriasServicios(SmsMercado mercado) {
        Session session = null;
        SmsMercado mercados = new SmsMercado();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsMercado as mercado "
                    + "left join fetch mercado.smsCategoriasServicios as categorias "
                    + "left join fetch categorias.smsMercados "
                    + "where mercado.mercadoNombre = '" + mercado.getMercadoNombre() + "' or "
                    + "mercado.idMercado = '" + mercado.getIdMercado() + "'");
            if (!query.list().isEmpty()) {
                mercados = (SmsMercado) query.list().get(0);
            }
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return mercados;
    }

    @Override
    public void actualizarMercado(SmsMercado mercado) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.update(mercado);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Categorias asociadas", "");
        } catch (Exception e) {
            e.getMessage();
            session.getTransaction().rollback();
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Imposible realizar operacion", "");

        } finally {
            if (session != null) {
                session.close();
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
