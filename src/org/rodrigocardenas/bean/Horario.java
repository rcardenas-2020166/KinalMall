
package org.rodrigocardenas.bean;


public class Horario 
{
    
     // ATRIBUTOS
    
    private int codigoHorario;
    private String horaEntrada;
    private String horaSalida;
    private boolean lunes;
    private boolean martes;
    private boolean miercoles;
    private boolean jueves;
    private boolean viernes;
    private String textoLunes;
    private String textoMartes;
    private String textoMiercoles;
    private String textoJueves;
    private String textoViernes;
    
    
     // CONSTRUCTORES
    
    public Horario()
    {
        
    }

    public Horario(int codigoHorario, String horaEntrada, String horaSalida, boolean lunes, boolean martes, boolean miercoles, 
                   boolean jueves, boolean viernes) 
    {
        this.codigoHorario = codigoHorario;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.lunes = lunes;
        this.martes = martes;
        this.miercoles = miercoles;
        this.jueves = jueves;
        this.viernes = viernes;
    }
    
    
    // METODOS DE ACCESO

    public int getCodigoHorario() 
    {
        return codigoHorario;
    }

    public void setCodigoHorario(int codigoHorario) 
    {
        this.codigoHorario = codigoHorario;
    }

    public String getHoraEntrada() 
    {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) 
    {
        this.horaEntrada = horaEntrada;
    }

    public String getHoraSalida() 
    {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) 
    {
        this.horaSalida = horaSalida;
    }

    public boolean isLunes() 
    {
        return lunes;
    }

    public void setLunes(boolean lunes) 
    {
        this.lunes = lunes;
    }

    public boolean isMartes() 
    {
        return martes;
    }

    public void setMartes(boolean martes) 
    {
        this.martes = martes;
    }

    public boolean isMiercoles() 
    {
        return miercoles;
    }

    public void setMiercoles(boolean miercoles) 
    {
        this.miercoles = miercoles;
    }

    public boolean isJueves() 
    {
        return jueves;
    }

    public void setJueves(boolean jueves) 
    {
        this.jueves = jueves;
    }

    public boolean isViernes() 
    {
        return viernes;
    }

    public void setViernes(boolean viernes) 
    {
        this.viernes = viernes;
    }
    
    
    
    // MÉTODOS QUE PERMITEN MOSTRAR DE MEJOR MANERA LOS DATOS EN LA TABLE VIEW
    
    public String getTextoLunes()
    {
        if(isLunes() == true)
        {
            
            textoLunes = "Trabajo";
        }
        
        else
        {
            
            textoLunes = "Descanso";
        }
        return textoLunes;
    }
    
    public String getTextoMartes()
    {
        if(isMartes() == true)
        {
            
            textoMartes = "Trabajo";
        }
        
        else
        {
            
            textoMartes = "Descanso";
        }
        return textoMartes;
    }
    
    public String getTextoMiercoles()
    {
        if(isMiercoles() == true)
        {
            
            textoMiercoles = "Trabajo";
        }
        
        else
        {
            
            textoMiercoles = "Descanso";
        }
        return textoMiercoles;
    }
    
    public String getTextoJueves()
    {
        if(isJueves() == true)
        {
            
            textoJueves = "Trabajo";
        }
        
        else
        {
            
            textoJueves = "Descanso";
        }
        return textoJueves;
    }
    
    public String getTextoViernes()
    {
        if(isViernes() == true)
        {
            
            textoViernes = "Trabajo";
        }
        
        else
        {
            
            textoViernes = "Descanso";
        }
        return textoViernes;
    }
    
    
    @Override
    public String toString() 
    {
        return getCodigoHorario() + " | " + getHoraEntrada() + " - " + getHoraSalida();
    }

}
