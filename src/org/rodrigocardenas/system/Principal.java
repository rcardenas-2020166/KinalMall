
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
import org.rodrigocardenas.controller.CargoController;
import org.rodrigocardenas.controller.ClienteController;
import org.rodrigocardenas.controller.CuentaPorCobrarController;
import org.rodrigocardenas.controller.CuentaPorPagarController;
import org.rodrigocardenas.controller.DepartamentoController;
import org.rodrigocardenas.controller.EmpleadoController;
import org.rodrigocardenas.controller.HorarioController;
import org.rodrigocardenas.controller.LocalController;
import org.rodrigocardenas.controller.LoginController;
import org.rodrigocardenas.controller.MenuPrincipalController;
import org.rodrigocardenas.controller.ProgramadorController;
import org.rodrigocardenas.controller.ProveedorController;
import org.rodrigocardenas.controller.TipoClienteController;
import org.rodrigocardenas.controller.UsuarioController;


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
                09/06/2021
                10/06/2021
                16/06/2021
                17/06/2021
                18/06/2021
                30/06/2021
                01/07/2021
                07/07/2021
                10/07/2021          
*/


public class Principal extends Application 
{
    private Stage escenarioPrincipal;
    private Scene escena;
    private final String PAQUETE_VISTA = "/org/rodrigocardenas/view/";
    private boolean estaFull = false;

    
    @Override
    
    public void start(Stage escenarioPrincipal) throws IOException 
    {
       this.escenarioPrincipal = escenarioPrincipal; 
       this.escenarioPrincipal.setTitle("Kinal Mall");
       ventanaLogin();
       escenarioPrincipal.show();
       escenarioPrincipal.getIcons().add(new Image("/org/rodrigocardenas/images/IconoKinalMall.png"));
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
        escenarioPrincipal.centerOnScreen();
        resultado = (Initializable)cargadorFXML.getController();
        return resultado;
    }
    
    
    public void menuPrincipal()
    {
        try
        {
            MenuPrincipalController menuPrincipal = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml",609,408);
            menuPrincipal.setEscenarioPrincipal(this);
            this.escenarioPrincipal.setTitle("Kinal Mall");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    public void ventanaProgramador()
    {
        try
        {
            ProgramadorController programador = (ProgramadorController) cambiarEscena("ProgramadorView.fxml",609,388);
            programador.setEscenarioPrincipal(this);
            //escenarioPrincipal.centerOnScreen();
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
            AdministracionController administracion = (AdministracionController) cambiarEscena("AdministracionView.fxml", 808, 490);
            administracion.setEscenarioPrincipal(this); 
            escenarioPrincipal.setTitle("Kinal Mall - Administración");
            //escenarioPrincipal.centerOnScreen();
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
            TipoClienteController tipoCliente = (TipoClienteController) cambiarEscena("TipoClienteView.fxml", 808, 531);
            tipoCliente.setEscenarioPrincipal(this);
            this.escenarioPrincipal.setTitle("Kinal Mall - Tipos de Clientes");
            //escenarioPrincipal.centerOnScreen();
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
            LocalController locales = (LocalController) cambiarEscena("LocalView.fxml", 950, 531);
            locales.setEscenarioPrincipal(this);
            this.escenarioPrincipal.setTitle("Kinal Mall - Locales");
            //escenarioPrincipal.centerOnScreen();
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
            ClienteController clientes = (ClienteController) cambiarEscena("ClienteView.fxml", 1100, 531);
            clientes.setEscenarioPrincipal(this);
            this.escenarioPrincipal.setTitle("Kinal Mall - Clientes");
            //escenarioPrincipal.centerOnScreen();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }    
    }
    
    
    public void ventanaHorarios()
    {
        try
        {
            HorarioController horarios = (HorarioController) cambiarEscena("HorarioView.fxml", 995, 490);
            horarios.setEscenarioPrincipal(this);
            this.escenarioPrincipal.setTitle("Kinal Mall - Horarios");
            //escenarioPrincipal.centerOnScreen();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    public void ventanaDepartamentos()
    {
        try
        {
            DepartamentoController departamentos = (DepartamentoController) cambiarEscena("DepartamentoView.fxml", 808, 490);
            departamentos.setEscenarioPrincipal(this);
            this.escenarioPrincipal.setTitle("Kinal Mall - Departamentos");
            //escenarioPrincipal.centerOnScreen();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    public void ventanaProveedores()
    {
        try
        {
            ProveedorController proveedores = (ProveedorController) cambiarEscena("ProveedorView.fxml", 1080, 531);
            proveedores.setEscenarioPrincipal(this);
            this.escenarioPrincipal.setTitle("Kinal Mall - Proveedores");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    public void ventanaCuentasPorPagar()
    {
        try
        {
            CuentaPorPagarController cuentasPorPagar = (CuentaPorPagarController) cambiarEscena("CuentaPorPagarView.fxml", 960, 531);
            cuentasPorPagar.setEscenarioPrincipal(this);
            this.escenarioPrincipal.setTitle("Kinal Mall - Cuentas por Pagar");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    public void ventanaCargos()
    {
        try
        {
            CargoController cargos = (CargoController) cambiarEscena("CargoView.fxml", 808, 531);
            cargos.setEscenarioPrincipal(this);
            this.escenarioPrincipal.setTitle("Kinal Mall - Cargos");
            //escenarioPrincipal.centerOnScreen();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    public void ventanaCuentasPorCobrar()
    {
        try
        {
            CuentaPorCobrarController cuentasPorCobrar = (CuentaPorCobrarController) cambiarEscena("CuentaPorCobrarView.fxml", 1076, 531);
            cuentasPorCobrar.setEscenarioPrincipal(this);
            this.escenarioPrincipal.setTitle("Kinal Mall - Cuentas por Cobrar");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    public void ventanaEmpleados()
    {
        try
        {
            EmpleadoController empleados = (EmpleadoController) cambiarEscena("EmpleadoView.fxml", 1200, 531);
            empleados.setEscenarioPrincipal(this);
            this.escenarioPrincipal.setTitle("Kinal Mall - Empleados");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    public void ventanaUsuarios()
    {
        try
        {
            UsuarioController usuarios = (UsuarioController) cambiarEscena("UsuarioView.fxml", 609, 442);
            usuarios.setEscenarioPrincipal(this);
            //escenarioPrincipal.centerOnScreen();
            this.escenarioPrincipal.setTitle("Kinal Mall - Registrar Usuario");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
     public void ventanaLogin()
    {
        try
        {
            LoginController login = (LoginController) cambiarEscena("LoginView.fxml", 635, 470);
            login.setEscenarioPrincipal(this);
            escenarioPrincipal.centerOnScreen();
            this.escenarioPrincipal.setTitle("Kinal Mall - Iniciar Sesión");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
