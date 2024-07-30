package br.com.teste.livraria.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.teste.livraria.model.dtos.PedidoDto;
import br.com.teste.livraria.model.entities.Pedido;
import br.com.teste.livraria.model.services.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService service;
	
	@PostMapping
	public ResponseEntity<Pedido> insert(@RequestBody PedidoDto pedidoDto) {
		Pedido pedido = service.insert(pedidoDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("{/id}")
				.buildAndExpand(pedido.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(pedido);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Pedido> findById(@PathVariable Long id) {
		Pedido pedido = service.findById(id);
		return ResponseEntity.ok(pedido);
	}
	
	@GetMapping
	public ResponseEntity<List<Pedido>> findAll() {
		List<Pedido> list = service.findAll();
		return ResponseEntity.ok(list);
	}
}
