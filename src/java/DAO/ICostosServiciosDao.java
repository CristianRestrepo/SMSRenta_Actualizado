/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.SmsCategoria;
import Modelo.SmsCostosservicios;
import Modelo.SmsServicios;
import java.util.List;

/**
 *
 * @author CristianCamilo
 */
public interface ICostosServiciosDao {
    
    public List<SmsCostosservicios> consultarCostos();
    public List<SmsCostosservicios> consultarCostoServicio(SmsServicios servicio, SmsCategoria categoria);
    public List<SmsCostosservicios> consultarCostoServicio(SmsServicios servicio);
    public void registrarCostoServicio(SmsCostosservicios costo);
    public void modificarCostoServicio(SmsCostosservicios costo);
    public void eliminarCostoServicio(SmsCostosservicios costo);
    public List<SmsCostosservicios> filtrarCostosServicios(String dato);  
    
}
