/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author GRUPO_PROG_2_C_1_4
 */
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class HistorialMedico {
    //atributos
    private String idMascota;
    private List<String> resultadosExamenesFisicos;
    private List<String> diagnosticosAnteriores;
    private List<String> tratamientosAnteriores;
    private List<Vacuna> vacunas;
    
    //constructor necesario para json
    public HistorialMedico() {
        //inicializar todas las listas en el constructor vacío
        this.resultadosExamenesFisicos = new ArrayList<>();
        this.diagnosticosAnteriores = new ArrayList<>();
        this.tratamientosAnteriores = new ArrayList<>();
        this.vacunas = new ArrayList<>();
    }
    
    //con id de la mascota
    public HistorialMedico(String idMascota) {
        this(); 
        this.idMascota = idMascota;
    }
    
    //leer y modificar los atributos
    public String getIdMascota() { return idMascota; }
    public void setIdMascota(String idMascota) { this.idMascota = idMascota; }
    
    public List<String> getResultadosExamenesFisicos() { 
        //asegura que nunca retorne null
        if (this.resultadosExamenesFisicos == null) {
            this.resultadosExamenesFisicos = new ArrayList<>();
        }
        return resultadosExamenesFisicos; 
    }
    
    //
    public void setResultadosExamenesFisicos(List<String> resultadosExamenesFisicos) { 
        this.resultadosExamenesFisicos = resultadosExamenesFisicos; 
    }
    
    public List<String> getDiagnosticosAnteriores() { 
        if (this.diagnosticosAnteriores == null) {
            this.diagnosticosAnteriores = new ArrayList<>();
        }
        return diagnosticosAnteriores; 
    }
    //
    public void setDiagnosticosAnteriores(List<String> diagnosticosAnteriores) { 
        this.diagnosticosAnteriores = diagnosticosAnteriores; 
    }
    
    public List<String> getTratamientosAnteriores() { 
        if (this.tratamientosAnteriores == null) {
            this.tratamientosAnteriores = new ArrayList<>();
        }
        return tratamientosAnteriores; 
    }
    
    public void setTratamientosAnteriores(List<String> tratamientosAnteriores) { 
        this.tratamientosAnteriores = tratamientosAnteriores; 
    }
    
    public List<Vacuna> getVacunas() { 
        if (this.vacunas == null) {
            this.vacunas = new ArrayList<>();
        }
        return vacunas; 
    }
    
    public void setVacunas(List<Vacuna> vacunas) { 
        this.vacunas = vacunas; 
    }
    
    //métodos utilitarios para agregar elementos a las listas
    public void agregarDiagnostico(String diagnostico) {
        getDiagnosticosAnteriores().add(diagnostico);
    }
    
    public void agregarTratamiento(String tratamiento) {
        getTratamientosAnteriores().add(tratamiento);
    }
    
    public void agregarResultadoExamen(String resultado) {
        getResultadosExamenesFisicos().add(resultado);
    }
    
    public void agregarVacuna(Vacuna vacuna) {
        getVacunas().add(vacuna);
    }
    
    //método para verificar si hay datos en el historial
    public boolean tieneDatos() {
        return !getDiagnosticosAnteriores().isEmpty() ||
               !getTratamientosAnteriores().isEmpty() ||
               !getResultadosExamenesFisicos().isEmpty() ||
               !getVacunas().isEmpty();
    }
}