package br.com.teste.livraria.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.teste.livraria.model.dtos.EditoraDto;
import br.com.teste.livraria.model.entities.Editora;
import br.com.teste.livraria.model.services.EditoraService;

@RestController
@RequestMapping("/editoras")
public class EditoraController {

	@Autowired
	private EditoraService service;
	
	@PostMapping
	public ResponseEntity<Editora> insert(@RequestBody EditoraDto obj) {
		Editora editora = service.insert(obj);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(editora.getId()).toUri();
		
		return ResponseEntity.created(uri).body(editora);
	}
}
