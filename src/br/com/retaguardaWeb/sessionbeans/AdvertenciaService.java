package br.com.retaguardaWeb.sessionbeans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import net.sf.jasperreports.engine.JRException;
import br.com.retaguardaWeb.entidades.Advertencia;
import br.com.retaguardaWeb.entidades.AdvertenciaMotivo;
import br.com.retaguardaWeb.entidades.Motivo;
import br.com.retaguardaWeb.vo.AdvertenciaVO;

@Stateless
@TransactionManagement
public class AdvertenciaService {

	@EJB
	private AdvertenciaService advertenciaService;
	@EJB
	private RelatorioService relatorioService;
	
	@PersistenceContext
	private EntityManager manager;

	public Advertencia adiciona(Advertencia advertencia, List<String> idMotivo) {
		Advertencia adver= new Advertencia();
		adver= this.manager.merge(advertencia);
		salvaAdvertenciaMotivo(idMotivo, adver);
		return adver;
	}
	
	public void remover(Advertencia advertencia) {
		List<AdvertenciaMotivo> lista = new ArrayList<AdvertenciaMotivo>();
		lista = advertenciaService.listaMotivos(advertencia);
		Advertencia adv = new Advertencia();
		adv = manager.merge(advertencia);
		for(AdvertenciaMotivo l : lista){
			this.manager.remove(l);
		}
		this.manager.remove(adv);
	}

	public void alterar(Advertencia advertencia) {
		this.manager.merge(advertencia);
	}

	
	
	public List<Advertencia> getAdvertencias() {
		TypedQuery<Advertencia> query = this.manager.createQuery(
				"select x from Advertencia x", Advertencia.class);

		return query.getResultList();
	}

	public Advertencia advertenciaPorId(Advertencia idAdvertencia) {
		return this.manager.find(Advertencia.class, idAdvertencia);
	}

	public void salvaAdvertenciaMotivo(AdvertenciaMotivo adv) {
		this.manager.merge(adv);
	}

	public List<AdvertenciaMotivo> listaMotivos(Advertencia adv) {
		List<AdvertenciaMotivo> motivos = new ArrayList<AdvertenciaMotivo>();
		TypedQuery<AdvertenciaMotivo> query = this.manager.createQuery("select x from AdvertenciaMotivo x where x.advertencia = :advertencia", AdvertenciaMotivo.class);
		query.setParameter("advertencia", adv);
		try {
			motivos = (List<AdvertenciaMotivo>) query.getResultList();
			return motivos;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void gerarAdv(Advertencia adv) {
		String jasper = "advertencia.jasper";
		String titulo = "CARTA DE ADVERTÊNCIA DISCIPLINAR";
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("adv", "");
		try {
			List<AdvertenciaVO> advVO = new ArrayList<AdvertenciaVO>();
			AdvertenciaVO advertenciaVO = new AdvertenciaVO();
			List<AdvertenciaMotivo> lista = new ArrayList<AdvertenciaMotivo>();
			lista = advertenciaService.listaMotivos(adv);
			advertenciaVO.setAdv(adv);
			advertenciaVO.setLintaMotivo(lista);
			advVO.add(advertenciaVO);
			relatorioService.pdf(jasper, titulo, "", "", advVO, hm);
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	protected void salvaAdvertenciaMotivo(List<String> idMotivo, Advertencia adver){
		for(String mot : idMotivo){
			AdvertenciaMotivo adv = new AdvertenciaMotivo();
			Motivo moti = new Motivo();
			moti.setId(Long.valueOf(mot));
			adv.setMotivo(moti);
			adv.setAdvertencia(adver);
			advertenciaService.salvaAdvertenciaMotivo(adv);
		}
	}


	
}
