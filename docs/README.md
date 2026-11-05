# Cómo funciona el sistema

Esta guía explica el programa en palabras sencillas. El proyecto está hecho con
Java y Swing. Swing es la parte de Java que permite crear ventanas, botones,
formularios y tablas.

## 1. Abrir el programa

Abre una terminal en la carpeta donde están los archivos `.java` y ejecuta:

```bash
javac *.java
java main
```

`javac` compila los archivos de Java. `java main` inicia el método `main` y
abre la ventana.

## 2. Qué archivos hay

| Archivo | Para qué sirve |
|---|---|
| `main.java` | Crea la ventana, muestra las pestañas y ejecuta las acciones |
| `producto.java` | Guarda los datos de cada producto |
| `cliente.java` | Guarda los datos de cada cliente |
| `Empleado.java` | Guarda los datos de cada empleado |
| `Bodega.java` | Guarda los datos de una bodega y su encargado |
| `Inventario.java` | Guarda el producto, la bodega y la cantidad disponible |
| `OrdenCompra.java` | Guarda una venta y su estado |
| `datos.txt` | Guarda los registros para que sigan allí al cerrar el programa |

Las clases de entidades son como fichas. Por ejemplo, un objeto `producto`
representa un solo producto y guarda su código, nombre, descripción, marca y
precio.

## 3. Qué significan algunas palabras del código

- **Clase:** el molde que dice qué datos tendrá algo. `producto` es un molde
  para crear productos.
- **Objeto:** una ficha creada usando una clase. `nuevo` en el formulario es un
  objeto `producto`.
- **Atributo:** un dato guardado dentro del objeto, como `precio`.
- **Constructor:** el método que da los primeros valores al crear el objeto.
- **Getter:** método que permite consultar un atributo, como `getPrecio()`.
- **Setter:** método que cambia un atributo, como `setCantidad()`.
- **`ArrayList`:** una lista que puede guardar varios objetos, como todos los
  clientes.
- **Método:** una función que pertenece a una clase y hace una tarea.
- **`if`:** permite tomar una decisión. Por ejemplo: si no hay stock, no vender.
- **`for`:** revisa elementos de una lista uno por uno.
- **`try` / `catch`:** permite mostrar un mensaje si ocurre un error.
- **`JButton`:** botón; al hacer clic se ejecuta una acción.
- **`JTable`:** tabla que muestra los registros.
- **`JComboBox`:** lista desplegable para seleccionar un registro existente.

## 4. Cómo empieza

En `main.java`, el método `main` hace dos cosas:

1. Llama a `cargarDatos()` para leer `datos.txt`.
2. Usa `SwingUtilities.invokeLater(...)` para crear y mostrar la ventana.

El constructor `main()` configura el título y el tamaño de la ventana. Después
agrega tres partes principales: el encabezado, las pestañas y el mensaje de
estado de abajo.

## 5. Qué hacen las pestañas

- **Inicio:** muestra el nombre y un mensaje de bienvenida.
- **Productos:** registra un producto y muestra la tabla de productos.
- **Clientes:** registra clientes de tipo Regular o VIP.
- **Empleados:** registra al encargado que se puede asignar a una bodega.
- **Bodegas:** registra bodegas y su capacidad máxima en unidades.
- **Inventario:** agrega unidades de un producto a una bodega.
- **Ventas:** vende un producto, muestra las órdenes y permite cancelarlas.

Los formularios usan campos de texto. En inventario y ventas se usan listas
desplegables para elegir elementos que ya fueron registrados.

## 6. Ejemplo: cómo se registra un producto

1. El usuario escribe los datos y pulsa **Registrar producto**.
2. `crearTabProductos()` obtiene los textos del formulario.
3. `texto(...)` revisa que cada dato sea válido y no esté vacío.
4. `buscarProducto(...)` comprueba que el código no exista.
5. Se crea el objeto con `new producto(...)`.
6. El objeto se agrega a la lista `productos`.
7. `guardarConReversa(...)` intenta escribir los datos en el archivo.
8. `ejecutar(...)` actualiza las tablas o muestra un mensaje si algo falla.

Los formularios de clientes, empleados y bodegas siguen una idea parecida:
validan los datos, crean un objeto y lo guardan.

## 7. Cómo funciona el inventario

Cada objeto `Inventario` relaciona:

- el ID de una bodega;
- el código de un producto;
- la cantidad de ese producto.

`agregarExistencias(...)` revisa que la cantidad sea positiva y que la suma de
las unidades no exceda la capacidad de la bodega. Si el producto ya tiene una
fila de inventario, suma la cantidad. Si no, crea una fila nueva.

`unidadesEnBodega(...)` suma todos los productos guardados en esa bodega.
`buscarInventario(...)` encuentra la fila de un producto en una bodega.

## 8. Cómo funciona una venta

El método `completarVenta(...)` sigue estos pasos:

1. Revisa que la cantidad sea mayor que cero.
2. Busca el producto en el inventario de la bodega.
3. Revisa que haya suficientes unidades.
4. Resta las unidades vendidas.
5. Crea una orden con fecha, total y estado **Completada**.
6. Guarda los cambios en `datos.txt`.

El total se calcula con esta operación:

```text
precio del producto × cantidad vendida
```

Si no hay suficiente stock, el método muestra un error y no crea la orden.
Cada orden de este proyecto contiene un solo producto.

## 9. Cómo funciona una cancelación

Al cancelar una orden, `cancelarOrdenSeleccionada()` obtiene la fila que se
eligió en la tabla. Luego `cancelarVenta(...)`:

1. Revisa que la orden no esté cancelada.
2. Comprueba que la bodega tenga espacio para recibir los productos.
3. Devuelve las unidades al inventario.
4. Cambia el estado de la orden a **Cancelada**.
5. Guarda los cambios en el archivo.

## 10. Cómo se guardan los datos

`guardarDatos()` convierte cada objeto en una línea de texto. Por ejemplo:

```text
P|COD01|Cuaderno|Rayado|Papel|10.0
I|BOD01|COD01|5
O|ORD-1|1234567890123|COD01|2|2026-11-04|20.0|Completada|BOD01
```

La primera letra indica el tipo de registro: `P` producto, `C` cliente, `E`
empleado, `B` bodega, `I` inventario y `O` orden. El símbolo `|` separa los
datos. Por eso el sistema no permite escribir ese símbolo en los campos.

`cargarDatos()` lee el archivo línea por línea. `leerRegistro(...)` identifica
el tipo de registro y crea el objeto correspondiente. También se aceptan los
registros con nombres largos creados por una versión anterior.

El archivo se busca en la carpeta desde donde ejecutaste `java main`. Al
guardar, se reescribe con todos los registros actuales.

## 11. Funciones principales de `main.java`

| Método | Qué hace |
|---|---|
| `crearPestanas()` | Construye las secciones de la ventana |
| `crearTabProductos()` y similares | Preparan cada formulario de registro |
| `actualizarPantalla()` | Refresca tablas y opciones desplegables |
| `ejecutar(...)` | Corre una acción y muestra errores entendibles |
| `buscarProducto(...)` y similares | Recorren listas para encontrar un registro |
| `agregarExistencias(...)` | Añade unidades respetando la capacidad |
| `completarVenta(...)` | Crea una orden y descuenta existencias |
| `cancelarVenta(...)` | Cancela una orden y devuelve existencias |
| `cargarDatos()` | Lee los registros del archivo |
| `guardarDatos()` | Escribe los registros en el archivo |

Los comentarios dentro de los archivos `.java` explican también los métodos de
las clases y los pasos importantes de cada operación.

## 12. Prompt para pedirle una explicación a otra IA

Copia este texto y pégalo en otra inteligencia artificial. Si puedes, adjunta
los archivos `.java` del proyecto:

```text
Estoy aprendiendo Java y necesito que me expliques este proyecto como a una
persona principiante, sin asumir que conozco conceptos avanzados.

Es un sistema sencillo de ventas e inventario con ventanas Java Swing. Tiene
las clases main, producto, cliente, Empleado, Bodega, Inventario y
OrdenCompra. Los datos se guardan en el archivo de texto datos.txt.

Explícame el código usando los nombres reales de las clases, variables y
métodos. Empieza por cómo se inicia el programa y luego explica:
1. Qué hace cada clase y qué atributos tiene.
2. Qué son los constructores, getters y setters que aparecen aquí.
3. Cómo se crean las pestañas, formularios, botones y tablas.
4. Qué ocurre paso a paso cuando registro un producto o un cliente.
5. Cómo se agregan unidades al inventario y se limita la capacidad de una
   bodega.
6. Cómo una venta revisa el stock, calcula el total, guarda una orden y
   descuenta las unidades.
7. Cómo se cancela una orden y se devuelven las unidades.
8. Cómo se leen y escriben los datos en datos.txt.
9. Qué significan ArrayList, for, if, try/catch, lambda y las partes de Swing
   que realmente usa este proyecto.

Usa ejemplos pequeños y señala métodos o fragmentos concretos. Explica las
palabras nuevas cuando aparezcan. No reescribas el proyecto ni propongas una
arquitectura avanzada: quiero entender el código actual. Al final, hazme cinco
preguntas cortas para comprobar si entendí y espera mis respuestas antes de
darme las soluciones.
```
