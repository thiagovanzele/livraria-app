package br.com.teste.livraria.model.dtos;

import br.com.teste.livraria.model.entities.Livro;

public record ItemPedidoDto(Livro livro, Integer quantidade, Double preco) {

}
