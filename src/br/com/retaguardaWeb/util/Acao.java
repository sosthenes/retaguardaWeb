package br.com.retaguardaWeb.util;

public interface Acao<T> {
	void executar(T t);
}
