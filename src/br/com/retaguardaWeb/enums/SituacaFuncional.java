package br.com.retaguardaWeb.enums;

public enum SituacaFuncional {

	Contratado(1); 
	
	private final int valor; 
	
	SituacaFuncional(int valorOpcao){ 
		valor = valorOpcao; 
	} 
	public int getValor(){ 
		return valor; 
	} 

}
