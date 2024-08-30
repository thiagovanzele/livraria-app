package br.com.teste.livraria.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.teste.livraria.model.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	UserDetails findByLogin(String login);

}
