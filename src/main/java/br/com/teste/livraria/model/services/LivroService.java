package br.com.teste.livraria.model.services;

import org.springframework.stereotype.Service;

import br.com.teste.livraria.model.dtos.LivroDto;
import br.com.teste.livraria.model.entities.Livro;
import br.com.teste.livraria.model.entities.Resumo;
import br.com.teste.livraria.model.repositories.LivroRepositorie;
import br.com.teste.livraria.model.repositories.ResumoRepositorie;

@Service
public class LivroService {

	private LivroRepositorie livroRepositorie;
	private ResumoRepositorie resumoRepository;
	
	public Livro insert(LivroDto livroDto) {
		Livro livro = new Livro();
		livro.setTitle(livroDto.titulo());
		
		Resumo resumo = new Resumo();
		resumo.setComentario(livroDto.comentario());
		resumo.setLivro(livro);
		resumoRepository.save(resumo);
		
		return livroRepositorie.save(livro);
		
	}
}
