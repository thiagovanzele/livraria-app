package br.com.teste.livraria.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@PostMapping
	public ResponseEntity<Autor> insert(@RequestBody AutorDto autorDto) {
		Autor autor = service.insert(autorDto);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(autor.getId()).toUri();
		
		return ResponseEntity.created(uri).body(autor);
	}
}
