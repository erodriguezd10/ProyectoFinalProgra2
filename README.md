# Sistema de gestión de clínica veterinaria con firebase

> **Versión:** 0.5 VersionFinal  
> **Desarrollado por:** GRUPO_PROG_2_C_1_4  
> **Integrantes:
> -Elmer Yovani Rodríguez de Paz 7690-22-11804, Project manager y backend
> -Kevin Reyes Ambrocio Bautista 7690-23-3155, Frotend
> -Eliezer Juan Israel López Garcia 7690-20-19696, Gestor de calidad
> -Leste Miguel Angel lópez Duarte 7690-23-25649, Documentador
> **Año:\*\* Noviembre 2025

## Descripción del proyecto

Sistema de escritorio desarrollado en Java para la gestión integral de una clínica veterinaria. La aplicación permite administrar clientes, mascotas, historial médico, productos y vacunas, utilizando Firebase Firestore como base de datos en la nube.

### Características principales

- **Gestión de Clientes:** Crear, actualizar, eliminar y consultar información de clientes
- **Registro de Mascotas:** Administrar mascotas con información detallada (raza, edad, color, etc.)
- **Historial Médico:** Mantener registro completo del historial médico de cada mascota
- **Gestión de Productos:** Controlar inventario de medicamentos y productos veterinarios
- **Control de Vacunas:** Registrar y dar seguimiento a las vacunas aplicadas
- **Interfaz Gráfica Moderna:** Interfaz elegante basada en FlatLaf con tema claro
- **Base de Datos en la Nube:** Sincronización automática con Firebase Firestore

## Tecnologías utilizadas

| Tecnología                 | Versión | Propósito                             |
| -------------------------- | ------- | ------------------------------------- |
| **Java**                   | 11+     | Lenguaje principal                    |
| **Maven**                  | 3.x     | Gestor de dependencias y construcción |
| **Firebase Admin SDK**     | 9.2.0   | Integración con Firebase              |
| **Google Cloud Firestore** | 3.9.5   | Base de datos en la nube              |
| **Google Cloud Core**      | 2.24.0  | Librerías base de Google Cloud        |
| **FlatLaf**                | 3.2.1   | Look and Feel moderno para Swing      |
| **Swing**                  | Nativo  | Framework GUI                         |

## Estructura del proyecto

```
proyecto/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── gui/
│   │   │   │   ├── MainWindow.java           # Ventana principal
│   │   │   │   └── panels/
│   │   │   │       ├── ClientesPanel.java    # Panel de clientes
│   │   │   │       ├── MascotasPanel.java    # Panel de mascotas
│   │   │   │       ├── HistorialPanel.java   # Panel de historial médico
│   │   │   │       └── ProductosPanel.java   # Panel de productos
│   │   │   ├── main/
│   │   │   │   └── Main.java                 # Punto de entrada
│   │   │   ├── models/
│   │   │   │   ├── Cliente.java              # Modelo de cliente
│   │   │   │   ├── Mascota.java              # Modelo de mascota
│   │   │   │   ├── HistorialMedico.java      # Modelo de historial
│   │   │   │   ├── Producto.java             # Modelo de producto
│   │   │   │   └── Vacuna.java               # Modelo de vacuna
│   │   │   ├── services/
│   │   │   │   ├── FirebaseService.java      # Servicio de Firebase
│   │   │   │   ├── ClienteService.java       # Servicio de clientes
│   │   │   │   ├── MascotaService.java       # Servicio de mascotas
│   │   │   │   ├── HistorialMedicoService.java # Servicio de historial
│   │   │   │   └── ProductoService.java      # Servicio de productos
│   │   │   └── utils/
│   │   │       ├── Constantes.java           # Constantes de la app
│   │   │       ├── GeneradorID.java          # Generador de IDs
│   │   │       └── Validaciones.java         # Validaciones comunes
│   │   └── resources/
│   └── test/
│       └── java/
├── firebase-config/
│   └── servicio-veterinaria-firebase-adminsdk.json  # Credenciales Firebase
├── POM.XML                                   # Configuración Maven
├── mvnw                                      # Maven Wrapper (Linux/Mac)
├── mvnw.cmd                                  # Maven Wrapper (Windows)
```

## Instalación y configuración

### Requisitos previos

- **Java Development Kit (JDK)** 8 o superior
- **Apache Maven** 3.6+ (opcional, se incluye Maven Wrapper)
- **Firebase Console** - Proyecto configurado en Firebase

### Pasos de instalación

1. **Clonar o descargar el proyecto**

   ```bash
   git clone <url-del-repositorio>
   cd veterinaria-firebase
   ```

2. **Configurar credenciales de Firebase**

   - Descargar el archivo de credenciales desde la consola de Firebase
   - Colocar el archivo `servicio-veterinaria-firebase-adminsdk.json` en la carpeta `firebase-config/`
   - Actualizar la ruta en `FirebaseService.java` si es necesario

3. **Compilar el proyecto con Maven**

   **En Windows:**

   ```cmd
   mvnw.cmd clean install
   ```

   **En Linux/Mac:**

   ```bash
   ./mvnw clean install
   ```

   **O si tienes Maven instalado globalmente:**

   ```bash
   mvn clean install
   ```

4. **Ejecutar la aplicación**

   **Con Maven:**

   ```bash
   mvn exec:java -Dexec.mainClass="main.Main"
   ```

   **En NetBeans:**

   - Click derecho sobre el proyecto
   - Seleccionar "Clean and Build"
   - Luego seleccionar "Run"

## Guía basíca de uso

### Interfaz Principal

La aplicación presenta una interfaz con pestañas para cada módulo:

- **Clientes:** Gestione la información de clientes registrados
- **Mascotas:** Registre y administre las mascotas de los clientes
- **HistorialMedico:** Mantenga el registro médico de cada mascota
- **Productos:** Controle el inventario de medicamentos

### Operaciones CRUD

Cada módulo permite:

- **Crear (C):** Añadir nuevos registros
- **Leer (R):** Consultar información existente
- **Actualizar (U):** Modificar datos
- **Eliminar (D):** Borrar registros

## Configuración de Firebase

### Inicializar Firebase Service

El servicio de Firebase se inicializa automáticamente al cargar la aplicación. Asegúrese de que:

1. El archivo `firebase-config/servicio-veterinaria-firebase-adminsdk.json` está presente
2. La ruta en `FirebaseService.java` es correcta
3. Tiene conexión a internet para sincronizar con Firestore

### Estructura de Firestore

La base de datos utiliza las siguientes colecciones:

```
firestore/
├── clientes/
├── mascotas/
├── historialMedico/
├── productos/
└── vacunas/
```

## Estructura de versiónes

````
PROYECTOFINAL/
├──target                            #Javadoc
├──v01*noviembre11*/
├──v0.2*noviembre14*/
├──v0.3*noviembre13*/
├──v0.4*noviembre15*/
├──v0.5*noviembre15*/                #Versión final
├──README                            #Este archivo
´´´

## Personalización

### Tema visual

El proyecto utiliza FlatLaf para una interfaz moderna. Para cambiar el tema, modifique en `Main.java`:

```java
// FlatLaf proporciona varios temas:
// - FlatLightLaf (predeterminado)
// - FlatDarkLaf
// - FlatIntelliJLaf
// - FlatDarculaLaf
UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
````

## Notas importantes

- La aplicación requiere **conexión a internet** para funcionar correctamente con Firebase
- Los datos se sincronizan automáticamente con la nube
- Se recomienda hacer respaldos periódicos de la base de datos Firebase
- Asegúrese de mantener las credenciales de Firebase en un lugar seguro

## Solución de problemas

### Error de conexión a Firebase

- Verifique la conexión a internet
- Compruebe que el archivo de credenciales está en la ubicación correcta
- Valide que el proyecto Firebase está activo

### Error al compilar

- Limpie el caché de Maven: `mvn clean`
- Reinicie el IDE
- Verifique que Java 11+ está instalado: `java -version`

### Interfaz no se muestra correctamente

- Verifique que FlatLaf está correctamente instalado
- Compruebe los requisitos de versión de Java

## Recursos adicionales

- [Documentación Firebase Admin SDK](https://firebase.google.com/docs/database/admin/start)
- [Documentación Google Cloud Firestore](https://cloud.google.com/firestore/docs)
- [Documentación FlatLaf](https://www.formdev.com/flatlaf/)
- [Documentación Java Swing](https://docs.oracle.com/javase/tutorial/uiswing/)

## Licencia

Este proyecto es proporcionado para propósitos educativos.

## Equipo de Desarrollo

**Desarrollado por:** GRUPO_PROG_2_C_1_4

**Última actualización:** 15 noviembre 2025  
**Estado del proyecto:** Finalizado

Nota importante:
Las credenciales de acceso a la base de datos mediante el archi json estan fuera de este repositorio
esto mismo se encuentra dentro del proyecto subido a canvas
la carpeta que contienen el archivo .json lleva por nombre "firebase-config"
