package br.com.retaguardaWeb.vo;

import java.util.Date;

import br.com.retaguardaWeb.entidades.Caixa;
import br.com.retaguardaWeb.entidades.Funcionario;
import br.com.retaguardaWeb.entidades.Usuario;
import br.com.retaguardaWeb.entidades.Valefuncionario;

public class ValefuncionarioVO {

	private Valefuncionario valeFuncionario;
	private String valorPorExtenso;
	private String dataDebito;

	
	
	public ValefuncionarioVO(Funcionario funcionario, String valor) {
		getValeFuncionario().setFuncionario(funcionario);
		getValeFuncionario().setValor(valor);
	}



	public ValefuncionarioVO() {
		super();
		// TODO Auto-generated constructor stub
	}



	public String getDataDebito() {
		return dataDebito;
	}

	public void setDataDebito(String dataDebito) {
		this.dataDebito = dataDebito;
	}

	public String getValorPorExtenso() {
		return valorPorExtenso;
	}

	public void setValorPorExtenso(String valorPorExtenso) {
		this.valorPorExtenso = valorPorExtenso;
	}

	public Valefuncionario getValeFuncionario() {
		if(valeFuncionario==null)
			valeFuncionario = new Valefuncionario();
		return valeFuncionario;
	}

	public void setValeFuncionario(Valefuncionario valeFuncionario) {
		this.valeFuncionario = valeFuncionario;
	}
	
	
	
	
	
	
	
}
