
package org.rodrigocardenas.bean;

import java.util.Date;

public class CuentaPorPagar 
{
    
    // ATRIBUTOS
    
    private int codigoCuentaPorPagar;
    private String numeroFactura;
    private Date fechaLimitePago;
    private String estadoPago;
    private double valorNetoPago;
    private int codigoAdministracion;
    private int codigoProveedor;
    private String valorNetoPagoTexto;
    
    
    // CONSTRUCTORES
    
    public CuentaPorPagar() 
    {
        
    }

    public CuentaPorPagar(int codigoCuentaPorPagar, String numeroFactura, Date fechaLimitePago, 
                          String estadoPago, double valorNetoPago, int codigoAdministracion, int codigoProveedor) 
    {
        this.codigoCuentaPorPagar = codigoCuentaPorPagar;
        this.numeroFactura = numeroFactura;
        this.fechaLimitePago = fechaLimitePago;
        this.estadoPago = estadoPago;
        this.valorNetoPago = valorNetoPago;
        this.codigoAdministracion = codigoAdministracion;
        this.codigoProveedor = codigoProveedor;
    }

    
    // MÈTODOS DE ACCESO

    public int getCodigoCuentaPorPagar() {
        return codigoCuentaPorPagar;
    }

    public void setCodigoCuentaPorPagar(int codigoCuentaPorPagar) {
        this.codigoCuentaPorPagar = codigoCuentaPorPagar;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public Date getFechaLimitePago() {
        return fechaLimitePago;
    }

    public void setFechaLimitePago(Date fechaLimitePago) {
        this.fechaLimitePago = fechaLimitePago;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public double getValorNetoPago() {
        return valorNetoPago;
    }

    public void setValorNetoPago(double valorNetoPago) {
        this.valorNetoPago = valorNetoPago;
    }

    public int getCodigoAdministracion() {
        return codigoAdministracion;
    }

    public void setCodigoAdministracion(int codigoAdministracion) {
        this.codigoAdministracion = codigoAdministracion;
    }

    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }
        
    public String getValorNetoPagoTexto() 
    {
        valorNetoPagoTexto = "Q. " + getValorNetoPago();
        return valorNetoPagoTexto;
    }

    public void setValorNetoPagoTexto(String valorNetoPagoTexto) 
    {
        this.valorNetoPagoTexto = valorNetoPagoTexto;
    }

}
