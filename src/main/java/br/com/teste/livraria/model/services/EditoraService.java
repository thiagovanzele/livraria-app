package br.com.teste.livraria.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.teste.livraria.model.dtos.EditoraDto;
import br.com.teste.livraria.model.entities.Editora;
import br.com.teste.livraria.model.entities.Livro;
import br.com.teste.livraria.model.exceptions.ResourceNotFoundException;
import br.com.teste.livraria.model.exceptions.ValidationException;
import br.com.teste.livraria.model.repositories.EditoraRepository;
import jakarta.transaction.Transactional;

@Service
public class EditoraService {

	@Autowired
	private EditoraRepository editoraRepositorie;
	
	public Editora findById(Long id) {
		return editoraRepositorie.findById(id).orElseThrow(() -> new ResourceNotFoundException(Editora.class, id));
	}
	
	public List<Editora> findAll() {
		return editoraRepositorie.findAll();
	}
	
	@Transactional
	public Editora insert(EditoraDto editoraDto) {
	    Editora editora = new Editora();
	    
	    try {
	        editora.setNome(editoraDto.nome());
	        	        
	        return editoraRepositorie.save(editora);
	    } catch (DataIntegrityViolationException e) {
	        throw new ValidationException("Valor já existente na base de dados");
	    }
	}

	
	@Transactional
	public void delete(Long id) {
		if (!editoraRepositorie.existsById(id)) {
			throw new ResourceNotFoundException(Editora.class, id);
		}
		
		editoraRepositorie.deleteById(id);
	}
	
	@Transactional
	public Editora update(Long id, EditoraDto obj) {
		Editora editora = editoraRepositorie.findById(id).orElseThrow(() -> new ResourceNotFoundException(Editora.class, id));
		updateData(editora, obj);
		return editoraRepositorie.save(editora);
	}

	private void updateData(Editora editora, EditoraDto obj) {
		editora.setNome(obj.nome());
		for (Livro livro : editora.getLivros()) {
			livro.setEditora(editora);
		}
	}
}
