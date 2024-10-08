package br.com.teste.livraria.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.teste.livraria.model.dtos.ItemPedidoDto;
import br.com.teste.livraria.model.entities.ItemPedido;
import br.com.teste.livraria.model.entities.Livro;
import br.com.teste.livraria.model.entities.Pedido;
import br.com.teste.livraria.model.entities.pk.ItemPedidoPK;
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

	@Autowired
	private LivroService livroService;


	public ItemPedido insert(ItemPedidoDto itemDto) {
		ItemPedido item = new ItemPedido();
		Livro livro = livroService.findById(itemDto.livroId());
		item.setLivro(livro);
		
		item.setQuantidade(itemDto.quantidade());
		
		item.setPreco(livro.getPreco() * itemDto.quantidade());

		return itemPedidoRepository.save(item);
	}

	public List<ItemPedido> findall() {
		return itemPedidoRepository.findAll();
	}

	public ItemPedido findById(Long idPedido, Long idLivro) {
		ItemPedidoPK id = new ItemPedidoPK();
		id.setLivro(livroRepository.findById(idLivro)
				.orElseThrow(() -> new ResourceNotFoundException(Livro.class, idLivro)));
		id.setPedido(pedidoRepository.findById(idPedido)
				.orElseThrow(() -> new ResourceNotFoundException(Pedido.class, idPedido)));

		return itemPedidoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ItemPedido.class, id));
	}

	public void delete(Long idPedido, Long idLivro) {
		ItemPedidoPK id = new ItemPedidoPK();
		id.setLivro(livroRepository.findById(idLivro)
				.orElseThrow(() -> new ResourceNotFoundException(Livro.class, idLivro)));
		id.setPedido(pedidoRepository.findById(idPedido)
				.orElseThrow(() -> new ResourceNotFoundException(Pedido.class, idPedido)));

		itemPedidoRepository.deleteById(id);
	}

	public ItemPedido update(Long idPedido, Long idLivro, ItemPedidoDto itemPedidoDto) {
		ItemPedido item = findById(idPedido, idLivro);
		updateData(item, itemPedidoDto);
		return itemPedidoRepository.save(item);
	}

	private void updateData(ItemPedido item, ItemPedidoDto itemPedidoDto) {
		item.setLivro(livroService.findById(itemPedidoDto.livroId()));
		item.setQuantidade(itemPedidoDto.quantidade());

	}

}
