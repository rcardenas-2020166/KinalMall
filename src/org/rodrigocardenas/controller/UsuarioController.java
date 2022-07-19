
package org.rodrigocardenas.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.WHITE;
import javax.swing.JOptionPane;
import org.rodrigocardenas.bean.Usuario;
import org.rodrigocardenas.db.Conexion;
import org.rodrigocardenas.system.Principal;


public class UsuarioController implements Initializable
{
    private Principal escenarioPrincipal;
    private enum operaciones {GUARDAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    
    
    @FXML private JFXButton btnRegistrar;
    @FXML private JFXButton btnRegresar;
    
    @FXML private TextField txtNombreUsuario;
    @FXML private TextField txtApellidoUsuario;
    @FXML private TextField txtUsuarioLogin;
    @FXML private TextField txtContrasena;
    
    @FXML private FontAwesomeIconView iconNuevo;
    @FXML private FontAwesomeIconView iconRegresar;


    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        
    }
    
    public void registrar()
    {
        switch(tipoDeOperacion)
        {
            case NINGUNO:
                
                limpiarControles();
                activarControles();
                
                btnRegistrar.setText("Guardar");
                iconNuevo = (new FontAwesomeIconView(FontAwesomeIcon.SAVE));
                btnRegistrar.setGraphic(iconNuevo);
                iconNuevo.setFill(WHITE);
                iconNuevo.setGlyphSize(28);
                iconNuevo.setWrappingWidth(140);
                
                btnRegresar.setText("Cancelar");
                iconRegresar = (new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE));
                btnRegresar.setGraphic(iconRegresar);
                iconRegresar.setFill(WHITE);
                iconRegresar.setGlyphSize(29);
                iconRegresar.setWrappingWidth(140);
                
                tipoDeOperacion = operaciones.GUARDAR;
                break;
                
                
            case GUARDAR:
                
                guardar();

                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }
    
    
    
    public void regresar()
    {
        
        switch (tipoDeOperacion)
        {
            case GUARDAR:
                
                desactivarControles();
                limpiarControles();
                
                btnRegistrar.setText("Nuevo");
                iconNuevo = (new FontAwesomeIconView(FontAwesomeIcon.USER_PLUS));
                btnRegistrar.setGraphic(iconNuevo);
                iconNuevo.setFill(WHITE);
                iconNuevo.setGlyphSize(28);
                iconNuevo.setWrappingWidth(140);
                
                btnRegresar.setText("Regresar");
                iconRegresar = (new FontAwesomeIconView(FontAwesomeIcon.REPLY_ALL));
                btnRegresar.setGraphic(iconRegresar);
                iconRegresar.setFill(WHITE);
                iconRegresar.setGlyphSize(29);
                iconRegresar.setWrappingWidth(140);
    
                tipoDeOperacion = operaciones.NINGUNO;
                
                break;
            
            default:
                
                ventanaLogin();
                
                break;
                        
        }
    }
    
    
    public void guardar()
    {
        Usuario registro = new Usuario();
        
        registro.setNombreUsuario(txtNombreUsuario.getText());
        registro.setApellidoUsuario(txtApellidoUsuario.getText());
        registro.setUsuarioLogin(txtUsuarioLogin.getText());
        registro.setContrasena(txtContrasena.getText());
        
        if (txtNombreUsuario.getText().equals("") | txtApellidoUsuario.getText().equals("") |
            txtUsuarioLogin.getText().equals("") | txtContrasena.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null,"Por Favor. Llene todos los campos.", "REGISTRO", JOptionPane.INFORMATION_MESSAGE);
        }
        
        else
        {
            try
            {
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarUsuario(?, ?, ?, ?)}");

                procedimiento.setString(1, registro.getNombreUsuario());
                procedimiento.setString(2, registro.getApellidoUsuario());
                procedimiento.setString(3, registro.getUsuarioLogin());
                procedimiento.setString(4, registro.getContrasena());

                procedimiento.execute();

                ventanaLogin();

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    
    
    
    // MÉTODOS DE CONTROLES
    
    public void desactivarControles()
    {
        txtNombreUsuario.setEditable(false);
        txtApellidoUsuario.setEditable(false);
        txtUsuarioLogin.setEditable(false);
        txtContrasena.setEditable(false);
    }
    
    public void activarControles()
    {
        txtNombreUsuario.setEditable(true);
        txtApellidoUsuario.setEditable(true);
        txtUsuarioLogin.setEditable(true);
        txtContrasena.setEditable(true);
    }
    
    public void limpiarControles()
    {
        txtNombreUsuario.clear();
        txtApellidoUsuario.clear();
        txtUsuarioLogin.clear();
        txtContrasena.clear();
    }
    
    
    //MÉTODOS DE ACCESO

    public Principal getEscenarioPrincipal() 
    {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) 
    {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaLogin()
    {
        escenarioPrincipal.ventanaLogin();
    }
    
}
