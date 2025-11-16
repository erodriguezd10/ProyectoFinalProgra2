/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author GRUPO_PROG_2_C_1_ 4
 */

public class Cliente {
    private String nombre;
    private String direccion;
    private String dpi;
    private String numeroTelefono;
    
    //constructor vacío necesario para json firebase 
    public Cliente() {}
    //Constructor principal
    public Cliente(String nombre, String direccion, String dpi, String numeroTelefono) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.dpi = dpi;
        this.numeroTelefono = numeroTelefono;
    }
    //g y s
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public String getDpi() { return dpi; }
    public void setDpi(String dpi) { this.dpi = dpi; }
    
    public String getNumeroTelefono() { return numeroTelefono; }
    public void setNumeroTelefono(String numeroTelefono) { this.numeroTelefono = numeroTelefono; }
    //vista previa del objeto
    
    @Override
    public String toString() {
        return "cliente{" + "nombre=" + nombre + ", dpi=" + dpi + ", telefono=" + numeroTelefono + '}';
    }
}