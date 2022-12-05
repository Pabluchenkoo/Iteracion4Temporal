package uniandes.isis2304.SuperAndes.negocio;

public class Carrito implements VOCarrito{

    private long idCarrito;
    private int asignado;
    private int numDoc;

    public Carrito(long idCarrito, int asignado, int numDoc) {
        this.idCarrito = idCarrito;
        this.asignado = asignado;
        this.numDoc = numDoc;
    }

    public Carrito() {
        this.idCarrito = 0;
        this.asignado = 0;
        this.numDoc = 0;
    }

    public long getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(long idCarrito) {
        this.idCarrito = idCarrito;
    }

    public int getAsignado() {
        return asignado;
    }

    public void setAsignado(int asignado) {
        this.asignado = asignado;
    }

    public int getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(int numDoc) {
        this.numDoc = numDoc;
    }

    @Override
    public String toString() {
        return "Carrito [idCarrito=" + idCarrito + ", asignado=" + asignado + ", numDoc=" + numDoc + "]";
    }

}
