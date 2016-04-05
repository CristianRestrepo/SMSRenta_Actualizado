/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsReferencia;
import java.util.List;

/**
 *
 * @author Desarrollo_Planit
 */
public interface IReferenciaDao {
    
    public List<SmsReferencia> mostrarReferencias();
    public List<SmsReferencia> consultarReferencias(SmsReferencia referencia);
    public List<SmsReferencia> filtrarReferencias(String dato);
    public void registrarReferencia(SmsReferencia referencia);
    public void modificarReferencia(SmsReferencia referencia);
    public void eliminarReferencia(SmsReferencia referencia);
    
}
