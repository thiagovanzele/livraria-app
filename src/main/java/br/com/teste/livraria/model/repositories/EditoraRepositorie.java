package br.com.teste.livraria.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.teste.livraria.model.entities.Editora;

public interface EditoraRepositorie extends JpaRepository<Editora, Long> {

}
