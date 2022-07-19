
package org.rodrigocardenas.bean;


public class Local 
{
    
    // ATRIBUTOS
    
    private int codigoLocal;
    private double saldoFavor;
    private double saldoContra;
    private int mesesPendientes;
    private boolean disponibilidad;
    private double valorLocal;
    private double valorAdministracion;
    private String disponibilidadTexto;
    private String valorLocalTexto;
    private String valorAdministracionTexto;

    
    // CONSTRUCTORES
    
    public Local()
    {
        
    }

    public Local(int codigoLocal, double saldoFavor, double saldoContra, int mesesPendientes,
                 boolean disponibilidad, double valorLocal, double valorAdministracion) 
    {
        this.codigoLocal = codigoLocal;
        this.saldoFavor = saldoFavor;
        this.saldoContra = saldoContra;
        this.mesesPendientes = mesesPendientes;
        this.disponibilidad = disponibilidad;
        this.valorLocal = valorLocal;
        this.valorAdministracion = valorAdministracion;
    }

    
    // MÉTODOS DE ACCESO
    
    public int getCodigoLocal() 
    {
        return codigoLocal;  
    }

    public void setCodigoLocal(int codigoLocal) 
    {
        this.codigoLocal = codigoLocal;
    }

    public double getSaldoFavor() {
        return saldoFavor;
    }

    public void setSaldoFavor(double saldoFavor) 
    {
        this.saldoFavor = saldoFavor;
    }

    public double getSaldoContra() 
    {
        return saldoContra;
    }

    public void setSaldoContra(double saldoContra) 
    {
        this.saldoContra = saldoContra;
    }

    public int getMesesPendientes() 
    {
        return mesesPendientes;
    }

    public void setMesesPendientes(int mesesPendientes) 
    {
        this.mesesPendientes = mesesPendientes;
    }

    public boolean isDisponibilidad() 
    {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) 
    {
        this.disponibilidad = disponibilidad;
    }

    public double getValorLocal() 
    {
        return valorLocal;
    }

    public void setValorLocal(double valorLocal) 
    {
        this.valorLocal = valorLocal;
    }

    public double getValorAdministracion() 
    {
        return valorAdministracion;
    }

    public void setValorAdministracion(double valorAdministracion) 
    {
        this.valorAdministracion = valorAdministracion;
    }

    @Override
    public String toString() 
    {
        String disponibilidadLocal = null;
        
        if(isDisponibilidad() == true)
        {
            disponibilidadLocal = " Disponible ";
        }
        
        else if (isDisponibilidad() == false)
        {
            disponibilidadLocal = " Ocupado ";
        }
        
        return getCodigoLocal() + " | " + " Q. " +getValorLocal() + " | " +  disponibilidadLocal;
    }

    // MÉTODOS DE ACCESO PARA LA TABLA SEA AMIGABLE CON EL USUARIO
    
    public String getDisponibilidadTexto() 
    {
        if(isDisponibilidad() == true)
        {
            disponibilidadTexto = " Disponible ";
        }
        
        else if (isDisponibilidad() == false)
        {
            disponibilidadTexto = " Ocupado ";
        }
        return disponibilidadTexto;
    }

    public void setDisponibilidadLocal(String disponibilidadTexto) 
    {
        this.disponibilidadTexto = disponibilidadTexto;
    }

    public String getValorLocalTexto() 
    {
        valorLocalTexto = "Q. " + getValorLocal();
        return valorLocalTexto;
    }

    public void setValorLocalTexto(String valorLocalTexto) 
    {
        this.valorLocalTexto = valorLocalTexto;
    }

    public String getValorAdministracionTexto() 
    {
        valorAdministracionTexto = "Q. " + getValorAdministracion();
        return valorAdministracionTexto;
    }

    public void setValorAdministracionTexto(String valorAdministracionTexto) 
    {
        this.valorAdministracionTexto = valorAdministracionTexto;
    }

}
