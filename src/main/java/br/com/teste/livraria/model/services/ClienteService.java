package br.com.teste.livraria.model.services;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.teste.livraria.model.dtos.ClienteDto;
import br.com.teste.livraria.model.entities.Cliente;
import br.com.teste.livraria.model.entities.Endereco;
import br.com.teste.livraria.model.exceptions.ResourceNotFoundException;
import br.com.teste.livraria.model.exceptions.ValidationException;
import br.com.teste.livraria.model.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoService enderecoService;

	private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

	public Cliente insert(ClienteDto clienteDto) {
		validaCliente(clienteDto);
		if (!validaEmail(clienteDto.email())) {
			throw new ValidationException("Email inválido");
		}

		Cliente cliente = new Cliente();
		cliente.setNome(clienteDto.nome());
		cliente.setEmail(clienteDto.email());
		cliente.setDocumento(clienteDto.documento());

		Endereco endereco = enderecoService.buscarEnderecoPorCep(clienteDto.cep(), clienteDto.numero());
		cliente.setEndereço(endereco);

		return clienteRepository.save(cliente);
	}

	public Cliente findById(Long id) {
		return clienteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Cliente.class, id));
	}

	public List<Cliente> findAll() {
		List<Cliente> list = clienteRepository.findAll();
		return list;
	}

	public void delete(Long id) {
		if (!clienteRepository.existsById(id)) {
			throw new ResourceNotFoundException(Cliente.class, id);
		}

		clienteRepository.deleteById(id);
	}

	public Cliente update(Long id, ClienteDto clienteDto) {
		Cliente cliente = clienteRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(Cliente.class, id));
		updateData(cliente, clienteDto);
		return clienteRepository.save(cliente);

	}

	private void updateData(Cliente cliente, ClienteDto clienteDto) {
		Endereco endereco = enderecoService.buscarEnderecoPorCep(clienteDto.cep(), clienteDto.numero());
		cliente.setEndereço(endereco);
		cliente.setEmail(clienteDto.email());
		cliente.setNome(clienteDto.nome());

	}

	private void validaCliente(ClienteDto cliente) {
		if (clienteRepository.existsByEmail(cliente.email())) {
			throw new ValidationException("Cliente com este e-mail já cadastrado.");
		}

		if (clienteRepository.existsByDocumento(cliente.documento())) {
			throw new ValidationException("Cliente com este CPF já cadastrado.");
		}
	}

	private boolean validaEmail(String email) {
		if (email == null) {
			return false;
		}
		
		Matcher matcher = EMAIL_PATTERN.matcher(email);
		return matcher.matches();
	}
}