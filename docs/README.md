# Sistema de Ventas e Inventario 

Proyecto sencillo de Java con  Programación Orientada a Objetos, listas, `if`, `switch` y persistencia de archivos de
texto. (Se maneja desde la terminal).

# 1. Breve Introducción del Sistema 

Este sistema permite gestionar una tienda: desde registrar productos, clientes, empleados y bodegas; hasta cargar el inventario (stock) en las bodegas y registrar ventas mediante órdenes de compra. 

Cuando se registra una venta, el sistema resta del inventario la cantidad vendida; si la orden se cancela el producto regresa al inventario. Todos los datos de órdenes de compra se guardan en el archivo llamado *datos.txt*

Para dicho sistema se utilizó el lenguaje Java, la programación orientada a objetos, la persistencia de datos y fue posible hacerlo en equipo debido al uso de Git/GitHub para el control de los cambios y versiones. 

# 2. Diagrama UML 
1. Entidades del Sistema: Producto, Cliente, Empleado, Bodega, Inventario y Orden de compra. 

@startuml
    
     ________________      ___________________      ______________     ______________
    |   PRODUCTO     |    |      CLIENTE      |    |  EMPLEADO    |   |    BODEGA    |
    |________________|    |___________________|    |______________|   |______________|
    |  *codigo       |    | *dpi              |    | *id          |   | *id          |
    |  *nombre       |    | *nombre           |    | *nombre      |   | *idEmpleado  |
    |  *descripcion  |    | *direccion        |    | *dpi         |   | *capacidad   |
    |  *marca        |    | *fechaNacimiento  |    | *direccion   |   | *direccion   |
    |  *precio       |    | *tipo             |    | *puesto      |   |              |
    |                |    |                   |    | *salario     |   |              |
    |________________|    |___________________|    |______________|   |______________|
     ________________      ___________________
    |  INVENTARIO    |    |   ORDEN COMPRA    |
    |________________|    |___________________|
    | *idBodega      |    | *id               |
    | *codigoProducto|    | *dpiCliente       |
    | *cantidad      |    | *codigoProducto   |
    |________________|    | *cantidad         |
                          | *fecha            |                         
                          | *total            |
                          | *estado           |
                          | *idBodega         |
                          |___________________|
  
 

Bodega --> Empleado
Inventario --> Bodega
Inventario --> producto
OrdenCompra --> cliente
OrdenCompra --> producto
OrdenCompra --> Bodega

@enduml

Cada clase tiene su constructor y ls métodos de sus atributos. Además, Inventario tiene setCantidad() y OrdenCompra tiene setEstado(), porque son los únicos datos que cambian después de crearse. La clase *Main* contiene el menú, las clases y la lógica del sistema (switches, ifs).

## 3. MANUAL DE USUARIO

Abra una terminal en esta carpeta:

```bash
javac *.java
java main
```
El programa debe ejecutarse siempre desde la misma carpeta, porque datos.txt se crea y se lee en el directorio de trabajo actual. 

El programa guarda y carga los registros en el archivo `datos.txt`. No escriba
el símbolo `|` en los datos.

Algunos registros dependen de otros, por lo que se recomienda este orden:

1. Registrar productos (opción 1).
2. Registrar clientes (opción 2).
3. Registrar empleados (opción 3).
4. Registrar bodegas (opción 4), que requiere un empleado.
5. Agregar productos a una bodega (opción 5), que requiere productos y bodegas.
6. Registrar ventas (opción 6).

    *MENU PRINCIPAL*

        === SISTEMA DE VENTAS ===
        1. Registrar producto
        2. Registrar cliente
        3. Registrar empleado
        4. Registrar bodega
        5. Agregar productos a bodega
        6. Registrar Orden de Compra
        7. Ver inventario
        8. Ver o cancelar órdenes
        9. Salir
        Elija una opción:

Escriba el número de la opción y presione Enter. Si escribe un número que no está en el menú aparece «Opción incorrecta.» si escribe letras, el programa le pedirá un número entero.

                                            *Opciones del menú*
    * Opción 1: Registrar producto. Escriba código, nombre, descripción, marca y precio.
     El código no puede repetirse y el precio no puede ser negativo.

    * Opción 2: Registrar cliente. Escriba DPI, nombre, dirección,
      fecha de nacimiento (formato AAAA-MM-DD, por ejemplo 1990-05-21) y tipo (Regular o VIP).
      El DPI no puede repetirse.

    * Opción 3: Registrar empleado. Escriba ID de empleado, nombre, DPI, dirección, 
      puesto y salario. El ID no puede repetirse y el salario no puede ser negativo.

    * Opción 4: Registrar bodega. Primero debe existir al menos un empleado.
      Escriba el ID de la bodega; el programa muestra la lista de empleados y usted
      escribe el ID del empleado encargado. Después indique la capacidad en unidades 
      (mayor que cero) y la dirección.

    * Opción 5: Agregar productos a bodega. 
      El programa muestra las bodegas y usted escribe el ID de la bodega; luego muestra
      los productos y usted escribe el código del producto y la cantidad a agregar. 
      Si el producto ya estaba en esa bodega, la cantidad se suma. 
      La cantidad total de la bodega no puede superar su capacidad.

    * Opción 6: Registrar Orden de Compra (venta). Es necesario tener clientes, 
      productos y bodegas. Siga los pasos:
      1. Escriba el DPI del cliente de la lista mostrada.
      2. Escriba el ID de la bodega desde la que se despachará.
      3. Escriba el código del producto.
      4. Escriba la cantidad.

      Si la bodega tiene unidades suficientes, el sistema descuenta el stock, registra 
      la orden como Completada y muestra el total de la venta, por ejemplo «Venta 
      completada. Total: Q 5000.0».

    * Opción 7: Ver inventario. Muestra, por cada bodega, el código y nombre del 
      producto y la cantidad disponible. Si no hay productos en inventario, el programa 
      lo indica.

    * Opción 8: Ver o cancelar órdenes. Muestra todas las órdenes con su ID, cliente, 
      producto, cantidad, fecha, total y estado. Para cancelar una orden escriba su ID 
      (por ejemplo ORD-1); para volver al menú escriba 0. Al cancelar, las unidades 
      regresan al inventario de la bodega y la orden queda como Cancelada.

    * Opción 9: Salir. Cierra el programa. Los datos ya fueron guardados después de  
      cada operación.

## 3.1 OPCIONES

El menú permite registrar productos, clientes, empleados, bodegas e inventario;
registrar ventas; consultar existencias; y cancelar órdenes.

Una orden registra un producto. Para vender otro producto, registre otra orden.

# 4. Estructura de la clase Main

Main mantiene seis listas estáticas (ArrayList) con los datos en memoria: productos, clientes, empleados, bodegas, inventarios y ordenes. Un objeto Scanner (teclado) lee los datos que escribe el usuario. 

```bash
Programa | main 
Carga los datos y muestra el menú en un ciclo hasta elegir la opción 9.
```
```bash
Registro | registrarProducto,registrarCliente, registrarEmpleado, registrarBodega
Piden los datos, validan y agregan el objeto a su lista.
```
```bash
Inventario | agregarInventario 
Suma stock de un producto en una bodega, respetando su capacidad.
```
```bash
Ventas | registrarVentas 
Crea la orden de compra y descuenta el stock
```
```bash 
Consulta | verInventario, verOrdenesverProductos, verClientes, verEmpleados, verBodegas
Muestra listados; verOrdenes también permite cancelar una orden.
```
```bash 
Búsqueda | buscarProducto, buscarCliente, buscarEmpleado, buscarBodega, buscarInventario, buscarOrden. 
Recorrre la lista y devuelven el objeto o null si no existe.
```
```bash
Calculo | totalEnBodega
Suma las unidades almacenadas en una bodega.
```
```bash
Entrada de datos| leerTexto, leerEntero, leerDecimal
Leen del teclado y repiten la pregunta hasta que el valor sea válido. 
```
```bash 
Persistencia| guardarDatos, cargarDatos
Escriben y leen el archivo datos.txt.
```
# 5.Persistencia en archivos 
Todos los datos se guardan en un único archivo de texto, *datos.txt*, con un registro por línea y los campos separados por una barra vertical. La primera parte de cada línea indica el tipo de registro: 
PRODUCTO, CLIENTE, EMPLEADO, BODEGA INVENTARIO,ORDEN DE COMPRA.

* guardarDatos() se ejecuta después de cada registro cambio de stock, venta o cancelación y reescribe el archivo completo. 

# 6. DIAGRAMA

El diagrama sencillo de las clases está en [docs/diagrama-uml.puml](docs/diagrama-uml.puml).
