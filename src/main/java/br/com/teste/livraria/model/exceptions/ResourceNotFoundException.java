package br.com.teste.livraria.model.exceptions;

public class ResourceNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(Class<?> classe, Object id) {
		super("Resource not found: " + classe.getSimpleName() + " WITH ID: " + id);
	}

}
