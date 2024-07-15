package br.com.teste.livraria.model.services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.teste.livraria.model.dtos.EditoraDto;
import br.com.teste.livraria.model.entities.Editora;
import br.com.teste.livraria.model.entities.Livro;
import br.com.teste.livraria.model.repositories.EditoraRepositorie;
import br.com.teste.livraria.model.repositories.LivroRepositorie;
import jakarta.transaction.Transactional;

@Service
public class EditoraService {

	@Autowired
	private EditoraRepositorie editoraRepositorie;
	
	@Autowired
	private LivroRepositorie livroRepositorie;
	
	@Transactional
	public Editora insert(EditoraDto editoraDto) {
		Editora editora = new Editora();
		
		editora.setNome(editoraDto.nome());
		editora.setLivros(livroRepositorie.findAllById(editoraDto.livrosIds()).stream().collect(Collectors.toSet()));
		
		for(Livro livro : editora.getLivros()) {
			if (livro.getEditora() == null) {
				livro.setEditora(editora);
			}
		}
		
		return editoraRepositorie.save(editora);
	}
}
