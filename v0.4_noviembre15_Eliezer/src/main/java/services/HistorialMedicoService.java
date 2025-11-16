/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

/**
 *
 * @author GRUPO_PROG_2_C_1_ 4
 */
import models.HistorialMedico;
import models.Vacuna;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class HistorialMedicoService {

    private final com.google.cloud.firestore.Firestore firestore;
    private static final String COLLECTION_NAME = "historialesMedicos";

    public HistorialMedicoService() {
        this.firestore = FirebaseService.getInstance().getFirestore();
    }

    //crear historial médico
    public String crearHistorialMedico(HistorialMedico historial) throws ExecutionException, InterruptedException {
        Map<String, Object> historialData = new HashMap<>();
        historialData.put("idMascota", historial.getIdMascota());
        historialData.put("resultadosExamenesFisicos", historial.getResultadosExamenesFisicos());
        historialData.put("diagnosticosAnteriores", historial.getDiagnosticosAnteriores());
        historialData.put("tratamientosAnteriores", historial.getTratamientosAnteriores());
        historialData.put("vacunas", historial.getVacunas());

        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(historial.getIdMascota());
        WriteResult result = docRef.set(historialData).get();
        return "Historial medico creado para mascota ID: " + historial.getIdMascota();
    }

    //leer historial médico por ID de mascota
    public HistorialMedico obtenerHistorialMedico(String idMascota) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(idMascota);
        var document = docRef.get().get();

        if (document.exists()) {
            return document.toObject(HistorialMedico.class);
        }
        return null;
    }

    //agregar vacuna al historial
    public String agregarVacuna(String idMascota, Vacuna vacuna) throws ExecutionException, InterruptedException {
        HistorialMedico historial = obtenerHistorialMedico(idMascota);
        if (historial != null) {
            historial.getVacunas().add(vacuna);
            return crearHistorialMedico(historial);
        }
        return "Historial medico no encontrado";
    }

    //agregar un diagnóstico
    public String agregarDiagnostico(String idMascota, String diagnostico) throws ExecutionException, InterruptedException {
        HistorialMedico historial = obtenerHistorialMedico(idMascota);
        if (historial != null) {
            historial.getDiagnosticosAnteriores().add(diagnostico);
            return crearHistorialMedico(historial);
        }
        return "Historial medico no encontrado";
    }
    
    //UPDATE -agregar un tratamiento
    public String agregarTratamiento(String idMascota, String tratamiento) throws ExecutionException, InterruptedException {
        HistorialMedico historial = obtenerHistorialMedico(idMascota);

        if (historial != null) {
            historial.getTratamientosAnteriores().add(tratamiento);
            return crearHistorialMedico(historial);
        } else {
            historial = new HistorialMedico(idMascota);
            historial.getTratamientosAnteriores().add(tratamiento);
            return crearHistorialMedico(historial);
        }
    }

//UPDATE -agrega un resultado de examén
    public String agregarResultadoExamen(String idMascota, String resultado) throws ExecutionException, InterruptedException {
        HistorialMedico historial = obtenerHistorialMedico(idMascota);

        if (historial != null) {
            historial.getResultadosExamenesFisicos().add(resultado);
            return crearHistorialMedico(historial);
        } else {
            historial = new HistorialMedico(idMascota);
            historial.getResultadosExamenesFisicos().add(resultado);
            return crearHistorialMedico(historial);
        }
    }
}
