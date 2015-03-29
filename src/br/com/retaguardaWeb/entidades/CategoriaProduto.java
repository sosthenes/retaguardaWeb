package br.com.retaguardaWeb.entidades;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="categoriaproduto")
public class CategoriaProduto {
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idCategoriaProduto")
	private Long id;
	
	private String descricao;
	
	private boolean meia;
	
	private boolean publicaWeb;
	
	private Integer ordem;
	
	@OneToMany(cascade = CascadeType.ALL )
	@JoinColumn(name="idCategoriaProduto")
	private List<Produto> listaProduto = new ArrayList<Produto>();
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isMeia() {
		return meia;
	}

	public void setMeia(boolean meia) {
		this.meia = meia;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public List<Produto> getListaProduto() {
		return listaProduto;
	}

	public void setListaProduto(List<Produto> listaProduto) {
		this.listaProduto = listaProduto;
	}

	public boolean isPublicaWeb() {
		return publicaWeb;
	}

	public void setPublicaWeb(boolean publicaWeb) {
		this.publicaWeb = publicaWeb;
	}

	
}