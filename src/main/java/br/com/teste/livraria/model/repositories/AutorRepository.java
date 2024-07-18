package br.com.teste.livraria.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.teste.livraria.model.entities.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long> {

	public Autor findAutorByNomeIgnoreCase(String nome);
}
