package br.com.retaguardaWeb.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="linkperfil")
public class LinkPerfil {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idLinkPerfil")
	private Long id;

	@ManyToOne
	@JoinColumn(name="idLinksMenu")
	private LinksMenu idLinksMenu = new LinksMenu();
	
	@ManyToOne
	@JoinColumn(name="idPerfilusuario")
	private PerfilUsuario idPerfilusuario = new PerfilUsuario();

	public LinksMenu getIdLinksMenu() {
		return idLinksMenu;
	}

	public void setIdLinksMenu(LinksMenu idLinksMenu) {
		this.idLinksMenu = idLinksMenu;
	}

	public PerfilUsuario getIdPerfilusuario() {
		return idPerfilusuario;
	}

	public void setIdPerfilusuario(PerfilUsuario idPerfilusuario) {
		this.idPerfilusuario = idPerfilusuario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

	
	
}
