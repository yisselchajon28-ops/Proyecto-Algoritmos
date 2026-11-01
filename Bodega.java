public class Bodega {
    private String id;
    private String idEmpleado;
    private int capacidad;
    private String direccion;

    public Bodega(String id, String idEmpleado, int capacidad, String direccion) {
        this.id = id;
        this.idEmpleado = idEmpleado;
        this.capacidad = capacidad;
        this.direccion = direccion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return id + " - " + direccion;
    }
}
