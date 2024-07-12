package br.com.teste.livraria.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.teste.livraria.model.entities.Resumo;

public interface ResumoRepositorie extends JpaRepository<Resumo, Long> {

}
