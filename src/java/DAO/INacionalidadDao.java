/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.SmsNacionalidad;
import java.util.List;

/**
 *
 * @author Desarrollo_Planit
 */
public interface INacionalidadDao {
    
    public List<SmsNacionalidad> consultarNacionalidades();
    public List<SmsNacionalidad> consultarNacionalidad(SmsNacionalidad nacionalidad);
    
}
