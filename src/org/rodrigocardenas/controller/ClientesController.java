
package org.rodrigocardenas.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.WHITE;
import javax.swing.JOptionPane;
import org.rodrigocardenas.bean.Administracion;
import org.rodrigocardenas.bean.Clientes;
import org.rodrigocardenas.bean.Locales;
import org.rodrigocardenas.bean.TipoCliente;
import org.rodrigocardenas.db.Conexion;
import org.rodrigocardenas.system.Principal;

public class ClientesController implements Initializable
{
    
    private Principal escenarioPrincipal;

     private enum operaciones
    {
        NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList <Clientes> listaClientes;
    private ObservableList <Administracion> listaAdministracion;
    private ObservableList <Locales> listaLocales;
    private ObservableList <TipoCliente> listaTipoClientes;


    
    // INYECCIONES JAVAFX
    
    @FXML private JFXButton btnNuevo;
    @FXML private JFXButton btnEditar;
    @FXML private JFXButton btnEliminar;
    @FXML private JFXButton btnReporte;
    
    @FXML private TextField txtCodigoCliente;
    @FXML private TextField txtNombresCliente;
    @FXML private TextField txtApellidosCliente;
    @FXML private TextField txtTelefonoCliente;
    @FXML private TextField txtDireccionCliente;
    @FXML private TextField txtEmailCliente;
    
    @FXML private JFXComboBox cmbCodigoLocal;
    @FXML private JFXComboBox cmbCodigoAdministracion;
    @FXML private JFXComboBox cmbCodigoTipoCliente;
    
    @FXML private TableView tblClientes;
    
    @FXML private TableColumn colCodigoCliente;
    @FXML private TableColumn colNombresCliente;
    @FXML private TableColumn colApellidosCliente;
    @FXML private TableColumn colTelefonoCliente;
    @FXML private TableColumn colDireccionCliente;
    @FXML private TableColumn colEmaliCliente;
    @FXML private TableColumn colCodigoLocal;
    @FXML private TableColumn colCodigoAdministracion;
    @FXML private TableColumn colCodigoTipoCliente;
    
    @FXML private FontAwesomeIconView iconNuevo;
    @FXML private FontAwesomeIconView iconEditar;
    @FXML private FontAwesomeIconView iconEliminar;
    @FXML private FontAwesomeIconView iconReporte;


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
    
    
    public void menuPrincipal()
    {
        escenarioPrincipal.menuPrincipal();
    }
    
    
 
}
