package br.com.retaguardaWeb.sessionbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.CategoriaProduto;
import br.com.retaguardaWeb.entidades.Produto;

@Stateless
public class CategoriaService {

	
	@PersistenceContext
	private EntityManager manager;

	public void adiciona(CategoriaProduto categoria) {
		this.manager.persist(categoria);
	}

	public List<CategoriaProduto> getCategorias() {
		List<CategoriaProduto> listaprodutos = new ArrayList<CategoriaProduto>();
		TypedQuery<CategoriaProduto> query = this.manager.createQuery("select x from CategoriaProduto x", CategoriaProduto.class);
		try {
			listaprodutos = query.getResultList();
			/*for (CategoriaProduto cat : listaprodutos) {
				System.out.println(cat.getDescricao());
				for(Produto prod : cat.getListaProduto()){
					System.out.println(prod.getDescricao());
				}
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaprodutos;
	}

	public List<CategoriaProduto> getCategoriasSite() {
		List<CategoriaProduto> listaprodutos = new ArrayList<CategoriaProduto>();
		TypedQuery<CategoriaProduto> query = this.manager.createQuery("select x from CategoriaProduto x where x.publicaWeb=1 order by x.ordem", CategoriaProduto.class);
		try {
			listaprodutos = query.getResultList();
			for (CategoriaProduto cat : listaprodutos) {
				System.out.println(cat.getDescricao());
				for(Produto prod : cat.getListaProduto()){
					System.out.println(prod.getDescricao());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaprodutos;
	}

	public CategoriaProduto pesquisa(CategoriaProduto categoria) {
		TypedQuery<CategoriaProduto> query = this.manager.createQuery("select x from CategoriaProduto x where idProduto = :id", CategoriaProduto.class);
		query.setParameter("id", categoria.getId());
		return query.getSingleResult();
	}
}
