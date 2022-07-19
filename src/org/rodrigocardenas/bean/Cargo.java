
package org.rodrigocardenas.bean;

public class Cargo 
{
    
    // ATRIBUTOS
    
    int codigoCargo;
    String nombreCargo;
    
    
    //CONSTRUCTORES
    
    public Cargo()
    {
        
    }

    public Cargo(int codigoCargo, String nombreCargo) 
    {
        this.codigoCargo = codigoCargo;
        this.nombreCargo = nombreCargo;
    }
    
    
    // MÉTODOS DE ACCESO

    public int getCodigoCargo() 
    {
        return codigoCargo;
    }

    public void setCodigoCargo(int codigoCargo) 
    {
        this.codigoCargo = codigoCargo;
    }

    public String getNombreCargo() 
    {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) 
    {
        this.nombreCargo = nombreCargo;
    }
    
    
    @Override
    public String toString() 
    {
        return getCodigoCargo()+ " | " + getNombreCargo();
    }
  
}
