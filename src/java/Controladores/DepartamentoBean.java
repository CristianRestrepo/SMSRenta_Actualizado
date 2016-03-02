/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.IDepartamentoDao;
import DAO.IPaisDao;
import DAO.ImpDepartamentoDao;
import DAO.ImpPaisDao;
import Modelo.SmsDepartamento;
import Modelo.SmsPais;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author Desarrollo_Planit
 */
public class DepartamentoBean {

    private SmsDepartamento departamentoView;
    private List<SmsDepartamento> departamentoListView;
    private List<String> nombresDepartamentoListView;
    private SmsPais paisView;

    IDepartamentoDao departamentoDao;
    IPaisDao paisDao;

    public DepartamentoBean() {

        departamentoDao = new ImpDepartamentoDao();
        paisDao = new ImpPaisDao();

        departamentoView = new SmsDepartamento();
        departamentoListView = new ArrayList<>();
        nombresDepartamentoListView = new ArrayList<>();

    }
    
    @PostConstruct
    public void init(){
        departamentoListView = departamentoDao.consultarDepartamentos();
    }

    public SmsDepartamento getDepartamentoView() {
        return departamentoView;
    }

    public void setDepartamentoView(SmsDepartamento departamentoView) {
        this.departamentoView = departamentoView;
    }

    public List<SmsDepartamento> getDepartamentoListView() {
        return departamentoListView;
    }

    public void setDepartamentoListView(List<SmsDepartamento> departamentoListView) {
        this.departamentoListView = departamentoListView;
    }

    public List<String> getNombresDepartamentoListView() {
        departamentoListView = new ArrayList<>();
        nombresDepartamentoListView = new ArrayList<>();
        departamentoListView = departamentoDao.consultarDepartamentos();
        for (int i = 0; i < departamentoListView.size(); i++) {
            nombresDepartamentoListView.add(departamentoListView.get(i).getDepartamentoNombre());
        }
        return nombresDepartamentoListView;
    }

    public void setNombresDepartamentoListView(List<String> nombresDepartamentoListView) {
        this.nombresDepartamentoListView = nombresDepartamentoListView;
    }

    public SmsPais getPaisView() {
        return paisView;
    }

    public void setPaisView(SmsPais paisView) {
        this.paisView = paisView;
    }

}
