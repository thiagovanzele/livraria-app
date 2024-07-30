package br.com.teste.livraria.model.dtos;

import java.time.LocalDateTime;
import java.util.Set;

public record PedidoDto(LocalDateTime data, Set<ItemPedidoDto> itens, Long clienteId) {

}
