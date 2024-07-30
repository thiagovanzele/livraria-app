package br.com.teste.livraria.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.teste.livraria.model.dtos.ItemPedidoDto;
import br.com.teste.livraria.model.entities.ItemPedido;
import br.com.teste.livraria.model.services.ItemPedidoService;

@RestController
@RequestMapping("/itens")
public class ItemPedidoController {

	@Autowired
	private ItemPedidoService service;
	
	@PostMapping
	public ResponseEntity<ItemPedido> insert(@RequestBody ItemPedidoDto itemDto) {
		ItemPedido pedido = service.insert(itemDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(pedido.getId()).toUri();
		
		return ResponseEntity.created(uri).body(pedido);
	}
}
