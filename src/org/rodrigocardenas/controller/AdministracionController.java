
package org.rodrigocardenas.controller;

import com.jfoenix.controls.JFXButton;
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
import org.rodrigocardenas.db.Conexion;
import org.rodrigocardenas.report.GenerarReporte;
import org.rodrigocardenas.system.Principal;

public class AdministracionController implements Initializable
{
    private Principal escenarioPrincipal;
    
    private enum operaciones
    {
        NUEVO,GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    
    private ObservableList <Administracion> listaAdministracion;
       
    @FXML private JFXButton btnNuevo;
    @FXML private JFXButton btnEditar;
    @FXML private JFXButton btnEliminar;
    @FXML private JFXButton btnReporte;
    @FXML private TextField txtCodigoAdministracion;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtTelefono;
    
    @FXML private TableView tblAdministracion;
    @FXML private TableColumn colCodigoAdministracion;
    @FXML private TableColumn colDireccion;
    @FXML private TableColumn colTelefono;
    
    @FXML private FontAwesomeIconView iconNuevo;
    @FXML private FontAwesomeIconView iconEditar;
    @FXML private FontAwesomeIconView iconEliminar;
    @FXML private FontAwesomeIconView iconReporte;
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        cargarDatos();
    }
    
    
    public void cargarDatos()
    {
        tblAdministracion.setItems(getAdministracion());
        colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory <Administracion,Integer>("codigoAdministracion"));
        colDireccion.setCellValueFactory(new PropertyValueFactory <Administracion,String>("direccion"));
        colTelefono.setCellValueFactory(new PropertyValueFactory <Administracion,String>("telefono"));
    }

    
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
        if(tblAdministracion.getSelectionModel().getSelectedItem() != null)
        {
            txtCodigoAdministracion.setText(String.valueOf(((Administracion)tblAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
            txtDireccion.setText(((Administracion)tblAdministracion.getSelectionModel().getSelectedItem()).getDireccion());
            txtTelefono.setText(((Administracion)tblAdministracion.getSelectionModel().getSelectedItem()).getTelefono());
        }
        else
        {
            txtCodigoAdministracion.setText("");
            txtDireccion.setText("");
            txtTelefono.setText("");
        }
        
    }
    
    
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
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    
    public void guardar()
    {
        Administracion registro = new Administracion();
        registro.setDireccion(txtDireccion.getText());
        registro.setTelefono(txtTelefono.getText());
        
        if (txtTelefono.getText().equals("") | txtDireccion.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null,"No puede dejar campos vacíos.", "ERROR", JOptionPane.WARNING_MESSAGE);
        }
        else{
                if(txtTelefono.getText().length() != 8)
                {
                    JOptionPane.showMessageDialog(null,"El número de teléfono debe \n"+"constar de 8 dígitos.", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    try
                    {
                        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarDatoAdministracion(?,?)}");
                        procedimiento.setString(1, registro.getDireccion());
                        procedimiento.setString(2, registro.getTelefono());
                        procedimiento.execute();
                        listaAdministracion.add(registro);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
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
                
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
            
                
            default:
                if (tblAdministracion.getSelectionModel().getSelectedItem() != null)
                { 
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿ Está seguro de ELIMINAR el registro ?", 
                                                                  "ELIMINAR ADMINISTRACIÓN", JOptionPane.YES_NO_OPTION,
                                                                  JOptionPane.QUESTION_MESSAGE);
                    if  (respuesta == JOptionPane.YES_NO_OPTION)
                    {
                        try
                        {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarDatoAdministracion(?)}");
                            procedimiento.setInt(1, ((Administracion) tblAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
                            procedimiento.execute();
                            listaAdministracion.remove(tblAdministracion.getSelectionModel().getSelectedIndex());
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
                if(tblAdministracion.getSelectionModel().getSelectedItem() != null)
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
                
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
                
        }
    }
    
    public void actualizar()
    {
        try
        {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarDatoAdministracion(?,?,?)}");
            Administracion registro = (Administracion)tblAdministracion.getSelectionModel().getSelectedItem();
            registro.setDireccion(txtDireccion.getText());
            registro.setTelefono(txtTelefono.getText());
            procedimiento.setInt(1, registro.getCodigoAdministracion());
            procedimiento.setString(2, registro.getDireccion());
            procedimiento.setString(3, registro.getTelefono());
            procedimiento.execute();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void reporte()
    {
        switch (tipoDeOperacion)
        {
            case NINGUNO:
                imprimirReporte();
                break;
            
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
                
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    
    
   
    // METODO PARA EL REPORTE IMPRESION
    
    public void imprimirReporte()
    {
        String ReporteAdministracion = "/org/rodrigocardenas/images/ReporteAdministracion.png";
        Map parametros = new HashMap();
        parametros.put("ReporteAdministracion", this.getClass().getResourceAsStream(ReporteAdministracion));
        GenerarReporte.mostrarReporte("ReporteAdministracion.jasper", "Reporte de Administración", parametros);
    }
    

    public void desactivarControles()
    {
        txtCodigoAdministracion.setEditable(false);
        txtDireccion.setEditable(false);
        txtTelefono.setEditable(false);
    }
    
    public void activarControles()
    {
        txtCodigoAdministracion.setEditable(false);
        txtDireccion.setEditable(true);
        txtTelefono.setEditable(true);
    }
    
    public void limpiarControles()
    {
        txtCodigoAdministracion.clear();
        txtDireccion.clear();
        txtTelefono.clear();
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
    
    public void menuPrincipal()
    {
        escenarioPrincipal.menuPrincipal();
    }
    
}
