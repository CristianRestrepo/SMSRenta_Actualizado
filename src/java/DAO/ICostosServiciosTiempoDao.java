/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.SmsCostoserviciosTiempo;
import java.util.List;

/**
 *
 * @author Desarrollo_Planit
 */
public interface ICostosServiciosTiempoDao {
    
    public List<SmsCostoserviciosTiempo> consultarCostos();
    
    public void registrarCosto(SmsCostoserviciosTiempo costo);
    
    public void modificarCosto(SmsCostoserviciosTiempo costo);
    
    public void eliminarCosto(SmsCostoserviciosTiempo costo);
    
}
