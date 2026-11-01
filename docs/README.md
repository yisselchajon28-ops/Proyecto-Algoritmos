# Sistema de Control de Inventario y Gestión Comercial

Aplicación de escritorio desarrollada en Java orientada a la gestión integral de inventarios, registro de entidades del sistema y procesamiento de operaciones comerciales. El software sustituye la interfaz por consola mediante un entorno gráfico interactivo implementado con la biblioteca nativa Java Swing.

---

## 1. Características Generales

* **Interfaz Gráfica Nativa (GUI):** Construida mediante componentes de Java Swing (`JFrame`, `JTabbedPane`, `JTable`, `JPanel`).
* **Arquitectura Orientada a Objetos:** Separación modular de entidades (`Producto`, `Cliente`, `Empleado`, `Bodega`, `Inventario`, `OrdenCompra`).
* **Sin Dependencias Externas:** Funciona exclusivamente con las bibliotecas estándar del Java Development Kit (JDK).
* **Persistencia Local de Datos:** Lectura y escritura automatizada sobre el archivo local `datos.txt` al iniciar y cerrar la aplicación.
* **Control Integral de Stock:** Validación de existencias en bodega para la ejecución de ventas y actualización automática en caso de cancelaciones.

---

## 2. Estructura del Proyecto

El proyecto está organizado en la raíz del repositorio con la siguiente estructura de archivos fuente:

* `main.java`: Punto de entrada de la aplicación, construcción del frame principal y controlador de la interfaz Swing.
* `producto.java`: Definición de la entidad Producto (código, nombre, descripción, precio, categoría).
* `cliente.java`: Definición de la entidad Cliente (ID, nombre, teléfono, dirección, correo).
* `Empleado.java`: Definición de la entidad Empleado (ID, nombre, puesto, departamento).
* `Bodega.java`: Definición de la entidad Bodega (ID, nombre, ubicación, capacidad).
* `Inventario.java`: Relación de existencias entre un producto y una bodega específica.
* `OrdenCompra.java`: Registro de transacciones comerciales y estado de las órdenes.
* `datos.txt`: Archivo plano de almacenamiento local persistente.
* `docs/README.md`: Documentación adicional del proyecto.

---

## 3. Módulos y Funcionalidades

### 3.1. Módulo de Registros (Entidades)
Permite el alta y la visualización de los elementos base del sistema mediante formularios con validación de campos:
* **Productos:** Asignación de identificadores, nombres, precios base y categorías.
* **Clientes:** Almacenamiento de datos de contacto e identificación.
* **Empleados:** Gestión de personal asociando cargos y áreas de desempeño.
* **Bodegas:** Definición de puntos físicos de almacenamiento y capacidades límite.

### 3.2. Módulo de Inventario
* Asignación de cantidades físicas de un producto determinado a una bodega específica.
* Consulta en tiempo real de disponibilidad por ubicación.

### 3.3. Módulo de Ventas y Órdenes de Compra
* **Procesamiento de Ventas:** Generación de órdenes asociando cliente, empleado, bodega, producto y cantidad. El sistema verifica automáticamente la disponibilidad de stock en la bodega antes de procesar la transacción.
* **Cancelación de Órdenes:** Permite revertir una transacción registrada, reincorporando la cantidad de productos vendida de vuelta al stock de la bodega correspondiente.

### 3.4. Tablas y Consultas
* Visualización en `JTable` para consultar de forma inmediata el listado de productos, clientes, existencias e historial de órdenes.

---

## 4. Requisitos del Sistema e Instalación

### Requisitos Prerequisito
* **Java Development Kit (JDK):** Versión 8 o superior instalada y configurada en las variables de entorno de la máquina objetivo.

### Compilación y Ejecución desde Terminal

Navegar hasta la carpeta raíz del proyecto y ejecutar los siguientes comandos:

```bash
# Compilar todas las clases del proyecto
javac *.java

# Iniciar la aplicación en modo gráfico
java main