/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author GRUPO_PROG_2_C_1_ 4
 */

public class Validaciones {

    //validaciones generales
    // Solo letras (incluye tildes y espacios)
    public static boolean soloLetras(String texto) {
        return texto != null && texto.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$");
    }

    //validaciones para los clientes
    public static boolean validarNombreCliente(String nombre) {
        if (nombre == null || nombre.trim().length() < 2) {
            return false;
        }
        return soloLetras(nombre);
    }

    public static boolean validarDPI(String dpi) {
        // 13 dígitos exactos
        return dpi != null && dpi.matches("^[0-9]{13}$");
    }

    public static boolean validarTelefono(String telefono) {
        // 8 dígitos exactos
        return telefono != null && telefono.matches("^[0-9]{8}$");
    }

    //validacione para las mascotas
    public static boolean validarNombreMascota(String nombre) {
        return soloLetras(nombre);
    }

    public static boolean validarEspecie(String especie) {
        return soloLetras(especie);
    }

    public static boolean validarColor(String color) {
        return soloLetras(color);
    }

    // edad de mascota permite decimales → se valida en String
    public static double validarEdad(String edadTexto) throws Exception {
        if (edadTexto == null || edadTexto.trim().isEmpty()) {
            return 0; // Edad por defecto
        }

        double edad;
        try {
            edad = Double.parseDouble(edadTexto);
        } catch (NumberFormatException e) {
            throw new Exception("La edad debe ser un número válido.");
        }

        if (edad < 0) {
            throw new Exception("Tu mascota no ha nacido.");
        }

        if (edad > 50) {
            throw new Exception("La edad ingresada es demasiado exagerada.");
        }

        return edad;
    }

    //validaciones para el producto
    public static boolean validarNombreProducto(String nombre) {
        return soloLetras(nombre);
    }

    public static boolean validarPrecio(double precio) throws Exception {
        if (precio < 0) {
            throw new Exception("El precio no puede ser negativo.");
        }
        return true;
    }

    // Cantidad NO permite decimales
    public static boolean validarCantidadTexto(String cantidad) {
        return cantidad.matches("^[0-9]+$"); // solo enteros
    }

    public static boolean validarCantidad(int cantidad) throws Exception {
        if (cantidad < 0) {
            throw new Exception("La cantidad no puede ser negativa.");
        }
        return true;
    }
}