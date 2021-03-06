/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsHojavida;
import java.util.List;

/**
 *
 * @author Desarrollo_Planit
 */
public interface IHojaVidaDao {
    
   //Definicion metodos CRUD
   public List<SmsHojavida> mostrarHojaVida();
   public void registrarHojaVida(SmsHojavida hojaVida);
   public void modificarHojaVida(SmsHojavida hojaVida);
   public void eliminarHojaVida(SmsHojavida hojaVida);
   public SmsHojavida consultarHojaVida(SmsHojavida hojavida);
}

