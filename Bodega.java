// Representa un lugar de almacenamiento y su capacidad máxima en unidades.
public class Bodega {
    // El ID del empleado enlaza la bodega con su encargado.
    private String id;
    private String idEmpleado;
    private int capacidad;
    private String direccion;

    // Crea una bodega con su encargado, capacidad y dirección.
    public Bodega(String id, String idEmpleado, int capacidad, String direccion) {
        this.id = id;
        this.idEmpleado = idEmpleado;
        this.capacidad = capacidad;
        this.direccion = direccion;
    }

    // Devuelve el identificador de esta bodega.
    public String getId() {
        return id;
    }

    // Cambia el identificador de la bodega.
    public void setId(String id) {
        this.id = id;
    }

    // Devuelve el ID del empleado encargado.
    public String getIdEmpleado() {
        return idEmpleado;
    }

    // Cambia el empleado encargado.
    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    // Devuelve la cantidad máxima de unidades que caben.
    public int getCapacidad() {
        return capacidad;
    }

    // Cambia la capacidad máxima.
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    // Devuelve la dirección de la bodega.
    public String getDireccion() {
        return direccion;
    }

    // Cambia la dirección.
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    // Muestra datos breves de la bodega en una lista desplegable.
    @Override
    public String toString() {
        return id + " - " + direccion;
    }
}
