package br.com.teste.livraria.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.teste.livraria.model.dtos.ClienteDto;
import br.com.teste.livraria.model.entities.Cliente;
import br.com.teste.livraria.model.entities.Endereco;
import br.com.teste.livraria.model.exceptions.ResourceNotFoundException;
import br.com.teste.livraria.model.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoService enderecoService;

	public Cliente insert(ClienteDto clienteDto) {
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
		if (clienteRepository.existsById(id)) {
			clienteRepository.deleteById(id);
		}

		throw new ResourceNotFoundException(Cliente.class, id);
	}
	
	public Cliente update(Long id, ClienteDto clienteDto) {
		Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Cliente.class, id));
		updateData(cliente, clienteDto);
		return clienteRepository.save(cliente);
		
	}

	private void updateData(Cliente cliente, ClienteDto clienteDto) {
		Endereco endereco = enderecoService.buscarEnderecoPorCep(clienteDto.cep(), clienteDto.numero());
		cliente.setEndereço(endereco);
		cliente.setEmail(clienteDto.email());
		cliente.setNome(clienteDto.nome());
		
	}
}
