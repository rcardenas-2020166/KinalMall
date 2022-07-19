
package org.rodrigocardenas.bean;


public class Clientes 
{
    
    // ATRIBUTOS
    
    private int codigoCliente;
    private String nombresCliente;
    private String apellidosCliente;
    private String telefonoCliente;
    private String direccionCliente;
    private String emailCliente;
    private int codigoLocal;
    private int codigoAdministracion;
    private int codigoTipoCliente;
    
    
    // CONSTRUCTORES
    
    public void Clientes()
    {
        
    }

    public Clientes(int codigoCliente, String nombresCliente, String apellidosCliente, String telefonoCliente, 
                    String direccionCliente, String emailCliente, int codigoLocal, int codigoAdministracion) 
    {
        this.codigoCliente = codigoCliente;
        this.nombresCliente = nombresCliente;
        this.apellidosCliente = apellidosCliente;
        this.telefonoCliente = telefonoCliente;
        this.direccionCliente = direccionCliente;
        this.emailCliente = emailCliente;
        this.codigoLocal = codigoLocal;
        this.codigoAdministracion = codigoAdministracion;
        this.codigoTipoCliente = codigoTipoCliente;
    }
    
    
    // MÈTODOS DE ACCESO

    public int getCodigoCliente() 
    {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) 
    {
        this.codigoCliente = codigoCliente;
    }

    public String getNombresCliente() 
    {
        return nombresCliente;
    }

    public void setNombresCliente(String nombresCliente) 
    {
        this.nombresCliente = nombresCliente;
    }

    public String getApellidosCliente() 
    {
        return apellidosCliente;
    }

    public void setApellidosCliente(String apellidosCliente) 
    {
        this.apellidosCliente = apellidosCliente;
    }

    public String getTelefonoCliente() 
    {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) 
    {
        this.telefonoCliente = telefonoCliente;
    }

    public String getDireccionCliente() 
    {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) 
    {
        this.direccionCliente = direccionCliente;
    }

    public String getEmailCliente() 
    {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) 
    {
        this.emailCliente = emailCliente;
    }

    public int getCodigoLocal() 
    {
        return codigoLocal;
    }

    public void setCodigoLocal(int codigoLocal) 
    {
        this.codigoLocal = codigoLocal;
    }

    public int getCodigoAdministracion() 
    {
        return codigoAdministracion;
    }

    public void setCodigoAdministracion(int codigoAdministracion) 
    {
        this.codigoAdministracion = codigoAdministracion;
    } 

    public int getCodigoTipoCliente() 
    {
        return codigoTipoCliente;
    }

    public void setCodigoTipoCliente(int codigoTipoCliente) 
    {
        this.codigoTipoCliente = codigoTipoCliente;
    }
      
}
