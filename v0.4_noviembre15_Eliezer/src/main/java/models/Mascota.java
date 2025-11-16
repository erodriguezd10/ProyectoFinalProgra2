/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author GRUPO_PROG_2_C_1_ 4
 */

import java.util.Date;

public class Mascota {
    //Atributos privados
    private String id;
    private String tipoAnimal;
    private String nombre;
    private String especie;
    private String sexo;
    private double edad;
    private String color;
    private Date fechaIngreso;
    private Date fechaRetiro;
    private String dpiCliente;
    
    //constructor inicial vacío requerido por firebase
    public Mascota() {}
    
    //constructor(para las nuevas mascotas)
    public Mascota(String tipoAnimal, String nombre, String especie, 
                   String sexo, int edad, String color, Date fechaIngreso, String dpiCliente) {
        this.tipoAnimal = tipoAnimal;
        this.nombre = nombre;
        this.especie = especie;
        this.sexo = sexo;
        this.edad = edad;
        this.color = color;
        this.fechaIngreso = fechaIngreso;
        this.dpiCliente = dpiCliente;
    }
    
    //constructor con id (cuando ya tienen el ID)
    public Mascota(String id, String tipoAnimal, String nombre, String especie, 
                   String sexo, double edad, String color, Date fechaIngreso, String dpiCliente) {
        this.id = id;
        this.tipoAnimal = tipoAnimal;
        this.nombre = nombre;
        this.especie = especie;
        this.sexo = sexo;
        this.edad = edad;
        this.color = color;
        this.fechaIngreso = fechaIngreso;
        this.dpiCliente = dpiCliente;
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getTipoAnimal() { return tipoAnimal; }
    public void setTipoAnimal(String tipoAnimal) { this.tipoAnimal = tipoAnimal; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }
    
    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }
    
    public double getEdad() { return edad; }
    public void setEdad(double edad) { this.edad = edad; }
    
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    
    public Date getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(Date fechaIngreso) { this.fechaIngreso = fechaIngreso; }
    
    public Date getFechaRetiro() { return fechaRetiro; }
    public void setFechaRetiro(Date fechaRetiro) { this.fechaRetiro = fechaRetiro; }
    
    public String getDpiCliente() { return dpiCliente; }
    public void setDpiCliente(String dpiCliente) { this.dpiCliente = dpiCliente; }
}