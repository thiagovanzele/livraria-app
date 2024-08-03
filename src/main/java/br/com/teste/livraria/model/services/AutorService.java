package br.com.teste.livraria.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.teste.livraria.model.dtos.AutorDto;
import br.com.teste.livraria.model.entities.Autor;
import br.com.teste.livraria.model.entities.Livro;
import br.com.teste.livraria.model.exceptions.ResourceNotFoundException;
import br.com.teste.livraria.model.exceptions.ValidationException;
import br.com.teste.livraria.model.repositories.AutorRepository;
import br.com.teste.livraria.model.repositories.LivroRepository;
import jakarta.transaction.Transactional;

@Service
public class AutorService {

	@Autowired
	private AutorRepository autorRepository;

	@Autowired
	private LivroRepository livroRepository;

	public Autor findById(Long id) {
		return autorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Autor.class, id));
	}

	public List<Autor> findAll() {
		return autorRepository.findAll();
	}

	@Transactional
	public Autor insert(AutorDto autorDto) {
		if (!autorDto.nome().matches("[a-zA-Z\\s]+")) {
	        throw new ValidationException("O nome do autor deve conter apenas letras e espaços");
	    }
		Autor autor = new Autor(autorDto, livroRepository);

		for (Livro livro : autor.getLivros()) {
			if (!livro.getAutores().contains(autor)) {
				livro.getAutores().add(autor);
			}
		}

		return autorRepository.save(autor);
	}	

	@Transactional
	public void delete(Long id) {
		if (!autorRepository.existsById(id)) {
			throw new ResourceNotFoundException(Autor.class, id);
		}
		autorRepository.deleteById(id);
	}

	@Transactional
	public Autor update(Long id, AutorDto autorDto) {

		Autor autor = autorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Autor.class, id));

		updateData(autor, autorDto);

		return autorRepository.save(autor);

	}

	private void updateData(Autor autor, AutorDto autorDto) {
		autor.setNome(autorDto.nome());

		autorDto.livrosIds().stream().forEach(idLivro -> {
			Livro livro = livroRepository.findById(idLivro)
					.orElseThrow(() -> new ResourceNotFoundException(Livro.class, idLivro));
			if (!autor.getLivros().contains(livro)) {
				autor.getLivros().add(livro);
			}

			if (!livro.getAutores().contains(autor)) {
				livro.getAutores().add(autor);
			}
		});
	}
}
