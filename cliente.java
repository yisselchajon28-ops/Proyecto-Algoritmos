// Guarda los datos personales de un cliente de la tienda.
public class cliente {
    // Información que identifica y describe al cliente.
    private String dpi;
    private String nombre;
    private String direccion;
    private String fechaNacimiento;
    private String tipo; // Normal o VIP

    // Crea un cliente con los datos recibidos desde el formulario.
    public cliente(String dpi, String nombre, String direccion, String fechaNacimiento, String tipo) {
        this.dpi = dpi;
        this.nombre = nombre;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.tipo = tipo;
    }

    // Devuelve el DPI del cliente.
    public String getDpi() {
        return dpi;
    }

    // Cambia el DPI del cliente.
    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    // Devuelve el nombre del cliente.
    public String getNombre() {
        return nombre;
    }

    // Cambia el nombre del cliente.
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Devuelve la dirección del cliente.
    public String getDireccion() {
        return direccion;
    }

    // Cambia la dirección del cliente.
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    // Devuelve la fecha de nacimiento guardada como texto.
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    // Cambia la fecha de nacimiento.
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    // Devuelve el tipo del cliente: Regular o VIP.
    public String getTipo() {
        return tipo;
    }

    // Cambia el tipo del cliente.
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // Define cómo se identifica el cliente en una lista desplegable.
    @Override
    public String toString() {
        return nombre + " - " + dpi;
    }
}
