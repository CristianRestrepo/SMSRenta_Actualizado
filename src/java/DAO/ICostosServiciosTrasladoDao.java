/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.SmsCostoserviciosTraslado;
import java.util.List;

/**
 *
 * @author Desarrollo_Planit
 */
public interface ICostosServiciosTrasladoDao {
    
    public List<SmsCostoserviciosTraslado> consultarCostos();
    
    public void registrarCosto(SmsCostoserviciosTraslado costo);
    
    public void modificarCosto(SmsCostoserviciosTraslado costo);
    
    public void eliminarCosto(SmsCostoserviciosTraslado costo);
    
    
}
