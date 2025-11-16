/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

/**
 *
 * @author GRUPO_PROG_2_C_1_ 4
 */
import models.Producto;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

//servicio para manejar operaciones CRUD de productos en Firestore
public class ProductoService {
    private final com.google.cloud.firestore.Firestore firestore;
    private static final String COLLECTION_NAME = "productos";
    
    public ProductoService() {
        this.firestore = FirebaseService.getInstance().getFirestore();
    }
    //CRUD
    //agregar producto
    public String agregarProducto(Producto producto) throws ExecutionException, InterruptedException {
        Map<String, Object> productoData = new HashMap<>();
        productoData.put("id", producto.getId());
        productoData.put("nombre", producto.getNombre());
        productoData.put("precio", producto.getPrecio());
        productoData.put("descripcion", producto.getDescripcion());
        productoData.put("cantidad", producto.getCantidad());
        
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(producto.getId());
        WriteResult result = docRef.set(productoData).get();
        return "Probuto agregado con ID: " + producto.getId();
    }
    
    //obtener producto por id
    public Producto obtenerProducto(String id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(id);
        var document = docRef.get().get();
        
        if (document.exists()) {
            return document.toObject(Producto.class);
        }
        return null;
    }
    
    //leer producto
    public List<Producto> obtenerTodosProductos() throws ExecutionException, InterruptedException {
        List<Producto> productos = new ArrayList<>();
        var querySnapshot = firestore.collection(COLLECTION_NAME).get().get();
        
        for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
            productos.add(document.toObject(Producto.class));
        }
        return productos;
    }
    
    //buscar producto por nonmrre
    public List<Producto> buscarProductosPorNombre(String nombre) throws ExecutionException, InterruptedException {
        List<Producto> productos = new ArrayList<>();
        var querySnapshot = firestore.collection(COLLECTION_NAME)
                .whereGreaterThanOrEqualTo("nombre", nombre)
                .whereLessThanOrEqualTo("nombre", nombre + "\uf8ff")
                .get().get();
        
        for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
            productos.add(document.toObject(Producto.class));
        }
        return productos;
    }
    
    //actualizar un producto
    public String actualizarProducto(Producto producto) throws ExecutionException, InterruptedException {
        Map<String, Object> updates = new HashMap<>();
        updates.put("nombre", producto.getNombre());
        updates.put("precio", producto.getPrecio());
        updates.put("descripcion", producto.getDescripcion());
        updates.put("cantidad", producto.getCantidad());
        
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(producto.getId());
        WriteResult result = docRef.update(updates).get();
        return "Producto actualizado correctamente";
    }
    
    //eliminar algún producto
    public String eliminarProducto(String id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(id);
        WriteResult result = docRef.delete().get();
        return "Producto eliminado correctamente";
    }
    
    //compra de producto(extra)
    public String comprarProducto(String idProducto, int cantidadComprada) throws ExecutionException, InterruptedException {
        Producto producto = obtenerProducto(idProducto);
        if (producto != null) {
            int nuevaCantidad = producto.getCantidad() - cantidadComprada;
            if (nuevaCantidad >= 0) {
                producto.setCantidad(nuevaCantidad);
                return actualizarProducto(producto);
            } else {
                return "No hay suficiente cantidad del producto";
            }
        }
        return "Producto no encontrado";
    }
}
//(@_@)