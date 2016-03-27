/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.IFacturaDao;
import DAO.ImpFacturaDao;
import Modelo.SmsFactura;
import Modelo.SmsReservacion;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CristianCamilo
 */
public class FacturaBean {

    private SmsFactura facturaView;
    private List<SmsFactura> facturaListView;

    IFacturaDao facturaDao;

    public FacturaBean() {

        facturaDao = new ImpFacturaDao();
        facturaView = new SmsFactura();
        facturaListView = new ArrayList<>();

    }

    public SmsFactura getFacturaView() {
        return facturaView;
    }

    public void setFacturaView(SmsFactura facturaView) {
        this.facturaView = facturaView;
    }

    public List<SmsFactura> getFacturaListView() {
        return facturaListView;
    }

    public void setFacturaListView(List<SmsFactura> facturaListView) {
        this.facturaListView = facturaListView;
    }

    //Metodos
    public void registrar(SmsReservacion reservacion) {
        facturaView.setSmsReservacion(reservacion);
        facturaDao.registrarFactura(facturaView);
        facturaView = new SmsFactura();

    }

    public void eliminar() {
        facturaDao.eliminarFactura(facturaView);
        facturaView = new SmsFactura();

    }
   
}
