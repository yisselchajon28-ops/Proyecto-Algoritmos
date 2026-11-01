public class Empleado {
    private String id;
    private String nombre;
    private String dpi;
    private String direccion;
    private String puesto;
    private double salario;

    public Empleado(String id, String nombre, String dpi, String direccion, String puesto, double salario) {
        this.id = id;
        this.nombre = nombre;
        this.dpi = dpi;
        this.direccion = direccion;
        this.puesto = puesto;
        this.salario = salario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return id + " - " + nombre;
    }
}
