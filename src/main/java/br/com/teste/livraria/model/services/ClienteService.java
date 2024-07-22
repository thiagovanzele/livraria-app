package br.com.teste.livraria.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.teste.livraria.model.dtos.ClienteDto;
import br.com.teste.livraria.model.entities.Cliente;
import br.com.teste.livraria.model.entities.Endereco;
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
}
