package br.com.teste.livraria.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.teste.livraria.model.dtos.ResumoDto;
import br.com.teste.livraria.model.entities.Livro;
import br.com.teste.livraria.model.entities.Resumo;
import br.com.teste.livraria.model.exceptions.ResourceNotFoundException;
import br.com.teste.livraria.model.exceptions.ValidationException;
import br.com.teste.livraria.model.repositories.ResumoRepository;

@Service
public class ResumoService {

	@Autowired
	private ResumoRepository resumoRepositore;

	@Autowired
	private LivroService livroService;

	public Resumo insert(ResumoDto obj) {
		try {
		Livro livro = livroService.findById(obj.livroId());

		Resumo resumo = new Resumo();
		resumo.setComentario(obj.resumo());

		livro.setResumo(resumo);

		resumo.setLivro(livro);
		return resumoRepositore.save(resumo);
		} catch (DataIntegrityViolationException e) {
			throw new ValidationException("O livro já possui um resumo");
		}
	}

	public Resumo findById(Long id) {
		return resumoRepositore.findById(id).orElseThrow(() -> new ResourceNotFoundException(Resumo.class, id));
	}

	public List<Resumo> findAll() {
		return resumoRepositore.findAll();
	}

	public void delete(Long id) {
		if (!resumoRepositore.existsById(id)) {
			throw new ResourceNotFoundException(Resumo.class, id);
		}
		resumoRepositore.deleteById(id);
	}

	public Resumo update(Long id, ResumoDto obj) {
		Resumo resumo = resumoRepositore.getReferenceById(id);

		if (resumo == null) {
			throw new ResourceNotFoundException(Resumo.class, id);
		}

		updateData(resumo, obj);
		return resumoRepositore.save(resumo);
	}

	private void updateData(Resumo resumo, ResumoDto obj) {
		resumo.setComentario(obj.resumo());
	}

}
