package br.com.retaguardaWeb.sessionbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.Cargo;
import br.com.retaguardaWeb.entidades.Loja;
import br.com.retaguardaWeb.entidades.Funcionario;
import br.com.retaguardaWeb.entidades.Setor;

@Stateless
public class FuncionarioService {

	
	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Funcionario funcionario) {
		this.manager.merge(funcionario);
	}

	public void remover(Funcionario funcionario) {
		funcionario = this.manager.merge(funcionario);
		this.manager.remove(funcionario);
	}

	
	public List<Funcionario> getFuncionarios(Loja loja) {
		List<Funcionario> listafuncionarios = new ArrayList<Funcionario>();
		TypedQuery<Funcionario> query = this.manager.createQuery("select x from Funcionario x where x.loja=:loja", Funcionario.class);
		query.setParameter("loja", loja);
		try {
			listafuncionarios = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listafuncionarios;
	}


	public Funcionario pesquisaPorId(Funcionario funcionario) {
		TypedQuery<Funcionario> query = this.manager.createQuery("select x from Funcionario x where idFuncionario = :id", Funcionario.class);
		query.setParameter("id", funcionario.getId());
		return query.getSingleResult();
	}

	public List<Funcionario> listaFuncionarioPorLoja(Loja loja, Setor setor, Cargo cargo) {
		List<Funcionario> listafuncionarios = new ArrayList<Funcionario>();
		try {
			String lcQuery = "select x from Funcionario x where x.loja=:loja";
			//String lcQuery = "select x from Funcionario x ";
			/*if(setor!=null && setor.getId()!=null){
				lcQuery+= " and x.setor=:idSetor";
			}
			if(cargo!=null && cargo.getId()!=null){
				lcQuery+= " and x.cargo=:idCargo";
			}*/
			
			TypedQuery<Funcionario> query = this.manager.createQuery(lcQuery, Funcionario.class);
			query.setParameter("loja", loja);
			/*
			if(setor!=null && setor.getId()!=null){
				query.setParameter("idSetor", setor);
			}
			if(cargo!=null && cargo.getId()!=null){
				query.setParameter("idCargo", cargo);
			}*/
			listafuncionarios = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listafuncionarios;
	}

	public List<Funcionario> listaFuncionarioPorSetor(Setor setor) {
		List<Funcionario> listafuncionarios = new ArrayList<Funcionario>();
		String lcQuery = "select x from Funcionario x where x.setor=:setor";
		try {
			TypedQuery<Funcionario> query = this.manager.createQuery(lcQuery, Funcionario.class);
			query.setParameter("setor", setor);
			listafuncionarios = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listafuncionarios;
	}
}
