/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.SmsCategoria;
import Modelo.SmsCostosServicio;
import Modelo.SmsServicios;
import java.util.List;

/**
 *
 * @author CristianCamilo
 */
public interface ICostosServiciosDAO {
    
    public List<SmsCostosServicio> mostrarCostosServicios();
    public List<SmsCostosServicio> consultarCostoServicio(SmsServicios servicio, SmsCategoria categoria);
    public void registrarCostoServicio(SmsCostosServicio costo);
    public void modificarCostoServicio(SmsCostosServicio costo);
    public void eliminarCostoServicio(SmsCostosServicio costo);
    public List<SmsCostosServicio> filtrarCostosServicios(String dato);  
    
}
