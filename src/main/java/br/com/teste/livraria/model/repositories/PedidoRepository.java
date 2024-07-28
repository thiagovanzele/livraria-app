package br.com.teste.livraria.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.teste.livraria.model.entities.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
