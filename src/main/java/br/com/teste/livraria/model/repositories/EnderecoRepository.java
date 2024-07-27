package br.com.teste.livraria.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.teste.livraria.model.entities.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

	public Endereco findEnderecoByCep(String cep);
}
