package uniandes.isis2304.SuperAndes.negocio;

public class OfertaMenorQueSuma2 implements VOOfertaMenorQueSuma2{
    
    private long iDOferta;
    
    private String nombre;
    
    private String restriccion;
    
    private double precio;

    public OfertaMenorQueSuma2(long iDOferta, String nombre, String restriccion, double precio) {
        super();
        this.iDOferta = iDOferta;
        this.nombre = nombre;
        this.restriccion = restriccion;
        this.precio = precio;
    }

    public long getIdOferta() {
        // TODO Auto-generated method stub
        return iDOferta;
    }

    public String getNombre() {
        // TODO Auto-generated method stub
        return nombre;
    }

    public String getRestriccion() {
        // TODO Auto-generated method stub
        return restriccion;
    }

    public double getPrecio() {
        // TODO Auto-generated method stub
        return precio;
    }

    @Override
    public String toString() {
        return "OfertaMenorQueSuma2 [iDOferta=" + iDOferta + ", nombre=" + nombre + ", restriccion=" + restriccion
                + ", precio=" + precio + "]";
    }
    
    
    

}
