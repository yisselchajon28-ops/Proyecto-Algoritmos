// Representa a un empleado que puede estar encargado de una bodega.
public class Empleado {
    // Datos básicos del empleado.
    private String id;
    private String nombre;
    private String dpi;
    private String direccion;
    private String puesto;
    private double salario;

    // Crea un empleado con todos sus datos.
    public Empleado(String id, String nombre, String dpi, String direccion, String puesto, double salario) {
        this.id = id;
        this.nombre = nombre;
        this.dpi = dpi;
        this.direccion = direccion;
        this.puesto = puesto;
        this.salario = salario;
    }

    // Devuelve el identificador del empleado.
    public String getId() {
        return id;
    }

    // Cambia el identificador del empleado.
    public void setId(String id) {
        this.id = id;
    }

    // Devuelve el nombre.
    public String getNombre() {
        return nombre;
    }

    // Cambia el nombre.
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Devuelve el DPI.
    public String getDpi() {
        return dpi;
    }

    // Cambia el DPI.
    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    // Devuelve la dirección.
    public String getDireccion() {
        return direccion;
    }

    // Cambia la dirección.
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    // Devuelve el puesto de trabajo.
    public String getPuesto() {
        return puesto;
    }

    // Cambia el puesto de trabajo.
    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    // Devuelve el salario.
    public double getSalario() {
        return salario;
    }

    // Cambia el salario.
    public void setSalario(double salario) {
        this.salario = salario;
    }

    // Muestra el ID y nombre cuando se elige un empleado en un componente Swing.
    @Override
    public String toString() {
        return id + " - " + nombre;
    }
}
