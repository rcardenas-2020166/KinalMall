
package org.rodrigocardenas.controller;


import com.jfoenix.controls.JFXButton;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.rodrigocardenas.system.Principal;


public class ProgramadorController implements Initializable
{
    private Principal escenarioPrincipal;
    
    @FXML JFXButton btnProgramador;
    @FXML JFXButton btnAdministracion;
    @FXML Label lblNombres;
    @FXML Label lblApellidos;
    @FXML Label lblTitulo;
    @FXML Label lblA;
    @FXML Label lblN;
    @FXML Label lblT;
    @FXML Label lblDatosProgramador;
    @FXML Label lblAdministracion;
    @FXML Label lblAnio;
    @FXML ImageView imageLogoKinal;

    
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        
    }

    @FXML
    public void opciones(ActionEvent event)
    {
        if (event.getSource() == btnProgramador)
        {
            lblDatosProgramador.setText(" DATOS DEL PROGRAMADOR ");
            lblA.setText(" Apellidos: ");
            lblApellidos.setText(" Cárdenas Monroy ");
            lblN.setText(" Nombres: ");
            lblNombres.setText(" Rodrigo Gerardo ");
            lblT.setText(" Cargo: ");
            lblTitulo.setText(" Programador "); 
            lblAdministracion.setText("");
            lblAnio.setText("");
            imageLogoKinal.setImage(new Image("/org/rodrigocardenas/images/LimpiezaImagen.png"));
        }
        
        else if(event.getSource()== btnAdministracion)
        {
            lblDatosProgramador.setText(" DATOS DE ADMINISTRACIÓN ");
            lblAdministracion.setText(" Fundación Kinal ® ");
            lblAnio.setText(" 2021 ");
            lblA.setText("");
            lblApellidos.setText("");
            lblN.setText("");
            lblNombres.setText("");
            lblT.setText("");
            lblTitulo.setText("");
            imageLogoKinal.setImage(new Image("/org/rodrigocardenas/images/LogoKinal.png"));
            
        }
    }
    
    public void menuPrincipal()
    {
        escenarioPrincipal.menuPrincipal();
    }
    
    public Principal getEscenarioPrincipal() 
    {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) 
    {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
}

