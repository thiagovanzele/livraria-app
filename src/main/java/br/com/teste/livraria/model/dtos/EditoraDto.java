package br.com.teste.livraria.model.dtos;

import java.util.Set;

public record EditoraDto (String nome, Set<Long> livrosIds) {
	
}
