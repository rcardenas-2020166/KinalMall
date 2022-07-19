
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
import org.rodrigocardenas.bean.Horario;
import org.rodrigocardenas.db.Conexion;
import org.rodrigocardenas.system.Principal;

public class HorarioController implements Initializable
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
    private ObservableList <Horario> listaHorarios;
    
    
     // INYECCIONES JAVAFX
    
    @FXML private JFXButton btnNuevo;
    @FXML private JFXButton btnEditar;
    @FXML private JFXButton btnEliminar;
    @FXML private JFXButton btnReporte;
    
    @FXML private TextField txtCodigoHorario;
    @FXML private TextField txtHoraEntrada;
    @FXML private TextField txtHoraSalida;
    @FXML private TextField txtLunes;
    @FXML private TextField txtMartes;
    @FXML private TextField txtMiercoles;
    @FXML private TextField txtJueves;
    @FXML private TextField txtViernes;
    
    @FXML private TableView tblHorarios;
    
    @FXML private TableColumn colCodigoHorario;
    @FXML private TableColumn colHoraEntrada;
    @FXML private TableColumn colHoraSalida;
    @FXML private TableColumn colLunes;
    @FXML private TableColumn colMartes;
    @FXML private TableColumn colMiercoles;
    @FXML private TableColumn colJueves;
    @FXML private TableColumn colViernes;   
    
    @FXML private FontAwesomeIconView iconNuevo;
    @FXML private FontAwesomeIconView iconEditar;
    @FXML private FontAwesomeIconView iconEliminar;
    @FXML private FontAwesomeIconView iconReporte;
    
    
    public void cargarDatos()
    {
        tblHorarios.setItems(getHorario());
        colCodigoHorario.setCellValueFactory(new PropertyValueFactory <Horario, Integer> ("codigoHorario"));
        colHoraEntrada.setCellValueFactory(new PropertyValueFactory <Horario, String> ("horaEntrada"));
        colHoraSalida.setCellValueFactory(new PropertyValueFactory <Horario, String> ("horaSalida"));
        colLunes.setCellValueFactory(new PropertyValueFactory <Horario, Boolean> ("textoLunes"));
        colMartes.setCellValueFactory(new PropertyValueFactory <Horario, Boolean> ("textoMartes"));
        colMiercoles.setCellValueFactory(new PropertyValueFactory <Horario, Boolean> ("textoMiercoles"));
        colJueves.setCellValueFactory(new PropertyValueFactory <Horario, Boolean> ("textoJueves"));
        colViernes.setCellValueFactory(new PropertyValueFactory <Horario, Boolean> ("textoViernes"));
    }
    
    
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
    
    
    public void seleccionarElemento()
    {
        if (tblHorarios.getSelectionModel().getSelectedItem() != null)
        {
            txtCodigoHorario.setText(String.valueOf(((Horario)tblHorarios.getSelectionModel().getSelectedItem()).getCodigoHorario()));
            txtHoraEntrada.setText(((Horario)tblHorarios.getSelectionModel().getSelectedItem()).getHoraEntrada());
            txtHoraSalida.setText(((Horario)tblHorarios.getSelectionModel().getSelectedItem()).getHoraSalida());
            txtLunes.setText(String.valueOf(((Horario)tblHorarios.getSelectionModel().getSelectedItem()).isLunes()));
            txtMartes.setText(String.valueOf(((Horario)tblHorarios.getSelectionModel().getSelectedItem()).isMartes()));
            txtMiercoles.setText(String.valueOf(((Horario)tblHorarios.getSelectionModel().getSelectedItem()).isMiercoles()));
            txtJueves.setText(String.valueOf(((Horario)tblHorarios.getSelectionModel().getSelectedItem()).isJueves()));
            txtViernes.setText(String.valueOf(((Horario)tblHorarios.getSelectionModel().getSelectedItem()).isViernes()));
        }
    }
    
    
    // ON ACTION SCENE BUIDER
    
    public void nuevo()
    {
        
        switch(tipoDeOperacion)
        {
            
            case NINGUNO:
                
                limpiarControles();
                activarControles();
                
                JOptionPane.showMessageDialog(null,"No puede dejar campos vacíos.", "ERROR", JOptionPane.WARNING_MESSAGE);

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
                
                if (tblHorarios.getSelectionModel().getSelectedItem() != null)
                {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿ Está seguro de ELIMINAR el registro ?",
                                                                  "ELIMINAR HORARIO", JOptionPane.YES_NO_OPTION,
                                                                  JOptionPane.QUESTION_MESSAGE);
                    
                    if (respuesta == JOptionPane.YES_NO_OPTION)
                    {
                        try
                        {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarHorario(?)}");
                            procedimiento.setInt(1, ((Horario) tblHorarios.getSelectionModel().getSelectedItem()).getCodigoHorario());
                            procedimiento.execute();
                            
                            listaHorarios.remove(tblHorarios.getSelectionModel().getSelectedIndex());
                            
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
                
                if (tblHorarios.getSelectionModel().getSelectedItem() != null)
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
                
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    
    
    // ACCIONES DEL CRUD
    
    public void guardar()
    {
        
        Horario registro = new Horario();
        
        registro.setHoraEntrada(txtHoraEntrada.getText());
        registro.setHoraSalida(txtHoraSalida.getText());
        registro.setLunes(Boolean.parseBoolean(txtLunes.getText()));
        registro.setMartes(Boolean.parseBoolean(txtMartes.getText()));
        registro.setMiercoles(Boolean.parseBoolean(txtMiercoles.getText()));
        registro.setJueves(Boolean.parseBoolean(txtJueves.getText()));
        registro.setViernes(Boolean.parseBoolean(txtViernes.getText()));

        try
        {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarHorario(?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setString(1, registro.getHoraEntrada());
            procedimiento.setString(2, registro.getHoraSalida());
            procedimiento.setBoolean(3, registro.isLunes());
            procedimiento.setBoolean(4, registro.isMartes());
            procedimiento.setBoolean(5, registro.isMiercoles());
            procedimiento.setBoolean(6, registro.isJueves());
            procedimiento.setBoolean(7, registro.isViernes());
            
            procedimiento.execute();
            
            listaHorarios.add(registro);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarHorario(?, ?, ?, ?, ?, ?, ?, ?)}");
            Horario registro = (Horario) tblHorarios.getSelectionModel().getSelectedItem();
            
            registro.setHoraEntrada(txtHoraEntrada.getText());
            registro.setHoraSalida(txtHoraSalida.getText());
            registro.setLunes(Boolean.parseBoolean(txtLunes.getText()));
            registro.setMartes(Boolean.parseBoolean(txtMartes.getText()));
            registro.setMiercoles(Boolean.parseBoolean(txtMiercoles.getText()));
            registro.setJueves(Boolean.parseBoolean(txtJueves.getText()));
            registro.setViernes(Boolean.parseBoolean(txtViernes.getText()));
            
            procedimiento.setInt(1, registro.getCodigoHorario());        
            procedimiento.setString(2, registro.getHoraEntrada());
            procedimiento.setString(3, registro.getHoraSalida());
            procedimiento.setBoolean(4, registro.isLunes());
            procedimiento.setBoolean(5, registro.isMartes());
            procedimiento.setBoolean(6, registro.isMiercoles());
            procedimiento.setBoolean(7, registro.isJueves());
            procedimiento.setBoolean(8, registro.isViernes());        
            
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
        txtCodigoHorario.setEditable(false);
        txtHoraEntrada.setEditable(false);
        txtHoraSalida.setEditable(false);
        txtLunes.setEditable(false);
        txtMartes.setEditable(false);
        txtMiercoles.setEditable(false);
        txtJueves.setEditable(false);
        txtViernes.setEditable(false);
    }
    
    
    public void activarControles()
    {
        txtCodigoHorario.setEditable(false);
        txtHoraEntrada.setEditable(true);
        txtHoraSalida.setEditable(true);
        txtLunes.setEditable(true);
        txtMartes.setEditable(true);
        txtMiercoles.setEditable(true);
        txtJueves.setEditable(true);
        txtViernes.setEditable(true);
    }
    
    public void limpiarControles()
    {
        txtCodigoHorario.clear();
        txtHoraEntrada.clear();
        txtHoraSalida.clear();
        txtLunes.clear();
        txtMartes.clear();
        txtMiercoles.clear();
        txtJueves.clear();
        txtViernes.clear();
    }        
           
    
    
    // MÉTODOS DE ACCESO A ESCENAS
    
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
