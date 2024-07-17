package br.com.teste.livraria.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.teste.livraria.model.entities.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {

}
