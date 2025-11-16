/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

/**
 *
 * @author GRUPO_PROG_2_C_1_ 4
 */
import models.Cliente;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ClienteService {
    //atributos y construcro
    private final com.google.cloud.firestore.Firestore firestore;
    private static final String COLLECTION_NAME = "Clientes";
    
    public ClienteService() {
        this.firestore = FirebaseService.getInstance().getFirestore();
    }
    
    //crear un dueño
    public String agregarCliente(Cliente cliente) throws ExecutionException, InterruptedException {
        Map<String, Object> clienteData = new HashMap<>();
        //datos
        clienteData.put("nombre", cliente.getNombre());
        clienteData.put("direccion", cliente.getDireccion());
        clienteData.put("dpi", cliente.getDpi());
        clienteData.put("numeroTelefono", cliente.getNumeroTelefono());
        
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(cliente.getDpi());
        WriteResult result = docRef.set(clienteData).get();
        return "Cliente agregado con ID: " + cliente.getDpi();
    }
    
    //leer cliente por DPI
    public Cliente obtenerCliente(String dpi) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(dpi);
        var document = docRef.get().get();
        
        if (document.exists()) {
            return document.toObject(Cliente.class);
        }
        return null;
    }
    
    //ver todos los clientes
    public List<Cliente> obtenerTodosClientes() throws ExecutionException, InterruptedException {
        List<Cliente> clientes = new ArrayList<>();
        var querySnapshot = firestore.collection(COLLECTION_NAME).get().get();
        
        for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
            clientes.add(document.toObject(Cliente.class));
        }
        return clientes;
    }
    
    //actualizar un cliente
    public String actualizarCliente(Cliente cliente) throws ExecutionException, InterruptedException {
        Map<String, Object> updates = new HashMap<>();
        updates.put("nombre", cliente.getNombre());
        updates.put("direccion", cliente.getDireccion());
        updates.put("numeroTelefono", cliente.getNumeroTelefono());
        
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(cliente.getDpi());
        WriteResult result = docRef.update(updates).get();
        return "Cliente actualizado correctamente";
    }
    
    //eliminar cliente
    public String eliminarCliente(String dpi) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(dpi);
        WriteResult result = docRef.delete().get();
        return "Cliente eliminado correctamente";
    }
}