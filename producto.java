// Representa un producto que se puede guardar en el inventario y vender.
public class producto {
    // Datos que describen el producto.
    private String codigo;
    private String nombre;
    private String descripcion;
    private String marca;
    private double precio;

    // Crea un producto con todos sus datos.
    public producto(String codigo, String nombre, String descripcion, String marca, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.marca = marca;
        this.precio = precio;
    }

    // Devuelve el código que identifica al producto.
    public String getCodigo() {
        return codigo;
    }

    // Cambia el código del producto.
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    // Devuelve el nombre que se muestra en pantalla.
    public String getNombre() {
        return nombre;
    }

    // Cambia el nombre del producto.
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Devuelve la descripción del producto.
    public String getDescripcion() {
        return descripcion;
    }

    // Cambia la descripción.
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // Devuelve la marca.
    public String getMarca() {
        return marca;
    }

    // Cambia la marca.
    public void setMarca(String marca) {
        this.marca = marca;
    }

    // Devuelve el precio por una unidad.
    public double getPrecio() {
        return precio;
    }

    // Cambia el precio por una unidad.
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    // Indica cómo se muestra el producto en una lista desplegable.
    @Override
    public String toString() {
        return codigo + " - " + nombre;
    }
}
