/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.SmsCategoria;
import Modelo.SmsCostosserviciosRenta;
import Modelo.SmsServicios;
import java.util.List;

/**
 *
 * @author CristianCamilo
 */
public interface ICostosServiciosRentaDao {
    
    public List<SmsCostosserviciosRenta> consultarCostos();
    public List<SmsCostosserviciosRenta> consultarCostoServicio(SmsServicios servicio, SmsCategoria categoria);
    public void registrarCostoServicio(SmsCostosserviciosRenta costo);
    public void modificarCostoServicio(SmsCostosserviciosRenta costo);
    public void eliminarCostoServicio(SmsCostosserviciosRenta costo);
    public List<SmsCostosserviciosRenta> filtrarCostosServicios(String dato);  
    
}
