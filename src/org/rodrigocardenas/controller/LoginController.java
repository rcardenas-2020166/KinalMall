
package org.rodrigocardenas.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javax.swing.JOptionPane;
import org.rodrigocardenas.bean.Login;
import org.rodrigocardenas.bean.Usuario;
import org.rodrigocardenas.db.Conexion;
import org.rodrigocardenas.system.Principal;


public class LoginController implements Initializable
{
    private Principal escenarioPrincipal;
    
    private ObservableList <Usuario> listaUsuarios;
    
    @FXML private JFXTextField txtUsuario;
    @FXML private JFXPasswordField txtPassword;
    @FXML private JFXButton btnLogin;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        
    }
    
    public ObservableList <Usuario> getUsuario()
    {
        ArrayList <Usuario> lista = new ArrayList <Usuario>();
        try
        {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarUsuarios()}");
            ResultSet resultado = procedimiento.executeQuery();
            
            while(resultado.next())
            {
                lista.add(new Usuario (resultado.getInt("codigoUsuario"),
                                       resultado.getString("nombreUsuario"),
                                       resultado.getString("apellidoUsuario"),
                                       resultado.getString("usuarioLogin"),
                                       resultado.getString("contrasena")));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return listaUsuarios = FXCollections.observableArrayList(lista);
    }
    
    @FXML
    private void login() throws NoSuchAlgorithmException
    {
        Login logeo = new Login();
        int x = 0;
        boolean bandera = false;
        logeo.setUsuarioMaster(txtUsuario.getText());
        logeo.setPasswordLogin(txtPassword.getText());
        
        while(x < getUsuario().size())
        {
            String user = getUsuario().get(x).getUsuarioLogin();
            String pass = getUsuario().get(x).getContrasena();

            if(user.equals(logeo.getUsuarioMaster()) && pass.equals(logeo.getPasswordLogin()))
            {
                JOptionPane.showMessageDialog(null,"Sesión Iniciada \n"+ getUsuario().get(x).getNombreUsuario()
                                                    + " " + getUsuario().get(x).getApellidoUsuario());
                escenarioPrincipal.menuPrincipal();
                
                x = getUsuario().size();
                bandera = true;
            }
            x++;
        }
        
        if (bandera == false)
        {
            JOptionPane.showMessageDialog(null,"Usuario o Contraseña Incorrecta");
        }
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
    
    public void ventanaUsuarios()
    {
        escenarioPrincipal.ventanaUsuarios();
    }
    
}
