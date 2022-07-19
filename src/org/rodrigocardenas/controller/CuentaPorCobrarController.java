
package org.rodrigocardenas.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import org.rodrigocardenas.bean.Cliente;
import org.rodrigocardenas.bean.CuentaPorCobrar;
import org.rodrigocardenas.bean.Local;
import org.rodrigocardenas.db.Conexion;
import org.rodrigocardenas.report.GenerarReporte;
import org.rodrigocardenas.system.Principal;

public class CuentaPorCobrarController implements Initializable
{
    
    private Principal escenarioPrincipal;
    
    private enum operaciones 
    {
        NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    
    private operaciones tipoDeOperacion = operaciones.NINGUNO;

    private ObservableList <CuentaPorCobrar> listaCuentasPorCobrar;
    private ObservableList <Administracion> listaAdministracion;
    private ObservableList <Cliente> listaClientes;  
    private ObservableList <Local> listaLocales; 
    
    
    // INYECCIONES JAVAFX
    
    @FXML private JFXButton btnNuevo;
    @FXML private JFXButton btnEditar;
    @FXML private JFXButton btnEliminar;
    @FXML private JFXButton btnReporte;
    @FXML private JFXButton btnRegresar;
    
    @FXML private TextField txtCodigoCuentaPorCobrar;
    @FXML private TextField txtNumeroFactura;
    @FXML private TextField txtAnio;
    @FXML private TextField txtMes;
    @FXML private TextField txtValorNetoPago;
    @FXML private TextField txtEstadoPago;
    
    @FXML private JFXComboBox cmbCodigoAdministracion;
    @FXML private JFXComboBox cmbCodigoCliente;
    @FXML private JFXComboBox cmbCodigoLocal;
    
    @FXML private TableView tblCuentasPorCobrar;
    
    @FXML private TableColumn colCodigoCuentaPorCobrar;
    @FXML private TableColumn colNumeroFactura;
    @FXML private TableColumn colAnio;
    @FXML private TableColumn colMes;
    @FXML private TableColumn colValorNetoPago;
    @FXML private TableColumn colEstadoPago;
    @FXML private TableColumn colCodigoAdministracion;
    @FXML private TableColumn colCodigoCliente;
    @FXML private TableColumn colCodigoLocal;
    
    @FXML private FontAwesomeIconView iconNuevo;
    @FXML private FontAwesomeIconView iconEditar;
    @FXML private FontAwesomeIconView iconEliminar;
    @FXML private FontAwesomeIconView iconReporte;
    @FXML private FontAwesomeIconView iconRegresar;

    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        cargarDatos();
    }   
    
    
    
    public void cargarDatos()
    {
        tblCuentasPorCobrar.setItems(getCuentaPorCobrar());
        
        colCodigoCuentaPorCobrar.setCellValueFactory(new PropertyValueFactory <CuentaPorCobrar, Integer>("codigoCuentaPorCobrar"));
        colNumeroFactura.setCellValueFactory(new PropertyValueFactory <CuentaPorCobrar, String>("numeroFactura"));
        colAnio.setCellValueFactory(new PropertyValueFactory <CuentaPorCobrar, Integer>("anio"));
        colMes.setCellValueFactory(new PropertyValueFactory <CuentaPorCobrar, Integer>("mes"));
        colValorNetoPago.setCellValueFactory(new PropertyValueFactory <CuentaPorCobrar, Double>("valorNetoPagoTexto"));
        colEstadoPago.setCellValueFactory(new PropertyValueFactory <CuentaPorCobrar, String>("estadoPago"));
        colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory <Administracion, Integer>("codigoAdministracion"));
        colCodigoCliente.setCellValueFactory(new PropertyValueFactory <Cliente, Integer>("codigoCliente"));
        colCodigoLocal.setCellValueFactory(new PropertyValueFactory <Local, Integer>("codigoLocal"));
        
        cmbCodigoAdministracion.setItems(getAdministracion());
        cmbCodigoCliente.setItems(getCliente());
        cmbCodigoLocal.setItems(getLocal());
    }
    
    
    
    public ObservableList <CuentaPorCobrar> getCuentaPorCobrar()
    {
        ArrayList <CuentaPorCobrar> lista = new ArrayList <CuentaPorCobrar>();
        try
        {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCuentasPorCobrar()}");
            ResultSet resultado = procedimiento.executeQuery();
            
            while (resultado.next())
            {
                lista.add(new CuentaPorCobrar(resultado.getInt("codigoCuentaPorCobrar"),
                                             resultado.getString("numeroFactura"),
                                             resultado.getInt("anio"),   
                                             resultado.getInt("mes"),
                                             resultado.getDouble("valorNetoPago"),
                                             resultado.getString("estadoPago"),
                                             resultado.getInt("codigoAdministracion"),
                                             resultado.getInt("codigoCliente"),
                                             resultado.getInt("codigoLocal")));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return listaCuentasPorCobrar = FXCollections.observableArrayList(lista);
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
    
    
    //VALORES CLIENTE
    
    public ObservableList <Cliente> getCliente()
    {
        ArrayList <Cliente> lista = new ArrayList <Cliente> ();
        
        try
        {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarClientes()}");
            ResultSet resultado = procedimiento.executeQuery();
            
            while(resultado.next())
            {
                lista.add(new Cliente(resultado.getInt("codigoCliente"),
                                      resultado.getString("nombresCliente"),
                                      resultado.getString("apellidosCliente"),
                                      resultado.getString("telefonoCliente"),
                                      resultado.getString("direccionCliente"),
                                      resultado.getString("email"),
                                      resultado.getInt("codigoLocal"),
                                      resultado.getInt("codigoAdministracion"),
                                      resultado.getInt("codigoTipoCliente")));
            }        
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return listaClientes = FXCollections.observableArrayList(lista);
    }
    
    
    //VALORES LOCAL
    
    public ObservableList <Local> getLocal()
    {
        ArrayList <Local> lista = new ArrayList <Local>();
        try
        {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarLocales()}");
            ResultSet resultado = procedimiento.executeQuery();
            
            while(resultado.next())
            {
                lista.add(new Local(resultado.getInt("codigoLocal"),
                                      resultado.getDouble("saldoFavor"),
                                      resultado.getDouble("saldoContra"),
                                      resultado.getInt("mesesPendientes"),
                                      resultado.getBoolean("disponibilidad"),
                                      resultado.getDouble("valorLocal"),
                                      resultado.getDouble("valorAdministracion")));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return listaLocales = FXCollections.observableArrayList(lista);
    }
    
    
    public void seleccionarElemento()
    {
        if(tblCuentasPorCobrar.getSelectionModel().getSelectedItem() != null)
        {
            txtCodigoCuentaPorCobrar.setText(String.valueOf(((CuentaPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoCuentaPorCobrar()));
            txtNumeroFactura.setText(((CuentaPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getNumeroFactura());
            txtAnio.setText(String.valueOf(((CuentaPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getAnio()));
            txtMes.setText(String.valueOf(((CuentaPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getMes()));
            txtValorNetoPago.setText(String.valueOf(((CuentaPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getValorNetoPago()));
            txtEstadoPago.setText(((CuentaPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getEstadoPago());
                        
            // ESTO HACE QUE SI ME MUESTRE DATOS SIN ELEGIR ANTES
            cmbCodigoAdministracion.getSelectionModel().selectFirst();
            cmbCodigoCliente.getSelectionModel().selectFirst();
            cmbCodigoLocal.getSelectionModel().selectFirst();
            
            cmbCodigoAdministracion.getSelectionModel().select(buscarAdministracion(((CuentaPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
            cmbCodigoCliente.getSelectionModel().select(buscarCliente(((CuentaPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoCliente()));
            cmbCodigoLocal.getSelectionModel().select(buscarLocal(((CuentaPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoLocal()));
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
    
   
    public Cliente buscarCliente (int codigoCliente)
    {
        Cliente resultado = null;
        try
        {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarCliente(?)}");
            procedimiento.setInt(1, codigoCliente);
            ResultSet registro = procedimiento.executeQuery();
            
            while(registro.next())
            {
                resultado = new Cliente(registro.getInt("codigoCliente"),
                                        registro.getString("nombresCliente"),
                                        registro.getString("apellidosCliente"),
                                        registro.getString("telefonoCliente"),
                                        registro.getString("direccionCliente"),
                                        registro.getString("email"),
                                        registro.getInt("codigoLocal"),
                                        registro.getInt("codigoAdministracion"),
                                        registro.getInt("codigoTipoCliente"));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return resultado;
    }
    
    
    public Local buscarLocal(int codigoLocal)
    {
        Local resultado = null;
        
        try
        {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarLocal(?)}");
            procedimiento.setInt(1, codigoLocal);
            ResultSet registro = procedimiento.executeQuery();
            
            while(registro.next())
            {
                resultado = new Local(registro.getInt("codigoLocal"),
                                      registro.getDouble("saldoFavor"),
                                      registro.getDouble("saldoContra"),
                                      registro.getInt("mesesPendientes"),
                                      registro.getBoolean("disponibilidad"),
                                      registro.getDouble("valorLocal"),
                                      registro.getDouble("valorAdministracion"));
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
                if (tblCuentasPorCobrar.getSelectionModel().getSelectedItem() != null)
                {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿ Está seguro de ELIMINAR el registro ?",
                                                                  "ELIMINAR CUENTA POR COBRAR", JOptionPane.YES_NO_OPTION,
                                                                  JOptionPane.QUESTION_MESSAGE);
                    
                    if (respuesta == JOptionPane.YES_NO_OPTION)
                    {
                        try
                        {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCuentaPorCobrar(?)}");
                            procedimiento.setInt(1, ((CuentaPorCobrar) tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoCuentaPorCobrar());
                            procedimiento.execute();
                            
                            listaCuentasPorCobrar.remove(tblCuentasPorCobrar.getSelectionModel().getSelectedIndex());
                            
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
                if (tblCuentasPorCobrar.getSelectionModel().getSelectedItem() != null)
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
            
                
            case NINGUNO:
                
                imprimirReporte();
                break;
        }
    }
    
    
    
    // ACCIONES DEL CRUD
    
    public void guardar()
    {

        CuentaPorCobrar registro = new CuentaPorCobrar();
        
        registro.setNumeroFactura(txtNumeroFactura.getText());
        registro.setAnio(Integer.parseInt(txtAnio.getText()));
        registro.setMes(Integer.parseInt(txtMes.getText()));
        registro.setValorNetoPago(Double.parseDouble(txtValorNetoPago.getText()));
        registro.setEstadoPago(txtEstadoPago.getText());
        registro.setCodigoAdministracion(((Administracion)cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion()); 
        registro.setCodigoCliente(((Cliente)cmbCodigoCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
        registro.setCodigoLocal(((Local)cmbCodigoLocal.getSelectionModel().getSelectedItem()).getCodigoLocal());
       
        if(txtAnio.getText().length() != 4)
        {
            JOptionPane.showMessageDialog(null,"El año debe de constar de 4 dígitos.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
        else 
        {
            try
            {
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCuentaPorCobrar(?, ?, ?, ?, ?, ?, ?, ?)}");

                procedimiento.setString(1, registro.getNumeroFactura());
                procedimiento.setInt(2, registro.getAnio());
                procedimiento.setInt(3, registro.getMes());
                procedimiento.setDouble(4, registro.getValorNetoPago());
                procedimiento.setString(5, registro.getEstadoPago());
                procedimiento.setInt(6, registro.getCodigoAdministracion());
                procedimiento.setInt(7, registro.getCodigoCliente());
                procedimiento.setInt(8, registro.getCodigoLocal());

                procedimiento.execute();

                listaCuentasPorCobrar.add(registro);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
  
    public void actualizar()
    {
        try
        {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCuentaPorCobrar(?, ?, ?, ?, ?, ?)}");
            CuentaPorCobrar registro = (CuentaPorCobrar) tblCuentasPorCobrar.getSelectionModel().getSelectedItem();
            
            registro.setNumeroFactura(txtNumeroFactura.getText());
            registro.setAnio(Integer.parseInt(txtAnio.getText()));
            registro.setMes(Integer.parseInt(txtMes.getText()));
            registro.setValorNetoPago(Double.parseDouble(txtValorNetoPago.getText()));
            registro.setEstadoPago(txtEstadoPago.getText());
            registro.setCodigoAdministracion(((Administracion)cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion()); 
            registro.setCodigoCliente(((Cliente)cmbCodigoCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
            registro.setCodigoLocal(((Local)cmbCodigoLocal.getSelectionModel().getSelectedItem()).getCodigoLocal());
            
            procedimiento.setInt(1, registro.getCodigoCuentaPorCobrar());
            procedimiento.setString(2, registro.getNumeroFactura());
            procedimiento.setInt(3, registro.getAnio());
            procedimiento.setInt(4, registro.getMes());
            procedimiento.setDouble(5, registro.getValorNetoPago());
            procedimiento.setString(6, registro.getEstadoPago());

            procedimiento.execute();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    // IMPRIMIR REPORTE
    
    public void imprimirReporte()
    {
        if (tblCuentasPorCobrar.getSelectionModel().getSelectedItem() != null)
        {
            Map parametros = new HashMap();
            int codigoCuentaPorCobrar = ((CuentaPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoCuentaPorCobrar();
            String ReporteHorizontal = "/org/rodrigocardenas/images/ReporteHorizontal.png";
            parametros.put("codigoCuentaPorCobrar", codigoCuentaPorCobrar);
            parametros.put("ReporteHorizontal", this.getClass().getResourceAsStream(ReporteHorizontal));
            GenerarReporte.mostrarReporte("ReporteCuentasPorCobrar.jasper", "Reporte de Cuentas Por Cobrar", parametros);
        }
        
        else
        {
            JOptionPane.showMessageDialog(null,"Debe seleccionar un elemento.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    // CONTROLES
        
    public void desactivarControles()
    {
        txtCodigoCuentaPorCobrar.setEditable(false);
        txtNumeroFactura.setEditable(false);
        txtAnio.setEditable(false);
        txtMes.setEditable(false);
        txtValorNetoPago.setEditable(false);
        txtEstadoPago.setEditable(false);

        cmbCodigoAdministracion.setDisable(true);
        cmbCodigoCliente.setDisable(true);
        cmbCodigoLocal.setDisable(true);
    }
    
    
    public void activarControles()
    {
        txtCodigoCuentaPorCobrar.setEditable(false);
        txtNumeroFactura.setEditable(true);
        txtAnio.setEditable(true);
        txtMes.setEditable(true);
        txtValorNetoPago.setEditable(true);
        txtEstadoPago.setEditable(true);

        cmbCodigoAdministracion.setDisable(false);
        cmbCodigoCliente.setDisable(false);
        cmbCodigoLocal.setDisable(false);
    }
    
    public void activarControlesActualizar()
    {
        txtCodigoCuentaPorCobrar.setEditable(false);
        txtNumeroFactura.setEditable(true);
        txtAnio.setEditable(true);
        txtMes.setEditable(true);
        txtValorNetoPago.setEditable(true);
        txtEstadoPago.setEditable(true);

        cmbCodigoAdministracion.setDisable(true);
        cmbCodigoCliente.setDisable(true);
        cmbCodigoLocal.setDisable(true);
    }
    
    
    public void limpiarControles()
    {
        txtCodigoCuentaPorCobrar.clear();
        txtNumeroFactura.clear();
        txtAnio.clear();
        txtMes.clear();
        txtValorNetoPago.clear();
        txtEstadoPago.clear();

        cmbCodigoAdministracion.getSelectionModel().clearSelection();
        cmbCodigoCliente.getSelectionModel().clearSelection();
        cmbCodigoLocal.getSelectionModel().clearSelection();
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
    
    
    public void ventanaLocales()
    {
        escenarioPrincipal.ventanaLocales();
    }
}
