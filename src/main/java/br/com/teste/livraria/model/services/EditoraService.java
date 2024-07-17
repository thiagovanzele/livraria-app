package br.com.teste.livraria.model.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.teste.livraria.model.dtos.EditoraDto;
import br.com.teste.livraria.model.entities.Editora;
import br.com.teste.livraria.model.entities.Livro;
import br.com.teste.livraria.model.exceptions.ResourceNotFoundException;
import br.com.teste.livraria.model.repositories.EditoraRepository;
import br.com.teste.livraria.model.repositories.LivroRepository;
import jakarta.transaction.Transactional;

@Service
public class EditoraService {

	@Autowired
	private EditoraRepository editoraRepositorie;
	
	@Autowired
	private LivroRepository livroRepositorie;
	
	public Editora findById(Long id) {
		return editoraRepositorie.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public List<Editora> findAll() {
		return editoraRepositorie.findAll();
	}
	
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
	
	@Transactional
	public void delete(Long id) {
		if (!editoraRepositorie.existsById(id)) {
			throw new ResourceNotFoundException(id);
		}
		
		editoraRepositorie.deleteById(id);
	}
	
	@Transactional
	public Editora update(Long id, EditoraDto obj) {
		Editora editora = editoraRepositorie.getReferenceById(id);
		if (editora == null) {
			throw new ResourceNotFoundException(id);
		}
		updateData(editora, obj);
		return editoraRepositorie.save(editora);
	}

	private void updateData(Editora editora, EditoraDto obj) {
		editora.setNome(obj.nome());
		editora.setLivros(livroRepositorie.findAllById(obj.livrosIds()).stream().collect(Collectors.toSet()));
		for (Livro livro : editora.getLivros()) {
			livro.setEditora(editora);
		}
	}
}
