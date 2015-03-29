package br.com.retaguardaWeb.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class EntidadeBase implements Serializable, EntidadeComId {
	
	private static final long serialVersionUID = 4879102937328754160L;


	
	
}
