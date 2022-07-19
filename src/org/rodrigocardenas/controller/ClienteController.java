
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
import org.rodrigocardenas.bean.Local;
import org.rodrigocardenas.bean.TipoCliente;
import org.rodrigocardenas.db.Conexion;
import org.rodrigocardenas.report.GenerarReporte;
import org.rodrigocardenas.system.Principal;

public class ClienteController implements Initializable
{
    
    private Principal escenarioPrincipal;

    private enum operaciones
    {
        NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList <Cliente> listaClientes;
    private ObservableList <Administracion> listaAdministracion;
    private ObservableList <Local> listaLocales;
    private ObservableList <TipoCliente> listaTiposClientes;


    
    // INYECCIONES JAVAFX
    
    @FXML private JFXButton btnNuevo;
    @FXML private JFXButton btnEditar;
    @FXML private JFXButton btnEliminar;
    @FXML private JFXButton btnReporte;
    @FXML private JFXButton btnRegresar;
    
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
    @FXML private TableColumn colEmailCliente;
    @FXML private TableColumn colCodigoLocal;
    @FXML private TableColumn colCodigoAdministracion;
    @FXML private TableColumn colCodigoTipoCliente;
    
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
        tblClientes.setItems(getCliente());
       
        colCodigoCliente.setCellValueFactory(new PropertyValueFactory <Cliente, Integer>("codigoCliente"));
        colNombresCliente.setCellValueFactory(new PropertyValueFactory <Cliente, String>("nombresCliente"));
        colApellidosCliente.setCellValueFactory(new PropertyValueFactory <Cliente, String>("apellidosCliente"));
        colTelefonoCliente.setCellValueFactory(new PropertyValueFactory <Cliente, String>("telefonoCliente"));
        colDireccionCliente.setCellValueFactory(new PropertyValueFactory <Cliente, String>("direccionCliente"));
        colEmailCliente.setCellValueFactory(new PropertyValueFactory <Cliente, String>("emailCliente"));
        colCodigoLocal.setCellValueFactory(new PropertyValueFactory <Local, Integer>("codigoLocal"));
        colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory <Administracion, Integer>("codigoAdministracion"));
        colCodigoTipoCliente.setCellValueFactory(new PropertyValueFactory <TipoCliente, Integer>("codigoTipoCliente"));
        
        cmbCodigoLocal.setItems(getLocal());
        cmbCodigoAdministracion.setItems(getAdministracion());
        cmbCodigoTipoCliente.setItems(getTipoCliente());
    }
    
    
    
    // VALORES LOCAL
    
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
    
    
    
    // VALORES TIPO CLIENTE
    
    public ObservableList <TipoCliente> getTipoCliente()
    {
        
        ArrayList <TipoCliente> lista = new ArrayList <TipoCliente>();
        
        try
        {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTiposClientes()}");
            ResultSet resultado = procedimiento.executeQuery();
            
            while(resultado.next())
            {
                lista.add(new TipoCliente(resultado.getInt("codigoTipoCliente"), resultado.getString("descripcion")));
            }
        }
        
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return listaTiposClientes = FXCollections.observableArrayList(lista);   
    }
    
    
    
    // VALORES CLIENTE

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
    
    
    public void seleccionarElemento()
    {
        if (tblClientes.getSelectionModel().getSelectedItem() != null)
        {
            txtCodigoCliente.setText(String.valueOf(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente()));
            txtNombresCliente.setText(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getNombresCliente());
            txtApellidosCliente.setText(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getApellidosCliente());
            txtTelefonoCliente.setText(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getTelefonoCliente());
            txtDireccionCliente.setText(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getDireccionCliente());
            txtEmailCliente.setText(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getEmailCliente());
           
            // ESTO HACE QUE SI ME MUESTRE DATOS SIN ELEGIR ANTES
            cmbCodigoLocal.getSelectionModel().selectFirst();
            cmbCodigoAdministracion.getSelectionModel().selectFirst();
            cmbCodigoTipoCliente.getSelectionModel().selectFirst();
            
            
            cmbCodigoLocal.getSelectionModel().select(buscarLocal(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getCodigoLocal()));
            cmbCodigoAdministracion.getSelectionModel().select(buscarAdministracion(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
            cmbCodigoTipoCliente.getSelectionModel().select(buscarTipoCliente(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getCodigoTipoCliente()));
        }
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
                if (tblClientes.getSelectionModel().getSelectedItem() != null)
                {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿ Está seguro de ELIMINAR el registro ?",
                                                                  "ELIMINAR CLIENTE", JOptionPane.YES_NO_OPTION,
                                                                  JOptionPane.QUESTION_MESSAGE);
                    
                    if (respuesta == JOptionPane.YES_NO_OPTION)
                    {
                        try
                        {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCliente(?)}");
                            procedimiento.setInt(1, ((Cliente) tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente());
                            procedimiento.execute();
                            
                            listaClientes.remove(tblClientes.getSelectionModel().getSelectedIndex());
                            
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
                if (tblClientes.getSelectionModel().getSelectedItem() != null)
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
    
    
    
    // MÉTODOS BUSCAR PARA COMBOBOX
    
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
    
    
    public TipoCliente buscarTipoCliente(int codigoTipoCliente)
    {
        TipoCliente resultado = null;
        try
        {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_BuscarTipoCliente(?)");
            procedimiento.setInt(1, codigoTipoCliente);
            ResultSet registro = procedimiento.executeQuery();
            
            while(registro.next())
            {
                resultado = new TipoCliente(registro.getInt("codigoTipoCliente"),
                                            registro.getString("descripcion"));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return resultado;
    }
    
    
    // ACCIONES DEL CRUD
    
    public void guardar()
    {
        
        Cliente registro = new Cliente();
        
        registro.setNombresCliente(txtNombresCliente.getText());
        registro.setApellidosCliente(txtApellidosCliente.getText());
        registro.setTelefonoCliente(txtTelefonoCliente.getText());
        registro.setDireccionCliente(txtDireccionCliente.getText());
        registro.setEmailCliente(txtEmailCliente.getText());
        registro.setCodigoLocal(((Local)cmbCodigoLocal.getSelectionModel().getSelectedItem()).getCodigoLocal());
        registro.setCodigoAdministracion(((Administracion)cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
        registro.setCodigoTipoCliente(((TipoCliente)cmbCodigoTipoCliente.getSelectionModel().getSelectedItem()).getCodigoTipoCliente());
        
        try
        {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCliente(?, ?, ?, ?, ?, ?, ?, ?)}");
            
            procedimiento.setString(1, registro.getNombresCliente());
            procedimiento.setString(2, registro.getApellidosCliente());
            procedimiento.setString(3, registro.getTelefonoCliente());
            procedimiento.setString(4, registro.getDireccionCliente());
            procedimiento.setString(5, registro.getEmailCliente());
            procedimiento.setInt(6, registro.getCodigoLocal());
            procedimiento.setInt(7, registro.getCodigoAdministracion());
            procedimiento.setInt(8, registro.getCodigoTipoCliente());
            
            procedimiento.execute();
            
            listaClientes.add(registro);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCliente(?, ?, ?, ?, ?, ?)}");
            Cliente registro = (Cliente) tblClientes.getSelectionModel().getSelectedItem();
            
            registro.setNombresCliente(txtNombresCliente.getText());
            registro.setApellidosCliente(txtApellidosCliente.getText());
            registro.setTelefonoCliente(txtTelefonoCliente.getText());
            registro.setDireccionCliente(txtDireccionCliente.getText());
            registro.setEmailCliente(txtEmailCliente.getText());
            
            procedimiento.setInt(1, registro.getCodigoCliente());
            procedimiento.setString(2, registro.getNombresCliente());
            procedimiento.setString(3, registro.getApellidosCliente());
            procedimiento.setString(4, registro.getTelefonoCliente());
            procedimiento.setString(5, registro.getDireccionCliente());
            procedimiento.setString(6, registro.getEmailCliente());
        
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
        if (tblClientes.getSelectionModel().getSelectedItem() != null)
        {
            Map parametros = new HashMap();
            int codigoCliente = ((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente();
            String ReporteHorizontal = "/org/rodrigocardenas/images/ReporteHorizontal.png";
            parametros.put("codigoCliente", codigoCliente);
            parametros.put("ReporteHorizontal", this.getClass().getResourceAsStream(ReporteHorizontal));
            GenerarReporte.mostrarReporte("ReporteClientes.jasper", "Reporte de Clientes", parametros);
        }
        
        else
        {
            JOptionPane.showMessageDialog(null,"Debe seleccionar un elemento.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    
    // CONTROLES
        
    public void desactivarControles()
    {
        txtCodigoCliente.setEditable(false);
        txtNombresCliente.setEditable(false);
        txtApellidosCliente.setEditable(false);
        txtTelefonoCliente.setEditable(false);
        txtDireccionCliente.setEditable(false);
        txtEmailCliente.setEditable(false);
        
        cmbCodigoLocal.setDisable(true);
        cmbCodigoAdministracion.setDisable(true);
        cmbCodigoTipoCliente.setDisable(true);
    }
    
    
    public void activarControles()
    {
        txtCodigoCliente.setEditable(false);
        txtNombresCliente.setEditable(true);
        txtApellidosCliente.setEditable(true);
        txtTelefonoCliente.setEditable(true);
        txtDireccionCliente.setEditable(true);
        txtEmailCliente.setEditable(true);
        cmbCodigoLocal.setDisable(false);
        cmbCodigoAdministracion.setDisable(false);
        cmbCodigoTipoCliente.setDisable(false);
    }
    
    
    public void activarControlesActualizar()
    {
        txtCodigoCliente.setEditable(false);
        txtNombresCliente.setEditable(true);
        txtApellidosCliente.setEditable(true);
        txtTelefonoCliente.setEditable(true);
        txtDireccionCliente.setEditable(true);
        txtEmailCliente.setEditable(true);
        cmbCodigoLocal.setDisable(true);
        cmbCodigoAdministracion.setDisable(true);
        cmbCodigoTipoCliente.setDisable(true);
    }
    
    
    public void limpiarControles()
    {
        txtCodigoCliente.clear();
        txtNombresCliente.clear();
        txtApellidosCliente.clear();
        txtTelefonoCliente.clear();
        txtDireccionCliente.clear();
        txtEmailCliente.clear();
        cmbCodigoLocal.getSelectionModel().clearSelection();
        cmbCodigoAdministracion.getSelectionModel().clearSelection();
        cmbCodigoTipoCliente.getSelectionModel().clearSelection();
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
    
    
    public void ventanaTipoCliente()
    {
        escenarioPrincipal.ventanaTipoCliente();
    }
    
}
