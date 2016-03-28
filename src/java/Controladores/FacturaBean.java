/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.IFacturaDao;
import DAO.IReservacionDao;
import DAO.ImpFacturaDao;
import DAO.ImpReservacionDao;
import Funciones.GenerarReportes;
import Modelo.SmsFactura;
import Modelo.SmsReservacion;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author CristianCamilo
 */
public class FacturaBean {

    private SmsFactura facturaView;
    private List<SmsFactura> facturaListView;
    
    private SmsReservacion reservacionView;

    IFacturaDao facturaDao;
    IReservacionDao reservacionDao;

    public FacturaBean() {

        facturaDao = new ImpFacturaDao();
        reservacionDao = new ImpReservacionDao();
        
        facturaView = new SmsFactura();
        reservacionView = new SmsReservacion();
        facturaListView = new ArrayList<>();

    }

    public SmsFactura getFacturaView() {
        return facturaView;
    }

    public void setFacturaView(SmsFactura facturaView) {
        this.facturaView = facturaView;
    }

    public SmsReservacion getReservacionView() {
        return reservacionView;
    }

    public void setReservacionView(SmsReservacion reservacionView) {
        this.reservacionView = reservacionView;
    }

        
    public List<SmsFactura> getFacturaListView() {
        return facturaListView;
    }

    public void setFacturaListView(List<SmsFactura> facturaListView) {
        this.facturaListView = facturaListView;
    }

    //Metodos
    public void registrar(SmsReservacion reservacion) {         
        Date fecha = new Date();
        reservacion = reservacionDao.consultarReserva(reservacion).get(0);
        facturaView.setFacturaFecha(fecha);
        facturaView.setFacturaFechaVencimiento(fecha);
        facturaView.setFacturaTotal(reservacion.getReservacionCosto());
        facturaView.setSmsReservacion(reservacion);
        facturaDao.registrarFactura(facturaView);
        facturaView = new SmsFactura();        
    }

    public void eliminar() {
        facturaDao.eliminarFactura(facturaView);
        facturaView = new SmsFactura();
    }
    
    public void generarFactura() throws JRException, IOException{
        GenerarReportes reporte = new GenerarReportes();
        facturaView = facturaDao.consultarFacturaSegunReservacion(reservacionView).get(0);
        reporte.generarFactura(facturaView);
    }
    
    public void generarFacturaPos() throws JRException, IOException{
        GenerarReportes reporte = new GenerarReportes();
        facturaView = facturaDao.consultarFacturaSegunReservacion(reservacionView).get(0);
        reporte.generarFacturaPOS(facturaView);
    }
   
}
