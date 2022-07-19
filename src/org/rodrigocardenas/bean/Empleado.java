
package org.rodrigocardenas.bean;

import java.util.Date;

public class Empleado 
{
    
    // ATRIBUTOS
    
    private int codigoEmpleado;
    private String nombresEmpleado;
    private String apellidosEmpleado;
    private String correoElectronico;
    private String telefonoEmpleado;
    private Date fechaContratacion;
    private double sueldo;
    private int codigoDepartamento;
    private int codigoCargo;
    private int codigoHorario;
    private int codigoAdministracion;
    private String sueldoTexto;
    
    
    // CONSTRUCTORES
    
    public Empleado() 
    {
        
    }
     

    public Empleado(int codigoEmpleado, String nombresEmpleado, String apellidosEmpleado, 
                    String correoElectronico, String telefonoEmpleado, Date fechaContratacion, 
                    double sueldo, int codigoDepartamento, int codigoCargo, int codigoHorario, int codigoAdministracion) 
    {
        this.codigoEmpleado = codigoEmpleado;
        this.nombresEmpleado = nombresEmpleado;
        this.apellidosEmpleado = apellidosEmpleado;
        this.correoElectronico = correoElectronico;
        this.telefonoEmpleado = telefonoEmpleado;
        this.fechaContratacion = fechaContratacion;
        this.sueldo = sueldo;
        this.codigoDepartamento = codigoDepartamento;
        this.codigoCargo = codigoCargo;
        this.codigoHorario = codigoHorario;
        this.codigoAdministracion = codigoAdministracion;
    }
    
    
    // MÈTODOS DE ACCESO

    public int getCodigoEmpleado() 
    {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(int codigoEmpleado) 
    {
        this.codigoEmpleado = codigoEmpleado;
    }

    public String getNombresEmpleado() 
    {
        return nombresEmpleado;
    }

    public void setNombresEmpleado(String nombresEmpleado) 
    {
        this.nombresEmpleado = nombresEmpleado;
    }

    public String getApellidosEmpleado() 
    {
        return apellidosEmpleado;
    }

    public void setApellidosEmpleado(String apellidosEmpleado) 
    {
        this.apellidosEmpleado = apellidosEmpleado;
    }

    public String getCorreoElectronico() 
    {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) 
    {
        this.correoElectronico = correoElectronico;
    }

    public String getTelefonoEmpleado() 
    {
        return telefonoEmpleado;
    }

    public void setTelefonoEmpleado(String telefonoEmpleado) 
    {
        this.telefonoEmpleado = telefonoEmpleado;
    }

    public Date getFechaContratacion() 
    {
        return fechaContratacion;
    }

    public void setFechaContratacion(Date fechaContratacion) 
    {
        this.fechaContratacion = fechaContratacion;
    }

    public double getSueldo() 
    {
        return sueldo;
    }

    public void setSueldo(double sueldo) 
    {
        this.sueldo = sueldo;
    }

    public int getCodigoDepartamento() 
    {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(int codigoDepartamento) 
    {
        this.codigoDepartamento = codigoDepartamento;
    }

    public int getCodigoCargo() 
    {
        return codigoCargo;
    }

    public void setCodigoCargo(int codigoCargo) 
    {
        this.codigoCargo = codigoCargo;
    }

    public int getCodigoHorario() 
    {
        return codigoHorario;
    }

    public void setCodigoHorario(int codigoHorario) 
    {
        this.codigoHorario = codigoHorario;
    }

    public int getCodigoAdministracion() 
    {
        return codigoAdministracion;
    }

    public void setCodigoAdministracion(int codigoAdministracion) 
    {
        this.codigoAdministracion = codigoAdministracion;
    }
    
    public String getSueldoTexto() 
    {
        sueldoTexto = "Q. " + getSueldo();
        return sueldoTexto;
    }

    public void setSueldoTexto(String sueldoTexto) 
    {
        this.sueldoTexto = sueldoTexto;
    }

}
