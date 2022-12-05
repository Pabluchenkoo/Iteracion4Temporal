package uniandes.isis2304.SuperAndes.negocio;

public class Proveedor implements VOProveedor{
    
    private long idSucursal;
    
    private long iDProveedor;
    
    private long nit;
    
    private String nombre;
    
    private int telefono;
    
    private String direccion;
    
    

    public Proveedor(long idSucursal, long iDProveedor, long nit, String nombre, int telefono, String direccion) {
        super();
        this.idSucursal = idSucursal;
        this.iDProveedor = iDProveedor;
        this.nit = nit;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    @Override
    public long getIdSucursal() {
        // TODO Auto-generated method stub
        return idSucursal;
    }

    @Override
    public long getIdProveedor() {
        // TODO Auto-generated method stub
        return iDProveedor;
    }

    @Override
    public long getNit() {
        // TODO Auto-generated method stub
        return nit;
    }

    @Override
    public String getNombre() {
        // TODO Auto-generated method stub
        return nombre;
    }

    @Override
    public int getTelefono() {
        // TODO Auto-generated method stub
        return telefono;
    }

    @Override
    public String getDireccion() {
        // TODO Auto-generated method stub
        return direccion;
    }

    @Override
    public String toString() {
        return "Proveedor [idSucursal=" + idSucursal + ", iDProveedor=" + iDProveedor + ", nit=" + nit + ", nombre="
                + nombre + ", telefono=" + telefono + ", direccion=" + direccion + "]";
    }
    
    

}
