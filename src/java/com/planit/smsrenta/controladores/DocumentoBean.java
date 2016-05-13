/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.controladores;

import com.planit.smsrenta.metodos.GenerarReportes;

/**
 *
 * @author Desarrollo_Planit
 */
public class DocumentoBean {

    private String documento;
    
    public DocumentoBean() {        
        documento = GenerarReportes.getRutaDocumento();
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }
    
        
}
