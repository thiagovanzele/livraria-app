package br.com.teste.livraria.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.teste.livraria.model.dtos.ViaCepResponse;
import br.com.teste.livraria.model.entities.Endereco;
import br.com.teste.livraria.model.exceptions.IncorrectCepException;
import br.com.teste.livraria.model.exceptions.ResourceNotFoundException;
import br.com.teste.livraria.model.repositories.EnderecoRepository;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;

	public Endereco buscarEnderecoPorCep(String cep, String numero) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			String url = "https://viacep.com.br/ws/" + cep + "/json/";

			ResponseEntity<ViaCepResponse> response = restTemplate.getForEntity(url, ViaCepResponse.class);

			if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
				ViaCepResponse body = response.getBody();
				Endereco endereco = new Endereco();
				endereco.setCep(cep);
				endereco.setNumero(numero);
				endereco.setRua(body.logradouro());
				endereco.setBairro(body.bairro());
				endereco.setCidade(body.localidade());
				endereco.setEstado(body.uf());

				return enderecoRepository.save(endereco);
			} else {
				throw new ResourceNotFoundException(Endereco.class, "Endereço não encontrado");
			}
		} catch (HttpClientErrorException e) {
			throw new IncorrectCepException("Cep incorreto ou mal informado");
		}
	}
}
