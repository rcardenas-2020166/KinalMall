
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
import org.rodrigocardenas.bean.Proveedor;
import org.rodrigocardenas.db.Conexion;
import org.rodrigocardenas.system.Principal;

public class ProveedorController implements Initializable
{
    
    private Principal escenarioPrincipal;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        cargarDatos();
    }
    
    private enum operaciones 
    {
        NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    
    
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    
    private ObservableList <Proveedor> listaProveedores;
    private ObservableList <Administracion> listaAdministracion;
    
    
    // INYECCIONES JAVAFX
    
    @FXML private JFXButton btnNuevo;
    @FXML private JFXButton btnEditar;
    @FXML private JFXButton btnEliminar;
    @FXML private JFXButton btnReporte;
    @FXML private JFXButton btnCuentasPorPagar;
    
    @FXML private TextField txtCodigoProveedor;
    @FXML private TextField txtNITProveedor;
    @FXML private TextField txtServicioPrestado;
    @FXML private TextField txtTelefonoProveedor;
    @FXML private TextField txtDireccionProveedor;
    @FXML private TextField txtSaldoFavor;
    @FXML private TextField txtSaldoContra;
    
    @FXML private JFXComboBox cmbCodigoAdministracion;
    
    @FXML private TableView tblProveedores;
    
    @FXML private TableColumn colCodigoProveedor;
    @FXML private TableColumn colNITProveedor;
    @FXML private TableColumn colServicioPrestado;
    @FXML private TableColumn colTelefonoProveedor;
    @FXML private TableColumn colDireccionProveedor;
    @FXML private TableColumn colSaldoFavor;
    @FXML private TableColumn colSaldoContra;
    @FXML private TableColumn colCodigoAdministracion;
    
    @FXML private FontAwesomeIconView iconNuevo;
    @FXML private FontAwesomeIconView iconEditar;
    @FXML private FontAwesomeIconView iconEliminar;
    @FXML private FontAwesomeIconView iconReporte;
    @FXML private FontAwesomeIconView iconCuentasPorPagar;
    
    
    
    public void cargarDatos()
    {
        tblProveedores.setItems(getProveedor());
        colCodigoProveedor.setCellValueFactory(new PropertyValueFactory <Proveedor, Integer>("codigoProveedor"));
        colNITProveedor.setCellValueFactory(new PropertyValueFactory <Proveedor, String>("NITProveedor"));
        colServicioPrestado.setCellValueFactory(new PropertyValueFactory <Proveedor, String>("servicioPrestado"));
        colTelefonoProveedor.setCellValueFactory(new PropertyValueFactory <Proveedor, String>("telefonoProveedor"));
        colDireccionProveedor.setCellValueFactory(new PropertyValueFactory <Proveedor, String>("direccionProveedor"));
        colSaldoFavor.setCellValueFactory(new PropertyValueFactory <Proveedor, Double>("saldoFavorTexto"));
        colSaldoContra.setCellValueFactory(new PropertyValueFactory <Proveedor, Double>("saldoContraTexto"));
        colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory <Administracion, Integer>("codigoAdministracion"));
        cmbCodigoAdministracion.setItems(getAdministracion());
    }
    
    
    
    public ObservableList <Proveedor> getProveedor()
    {
        ArrayList <Proveedor> lista = new ArrayList <Proveedor>();
        try
        {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProveedores()}");
            ResultSet resultado = procedimiento.executeQuery();
            
            while(resultado.next())
            {
                lista.add(new Proveedor(resultado.getInt("codigoProveedor"),
                                        resultado.getString("NITProveedor"),
                                        resultado.getString("servicioPrestado"),
                                        resultado.getString("telefonoProveedor"),
                                        resultado.getString("direccionProveedor"),
                                        resultado.getDouble("saldoFavor"),
                                        resultado.getDouble("saldoContra"),
                                        resultado.getInt("codigoAdministracion")));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
         
        return listaProveedores = FXCollections.observableArrayList(lista);
    }
    
    
    
    // VALORES ADMINISTRACION
    
    public ObservableList <Administracion> getAdministracion()
    {
        ArrayList <Administracion> lista = new ArrayList <Administracion>();
        try
        {
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarDatosAdministracion()}");
            ResultSet resultado = procedimiento.executeQuery();
            
            while(resultado.next())
            {
                lista.add(new Administracion(resultado.getInt("codigoAdministracion"),
                                             resultado.getString("direccion"),
                                             resultado.getString("telefono")));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return listaAdministracion = FXCollections.observableArrayList(lista);
    }
    
          
    
    public void seleccionarElemento()
    {
        if (tblProveedores.getSelectionModel().getSelectedItem() != null)
        {
            txtCodigoProveedor.setText(String.valueOf(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
            txtNITProveedor.setText(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getNITProveedor());
            txtServicioPrestado.setText(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getServicioPrestado());
            txtTelefonoProveedor.setText(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getTelefonoProveedor());
            txtDireccionProveedor.setText(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getDireccionProveedor());
            txtSaldoFavor.setText(String.valueOf(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getSaldoFavor()));
            txtSaldoContra.setText(String.valueOf(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getSaldoContra()));
           
            // ESTO HACE QUE SI ME MUESTRE DATOS SIN ELEGIR ANTES
            cmbCodigoAdministracion.getSelectionModel().selectFirst();
            cmbCodigoAdministracion.getSelectionModel().select(buscarAdministracion(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
        }
    }
    
    
    
    //BUSCAR DATOS PARA EL COMBOBOX
    
    public Administracion buscarAdministracion(int codigoAdministracion)
    {
        Administracion resultado = null;
        try
        {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarDatoAdministracion(?)}");
            procedimiento.setInt(1, codigoAdministracion);
            ResultSet registro = procedimiento.executeQuery();
            
            while(registro.next())
            {
                resultado = new Administracion(registro.getInt("codigoAdministracion"),
                                               registro.getString("direccion"),
                                               registro.getString("telefono"));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return resultado;
    }
    
    
    
    // ON ACTION SCENE BUILDER
    
    public void nuevo()
    {
        switch (tipoDeOperacion)
        {
            
            case NINGUNO:
                
                limpiarControles();
                activarControles();
                
                btnNuevo.setText("Guardar");
                iconNuevo = (new FontAwesomeIconView(FontAwesomeIcon.SAVE));
                btnNuevo.setGraphic(iconNuevo);
                iconNuevo.setFill(WHITE);
                iconNuevo.setGlyphSize(28);
                iconNuevo.setWrappingWidth(140);

                btnEliminar.setText("Cancelar");
                iconEliminar = (new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE));
                btnEliminar.setGraphic(iconEliminar);
                iconEliminar.setFill(WHITE);
                iconEliminar.setGlyphSize(29);
                iconEliminar.setWrappingWidth(140);
                
                btnEditar.setText("");
                btnEditar.setDisable(true);
                iconEditar.setFill(Color.rgb(35,54,112));
                
                btnReporte.setText("");
                btnReporte.setDisable(true);
                iconReporte.setFill(Color.rgb(35,54,112));
                
                btnCuentasPorPagar.setText("");
                btnCuentasPorPagar.setDisable(true);
                iconCuentasPorPagar.setFill(Color.rgb(35,54,112));

                tipoDeOperacion = operaciones.GUARDAR;
                break;  
                
                
            case GUARDAR:
                
                guardar();
                desactivarControles();
                limpiarControles();

                btnNuevo.setText("Nuevo");
                iconNuevo = (new FontAwesomeIconView(FontAwesomeIcon.PLUS_SQUARE));
                btnNuevo.setGraphic(iconNuevo);
                iconNuevo.setFill(WHITE);
                iconNuevo.setGlyphSize(28);
                iconNuevo.setWrappingWidth(134);
                
                btnEliminar.setText("Eliminar");
                iconEliminar = (new FontAwesomeIconView(FontAwesomeIcon.TRASH_ALT));
                btnEliminar.setGraphic(iconEliminar);
                iconEliminar.setFill(WHITE);
                iconEliminar.setGlyphSize(29);
                iconEliminar.setWrappingWidth(134);
                
                btnEditar.setText("Editar");
                btnEditar.setDisable(false);
                iconEditar.setFill(WHITE);
                
                btnReporte.setText("Reporte");
                btnReporte.setDisable(false);
                iconReporte.setFill(WHITE);
                
                btnCuentasPorPagar.setText("       Cuentas por \n" +
                                           "       Pagar");
                btnCuentasPorPagar.setDisable(false);
                iconCuentasPorPagar.setFill(Color.rgb(243,156,18));
                
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;                     
        }
    }
    
    
    public void eliminar()
    {
        
        switch (tipoDeOperacion)
        {
            case GUARDAR:
                desactivarControles();
                limpiarControles();
                
                btnNuevo.setText("Nuevo");
                iconNuevo = (new FontAwesomeIconView(FontAwesomeIcon.PLUS_SQUARE));
                btnNuevo.setGraphic(iconNuevo);
                iconNuevo.setFill(WHITE);
                iconNuevo.setGlyphSize(28);
                iconNuevo.setWrappingWidth(134);
                
                btnEliminar.setText("Eliminar");
                iconEliminar = (new FontAwesomeIconView(FontAwesomeIcon.TRASH_ALT));
                btnEliminar.setGraphic(iconEliminar);
                iconEliminar.setFill(WHITE);
                iconEliminar.setGlyphSize(29);
                iconEliminar.setWrappingWidth(134);
                
                btnEditar.setText("Editar");
                btnEditar.setDisable(false);
                iconEditar.setFill(WHITE);
                
                btnReporte.setText("Reporte");
                btnReporte.setDisable(false);
                iconReporte.setFill(WHITE);
                
                btnCuentasPorPagar.setText("       Cuentas por \n" +
                                           "       Pagar");
                btnCuentasPorPagar.setDisable(false);
                iconCuentasPorPagar.setFill(Color.rgb(243,156,18));
                
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
                
            default:
                if (tblProveedores.getSelectionModel().getSelectedItem() != null)
                {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿ Está seguro de ELIMINAR el registro ?",
                                                                  "ELIMINAR PROVEEDOR", JOptionPane.YES_NO_OPTION,
                                                                  JOptionPane.QUESTION_MESSAGE);
                    
                    if (respuesta == JOptionPane.YES_NO_OPTION)
                    {
                        try
                        {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarProveedor(?)}");
                            procedimiento.setInt(1, ((Proveedor) tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor());
                            procedimiento.execute();
                            
                            listaProveedores.remove(tblProveedores.getSelectionModel().getSelectedIndex());
                            
                            limpiarControles();                       
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }  
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Debe seleccionar un elemento.", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                break;         
        }
    }
    
    
    public void editar()
    {
        switch (tipoDeOperacion)
        {
            
            case NINGUNO:
                if (tblProveedores.getSelectionModel().getSelectedItem() != null)
                {
                    activarControlesActualizar();
                    
                    btnEditar.setText("Actualizar");
                    iconEditar = (new FontAwesomeIconView(FontAwesomeIcon.REFRESH));
                    btnEditar.setGraphic(iconEditar);
                    iconEditar.setFill(WHITE);
                    iconEditar.setGlyphSize(28);
                    iconEditar.setWrappingWidth(140);

                    btnReporte.setText("Cancelar");
                    iconReporte = (new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE));
                    btnReporte.setGraphic(iconReporte);
                    iconReporte.setFill(WHITE);
                    iconReporte.setGlyphSize(29);
                    iconReporte.setWrappingWidth(140);

                    btnNuevo.setText("");
                    btnNuevo.setDisable(true);
                    iconNuevo.setFill(Color.rgb(35,54,112));

                    btnEliminar.setText("");
                    btnEliminar.setDisable(true);
                    iconEliminar.setFill(Color.rgb(35,54,112));
                    
                    btnCuentasPorPagar.setText("");
                    btnCuentasPorPagar.setDisable(true);
                    iconCuentasPorPagar.setFill(Color.rgb(35,54,112));

                    tipoDeOperacion = operaciones.ACTUALIZAR;
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Debe seleccionar un elemento.", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                break;
                
                
            case ACTUALIZAR:
                
                actualizar();
                desactivarControles();
                limpiarControles();
                
                btnEditar.setText("Editar");
                iconEditar = (new FontAwesomeIconView(FontAwesomeIcon.EDIT));
                btnEditar.setGraphic(iconEditar);
                iconEditar.setFill(WHITE);
                iconEditar.setGlyphSize(29);
                iconEditar.setWrappingWidth(137);
                
                btnReporte.setText("Reporte");
                iconReporte = (new FontAwesomeIconView(FontAwesomeIcon.FILE));
                btnReporte.setGraphic(iconReporte);
                iconReporte.setFill(WHITE);
                iconReporte.setGlyphSize(26);
                iconReporte.setWrappingWidth(134);
                
                btnNuevo.setText("Nuevo");
                btnNuevo.setDisable(false);
                iconNuevo.setFill(WHITE);
                
                btnEliminar.setText("Eliminar");
                btnEliminar.setDisable(false);
                iconEliminar.setFill(WHITE);
                
                btnCuentasPorPagar.setText("       Cuentas por \n" +
                                           "       Pagar");
                btnCuentasPorPagar.setDisable(false);
                iconCuentasPorPagar.setFill(Color.rgb(243,156,18));
                
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;             
        }
    }
    
    
    public void reporte()
    {
        switch (tipoDeOperacion)
        {
            
            case ACTUALIZAR:
                
                desactivarControles();
                limpiarControles();
                
                btnEditar.setText("Editar");
                iconEditar = (new FontAwesomeIconView(FontAwesomeIcon.EDIT));
                btnEditar.setGraphic(iconEditar);
                iconEditar.setFill(WHITE);
                iconEditar.setGlyphSize(29);
                iconEditar.setWrappingWidth(137);
                
                btnReporte.setText("Reporte");
                iconReporte = (new FontAwesomeIconView(FontAwesomeIcon.FILE));
                btnReporte.setGraphic(iconReporte);
                iconReporte.setFill(WHITE);
                iconReporte.setGlyphSize(26);
                iconReporte.setWrappingWidth(134);
                
                btnNuevo.setText("Nuevo");
                btnNuevo.setDisable(false);
                iconNuevo.setFill(WHITE);
                
                btnEliminar.setText("Eliminar");
                btnEliminar.setDisable(false);
                iconEliminar.setFill(WHITE);
                
                btnCuentasPorPagar.setText("       Cuentas por \n" +
                                           "       Pagar");
                btnCuentasPorPagar.setDisable(false);
                iconCuentasPorPagar.setFill(Color.rgb(243,156,18));
                
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    
    
    // ACCIONES DEL CRUD
    
    public void guardar()
    {
        
        Proveedor registro = new Proveedor();
        
        registro.setNITProveedor(txtNITProveedor.getText());
        registro.setServicioPrestado(txtServicioPrestado.getText());
        registro.setTelefonoProveedor(txtTelefonoProveedor.getText());
        registro.setDireccionProveedor(txtDireccionProveedor.getText());
        registro.setSaldoFavor(Double.parseDouble(txtSaldoFavor.getText()));
        registro.setSaldoContra(Double.parseDouble(txtSaldoContra.getText()));
        registro.setCodigoAdministracion(((Administracion)cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion()); 
        
        try
        {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarProveedor(?, ?, ?, ?, ?, ?, ?)}");
            
            procedimiento.setString(1, registro.getNITProveedor());
            procedimiento.setString(2, registro.getServicioPrestado());
            procedimiento.setString(3, registro.getTelefonoProveedor());
            procedimiento.setString(4, registro.getDireccionProveedor());
            procedimiento.setDouble(5, registro.getSaldoFavor());
            procedimiento.setDouble(6, registro.getSaldoContra());
            procedimiento.setInt(7, registro.getCodigoAdministracion());
            
            procedimiento.execute();
            
            listaProveedores.add(registro);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
  
    public void actualizar()
    {
        try
        {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarProveedor(?, ?, ?, ?, ?, ?, ?)}");
            Proveedor registro = (Proveedor) tblProveedores.getSelectionModel().getSelectedItem();
            
            registro.setNITProveedor(txtNITProveedor.getText());
            registro.setServicioPrestado(txtServicioPrestado.getText());
            registro.setTelefonoProveedor(txtTelefonoProveedor.getText());
            registro.setDireccionProveedor(txtDireccionProveedor.getText());
            registro.setSaldoFavor(Double.parseDouble(txtSaldoFavor.getText()));
            registro.setSaldoContra(Double.parseDouble(txtSaldoContra.getText()));
            
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNITProveedor());
            procedimiento.setString(3, registro.getServicioPrestado());
            procedimiento.setString(4, registro.getTelefonoProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setDouble(6, registro.getSaldoFavor());
            procedimiento.setDouble(7, registro.getSaldoContra());

            procedimiento.execute();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    
    
    // CONTROLES
        
    public void desactivarControles()
    {
        txtCodigoProveedor.setEditable(false);
        txtNITProveedor.setEditable(false);
        txtServicioPrestado.setEditable(false);
        txtTelefonoProveedor.setEditable(false);
        txtDireccionProveedor.setEditable(false);
        txtSaldoFavor.setEditable(false);
        txtSaldoContra.setEditable(false);
        cmbCodigoAdministracion.setDisable(true);
    }
    
    
    public void activarControles()
    {
        txtCodigoProveedor.setEditable(false);
        txtNITProveedor.setEditable(true);
        txtServicioPrestado.setEditable(true);
        txtTelefonoProveedor.setEditable(true);
        txtDireccionProveedor.setEditable(true);
        txtSaldoFavor.setEditable(true);
        txtSaldoContra.setEditable(true);
        cmbCodigoAdministracion.setDisable(false);
    }
    
    
    public void activarControlesActualizar()
    {
        txtCodigoProveedor.setEditable(false);
        txtNITProveedor.setEditable(true);
        txtServicioPrestado.setEditable(true);
        txtTelefonoProveedor.setEditable(true);
        txtDireccionProveedor.setEditable(true);
        txtSaldoFavor.setEditable(true);
        txtSaldoContra.setEditable(true);
        cmbCodigoAdministracion.setDisable(true);
    }
    
    
    public void limpiarControles()
    {
        txtCodigoProveedor.clear();
        txtNITProveedor.clear();
        txtServicioPrestado.clear();
        txtTelefonoProveedor.clear();
        txtDireccionProveedor.clear();
        txtSaldoFavor.clear();
        txtSaldoContra.clear();
        cmbCodigoAdministracion.getSelectionModel().clearSelection();
    }
    
    
    
    // MÉTODOS DE ACCESO ESCENAS
    
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
    
    
    public void ventanaCuentasPorPagar()
    {
        escenarioPrincipal.ventanaCuentasPorPagar();
    }
    
}
