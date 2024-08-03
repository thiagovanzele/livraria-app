package br.com.teste.livraria.model.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.teste.livraria.model.dtos.ItemPedidoDto;
import br.com.teste.livraria.model.dtos.PedidoDto;
import br.com.teste.livraria.model.entities.Cliente;
import br.com.teste.livraria.model.entities.ItemPedido;
import br.com.teste.livraria.model.entities.Livro;
import br.com.teste.livraria.model.entities.Pedido;
import br.com.teste.livraria.model.exceptions.ResourceNotFoundException;
import br.com.teste.livraria.model.repositories.ClienteRepository;
import br.com.teste.livraria.model.repositories.LivroRepository;
import br.com.teste.livraria.model.repositories.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private LivroRepository livroRepository;


	public Pedido insert(PedidoDto pedidoDto) {
		Pedido pedido = new Pedido();
		Cliente cliente = clienteRepository.findById(pedidoDto.clienteId())
				.orElseThrow(() -> new ResourceNotFoundException(Cliente.class, pedidoDto.clienteId()));
		pedido.setCliente(cliente);
		pedido.setData(pedidoDto.data());

		Set<ItemPedido> itens = pedidoDto.itens().stream().map(this::convertToEntity).collect(Collectors.toSet());

		for (ItemPedido item : itens) {
			item.setPedido(pedido);
		}
		
		pedido.setItens(itens);

		return pedidoRepository.save(pedido);
	}

	public Pedido findById(Long id) {
		return pedidoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Pedido.class, id));
	}
	    

	public List<Pedido> findAll() {
		List<Pedido> pedidos = pedidoRepository.findAll();
		return pedidos;
	}
	
	public void delete(Long id) {
		if (!pedidoRepository.existsById(id)) {
			throw new ResourceNotFoundException(Pedido.class, id);
		}
		
		pedidoRepository.deleteById(id);
	}

	private ItemPedido convertToEntity(ItemPedidoDto itemDto) {
		Livro livro = livroRepository.findById(itemDto.livroId())
				.orElseThrow(() -> new ResourceNotFoundException(Livro.class, itemDto.livroId()));

		return new ItemPedido(null, livro, itemDto.quantidade());
	}

}
