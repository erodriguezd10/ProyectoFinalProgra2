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

public class Vacuna {
    private Date fechaAdministracion;
    //almacena la información de la vacuna
    private String tipoVacuna;
    
    //constructor vacío
    public Vacuna() {}
    //constructor con parametros
    public Vacuna(Date fechaAdministracion, String tipoVacuna) {
        this.fechaAdministracion = fechaAdministracion;
        this.tipoVacuna = tipoVacuna;
    }
    
    public Date getFechaAdministracion() { return fechaAdministracion; }
    public void setFechaAdministracion(Date fechaAdministracion) { this.fechaAdministracion = fechaAdministracion; }
    
    public String getTipoVacuna() { return tipoVacuna; }
    public void setTipoVacuna(String tipoVacuna) { this.tipoVacuna = tipoVacuna; }
}