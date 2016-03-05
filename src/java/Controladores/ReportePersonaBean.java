/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelo.Reporte_Persona;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author SISTEMAS
 */
public class ReportePersonaBean {

    private List<Reporte_Persona> personas = new ArrayList<>();

    /**
     * Creates a new instance of NewJSFManagedBean
     */
    public ReportePersonaBean() {
    }

    public List<Reporte_Persona> getPersonas() {

        Reporte_Persona per = new Reporte_Persona();
        per.setNombre("Johann");
        per.setApellido("Cañon");

        Calendar cal = Calendar.getInstance();
        cal.set(1991, 10, 16);
        per.setFechaDeNacimiento(cal.getTime());

        personas.add(per);

        per = new Reporte_Persona();
        per.setNombre("Fernando");
        per.setApellido("Cepeda");

        cal = Calendar.getInstance();
        cal.set(1990, 4, 1);
        per.setFechaDeNacimiento(cal.getTime());

        personas.add(per);
        return personas;
    }

    public void setPersonas(List<Reporte_Persona> personas) {
        this.personas = personas;
    }

    public void exportarPDF() throws JRException, IOException {


        Map parametros = new HashMap();
        parametros.put("txtUsuario", "SMS Renta - PSP Pagos");

        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Reportes_SMS/reportePrueba.jasper"));
       
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, new JRBeanCollectionDataSource(this.getPersonas()));

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment;  filename=reportePrueba.pdf");
//        ("Content-disposition", "attachment;  filename=reportePrueba.pdf");
        ServletOutputStream stream = response.getOutputStream();

        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
        
        stream.flush();
        stream.close();

        FacesContext.getCurrentInstance().responseComplete();

    }

}
