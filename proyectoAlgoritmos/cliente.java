public class cliente {
    // Atributos
    private String dpi;
    private String nombre;
    private String direccion;
    private String fechaNacimiento;
    private String tipo; // Normal o VIP

    // Constructor
    public cliente(String dpi, String nombre, String direccion, String fechaNacimiento, String tipo) {
        this.dpi = dpi;
        this.nombre = nombre;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.tipo = tipo;
    }

    // Getter y Setter

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
