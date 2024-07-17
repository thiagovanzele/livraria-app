package br.com.teste.livraria.model.services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.teste.livraria.model.dtos.AutorDto;
import br.com.teste.livraria.model.entities.Autor;
import br.com.teste.livraria.model.entities.Livro;
import br.com.teste.livraria.model.repositories.AutorRepository;
import br.com.teste.livraria.model.repositories.LivroRepository;
import jakarta.transaction.Transactional;

@Service
public class AutorService {

	@Autowired
	private AutorRepository autorRepository;
	
	@Autowired
	private LivroRepository livroRepository;
	
	@Transactional
	public Autor insert(AutorDto autorDto) {
		Autor autor = new Autor(autorDto, livroRepository);
			
		for (Livro livro : autor.getLivros()) {
			if (!livro.getAutores().contains(autor)) {
				livro.getAutores().add(autor);
			}
		}
		
		return autorRepository.save(autor);
	}
}
