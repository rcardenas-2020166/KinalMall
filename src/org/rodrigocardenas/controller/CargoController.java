package org.rodrigocardenas.controller;

import com.jfoenix.controls.JFXButton;

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
import org.rodrigocardenas.bean.Cargo;
import org.rodrigocardenas.db.Conexion;
import org.rodrigocardenas.system.Principal;

public class CargoController implements Initializable
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
    private ObservableList <Cargo> listaCargos;
    
    
    // INYECCIONES JAVAFX
    
    @FXML private JFXButton btnNuevo;
    @FXML private JFXButton btnEditar;
    @FXML private JFXButton btnEliminar;
    @FXML private JFXButton btnReporte;
    @FXML private JFXButton btnEmpleados;
    
    @FXML private TextField txtCodigoCargo;
    @FXML private TextField txtNombreCargo;
    
    @FXML private TableView tblCargos;
    
    @FXML private TableColumn colCodigoCargo;
    @FXML private TableColumn colNombreCargo;
    
    @FXML private FontAwesomeIconView iconNuevo;
    @FXML private FontAwesomeIconView iconEditar;
    @FXML private FontAwesomeIconView iconEliminar;
    @FXML private FontAwesomeIconView iconReporte;
    @FXML private FontAwesomeIconView iconEmpleados;

    
    public void cargarDatos()
    {
        tblCargos.setItems(getCargo());
        colCodigoCargo.setCellValueFactory(new PropertyValueFactory <Cargo, Integer>("codigoCargo"));
        colNombreCargo.setCellValueFactory(new PropertyValueFactory <Cargo, String>("nombreCargo")); 
    }
    
    
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
    
    
    public void seleccionarElemento()
    {
        if(tblCargos.getSelectionModel().getSelectedItem() != null)
        {
            txtCodigoCargo.setText(String.valueOf(((Cargo)tblCargos.getSelectionModel().getSelectedItem()).getCodigoCargo()));
            txtNombreCargo.setText(((Cargo)tblCargos.getSelectionModel().getSelectedItem()).getNombreCargo());
        }
    }
    
    
    
    // ON ACTION SCENE BUILDER
    
    public void nuevo()
    {
        switch(tipoDeOperacion)
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
                
                btnEmpleados.setText("");
                btnEmpleados.setDisable(true);
                iconEmpleados.setFill(Color.rgb(35,54,112));

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
                
                btnEmpleados.setText("    Empleados");
                btnEmpleados.setDisable(false);
                iconEmpleados.setFill(Color.rgb(243,156,18));
                
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
                
                btnEmpleados.setText("    Empleados");
                btnEmpleados.setDisable(false);
                iconEmpleados.setFill(Color.rgb(243,156,18));
                
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;

            default:
                if (tblCargos.getSelectionModel().getSelectedItem() != null)
                {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿ Está seguro de ELIMINAR el registro ?", 
                                                                  "ELIMINAR CARGO", JOptionPane.YES_NO_OPTION,
                                                                  JOptionPane.QUESTION_MESSAGE);
                    
                    if (respuesta == JOptionPane.YES_NO_OPTION)
                    {
                        try
                        {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCargo(?)}");
                            procedimiento.setInt(1, ((Cargo) tblCargos.getSelectionModel().getSelectedItem()).getCodigoCargo());
                            procedimiento.execute();
                            
                            listaCargos.remove(tblCargos.getSelectionModel().getSelectedIndex());
                            
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
                if (tblCargos.getSelectionModel().getSelectedItem() != null)
                {
                    activarControles();
                    
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
                    
                    btnEmpleados.setText("");
                    btnEmpleados.setDisable(true);
                    iconEmpleados.setFill(Color.rgb(35,54,112));

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
                
                btnEmpleados.setText("    Empleados");
                btnEmpleados.setDisable(false);
                iconEmpleados.setFill(Color.rgb(243,156,18));
   
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
                
                btnEmpleados.setText("    Empleados");
                btnEmpleados.setDisable(false);
                iconEmpleados.setFill(Color.rgb(243,156,18));
                
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
        
    }
    
    
    
    //ACCIONES DEL CRUD
    
    public void guardar()
    {
        Cargo registro = new Cargo();
        
        registro.setNombreCargo(txtNombreCargo.getText());
        
        try
        {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_AgregarCargo(?)");
            
            procedimiento.setString(1, registro.getNombreCargo());
            
            procedimiento.execute();
            
            listaCargos.add(registro);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCargo(?, ?)}");
            Cargo registro = (Cargo)tblCargos.getSelectionModel().getSelectedItem();
            
            registro.setNombreCargo(txtNombreCargo.getText());
            
            procedimiento.setInt(1, registro.getCodigoCargo());
            procedimiento.setString(2, registro.getNombreCargo());
            
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
        txtCodigoCargo.setEditable(false);
        txtNombreCargo.setEditable(false);
    }
    
    
    public void activarControles()
    {
        txtCodigoCargo.setEditable(false);
        txtNombreCargo.setEditable(true);
    }
    
    public void limpiarControles()
    {
        txtCodigoCargo.clear();
        txtNombreCargo.clear();
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
    
    
        public void ventanaEmpleados()
    {
        escenarioPrincipal.ventanaEmpleados();
    }
    
}
