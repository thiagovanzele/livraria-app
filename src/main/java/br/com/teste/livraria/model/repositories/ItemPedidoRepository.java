package br.com.teste.livraria.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.teste.livraria.model.entities.ItemPedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

}
