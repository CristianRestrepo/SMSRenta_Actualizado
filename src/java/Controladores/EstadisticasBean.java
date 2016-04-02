/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import javax.faces.bean.RequestScoped;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author mac
 */
@ManagedBean
@RequestScoped
public class EstadisticasBean implements Serializable {

    private LineChartModel dateModel;
    private PieChartModel pieModel1;
    private BarChartModel barModel;

    public EstadisticasBean() {
        dateModel = new LineChartModel();
        pieModel1 = new PieChartModel();
        barModel = new BarChartModel();
    }

    @PostConstruct
    public void init() {
        
    }

    public LineChartModel getDateModel() {
        return dateModel;
    }

    public void setDateModel(LineChartModel dateModel) {
        this.dateModel = dateModel;
    }

    public PieChartModel getPieModel1() {
        return pieModel1;
    }

    public void setPieModel1(PieChartModel pieModel1) {
        this.pieModel1 = pieModel1;
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

    
    
    

}
