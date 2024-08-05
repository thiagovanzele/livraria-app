package br.com.teste.livraria.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.teste.livraria.model.dtos.LivroDto;
import br.com.teste.livraria.model.entities.Autor;
import br.com.teste.livraria.model.entities.Editora;
import br.com.teste.livraria.model.entities.Livro;
import br.com.teste.livraria.model.entities.Resumo;
import br.com.teste.livraria.model.exceptions.ResourceNotFoundException;
import br.com.teste.livraria.model.exceptions.ValidationException;
import br.com.teste.livraria.model.repositories.AutorRepository;
import br.com.teste.livraria.model.repositories.EditoraRepository;
import br.com.teste.livraria.model.repositories.LivroRepository;
import jakarta.transaction.Transactional;

@Service
public class LivroService {

	@Autowired
	private LivroRepository livroRepository;

	@Autowired
	private EditoraRepository editoraRepository;

	@Autowired
	private AutorRepository autorRepository;

	@Transactional
	public Livro insert(LivroDto livroDto) {
		
		try {
		validaLivro(livroDto);
		
		Livro livro = new Livro();
		
		livro.setTitutlo(livroDto.titulo());
		livro.setPreco(livroDto.preco());

		Editora editora = editoraRepository.findById(livroDto.idEditora()).orElseThrow(() -> new ResourceNotFoundException(Editora.class, livroDto.idEditora()));
		livro.setEditora(editora);

		Resumo resumo = new Resumo();
		resumo.setComentario(livroDto.resumo());
		resumo.setLivro(livro);
		livro.setResumo(resumo);
		editora.getLivros().add(livro);
		
		livroDto.autoresIds().stream().forEach(idAutor -> {
			Autor autor = autorRepository.findById(idAutor).orElseThrow(() -> new ResourceNotFoundException(Autor.class, idAutor));
			autorRepository.save(autor);
			autor.getLivros().add(livro);
			livro.getAutores().add(autor);
		});
		
		return livroRepository.save(livro);
		} catch (DataIntegrityViolationException e) {
			throw new ValidationException(e.getMessage());
		} catch (NullPointerException e) {
			throw new ValidationException("Valores nullos não são aceitos");
		}

	}

	public Livro findById(Long id) {
		return livroRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Livro.class, id));
	}

	public List<Livro> findAll() {
		List<Livro> list = livroRepository.findAll();
		return list;
	}
	
	public List<Livro> findByName(String nomeDoLivro) {
		List<Livro> list = livroRepository.findByTituloContainingIgnoreCase(nomeDoLivro);
		if (list == null || list.isEmpty()) {
			throw new ResourceNotFoundException(Livro.class, nomeDoLivro);
		}
		return list;
	}

	@Transactional
	public Livro update(Long id, LivroDto livroDto) {
		Livro livro = findById(id);
		updateData(livro, livroDto);
		return livroRepository.save(livro);
	}

	private void updateData(Livro livro, LivroDto livroDto) {
		livro.setTitutlo(livroDto.titulo());
		livro.getResumo().setComentario(livroDto.resumo());
		livro.setPreco(livroDto.preco());

		Resumo resumo = livro.getResumo();
		resumo.setLivro(livro);
	}

	@Transactional
	public void delete(Long id) {
		if (!livroRepository.existsById(id)) {
			throw new ResourceNotFoundException(Livro.class, id);
		}
		livroRepository.deleteById(id);
	}
	
	private void validaLivro(LivroDto livroDto) {
		
		if (livroDto.titulo().isEmpty() || livroDto.titulo() == null) {
			throw new ValidationException("O livro deve conter um título");
		}
		
		if (livroDto.resumo().isEmpty() || livroDto.resumo().isBlank()) {
			throw new ValidationException("O livro deve conter um resumo");
		}
		
		if (livroDto.autoresIds().isEmpty() || livroDto.autoresIds() == null) {
			throw new ValidationException("O livro deve conter pelo menos um autor");
		}
		
		if (livroDto.idEditora() == null) {
			throw new ValidationException("O livro deve conter pelo menos uma editora");
		}
		
		if (livroDto.preco() == null || livroDto.preco() < 0) {
			throw new ValidationException("Valor inválido para o livro");
		}
	}
}
