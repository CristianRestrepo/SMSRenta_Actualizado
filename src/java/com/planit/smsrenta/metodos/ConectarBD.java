package com.planit.smsrenta.metodos;


import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ConectarBD {

public Connection conexion;
public PreparedStatement sentencia;
public ConectarBD(){

String ruta="jdbc:mysql://localhost:3306/smsrenta_actualizada";
try{
Class.forName("com.mysql.jdbc.Driver");
conexion=DriverManager.getConnection(ruta,"root","");         
        }
catch(ClassNotFoundException e){
System.out.println("error"+e);
}
catch(SQLException e){
System.out.println("error en la conexion"+ e);
}
}

public Connection getConexion() {
return conexion;
    }

public void setConexion(Connection conexion) {
this.conexion = conexion;
    }

    Statement createStatement() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
}
