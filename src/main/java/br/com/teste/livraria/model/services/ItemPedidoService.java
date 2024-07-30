package br.com.teste.livraria.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.teste.livraria.model.dtos.ItemPedidoDto;
import br.com.teste.livraria.model.entities.ItemPedido;
import br.com.teste.livraria.model.entities.Livro;
import br.com.teste.livraria.model.entities.Pedido;
import br.com.teste.livraria.model.exceptions.ResourceNotFoundException;
import br.com.teste.livraria.model.repositories.ItemPedidoRepository;
import br.com.teste.livraria.model.repositories.LivroRepository;
import br.com.teste.livraria.model.repositories.PedidoRepository;

@Service
public class ItemPedidoService {

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private LivroRepository livroRepository;
	
	public ItemPedido insert(ItemPedidoDto itemDto) {
		ItemPedido item = new ItemPedido();
		item.setLivro(livroRepository.findById(itemDto.livroId()).orElseThrow(() -> new ResourceNotFoundException(Livro.class, item)));
		item.setPedido(pedidoRepository.findById(itemDto.pedidoId()).orElseThrow(() -> new ResourceNotFoundException(Pedido.class, item)));
		item.setPreco(itemDto.preco());
		item.setQuantidade(itemDto.quantidade());
		
		return itemPedidoRepository.save(item);
	}
}
