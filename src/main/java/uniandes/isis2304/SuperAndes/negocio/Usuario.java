package uniandes.isis2304.SuperAndes.negocio;

public class Usuario implements VOUsuario{
    
    private long iDRol;

    private long cedula;
    
    private long iDSucursal;
    
    private long nitSupermercado;
    
    private String nombre;
    
    private String correo;
    
    private String palabraClave;
    
    

    public Usuario(long iDRol,long cedula, long iDSucursal, long nitSupermercado, String nombre, String correo,
            String palabraClave) {
        super();
        this.iDRol = iDRol;
        this.cedula = cedula;
        this.iDSucursal = iDSucursal;
        this.nitSupermercado = nitSupermercado;
        this.nombre = nombre;
        this.correo = correo;
        this.palabraClave = palabraClave;
    }
    public Usuario() {
        super();
        this.iDRol = 0;
        this.cedula = 0;
        this.iDSucursal = 0;
        this.nitSupermercado = 0;
        this.nombre = "";
        this.correo = "";
        this.palabraClave = "";
    }

    @Override
    public long getIdRol() {
        // TODO Auto-generated method stub
        return iDRol;
    }

    @Override
    public long getCedula() {
        return cedula;
    }

    @Override
    public long getIdSucursal() {
        // TODO Auto-generated method stub
        return iDSucursal;
    }

    @Override
    public long getNitSupermercado() {
        // TODO Auto-generated method stub
        return nitSupermercado;
    }

    @Override
    public String getNombre() {
        // TODO Auto-generated method stub
        return nombre;
    }

    @Override
    public String getCorreo() {
        // TODO Auto-generated method stub
        return correo;
    }

    @Override
    public String getPalabraClave() {
        // TODO Auto-generated method stub
        return palabraClave;
    }

    @Override
    public String toString() {
        return "Usuario [iDRol=" + iDRol + ", iDSucursal=" + iDSucursal + ", nitSupermercado=" + nitSupermercado
                + ", nombre=" + nombre + ", correo=" + correo + ", palabraClave=" + palabraClave + "]";
    }

    
    
    
    

}
 