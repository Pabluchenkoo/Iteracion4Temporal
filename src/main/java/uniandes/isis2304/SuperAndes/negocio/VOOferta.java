package uniandes.isis2304.SuperAndes.negocio;

import java.sql.Timestamp;

public interface VOOferta {
    
    public String getCodigoDeBarras();
    public Long getIdOferta();
    public String getNombre();
    public String getRestriccion();
    public Long getUnidadesDisponibles();

    public boolean getActiva();

    public Timestamp getFechaCaducidad();

}
