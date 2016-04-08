/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao; 
import com.planit.smsrenta.modelos.SmsCiudad;
import com.planit.smsrenta.modelos.SmsLocalidad;
import java.util.List;

/**
 *
 * @author Desarrollo_Planit
 */
public interface ILocalidadDao  {
    
    public List<SmsLocalidad> consultarLocalidades();
    public List<SmsLocalidad> consultarLocalidad(SmsLocalidad localidad);
    public List<SmsLocalidad> consultarLocalidadesSegunCiudad(SmsCiudad ciudad);
}
