package uniandes.isis2304.SuperAndes.negocio;

import java.math.BigDecimal;

public interface VOAlmacenamiento {

	public Long getIdCategoria();
	public Long getIdSucursal();
	public Long getId();
	public String getTipo();
	public BigDecimal getVolumen();
	public BigDecimal getPeso();
	public BigDecimal getVolumenOcupado();
	public BigDecimal getPesoOcupado();
	public String toString();
}
