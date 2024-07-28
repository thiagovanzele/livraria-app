package br.com.teste.livraria.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.teste.livraria.model.entities.Pedido;
import br.com.teste.livraria.model.exceptions.ResourceNotFoundException;
import br.com.teste.livraria.model.repositories.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	public Pedido findById(Long id) {
		return pedidoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Pedido.class, id));
	}

	public List<Pedido> findAll() {
		List<Pedido> pedidos = pedidoRepository.findAll();
		return pedidos;
	}

}
