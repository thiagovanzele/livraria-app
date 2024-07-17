package br.com.teste.livraria.model.dtos;

import java.util.Set;

public record AutorDto(String nome, Set<Long> livrosIds) {

}
