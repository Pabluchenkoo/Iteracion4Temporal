package uniandes.isis2304.SuperAndes.negocio;

public class Rol implements VORol{

    private long id;

    private String nombre;

    public Rol(long id, String nombre) {
        super();
        this.id = id;
        this.nombre = nombre;
    }

    public Rol() {
        this.id = 30;
        this.nombre = "";
    }

    @Override
    public String toString() {
        return "Rol{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public String getNombre() {
        return null;
    }
}
