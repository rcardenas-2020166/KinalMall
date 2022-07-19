
package org.rodrigocardenas.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javax.swing.JOptionPane;
import org.rodrigocardenas.system.Principal;

public class MenuPrincipalController implements Initializable
{
    private Principal escenarioPrincipal;

    
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        
    }

    public Principal getEscenarioPrincipal() 
    {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) 
    {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaProgramador()
    {
        escenarioPrincipal.ventanaProgramador();
    }
    
    public void ventanaAdministracion()
    {
        escenarioPrincipal.ventanaAdministracion();
    }
    
    public void ventanaTipoCliente()
    {
        escenarioPrincipal.ventanaTipoCliente();
    }
    
    public void ventanaLocales()
    {
        escenarioPrincipal.ventanaLocales();
    }
     
    public void ventanaClientes()
    {
        escenarioPrincipal.ventanaClientes();
    }
    
    public void ventanaHorarios()
    {
        escenarioPrincipal.ventanaHorarios();
    }
    
    public void ventanaDepartamentos()
    {
        escenarioPrincipal.ventanaDepartamentos();
    }
    
    public void ventanaProveedores()
    {
        escenarioPrincipal.ventanaProveedores();
    }
    
    public void ventanaCuentasPorPagar()
    {
        escenarioPrincipal.ventanaCuentasPorPagar();
    }
    
    public void ventanaCargos()
    {
        escenarioPrincipal.ventanaCargos();
    }
    
    public void ventanaCuentasPorCobrar()
    {
        escenarioPrincipal.ventanaCuentasPorCobrar();
    }
    
    public void ventanaEmpleados()
    {
        escenarioPrincipal.ventanaEmpleados();
    }  
    
    public void ventanaUsuarios()
    {
        escenarioPrincipal.ventanaUsuarios();
    }
    
     public void ventanaLogin()
    {
        escenarioPrincipal.ventanaLogin();
    }
    
    public void salirPrograma()
    {
        int respuesta = JOptionPane.showConfirmDialog(null, "¿ Está seguro que desea SALIR del Programa ?", 
                                                                  "SALIR DEL PROGRAMA", JOptionPane.YES_NO_OPTION,
                                                                  JOptionPane.QUESTION_MESSAGE);
        if  (respuesta == JOptionPane.YES_NO_OPTION)
        {
            System.exit(0);
        }
    }
    
    public void login()
    {
        int respuesta = JOptionPane.showConfirmDialog(null, "¿ Está seguro que desea Cerrar su Sesión ?", 
                                                                  "CERRAR SESIÓN", JOptionPane.YES_NO_OPTION,
                                                                  JOptionPane.QUESTION_MESSAGE);
        if  (respuesta == JOptionPane.YES_NO_OPTION)
        {
            escenarioPrincipal.ventanaLogin();
        }
    }
   
    
}
