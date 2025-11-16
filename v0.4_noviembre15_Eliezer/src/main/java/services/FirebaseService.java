/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

/**
 *
 * @author GRUPO_PROG_2_C_1_ 4
 */
//Firestore
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseService {
    private static FirebaseService instance;//se usa para implementar el patrón singleton
    private Firestore firestore; //será el objeto principal de conexión para realizar consultas a Firebase.
    
    //constructor privado
    private FirebaseService() {
        initializeFirebase();
    }
    
    //patrón sigelton
    public static synchronized FirebaseService getInstance() {
        if (instance == null) {
            instance = new FirebaseService();
        }
        return instance;
    }
    
    //establece la conexión con firebase
    private void initializeFirebase() {
        try {
            //ruta de la llave privada de conexión con Firebase
            FileInputStream serviceAccount = 
                new FileInputStream("firebase-config/servicio-veterinaria-firebase-adminsdk.json");
            
            //creación de los FirebaseOptions (credenciales y proyecto a usar)
            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
            
            //iniciar FirebaseApp
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
            //instancia de firestore
            firestore = FirestoreClient.getFirestore();
            //Conexion a Firebase establecida correctamente
            System.out.println("");
            
        } catch (IOException e) {
            System.err.println("Error al conectar con Firebase: " + e.getMessage());
            e.printStackTrace();
        }
    }
    //permite a las démas clases obtener la conexión con firebase 
    public Firestore getFirestore() {
        return firestore;
    }
}