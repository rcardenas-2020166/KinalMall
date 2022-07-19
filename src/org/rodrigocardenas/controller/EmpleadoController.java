
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
import java.util.HashMap;
import java.util.Locale;
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
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.WHITE;
import javax.swing.JOptionPane;
import org.rodrigocardenas.bean.Administracion;
import org.rodrigocardenas.bean.Cargo;
import org.rodrigocardenas.bean.Departamento;
import org.rodrigocardenas.bean.Empleado;
import org.rodrigocardenas.bean.Horario;
import org.rodrigocardenas.db.Conexion;
import org.rodrigocardenas.report.GenerarReporte;
import org.rodrigocardenas.system.Principal;

public class EmpleadoController implements Initializable
{
    
    private Principal escenarioPrincipal;

    private enum operaciones
    {
        NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList <Empleado> listaEmpleados;
    private ObservableList <Departamento> listaDepartamentos;
    private ObservableList <Cargo> listaCargos;
    private ObservableList <Horario> listaHorarios;
    private ObservableList <Administracion> listaAdministracion;
    
    private DatePicker fechaContratacion;

    
    // INYECCIONES JAVAFX
    
    @FXML private JFXButton btnNuevo;
    @FXML private JFXButton btnEditar;
    @FXML private JFXButton btnEliminar;
    @FXML private JFXButton btnReporte;
    @FXML private JFXButton btnRegresar;
    
    @FXML private TextField txtCodigoEmpleado;
    @FXML private TextField txtNombresEmpleado;
    @FXML private TextField txtApellidosEmpleado;
    @FXML private TextField txtCorreoElectronico;
    @FXML private TextField txtTelefonoEmpleado;
    @FXML private TextField txtSueldo;
    
    @FXML private JFXComboBox cmbCodigoDepartamento;
    @FXML private JFXComboBox cmbCodigoCargo;
    @FXML private JFXComboBox cmbCodigoHorario;
    @FXML private JFXComboBox cmbCodigoAdministracion;
    
    @FXML private TableView tblEmpleados;
    
    @FXML private TableColumn colCodigoEmpleado;
    @FXML private TableColumn colNombresEmpleado;
    @FXML private TableColumn colApellidosEmpleado;
    @FXML private TableColumn colCorreoElectronico;
    @FXML private TableColumn colTelefonoEmpleado;
    @FXML private TableColumn colFechaContratacion;
    @FXML private TableColumn colSueldo;
    @FXML private TableColumn colCodigoDepartamento;
    @FXML private TableColumn colCodigoCargo;
    @FXML private TableColumn colCodigoHorario;
    @FXML private TableColumn colCodigoAdministracion;
    
    @FXML private GridPane grpFechaContratacion;
    
    @FXML private FontAwesomeIconView iconNuevo;
    @FXML private FontAwesomeIconView iconEditar;
    @FXML private FontAwesomeIconView iconEliminar;
    @FXML private FontAwesomeIconView iconReporte;
    @FXML private FontAwesomeIconView iconRegresar;
    

    
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        fechaContratacion = new DatePicker(Locale.ENGLISH);
        fechaContratacion.setDateFormat(new SimpleDateFormat("yyy-MM-dd"));
        fechaContratacion.getCalendarView().todayButtonTextProperty().set("Today");
        fechaContratacion.getCalendarView().setShowWeeks(false);
        grpFechaContratacion.add(fechaContratacion, 5, 0);
        fechaContratacion.setDisable(true);
        fechaContratacion.getStylesheets().add("/org/rodrigocardenas/resource/DatePicker.css");
        cargarDatos();
    }
    
    
    public void cargarDatos()
    {
        tblEmpleados.setItems(getEmpleado());
       
        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory <Empleado, Integer>("codigoEmpleado"));
        colNombresEmpleado.setCellValueFactory(new PropertyValueFactory <Empleado, String>("nombresEmpleado"));
        colApellidosEmpleado.setCellValueFactory(new PropertyValueFactory <Empleado, String>("apellidosEmpleado"));
        colCorreoElectronico.setCellValueFactory(new PropertyValueFactory <Empleado, String>("correoElectronico"));
        colTelefonoEmpleado.setCellValueFactory(new PropertyValueFactory <Empleado, String>("telefonoEmpleado"));
        colFechaContratacion.setCellValueFactory(new PropertyValueFactory <Empleado, Date>("fechaContratacion"));
        colSueldo.setCellValueFactory(new PropertyValueFactory <Empleado, Double>("sueldoTexto"));
        colCodigoDepartamento.setCellValueFactory(new PropertyValueFactory <Departamento, Integer>("codigoDepartamento"));
        colCodigoCargo.setCellValueFactory(new PropertyValueFactory <Cargo, Integer>("codigoCargo"));
        colCodigoHorario.setCellValueFactory(new PropertyValueFactory <Horario, Integer>("codigoHorario"));
        colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory <Administracion, Integer>("codigoAdministracion"));
        
        cmbCodigoDepartamento.setItems(getDepartamento());
        cmbCodigoCargo.setItems(getCargo());
        cmbCodigoHorario.setItems(getHorario());
        cmbCodigoAdministracion.setItems(getAdministracion());
    }
    
    
    
    // VALORES DEPARTAMENTO
    
    public ObservableList <Departamento> getDepartamento()
    {
        ArrayList <Departamento> lista = new ArrayList <Departamento>();
        
        try
        {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarDepartamentos()}");
            ResultSet resultado = procedimiento.executeQuery();
            
            while(resultado.next())
            {
                lista.add(new Departamento(resultado.getInt("codigoDepartamento"), resultado.getString("nombreDepartamento")));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return listaDepartamentos = FXCollections.observableArrayList(lista);
    }
    
    
    
    // VALORES CARGO
    
    public ObservableList <Cargo> getCargo()
    {
        ArrayList <Cargo> lista = new ArrayList <Cargo>();
        
        try
        {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCargos()}");
            ResultSet resultado = procedimiento.executeQuery();
            
            while(resultado.next())
            {
                lista.add(new Cargo(resultado.getInt("codigoCargo"), resultado.getString("nombreCargo")));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return listaCargos = FXCollections.observableArrayList(lista);
    }
    
    
    
    // VALORES HORARIO
    
    public ObservableList <Horario> getHorario()
    {
        
        ArrayList <Horario> lista = new ArrayList <Horario>();
        
        try
        {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarHorarios()}");
            ResultSet resultado = procedimiento.executeQuery();
            
            while(resultado.next())
            {
                lista.add(new Horario(resultado.getInt("codigoHorario"),
                                      resultado.getString("horaEntrada"),
                                      resultado.getString("horaSalida"),
                                      resultado.getBoolean("lunes"),
                                      resultado.getBoolean("martes"),
                                      resultado.getBoolean("miercoles"),
                                      resultado.getBoolean("jueves"),
                                      resultado.getBoolean("viernes")));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return listaHorarios = FXCollections.observableArrayList(lista);
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
    
    
    
    // VALORES EMPLEADO
    
    public ObservableList <Empleado> getEmpleado()
    {
        ArrayList <Empleado> lista = new ArrayList <Empleado>();
        try
        {
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpleados()}");
            ResultSet resultado = procedimiento.executeQuery();
            
            while(resultado.next())
            {
                lista.add(new Empleado(resultado.getInt("codigoEmpleado"),
                                       resultado.getString("nombresEmpleado"),
                                       resultado.getString("apellidosEmpleado"),
                                       resultado.getString("correoElectronico"),
                                       resultado.getString("telefonoEmpleado"),
                                       resultado.getDate("fechaContratacion"),
                                       resultado.getDouble("sueldo"),
                                       resultado.getInt("codigoDepartamento"),
                                       resultado.getInt("codigoCargo"),
                                       resultado.getInt("codigoHorario"),
                                       resultado.getInt("codigoAdministracion")));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return listaEmpleados = FXCollections.observableArrayList(lista);
    }
    
    
    
    
    public void seleccionarElemento()
    {
        if (tblEmpleados.getSelectionModel().getSelectedItem() != null)
        {
            txtCodigoEmpleado.setText(String.valueOf(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
            txtNombresEmpleado.setText(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getNombresEmpleado());
            txtApellidosEmpleado.setText(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getApellidosEmpleado());
            txtCorreoElectronico.setText(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getCorreoElectronico());
            txtTelefonoEmpleado.setText(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getTelefonoEmpleado());
            txtSueldo.setText(String.valueOf(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getSueldo()));
            
            fechaContratacion.selectedDateProperty().set(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getFechaContratacion());

            // ESTO HACE QUE SI ME MUESTRE DATOS SIN ELEGIR ANTES
            cmbCodigoDepartamento.getSelectionModel().selectFirst();
            cmbCodigoCargo.getSelectionModel().selectFirst();
            cmbCodigoHorario.getSelectionModel().selectFirst();
            cmbCodigoAdministracion.getSelectionModel().selectFirst();        
            
            cmbCodigoDepartamento.getSelectionModel().select(buscarDepartamento(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoDepartamento()));
            cmbCodigoCargo.getSelectionModel().select(buscarCargo(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoCargo()));
            cmbCodigoHorario.getSelectionModel().select(buscarHorario(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoHorario()));
            cmbCodigoAdministracion.getSelectionModel().select(buscarAdministracion(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));           
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
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null)
                {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿ Está seguro de ELIMINAR el registro ?",
                                                                  "ELIMINAR EMPLEADO", JOptionPane.YES_NO_OPTION,
                                                                  JOptionPane.QUESTION_MESSAGE);
                    
                    if (respuesta == JOptionPane.YES_NO_OPTION)
                    {
                        try
                        {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEmpleado(?)}");
                            procedimiento.setInt(1, ((Empleado) tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
                            procedimiento.execute();
                            
                            listaEmpleados.remove(tblEmpleados.getSelectionModel().getSelectedIndex());
                            
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
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null)
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
    
    public Departamento buscarDepartamento(int codigoDepartamento)
    {
        Departamento resultado = null;
        
        try
        {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarDepartamento(?)}");
            procedimiento.setInt(1, codigoDepartamento);
            ResultSet registro = procedimiento.executeQuery();
            
            while(registro.next())
            {
                resultado = new Departamento(registro.getInt("codigoDepartamento"), registro.getString("nombreDepartamento"));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return resultado;
    }
    
    
    public Cargo buscarCargo(int codigoCargo)
    {
        Cargo resultado = null;
        
        try
        {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarCargo(?)}");
            procedimiento.setInt(1, codigoCargo);
            ResultSet registro = procedimiento.executeQuery();
            
            while(registro.next())
            {
                resultado = new Cargo(registro.getInt("codigoCargo"), registro.getString("nombreCargo"));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return resultado;
    }
    
    
    public Horario buscarHorario(int codigoHorario)
    {
        Horario resultado = null;
        
        try
        {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarHorario(?)}");
            procedimiento.setInt(1, codigoHorario);
            ResultSet registro = procedimiento.executeQuery();
            
            while(registro.next())
            {
                resultado = new Horario(registro.getInt("codigoHorario"),
                                        registro.getString("horaEntrada"),
                                        registro.getString("horaSalida"),
                                        registro.getBoolean("lunes"),
                                        registro.getBoolean("martes"),
                                        registro.getBoolean("miercoles"),
                                        registro.getBoolean("jueves"),
                                        registro.getBoolean("viernes"));
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
    
   
    
    // ACCIONES DEL CRUD
    
    public void guardar()
    {
        
        Empleado registro = new Empleado();
        
        registro.setNombresEmpleado(txtNombresEmpleado.getText());
        registro.setApellidosEmpleado(txtApellidosEmpleado.getText());
        registro.setCorreoElectronico(txtCorreoElectronico.getText());        
        registro.setTelefonoEmpleado(txtTelefonoEmpleado.getText());
        registro.setFechaContratacion(fechaContratacion.getSelectedDate());
        registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
        registro.setCodigoDepartamento(((Departamento)cmbCodigoDepartamento.getSelectionModel().getSelectedItem()).getCodigoDepartamento());
        registro.setCodigoCargo(((Cargo)cmbCodigoCargo.getSelectionModel().getSelectedItem()).getCodigoCargo());
        registro.setCodigoHorario(((Horario)cmbCodigoHorario.getSelectionModel().getSelectedItem()).getCodigoHorario());
        registro.setCodigoAdministracion(((Administracion)cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
        
        try
        {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEmpleado(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            
            procedimiento.setString(1, registro.getNombresEmpleado());
            procedimiento.setString(2, registro.getApellidosEmpleado());
            procedimiento.setString(3, registro.getCorreoElectronico());
            procedimiento.setString(4, registro.getTelefonoEmpleado());         
            procedimiento.setDate(5, new java.sql.Date(registro.getFechaContratacion().getTime()));
            procedimiento.setDouble(6, registro.getSueldo());
            procedimiento.setInt(7, registro.getCodigoDepartamento());
            procedimiento.setInt(8, registro.getCodigoCargo());
            procedimiento.setInt(9, registro.getCodigoHorario());
            procedimiento.setInt(10, registro.getCodigoAdministracion());
            
            procedimiento.execute();
            
            listaEmpleados.add(registro);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarEmpleado(?, ?, ?, ?, ?, ?, ?)}");
            Empleado registro = (Empleado) tblEmpleados.getSelectionModel().getSelectedItem();
            
            registro.setNombresEmpleado(txtNombresEmpleado.getText());
            registro.setApellidosEmpleado(txtApellidosEmpleado.getText());
            registro.setCorreoElectronico(txtCorreoElectronico.getText());        
            registro.setTelefonoEmpleado(txtTelefonoEmpleado.getText());
            registro.setFechaContratacion(fechaContratacion.getSelectedDate());
            registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
            
            procedimiento.setInt(1, registro.getCodigoEmpleado());
            procedimiento.setString(2, registro.getNombresEmpleado());
            procedimiento.setString(3, registro.getApellidosEmpleado());
            procedimiento.setString(4, registro.getCorreoElectronico());
            procedimiento.setString(5, registro.getTelefonoEmpleado());         
            procedimiento.setDate(6, new java.sql.Date(registro.getFechaContratacion().getTime()));
            procedimiento.setDouble(7, registro.getSueldo());
        
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
        if (tblEmpleados.getSelectionModel().getSelectedItem() != null)
        {
            Map parametros = new HashMap();
            int codigoEmpleado = ((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado();
            String ReporteHorizontal = "/org/rodrigocardenas/images/ReporteHorizontal.png";
            parametros.put("codigoEmpleado", codigoEmpleado);
            parametros.put("ReporteHorizontal", this.getClass().getResourceAsStream(ReporteHorizontal));
            GenerarReporte.mostrarReporte("ReporteEmpleados.jasper", "Reporte de Empleados", parametros);
        }
        
        else
        {
            JOptionPane.showMessageDialog(null,"Debe seleccionar un elemento.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
 
    
    // CONTROLES
        
    public void desactivarControles()
    {
        txtCodigoEmpleado.setEditable(false);
        txtNombresEmpleado.setEditable(false);
        txtApellidosEmpleado.setEditable(false);
        txtCorreoElectronico.setEditable(false);
        txtTelefonoEmpleado.setEditable(false);
        txtSueldo.setEditable(false);
          
        cmbCodigoDepartamento.setDisable(true);
        cmbCodigoCargo.setDisable(true);
        cmbCodigoHorario.setDisable(true);
        cmbCodigoAdministracion.setDisable(true);
        
        fechaContratacion.setDisable(true);
    }
    
    
    public void activarControles()
    {
        txtCodigoEmpleado.setEditable(false);
        txtNombresEmpleado.setEditable(true);
        txtApellidosEmpleado.setEditable(true);
        txtCorreoElectronico.setEditable(true);
        txtTelefonoEmpleado.setEditable(true);
        txtSueldo.setEditable(true);
          
        cmbCodigoDepartamento.setDisable(false);
        cmbCodigoCargo.setDisable(false);
        cmbCodigoHorario.setDisable(false);
        cmbCodigoAdministracion.setDisable(false);
        
        fechaContratacion.setDisable(false);
    }
    
    
    public void activarControlesActualizar()
    {
        txtCodigoEmpleado.setEditable(false);
        txtNombresEmpleado.setEditable(true);
        txtApellidosEmpleado.setEditable(true);
        txtCorreoElectronico.setEditable(true);
        txtTelefonoEmpleado.setEditable(true);
        txtSueldo.setEditable(true);
          
        cmbCodigoDepartamento.setDisable(true);
        cmbCodigoCargo.setDisable(true);
        cmbCodigoHorario.setDisable(true);
        cmbCodigoAdministracion.setDisable(true);
        
        fechaContratacion.setDisable(false);
    }
    
    
    public void limpiarControles()
    {
        txtCodigoEmpleado.clear();
        txtNombresEmpleado.clear();
        txtApellidosEmpleado.clear();
        txtCorreoElectronico.clear();
        txtTelefonoEmpleado.clear();
        txtSueldo.clear();
          
        cmbCodigoDepartamento.getSelectionModel().clearSelection();
        cmbCodigoCargo.getSelectionModel().clearSelection();
        cmbCodigoHorario.getSelectionModel().clearSelection();
        cmbCodigoAdministracion.getSelectionModel().clearSelection();
        
        fechaContratacion.setSelectedDate(null);
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
    
    
    public void ventanaCargos()
    {
        escenarioPrincipal.ventanaCargos();
    }
    
}
