package br.com.teste.livraria.model.dtos;

import java.util.Set;

public record LivroDto(String titulo, String resumo, Long idEditora, Set<Long> autoresIds) {

}
