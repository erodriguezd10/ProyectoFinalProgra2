/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

/**
 *
 * @author GRUPO_PROG_2_C_1_ 4
 */
import models.Mascota;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

//Servicio para manejar operaciones CRUD de Mascota en Firestore
public class MascotaService {
    private final com.google.cloud.firestore.Firestore firestore;
    private static final String COLLECTION_NAME = "mascotas";
    
    public MascotaService() {
        this.firestore = FirebaseService.getInstance().getFirestore();
    }

    //CRUD
    //agregar mascota
    public String agregarMascota(Mascota mascota) throws ExecutionException, InterruptedException {
        //creación del mapa con los datos de la mascota
        Map<String, Object> mascotaData = new HashMap<>();
        mascotaData.put("id", mascota.getId());
        mascotaData.put("tipoAnimal", mascota.getTipoAnimal());
        mascotaData.put("nombre", mascota.getNombre());
        mascotaData.put("especie", mascota.getEspecie());
        mascotaData.put("sexo", mascota.getSexo());
        mascotaData.put("edad", mascota.getEdad());
        mascotaData.put("color", mascota.getColor());
        mascotaData.put("fechaIngreso", mascota.getFechaIngreso());
        mascotaData.put("fechaRetiro", mascota.getFechaRetiro());
        mascotaData.put("dpiCliente", mascota.getDpiCliente());
        
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(mascota.getId());
        WriteResult result = docRef.set(mascotaData).get();
        return "Mascota agregada con ID: " + mascota.getId();
    }
    
    //buscar mascota por ID
    public Mascota obtenerMascota(String id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(id);
        var document = docRef.get().get();
        
        if (document.exists()) {
            return document.toObject(Mascota.class);
        }
        return null;
    }
    
    //buscar mascotas por cliente(DPI, relación)
    public List<Mascota> obtenerMascotasPorCliente(String dpiCliente) throws ExecutionException, InterruptedException {
        List<Mascota> mascotas = new ArrayList<>();
        Query query = firestore.collection(COLLECTION_NAME).whereEqualTo("dpiCliente", dpiCliente);
        var querySnapshot = query.get().get();
        
        for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
            mascotas.add(document.toObject(Mascota.class));
        }
        return mascotas;
    }
    
    //obtener todas las mascotas
    public List<Mascota> obtenerTodasMascotas() throws ExecutionException, InterruptedException {
        List<Mascota> mascotas = new ArrayList<>();
        var querySnapshot = firestore.collection(COLLECTION_NAME).get().get();
        
        for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
            mascotas.add(document.toObject(Mascota.class));
        }
        return mascotas;
    }
    
    //actualizar mascota
    public String actualizarMascota(Mascota mascota) throws ExecutionException, InterruptedException {
        Map<String, Object> updates = new HashMap<>();
        updates.put("tipoAnimal", mascota.getTipoAnimal());
        updates.put("nombre", mascota.getNombre());
        updates.put("especie", mascota.getEspecie());
        updates.put("sexo", mascota.getSexo());
        updates.put("edad", mascota.getEdad());
        updates.put("color", mascota.getColor());
        updates.put("fechaRetiro", mascota.getFechaRetiro());
        
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(mascota.getId());
        WriteResult result = docRef.update(updates).get();
        return "Mascota actualizada correctamente";
    }
    
    //eliminar mascota
    public String eliminarMascota(String id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(id);
        WriteResult result = docRef.delete().get();
        return "Mascota eliminada correctamente";
    }
}