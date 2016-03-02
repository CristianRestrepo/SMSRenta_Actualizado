/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.SmsDepartamento;
import java.util.List;

/**
 *
 * @author Desarrollo_Planit
 */
public interface IDepartamentoDao {
    
    public List<SmsDepartamento> consultarDepartamentos();
    
    public List<SmsDepartamento> consultarDepartamento(SmsDepartamento departamento);
    
    public List<SmsDepartamento> filtrarDepartamentos(String valor);
    
    public void registrarDepartamento(SmsDepartamento departamento);
    
    public void modificarDepartamento(SmsDepartamento departamento);
    
    public void eliminarDepartamento(SmsDepartamento departamento);
}
