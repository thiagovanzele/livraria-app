package br.com.teste.livraria.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.teste.livraria.model.dtos.ItemPedidoDto;
import br.com.teste.livraria.model.entities.ItemPedido;
import br.com.teste.livraria.model.services.ItemPedidoService;

@RestController
@RequestMapping("/itens-pedido")
public class ItemPedidoController {

	@Autowired
	private ItemPedidoService service;

	@PostMapping
	public ResponseEntity<ItemPedido> insert(@RequestBody ItemPedidoDto itemDto) {
		ItemPedido pedido = service.insert(itemDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pedido.getId()).toUri();

		return ResponseEntity.created(uri).body(pedido);
	}
	
	@GetMapping
	public ResponseEntity<List<ItemPedido>> findAll() {
		List<ItemPedido> list = service.findall();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{pedidoId}/livro/{livroId}")
	public ResponseEntity<ItemPedido> findById(@PathVariable Long pedidoId, @PathVariable Long livroId) {
		ItemPedido item = service.findById(pedidoId, livroId);
		return ResponseEntity.ok(item);
	}

	@DeleteMapping("/{pedidoId}/livro/{livroId}")
	public ResponseEntity<Void> delete(@PathVariable Long pedidoId, @PathVariable Long livroId) {
		service.delete(pedidoId, livroId);
		return ResponseEntity.noContent().build();
	}
}
