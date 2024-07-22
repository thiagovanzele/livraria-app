package br.com.teste.livraria.model.exceptions;

public class IncorrectCepException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public IncorrectCepException(String msg) {
		super(msg);
	}

}
