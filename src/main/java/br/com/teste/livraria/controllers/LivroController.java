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

import br.com.teste.livraria.model.dtos.LivroDto;
import br.com.teste.livraria.model.entities.Livro;
import br.com.teste.livraria.model.services.LivroService;

@RestController
@RequestMapping("/livros")
public class LivroController {

	@Autowired
	private LivroService service;
	
	@GetMapping("/{id}")
	public ResponseEntity<Livro> findById(@PathVariable Long id) {
		Livro livro = service.findById(id);
		return ResponseEntity.ok(livro);
	}
	
	@GetMapping
	public ResponseEntity<List<Livro>> findAll() {
		List<Livro> list = service.findAll();
		return ResponseEntity.ok(list);
	}
	
	@PostMapping
	public ResponseEntity<Livro> insert(@RequestBody LivroDto livroDto) {
		Livro livro = service.insert(livroDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(livro.getId()).toUri();
		
		return ResponseEntity.created(uri).body(livro);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Livro> update(@PathVariable Long id, @RequestBody LivroDto livroDto) {
		Livro livro = service.update(id, livroDto);
		return ResponseEntity.ok(livro);
	}
}
