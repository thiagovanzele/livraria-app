package br.com.teste.livraria.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.teste.livraria.model.dtos.LivroDto;
import br.com.teste.livraria.model.entities.Livro;
import br.com.teste.livraria.model.entities.Resumo;
import br.com.teste.livraria.model.exceptions.ResourceNotFoundException;
import br.com.teste.livraria.model.repositories.LivroRepositorie;
import jakarta.transaction.Transactional;

@Service
public class LivroService {

	@Autowired
	private LivroRepositorie livroRepositorie;

	@Transactional
	public Livro insert(LivroDto livroDto) {
		Livro livro = new Livro();
		livro.setTitle(livroDto.titulo());
		
		Resumo resumo = new Resumo();
		resumo.setComentario(livroDto.resumo());
		resumo.setLivro(livro);
		livro.setResumo(resumo);
						
		return livroRepositorie.save(livro);
	}
	
	public Livro findById(Long id) {
		return livroRepositorie.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public List<Livro> findAll() {
		List<Livro> list = livroRepositorie.findAll();
		return list;
	}
	
	@Transactional
	public Livro update(Long id, LivroDto livroDto) {
		Livro livro = findById(id);
		updateData(livro, livroDto);
		return livroRepositorie.save(livro);
	}

	private void updateData(Livro livro, LivroDto livroDto) {
		livro.setTitle(livroDto.titulo());
		livro.getResumo().setComentario(livroDto.resumo());
		
		Resumo resumo = livro.getResumo();
		resumo.setLivro(livro);
	}
	
	@Transactional
	public void delete(Long id) {
		if(!livroRepositorie.existsById(id)) {
			throw new ResourceNotFoundException(id);
		}
		livroRepositorie.deleteById(id);
	}
}
