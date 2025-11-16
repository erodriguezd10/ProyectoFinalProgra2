/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author GRUPO_PROG_2_C_1_ 4
 */
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

//generador de IDs únicos para mascotas y productos
public class GeneradorID {
    private static Random random = new Random();
    
    //Genera un ID único para una mascota
    public static String generarIDMascota() {
        String fecha = new SimpleDateFormat("yyyyMMdd").format(new Date());
        int numero = random.nextInt(9000) + 1000;
        return "MASC-" + fecha + "-" + numero;
    }
    //Genera un ID único para un producto
    public static String generarIDProducto() {
        String fecha = new SimpleDateFormat("yyyyMMdd").format(new Date());
        int numero = random.nextInt(9000) + 1000;
        return "PROD-" + fecha + "-" + numero;
    }
    
    public static String generarIDSimple() {
        long timestamp = System.currentTimeMillis();
        return "CLI-" + timestamp;
    }
    
    public static String generarIDUUID() {
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        return "ID-" + uuid.toUpperCase();
    }
}
//(*_*)