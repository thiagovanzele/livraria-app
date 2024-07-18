package br.com.teste.livraria.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.teste.livraria.model.dtos.AutorDto;
import br.com.teste.livraria.model.entities.Autor;
import br.com.teste.livraria.model.services.AutorService;

@RestController
@RequestMapping("/autores")
public class AutorController {

	@Autowired
	private AutorService service;
	
	@GetMapping("/{id}")
	public ResponseEntity<Autor> findById(@PathVariable Long id) {
		Autor autor = service.findById(id);
		return ResponseEntity.ok(autor);
	}
	
	@GetMapping
	public ResponseEntity<List<Autor>> findAll() {
		List<Autor> list = service.findAll();
		return ResponseEntity.ok(list);
	}
		
	@PostMapping
	public ResponseEntity<Autor> insert(@RequestBody AutorDto autorDto) {
		Autor autor = service.insert(autorDto);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(autor.getId()).toUri();
		
		return ResponseEntity.created(uri).body(autor);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Autor> update(@PathVariable Long id, @RequestBody AutorDto autorDto) {
		Autor autor = service.update(id, autorDto);
		return ResponseEntity.ok(autor);
	}
}
