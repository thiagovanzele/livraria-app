package br.com.teste.livraria.model.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.teste.livraria.model.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	Optional<Cliente> findClienteByEmail(String email);

	Optional<Cliente> findClienteByDocumento(String documento);

	default boolean existsByEmail(String email) {
		return findClienteByEmail(email).isPresent();
	}

	default boolean existsByDocumento(String documento) {
		return findClienteByDocumento(documento).isPresent();

	}
}
