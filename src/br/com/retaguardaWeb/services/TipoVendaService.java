package br.com.retaguardaWeb.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.Loja;
import br.com.retaguardaWeb.entidades.TipoVenda;
import br.com.retaguardaWeb.entidades.TipoVenda;

@Stateless
public class TipoVendaService {

	
	@PersistenceContext
	private EntityManager manager;

	public void adiciona(TipoVenda tipoVenda) {
		this.manager.persist(tipoVenda);
	}

	/*public List<TipoVenda> buscaTipoVendaPorTelefone(TipoVenda tipoVenda) {
		List<TipoVenda> listaTel = new ArrayList<TipoVenda>();
		TypedQuery<TipoVenda> query = this.manager.createQuery("select x from TipoVenda x where  x.numero LIKE '%"+tipoVenda.getNumero()+"%' ", TipoVenda.class);
		//query.setParameter("tipoVenda",  );
		try {
			listaTel = query.getResultList();
		} catch (Exception e) {
			return null;
		}
		return listaTel;
	}
	public List<TipoVenda> buscaTipoVendaPorTelefone(String tipoVenda) {
		TipoVenda tipoVendaTemp = new TipoVenda();
		tipoVendaTemp.setDescricao(tipoVenda);
		return buscaTipoVendaPorTelefone(tipoVendaTemp);
	}*/

	
	public List<TipoVenda> getITipoVendas(Loja loja) {
		TypedQuery<TipoVenda> query = this.manager.createQuery("select x from TipoVenda x "
				+ " where x.idLoja=:loja"
				+ " order by descricao", TipoVenda.class);
		query.setParameter("loja", loja);
		return query.getResultList();
	}

	public TipoVenda pesquisa(TipoVenda tipoVenda) {
		TypedQuery<TipoVenda> query = this.manager.createQuery("select x from TipoVenda x where idTipoVenda = :id", TipoVenda.class);
		query.setParameter("id", tipoVenda.getId());
		return query.getSingleResult();
	}
	
	public TipoVenda obterPorId(TipoVenda tipoVenda) {
		TypedQuery<TipoVenda> query = this.manager.createQuery("select x from TipoVenda x where id = :id", TipoVenda.class);
		query.setParameter("id", tipoVenda.getId());
		return query.getSingleResult();
	}
	
	public void atualizar(TipoVenda tipoVenda) {
		manager.merge(tipoVenda);
	}
}
