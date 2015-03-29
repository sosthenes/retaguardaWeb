package br.com.retaguardaWeb.vo;

import java.util.ArrayList;
import java.util.List;

import br.com.retaguardaWeb.entidades.Advertencia;
import br.com.retaguardaWeb.entidades.AdvertenciaMotivo;

public class AdvertenciaVO {

	Advertencia adv;
	List<AdvertenciaMotivo> lintaMotivo = new ArrayList<AdvertenciaMotivo>();
	public Advertencia getAdv() {
		return adv;
	}
	public void setAdv(Advertencia adv) {
		this.adv = adv;
	}
	public List<AdvertenciaMotivo> getLintaMotivo() {
		return lintaMotivo;
	}
	public void setLintaMotivo(List<AdvertenciaMotivo> lintaMotivo) {
		this.lintaMotivo = lintaMotivo;
	}
	
	
	
	
}
