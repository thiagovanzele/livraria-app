package br.com.teste.livraria.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.teste.livraria.model.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
