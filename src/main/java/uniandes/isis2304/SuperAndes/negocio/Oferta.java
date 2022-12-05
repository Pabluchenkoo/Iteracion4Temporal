package uniandes.isis2304.SuperAndes.negocio;

import java.sql.Timestamp;

public class Oferta implements VOOferta{

    private String codigoDeBarras;
    
    private Long iDOferta;
    
    private String nombre;
    
    private String restriccion;

    private Long unidadesDisponibles;

    private boolean activa;

    private Timestamp fechaCaducidad;
    
    
    

    public Oferta(String codigoDeBarras, Long iDOferta, String nombre,
                  String restriccion, Long unidadesDisponibles, boolean activa, Timestamp fechaCaducidad) {
        super();
        this.codigoDeBarras = codigoDeBarras;
        this.iDOferta = iDOferta;
        this.nombre = nombre;
        this.restriccion = restriccion;
        this.unidadesDisponibles = unidadesDisponibles;
        this.activa = activa;
        this.fechaCaducidad = fechaCaducidad;
    }

    @Override
    public String getCodigoDeBarras() {
        // TODO Auto-generated method stub
        return codigoDeBarras;
    }

    @Override
    public Long getIdOferta() {
        // TODO Auto-generated method stub
        return iDOferta;
    }

    @Override
    public String getNombre() {
        // TODO Auto-generated method stub
        return nombre;
    }

    @Override
    public String getRestriccion() {
        // TODO Auto-generated method stub
        return restriccion;
    }



    @Override
    public String toString() {
        return "Oferta [codigoDeBarras=" + codigoDeBarras + ", iDOferta=" + iDOferta + ", nombre=" + nombre
                + ", restriccion=" + restriccion + "]";
    }

    @Override
    public Long getUnidadesDisponibles() {
        return unidadesDisponibles;
    }

    @Override
    public boolean getActiva() {
        return activa;
    }

    @Override
    public Timestamp getFechaCaducidad() {
        return fechaCaducidad;
    }


}
