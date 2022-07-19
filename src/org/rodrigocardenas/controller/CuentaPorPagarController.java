
package org.rodrigocardenas.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import eu.schudt.javafx.controls.calendar.DatePicker;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.WHITE;
import javax.swing.JOptionPane;
import org.rodrigocardenas.bean.Administracion;
import org.rodrigocardenas.bean.Proveedor;
import org.rodrigocardenas.bean.CuentaPorPagar;
import org.rodrigocardenas.db.Conexion;
import org.rodrigocardenas.system.Principal;

public class CuentaPorPagarController implements Initializable
{
    
    private Principal escenarioPrincipal;
    
    private enum operaciones 
    {
        NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    
    private operaciones tipoDeOperacion = operaciones.NINGUNO;

    private ObservableList <CuentaPorPagar> listaCuentasPorPagar;
    private ObservableList <Administracion> listaAdministracion;
    private ObservableList <Proveedor> listaProveedores;
    
    private DatePicker fechaLimite;
    
    
    
    // INYECCIONES JAVAFX
    
    @FXML private JFXButton btnNuevo;
    @FXML private JFXButton btnEditar;
    @FXML private JFXButton btnEliminar;
    @FXML private JFXButton btnReporte;
    @FXML private JFXButton btnRegresar;
    
    @FXML private TextField txtCodigoCuentaPorPagar;
    @FXML private TextField txtNumeroFactura;
    @FXML private TextField txtEstadoPago;
    @FXML private TextField txtValorNetoPago;
    
    @FXML private JFXComboBox cmbCodigoAdministracion;
    @FXML private JFXComboBox cmbCodigoProveedor;
    
    @FXML private TableView tblCuentasPorPagar;
    
    @FXML private TableColumn colCodigoCuentaPorPagar;
    @FXML private TableColumn colNumeroFactura;
    @FXML private TableColumn colFechaLimitePago;
    @FXML private TableColumn colEstadoPago;
    @FXML private TableColumn colValorNetoPago;
    @FXML private TableColumn colCodigoAdministracion;
    @FXML private TableColumn colCodigoProveedor;
    
    @FXML private GridPane grpFechaLimite;
    
    @FXML private FontAwesomeIconView iconNuevo;
    @FXML private FontAwesomeIconView iconEditar;
    @FXML private FontAwesomeIconView iconEliminar;
    @FXML private FontAwesomeIconView iconReporte;
    @FXML private FontAwesomeIconView iconRegresar;

    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        fechaLimite = new DatePicker(Locale.ENGLISH);
        fechaLimite.setDateFormat(new SimpleDateFormat("yyy-MM-dd"));
        fechaLimite.getCalendarView().todayButtonTextProperty().set("Today");
        fechaLimite.getCalendarView().setShowWeeks(false);
        grpFechaLimite.add(fechaLimite, 5, 0);
        fechaLimite.setDisable(true);
        fechaLimite.getStylesheets().add("/org/rodrigocardenas/resource/DatePicker.css");
        cargarDatos();
    }  
    
    
    
    public void cargarDatos()
    {
        tblCuentasPorPagar.setItems(getCuentaPorPagar());
        
        colCodigoCuentaPorPagar.setCellValueFactory(new PropertyValueFactory <CuentaPorPagar, Integer>("codigoCuentaPorPagar"));
        colNumeroFactura.setCellValueFactory(new PropertyValueFactory <CuentaPorPagar, String>("numeroFactura"));
        colFechaLimitePago.setCellValueFactory(new PropertyValueFactory <CuentaPorPagar, Date>("fechaLimitePago"));
        colEstadoPago.setCellValueFactory(new PropertyValueFactory <CuentaPorPagar, String>("estadoPago"));
        colValorNetoPago.setCellValueFactory(new PropertyValueFactory <CuentaPorPagar, Double>("valorNetoPagoTexto"));
        colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory <Administracion, Integer>("codigoAdministracion"));
        colCodigoProveedor.setCellValueFactory(new PropertyValueFactory <Proveedor, Integer>("codigoProveedor"));
        
        cmbCodigoAdministracion.setItems(getAdministracion());
        cmbCodigoProveedor.setItems(getProveedor());
    }
    
    
    
    public ObservableList <CuentaPorPagar> getCuentaPorPagar()
    {
        ArrayList <CuentaPorPagar> lista = new ArrayList <CuentaPorPagar>();
        try
        {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCuentasPorPagar()}");
            ResultSet resultado = procedimiento.executeQuery();
            
            while (resultado.next())
            {
                lista.add(new CuentaPorPagar(resultado.getInt("codigoCuentaPorPagar"),
                                             resultado.getString("numeroFactura"),
                                             resultado.getDate("fechaLimitePago"),   
                                             resultado.getString("estadoPago"),
                                             resultado.getDouble("valorNetoPago"),
                                             resultado.getInt("codigoAdministracion"),
                                             resultado.getInt("codigoProveedor")));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return listaCuentasPorPagar = FXCollections.observableArrayList(lista);
    }
    
    
    //VALORES PROVEEDOR
    
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
        if(tblCuentasPorPagar.getSelectionModel().getSelectedItem() != null)
        {
            txtCodigoCuentaPorPagar.setText(String.valueOf(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoCuentaPorPagar()));
            txtNumeroFactura.setText(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getNumeroFactura());
            txtEstadoPago.setText(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getEstadoPago());
            txtValorNetoPago.setText(String.valueOf(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getValorNetoPago()));
            
            fechaLimite.selectedDateProperty().set(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getFechaLimitePago());
            
            // ESTO HACE QUE SI ME MUESTRE DATOS SIN ELEGIR ANTES
            cmbCodigoAdministracion.getSelectionModel().selectFirst();
            cmbCodigoProveedor.getSelectionModel().selectFirst();
            
            cmbCodigoAdministracion.getSelectionModel().select(buscarAdministracion(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
            cmbCodigoProveedor.getSelectionModel().select(buscarProveedor(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoProveedor()));

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
    
   
    public Proveedor buscarProveedor(int codigoProveedor)
    {
        Proveedor resultado = null;
        try
        {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarProveedor(?)}");
            procedimiento.setInt(1, codigoProveedor);
            ResultSet registro = procedimiento.executeQuery();
            
            while(registro.next())
            {
                resultado = new Proveedor(registro.getInt("codigoProveedor"),
                                          registro.getString("NITProveedor"),
                                          registro.getString("servicioPrestado"),
                                          registro.getString("telefonoProveedor"),
                                          registro.getString("direccionProveedor"),
                                          registro.getDouble("saldoFavor"),
                                          registro.getDouble("saldoContra"),
                                          registro.getInt("codigoAdministracion"));
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
                
                btnRegresar.setText("");
                btnRegresar.setDisable(true);
                iconRegresar.setFill(Color.rgb(35,54,112));

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
                
                btnRegresar.setText(" Regresar");
                btnRegresar.setDisable(false);
                iconRegresar.setFill(Color.rgb(243,156,18));
                
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
                
                btnRegresar.setText(" Regresar");
                btnRegresar.setDisable(false);
                iconRegresar.setFill(Color.rgb(243,156,18));
                
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
                
            default:
                if (tblCuentasPorPagar.getSelectionModel().getSelectedItem() != null)
                {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿ Está seguro de ELIMINAR el registro ?",
                                                                  "ELIMINAR CUENTA POR PAGAR", JOptionPane.YES_NO_OPTION,
                                                                  JOptionPane.QUESTION_MESSAGE);
                    
                    if (respuesta == JOptionPane.YES_NO_OPTION)
                    {
                        try
                        {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCuentaPorPagar(?)}");
                            procedimiento.setInt(1, ((CuentaPorPagar) tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoCuentaPorPagar());
                            procedimiento.execute();
                            
                            listaCuentasPorPagar.remove(tblCuentasPorPagar.getSelectionModel().getSelectedIndex());
                            
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
                if (tblCuentasPorPagar.getSelectionModel().getSelectedItem() != null)
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
                    
                    btnRegresar.setText("");
                    btnRegresar.setDisable(true);
                    iconRegresar.setFill(Color.rgb(35,54,112));

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
                
                btnRegresar.setText(" Regresar");
                btnRegresar.setDisable(false);
                iconRegresar.setFill(Color.rgb(243,156,18));
                
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
                
                btnRegresar.setText(" Regresar");
                btnRegresar.setDisable(false);
                iconRegresar.setFill(Color.rgb(243,156,18));
                
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    
    
    // ACCIONES DEL CRUD
    
    public void guardar()
    {

        CuentaPorPagar registro = new CuentaPorPagar();
        
        registro.setNumeroFactura(txtNumeroFactura.getText());
        registro.setFechaLimitePago(fechaLimite.getSelectedDate());
        registro.setEstadoPago(txtEstadoPago.getText());
        registro.setValorNetoPago(Double.parseDouble(txtValorNetoPago.getText()));
        registro.setCodigoAdministracion(((Administracion)cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion()); 
        registro.setCodigoProveedor(((Proveedor)cmbCodigoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor()); 
       
        try
        {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCuentaPorPagar(?, ?, ?, ?, ?, ?)}");
            
            procedimiento.setString(1, registro.getNumeroFactura());
            procedimiento.setDate(2, new java.sql.Date(registro.getFechaLimitePago().getTime()));
            procedimiento.setString(3, registro.getEstadoPago());
            procedimiento.setDouble(4, registro.getValorNetoPago());
            procedimiento.setInt(5, registro.getCodigoAdministracion());
            procedimiento.setInt(6, registro.getCodigoProveedor());
            
            procedimiento.execute();
            
            listaCuentasPorPagar.add(registro);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCuentaPorPagar(?, ?, ?, ?, ?)}");
            CuentaPorPagar registro = (CuentaPorPagar) tblCuentasPorPagar.getSelectionModel().getSelectedItem();
            
            registro.setNumeroFactura(txtNumeroFactura.getText());
            registro.setFechaLimitePago(fechaLimite.getSelectedDate());
            registro.setEstadoPago(txtEstadoPago.getText());
            registro.setValorNetoPago(Double.parseDouble(txtValorNetoPago.getText()));
            
            procedimiento.setInt(1, registro.getCodigoCuentaPorPagar());
            procedimiento.setString(2, registro.getNumeroFactura());
            procedimiento.setDate(3, new java.sql.Date(registro.getFechaLimitePago().getTime()));
            procedimiento.setString(4, registro.getEstadoPago());
            procedimiento.setDouble(5, registro.getValorNetoPago());

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
        txtCodigoCuentaPorPagar.setEditable(false);
        txtNumeroFactura.setEditable(false);
        txtEstadoPago.setEditable(false);
        txtValorNetoPago.setEditable(false);
        
        cmbCodigoAdministracion.setDisable(true);
        cmbCodigoProveedor.setDisable(true);
        
        fechaLimite.setDisable(true);
    }
    
    
    public void activarControles()
    {
        txtCodigoCuentaPorPagar.setEditable(false);
        txtNumeroFactura.setEditable(true);
        txtEstadoPago.setEditable(true);
        txtValorNetoPago.setEditable(true);
        
        cmbCodigoAdministracion.setDisable(false);
        cmbCodigoProveedor.setDisable(false);
        
        fechaLimite.setDisable(false);
    }
    
    public void activarControlesActualizar()
    {
        txtCodigoCuentaPorPagar.setEditable(false);
        txtNumeroFactura.setEditable(true);
        txtEstadoPago.setEditable(true);
        txtValorNetoPago.setEditable(true);
       
        cmbCodigoAdministracion.setDisable(true);
        cmbCodigoProveedor.setDisable(true);
       
        fechaLimite.setDisable(false);
    }
    
    
    public void limpiarControles()
    {
        txtCodigoCuentaPorPagar.clear();
        txtNumeroFactura.clear();
        txtEstadoPago.clear();
        txtValorNetoPago.clear();
        
        cmbCodigoAdministracion.getSelectionModel().clearSelection();
        cmbCodigoProveedor.getSelectionModel().clearSelection();
        
        fechaLimite.setSelectedDate(null);
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
    
    
    public void ventanaProveedores()
    {
        escenarioPrincipal.ventanaProveedores();
    }
    
}
