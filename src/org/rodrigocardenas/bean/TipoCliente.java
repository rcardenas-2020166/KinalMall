
package org.rodrigocardenas.bean;

public class TipoCliente 
{
    
    // ATRIBUTOS
    
    private int codigoTipoCliente;
    private String descripcion;
    
    
    //CONSTRUCTORES
    
    public TipoCliente()
    {
        
    }

    public TipoCliente(int codigoTipoCliente, String descripcion) 
    {
        this.codigoTipoCliente = codigoTipoCliente;
        this.descripcion = descripcion;
    }
    
    
    // MÉTODOS DE ACCESO

    public int getCodigoTipoCliente() 
    {
        return codigoTipoCliente;
    }

    public void setCodigoTipoCliente(int codigoTipoCliente) 
    {
        this.codigoTipoCliente = codigoTipoCliente;
    }

    public String getDescripcion() 
    {
        return descripcion;
    }

    public void setDescripcion(String descripcion) 
    {
        this.descripcion = descripcion;
    }
    
}
