
package org.rodrigocardenas.system;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.rodrigocardenas.controller.AdministracionController;
import org.rodrigocardenas.controller.ClientesController;
import org.rodrigocardenas.controller.LocalesController;
import org.rodrigocardenas.controller.MenuPrincipalController;
import org.rodrigocardenas.controller.ProgramadorController;
import org.rodrigocardenas.controller.TipoClienteController;


/*
    Programador - Alumno:	
		Rodrigo Gerardo Cárdenas Monroy

    Carné:
		2020166

    Creación:
		28/04/2021

    Modificaciones:
                29/04/2021
		06/05/2021
                07/05/2021
                12/05/2021
                13/05/2021
                19/05/2021
                20/05/2021
                26/05/2021
                27/05/2021 
                02/06/2021
                03/06/2021
*/


public class Principal extends Application 
{
    private Stage escenarioPrincipal;
    private Scene escena;
    private final String PAQUETE_VISTA = "/org/rodrigocardenas/view/";
    
    @Override
    
    public void start(Stage escenarioPrincipal) throws IOException 
    {
       this.escenarioPrincipal = escenarioPrincipal; 
       this.escenarioPrincipal.setTitle("Kinal Mall");
       menuPrincipal();
       escenarioPrincipal.show();
       escenarioPrincipal.getIcons().add(new Image("/org/rodrigocardenas/images/IconoKinalMall.png"));
    }
    
    public void menuPrincipal()
    {
        try
        {
            MenuPrincipalController menuPrincipal = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml",609,388);
            menuPrincipal.setEscenarioPrincipal(this);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) 
    {
        launch(args);
    }
    
    public Initializable cambiarEscena(String fxml, int ancho, int alto) throws IOException
    {
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA+fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA+fxml));
        escena = new Scene(cargadorFXML.load(archivo),ancho,alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable)cargadorFXML.getController();
        return resultado;
    }   
    
    public void ventanaProgramador()
    {
        try
        {
            ProgramadorController programador = (ProgramadorController) cambiarEscena("ProgramadorView.fxml",609,388);
            programador.setEscenarioPrincipal(this);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void ventanaAdministracion()
    {
        try
        {
            AdministracionController administracion = (AdministracionController) cambiarEscena("AdministracionView.fxml", 800, 450);
            administracion.setEscenarioPrincipal(this);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void ventanaTipoCliente()
    {
        try
        {
            TipoClienteController tipoCliente = (TipoClienteController) cambiarEscena("TipoClienteView.fxml", 800, 450);
            tipoCliente.setEscenarioPrincipal(this);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void ventanaLocales()
    {
        try
        {
            LocalesController locales = (LocalesController) cambiarEscena("LocalesView.fxml", 875, 450);
            locales.setEscenarioPrincipal(this);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }    
    }


    public void ventanaClientes()
    {
        try
        {
            ClientesController clientes = (ClientesController) cambiarEscena("ClientesView.fxml", 1050, 450);
            clientes.setEscenarioPrincipal(this);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }    
    }
}
