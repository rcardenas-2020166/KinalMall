
package org.rodrigocardenas.bean;

public class Departamento
{
    
    // ATRIBUTOS
    
    int codigoDepartamento;
    String nombreDepartamento;
    
    
    //CONSTRUCTORES
    
    public Departamento()
    {
        
    }

    public Departamento(int codigoDepartamento, String nombreDepartamento) 
    {
        this.codigoDepartamento = codigoDepartamento;
        this.nombreDepartamento = nombreDepartamento;
    }
    
    
    // MÉTODOS DE ACCESO

    public int getCodigoDepartamento()    
    {
        return codigoDepartamento;
    }

    
    public void setCodigoDepartamento(int codigoDepartamento) 
    {
        this.codigoDepartamento = codigoDepartamento;
    }

    public String getNombreDepartamento() 
    {
        return nombreDepartamento;
    }


    public void setNombreDepartamento(String nombreDepartamento) 
    {
        this.nombreDepartamento = nombreDepartamento;
    }
    
    
    @Override
    public String toString() 
    {
        return getCodigoDepartamento()+ " | " + getNombreDepartamento();
    }

}
