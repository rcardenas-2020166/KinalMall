
package org.rodrigocardenas.bean;

public class Proveedor 
{
     // ATRIBUTOS
    
    private int codigoProveedor;
    private String NITProveedor;
    private String servicioPrestado;
    private String telefonoProveedor;
    private String direccionProveedor;
    private double saldoFavor;
    private double saldoContra;
    private int codigoAdministracion;
    private String saldoFavorTexto;
    private String saldoContraTexto;


        
    // CONSTRUCTORES
    
    public Proveedor() 
    {
        
    }
    

    public Proveedor(int codigoProveedor, String NITProveedor, String servicioPrestado, 
                     String telefonoProveedor, String direccionProveedor, double saldoFavor, double saldoContra,
                     int codigoAdministracion) 
    {
        this.codigoProveedor = codigoProveedor;
        this.NITProveedor = NITProveedor;
        this.servicioPrestado = servicioPrestado;
        this.telefonoProveedor = telefonoProveedor;
        this.direccionProveedor = direccionProveedor;
        this.saldoFavor = saldoFavor;
        this.saldoContra = saldoContra;
        this.codigoAdministracion = codigoAdministracion;
    }
    

    
    
    
    // MÈTODOS DE ACCESO

    public int getCodigoProveedor() 
    {
        return codigoProveedor;
    }

    public void setCodigoProveedor(int codigoProveedor) 
    {
        this.codigoProveedor = codigoProveedor;
    }

    public String getNITProveedor() 
    {
        return NITProveedor;
    }

    public void setNITProveedor(String NITProveedor) 
    {
        this.NITProveedor = NITProveedor;
    }

    public String getServicioPrestado() 
    {
        return servicioPrestado;
    }

    public void setServicioPrestado(String servicioPrestado) 
    {
        this.servicioPrestado = servicioPrestado;
    }

    public String getTelefonoProveedor() 
    {
        return telefonoProveedor;
    }

    public void setTelefonoProveedor(String telefonoProveedor) 
    {
        this.telefonoProveedor = telefonoProveedor;
    }

    public String getDireccionProveedor() 
    {
        return direccionProveedor;
    }

    public void setDireccionProveedor(String direccionProveedor) 
    {
        this.direccionProveedor = direccionProveedor;
    }

    public double getSaldoFavor() 
    {
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

    public int getCodigoAdministracion() 
    {
        return codigoAdministracion;
    }

    public void setCodigoAdministracion(int codigoAdministracion) 
    {
        this.codigoAdministracion = codigoAdministracion;
    }

    
    // TO STRING
    @Override
    public String toString() 
    {
        return getCodigoProveedor() + " | " + getNITProveedor();
    }
    
    
    public String getSaldoFavorTexto() 
    {
        saldoFavorTexto = "Q. " + getSaldoFavor();
        return saldoFavorTexto;
    }

    public void setSaldoFavorTexto(String saldoFavorTexto) 
    {
        this.saldoFavorTexto = saldoFavorTexto;
    }
    
    public String getSaldoContraTexto() 
    {
        saldoContraTexto = "Q. " + getSaldoContra();
        return saldoContraTexto;
    }

    public void setSaldoContraTexto(String saldoContraTexto) 
    {
        this.saldoContraTexto = saldoContraTexto;
    }

}
