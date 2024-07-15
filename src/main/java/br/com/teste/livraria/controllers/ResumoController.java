package br.com.teste.livraria.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.teste.livraria.model.dtos.ResumoDto;
import br.com.teste.livraria.model.entities.Resumo;
import br.com.teste.livraria.model.services.ResumoService;

@RestController
@RequestMapping("/resumos")
public class ResumoController {
	
	@Autowired
	private ResumoService service;
	
	@PostMapping
	public ResponseEntity<Resumo> insert(@RequestBody ResumoDto obj) {
		Resumo resumo = service.insert(obj);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(resumo.getId()).toUri();	
		
		return ResponseEntity.created(uri).body(resumo);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Resumo> findById(@PathVariable Long id) {
		Resumo obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping
	public ResponseEntity<List<Resumo>> findAll() {
		List<Resumo> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Resumo> update(@PathVariable Long id, @RequestBody ResumoDto obj) {
		Resumo resumo = service.update(id, obj);
		return ResponseEntity.ok().body(resumo);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
