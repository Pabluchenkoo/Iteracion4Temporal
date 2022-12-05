package uniandes.isis2304.SuperAndes.negocio;

import java.math.BigDecimal;

public interface VOPresentacion {
	public long getIdPresentacion();
	public BigDecimal getPrecioPorUnidad();
	public String getUnidadDeMedida();
	public String getEspecificacion();
	public String toString();
}
