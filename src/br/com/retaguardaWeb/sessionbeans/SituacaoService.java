package br.com.retaguardaWeb.sessionbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.Loja;
import br.com.retaguardaWeb.entidades.SituacaoFuncional;

@Stateless
public class SituacaoService {

	
	@PersistenceContext
	private EntityManager manager;

	public void adiciona(SituacaoFuncional situacao) {
		this.manager.merge(situacao);
	}

	public void remover(SituacaoFuncional situacao) {
		situacao = this.manager.merge(situacao);
		this.manager.remove(situacao);
	}

	
	public List<SituacaoFuncional> getSituacaos() {
		List<SituacaoFuncional> listasituacaos = new ArrayList<SituacaoFuncional>();
		TypedQuery<SituacaoFuncional> query = this.manager.createQuery("select x from Situacao x", SituacaoFuncional.class);
		try {
			listasituacaos = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listasituacaos;
	}


	public SituacaoFuncional pesquisaPorId(SituacaoFuncional situacao) {
		TypedQuery<SituacaoFuncional> query = this.manager.createQuery("select x from SituacaoFuncional x where idSituacao = :id", SituacaoFuncional.class);
		query.setParameter("id", situacao.getId());
		return query.getSingleResult();
	}

	public List<SituacaoFuncional> listaSituacaoPorLoja(Loja loja) {
		List<SituacaoFuncional> listasituacaos = new ArrayList<SituacaoFuncional>();
		TypedQuery<SituacaoFuncional> query = this.manager.createQuery("select x from SituacaoFuncional x where x.idLoja=:loja", SituacaoFuncional.class);
		try {
			query.setParameter("loja", loja);
			listasituacaos = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listasituacaos;
	}
}
