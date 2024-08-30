package br.com.teste.livraria.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.teste.livraria.model.dtos.AuthenticationDto;
import br.com.teste.livraria.model.dtos.LoginResponseDto;
import br.com.teste.livraria.model.dtos.RegisterDto;
import br.com.teste.livraria.model.entities.Usuario;
import br.com.teste.livraria.model.entities.enums.UserRole;
import br.com.teste.livraria.model.repositories.UsuarioRepository;
import br.com.teste.livraria.model.services.TokenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationMaanger;

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private TokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid AuthenticationDto data) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
		var auth = this.authenticationMaanger.authenticate(usernamePassword);
		
		var token = tokenService.generateToken((Usuario)auth.getPrincipal());

		return ResponseEntity.ok(new LoginResponseDto(token));
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid RegisterDto data) {
		if (this.repository.findByLogin(data.login()) != null)
			return ResponseEntity.badRequest().build();

		String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
		Usuario usuario = new Usuario(data.login(), encryptedPassword, UserRole.valueOf(data.role()));

		this.repository.save(usuario);

		return ResponseEntity.ok().build();

	}
}
