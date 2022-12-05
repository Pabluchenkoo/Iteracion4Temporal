package uniandes.isis2304.SuperAndes.negocio;

import java.math.BigDecimal;

public class Almacenamiento implements VOAlmacenamiento{

	private Long idCategoria;
	private Long idSucursal;
	private Long id;
	private String tipo;
	private BigDecimal volumen;
	private BigDecimal peso;
	private BigDecimal volumenOcupado;
	private BigDecimal pesoOcupado;
	
	public Almacenamiento(Long idCategoria, Long idSucursal, Long id, String tipo, BigDecimal volumen,
						  BigDecimal peso, BigDecimal volumenOcupado, BigDecimal pesoOcupado) {
		this.idCategoria = idCategoria;
		this.idSucursal = idSucursal;
		this.id = id;
		this.tipo = tipo;
		this.volumen = volumen;
		this.peso = peso;
		this.volumenOcupado = volumenOcupado;
		this.pesoOcupado = pesoOcupado;
	}


	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public Long getIdSucursal() {
		return idSucursal;
	}

	public void setIdSucursal(long idSucursal) {
		this.idSucursal = idSucursal;
	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getVolumen() {
		return volumen;
	}

	public void setVolumen(BigDecimal volumen) {
		this.volumen = volumen;
	}

	public BigDecimal getPeso() {
		return peso;
	}

	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	public BigDecimal getVolumenOcupado() {
		return volumenOcupado;
	}

	public void setVolumenOcupado(BigDecimal volumenOcupado) {
		this.volumenOcupado = volumenOcupado;
	}

	public BigDecimal getPesoOcupado() {
		return pesoOcupado;
	}

	public void setPesoOcupado(BigDecimal pesoOcupado) {
		this.pesoOcupado = pesoOcupado;
	}

	@Override
	public String toString() {
		return "Almacenamiento [idCategoria=" + idCategoria + ", idSucursal=" + idSucursal + ", idAlamcenamiento="
				+ id + ", tipo=" + tipo + ", volumen=" + volumen + ", peso=" + peso + ", volumenOcupado="
				+ volumenOcupado + ", pesoOcupado=" + pesoOcupado + "]";
	}
	
	
}
